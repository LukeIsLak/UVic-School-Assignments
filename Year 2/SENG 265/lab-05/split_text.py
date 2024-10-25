#!/usr/bin/env python3

import sys

def main():
	try:
		text = sys.argv[1]
		split_text = text.split(",")
		print(split_text)
		
		for token in split_text:
			print(token)
	except:
		print("Error: Error retrieving text, argument may not be present")

if __name__ == "__main__":
    main()
