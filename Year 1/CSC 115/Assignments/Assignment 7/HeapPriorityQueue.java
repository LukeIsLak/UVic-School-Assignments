import javax.xml.stream.events.StartDocument;

/*
* HeapPriorityQueue.java
*
* An implementation of a minimum PriorityQueue using a heap.
* based on the implementation in "Data Structures and Algorithms
* in Java", by Goodrich and Tamassia
*
* This implementation will throw a Runtime, HeapEmptyException
*	if the heap is empty and removeMin is called.
*
* This implementation will throw a Runtime, HeapFullException
*	if the heap is full and insert is called.
*
*/
public class HeapPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {

	protected final static int DEFAULT_SIZE = 10000;
	
	protected T[] storage;
	protected int currentSize;
	
	// Feel free to change rootIndex to 0 if you want to 
	// use 0-based indexing (either option is fine)
	private static final int rootIndex = 0;

	/*
	 * Constructor that initializes the array to hold DEFAULT_SIZE elements
	 */
	@SuppressWarnings({"unchecked"})
	public HeapPriorityQueue(Class<T> dataType) {
		// Creating generics arrays in Java is not very clean. The following 
		// two lines allocate the generic array for you based on whether you
		// have selected to store the root at index 0 or 1 above.
		if (rootIndex == 0) {
			storage = (T[]) java.lang.reflect.Array.newInstance(dataType, DEFAULT_SIZE);
		} else {
			storage = (T[]) java.lang.reflect.Array.newInstance(dataType, DEFAULT_SIZE+1);
		}
		currentSize = 0;
	}
	
	/*
	 * Constructor that initializes the array to hold size elements
	 */
	@SuppressWarnings({"unchecked"})
	public HeapPriorityQueue(Class<T> clazz, int size) {
		// Creating generics arrays in Java is not very clean. The following 
		// two lines allocate the generic array for you based on whether you
		// have selected to store the root at index 0 or 1 above.
		if (rootIndex == 0) {
			storage = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
		} else {
			storage = (T[]) java.lang.reflect.Array.newInstance(clazz, size+1);
		}
		currentSize = 0;
	}

	private void swampIndex(int a, int b) {
		T tempVal = storage[a];
		storage[a] = storage[b];
		storage[b] = tempVal;
	}

	public void insert (T element) throws HeapFullException {
		if (isFull()) {
			throw new HeapFullException();
		}
		if (isEmpty()) {
			storage[rootIndex] = element;
			currentSize ++;
			return;
		}
			storage[currentSize] = element;
			bubbleUp(currentSize);
			currentSize ++;
    }
	
	private void bubbleUp(int index) {
		if (index == rootIndex || storage[index] == null) {
			return;
		}
		int parIndex = (index-1) / 2;
		if (storage[parIndex].compareTo(storage[index]) > 0) {
			swampIndex(parIndex, index);
			bubbleUp(parIndex);
		}
		else {
			return;
		
		}
	}
			
	public T removeMin() throws HeapEmptyException {
		if (isEmpty()) {
			throw new HeapEmptyException();
		}
		if (currentSize == 1) {
			T tempVal = storage[rootIndex];
			storage[rootIndex] = null;
			currentSize --;
			return tempVal;
		}
		T tempVal = storage[rootIndex];
		storage[rootIndex] = storage[currentSize-1];
		storage[currentSize-1] = null;
		currentSize--;
		bubbleDown(rootIndex);
		return tempVal;
	}
	
	private void bubbleDown(int index) {
			int left = (index*2) + 1;
			int right = (index*2) + 2;
			
			if (left >= currentSize || index >= storage.length) {
				return;
			}

			if (storage[left] == null && storage[right] == null) {
				return;
			}
			else if (storage[left] == null && storage[right] != null) {
				if (storage[right].compareTo(storage[index]) > 0) {
					swampIndex(right, index);
					bubbleDown(right);
				}
			}
			else if (storage[right] == null && storage[left] != null) {
				if (storage[left].compareTo(storage[index]) < 0) {
					swampIndex(left, index);
					bubbleDown(left);
				}
			}
			else if (storage[left].compareTo(storage[right]) < 0) {
				if (storage[left].compareTo(storage[index]) < 0) {
					swampIndex(left, index);
					bubbleDown(left);
				}
			}
			else if (storage[right].compareTo(storage[left]) <= 0) {
				if (storage[right].compareTo(storage[index]) < 0) {
					swampIndex(right, index);
					bubbleDown(right);
			}
		}
	}

	public boolean isEmpty(){
		return (currentSize == 0);
	}
	
	public boolean isFull() {
		return (storage.length == currentSize);
	}
	
	public int size () {
		return currentSize;
	}

	public String toString() {
		String s = "";
		String sep = "";
		if (rootIndex == 0) {
			for (int i = 0; i < currentSize; i++) {
				s += sep + storage[i];
				sep = " ";				
			}
		} else if (rootIndex == 1) {
			for(int i=1; i<=currentSize; i++) {
				s += sep + storage[i];
				sep = " ";
			}
		}
		return s;
	}
}
