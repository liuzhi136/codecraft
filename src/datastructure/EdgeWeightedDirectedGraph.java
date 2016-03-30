package datastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EdgeWeightedDirectedGraph {

	private final int V;
	private int E;
	private Bag<DirectedEdge>[] adj;
	private int[] indegree;
	private DirectedEdge[] edges = new DirectedEdge[600];
	
	public EdgeWeightedDirectedGraph(int V){
		this.V = V;
		E = 0;
		indegree = new int[V];
		adj = (Bag<DirectedEdge>[])new Bag[V];
		for (int i = 0; i < V; i++){
			adj[i] = new Bag<DirectedEdge>();
		}
	}
	
	public EdgeWeightedDirectedGraph(BufferedReader reader) throws NumberFormatException, IOException{
		this(Integer.parseInt(reader.readLine()));//读节点数
		int E = Integer.parseInt(reader.readLine());
		for (int i = 0; i < E; i++){
			String line = reader.readLine().trim();
			if (line == null || "".equals(line)) continue;
			String[] nodes = line.split("\\s+");
			int from = Integer.parseInt(nodes[0]), to = Integer.parseInt(nodes[1]);
			double weight = Double.parseDouble(nodes[2]);
			DirectedEdge edge = new DirectedEdge(from, to, weight);
			edges[i] = edge;
			addEdge(edge);
		}
	}
	
	public int V(){ return V; }
	public int E(){ return E; }
	
	public void addEdge(DirectedEdge edge){
		adj[edge.from()].add(edge);
		indegree[edge.to()]++;
		E++;
	}
	
	public int indegree(int v){
		return indegree[v];
	}
	
	public int outdegree(int v){ return adj[v].size(); }
	
	public Iterable<DirectedEdge> adj(int v){ return adj[v]; } 
	
	/**
	 * 返回图中所有有向边，按照数据格式中边的编号存储
	 * @return
	 */
	public DirectedEdge[] edges(){
		return edges;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Vertices: ").append(V).append(", edges: ").append(E).append("\n");
		for (int v = 0; v < V; v++){
			sb.append(v).append(": ");
			for (DirectedEdge e : adj[v])
				sb.append(e.to()).append(" ");
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
