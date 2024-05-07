; a2-signalling.asm
; CSC 230: Fall 2022
;
; Student name:
; Student ID:
; Date of completed work:
;
; *******************************
; Code provided for Assignment #2
;
; Author: Mike Zastre (2022-Oct-15)
;
 
; This skeleton of an assembly-language program is provided to help you
; begin with the programming tasks for A#2. As with A#1, there are "DO
; NOT TOUCH" sections. You are *not* to modify the lines within these
; sections. The only exceptions are for specific changes changes
; announced on Brightspace or in written permission from the course
; instructor. *** Unapproved changes could result in incorrect code
; execution during assignment evaluation, along with an assignment grade
; of zero. ****

.include "m2560def.inc"
.cseg
.org 0

; ***************************************************
; **** BEGINNING OF FIRST "STUDENT CODE" SECTION ****
; ***************************************************

	; initializion code will need to appear in this
    ; section

	;Set PORTL and PORTB as output
	ldi r16, 0xFF
	sts DDRL, r16
	out DDRB, r16

	;will we used in set_leds
	.def rL = r20
	.def rB = r19
	.def n = r18

; ***************************************************
; **** END OF FIRST "STUDENT CODE" SECTION **********
; ***************************************************

; ---------------------------------------------------
; ---- TESTING SECTIONS OF THE CODE -----------------
; ---- TO BE USED AS FUNCTIONS ARE COMPLETED. -------
; ---------------------------------------------------
; ---- YOU CAN SELECT WHICH TEST IS INVOKED ---------
; ---- BY MODIFY THE rjmp INSTRUCTION BELOW. --------
; -----------------------------------------------------

	rjmp test_part_d
	; Test code


test_part_a:
	ldi r16, 0b00100001
	rcall set_leds
	rcall delay_long

	clr r16
	rcall set_leds
	rcall delay_long

	ldi r16, 0b00111000
	rcall set_leds
	rcall delay_short

	clr r16
	rcall set_leds
	rcall delay_long

	ldi r16, 0b00100001
	rcall set_leds
	rcall delay_long

	clr r16
	rcall set_leds

	rjmp end


test_part_b:
	ldi r17, 0b00101010
	rcall slow_leds
	ldi r17, 0b00010101
	rcall slow_leds
	ldi r17, 0b00101010
	rcall slow_leds
	ldi r17, 0b00010101
	rcall slow_leds

	rcall delay_long
	rcall delay_long

	ldi r17, 0b00101010
	rcall fast_leds
	ldi r17, 0b00010101
	rcall fast_leds
	ldi r17, 0b00101010
	rcall fast_leds
	ldi r17, 0b00010101
	rcall fast_leds
	ldi r17, 0b00101010
	rcall fast_leds
	ldi r17, 0b00010101
	rcall fast_leds
	ldi r17, 0b00101010
	rcall fast_leds
	ldi r17, 0b00010101
	rcall fast_leds

	rjmp end

test_part_c:
	ldi r16, 0b11111000
	push r16
	rcall leds_with_speed
	pop r16

	ldi r16, 0b11011100
	push r16
	rcall leds_with_speed
	pop r16

	ldi r20, 0b00100000
test_part_c_loop:
	push r20
	rcall leds_with_speed
	pop r20
	lsr r20
	brne test_part_c_loop

	rjmp end


test_part_d:
	ldi r21, 'E'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long

	ldi r21, 'A'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long


	ldi r21, 'M'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long

	ldi r21, 'H'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long

	rjmp end


test_part_e:
	ldi r25, HIGH(WORD02 << 1)
	ldi r24, LOW(WORD02 << 1)
	rcall display_message
	rjmp end

end:
    rjmp end






; ****************************************************
; **** BEGINNING OF SECOND "STUDENT CODE" SECTION ****
; ****************************************************

set_leds:
	;save current values
	push r16
	push n

	;shift r16 by two
	lsl r16
	lsl r16

	;create the byte for port b
	clr n
	loop_port_b:
		inc n
		lsr rB; put a empty (spacer) value in rb at the front
		lsl r16; put leading bit of input into carry, shift over
		ror rB; transfer carry into rb at the front
		;do twice
		cpi n, 0x02 
		brne loop_port_b
		;shift rb by 4 to the right for correct format
		lsr rB
		lsr rB
		lsr rB
		lsr rB

	;create the byte for port l
	clr n
	loop_port_l:
		inc n
		lsr rL; put a empty (spacer) value in rl at the front
		lsl r16; put leading bit of input into carry, shift over
		ror rL; transfer carry into rl at the front
		;do four times
		cpi n,0x04
		brne loop_port_l

	;return saved values, output to the board
	pop n
	pop r16
	sts PORTL, rL
	out PORTB, rB

	ret


slow_leds:
	push r17 ;save current values
	mov r16, r17 ; copy r17 into r16 to be read by set_leds
	rcall set_leds ; set leds of r16
	rcall delay_long ; delay for long bit
	ldi r16, 0x00 ; turn off leds
	rcall set_leds ; turn off leds
	pop r17 ;return saved values
	ret


fast_leds:
	push r17 ;save current values
	mov r16, r17 ; copy r17 into r16 to be read by set_leds
	rcall set_leds ;set leds of r16
	rcall delay_short ;delay for short bit
	ldi r16, 0x00 ; turn off leds
	rcall set_leds ; turn off leds
	pop r17 ;return saved values
	ret


leds_with_speed:
	push ZH ;save current values
	push ZL
	push r16
	push r17

	in ZH, SPH
	in ZL, SPL

	ldd r16, Z+8 ; access the point in stack memory
	push r16 ; save r16 for later

	; see if the byte leads with two 1s
	andi r16, 0b11000000
	cpi r16, 0b11000000
	brne fast

	; if it doesn't, call slow_leds;
	short:
		pop r17 ; grab r16 from earlier
		rcall slow_leds
		rjmp return

	; if it does, call fast_leds
	fast:
		pop r17 ; grab r16 from earlier
		rcall fast_leds
		rjmp return

	; return saved values
	return:
		pop r17
		pop r16
		pop ZL
		pop ZH
		ret


; Note -- this function will only ever be tested
; with upper-case letters, but it is a good idea
; to anticipate some errors when programming (i.e. by
; accidentally putting in lower-case letters). Therefore
; the loop does explicitly check if the hyphen/dash occurs,
; in which case it terminates with a code not found
; for any legal letter.

encode_letter:
	;save current values
	push ZH
	push ZL

	push r17

	in ZH, SPH
	in ZL, SPL

	ldd r17, Z+7 ; access the point in stack memory
	
	; set Z to the first byte stored at address PATTERNS
	ldi r31, high(PATTERNS<<1)
	ldi r30, low(PATTERNS<<1)
	
	; search for the letter
	search:
		lpm r16, z+ ;set r16 to the ascii value of the letter at Z

		;compare with '-', if it is equal, something is wrong (value not found [i.e. letter lowercase]) and we will end the program
		cpi r16, 0x2D
		breq escape

		cp r16, r17 ;compare to the point in stack memory
		breq ini_byte ; start to create byte if equal

		;if '-' is not found, increment Z by 7 to get the next letter
		adiw ZH:ZL, 7
		rjmp search

	;something is wrong, and terminate the program
	escape:
		jmp end

	;prepare to get the byte
	ini_byte:
		ldi r18, 0x06 ; counter
		clr r25
		rjmp create_byte
	
	;create the byte
	create_byte:
		lpm r16, z+ ; grab the value at index Z (in memory) and increment Z
		ldi r17, 'n'
		cp r17, r16 ; compare r16 with r17, if r16 is greater than r17 (as in 'o' > 'n'), set the carry flag to 1
		rol r25 ; put the carry flag in r16
		dec r18
		breq compare_dur
		rjmp create_byte

	;set proper duration
	compare_dur:
		lpm r16, z+ ;grab the value at index Z, which should be the speed counter
		cpi r16, 1 ; compare with 1, if equal, will set_long
		breq set_long
		rjmp e_return

	set_long:
		ori r25, 0b11000000

	e_return:
		;return saved values
		pop r17
		pop ZL
		pop ZH
		ret

display_message:
	;save current values
	push ZH
	push ZL
	push r17
	push r25
	push r24
	
	;move the input into the Z register
	mov r31, r25
	mov r30, r24

	outer_loop:
		lpm r17, Z+ ;grab the current value at Z (input) then increment Z
		cpi r17, 0
		breq d_return; break if the current value at Z is 0
		push r17; push the current value of Z onto the stack for encode_letter
		rcall encode_letter
		pop r17
		push r25; push the return of encode_letter to the stack for leds_with_speed
		rcall leds_with_speed
		pop r25
	
		;set delay between letters
		rcall delay_short
		rcall delay_short
		rjmp outer_loop
		
		
	;return saved values
	d_return:
		pop r24
		pop r25
		pop r17
		pop ZL
		pop ZH
	ret


; ****************************************************
; **** END OF SECOND "STUDENT CODE" SECTION **********
; ****************************************************




; =============================================
; ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
; =============================================

; about one second
delay_long:
	push r16

	ldi r16, 14
delay_long_loop:
	rcall delay
	dec r16
	brne delay_long_loop

	pop r16
	ret


; about 0.25 of a second
delay_short:
	push r16

	ldi r16, 4
delay_short_loop:
	rcall delay
	dec r16
	brne delay_short_loop

	pop r16
	ret

; When wanting about a 1/5th of a second delay, all other
; code must call this function
;
delay:
	rcall delay_busywait
	ret


; This function is ONLY called from "delay", and
; never directly from other code. Really this is
; nothing other than a specially-tuned triply-nested
; loop. It provides the delay it does by virtue of
; running on a mega2560 processor.
;
delay_busywait:
	push r16
	push r17
	push r18

	ldi r16, 0x08
delay_busywait_loop1:
	dec r16
	breq delay_busywait_exit

	ldi r17, 0xff
delay_busywait_loop2:
	dec r17
	breq delay_busywait_loop1

	ldi r18, 0xff
delay_busywait_loop3:
	dec r18
	breq delay_busywait_loop2
	rjmp delay_busywait_loop3

delay_busywait_exit:
	pop r18
	pop r17
	pop r16
	ret


; Some tables
PATTERNS:
	; LED pattern shown from left to right: "." means off, "o" means
    ; on, 1 means long/slow, while 2 means short/fast.
	.db "A", "..oo..", 1
	.db "B", ".o..o.", 2
	.db "C", "o.o...", 1
	.db "D", ".....o", 1
	.db "E", "oooooo", 1
	.db "F", ".oooo.", 2
	.db "G", "oo..oo", 2
	.db "H", "..oo..", 2
	.db "I", ".o..o.", 1
	.db "J", ".....o", 2
	.db "K", "....oo", 2
	.db "L", "o.o.o.", 1
	.db "M", "oooooo", 2
	.db "N", "oo....", 1
	.db "O", ".oooo.", 1
	.db "P", "o.oo.o", 1
	.db "Q", "o.oo.o", 2
	.db "R", "oo..oo", 1
	.db "S", "....oo", 1
	.db "T", "..oo..", 1
	.db "U", "o.....", 1
	.db "V", "o.o.o.", 2
	.db "W", "o.o...", 2
	.db "X", "oo....", 2
	.db "Y", "..oo..", 2
	.db "Z", "o.....", 2
	.db "-", "o...oo", 1   ; Just in case!

WORD00: .db "HELLOWORLD", 0, 0
WORD01: .db "THE", 0
WORD02: .db "QUICK", 0
WORD03: .db "BROWN", 0
WORD04: .db "FOX", 0
WORD05: .db "JUMPED", 0, 0
WORD06: .db "OVER", 0, 0
WORD07: .db "THE", 0
WORD08: .db "LAZY", 0, 0
WORD09: .db "DOG", 0

; =======================================
; ==== END OF "DO NOT TOUCH" SECTION ====
; =======================================

