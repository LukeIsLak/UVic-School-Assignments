public class StudentLinkedList implements StudentList {

	StudentNode head;
	int count;

	public StudentLinkedList() {
		head = null;
		count = 0;
	}

	public void add(Student s) {
		StudentNode node = new StudentNode(s);
		if (count == 0) {
			head = node;
		}
		else {
			StudentNode cur = head;
			while (cur.next != null) {
				cur = cur.next;
			}
			cur.next = node;
		}
		count++;
	}

	public int size() {
		// TODO: complete
		return count;
	}

	public void removeFront() {
		head = (head != null)?head.next:null;
		if (count > 0) {
			count--;
		}
	}

	public boolean contains(Student s) {
		if (count > 0) {
		StudentNode cur = head;
		while (cur != null) {
			if (cur.getData().equals(s)) {
				return true;
			}
			cur = cur.next;
			}
		}
		return false;
	}
	
	/*
	 * Purpose: returns a String reprensentation of elements
	 *      in this list separated by newlines
	 * Parameters: none
	 * Returns: String - the representation
	 */
	public String toString() {
		// DO NOT CHANGE THIS METHOD - it is correct
		// Changing it will result in your code getting
		// a score of 0 from the autograder for this lab
		
		String s = "";
		StudentNode tmp = head;

		while(tmp != null) {
			s  += tmp.getData() + "\n";
			tmp = tmp.next;
		}

		return s;
	}
}
