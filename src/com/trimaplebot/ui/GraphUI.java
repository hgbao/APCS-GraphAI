package com.trimaplebot.ui;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import com.trimaplebot.model.Configuration;
import com.trimaplebot.model.Data;
import com.trimaplebot.model.Node;
import com.trimaplebot.support.DialogSupport;
import com.trimaplebot.support.GraphicSupport;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.AbstractEdgeShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphUI extends JPanel {
	EditingModalGraphMouse gm;

	private int width, height;

	private Graph<Node, String> graph;
	private Layout<Node, String> layout;
	private VisualizationViewer<Node, String> vv;

	private PickedState<Node> pickNode;
	private PickedState<String> pickEdge;

	private boolean isEdgeSelected = false;
	private boolean isNodeSelected = false;

	public GraphUI(int width, int height) {
		this.width = width;
		this.height = height;
		this.graph = Data.sgv.g;
		addControl();
		addEvent();
		addTransformer();
	}

	private void addControl() {
		layout = new CircleLayout(graph);
		layout.setSize(new Dimension(width, height));
		vv = new VisualizationViewer<Node, String>(layout);
		//View size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		vv.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));

		// Graph mouse
		gm = new EditingModalGraphMouse(vv.getRenderContext(),
				Data.sgv.nodeFactory, Data.sgv.edgeFactory);
		vv.setGraphMouse(gm);
		vv.addKeyListener(gm.getModeKeyListener());

		this.add(vv);
	}

	private void addEvent() {
		// Node picking
		pickNode = vv.getPickedVertexState();
		pickNode.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					isEdgeSelected = false;
					isNodeSelected = true;
					Node item = (Node) e.getItem();
					int pos = Data.getPosition(item);
					if (pos != -1) {
						Data.currentNode = pos;
						Data.currentPath = -1;
						Data.myUI.panelControl.updateData();
						if (!Data.runningAlgorithm) {
							// Clear all picking states
							for (int i = 0; i < Data.nodeList.size(); i++) {
								for (int j = i + 1; j < Data.nodeList.size(); j++)
									pickEdge.pick(i + " " + j, false);
							}
						}
					}
				} else {
					isNodeSelected = false;
				}
			}
		});

		// Edge picking
		pickEdge = vv.getPickedEdgeState();
		pickEdge.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					isEdgeSelected = true;
					String item = e.getItem().toString();
					String[] str = item.split(" ");
					Data.currentNode = Integer.parseInt(str[0]);
					Data.currentPath = Integer.parseInt(str[1]);
					Data.myUI.panelControl.updateData();
					if (!Data.runningAlgorithm) {
						// Clear all picking states
						for (int i = 0; i < Data.nodeList.size(); i++) {
							pickNode.pick(Data.nodeList.get(i), false);
						}
					}
				} else {
					isEdgeSelected = false;
				}
			}
		});

		// Mouse listener
		vv.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (Data.nodeAdded) {
					updateData();
					Node node = Data.nodeList.get(Data.currentNode);
					node.setNodeX(arg0.getX());
					node.setNodeY(arg0.getY());
					Data.currentPath = -1;
					Data.nodeAdded = false;
					pickNode.pick(node, true);
				}

				if (isNodeSelected) {
					Node node = Data.nodeList.get(Data.currentNode);
					double x = layout.transform(node).getX();
					double y = layout.transform(node).getY();
					Data.nodeList.get(Data.currentNode).setNodeX((int)x);
					Data.nodeList.get(Data.currentNode).setNodeY((int)y);
				} else if (!isEdgeSelected) {
					Data.currentNode = -1;// Random click
					Data.myUI.panelControl.updateData();
				}

				if (Data.edgeAdded) {
					// Get current end points
					Pair<Node> pair = graph
							.getEndpoints(Configuration.DEFAULT_EDGE_NAME);
					Node start = pair.getFirst();
					Node end = pair.getSecond();
					int posStart = Data.getPosition(start);
					int posEnd = Data.getPosition(end);
					if (posStart > posEnd) {
						int tmp = posStart;
						posStart = posEnd;
						posEnd = tmp;
					}
					// Change edge name
					String edgeName = posStart + " " + posEnd;
					if (graph.getEndpoints(edgeName) != null
							|| posStart == posEnd) {
						graph.removeEdge(Configuration.DEFAULT_EDGE_NAME);
					} else {
						graph.removeEdge(Configuration.DEFAULT_EDGE_NAME);
						graph.addEdge(edgeName, start, end);
						Data.edgeAdded = false;
					}
					// Update data
					Data.currentNode = posStart;
					updateData();
					Data.currentPath = posEnd;
					Data.edgeAdded = false;
					Data.myUI.panelControl.updateData();
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Data change
				Configuration.dataChange();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// updateData();
			}
		});
	}

	public void addTransformer() {
		RenderContext<Node, String> render = vv.getRenderContext();

		// Path label
		Transformer<String, String> edgeLabel = new Transformer<String, String>() {
			@Override
			public String transform(String edge) {
				// Change edgeLabel
				Pair<Node> pair = graph.getEndpoints(edge);
				int start = Data.getPosition(pair.getFirst());
				int end = Data.getPosition(pair.getSecond());
				if (Data.pathList[start][end] == 0) {
					Data.pathList[start][end] = Configuration.DEFAULT_WEIGHT;
					Data.pathList[end][start] = Configuration.DEFAULT_WEIGHT;
				}
				String edgeLabel = Data.pathList[start][end] + "";
				Data.myUI.panelControl.updateData();
				return edgeLabel;
			}

		};
		render.setEdgeLabelTransformer(edgeLabel);

		// Path color
		Transformer<String, Paint> edgeColor = new Transformer<String, Paint>() {
			@Override
			public Paint transform(String edge) {
				String color = Configuration.colorPathNormal;
				if (pickEdge.isPicked(edge))
					color = Configuration.colorPathHighlight;
				return GraphicSupport.getColor(color);
			}
		};
		render.setEdgeDrawPaintTransformer(edgeColor);

		// Path stroke
		Transformer<String, Stroke> edgeStroke = new Transformer<String, Stroke>() {
			@Override
			public Stroke transform(String edge) {
				return new BasicStroke(Configuration.pathWidth);
			}
		};
		render.setEdgeStrokeTransformer(edgeStroke);
		render.setLabelOffset((int) -Configuration.pathWidth / 2);

		// Path label color
		Color color = GraphicSupport.getColor(Configuration.colorPathHighlight);
		render.setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(color));

		// Path shape
		if (Configuration.pathStyle == 0)
			render.setEdgeShapeTransformer(new EdgeShape.Line<Node, String>());
		else if (Configuration.pathStyle == 1)
			render.setEdgeShapeTransformer(new EdgeShape.BentLine<Node, String>());
		else if (Configuration.pathStyle == 3)
			render.setEdgeShapeTransformer(new EdgeShape.CubicCurve<Node, String>());
		else
			render.setEdgeShapeTransformer(new EdgeShape.QuadCurve<Node, String>());

		// Path offset
		AbstractEdgeShapeTransformer<Node, String> aesf = (AbstractEdgeShapeTransformer<Node, String>) render
				.getEdgeShapeTransformer();
		aesf.setControlOffsetIncrement(Configuration.pathOffset);

		// Vertex label
		render.setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);

		// Vertex color
		Transformer<Node, Paint> vertexColor = new Transformer<Node, Paint>() {
			@Override
			public Paint transform(Node node) {
				String color = Configuration.colorCityNormal;
				if (pickNode.isPicked(node))
					color = Configuration.colorCityHighlight;
				return GraphicSupport.getColor(color);
			}
		};
		if (Configuration.CITY_ICON == 0)
			render.setVertexFillPaintTransformer(vertexColor);
		else
			render.setVertexFillPaintTransformer(null);

		// Vertex icon
		Transformer<Node, Icon> vertexIcon = new Transformer<Node, Icon>() {
			@Override
			public Icon transform(Node node) {
				Icon icon = null;
				try {
					String url = System.getProperty("user.dir") + "\\"
							+ Configuration.DEFAULT_IMAGE_ICON;
					Icon icNormal = GraphicSupport
							.toIcon(Configuration.imageCityNormal);
					Icon icHighlight = GraphicSupport
							.toIcon(Configuration.imageCityHighlight);

					if (icNormal == null && icHighlight == null)
						icNormal = GraphicSupport.toIcon(url);

					if (pickNode.isPicked(node)) {
						if (icHighlight == null && icNormal != null)
							icon = GraphicSupport.resize(icNormal, true);
						else
							icon = icHighlight;
					} else {
						if (icNormal == null && icHighlight != null)
							icon = GraphicSupport.resize(icHighlight, false);
						else
							icon = icNormal;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return icon;
			}
		};
		if (Configuration.CITY_ICON == 1)
			render.setVertexIconTransformer(vertexIcon);
		else
			render.setVertexIconTransformer(null);

		vv.repaint();
	}

	public void updateData() {
		int n = Data.nodeList.size();
		for (int i = 0; i < n; i++) {
			pickNode.pick(Data.nodeList.get(i), false);
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++)
				pickEdge.pick(i + " " + j, false);
		}
		if (Data.currentNode != -1)
			pickNode.pick(Data.nodeList.get(Data.currentNode), true);
		vv.repaint();
	}

	public void traverseGraph(ArrayList<Node> path) {
		Data.currentNode = -1;
		updateData();
		if (path == null || path.isEmpty()) {
			DialogSupport.InvalidWay();
			Data.myUI.panelControl.updateData();
		} else {
			int n = path.size();
			for (int i = 0; i < n - 1; i++) {
				Node nodeStart = path.get(i);
				Node nodeEnd = path.get(i + 1);
				pickNode.pick(nodeStart, true);// pick node
				int start = Data.getPosition(nodeStart);
				int end = Data.getPosition(nodeEnd);
				String edgeName = (start < end) ? (start + " " + end) : (end
						+ " " + start);
				pickEdge.pick(edgeName, true);// pick edge
			}
			pickNode.pick(path.get(n - 1), true);// last node
		}
	}

	public void loadData() {
		// City list
		int n = Data.nodeList.size();
		for (int i = 0; i < n; i++) {
			Node city = Data.nodeList.get(i);
			Point2D pos = new Point2D.Double(city.getNodeX(), city.getNodeY());
			graph.addVertex(city);
			layout.setLocation(city, pos);
		}
		// Edge list
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++) {
				if (Data.pathList[i][j] != 0) {
					Node start = Data.nodeList.get(i);
					Node end = Data.nodeList.get(j);
					int posStart = Data.getPosition(start);
					int posEnd = Data.getPosition(end);
					graph.addEdge(posStart + " " + posEnd, start, end);
				}
			}
		vv.repaint();
	}
}