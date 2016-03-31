package datastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Topological {
	
	private Iterable<Integer> order;
	
	public Topological(EdgeWeightedDirectedGraph g){
		EdgeWeightedDirectedCycle cyclefinder = new EdgeWeightedDirectedCycle(g);
		if (!cyclefinder.hasCycle()){
			DepthFirstOrder dfs = new DepthFirstOrder(g);
			order = dfs.reversePostOrder();
		}
	}
	
	public Iterable<Integer> order(){ return order; }
	//判断该图是否为有向无环图
	public boolean isDAG(){ return order != null; }

	public static void main(String[] args) throws NumberFormatException, IOException {
		long start = System.currentTimeMillis();
		try (BufferedReader reader = new BufferedReader(new FileReader("data/case1/topo.csv"))){
			EdgeWeightedDirectedGraph g = new EdgeWeightedDirectedGraph(reader);
			System.out.print(g);
			Topological topo = new Topological(g);
			System.out.println(topo.isDAG());
			Iterable<Integer> order = topo.order();
			for (Integer val : order){
				System.out.print(val.intValue() + " ");
			}
			long end = System.currentTimeMillis();
			System.out.println("Its temporal cost: " + (end - start));
		}
	}

}
