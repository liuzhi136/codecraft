package com.routesearch.route;

import java.util.Iterator;

public class Stack<T> implements Iterable<T>{

	private int N;
	private Node first;
	
	private class Node{
		T item;
		Node next;
	}
	
	public void push(T item){
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}
	
	public T pop(){
		T item = first.item;
		N--;
		first = first.next;
		return item;
	}
	
	public T peek(){
		T item = first.item;
		return item;
	}
	
	public static Stack<String> copy(Stack<String> s){
		Stack<String> duplicate = new Stack<>();
		for (String val : s){
			duplicate.push(val);
		}
		return duplicate;
	}
	
	public int size(){
		return N;
	}
	
	public boolean isEmpty(){
		return first == null;
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
