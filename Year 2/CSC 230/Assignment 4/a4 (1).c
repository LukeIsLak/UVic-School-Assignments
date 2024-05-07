/* a4.c
 * CSC Fall 2022
 * 
 * Student name: Luke Kuligowicz
 * Student UVic ID: V01019209
 * Date of completed work: 12/09/2023
 *
 *
 * Code provided for Assignment #4
 *
 * Author: Mike Zastre (2022-Nov-22)
 *
 * This skeleton of a C language program is provided to help you
 * begin the programming tasks for A#4. As with the previous
 * assignments, there are "DO NOT TOUCH" sections. You are *not* to
 * modify the lines within these section.
 *
 * You are also NOT to introduce any new program-or file-scope
 * variables (i.e., ALL of your variables must be local variables).
 * YOU MAY, however, read from and write to the existing program- and
 * file-scope variables. Note: "global" variables are program-
 * and file-scope variables.
 *
 * UNAPPROVED CHANGES to "DO NOT TOUCH" sections could result in
 * either incorrect code execution during assignment evaluation, or
 * perhaps even code that cannot be compiled.  The resulting mark may
 * be zero.
 */


/* =============================================
 * ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
 * =============================================
 */

#define __DELAY_BACKWARD_COMPATIBLE__ 1
#define F_CPU 16000000UL

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#define DELAY1 0.000001
#define DELAY3 0.01

#define PRESCALE_DIV1 8
#define PRESCALE_DIV3 64
#define TOP1 ((int)(0.5 + (F_CPU/PRESCALE_DIV1*DELAY1))) 
#define TOP3 ((int)(0.5 + (F_CPU/PRESCALE_DIV3*DELAY3)))

#define PWM_PERIOD ((long int)500)

volatile long int count = 0;
volatile long int slow_count = 0;


ISR(TIMER1_COMPA_vect) {
	count++;
}


ISR(TIMER3_COMPA_vect) {
	slow_count += 5;
}

/* =======================================
 * ==== END OF "DO NOT TOUCH" SECTION ====
 * =======================================
 */


/* *********************************************
 * **** BEGINNING OF "STUDENT CODE" SECTION ****
 * *********************************************
 */

void led_state(uint8_t LED, uint8_t state) {
	/*define the PORTL experience*/
	uint8_t l_led = PORTL;

	/*Set the leds based on which led we are going to set*/
	switch(LED) {
		case 0: l_led = (state != 0) ? l_led | 0b10000000 : l_led & 0b01111111; break;
		case 1: l_led = (state != 0) ? l_led | 0b00100000 : l_led & 0b11011111; break;
		case 2: l_led = (state != 0) ? l_led | 0b00001000 : l_led & 0b11110111; break;
		case 3: l_led = (state != 0) ? l_led | 0b00000010 : l_led & 0b11111101; break;
		default: l_led = (state != 0) ? l_led | 0b10101010 : 0; break;
	}
	
	PORTL = l_led;
}



void SOS() {
    uint8_t light[] = {
        0x1, 0, 0x1, 0, 0x1, 0,
        0xf, 0, 0xf, 0, 0xf, 0,
        0x1, 0, 0x1, 0, 0x1, 0,
        0x0
    };

    int duration[] = {
        100, 250, 100, 250, 100, 500,
        250, 250, 250, 250, 250, 500,
        100, 250, 100, 250, 100, 250,
        250
    };

	int length = 19;
	
	/*Depending on the number in lights, update the light*/
	for (int i = 0; i < length; i++) {
		for (int k = 0; k < 4; k++) {
			/*set the led based on the hex value, either 0, 1, or f*/
			led_state(k, (1<<k)&light[i]);
		}
		/*Delay for that element*/
		_delay_ms(duration[i]);
	}
}

/*Glow based on a brighness*/
void glow(uint8_t LED, float brightness) {
	/*Create a threshold to turn on and off the light rapidly*/
	int threshold = PWM_PERIOD * brightness;
	/*forever*/
	while (1) { 
		/*Turn on the led if the count if greater than threshold*/
		if (count < threshold) { 
			led_state(LED, 1);
		/*Turn off is the count is less than the period*/
		} else if (count < PWM_PERIOD) { 
			led_state(LED, 0);
		
		/*Turn on and reset count*/
		} else {
			count = 0; 
			led_state(LED, 1);
		} 
	}
}


/*Pulse glow*/
void pulse_glow(uint8_t LED) {
	/*forever*/
	while (1) {
		/*set the light on gradually*/
		for (float threshold = 0; threshold < PWM_PERIOD; threshold = slow_count*0.1) {
			/*Turn on the led if the count if greater than threshold*/
			if (count < threshold) {
				led_state(LED, 1);
				/*Turn off is the count is less than the period*/
				} else if (count < PWM_PERIOD) {
				led_state(LED, 0);
				
				/*Turn on and reset count*/
				} else {
				count = 0;
				led_state(LED, 1);
			}
		}
		/*reset slow count*/
		slow_count = 0;
		/*once the light in fully on, gradually set it off*/
		for (float threshold = PWM_PERIOD; threshold > 0; threshold = PWM_PERIOD-(slow_count*0.1)) {
			/*Turn on the led if the count if greater than threshold*/
			if (count < threshold) {
				led_state(LED, 1);
				/*Turn off is the count is less than the period*/
				} else if (count < PWM_PERIOD) {
				led_state(LED, 0);
				
				/*Turn on and reset count*/
				} else {
				count = 0;
				led_state(LED, 1);
			}
		}
		/*reset slow count*/
		slow_count = 0;
	}
}


void light_show() {
	/*Set constants*/
	const int slow = 200;
	const int fast = 100;

/*Custom set lights based on the video*/
uint8_t light[] = {
	0xff, 0, 0xff, 0, 0xff, 0,
	
	0x28, 0, 0x82, 0, 
	
	0xff, 0, 0xff, 0, 0xff, 0,
	
	0x82, 0, 0x28, 0, 
	
	0x80, 0xA0, 0x28, 0x0A, 0x02, 0x0A, 0x28, 0xA0,
	
	0x80, 0xA0, 0x28, 0x0A, 0x02, 0x0A, 0x28, 0,
	
	0xff, 0, 0xff, 0, 0x28, 0, 0x28, 0
};

/*Custom set speeds based on the video*/
int duration[] = {
	slow, slow, slow, slow, slow, slow,
	
	fast, fast, fast, fast,
	
	slow, slow, slow, slow, slow, slow,
	
	fast, fast, fast, fast,
	
	fast, fast, fast, fast, fast, fast, fast, fast,
	
	fast, fast, fast, fast, fast, fast, fast, fast,
	
	slow, slow, slow, slow, slow, slow, slow	
};

int length = 44;

/*For each element in the array*/
for (int i = 0; i < length; i++) {
	/*For each led, grab the hex, and at the correct bit position, set the led to that state (on or off)*/
	for (int k = 3; k >= 0; k--) {
		led_state(k, (2<<(k*2))&light[i]);
	}
	/*Delay for that element*/
	_delay_ms(duration[i]);
} 
}


/* ***************************************************
 * **** END OF FIRST "STUDENT CODE" SECTION **********
 * ***************************************************
 */


/* =============================================
 * ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
 * =============================================
 */

int main() {
    /* Turn off global interrupts while setting up timers. */

	cli();

	/* Set up timer 1, i.e., an interrupt every 1 microsecond. */
	OCR1A = TOP1;
	TCCR1A = 0;
	TCCR1B = 0;
	TCCR1B |= (1 << WGM12);
    /* Next two lines provide a prescaler value of 8. */
	TCCR1B |= (1 << CS11);
	TCCR1B |= (1 << CS10);
	TIMSK1 |= (1 << OCIE1A);

	/* Set up timer 3, i.e., an interrupt every 10 milliseconds. */
	OCR3A = TOP3;
	TCCR3A = 0;
	TCCR3B = 0;
	TCCR3B |= (1 << WGM32);
    /* Next line provides a prescaler value of 64. */
	TCCR3B |= (1 << CS31);
	TIMSK3 |= (1 << OCIE3A);


	/* Turn on global interrupts */
	sei();

/* =======================================
 * ==== END OF "DO NOT TOUCH" SECTION ====
 * =======================================
 */


/* *********************************************
 * **** BEGINNING OF "STUDENT CODE" SECTION ****
 * *********************************************
 */
	
	/*Initiate the port so it can recieve inputs*/
	DDRL = 0xff;
	
/*
	led_state(0, 1);
	_delay_ms(1000);
	led_state(2, 1);
	_delay_ms(1000);
	led_state(1, 1);
	_delay_ms(1000);
	led_state(2, 0);
	_delay_ms(1000);
	led_state(0, 0);
	_delay_ms(1000);
	led_state(1, 0);
	_delay_ms(1000);
 */
/*
	SOS();
*/
/*
	glow(2, 1);
*/

/*
	pulse_glow(3);
*/

/*

	light_show();
*/

/* ****************************************************
 * **** END OF SECOND "STUDENT CODE" SECTION **********
 * ****************************************************
 */
}
