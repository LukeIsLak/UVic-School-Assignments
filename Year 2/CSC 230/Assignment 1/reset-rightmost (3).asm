; reset-rightmost.asm
; CSC 230: Fall 2022
;
; Code provided for Assignment #1
;
; Mike Zastre (2022-Sept-22)

; This skeleton of an assembly-language program is provided to help you
; begin with the programming task for A#1, part (b). In this and other
; files provided through the semester, you will see lines of code
; indicating "DO NOT TOUCH" sections. You are *not* to modify the
; lines within these sections. The only exceptions are for specific
; changes announced on conneX or in written permission from the course
; instructor. *** Unapproved changes could result in incorrect code
; execution during assignment evaluation, along with an assignment grade
; of zero. ****
;
; In a more positive vein, you are expected to place your code with the
; area marked "STUDENT CODE" sections.

; ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
; Your task: You are to take the bit sequence stored in R16,
; and to reset the rightmost contiguous sequence of set
; by storing this new value in R25. For example, given
; the bit sequence 0b01011100, resetting the right-most
; contigous sequence of set bits will produce 0b01000000.
; As another example, given the bit sequence 0b10110110,
; the result will be 0b10110000.
;
; Your solution must work, of course, for bit sequences other
; than those provided in the example. (How does your
; algorithm handle a value with no set bits? with all set bits?)

; ANY SIGNIFICANT IDEAS YOU FIND ON THE WEB THAT HAVE HELPED
; YOU DEVELOP YOUR SOLUTION MUST BE CITED AS A COMMENT (THAT
; IS, WHAT THE IDEA IS, PLUS THE URL).

    .cseg
    .org 0

; ==== END OF "DO NOT TOUCH" SECTION ==========

	ldi R16, 0b01011100


	; THE RESULT **MUST** END UP IN R25

; **** BEGINNING OF "STUDENT CODE" SECTION **** 

	;preset data
	mov r25, r16
	ldi r17, 8

	;find the first group of ones, move bit 0 of r16 into carry, if 1, move onto contigous_found, otherwise rotate as normal
	loop:
		lsr r16
		brcs contigous_found
		ror r25

		dec r17
		brne loop

		;end of the loop reached, stop the program
		rjmp reset_rightmost_stop

	;for the first group of 1, rotate in a 0 instead of 1
	contigous_found:
		lsr r25
		dec r17

		;terminate out of loop if already looped through
		breq reset_rightmost_stop
		lsr r16
		brcs contigous_found
		ror r25

		;no more ones detected
		rjmp finish

	;rotate through the rest of the byte as normal
	finish:
		dec r17

		;terminate the loop and go into the program
		breq reset_rightmost_stop
		lsr r16
		ror r25
		rjmp finish


; **** END OF "STUDENT CODE" SECTION ********** 



; ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
reset_rightmost_stop:
    rjmp reset_rightmost_stop


; ==== END OF "DO NOT TOUCH" SECTION ==========
