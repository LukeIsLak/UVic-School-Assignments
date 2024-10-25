#! /usr/bin/env python3

import sys

def main():
	input = sys.stdin
    
	output = []
    
	for line in input:
		token = line.split(",")
		for i in range(len(token)):
			output.append(token[i].split("\n")[0])
	
	output = set(output)
	output = list(output)
	output.sort()
	
	for word in output:
		print(word)
		


if __name__ == "__main__":
    main()