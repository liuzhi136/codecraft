package com.routesearch.route;

public class AllPairPath {
	
	private SP[] all;
	
	public AllPairPath(EdgeWeightedDirectedGraph g){
		int[] vertices = g.Vertices();
		all = new SP[vertices.length];
		for (int v : vertices){
			if (v == -1) continue;
			all[v] = new SP(g, v);
		}
	}
	
	public Iterable<DirectedEdge> pathTo(int from, int to){ return all[from].pathTo(to); }
	public double cost(int from, int to){ return all[from].cost(to); }
	public boolean hasPathTo(int from, int to){ return all[from].hasPathTo(to); }

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (SP sp : all){
			if (sp == null) continue;
			sb.append(sp.toString()).append("\n");
		}
		return sb.toString();
	}
}
