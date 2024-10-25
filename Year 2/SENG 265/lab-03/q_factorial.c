#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{

    /*
     * variable to store the final answer
     */
    long int factorial = 1;

    long int num = atoi(argv[1]);

    for(int i = num; i > 0; i--) {
      factorial = factorial * i;
}
    printf("%ld\n", factorial);
}
