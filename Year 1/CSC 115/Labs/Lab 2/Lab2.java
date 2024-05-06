import java.lang.reflect.Array;

/*
 * Lab2.java
 *
 * A class of static methods that operate on Students
 *
 */
public class Lab2 {
    
	/*
	 * Purpose: determines which student has the higher grade
	 * Parameters: Student - s1, Student - s2
	 * Returns: Student - the Student with the higher grade,
	 *                    s1 if they have the same grade
	 * Precondition: s1 and s2 are not null
	 */
	public static Student getHigherGradeStudent(Student s1, Student s2) {
		return (s1.getGrade() >= s2.getGrade())? s1 : s2;
	}


	/*
	 * Purpose: determines whether the grade of Student s
	 *          is above the threshold
	 * Parameters: Student - s, int - threshold
	 * Returns: boolean - true if grade is above threshold, false otherwise
	 * Preconditions: s is not null
	 */
	// TODO: implement isGradeAbove
	public static boolean isGradeAbove(Student s, int threshold) {
		return s.getGrade() > threshold;
	}


	/*
	 * Purpose: creates an array sIDs of all Students in students
	 * Parameters: Student[] - students
	 * Returns: String[] - array of sIDs
	 * Preconditions: students is not null and contains no null elements
	 */
	// TODO: implement getClasslist
	public static String[] getClasslist(Student[] classn) {
		String[] classlist = new String[classn.length];
		for (int i = 0; i < classn.length; i++) {
			classlist[i] = classn[i].getSID();
		}
		return classlist;
		}



	/*
	 * Purpose: counts the number of Students in students
	 *          with grade above threshold
	 * Parameters: Student[] - students, int - threshold
	 * Returns: int - the number of students with a grade above threshold
	 * Preconditions: students is not null and contains no null elements
	 */
	// TODO: implement countAbove
	// HINT: you should be using the isGradeAbove method!
	public static int countAbove(Student[] classlist, int threshold) {
		int numAbove = 0;
		for (int i = 0; i < classlist.length; i++)  {
			if (isGradeAbove(classlist[i], threshold)) {
				numAbove ++;
			}
		}
		return numAbove;
		}



	/*
	 * Purpose: calculates the average grade of Students in students,
	 *          does NOT include students with -1 grade in calculation
	 *          average is 0.0 if students is empty or all students have -1 grade
	 * Parameters: Student[] - students
	 * Returns: double - the average grade
	 * Preconditions: students is not null and contains no null elements
	 */
	// TODO: implement getClassAverage
	// HINT: you can use the isGradeAbove method again

	public static double getClassAverage(Student[] students) {
		double average = 0.0;
		int count = 0;
		for (int i = 0; i < students.length; i++) {
			if (isGradeAbove(students[i], -1)) {
				average += students[i].getGrade();
				count ++;
			}
		}

		return average/((count>0)? count: 1);
	}


	/*
	 * Purpose: creates a new array 1 longer than students
	 *          and adds all students and s to the new array
	 * Parameters: Student[] - students, Student s
	 * Returns: Student[] - the new array
	 * Preconditions: students is not null and contains no null elements
	 *                Student s is not already contained within students
	 */
	// TODO: implement registerStudent

	public static Student[] registerStudents(Student[] students, Student s) {
		Student[] newClass = new Student[students.length+1];
		for (int i = 0; i < students.length; i++) {
			newClass[i] = students[i];
		}
		newClass[students.length] = s;
		return newClass;
	}

}
