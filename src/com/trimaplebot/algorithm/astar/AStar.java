package com.trimaplebot.algorithm.astar;

import java.util.*;
import java.util.Map.Entry;

public class AStar<T> {

	private GraphAStar<T> graph;

	public AStar(GraphAStar<T> graphAStar) {
		this.graph = graphAStar;
	}

	private class NodeComparator implements Comparator<NodeData<T>> {
		public int compare(NodeData<T> x, NodeData<T> y) {
			if (x.getF() > y.getF())
				return 1;
			if (x.getF() < y.getF())
				return -1;
			return 0;
		}
	}

	public ArrayList<T> search(T start, T end) {
		Queue<NodeData<T>> open = new PriorityQueue<NodeData<T>>(11,
				new NodeComparator());
		Set<NodeData<T>> close = new HashSet<NodeData<T>>();
		Map<T, T> path = new HashMap<T, T>();

		NodeData<T> startNode = graph.getNodeData(start);
		startNode.setG(0);
		startNode.setF(end);
		open.add(startNode);

		while (!open.isEmpty()) {
			NodeData<T> node = open.poll();
			close.add(node);

			// Success
			if (node.getData().equals(end)) {
				return createPath(path, end);
			}

			// Expand
			for (Entry<NodeData<T>, Double> neighborEntry : graph.edgesFrom(
					node.getData()).entrySet()) {
				NodeData<T> neighbor = neighborEntry.getKey();
				double originF = neighbor.getF();

				double tmpG = node.getG() + neighborEntry.getValue();

				if (open.contains(neighbor) || close.contains(neighbor)) {
					if (tmpG < neighbor.getG()) {
						neighbor.setG(tmpG);
						neighbor.setF(end);
						open.add(neighbor);
						path.put(neighbor.getData(), node.getData());
					}
				} else {
					neighbor.setG(tmpG);
					neighbor.setF(end);
					open.add(neighbor);
					path.put(neighbor.getData(), node.getData());
				}

				if (neighbor.getF() < originF && close.contains(neighbor)) {
					close.remove(neighbor);
					open.add(neighbor);
				}
			}
		}
		return null;
	}

	private ArrayList<T> createPath(Map<T, T> path, T destination) {
		ArrayList<T> pathList = new ArrayList<T>();
		pathList.add(destination);
		while (path.containsKey(destination)) {
			destination = path.get(destination);
			pathList.add(destination);
		}
		Collections.reverse(pathList);
		return pathList;
	}
}
