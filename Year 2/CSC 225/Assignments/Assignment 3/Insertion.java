import java.util.*;
import java.io.*;

public class Insertion{
	public static int CountInversions(int[] arr){
        int count = 0;
		int val;

		for (int i = 1; i < arr.length; i++) {
			val = arr[i];
			int j = i - 1;
			while (j >= 0 && arr[j] > val) {
				arr[j+1] = arr[j];
				j--;
				count++;
			}
			arr[j+1] = val;
		}

		return count;
	}

	public static void main(String []args){
			Scanner s;
			if (args.length > 0){
				try{
					s = new Scanner(new File(args[0]));
				} catch(java.io.FileNotFoundException e){
					System.out.printf("Unable to open %s\n",args[0]);
					return;
				}
				System.out.printf("Reading input values from %s.\n",args[0]);
			}else{
				s = new Scanner(System.in);
				System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
			}
			Vector<Integer> inputVector = new Vector<Integer>();
			int v;
			while(s.hasNextInt() && (v = s.nextInt()) >= 0)
				inputVector.add(v);

			int[] array = new int[inputVector.size()];
	
			for (int i = 0; i < array.length; i++)
				array[i] = inputVector.get(i);

			System.out.printf("Read %d values.\n",array.length);
			


			
			int numberOfInversions = CountInversions(array);
			System.out.println(numberOfInversions);
			
			

			//System.out.printf("Array %s a pair of values which add to 225.\n",pairExists? "contains":"does not contain");
			
		}
}
