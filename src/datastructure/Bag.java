package com.routesearch.route;

import java.util.Iterator;

public class Bag<T> implements Iterable<T> {
	
	private Node first;
	private int N;
	private class Node{
		T item;
		Node next;
	}
	
	public void add(T item){
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}
	
	public int size(){ return N; }

	@Override
	public Iterator<T> iterator() {
		return new ListIteratorr();
	}
	
	private class ListIteratorr implements Iterator<T>{

		private Node current = first;
		@Override
		public boolean hasNext() { return current != null; }

		@Override
		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {}
		
	}

}
