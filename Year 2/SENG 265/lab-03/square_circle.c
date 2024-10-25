#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(int argc, char *argv[]) {

	int count = 0;
	srand(time(NULL));
	for (int i = 0; i < atoi(argv[1]); i++) {
		double random_num_x = ((double)rand()/RAND_MAX) * 2.0 - 1;
		double random_num_y = ((double)rand()/RAND_MAX) * 2.0 - 1;

		double x_calc = random_num_x * random_num_x;
		double y_calc = random_num_y * random_num_y;

		double calc = sqrt(x_calc + y_calc);


		if (calc <= 1) {
			count = count+1;	
		}
	}
	
	double result = ((double)count / atoi(argv[1]))*4;
	printf ("Result is: %lf\n", result);
	
		
}
