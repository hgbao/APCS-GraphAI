package com.trimaplebot.algorithm.astar;

import java.util.*;

public class GraphAStar<T>{
	private Map<T, Map<NodeData<T>, Double>> graph;
	private HashMap<T, HashMap<T, Double>> heuristicMap;
	private Map<T, NodeData<T>> nodeList;

	public GraphAStar(HashMap<T, HashMap<T, Double>> heuristicMap) {
		graph = new HashMap<T, Map<NodeData<T>, Double>>();
		nodeList = new HashMap<T, NodeData<T>>();
		this.heuristicMap = heuristicMap;
	}

	public void addNode(T node) {
		graph.put(node, new HashMap<NodeData<T>, Double>());
		nodeList.put(node, new NodeData<T>(node, heuristicMap.get(node)));
	}

	public void addEdge(T from, T to, double weight) {
		graph.get(from).put(nodeList.get(to), weight);
	}

	public Map<NodeData<T>, Double> edgesFrom(T node) {
		return Collections.unmodifiableMap(graph.get(node));
	}

	public NodeData<T> getNodeData(T node) {
		return nodeList.get(node);
	}
}
