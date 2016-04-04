package datastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 返回给定起点到s, 到graph中任一顶点的最短路径
 * @author leo
 *
 */
public class BFSPaths {

	private boolean[] marked;
	private int[] distTo;
	private int[] edgeTo;
	
	public BFSPaths(DirectedGraph graph, int s){
		marked = new boolean[graph.V()];
		edgeTo = new int[graph.V()];
		distTo = new int[graph.V()];
		for (int i = 0; i < graph.V(); i++)
			distTo[i] = Integer.MAX_VALUE;
		bfs(graph, s);
	}
	
	private void bfs(DirectedGraph graph, int s){
		Queue<Integer> q = new Queue<>();
		marked[s] = true;
		distTo[s] = 0;
		q.enqueue(new Integer(s));
		while (!q.isEmpty()){
			int v = q.dequeue().intValue();
			for (int w : graph.adj(v)){
				if (!marked[w]){
					distTo[w] = distTo[v] + 1;
					edgeTo[w] = v;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}
	
	/**
	 * 是否存在从s到v的一条有向路径
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(int v){
		return marked[v];
	}
	
	/**
	 * 返回从s到v的最短路径中边的个数
	 * @param v
	 */
	public int distTo(int v){
		return distTo[v];
	}
	
	public Iterable<Integer> pathTo(int v){
		if (!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(new Integer(x));
		path.push(x);
		return path;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < edgeTo.length; i++)
			sb.append(edgeTo[i]).append("->").append(i).append(", ");
		sb.append("]\n");
		sb.append("[");
		for (int i = 0; i < distTo.length; i++)
			sb.append(i).append(": ").append(distTo[i]).append(", ");
		sb.append("]\n");
		return sb.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("data/tinyDG.txt"))){
			DirectedGraph graph = new DirectedGraph(reader);
			System.out.println(graph.toString());
			int s = 2;
			BFSPaths path = new BFSPaths(graph, s);
			System.out.println(path.toString());
			for (int v = 0; v < graph.V(); v++){
				Iterable<Integer> shortPath = path.pathTo(v);
				if (shortPath == null) continue;
				for (Integer w : shortPath){
					System.out.print(w.intValue() + "->");
				}
				System.out.println();
			}
		}
	}

}
