package com.routesearch.route;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SP {

	private double[] distTo;//从起点到任意可达节点的代价，不可达时为Max.Positive_Infinity
	private DirectedEdge[] edgeTo;//存储最优边
	private Queue<Integer> q = new Queue<>();//该方法使用广度优先思想，该队列是广度优先中的队列
	private final int s;//起点
	
	/**
	 * 从ｓ开始到任意可达顶点的最短路径, 思想类似广度优先
	 * @param g
	 * @param s
	 */
	public SP(EdgeWeightedDirectedGraph g, int s){
		distTo = new double[600];
		edgeTo = new DirectedEdge[600];
		this.s = s;
		for (int i = 0; i < g.V(); i++)
			distTo[i] = Double.MAX_VALUE;
		distTo[s] = 0;
		bfs(g, s);
	}
	/**
	 * 广度优先遍历
	 * @param g
	 * @param s
	 */
	private void bfs(EdgeWeightedDirectedGraph g, int s){
		q.enqueue(new Integer(s));
		while (!q.isEmpty()){
			int v = q.dequeue().intValue();
			relax(g, v);
		}
	}
	
	/**
	 * 边松弛方法，判断当前节点的代价(distTo[w])是否比其父节点的代价(distTo[v])和该边权重(e.weight())的和还大
	 * 如果distTo[w] > distTo[v] + e.weight()成立，更新distTo[w]为新的代价值，且w入队
	 * @param g
	 * @param v
	 */
	private void relax(EdgeWeightedDirectedGraph g, int v){
		for (DirectedEdge e : g.adj(v)){
			int w = e.to();
				if (distTo[w] > distTo[v] + e.weight()){
					distTo[w] = distTo[v] + e.weight();
					edgeTo[w] = e;
					q.enqueue(w);
			}
		}
	}
	
	/**
	 * 判断从起点s到顶点v是否可达
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(int v){
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	/**
	 * 返回起点s到顶点v的代价
	 * @param v
	 * @return
	 */
	public double cost(int v){
		return distTo[v];
	}
	/**
	 * 返回从起点s到顶点v的一个最短路径，如果v不可达则返回null
	 * @param v
	 * @return
	 */
	public Iterable<DirectedEdge> pathTo(int v){
		if (!hasPathTo(v)) return null;
		Stack<DirectedEdge> path = new Stack<>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.push(e);
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
		try (BufferedReader reader = new BufferedReader(new FileReader("data/case2/topo.csv"))){
			EdgeWeightedDirectedGraph g = new EdgeWeightedDirectedGraph(reader);
			int[] conditions = {3,5,7,11,13,17};
			System.out.print(g);
			SP sp = new SP(g, 2);
			System.out.print(sp.toString());
			for(int val : conditions){
				System.out.println(String.format("%d to %d", 2, val));
				Iterable<DirectedEdge> path = sp.pathTo(val);
				if (path != null){
					for (DirectedEdge e : path)
						System.out.print(e + " ");
					System.out.print("the total cost" + sp.cost(val));
				} else {
					System.out.println("NA");
				}
				System.out.println();
			}
			
			long end = System.currentTimeMillis();
			System.out.println("Its temporal cost: " + (end - start));
		}
	}

}
