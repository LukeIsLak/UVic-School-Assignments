import javax.print.attribute.standard.RequestingUserName;

public class QueueRefBased implements Queue {

	private QueueNode front;
	private QueueNode back;
	// no numElements for this implementation

	public QueueRefBased() {
		front = null;
		back = null;
	}

	public int size() {
		int count = 0;
		if (front != null) {
			count ++;
			QueueNode curNode = front;
			while (curNode.getNext() != null) {
				curNode = curNode.getNext();
				count ++;
			}
		}		
		return count;			
	}

	public boolean isEmpty() {
		return (size() == 0);
	}

	public void enqueue (int value) {
		QueueNode q = new QueueNode(value);
		if (front == null && back == null) {
			front = q;
			back = q;
		}
		else {
			back.setNext(q);
			back = q;
		}
	}

	public int dequeue() {
		if (front != null && back != null) {
			if (front == back) {
				int tmpVal = front.getValue();
				front = null;
				back = null;
				return tmpVal;
			}
			else {
				int tmpVal = front.getValue();
				front = front.getNext();
				return tmpVal;
			}
		}
		else {
			return 0;
		}
	}

	public int peek()  {
		if (front != null) {
			return front.getValue();
		}
		else {
			return 0;
		}
	}

	public void makeEmpty() {
		front = null;
		back = null;
	}
}
