package com.trimaplebot.model;

public class Node {
	//Attribute
	String nodeName;
	int nodeX, nodeY; //Coordinates
	
	//Constructor
	public Node(String nodeName, int nodeX, int nodeY) {
		super();
		this.nodeName = nodeName;
		this.nodeX = nodeX;
		this.nodeY = nodeY;
	}
	public Node(String nodeName){
		super();
		this.nodeName = nodeName;
	}
	
	//Getters and Setters
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getNodeX() {
		return nodeX;
	}
	public void setNodeX(int nodeX) {
		this.nodeX = nodeX;
	}
	public int getNodeY() {
		return nodeY;
	}
	public void setNodeY(int nodeY) {
		this.nodeY = nodeY;
	}
	
	@Override
	public String toString() {
		return this.nodeName;
	}
	
}
