package com.trimaplebot.ui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import com.trimaplebot.algorithm.IDS;
import com.trimaplebot.algorithm.UCS;
import com.trimaplebot.algorithm.astar.AStar;
import com.trimaplebot.algorithm.astar.GraphAStar;
import com.trimaplebot.model.Configuration;
import com.trimaplebot.model.Data;
import com.trimaplebot.model.Node;
import com.trimaplebot.support.DialogSupport;
import com.trimaplebot.support.FileSupport;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

public class MainUI extends JFrame {
	int width, height;

	public ControlUI panelControl;
	public JTabbedPane panelTab;
	public GraphUI panelGraph;
	
	public ResultUI dialogResult;

	private JMenuBar menuBar;
	private JMenuItem mntmUndirected;
	private JMenuItem mntmOpen, mntmSave, mntmSaveAs, mntmExit;
	private JMenuItem mntmConfig, mntmResult;

	// Constructor
	public MainUI(String title) {
		this.setTitle(title);
		this.width = Configuration.WIDTH_MAIN;
		this.height = Configuration.HEIGHT_MAIN;

		this.addMenu();
		this.addMenuEvent();
		this.addControl();
		this.addEvent();
	}

	// UI displaying
	public void showUI() {
		this.setMinimumSize(new Dimension(width, height));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// to have a confim
															// dialog
		this.setResizable(true);
		this.setVisible(true);
	}

	private void addMenu() {
		// Menu File
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnNew = new JMenu("New");
		mnFile.add(mnNew);
		mntmUndirected = new JMenuItem("Undirected graph");
		mnNew.add(mntmUndirected);
		mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSaveAs = new JMenuItem("Save as");
		mnFile.add(mntmSaveAs);
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		// Menu option
		JMenu mnOption = new JMenu("Option");
		menuBar.add(mnOption);

		mntmConfig = new JMenuItem("Configuration");
		mnOption.add(mntmConfig);
		mntmResult = new JMenuItem("Result panel");
		mnOption.add(mntmResult);

		// Menu shortcut key
		KeyStroke ctrlU = KeyStroke.getKeyStroke(KeyEvent.VK_U, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		mntmUndirected.setAccelerator(ctrlU);

		KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		mntmOpen.setAccelerator(ctrlO);

		KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		mntmSave.setAccelerator(ctrlS);

	}

	private void addMenuEvent() {
		// Menu File
		mntmUndirected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Configuration.isChanging || DialogSupport.ConfirmExit()) {
					Data.recoverDefault();
					panelControl.updateData();
					panelGraph.updateData();
					panelTab.setTitleAt(0, Configuration.DEFAULT_FILE_NAME);
					Configuration.isChanging = false;
				}
			}
		});

		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Configuration.isChanging || DialogSupport.ConfirmExit()) {
					String fileName = FileSupport.openDataFile();
					if (!fileName.isEmpty()) {
						panelTab.setTitleAt(0, fileName);
						Configuration.isChanging = false;
					}
				}
			}
		});

		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fileName = FileSupport.saveDataFile(false);
				if (!fileName.isEmpty()) {
					panelTab.setTitleAt(0, fileName);
					// Data saved
					Configuration.isChanging = false;
				}
			}
		});
		
		mntmSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fileName = FileSupport.saveDataFile(true);
				if (!fileName.isEmpty()) {
					panelTab.setTitleAt(0, fileName);
					// Data saved
					Configuration.isChanging = false;
				}
			}
		});

		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Configuration.isChanging || DialogSupport.ConfirmExit()) {
					System.exit(0);
				}
			}
		});

		// Menu option
		mntmConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigUI configuration = new ConfigUI("Configuration");
				configuration.showUI();
			}
		});
		
		mntmResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogResult.showUI();
			}
		});
	}

	private void addControl() {
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		// Split pane
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setDividerLocation(0.75);
		splitPane.setDividerSize(0);
		getContentPane().add(splitPane);

		panelControl = new ControlUI(width / 3, height);
		splitPane.setRightComponent(panelControl);

		panelTab = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(panelTab);

		panelGraph = new GraphUI(width * 2 / 3, height);
		panelTab.addTab(Configuration.DEFAULT_FILE_NAME, null, panelGraph, null);
		
		dialogResult = new ResultUI("Result");

		// Data
		panelControl.updateData();
	}

	private void addEvent() {
		// Mouse control
		panelControl.cbMouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String) panelControl.cbMouse.getSelectedItem();

				if (item.equals(Configuration.STR_MOUSE[0])) {
					panelGraph.gm.setMode(Mode.EDITING);
				} else if (item.equals(Configuration.STR_MOUSE[1])) {
					panelGraph.gm.setMode(Mode.PICKING);
				} else {
					panelGraph.gm.setMode(Mode.TRANSFORMING);
				}
			}
		});

		// Travel control
		panelControl.btnTravel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.runningAlgorithm = true;

				String item = panelControl.cbAlgorithm.getSelectedItem()
						.toString();
				String strStart = panelControl.cbStart.getSelectedItem()
						.toString();
				int start = Data.getPosition(strStart);
				String strEnd = panelControl.cbEnd.getSelectedItem().toString();
				int end = Data.getPosition(strEnd);

				ArrayList<Node> path = null;
				if (item.equals(Configuration.STR_ALGORITHM[0])) {
					UCS ucs = new UCS(Data.nodeList, Data.pathList);
					path = ucs.search(start, end);
					panelGraph.traverseGraph(path);

				} else if (item.equals(Configuration.STR_ALGORITHM[1])) {
					IDS ids = new IDS(Data.nodeList, Data.pathList);
					path = ids.search(start, end);
					panelGraph.traverseGraph(path);

				} else {
					//Create heuristic map
					HashMap<Node, HashMap<Node, Double>> heuristicMap = new HashMap<Node, HashMap<Node,Double>>();
					int n = Data.nodeList.size();
					for (int i = 0; i < n; i++){
						HashMap<Node, Double> tmp = new HashMap<Node, Double>();
						for (int j = 0; j < n; j++){
							tmp.put(Data.nodeList.get(j), Data.heuristic[i][j]);
						}
						heuristicMap.put(Data.nodeList.get(i), tmp);
					}
					//Create graph and add data
					GraphAStar graphAStar = new GraphAStar(heuristicMap);
					for (int i = 0; i < n; i++){
						graphAStar.addNode(Data.nodeList.get(i));
					}
					for (int i = 0; i < n; i++){
						for (int j = 0; j < n; j++){
							if (Data.pathList[i][j] != 0)
								graphAStar.addEdge(Data.nodeList.get(i), Data.nodeList.get(j), Data.pathList[i][j]);
						}
					}
					//Create algorithm
					AStar<Node> astar = new AStar<Node>(graphAStar);
					path = astar.search(Data.nodeList.get(start), Data.nodeList.get(end));
					panelGraph.traverseGraph(path);
				}
				
				//Output the way and sum
				if (path != null && !path.isEmpty()) {
					dialogResult.showResult(path);
				}

				Data.runningAlgorithm = false;
			}
		});
		
		panelControl.btnImportHeuristic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HeuristicUI heuristic = new HeuristicUI("Heuristic import");
				heuristic.showUI();
			}
		});

		// Close without saving
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (!Configuration.isChanging || DialogSupport.ConfirmExit()) {
					System.exit(0);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}
