package com.trimaplebot.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import com.trimaplebot.model.Node;

public class IDS {
	// Input
	private ArrayList<Node> list_node;
	private double[][] list_path;
	
	//Result

	public IDS(ArrayList<Node> list_node, double[][] list_path) {
		this.list_node = list_node;
		this.list_path = list_path;
	}

	public ArrayList<Node> search(int from, int to) {
		ArrayList<Node> result = new ArrayList<Node>();
		
		if (from == to){
			result.add(list_node.get(from));
			return result;
		}
		
		// DFS with L to build a path array
		int n = list_node.size();
		int L = 0;
		int[] path = new int[n];
		while (L < n){
			boolean[] visit = new boolean[n];
			for (int i = 0; i < n; i++) {
				path[i] = -1;
			}
			L++;
			recursion(from, visit, path, L, to);
			if (visit[to])
				break;
		}
		
		//Has the result
		while (from != to) {
			result.add(list_node.get(to));
			to = path[to];
		}
		result.add(list_node.get(to));
		Collections.reverse(result);
		
		return result;
	}

	private void recursion(int v, boolean[] visit, int[] path, int L, int destination) {
		visit[v] = true;
		if (visit[destination]){
			return;
		}
		if (L == 0)
			return;
		
		for (int i = 0; i < visit.length; i++){
			if (!visit[i] && list_path[v][i] != 0) {
				path[i] = v;
				L--;
				recursion(i, visit, path, L, destination);
				//Recovery
				if (visit[destination])
					return;
				visit[i] = false;
				L++;
				path[i] = -1;
			}
		}
	}
}
