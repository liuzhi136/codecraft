package algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import datastructure.DirectedEdge;
import datastructure.EdgeWeightedDirectedGraph;
import datastructure.Stack;
import datastructure.Topological;

public class TopologicalSP {
	
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	private final int s;
	
	public TopologicalSP(EdgeWeightedDirectedGraph g, int s){
		distTo = new double[g.V()];
		edgeTo = new DirectedEdge[g.V()];
		this.s = s;
		for (int v = 0; v < g.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0;
		
		Topological topo = new Topological(g);
		Iterable<Integer> order = topo.order();
		for (Integer val : order)
			System.out.print(val + " ");
		for (Integer v : order)
			for (DirectedEdge e : g.adj(v.intValue()))
				relax(e);
	}
	/**
	 * 边松弛方法，判断当前节点的代价(distTo[w])是否比其父节点的代价(distTo[v])和该边权重(e.weight())的和还大
	 * 如果distTo[w] > distTo[v] + e.weight()成立，更新distTo[w]为新的代价值，且w入队
	 * @param g
	 * @param v
	 */
	private void relax(DirectedEdge e){
			int v = e.from(), w = e.to();
			if (distTo[w] > distTo[v] + e.weight()){
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
			}
	}

	public double cost(int v){ return distTo[v]; }
	public boolean hasPathTo(int v){ return distTo[v] < Double.POSITIVE_INFINITY; }
	public Iterable<DirectedEdge> pathTo(int v){
		if (!hasPathTo(v)) return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge x = edgeTo[v]; x != null; x = edgeTo[x.from()])
			path.push(x);
		return path;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("the original vertice is : ").append(s).append("\n");
		sb.append("[");
		for (int i = 0; i < edgeTo.length; i++)
			sb.append(i).append(": ").append(edgeTo[i]).append(", ");
		sb.append("]\n");
		sb.append("[");
		for (int i = 0; i < distTo.length; i++)
			sb.append(i).append(": ").append(distTo[i]).append(", ");
		sb.append("]\n");
		return sb.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		long start = System.currentTimeMillis();
		try (BufferedReader reader = new BufferedReader(new FileReader("data/tinyEWDAG.txt"))){
			EdgeWeightedDirectedGraph g = new EdgeWeightedDirectedGraph(reader);
			System.out.print(g);
			TopologicalSP sp = new TopologicalSP(g, 5);
			System.out.print(sp.toString());
			Iterable<DirectedEdge> path = sp.pathTo(6);
			if (path != null){
				for (DirectedEdge e : path)
					System.out.print(e + " ");
				System.out.print("the total cost: " + sp.cost(6));
			} else {
				System.out.println("NA");
			}
			System.out.println();
			long end = System.currentTimeMillis();
			System.out.println("Its temporal cost: " + (end - start));
		}
	}

}
