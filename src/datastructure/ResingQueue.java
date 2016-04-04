package datastructure;

import java.util.Iterator;

public class ResingQueue<T> implements Iterable<T>{
	
	private int N;
	private T[] arr = (T[])new Object[8];
	private int first = 0;
	
	public void enqueue(T item){
		if (N == arr.length) resize(2*arr.length);
		arr[N++] = item;
	}
	
	public T dequeue(){
		T item = arr[first];
		arr[first] = null;
		N--;
		for (int i = 0; i <= arr.length - 2; i++){
			arr[i] = arr[i+1];
		}
		if (N > 0 && N < arr.length/4) resize(arr.length / 2);
		return item;
	}
	
	private void resize(int length){
		T[] temp = (T[])new Object[length];
		for (int i = 0; i < N; i++){
			temp[i] = arr[i];
		}
		arr = temp;
	}
	
	public String toString(){
		return String.format("capacity: %d, size: %d", arr.length, N);
	}
	
	public int size(){ return N; }
	public boolean isEmpty(){ return N == 0; }

	public static void main(String[] args) {
		ResingQueue<String> q = new ResingQueue<>();
		String[] vals = {"to", "be", "or", "not", "to", "-", "be", "-", "-", "that", "-", "-", "-", "is"};
		for (String val : vals){
			if (!val.equals("-")){
				q.enqueue(val);
			} else if (!q.isEmpty()) System.out.print(q.dequeue() + " ");
		}
		System.out.println("(" + q.size() + " left in queue)");
	}
	
	@Override
	public Iterator<T> iterator() {
		return new QueueIterator();
	}
	private class QueueIterator implements Iterator<T>{

		private int i = N;
		private int start = first;
		
		@Override
		public boolean hasNext() {
			return i > 0;
		}

		@Override
		public T next() {
			i--;
			return arr[start++];
		}

		@Override
		public void remove() {
		}
		
	}
}
