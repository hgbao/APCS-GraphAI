package com.trimaplebot.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import com.trimaplebot.model.Node;

public class UCS {
	private ArrayList<Node> NodeList;
	private double[][] pathList;

	// Constructor
	public UCS(ArrayList<Node> NodeList, double[][] pathList) {
		super();
		this.NodeList = NodeList;
		this.pathList = pathList;
	}

	public ArrayList<Node> search(int source, int goal) {
		ArrayList<Node> result = new ArrayList<Node>();
		ArrayList<Integer> start = new ArrayList<Integer>();
		start.add(source);
		ArrayList<ArrayList> queue = new ArrayList();
		queue.add(start);
		ArrayList path = uni_cost(queue, goal);
		for (int i = 0; i < path.size(); i++) {
			result.add(NodeList.get((int) path.get(i)));
		}

		Collections.reverse(result);
		return result;
	}

	public ArrayList<ArrayList> extend(ArrayList<Integer> path) {
		ArrayList<ArrayList> NewPaths = new ArrayList();
		for (int i = 0; i < NodeList.size(); i++)
			if (pathList[path.get(0)][i] > 0 && !path.contains(i)) {
				ArrayList Path1 = (ArrayList) path.clone();
				Path1.add(0, i);
				NewPaths.add(Path1);
			}
		return NewPaths;
	}

	public ArrayList append(ArrayList x, ArrayList y) {
		ArrayList z = (ArrayList) x.clone();
		for (int i = 0; i < y.size(); i++)
			z.add(y.get(i));
		return z;
	}

	public double path_cost(ArrayList<Integer> path) {
		double cost = 0;
		for (int i = 0; i < path.size() - 1; i++)
			cost = cost + pathList[path.get(i + 1)][path.get(i)];
		return cost;
	}

	public ArrayList<ArrayList> uni_cost(ArrayList<ArrayList> queue, int goal) {
		if (queue.size() == 0)
			return queue;
		if ((Integer) queue.get(0).get(0) == goal)
			return queue.get(0);
		else {
			ArrayList<ArrayList> pathTmp = extend(queue.get(0));
			queue.remove(0);
			ArrayList<ArrayList> queueTmp = append(queue, pathTmp);
			sort(queueTmp);
			return uni_cost(queueTmp, goal);
		}
	}

	public void sort(ArrayList<ArrayList> queue) {
		int out, in;
		for (out = queue.size() - 1; out >= 1; out--)
			for (in = 0; in < out; in++)
				if (path_cost(queue.get(in)) > path_cost(queue.get(in + 1)))
					swap(queue, in, in + 1);
	}

	private void swap(ArrayList<ArrayList> a, int one, int two) {
		ArrayList<Integer> temp = a.get(one);
		a.set(one, a.get(two));
		a.set(two, temp);
	}
}
