public class A4Stack<T> implements Stack<T> {
	
	private A4Node<T> head;
	// Do NOT add any other fields to this class.
	// You should be able to implement the Stack 
	// interface with just a head field.

	public A4Stack() {
		this.head = null;
	}
	
	public void push(T value) {
		A4Node<T> newHead = new A4Node<T>(value);
		newHead.setNext(head);
		this.head = newHead;
	}
	
	public T pop() {
		T val = this.head.getData();
		head = head.getNext();
		return val; // so it compiles
	}
	
	public void popAll() {
		head = null;
	}
	
	public boolean isEmpty() {
		return (head == null); // so it compiles
	}
	
	public T peek() {
		// TODO: implement this
		return head.getData(); // so it compiles
	}
	
	// Implemented for you for debugging purposes
	public String toString() {
		String result = "{";
		String separator = "";
		
		A4Node<T> cur = head;
		while (cur != null) {
			result += separator + cur.getData().toString();
			separator = ", ";
			cur = cur.next;
		}
	
		result += "}";
		return result;
	}
}