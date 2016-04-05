package com.routesearch.route;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 此类用于返回有向图基于深度优先搜索的顶点序列，此类用于辅助拓扑排序
 * @author leo
 *
 */
public class DepthFirstOrder {
	
	private boolean[] marked;
	private Queue<Integer> pre;//先序
	private Queue<Integer> post;//后序
	private Stack<Integer> reversePost;//逆后序

	public DepthFirstOrder(EdgeWeightedDirectedGraph g){
		int[] vertices = g.Vertices();
		pre = new Queue<>();
		post = new Queue<>();
		reversePost = new Stack<>();
		marked = new boolean[vertices.length];
		for (int v : vertices){
			if (v == -1) continue;
			if (!marked[v]) dfs(g, v);
		}
			
	}
	private void dfs(EdgeWeightedDirectedGraph g, int v){
		pre.enqueue(new Integer(v));
		marked[v] = true;
		for (DirectedEdge e : g.adj(v)){
			int w = e.to();
			if (!marked[w]) dfs(g, w);
		}
		post.enqueue(new Integer(v));
		reversePost.push(new Integer(v));
	}
	
	public Iterable<Integer> preOrder(){ return pre; }
	public Iterable<Integer> postOrder(){ return post; }
	public Iterable<Integer> reversePostOrder(){ return reversePost; }
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		try (BufferedReader reader = new BufferedReader(new FileReader("data/case2/topo.csv"))){
			EdgeWeightedDirectedGraph g = new EdgeWeightedDirectedGraph(reader);
			System.out.println(g);
			DepthFirstOrder dfsOrder = new DepthFirstOrder(g);
			Iterable<Integer> reverse = dfsOrder.reversePostOrder();
			List<Integer> demands = new ArrayList<>();
			demands.add(new Integer(3));demands.add(new Integer(5));demands.add(new Integer(7));
			demands.add(new Integer(11));demands.add(new Integer(13));demands.add(new Integer(17));
			for (Integer v : reverse){
				System.out.print(v.intValue() + " ");
			}
		}
	}
}
