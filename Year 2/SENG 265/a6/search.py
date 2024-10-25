#!/usr/bin/env python3

'''Important Libraries'''
import os
import sys

'''Global Variables'''
index_path = sys.argv[1]
stored_data = {}

'''Given an directory path, put all of the contents of each file into a dictionary'''
def read_index (d_path) :
	cwd = os.listdir(d_path)
	
	for filename in cwd:
		newlist = []
		inp_file = open(d_path + '/' + filename, "r")
		if filename == "td_matrix.txt":
			dim = inp_file.readline().strip().split()
			newlist.append([int(dim[0]), int(dim[1])])
		for line in inp_file:
			to_add = [i for i in line.strip().split()];
			if len(to_add) > 1:
				newlist.append(to_add)
			else:
				newlist.append(to_add[0])
	
		stored_data[filename] = newlist
		inp_file.close()

'''Creates a query vector using input from standard input'''
def create_query_vector():
	global stored_data
	all_inputs = []
	query_vector = [0 for x in range(stored_data["td_matrix.txt"][0][0])]
	for i, line in enumerate(sys.stdin):
		all_inputs.append(line.strip().split()[0])
		if all_inputs[i] in stored_data["sorted_terms.txt"]:
			for input_ele in stored_data["td_matrix.txt"][stored_data["sorted_terms.txt"].index(all_inputs[i])+1]:
				if int(input_ele) > 0:
					query_vector[int(stored_data["sorted_terms.txt"].index(all_inputs[i]))] = 1
					break
			
	
	return query_vector

''''''
def cosine_similarity(query_vector, vector):
	dot = sum([query_vector[i] * vector[i] for i in range(len(query_vector))])
	length = (sum([(query_vector[i]**2) for i in range(len(query_vector))])*sum([(vector[i]**2) for i in range(len(vector))]))**0.5
	return "%.4f" % (dot / length)
	

def output():
	query_vector = create_query_vector()
	sim_doc = []
	for i, document in enumerate(stored_data["sorted_documents.txt"]):
		sim_doc.append([cosine_similarity(query_vector, [int(stored_data["td_matrix.txt"][x+1][i]) for x in range(stored_data["td_matrix.txt"][0][0])]), document])
	
	
	sim_doc.sort(reverse=True)
	for ele in sim_doc:
		print(ele[0], ele[1])
		
		
read_index(index_path)
output()