package datastructure;

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
		pre = new Queue<>();
		post = new Queue<>();
		reversePost = new Stack<>();
		marked = new boolean[g.V()];
		for (int v = 0; v < g.V(); v++)
			if (!marked[v]) dfs(g, v);
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
	
}
