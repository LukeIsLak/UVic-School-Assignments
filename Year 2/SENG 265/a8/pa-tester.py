#!/usr/bin/env python3

import sys
import re

LEVELS = {0:"INVALID", 1:"VERY_WEAK", 2:"WEAK", 3:"MEDIUM", 4:"STRONG", 5:"VERY_STRONG"}

def main():
	""" This script reads a text from standard input,
	analyzes the validity of a password in each line,
	if valid assesses the strength of the password,
	and writes results of the password analysis into
	the standard output  """

	# if arguments provided, show error message
	if len(sys.argv) != 1:
		print("No arguments should be provided.")
		print("Usage: %s" % sys.argv[0])
		return 1;
		
	"""For each line in stdin"""
	for line in sys.stdin:
			level = 0
			output = []
			line = line.strip("\n")
			"""If it has less than 7 characters"""
			if((re.search(r'^.{0,7}$', line))):
				level = 0
				output.append('TOO_SHORT')
			
			"""If it has a non ascii value"""
			if((re.search(r'[^\x00-\x7F]', line))):
				level = 0
				output.append('NONASCII')
			
			"""If it is invalid, break from loop"""
			if (len(output) >= 1):
				output_line(level, output)
				continue
				
			"""If it is not invalid, proceed"""
			
			"""If there is an uppercase ascii"""
			if((re.search(r'[\x41-\x5A]', line))):
				level = calc_level(level)
				output.append('UPPERCASE')
				
			"""If there is a lowercase ascii"""
			if((re.search(r'[\x61-\x7A]', line))):
				level = calc_level(level)
				output.append('LOWERCASE')
				
			"""If there is a decimal number"""
			if((re.search(r'\d', line))):
				level = calc_level(level)
				output.append('NUMBER')
				
			"""If there is a ascii, special character"""	
			if((re.search(r'\W', line))):
				level = calc_level(level)
				output.append('SPECIAL')
				
			"""If there is a sequence of 3 characters"""
			if((re.search(r'(.)\1{2}', line))):
				level = level - 1
				output.append('sequence')
				
			"""output the line"""
			output_line(level, output)

"""Output all the password characteristics"""
def output_line(level, output):
	print(str(level)+","+LEVELS[level], end =",")
	for i,element in enumerate(output):
		print(element, end="," if i < len(output)-1 else "\n")

"""Calculate the strength level"""
def calc_level(level):
	return level+1 if level != 0 else 2
	




	# end the script normally
	return 0

if __name__ == "__main__":
	main()
