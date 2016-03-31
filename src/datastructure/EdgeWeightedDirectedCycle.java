package datastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EdgeWeightedDirectedCycle {
	
	private boolean[] marked;
	private DirectedEdge[] edgeTo;
	private Stack<DirectedEdge> cycle;
	private boolean[] onStack;
	
	public EdgeWeightedDirectedCycle(EdgeWeightedDirectedGraph g){
		onStack = new boolean[g.V()];
		edgeTo = new DirectedEdge[g.V()];
		marked = new boolean[g.V()];
		for (int v = 0; v < g.V(); v++)
			if (!marked[v]) dfs(g, v);
	}
	private void dfs(EdgeWeightedDirectedGraph g, int v){
		onStack[v] = true;
		marked[v] = true;
		for (DirectedEdge e : g.adj(v)){
			int w = e.to();
			if (this.hasCycle()) return;
			else if (!marked[w]){ edgeTo[w] = e; dfs(g, w); }
			else if (onStack[w]){
				cycle = new Stack<DirectedEdge>();
				while (e.from() != w){
					cycle.push(e);
					e = edgeTo[e.from()];
				}
				cycle.push(e);
				return;
			}
		}
		onStack[v] = false;
	}
	/**
	 * 判断该有向图是否存在环
	 * @return
	 */
	public boolean hasCycle(){ return cycle != null; }
	/**
	 * 返回环
	 * @return
	 */
	public Iterable<DirectedEdge> cycle(){ return cycle; }

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("data/case1/topo.csv"))){
			EdgeWeightedDirectedGraph g = new EdgeWeightedDirectedGraph(reader);
			System.out.println(g);
			EdgeWeightedDirectedCycle cyclefinder = new EdgeWeightedDirectedCycle(g);
			System.out.println("Contains a cycle? " + cyclefinder.hasCycle());
			Iterable<DirectedEdge> cycle = cyclefinder.cycle();
			if (cycle != null){
				for (DirectedEdge e : cycle){
					System.out.print(e + " ");
				}
			}
			
		}
	}

}
