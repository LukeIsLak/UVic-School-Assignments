import java.util.Random;

public class Assignment3 {
    public static void main (String[] args) {
        int elementSize = 1000;
        Random rand = new Random();

        int[] arrAsc = new int[elementSize];
        int[] arrDes = new int[elementSize];
        int[] arrRan = new int[elementSize];
        int[] arrAsc2 = new int[elementSize];
        int[] arrDes2 = new int[elementSize];
        int[] arrRan2 = new int[elementSize];

        for (int i = 0; i < elementSize; i++) {
            arrAsc[i] = i + 1;
            arrAsc2[i] = i + 1;
            arrDes[i] = elementSize - i;
            arrDes2[i] = elementSize - i;
            arrRan[i] = rand.nextInt(elementSize) + 1;
            arrRan2[i] = arrRan[i];
        }

        System.out.println("-- Testing Blind.java --------");
        System.out.printf("Array1 - Ascending || ");
        long startTime1 = System.nanoTime();
        int Blind_Inversions1 = Blind.CountInversions(arrAsc);
        System.out.printf("Inversions: %d ", Blind_Inversions1);
        long endTime1 = System.nanoTime();
        double timem1 = (endTime1-startTime1)/1000.0;
        System.out.printf("Total Time: %.4f", timem1);
        System.out.printf(" Microseconds\n");

        System.out.printf("Array2 - Descending || ");
        long startTime2 = System.nanoTime();
        int Blind_Inversions2 = Blind.CountInversions(arrDes);
        System.out.printf("Inversions: %d ", Blind_Inversions2);
        long endTime2 = System.nanoTime();
        double timem2 = (endTime2-startTime2)/1000.0;
        System.out.printf("Total Time: %.4f", timem2);
        System.out.printf(" Microseconds\n");

        System.out.printf("Array3 - Random || ");
        long startTime3 = System.nanoTime();
        int Blind_Inversions3 = Blind.CountInversions(arrRan);
        System.out.printf("Inversions: %d ", Blind_Inversions3);
        long endTime3 = System.nanoTime();
        double timem3 = (endTime3-startTime3)/1000.0;
        System.out.printf("Total Time: %.4f", timem3);
        System.out.printf(" Microseconds\n");






        System.out.println("-- Testing Insertion.java ----");
        System.out.printf("Array1 - Ascending || ");
        long startTime4 = System.nanoTime();
        int Insertion_Inversions1 = Insertion.CountInversions(arrAsc);
        System.out.printf("Inversions: %d ", Insertion_Inversions1);
        long endTime4 = System.nanoTime();
        double timem4 = (endTime4-startTime4)/1000.0;
        System.out.printf("Total Time: %.4f", timem4);
        System.out.printf(" Microseconds\n");

        System.out.printf("Array2 - Descending || ");
        long startTime5 = System.nanoTime();
        int Insertion_Inversions2 = Insertion.CountInversions(arrDes);
        System.out.printf("Inversions: %d ", Insertion_Inversions2);
        long endTime5 = System.nanoTime();
        double timem5 = (endTime5-startTime5)/1000.0;
        System.out.printf("Total Time: %.4f", timem5);
        System.out.printf(" Microseconds\n");

        System.out.printf("Array3 - Random || ");
        long startTime6 = System.nanoTime();
        int Insertion_Inversions3 = Insertion.CountInversions(arrRan);
        System.out.printf("Inversions: %d ", Insertion_Inversions3);
        long endTime6 = System.nanoTime();
        double timem6 = (endTime6-startTime6)/1000.0;
        System.out.printf("Total Time: %.4f", timem6);
        System.out.printf(" Microseconds\n");





        System.out.println("-- Testing Merge.java --------");
        System.out.printf("Array1 - Ascending || ");
        long startTime7 = System.nanoTime();
        int Merge_Inversions1 = Merge.CountInversions(arrAsc2);
        System.out.printf("Inversions: %d ", Merge_Inversions1);
        long endTime7 = System.nanoTime();
        double timem7 = (endTime7-startTime7)/1000.0;
        System.out.printf("Total Time: %.4f", timem7);
        System.out.printf(" Microseconds\n");

        System.out.printf("Array2 - Descending || ");
        long startTime8 = System.nanoTime();
        int Merge_Inversions2 = Merge.CountInversions(arrDes2);
        System.out.printf("Inversions: %d ", Merge_Inversions2);
        long endTime8 = System.nanoTime();
        double timem8 = (endTime8-startTime8)/1000.0;
        System.out.printf("Total Time: %.4f", timem8);
        System.out.printf(" Microseconds\n");

        System.out.printf("Array3 - Random || ");
        long startTime9 = System.nanoTime();
        int Merge_Inversions3 = Merge.CountInversions(arrRan2);
        System.out.printf("Inversions: %d ", Merge_Inversions3);
        long endTime9 = System.nanoTime();
        double timem9 = (endTime9-startTime9)/1000.0;
        System.out.printf("Total Time: %.4f", timem9);
        System.out.printf(" Microseconds\n");



        double[] asc_times = {
            timem1, //from Blind.java
            timem4, //from Insertion.java
            timem7  //from Merge.java
        };
        double[] des_times = {
            timem2, //from Blind.java
            timem5, //from Insertion.java
            timem8  //from Merge.java
        };
        double[] ran_times = {
            timem3, //from Blind.java
            timem6, //from Insertion.java
            timem9  //from Merge.java
        };
    }
}
