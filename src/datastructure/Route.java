/**
 * 实现代码文件
 * 
 * @author XXX
 * @since 2016-3-4
 * @version V1.0
 */
package com.routesearch.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class Route
{
	private static AllPairPath all;
	private static LowestCostPath lowestCost;
	private static List<List<Integer>> phrases = new ArrayList<List<Integer>>();;
    /**
     * 你需要完成功能的入口
     * 
     * @author 刘智
     * @since 2016-3-4
     * @version V1
     */
    public static String searchRoute(String graphContent, String condition)
    {
    	String[] edges = graphContent.split("\\s+");
    	String[] conditions = condition.trim().split(",");
		String[] includedNodes = conditions[2].split("\\|");
		List<Integer> nodes = new ArrayList<Integer>(includedNodes.length);
		for (int i = 0; i < includedNodes.length; i++) nodes.add(new Integer(includedNodes[i]));
		
		EdgeWeightedDirectedGraph g = new EdgeWeightedDirectedGraph(edges);
		System.out.println(g);
		System.out.println("start vertice: " + conditions[0] + ", end vertice: " + conditions[1]);
		System.out.println("the included vertices: " + nodes);
		int start = Integer.parseInt(conditions[0]), end = Integer.parseInt(conditions[1]);
		all = new AllPairPath(g);
//		System.out.println(all);
		
		/*lowestCost = new LowestCostPath(all);
		if (nodes.size() > 7){
			int n = nodes.size() / 7, residual = nodes.size() % 7;
			int from = 0, termination = 0;
			for (int i = 0; i <= n; i++){
				from = i * 7; termination = i * 7 + 7;
				if (i == n && residual > 0){
					termination = i * 7 + residual;
				} else if (i == n && residual == 0) break;
				phrases.add(nodes.subList(from, termination));
			}
		} else phrases.add(nodes);
		System.out.println(phrases);
		double cost = 0; int len = phrases.size(), termination = 0;
		for (int i = 0; i < len; i++){
			List<Integer> phrase =  phrases.get(i);
			if (i == len - 1) termination = end;
			else termination = phrase.get(phrase.size() - 1);
			System.out.println(start + ", " + termination);
			cost += lowestCost.findLowestCostPath(start, termination, phrase);
			System.out.println(cost);
			start = phrase.get(phrase.size() - 1);
		}
		System.out.println(cost);*/
		double cost = 0;
		Queue<DirectedEdge> sequence = new Queue<>();
		int current_node = 0, next_node = 0; 
		//找到起点start与中间结点序列中代价最小的一个节点，将该节点作为中间结点遍历的第一个
		current_node = findLowestCostVertice(start, nodes);
		cost += all.cost(start, current_node);
		sequence.addAll(all.pathTo(start, current_node));
		//找到后将该节点从中间结点集中移除
		nodes.remove(new Integer(current_node));
		for (DirectedEdge e : sequence){
			System.out.print(e + " ");
		}
		System.out.println();
		//开始遍历中间结点集，以此找出当前节点集中与前一个的代价最小顶点
		int count = nodes.size();
		while (count-- > 0){
			next_node = findLowestCostVertice(current_node, nodes);
			cost += all.cost(current_node, next_node);
			sequence.addAll(all.pathTo(current_node, next_node));
			current_node = next_node;
			//找到后将该节点从中间结点集中移除
			nodes.remove(new Integer(next_node));
		}
		for (DirectedEdge e : sequence){
			System.out.print(e + " ");
		}
		System.out.println();
		//搜索中间结点集中最后一个节点vn与末端节点end之间的最短路径
		Iterable<DirectedEdge> last = all.pathTo(current_node, end);
		cost += all.cost(current_node, end);
		sequence.addAll(last);
		for (DirectedEdge e : sequence){
			System.out.print(e + " ");
		}
		System.out.println();
		System.out.println("total cost: " + cost);
        return "hello world!";
    }
    /**
     * 找出开始节点start到剩余中间结点集中，代价最小的顶点current
     * 如果中间结点集为空，则返回-1； 如果中间结点集只有一个顶点，返回该顶点。
     * @param start
     * @param nodes
     * @return
     */
    public static int findLowestCostVertice(int start, List<Integer> nodes){
    	int current = -1;
    	if (nodes != null && nodes.size() > 1){
    		current = nodes.get(0).intValue();
        	double cost  = all.cost(start, current);
    		for (int i = 1; i < nodes.size(); i++){
    			double cost_i = all.cost(start, nodes.get(i).intValue());
    			if (cost > cost_i) current = nodes.get(i).intValue();
    		}
    	} else if (nodes != null && nodes.size() == 1) current = nodes.get(0).intValue();
    	
		return current;
    }
    
}