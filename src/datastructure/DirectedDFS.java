package datastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DirectedDFS {
	
	private boolean[] marked;
	
	/**
	 * s是起始点，找出所有s可达的点
	 * @param g
	 * @param s
	 */
	public DirectedDFS(DirectedGraph g, int s){
		marked = new boolean[g.V()];
		dfs(g, s);
	}
	
	/**
	 * sources是起点集合，找出sources中任一顶点可达的所有定点。
	 * @param g
	 * @param sources
	 */
	public DirectedDFS(DirectedGraph g, Iterable<Integer> sources){
		marked = new boolean[g.V()];
		for (int s : sources)
			if (!marked[s]) dfs(g, s);
	}
	
	private void dfs(DirectedGraph g, int s){
		marked[s] = true;
		for (int w : g.adj(s))
			if (!marked[w])
				dfs(g, w);
	}
	
	public boolean marked(int v){
		return marked[v];
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("data/tinyDG.txt"))){
			DirectedGraph graph = new DirectedGraph(reader);
			System.out.println(graph.toString());
			Bag<Integer> sources = new Bag<Integer>();
			for (int i = 0; i < args.length; i++){
				sources.add(new Integer(args[i]));
			}
			
			DirectedDFS reachable = new DirectedDFS(graph, sources);
			for (int v = 0; v < graph.V(); v++){
				if (reachable.marked(v)) System.out.print(v + " ");
			}
			System.out.println();
		}
	}

}
