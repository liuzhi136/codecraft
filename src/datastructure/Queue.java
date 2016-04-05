package com.routesearch.route;

import java.util.Iterator;

public class Queue<T> implements Iterable<T>{

	private int N;
	private Node first;
	private Node last;
	private class Node{
		T item;
		Node next;
	}
	
	public void enqueue(T item){
		Node oldlast = last;
		last = new Node();
		last.item = item;
		if (isEmpty()) first = last;
		else oldlast.next = last;
		N++;
	}
	
	public T dequeue(){
		T item = first.item;
		first = first.next;
		if (isEmpty()) last = null;
		N--;
		return item;
	}
	
	public int size(){
		return N;
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	public void addAll(Iterable<T> edges){
		if (edges == null) return;
		for (T e : edges){
			this.enqueue(e);
		}
	}
	
	public static void main(String[] args) {
		Queue<String> q = new Queue<>();
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
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<T>{

		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			
		}
		
	}

}
