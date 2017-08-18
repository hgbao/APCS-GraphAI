package com.trimaplebot.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.trimaplebot.model.Configuration;
import com.trimaplebot.model.Data;


public class HeuristicUI extends JDialog {
	private JTable table;

	private JPanel panel_0, panel_1;
	private JButton btnUpdate, btnCancel;
	private JScrollPane scrollPane;

	public HeuristicUI(String title) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle(title);
		this.setSize(new Dimension(500, 400));

		// Layout
		getContentPane().setLayout(null);

		panel_0 = new JPanel();
		panel_0.setBounds(0, 0, 484, 314);
		getContentPane().add(panel_0);
						panel_0.setLayout(new BoxLayout(panel_0, BoxLayout.X_AXIS));
						
						scrollPane = new JScrollPane();
						panel_0.add(scrollPane);

		panel_1 = new JPanel();
		panel_1.setBounds(0, 325, 484, 36);
		getContentPane().add(panel_1);

		btnUpdate = new JButton("Update");
		btnCancel = new JButton("Cancel");

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnUpdate, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(5)
						.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addGap(5)
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(btnUpdate)
										.addComponent(btnCancel))
						.addContainerGap()));
		panel_1.setLayout(gl_panel_1);

		initializeData();
		addEvent();
	}

	public void showUI() {
		this.pack();
		this.setSize(new Dimension(Configuration.WIDTH_HEURISTIC,
				Configuration.HEIGHT_HEURISTIC));
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void initializeData() {
		//Header as the name of all cities
		int n = Data.nodeList.size();
		String[] header = new String[n];
		for (int i = 0; i < n; i++) {
			header[i] = Data.nodeList.get(i).toString();
		}
		
		//Model for row header
		ListModel<String> lm = new AbstractListModel<String>() {
			@Override
			public String getElementAt(int index) {
				return header[index];
			}
			@Override
			public int getSize() {
				return header.length;
			}
		};
		
		DefaultTableModel dm = new DefaultTableModel(lm.getSize(), header.length);
		table = new JTable(dm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		for (int i = 0; i < header.length; i++){
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setHeaderValue(header[i]);
		}
		
		//Create row Header
		JList<String> rowHeader = new JList<String>(lm);
		rowHeader.setFixedCellWidth(50);
		rowHeader.setFixedCellHeight(30);
		
		//Set renderer
		rowHeader.setCellRenderer(new RowRenderer(table));
		rowHeader.setBackground(scrollPane.getBackground());
		
		//JScrollPane
		scrollPane.getViewport().add(table);
		scrollPane.setRowHeaderView(rowHeader);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Fill data
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++)
				table.getModel().setValueAt(Data.heuristic[i][j] + "", i, j);
		}
	}

	private void addEvent() {
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Change data
				int n = Data.nodeList.size();
				for (int i = 0; i < n; i++){
					for (int j = 0; j < n; j++){
						String strValue = table.getModel().getValueAt(i, j).toString();
						double value = Double.parseDouble(strValue);
						Data.heuristic[i][j] = value;
					}
				}
				Configuration.dataChange();
				//Close
				setVisible(false);
				dispose();
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Close
				setVisible(false);
				dispose();
			}
		});
	}
	
	class RowRenderer extends JLabel implements ListCellRenderer<String>{
		
		public RowRenderer(JTable table){
			setHorizontalAlignment(CENTER);
			
			JTableHeader header = table.getTableHeader();
			setOpaque(true);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setForeground(header.getForeground());
			setBackground(header.getBackground());
			setFont(header.getFont());
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends String> list, String value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setText(value);
			return this;
		}
		
	}
}
