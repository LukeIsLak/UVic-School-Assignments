# include <stdio.h>
# include <stdlib.h>
# include <string.h>
# include <ctype.h>


int main(int argc, char* argv[]) {

  /*Grab the standard input and create a buffer*/
	FILE *data = stdin;
	int buf;

	/*While we do no have a file end of file, print each character in the lower form*/
	while (1) {
		buf = fgetc(data);
		if ( feof(data)) {
			break;
		}
		if ( ispunct(buf) ) {
		  continue;
		}
		printf("%c", tolower(buf));
	   
	}

	/*Return*/
	return 0;
}          
