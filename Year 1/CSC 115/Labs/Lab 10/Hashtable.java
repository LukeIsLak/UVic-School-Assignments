public class Hashtable{
    
private static final int TABLE_SZ = 7; // a prime number

	Student[] table;
	int count;  // number of Students in the table

	public Hashtable() {
		table = new Student[TABLE_SZ];
		count = 0;
	}



	/* MethodName: insertCollisions
	 * Purpose: insert (or update entry) s in this Hashtable with no collision handling strategy
	 * Parameters: Student - s
	 * Throws:  TableFullException - if inserting a new key into a full table
	 *          CollisionException - if inserting a new key into table at index that is full
	 * Returns: nothing
	 */
	// TODO: complete this function

	public void insertCollisions(Student s) throws TableFullException, CollisionException{
		int pos = getPos(s);
		if (table[pos] == null) {
			table[pos] = s;
			count ++;
		}

		else if (table[pos].getSID().equals(s.getSID())) {
			table[pos].setGrade(s.getGrade());
		}
		else {
			if (count == table.length) {
				throw new TableFullException();
			}
			throw new CollisionException();
		}
	}


	/* MethodName: getCollisions
	 * Purpose: find Student with sid in this Hashtable with no collision handling and returns their grade
	 * Parameters: String - sid
	 * Throws:  KeyNotFoundException  - if Student with sid is not found in table
	 * Returns: int - the grade of Student with sid
	 */
	public int getCollisions(String sid) throws KeyNotFoundException{
		int pos = sid.hashCode()%table.length;
		if (table[pos] == null) {
			throw new KeyNotFoundException();
		}
		else if (!table[pos].getSID().equals(sid)) {
			throw new KeyNotFoundException();
		}
		else {
			return table[pos].getGrade();
		}
	}


	/* MethodName: insertLinearProbing
	 * Purpose: insert (or update entry) s in this Hashtable with Linear Probing to handle collisions
	 * Parameters: Student - s
	 * Throws: TableFullException  - if inserting a new key into a full table
	 * Returns: nothing
	 */
	// TODO: complete this function
	public void insertLinearProbing(Student s) throws TableFullException{
		int pos = getPos(s);
		for (int i = 0; i < table.length; i++) {
			int newPos = (pos + i)%table.length;
			if (table[newPos] == null) {
				table[newPos] = s;
				count ++;
				return;
			}
			else if (table[pos].getSID().equals(s.getSID())) {
				int grade = s.getGrade();
				table[pos].setGrade(grade);
				return;
			}
		}
		throw new TableFullException();
	}



	/* getLinearProbing
	 * Purpose: find Student with sid in this Hashtable that uses Linear Probing and returns their grade
	 * Parameters: String - sid
	 * Throws:  KeyNotFoundException  - if Student with sid is not found in table
	 * Returns: int - the grade of Student with sid
	 */
	public int getLinearProbing(String sid) throws KeyNotFoundException{
		int pos = sid.hashCode()%table.length;
		for (int i = 0; i < table.length; i++) {
			int newPos = (pos + i)%table.length;
			if (table[newPos] == null) {
				throw new KeyNotFoundException();
			}
			if (table[newPos].getSID().equals(sid)) {
				return table[newPos].getGrade();
			}
		}
		throw new KeyNotFoundException();
	}

	/*
	 * Purpose: returns the number of elements in this Hashtable
	 * Parameters: none
	 * Returns: int - the number of elements
	 */

	public int getPos (Student s) {
		return s.hashCode()%TABLE_SZ;
	}
	public int size() {
		return count;
	}

	/*
	 * Purpose: returns a String reprensentation of elements
	 *      in this Hashtable separated by newlines
	 * Parameters: none
	 * Returns: String - the representation
	 */
	public String toString() {
		String s = "";

		for(int i=0; i<TABLE_SZ; i++) {
			if (table[i] != null) {
				s  += table[i] + "\n";
			}
		}
		
		return s;
	}
    

}
