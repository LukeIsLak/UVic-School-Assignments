#!/usr/bin/env python3

import os
import sys

'''Some Global Variables for the Matrix, Keys, and File Paths'''
matrix = {}
matrix_keys = []

input_path = sys.argv[1]
output_path = sys.argv[2]

'''Reads the file from the given path, outputs the contents to a matrix'''
def read_file(file_name, d_path, tot_files, index):
	
	'''Read the file, tokenize each line'''
	inp_file = open(d_path + '/' + file_name, "r")
	for line in inp_file:
		line_tok = line.strip().split()
		
		key, value = line_tok[0], line_tok[1]
		
		'''If the current key from line isn't noted, note it'''
		if key not in matrix:
			matrix_keys.append(key)
			matrix[key] = ['0' for x in range(tot_files)]
		
		'''Change the value in the matrix to what it should be'''
		matrix[key][index] = value
			
	inp_file.close()

'''Read the directory, and output the contents from read_file'''
def read_dir_to_out(d_path):
	'''Sort the contents of the dir'''
	cwd = sorted(os.listdir(d_path))
	
	'''Read each file in dir using read_file'''
	for i, file_name in enumerate(cwd):
		read_file(file_name, d_path, len(cwd), i)
	
	'''Output the noted contents to separate files'''
	global matrix_keys
	matrix_keys = sorted(matrix_keys)
	output_sort("sorted_documents.txt", cwd)	
	output_sort("sorted_terms.txt", matrix_keys)
	output_matrix("td_matrix.txt", matrix_keys, len(cwd))


'''Outputs the non-matrix files'''
def output_sort(o_path, list_elements):
	global output_path
	out_file = open(os.path.join(output_path, o_path), "w")
	'''For each element, write it to the file'''
	for word in list_elements:
		out_file.write("%s\n" % word)
	out_file.close()
	

'''Outputs the matrix file'''
def output_matrix(o_path, keys, files):
	global output_path
	out_file = open(os.path.join(output_path, o_path), "w")
	'''write the dimensions to file'''
	out_file.write(str(len(keys)) + " " + str(files) + "\n")
	'''For each element, write it to the file'''
	for key in keys:
		out_file.write(' '.join(matrix[key]) + '\n')
	out_file.close()

def main():
	read_dir_to_out(input_path)
	
if __name__ == "__main__":
	main()