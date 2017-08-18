package com.trimaplebot.algorithm.astar;

import java.util.*;

final class NodeData<T> {
	private T data;
	private Map<T, Double> heuristic;

	private double f; // f = g + h
	private double g; // real distance
	private double h; // heuristic distance

	public NodeData(T node, Map<T, Double> heuristic) {
		this.data = node;
		this.g = Double.MAX_VALUE;
		this.f = Double.MAX_VALUE;
		this.heuristic = heuristic;
	}

	public T getData() {
		return data;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getH() {
		return h;
	}

	public double getF() {
		return f;
	}

	public void setF(T destination) {
		h = heuristic.get(destination);
		f = g + h;
	}
}
