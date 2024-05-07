;
; a3part-D.asm
;
; Part D of assignment #3
;
;
; Student name: Luke Kuligowicz
; Student ID: V01019209
; Date of completed work: 11/26/2023
;
; **********************************
; Code provided for Assignment #3
;
; Author: Mike Zastre (2022-Nov-05)
;
; This skeleton of an assembly-language program is provided to help you 
; begin with the programming tasks for A#3. As with A#2 and A#1, there are
; "DO NOT TOUCH" sections. You are *not* to modify the lines within these
; sections. The only exceptions are for specific changes announced on
; Brightspace or in written permission from the course instruction.
; *** Unapproved changes could result in incorrect code execution
; during assignment evaluation, along with an assignment grade of zero. ***
;


; =============================================
; ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
; =============================================
;
; In this "DO NOT TOUCH" section are:
; 
; (1) assembler direction setting up the interrupt-vector table
;
; (2) "includes" for the LCD display
;
; (3) some definitions of constants that may be used later in
;     the program
;
; (4) code for initial setup of the Analog-to-Digital Converter
;     (in the same manner in which it was set up for Lab #4)
;
; (5) Code for setting up three timers (timers 1, 3, and 4).
;
; After all this initial code, your own solutions's code may start
;

.cseg
.org 0
	jmp reset

; Actual .org details for this an other interrupt vectors can be
; obtained from main ATmega2560 data sheet
;
.org 0x22
	jmp timer1

; This included for completeness. Because timer3 is used to
; drive updates of the LCD display, and because LCD routines
; *cannot* be called from within an interrupt handler, we
; will need to use a polling loop for timer3.
;
; .org 0x40
;	jmp timer3

.org 0x54
	jmp timer4

.include "m2560def.inc"
.include "lcd.asm"

.cseg
#define CLOCK 16.0e6
#define DELAY1 0.01
#define DELAY3 0.1
#define DELAY4 0.5

#define BUTTON_RIGHT_MASK 0b00000001	
#define BUTTON_UP_MASK    0b00000010
#define BUTTON_DOWN_MASK  0b00000100
#define BUTTON_LEFT_MASK  0b00001000

#define BUTTON_RIGHT_ADC  0x032
#define BUTTON_UP_ADC     0x0b0   ; was 0x0c3
#define BUTTON_DOWN_ADC   0x160   ; was 0x17c
#define BUTTON_LEFT_ADC   0x22b
#define BUTTON_SELECT_ADC 0x316

.equ PRESCALE_DIV=1024   ; w.r.t. clock, CS[2:0] = 0b101

; TIMER1 is a 16-bit timer. If the Output Compare value is
; larger than what can be stored in 16 bits, then either
; the PRESCALE needs to be larger, or the DELAY has to be
; shorter, or both.
.equ TOP1=int(0.5+(CLOCK/PRESCALE_DIV*DELAY1))
.if TOP1>65535
.error "TOP1 is out of range"
.endif

; TIMER3 is a 16-bit timer. If the Output Compare value is
; larger than what can be stored in 16 bits, then either
; the PRESCALE needs to be larger, or the DELAY has to be
; shorter, or both.
.equ TOP3=int(0.5+(CLOCK/PRESCALE_DIV*DELAY3))
.if TOP3>65535
.error "TOP3 is out of range"
.endif

; TIMER4 is a 16-bit timer. If the Output Compare value is
; larger than what can be stored in 16 bits, then either
; the PRESCALE needs to be larger, or the DELAY has to be
; shorter, or both.
.equ TOP4=int(0.5+(CLOCK/PRESCALE_DIV*DELAY4))
.if TOP4>65535
.error "TOP4 is out of range"
.endif

reset:
; ***************************************************
; **** BEGINNING OF FIRST "STUDENT CODE" SECTION ****
; ***************************************************

; Anything that needs initialization before interrupts
; start must be placed here.

.def DATAH=r25
.def DATAL=r24
.def BOUNDARY_H=r23
.def BOUNDARY_L=r22
.def CHAR_SAVE=r21

.equ ADCSRA_BTN=0x7A
.equ ADCSRB_BTN=0x7B
.equ ADMUX_BTN=0x7C
.equ ADCL_BTN=0x78
.equ ADCH_BTN=0x79

ldi r16, LOW(RAMEND)
out SPL, r16
ldi r16, HIGH(RAMEND)
out SPH, r16

.equ RIGHT  = 50
.equ UP	    = 176
.equ DOWN	= 352
.equ LEFT	= 555
.equ SELECT	= 800

; ***************************************************
; ******* END OF FIRST "STUDENT CODE" SECTION *******
; ***************************************************

; =============================================
; ====  START OF "DO NOT TOUCH" SECTION    ====
; =============================================

	; initialize the ADC converter (which is needed
	; to read buttons on shield). Note that we'll
	; use the interrupt handler for timer 1 to
	; read the buttons (i.e., every 10 ms)
	;
	ldi temp, (1 << ADEN) | (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0)
	sts ADCSRA, temp
	ldi temp, (1 << REFS0)
	sts ADMUX, r16

	; Timer 1 is for sampling the buttons at 10 ms intervals.
	; We will use an interrupt handler for this timer.
	ldi r17, high(TOP1)
	ldi r16, low(TOP1)
	sts OCR1AH, r17
	sts OCR1AL, r16
	clr r16
	sts TCCR1A, r16
	ldi r16, (1 << WGM12) | (1 << CS12) | (1 << CS10)
	sts TCCR1B, r16
	ldi r16, (1 << OCIE1A)
	sts TIMSK1, r16

	; Timer 3 is for updating the LCD display. We are
	; *not* able to call LCD routines from within an 
	; interrupt handler, so this timer must be used
	; in a polling loop.
	ldi r17, high(TOP3)
	ldi r16, low(TOP3)
	sts OCR3AH, r17
	sts OCR3AL, r16
	clr r16
	sts TCCR3A, r16
	ldi r16, (1 << WGM32) | (1 << CS32) | (1 << CS30)
	sts TCCR3B, r16
	; Notice that the code for enabling the Timer 3
	; interrupt is missing at this point.

	; Timer 4 is for updating the contents to be displayed
	; on the top line of the LCD.
	ldi r17, high(TOP4)
	ldi r16, low(TOP4)
	sts OCR4AH, r17
	sts OCR4AL, r16
	clr r16
	sts TCCR4A, r16
	ldi r16, (1 << WGM42) | (1 << CS42) | (1 << CS40)
	sts TCCR4B, r16
	ldi r16, (1 << OCIE4A)
	sts TIMSK4, r16

	sei

; =============================================
; ====    END OF "DO NOT TOUCH" SECTION    ====
; =============================================

; ****************************************************
; **** BEGINNING OF SECOND "STUDENT CODE" SECTION ****
; ****************************************************

start:
	
	rcall lcd_init

	ldi r16, ' ' 
	sts LAST_BUTTON_PRESSED, r16

	ldi r16, 0x87
	sts ADCSRA_BTN, r16

	ldi r16, 0x00
	sts ADCSRB_BTN, r16

	ldi r16, 0x40
	sts ADMUX_BTN, r16

	ldi r16, low(900);
	mov BOUNDARY_L, r16
	ldi r16, high(900)
	mov BOUNDARY_H, r16

	clr r16
	sts CURRENT_CHAR_INDEX, r16

	rjmp find_length

stop:
	rjmp stop

find_length:
	clr r17
	ldi r31, high(AVAILABLE_CHARSET<<1)
	ldi r30, low(AVAILABLE_CHARSET<<1)
	
	search:
		lpm r16, z+
		cpi r16, 0
		breq escape
		inc r17
		rjmp search

	escape:
		dec r17
		sts CHARSET_LEN, r17
	
init_top_line:
	push r16
	push r17
	push r18
	push ZL
	push ZH
	push YL
	push YH

	ldi r16, 16

	ldi r31, high(TOP_LINE_CONTENT)
	ldi r30, low(TOP_LINE_CONTENT)
	ldi r29, high(CURRENT_CHARSET_INDEX)
	ldi r28, low(CURRENT_CHARSET_INDEX)
	ldi r17, ' '
	ldi r18, 0
	loop_tl:
		st Z+, r17
		st Y+, r18
		dec r16
		cpi r16, 0
		breq go_timer3
		rjmp loop_tl

	go_timer3:
	pop YH
	pop YL
	pop ZH
	pop ZL
	pop r18
	pop r17
	pop r16
		rjmp timer3

timer1:
	push r16
	lds r16, SREG
	push r16
	lds	r16, ADCSRA_BTN	

	ori r16, 0x40
	sts	ADCSRA_BTN, r16

wait:	lds r16, ADCSRA_BTN
		andi r16, 0x40
		brne wait

		lds DATAL, ADCL_BTN
		lds DATAH, ADCH_BTN

		clr r16
		sts BUTTON_IS_PRESSED, r16
		cp DATAL, BOUNDARY_L
		cpc DATAH, BOUNDARY_H
		brsh skip		
		ldi r16, 1
		sts BUTTON_IS_PRESSED, r16

	compare_button_pressed:
		push DATAL
		push DATAH
		subi DATAL, low(RIGHT)
		sbci DATAH, high(RIGHT)
		pop DATAH
		pop DATAL
		brmi right_button_pressed
		breq right_button_pressed

		push DATAL
		push DATAH
		subi DATAL, low(UP)
		sbci DATAH, high(UP)
		pop DATAH
		pop DATAL
		brmi up_button_pressed
		breq up_button_pressed

		push DATAL
		push DATAH
		subi DATAL, low(DOWN)
		sbci DATAH, high(DOWN)
		pop DATAH
		pop DATAL
		brmi down_button_pressed
		breq down_button_pressed

		push DATAL
		push DATAH
		subi DATAL, low(LEFT)
		sbci DATAH, high(LEFT)
		pop DATAH
		pop DATAL
		brmi left_button_pressed
		breq left_button_pressed
		rjmp select_button_pressed

	right_button_pressed:
		ldi r16, 'R' 
		sts LAST_BUTTON_PRESSED, r16
		rjmp skip
	up_button_pressed:
		ldi r16, 'U' 
		sts LAST_BUTTON_PRESSED, r16
		rjmp skip
	left_button_pressed:
		ldi r16, 'L' 
		sts LAST_BUTTON_PRESSED, r16
		rjmp skip
	down_button_pressed:
		ldi r16, 'D' 
		sts LAST_BUTTON_PRESSED, r16
		rjmp skip
	select_button_pressed:
		ldi r16, 'S' 
		sts LAST_BUTTON_PRESSED, r16
		rjmp skip

	skip:
		pop r16
		sts SREG, r16
		pop r16
		reti

timer3:
	in temp, TIFR3
	sbrs temp, OCF3A
	rjmp timer3


	update_lcd:
		ldi r16, 1
		ldi r17, 15
		push r16 ;row
		push r17 ;column
		rcall lcd_gotoxy
		pop r17
		pop r16

		lds r16, BUTTON_IS_PRESSED

		cpi r16, 1
		breq set_star
		rjmp set_dash

		set_dash:
			ldi r16, '-'
			push r16
			rcall lcd_putchar
			pop r16
			rjmp reset_timer_3
		set_star:
			lds r16, LAST_BUTTON_PRESSED
			cpi r16, 'S'
			breq skip_sel
			ldi r16, 1
			ldi r17, 0
			push r16 ;row
			push r17 ;column
			rcall lcd_gotoxy
			pop r17
			pop r16
			ldi r16, ' '
			push r16
			rcall lcd_putchar
			rcall lcd_putchar
			rcall lcd_putchar
			rcall lcd_putchar
			pop r16
			skip_sel:
			ldi r16, 1
			ldi r17, 15
			push r16 ;row
			push r17 ;column
			rcall lcd_gotoxy
			pop r17
			pop r16
			ldi r16, '*'
			push r16
			rcall lcd_putchar
			pop r16

			check_button_pressed:
				lds r18, LAST_BUTTON_PRESSED
				cpi r18, 'R'
				breq right_button_sub
				cpi r18, 'U'
				breq up_button_sub
				cpi r18, 'L'
				breq left_button_sub
				cpi r18, 'D'
				breq down_button_sub
				rjmp reset_timer_3

			right_button_sub:
				ldi r16, 1
				ldi r17, 3
				push r16 ;row
				push r17 ;column
				rcall lcd_gotoxy
				pop r17
				pop r16
				push r18
				rcall lcd_putchar
				pop r18
				rjmp update_top_line
			up_button_sub:
				ldi r16, 1
				ldi r17, 2
				push r16 ;row
				push r17 ;column
				rcall lcd_gotoxy
				pop r17
				pop r16
				push r18
				rcall lcd_putchar
				pop r18
				rjmp update_top_line

			left_button_sub:
				ldi r16, 1
				ldi r17, 0
				push r16 ;row
				push r17 ;column
				rcall lcd_gotoxy
				pop r17
				pop r16
				push r18
				rcall lcd_putchar
				pop r18
				rjmp reset_timer_3
			down_button_sub:
				ldi r16, 1
				ldi r17, 1
				push r16 ;row
				push r17 ;column
				rcall lcd_gotoxy
				pop r17
				pop r16
				push r18
				rcall lcd_putchar
				pop r18	
				rjmp update_top_line

	;Now updates the top_line at column CURRENT_CHAR_INDEX, 
	;Instead of grabbing the first element of TOP_LINE_CONTENT, adds the CURRENT_CHAR_INDEX to it to grab the current element stored in TOP_LINE_CONTENT
	update_top_line:
		in ZL, SPL
		in ZH, SPH
		ldi r16, 0
		;New column value
		lds r18, CURRENT_CHAR_INDEX

		ldi r31, high(TOP_LINE_CONTENT)
		ldi r30, low(TOP_LINE_CONTENT)

		;add the offset to grab the correct TOP_LINE_CONTENT
		add r30, r18
		adc r31, r16

		upd_tl:
			ld r17, Z
			push r16 ;row
			push r18 ;column
			rcall lcd_gotoxy
			pop r18
			pop r16
			push r17
			rcall lcd_putchar
			pop r17

		reset_timer_3:
			ldi temp, 1<<OCF3A ;clear bit 1 in TIFR3 by writing logical one to its bit position, P163 of the Datasheet
			out TIFR3, temp

	rjmp timer3

;
; Note: There is no "timer3" interrupt handler as you must use
; timer3 in a polling style (i.e. it is used to drive the refreshing
; of the LCD display, but LCD functions cannot be called/used from
; within an interrupt handler).


timer4:
	push r16
	lds r16, SREG
	push r16
	push r17

		push ZL
		push ZH
		push YL
		push YH
		push XL
		push XH
		push r16
		push r17
		push r18
		push r19

		ldi r31, high(AVAILABLE_CHARSET<<1)
		ldi r30, low(AVAILABLE_CHARSET<<1)	
		ldi r29, high(TOP_LINE_CONTENT)
		ldi r28, low(TOP_LINE_CONTENT)
		ldi r27, high(CURRENT_CHARSET_INDEX)
		ldi r26, low(CURRENT_CHARSET_INDEX)

		lds r16, BUTTON_IS_PRESSED
		cpi r16, 1
		brne skip_skip4

		lds r18, LAST_BUTTON_PRESSED
		cpi r18, 'U'
		breq up_button_func4
		cpi r18, 'D'
		breq down_button_func4
		cpi r18, 'R'
		breq right_button_func4
		cpi r18, 'L'
		breq left_button_func4
		rjmp skip_4

		skip_skip4:
			rjmp skip_4



		;nows stores the result into CURRENT_CHARSET_INDEX at index CURRENT_CHAR_INDEX, as we add it to the former
		up_button_func4:
			clr r18
			;grabs the actual current_charset_index given the char_index
			lds r19, CURRENT_CHAR_INDEX
			add r26, r19
			adc r27, r18
			ld r17, x
			lds r16, CHARSET_LEN
			;only want to store if it is no the same length of the charset_len
			cp r17, r16
			breq skip_4
			;then we increase it and store it
			inc r17
			st X, r17
			rjmp update_line

		;nows stores the result into CURRENT_CHARSET_INDEX at index CURRENT_CHAR_INDEX, as we add it to the former
		down_button_func4:
			clr r18
			;grabs the actual current_charset_index given the char_index
			lds r19, CURRENT_CHAR_INDEX
			add r26, r19
			adc r27, r18
			ld r17, x
			;only want to store if it is not 0
			cpi r17, 0
			breq skip_4
			;then we decrease it and store it
			dec r17
			st X, r17
			rjmp update_line

		;Modifies CURRENT_CHAR_INDEX by dec, min at 0
		left_button_func4:
			lds r17, CURRENT_CHAR_INDEX
			cpi r17, 0
			breq left_continue
			dec r17
			left_continue:
			sts CURRENT_CHAR_INDEX, r17
			rjmp skip_4

		; Modifies CURRENT_CHAR_INDEX by inc, max at 15 (max bound of lcd screen grid)
		right_button_func4:
			lds r17, CURRENT_CHAR_INDEX
			cpi r17, 15
			breq skip_4
			inc r17
			sts CURRENT_CHAR_INDEX, r17
			rjmp skip_4




		;CURRENT_CHAR_INDEX allows us to modify the correct index of TOP_LINE_CONTENT without modifying the first
		update_line:

			lds r19, CURRENT_CHAR_INDEX
			clr r18
			add r28, r19
			adc r29, r18

			insert:
				clr r19

				ld r16, X
				add r30, r16
				adc r31, r19
				lpm r18, Z
				st Y, r18
	

	;return all modifies registers and the SREG
	skip_4:
		pop r19
		pop r18
		pop r17
		pop r16
		pop XH
		pop XL
		pop YH
		pop YL
		pop ZH
		pop ZL
		pop r17
		pop r16
		sts SREG, r16
		pop r16
		reti

; ****************************************************
; ******* END OF SECOND "STUDENT CODE" SECTION *******
; ****************************************************


; =============================================
; ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
; =============================================

; r17:r16 -- word 1
; r19:r18 -- word 2
; word 1 < word 2? return -1 in r25
; word 1 > word 2? return 1 in r25
; word 1 == word 2? return 0 in r25
;
compare_words:
	; if high bytes are different, look at lower bytes
	cp r17, r19
	breq compare_words_lower_byte

	; since high bytes are different, use these to
	; determine result
	;
	; if C is set from previous cp, it means r17 < r19
	; 
	; preload r25 with 1 with the assume r17 > r19
	ldi r25, 1
	brcs compare_words_is_less_than
	rjmp compare_words_exit

compare_words_is_less_than:
	ldi r25, -1
	rjmp compare_words_exit

compare_words_lower_byte:
	clr r25
	cp r16, r18
	breq compare_words_exit

	ldi r25, 1
	brcs compare_words_is_less_than  ; re-use what we already wrote...

compare_words_exit:
	ret

.cseg
AVAILABLE_CHARSET: .db "0123456789abcdef_", 0


.dseg

BUTTON_IS_PRESSED: .byte 1			; updated by timer1 interrupt, used by LCD update loop
LAST_BUTTON_PRESSED: .byte 1        ; updated by timer1 interrupt, used by LCD update loop

TOP_LINE_CONTENT: .byte 16			; updated by timer4 interrupt, used by LCD update loop
CURRENT_CHARSET_INDEX: .byte 16		; updated by timer4 interrupt, used by LCD update loop
CURRENT_CHAR_INDEX: .byte 1			; ; updated by timer4 interrupt, used by LCD update loop


; =============================================
; ======= END OF "DO NOT TOUCH" SECTION =======
; =============================================


; ***************************************************
; **** BEGINNING OF THIRD "STUDENT CODE" SECTION ****
; ***************************************************

.dseg
CHARSET_LEN: .byte 1

; If you should need additional memory for storage of state,
; then place it within the section. However, the items here
; must not be simply a way to replace or ignore the memory
; locations provided up above.


; ***************************************************
; ******* END OF THIRD "STUDENT CODE" SECTION *******
; ***************************************************
