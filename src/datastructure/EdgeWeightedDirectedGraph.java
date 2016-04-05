package com.routesearch.route;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EdgeWeightedDirectedGraph {

	private int V;
	private int E;
	private Bag<DirectedEdge>[] adj = (Bag<DirectedEdge>[])new Bag[600];
	private Map<DirectedEdge, String> edges = new HashMap<>(4800);
	private int[] vertices = new int[600];
	
	public EdgeWeightedDirectedGraph(){
		this.E = 0;
		for (int i = 0; i < 600; i++) vertices[i] = -1;
	}
	
	public EdgeWeightedDirectedGraph(BufferedReader reader) throws NumberFormatException, IOException{
		this();
		int E = 45;
		Set<Integer> verticeSet = new HashSet<>(600);
		for (int i = 0; i < E; i++){
			String line = reader.readLine().trim();
			if (line == null || "".equals(line)) continue;
			String[] nodes = line.split("\\s+|,");
			int from = Integer.parseInt(nodes[1]), to = Integer.parseInt(nodes[2]);
			//收集顶点
			verticeSet.add(new Integer(from)); verticeSet.add(new Integer(to));
			double weight = Double.parseDouble(nodes[3]);
			DirectedEdge edge = new DirectedEdge(from, to, weight);
			edges.put(edge, nodes[0]);
			addEdge(edge);
		}
		V = verticeSet.size();
		Integer[] arr = new Integer[V];
		verticeSet.toArray(arr);
		for (int i = 0; i < V; i++) vertices[i] = arr[i]; 
	}

	/**
	 * 接受一个字符串，字符串的内容是包含所有边的字符串数组
	 * @param edges
	 */
	public EdgeWeightedDirectedGraph(String[] edges){
		this();
		Set<Integer> verticeSet = new HashSet<>(600);
		for (int i = 0; i < edges.length; i++){
			String line = edges[i];
			if (line == null || "".equals(line)) continue;
			String[] nodes = line.split("\\s+|,");
			int from = Integer.parseInt(nodes[1]), to = Integer.parseInt(nodes[2]);
			//收集顶点
			verticeSet.add(new Integer(from)); verticeSet.add(new Integer(to));
			double weight = Double.parseDouble(nodes[3]);
			DirectedEdge edge = new DirectedEdge(from, to, weight);
			//按照边保存边对应的编号
			this.edges.put(edge, nodes[0]);
			addEdge(edge);
		}
		V = verticeSet.size();
		Integer[] arr = new Integer[V];
		verticeSet.toArray(arr);
		for (int v = 0; v < V; v++) vertices[v] = arr[v]; 
	}
	
	public int V(){ return V; }
	public int E(){ return E; }
	public int[] Vertices(){ return vertices; }
	
	public void addEdge(DirectedEdge edge){
		if (adj[edge.from()] == null) adj[edge.from()] = new Bag<DirectedEdge>();
		if (adj[edge.to()] == null) adj[edge.to()] = new Bag<DirectedEdge>();
		adj[edge.from()].add(edge);
		E++;
	}
	
	public int outdegree(int v){ return adj[v].size(); }
	
	public Iterable<DirectedEdge> adj(int v){ return adj[v]; } 
	
	/**
	 * 返回图中所有有向边，按照数据格式中边的编号存储
	 * @return
	 */
	public Map<DirectedEdge, String> edges(){
		return edges;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Vertices: ").append(V).append(", edges: ").append(E).append("\n");
		for (int v: vertices){
			if (v == -1) continue;
			sb.append(v).append(": ");
			System.out.print(v + " ");
			for (DirectedEdge e : adj[v]){
				sb.append(e.to()).append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("data/tinyEWD.txt"))){
			EdgeWeightedDirectedGraph g = new EdgeWeightedDirectedGraph(reader);
			System.out.println(g);
		}
	}

}
