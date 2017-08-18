package com.trimaplebot.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.util.ArrayList;

import com.trimaplebot.model.Configuration;
import com.trimaplebot.model.Data;
import com.trimaplebot.model.Node;

public class ResultUI extends JDialog {
	JTextArea txtResult;
	JScrollPane scrollPane;
	JButton btnClear;

	public ResultUI(String title) {
		// Initialize UI
		this.pack();
		this.setTitle(title);
		this.setSize(new Dimension(Configuration.WIDTH_CONFIGURATION,
				Configuration.HEIGHT_CONFIGURATION));
		this.setLocationRelativeTo(null);
		this.setVisible(false);
		this.setResizable(false);

		// Add control
		txtResult = new JTextArea();
		scrollPane = new JScrollPane(txtResult);
		btnClear = new JButton("Clear");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				groupLayout
						.createSequentialGroup()
						.addGap(20)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(btnClear,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addGap(20)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addGap(20)
						.addComponent(scrollPane).addGap(20)
						.addComponent(btnClear).addGap(20)));
		getContentPane().setLayout(groupLayout);

		// Add event
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtResult.setText("");
			}
		});
	}

	public void showUI() {
		this.setVisible(true);
	}

	public void showResult(ArrayList<Node> path) {
		if (path != null && !path.isEmpty()) {
			String content = txtResult.getText();

			double sum = 0;
			int step = path.size();
			String result = "";
			for (int i = 0; i < path.size(); i++) {
				result = result + path.get(i).toString();
				if (i < path.size() - 1) {
					result += " -> ";
					int from = Data.nodeList.indexOf(path.get(i));
					int to = Data.nodeList.indexOf(path.get(i + 1));
					sum += Data.pathList[from][to];
				}
			}
			result += "\n";
			result = result + "- Total cost: " + sum + "\n";
			result = result + "- Total steps: " + step + "\n";
			content += "==================\n";
			content += result;
			txtResult.setText(content);
		}
	}
}
