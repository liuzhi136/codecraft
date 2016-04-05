package com.routesearch.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LowestCostPath {
	
	private static AllPairPath all;
	private static Stack<Integer> path;
	
	public LowestCostPath(AllPairPath all){
		LowestCostPath.all = all;
		path = new Stack<>();
	}
	
	public static double findLowestCostPath(int start, int end, List<Integer> traverseNodes){
		if (traverseNodes.size() == 0){
//			System.out.println("start: " + start + ", end: " + end + ", cost: " + all.cost(start, end));
			return all.cost(start, end);
		}
//		System.out.println("current traverse nodes: \n" + traverseNodes);
		Map<Integer, Double> allCost = new HashMap<>(traverseNodes.size());
		for (Integer v : traverseNodes) {
			double result = 0;
			List<Integer> temp = deepCopy(traverseNodes);
			temp.remove(v);
			result = all.cost(start, v.intValue());
			/*//当起始两点间不能到达时，该分支放弃
			if (result == Double.MAX_VALUE) continue;*/
//			System.out.println("start: " + start + ", v: " + v + ", cost: " + result);
			result += findLowestCostPath(v, end, temp);
			allCost.put(v, result);
		}
//		System.out.println(allCost);
		//当本次递归结束时，本次所有的情况都遍历结束，求当前节点中的最小值
		Integer v = min(allCost);
		path.push(v);
//		System.out.println(v);
		return allCost.get(v);
	}
	private static List<Integer> deepCopy(List<Integer> traverseNodes){
		List<Integer> copy = new ArrayList<>(traverseNodes.size());
		for (Integer v : traverseNodes){
			copy.add(new Integer(v.intValue()));
		}
		return copy;
	}
	/**
	 * 找到当前最小
	 * @param allCost
	 * @return
	 */
	private static Integer min(Map<Integer, Double> allCost){
		if (allCost == null) return null;
		Integer result = null; double cost = Double.POSITIVE_INFINITY;
		for (Map.Entry<Integer, Double> entry : allCost.entrySet()){
			if (cost > entry.getValue().doubleValue()){
				cost = entry.getValue().doubleValue();
				result = entry.getKey();
			}
		}
		return result;
	}

	public Stack<Integer> path(){ return path; }
}
