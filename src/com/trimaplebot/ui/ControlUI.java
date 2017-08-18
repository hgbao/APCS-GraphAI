package com.trimaplebot.ui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;

import com.trimaplebot.model.Configuration;
import com.trimaplebot.model.Data;
import com.trimaplebot.model.Node;
import com.trimaplebot.support.DialogSupport;

public class ControlUI extends JPanel {

	JComboBox<String> cbMouse;

	JPanel panel_1;
	JTextField txtName;
	JTextField txtWeight;
	JComboBox<String> cbPath;
	JButton btnUpdate, btnCancel, btnDelete;

	JPanel panel_2;
	JComboBox<String> cbStart, cbEnd, cbAlgorithm;
	JButton btnTravel, btnImportHeuristic;

	private int curPath;

	public ControlUI(int width, int height) {
		this.setMinimumSize(new Dimension(width, height));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// ============== Mouse type ===============
		JPanel panel_0 = new JPanel();
		panel_0.setBorder(new TitledBorder(null, "Mouse", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(panel_0);

		JLabel lblMouseType = new JLabel("Mouse type:");

		cbMouse = new JComboBox<String>();
		GroupLayout gl_panel_0 = new GroupLayout(panel_0);
		gl_panel_0.setHorizontalGroup(gl_panel_0.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_0
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblMouseType)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cbMouse, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_0
				.setVerticalGroup(gl_panel_0
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_0
										.createSequentialGroup()
										.addGap(10)
										.addGroup(
												gl_panel_0
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblMouseType)
														.addComponent(
																cbMouse,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap(100, Short.MAX_VALUE)));
		panel_0.setLayout(gl_panel_0);

		// ========== City implement ==============
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "City implementation",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		this.add(panel_1);
		// City
		JLabel lblName = new JLabel("Name:");
		txtName = new JTextField();
		txtName.setColumns(10);
		// Path
		JLabel lblPath = new JLabel("Path:");
		cbPath = new JComboBox<String>();
		txtWeight = new JTextField();
		txtWeight.setColumns(10);
		// Button
		btnUpdate = new JButton("Update");
		btnCancel = new JButton("Cancel");
		btnDelete = new JButton("Delete");

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(lblName)
														.addComponent(lblPath))
										.addGap(20)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				btnDelete,
																				GroupLayout.DEFAULT_SIZE,
																				103,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								txtName,
																								GroupLayout.DEFAULT_SIZE,
																								217,
																								Short.MAX_VALUE)
																						.addGroup(
																								gl_panel_1
																										.createSequentialGroup()
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																btnUpdate,
																																GroupLayout.DEFAULT_SIZE,
																																103,
																																Short.MAX_VALUE)
																														.addComponent(
																																cbPath,
																																0,
																																103,
																																Short.MAX_VALUE))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																txtWeight,
																																GroupLayout.DEFAULT_SIZE,
																																108,
																																Short.MAX_VALUE)
																														.addComponent(
																																btnCancel,
																																GroupLayout.DEFAULT_SIZE,
																																108,
																																Short.MAX_VALUE))))
																		.addGap(10)))));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addGap(10)
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblName)
										.addComponent(txtName,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(txtWeight,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPath)
										.addComponent(cbPath,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(btnCancel)
										.addComponent(btnUpdate))
						.addPreferredGap(ComponentPlacement.RELATED, 300,
								Short.MAX_VALUE).addComponent(btnDelete)
						.addGap(20)));
		panel_1.setLayout(gl_panel_1);

		// ============ Finding path =============
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Finding path",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_2);
		// Journey
		JLabel lblNewLabel = new JLabel("Journey:");
		cbStart = new JComboBox<String>();
		cbEnd = new JComboBox<String>();
		// Algorithm
		JLabel lblAlgorithm = new JLabel("Algorithm:");
		cbAlgorithm = new JComboBox<String>();
		btnTravel = new JButton("Travel");

		btnImportHeuristic = new JButton("Import heuristic");

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblNewLabel)
														.addComponent(
																lblAlgorithm))
										.addGap(20)
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addComponent(
																				cbStart,
																				0,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				cbEnd,
																				0,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addComponent(
																cbAlgorithm,
																0,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																btnTravel,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																btnImportHeuristic,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_2
						.createSequentialGroup()
						.addGap(10)
						.addGroup(
								gl_panel_2
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel,
												GroupLayout.DEFAULT_SIZE, 20,
												Short.MAX_VALUE)
										.addComponent(cbStart,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(cbEnd,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_panel_2
										.createParallelGroup(Alignment.LEADING)
										.addComponent(cbAlgorithm)
										.addComponent(lblAlgorithm))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnTravel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnImportHeuristic).addGap(45)));
		panel_2.setLayout(gl_panel_2);

		initializeData();
		addEvent();
	}

	private void addEvent() {
		// ComboBox
		cbPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Data.currentNode != -1 && cbPath.getSelectedItem() != null) {
					int from = Data.currentNode;
					String path = cbPath.getSelectedItem().toString();
					curPath = Data.getPosition(path);
					if (curPath != -1)
						txtWeight.setText(Data.pathList[from][curPath] + "");
				}
			}
		});

		cbAlgorithm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = cbAlgorithm.getSelectedItem().toString();
				if (str.equalsIgnoreCase(Configuration.STR_ALGORITHM[2])) {
					btnImportHeuristic.setEnabled(true);
				} else
					btnImportHeuristic.setEnabled(false);
			}
		});

		// Buttons
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Data.currentNode != -1) {
					// Name
					String cityName = txtName.getText();
					if (Data.getPosition(cityName) == -1)
						Data.nodeList.get(Data.currentNode).setNodeName(
								cityName);
					else if (Data.getPosition(cityName) != Data.currentNode)
						DialogSupport.InvalidName();

					String strWeight = txtWeight.getText();
					if (!strWeight.isEmpty()) {
						// Weight for data
						double weight = Double.parseDouble(txtWeight.getText());
						weight = (weight < 0) ? 0 : weight;
						int from = Data.currentNode, to = curPath;
						Data.pathList[from][to] = weight;
						Data.pathList[to][from] = weight;
						updateData();
						// Weight for graph
						if (from > to) {
							int tmp = from;
							from = to;
							to = tmp;
						}
						String edgeName = from + " " + to;
						if (weight > 0) {
							if (Data.sgv.g.getEndpoints(edgeName) == null)
								Data.sgv.g.addEdge(edgeName,
										Data.nodeList.get(from),
										Data.nodeList.get(to));
						} else
							Data.sgv.g.removeEdge(edgeName);
					}

					Data.myUI.panelGraph.updateData();

					//Data change
					Configuration.dataChange();
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Data.currentNode != -1) {
					updateData();
				}
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Data.currentNode != -1) {
					// Remove node
					Node removeNode = Data.nodeList.get(Data.currentNode);
					Data.sgv.g.removeVertex(removeNode);
					Data.removeNode(Data.currentNode);
					updateData();
					Data.myUI.panelGraph.updateData();

					// Data change
					Configuration.dataChange();
				}
			}
		});
	}

	private void initializeData() {
		// Mouse mode
		for (String str : Configuration.STR_MOUSE) {
			cbMouse.addItem(str);
		}
		// Algorithm
		for (String str : Configuration.STR_ALGORITHM) {
			cbAlgorithm.addItem(str);
		}
		// Combo box size
		cbPath.setMaximumSize(cbPath.getMinimumSize());
		cbStart.setMaximumSize(cbStart.getMinimumSize());
		cbEnd.setMaximumSize(cbEnd.getMinimumSize());
	}

	public void updateData() {
		// Current city
		if (Data.currentNode != -1) {
			// Enable panel
			for (Component comp : panel_1.getComponents()) {
				comp.setEnabled(true);
			}
			// Set data
			Node node = Data.nodeList.get(Data.currentNode);
			txtName.setText(node.getNodeName());
			cbPath.removeAllItems();
			for (int i = 0; i < Data.nodeList.size(); i++) {
				Node cur = Data.nodeList.get(i);
				if (!cur.getNodeName().equalsIgnoreCase(node.getNodeName())) {
					cbPath.addItem(cur.getNodeName());
				}
			}
			if (Data.currentPath != -1) {
				String item = Data.nodeList.get(Data.currentPath).toString();
				cbPath.setSelectedItem(item);
			}
		} else {
			// Disable panel
			for (Component comp : panel_1.getComponents())
				comp.setEnabled(false);
			// Set data
			txtName.setText("");
			cbPath.removeAllItems();
			txtWeight.setText("");
		}

		// Algorithm
		if (Data.nodeList.isEmpty()) {
			for (Component comp : panel_2.getComponents())
					comp.setEnabled(false);
		} else {
			for (Component comp : panel_2.getComponents())
				if (comp != btnImportHeuristic)
					comp.setEnabled(true);
			cbStart.removeAllItems();
			cbEnd.removeAllItems();
			for (Node node : Data.nodeList) {
				cbStart.addItem(node.getNodeName());
				cbEnd.addItem(node.getNodeName());
			}
		}
	}
}