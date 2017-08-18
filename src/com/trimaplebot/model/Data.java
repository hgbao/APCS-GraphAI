package com.trimaplebot.model;

import java.util.ArrayList;

import com.trimaplebot.support.MatrixSupport;
import com.trimaplebot.ui.MainUI;

public class Data {
	// Data
	public static ArrayList<Node> nodeList = new ArrayList<Node>();
	public static double[][] pathList = new double[0][0];
	public static double[][] heuristic = new double[0][0];
	public static int currentNode = -1;
	public static int currentPath = -1;

	public static boolean edgeAdded = false;
	public static boolean nodeAdded = false;
	public static boolean runningAlgorithm = false;

	// Get data
	public static int getPosition(Node node) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i) == node)
				return i;
		}
		return -1;
	}

	public static int getPosition(String name) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getNodeName().equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}

	// Update data
	public static void addNode(Node node) {
		// Data
		Data.nodeList.add(node);
		currentNode = nodeList.size() - 1;
		pathList = MatrixSupport.addElement(pathList);
		heuristic = MatrixSupport.addElement(heuristic);
		myUI.panelControl.updateData();
	}

	public static void removeNode(int position) {
		Data.nodeList.remove(position);
		currentNode = -1;
		pathList = MatrixSupport.removeElement(pathList, position);
		heuristic = MatrixSupport.removeElement(heuristic, position);
		myUI.panelControl.updateData();
	}

	public static void recoverDefault() {
		// Graph
		int n = nodeList.size();
		for (int i = 0; i < n; i++)
			sgv.g.removeVertex(nodeList.get(i));
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++) {
				if (pathList[i][j] != 0)
					sgv.g.removeEdge(i + " " + j);
			}
		// Data
		nodeList.clear();
		pathList = new double[0][0];
		currentNode = -1;
		currentPath = -1;
		edgeAdded = false;
		nodeAdded = false;
		runningAlgorithm = false;
	}

	// Graph
	public static InteractiveGraphView sgv = new InteractiveGraphView();

	// Main window
	public static MainUI myUI = new MainUI("Trimaple Bot");
}
