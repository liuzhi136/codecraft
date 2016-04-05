package com.routesearch.route;

public class DirectedEdge {
	
	private final int from;
	private final int to;
	private final double weight;
	
	public DirectedEdge(int from, int to, double weight){
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public double weight(){
		return weight;
	}
	
	public int from(){
		return from;
	}
	
	public int to(){
		return to;
	}
	
	public String toString(){
		return String.format("%d->%d %.3f", from, to, weight);
	}
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (o == null) return false;
		if (this.getClass() != o.getClass()) return false;
		DirectedEdge other = (DirectedEdge) o;
		if (this.weight != other.weight()) return false;
		if (this.from != other.from()) return false;
		if (this.to != other.to()) return false;
		return true;
	}
	
	@Override
	public int hashCode(){
		int hash = 17;
		hash = 31 * hash + from;
		hash = 31 * hash + to;
		long bits = Double.doubleToLongBits(weight);
        hash = 31 * hash + (int)(bits ^ (bits >>> 32));
		return hash;
	}
}
