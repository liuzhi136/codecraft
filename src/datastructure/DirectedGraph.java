package datastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class DirectedGraph {
	private final int V;
	private int E;
	private Bag<Integer>[] adj;
	
	public DirectedGraph(int V){
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[])new Bag[V];
		for (int v = 0; v < adj.length; v++){
			adj[v] = new Bag<Integer>();
		}
	}
	
	public DirectedGraph(BufferedReader reader) throws NumberFormatException, IOException{
		this(Integer.parseInt(reader.readLine()));//读节点数
		int E = Integer.parseInt(reader.readLine());
		for (int i = 0; i < E; i++){
			String line = reader.readLine();
			if (line == null || "".equals(line)) continue;
			String[] nodes = line.split("\\s+|,");
			int v = Integer.parseInt(nodes[1]), w = Integer.parseInt(nodes[2]);
			addEdge(v, w);
		}
	}
	
	public int V(){ return V;}
	public int E(){ return E;}
	
	public void addEdge(int start, int end){
		adj[start].add(new Integer(end));
		E++;
	}
	
	public Iterable<Integer> adj(int v){
		return adj[v];
	}
	
	public DirectedGraph reverse(){
		DirectedGraph DR = new DirectedGraph(V);
		for (int v = 0; v < adj.length; v++)
			for (int w : adj(v))
				DR.addEdge(w, v);
		return DR;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(V).append(" vertices, ").append(E).append(" edges\n");
		for (int v = 0; v < V; v++){
			sb.append(v).append(": ");
			for (int w : adj[v])
				sb.append(w).append(" ");
			sb.append("\n");
		}
		return sb.toString();
	}
}
