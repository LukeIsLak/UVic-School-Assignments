# include <stdio.h>
# include <stdlib.h>
# include <string.h>
# include <ctype.h>


/*Pre-Set macros*/
#define MAX_LINE_NUM 500
#define MAX_LINE_SIZE 2500
#define MAX_WORD_SIZE 100
#define MAX_WORD_PER_LINE 500
#define STOP_WORDS "the", "a", "of", "for" ,"to" ,"and", "but", "yet", "an"


/*Prototype Function for Compiling*/
void tokenize_string(char *string, char *sep);
int compare_token(char *token);
void output_string(char string_array[MAX_WORD_PER_LINE][MAX_WORD_SIZE], int words);


/*Main function that holds the data compiler and data output*/
int main(int argc, char* argv[]) {
	FILE *data;
	char buf[MAX_LINE_SIZE];

	/*If we have a input parameter, use that*/
	if (argc > 1) {
		data = fopen(argv[1], "r");
	}

	/*If we dont, use the standard input or pipelined input*/
	else {
        	data = stdin;
	}


	/*If data did not work properly*/
	if (data == NULL) {
		perror("Error: Error Opening File");
		printf("%s", argv[1]);
		fclose(data);
		exit(1);
	}
	
	/*For each line, update it based on our requirements*/
	int line_num = 0;
	while (fgets(buf, MAX_LINE_SIZE, data) != NULL ) {
		tokenize_string(buf, " ");
		line_num ++;
	}

	fclose(data);
	return 0;
}

/*Function to tokenize each string passed with specific seperator*/
void tokenize_string (char *string, char *sep) {
  	char* t;
	char string_array[MAX_WORD_PER_LINE][MAX_WORD_SIZE];
	int i = 0;

  	/*Tokenize the string based on seperator*/
  	for (t = strtok(string, sep); t != NULL; t = strtok(NULL, sep)) {		

		/*Check if temporary token has stop word, then add original token accordingly*/
		if (compare_token(t) == 0) {						
			strcpy(string_array[i], t);
			i ++;
    		}

  	}

	/*Output the uodated data*/
	output_string (string_array, i);	 
}

/*Function to compare each token to all the words in our STOP_WORDS macro*/
int compare_token (char *token) {

        /*Turn our macro into a string of strings and find the size*/
        char* all_words[] = {STOP_WORDS};
        long unsigned int array_size = sizeof(all_words)/sizeof(all_words[0]);

        /*Compare each word in the array to the the token*/
        for (long unsigned int i = 0; i < array_size; i++) {

		/*If our word is the same as the token*/
	  if (strcmp(all_words[i], token) == 0) {
                        return 1;
          }
        }

        return 0;
 
}


/*Function that outpus the array of updated data*/
void output_string(char string_array[MAX_WORD_PER_LINE][MAX_WORD_SIZE], int words) {
	for(int i = 0; i < words; i++) {
		printf("%s", string_array[i]);
		if (i < words - 1) {
 			printf(" ");
		}	
	};
}
