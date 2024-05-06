// Name:Luke Kuligowicz
// Student number: v01019209

public class A3LinkedList implements A3List {
	private A3Node head;
	private A3Node tail;
	private int length;
	
	public A3LinkedList() {
		head = null;
		tail = null;
		length = 0;
	}
	
	public void addFront(String s) {
		if (head != null) {
			A3Node newHead= new A3Node(s);
			newHead.setNext(head);
			head.setPrev(newHead);
			head = newHead;
			length ++;
		}
		else {
			A3Node newHead= new A3Node(s);
			head = newHead;
			tail = newHead;
			length ++;
		}
	}

	public void addBack(String s) {
		if (tail != null) {
			A3Node newHead= new A3Node(s);
			tail.setNext(newHead);
			newHead.setPrev(tail);
			tail = newHead;
			length ++;
		}
		else {
			A3Node newHead= new A3Node(s);
			head = newHead;
			tail = newHead;
			length ++;
		}
	}
	
	public int size() {
		return length;
	}
	
	public boolean isEmpty() {
		return length==0;
	}
	
	public void removeFront() {
		if (head != null && head.getNext() != null) {
			A3Node temp = head;
			temp.getNext().setPrev(null);
			head = temp.getNext();

		}
		else if (head != null) {
			head = null;
		}
	}
	
	public void removeBack() {
		if (tail == null) {
			return;
		}
		if (tail == head) {
			removeFront();
		}
		else if(tail.getPrev() == null) {
			tail = null;
			head = null;
			length -= (length > 0)? 1 : 0;
		}
		else {
			tail = tail.getPrev();
			tail.setNext(null);
			length -= (length > 0)? 1 : 0;
		}
	}
	
	public void removeAt(int pos) {
		int index = 1;
		if (head != null) {
			A3Node currNode = head;
				if (pos == 0) {
					removeFront();
					length--;
				}
				if (pos == (length-1)) {
					removeBack();
					length--;
				}
			if (currNode.getNext() != null) {
				currNode = currNode.getNext();
			while (index < (length-1) ) {
				if (index == pos) {
					currNode.getPrev().setNext(currNode.getNext());
					currNode.getNext().setPrev(currNode.getPrev());
					length --;
				}
				currNode = currNode.getNext();
				index ++;
				}
			}
		}
	}

	public A3LinkedList mergeSorted(A3LinkedList other) {
		A3LinkedList merged = new A3LinkedList();
		A3Node head1 = head;
		A3Node head2 = other.head;

		while (head1 != null || head2 != null) {
			if (head1 != null && head2 != null) {
				if (head1.getData().compareTo(head2.getData()) < 0) {
					merged.addBack(head1.getData());
					head1 = head1.getNext();
				}
				else if (head1.getData().compareTo(head2.getData()) > 0) {
					merged.addBack(head2.getData());
					head2 = head2.getNext();
				}
				else {
					if (head1.getData().equals(head2)) {
						merged.addBack(head1.getData());
						head1 = head1.getNext();

						merged.addBack(head2.getData());
						head2 = head2.getNext();
					}
				}
			}
			if (head1 == null && head2 != null) {
				merged.addBack(head2.getData());
				head2 = head2.getNext();
			}
			else if (head2 == null && head1 != null) {
				merged.addBack(head1.getData());
				head1 = head1.getNext();
			}
		}
		
		return merged;
	}
	
	/*
	 * Purpose: return a string representation of the list 
	 *          when traversed from front to back
	 * Parameters: none
	 * Returns: nothing
	 *
	 * USED TO HELP YOU WITH DEBUGGING
	 * DO NOT CHANGE THIS METHOD
	 */
	public String frontToBack() {
		String result = "{";
		A3Node cur = head;
		while (cur != null) {
			result += cur.getData();
			cur = cur.next;
		}
		result += "}";
		return result;
	}
	
	/*
	 * Purpose: return a string representation of the list 
	 *          when traversed from back to front
	 * Parameters: none
	 * Returns: nothing
	 *
	 * USED TO HELP YOU WITH DEBUGGING
	 * DO NOT CHANGE THIS METHOD
	 */
	public String backToFront() {
		String result = "{";
		A3Node cur = tail;
		while (cur != null) {
			result += cur.getData();
			cur = cur.prev;
		}
		result += "}";
		return result;
	}
}
	