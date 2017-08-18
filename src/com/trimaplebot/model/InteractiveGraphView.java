package com.trimaplebot.model;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class InteractiveGraphView {
	public static Graph<Node, String> g;
	public static Factory<Node> nodeFactory;
	public static Factory<String> edgeFactory;
	
	private static int nodeCount = 0;

	public InteractiveGraphView() {
		g = new SparseMultigraph<Node, String>();
		nodeFactory = new Factory<Node>() {
			public Node create() {
				String nodeName = Configuration.DEFAULT_NODE_NAME + nodeCount++;
				Node newNode = new Node(nodeName);
				Data.addNode(newNode);
				Data.nodeAdded = true;
				return newNode;
			}
		};
		edgeFactory = new Factory<String>() {
			public String create() {
				String edgeName = Configuration.DEFAULT_EDGE_NAME;
				Data.edgeAdded = true;
				return edgeName;
			}
		};
	}
}
