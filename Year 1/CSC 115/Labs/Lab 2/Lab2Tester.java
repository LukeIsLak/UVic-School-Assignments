/*
 * Lab2Tester.java
 *
 * A tester for the methods in Lab2.java
 *
 */
import java.util.Arrays;

public class Lab2Tester {
    
	private static int testPassCount = 0;
	private static int testCount = 0;

	// for approximate comparison of floating point numbers
	private static final double THRESHOLD = 0.01;

	public static void main(String[] args) {
		
		testGetHigherGradeStudent();
		testIsGradeAbove();
		testGetClasslist();
		testCountAbove();
		testGetClassAverage();
		testRegisterStudent();

		System.out.println("Passed " + testPassCount + " / " + testCount + " tests");
	}
	
	public static void testGetHigherGradeStudent() {
		// TODO: once you have implemented getHigherGradeStudent in Lab2.java
		//  uncomment the following tests - make sure you understand what they are testing


		Student s0  = new Student("abc", 50);
		Student s1a = new Student("def", 56);
		Student s1b = new Student("xyz", 56);
		Student s2  = new Student("xyz", 99);

		Student result;

		result = Lab2.getHigherGradeStudent(s0,s1a);
		System.out.println("should be  " + s1a + " is " + result);
		displayResults(result.equals(s1a), "testGetHigherGradeStudent");

		result = Lab2.getHigherGradeStudent(s1a,s0);
		System.out.println("should be  " + s1a + " is " + result);
		displayResults(result.equals(s1a), "testGetHigherGradeStudent");

		result = Lab2.getHigherGradeStudent(s1b,s1a);
		System.out.println("should be  " + s1b + " is " + result);
		displayResults(result.equals(s1b) && result == s1b, "testGetHigherGradeStudent");

		result = Lab2.getHigherGradeStudent(s1b,s2);
		System.out.println("should be  " + s2 + " is " + result);
		displayResults(result.equals(s2), "testGetHigherGradeStudent");

	}

	public static void testIsGradeAbove() {
		Student s0  = new Student("abc", 50);
		Student s1a = new Student("def", 56);
		Student s1b = new Student("xyz", 56);
		Student s2  = new Student("xyz", 99);

		boolean result = Lab2.isGradeAbove(s0, 0);
		System.out.println("should be  " + true + " is " + result + ": Input is " + s0.getGrade() + " Threshold is 0");
		displayResults((result == true), "testIsGradeAbove");

		
		result = Lab2.isGradeAbove(s1a, 60);
		System.out.println("should be  " + false + " is " + result + ": Input is " + s0.getGrade() + " Threshold is 60");
		displayResults((result == false), "testIsGradeAbove");

		
		result = Lab2.isGradeAbove(s1b, 56);
		System.out.println("should be  " + false + " is " + result + ": Input is " + s0.getGrade() + " Threshold is 56");
		displayResults((result == false), "testIsGradeAbove");

		
		result = Lab2.isGradeAbove(s2, 70);
		System.out.println("should be  " + true + " is " + result + ": Input is " + s0.getGrade() + " Threshold is 70");
		displayResults((result == true), "testIsGradeAbove");


	}

	public static void testGetClasslist() {
		// TODO: write tests for Lab2.getClasslist

		// NOTE: the Arrays library has been imported for you.
		//       you can use the Arrays.equals method to compare
		//       2 arrays of String objects as String has a equals method
		// The API for Arrays.equals:
		//       equals(Object[] a, Object[] a2)
		//       Returns true if the two specified arrays are equal to one another.

		// TODO: once you have implemented getClasslist in Lab2.java
		// uncomment the following test. We have gotten you started with 
		// some initial test data and one test, but you should consider 
		// other cases (empty array, longer array)

		Student s0  = new Student("abc", 50);
		Student s1a = new Student("def", 56);
		Student s1b = new Student("xyz", 56);
		Student s2  = new Student("xyz", 99);
		Student[] students0 = {};
		Student[] students1 = {s0};
		Student[] students2 = {s0, s1a};
		Student[] students3 = {s0, s1a, s1b, s2};
		String[] expected0 = {};
		String[] expected1 = {"abc"};
		String[] expected2 = {"abc", "def"};
		String[] expected3 = {"abc", "def", "xyz", "xyz"};

		String[] result;

		result = Lab2.getClasslist(students0);
		displayResults(Arrays.equals(result, expected0), "testGetClasslist - 0 elements");
		result = Lab2.getClasslist(students1);
		displayResults(Arrays.equals(result, expected1), "testGetClasslist - 1 elements");
		result = Lab2.getClasslist(students2);
		displayResults(Arrays.equals(result, expected2), "testGetClasslist - 2 elements");
		result = Lab2.getClasslist(students3);
		displayResults(Arrays.equals(result, expected3), "testGetClasslist - 4 elements");
	}


	public static void testCountAbove() {
		Student s0  = new Student("abc", 50);
		Student s1a = new Student("def", 56);
		Student s1b = new Student("xyz", 56);
		Student s2  = new Student("xyz", 99);
		Student[] students0 = {};
		Student[] students1 = {s0};
		Student[] students2 = {s0, s1a};
		Student[] students3 = {s0, s1a, s1b, s2};
		int threshold1 = 50;
		int threshold2 = 51;
		int threshold3 = 55;
		int expected0 = 0;
		int expected1 = 0;
		int expected2 = 1;
		int expected3 = 3;

		int result = Lab2.countAbove(students0, threshold1);
		System.out.println("should be  " + 0 + " is " + result);
		displayResults((result == expected0), "testingCountAbove");

		result = Lab2.countAbove(students1, threshold1);
		System.out.println("should be  " + expected1 + " is " + result);
		displayResults((result == expected1), "testingCountAbove");

		result = Lab2.countAbove(students2, threshold2);
		System.out.println("should be  " + expected2 + " is " + result);
		displayResults((result == expected2), "testingCountAbove");

		result = Lab2.countAbove(students3, threshold3);
		System.out.println("should be  " + expected3 + " is " + result);
		displayResults((result == expected3), "testingCountAbove");


	}


	public static void testGetClassAverage() {
		Student s0 = new Student("abc", 50);
		Student s1 = new Student(); // considered invalid for average average calculation
		Student s2 = new Student("xyz", 99);
		Student s3 = new Student("def", 88);

		Student[] students0 = {};
		Student[] students1 = {s0};
		Student[] students2 = {s0, s1, s2};
		Student[] students3 = {s0, s2, s3};

		double result = 0.0;
		double expected = 0.0;
		
		// Some of you noticed in Lab1 that floating point arithmetic sometimes
		// produces results with many decimals places. We use the threshold
		// (defined as a global variable at the top) to specify the margin 
		// of error we are okay with, and just make sure the expected and 
		// returned results are within the threshold of acceptable error.

		result = Lab2.getClassAverage(students0);
		expected = 0.0;
		displayResults(Math.abs(result-expected)<THRESHOLD, "testGetAverage - empty");

		result = Lab2.getClassAverage(students1);
		expected = 50.0;
		displayResults(Math.abs(result-expected)<THRESHOLD, "testGetAverage - 1 student");

		result = Lab2.getClassAverage(students2);
		expected = (50 + 99) / 2.0; // because s1 does not have a "real" grade
		displayResults(Math.abs(result-expected)<THRESHOLD, "testGetAverage - 3 students, count 2");

		result = Lab2.getClassAverage(students3);
		expected = (50 + 99 + 88) / 3.0;
		displayResults(Math.abs(result-expected)<THRESHOLD, "testGetAverage - 3 students, count 3");

	}

	public static void testRegisterStudent() {
		Student s0  = new Student("abc", 50);
		Student s1a = new Student("def", 56);
		Student s1b = new Student("xyz", 56);
		Student s2  = new Student("xyz", 99);
		Student[] students0 = {};
		Student[] students1 = {s0};
		Student[] students2 = {s0, s1a};
		Student[] students3 = {s0, s1a, s1b, s2};

		Student s3  = new Student("test", 80);

		Student[] expected0 = {s3};
		Student[] expected1 = {s0, s3};
		Student[] expected2 = {s0, s1a, s3};
		Student[] expected3 = {s0, s1a, s1b, s2, s3};

		Student[] result = Lab2.registerStudents(students0, s3);
		System.out.println(result.toString());
		displayResults((Arrays.equals(result, expected0)), "testingregisterStudents");
		result = Lab2.registerStudents(students1, s3);
		System.out.println(result.toString());
		displayResults((Arrays.equals(result, expected1)), "testingregisterStudents");
		result = Lab2.registerStudents(students2, s3);
		System.out.println(result.toString());
		displayResults((Arrays.equals(result, expected2)), "testingregisterStudents");
		result = Lab2.registerStudents(students3, s3);
		System.out.println(result.toString());
		displayResults((Arrays.equals(result, expected3)), "testingregisterStudents");

	}
	
	public static void displayResults (boolean passed, String testName) {
		/* There is some magic going on here getting the line number
		 * Borrowed from:
		 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
		 */
		
		testCount++;
		if (passed) {
			System.out.println ("Passed test: " + testName);
			testPassCount++;
		} else {
			System.out.println ("Failed test: " + testName + " at line "
								+ Thread.currentThread().getStackTrace()[2].getLineNumber());
		}

	}

}
