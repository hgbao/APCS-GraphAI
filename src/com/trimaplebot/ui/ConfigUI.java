package com.trimaplebot.ui;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSlider;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.trimaplebot.model.Configuration;
import com.trimaplebot.model.Data;
import com.trimaplebot.support.FileSupport;

public class ConfigUI extends JDialog implements ActionListener, ItemListener,
		DocumentListener {
	// Previous settings
	private int CITY_ICON;
	private String colorCityNormal;
	private String colorCityHighlight;
	private String imageCityNormal;
	private String imageCityHighlight;

	private int pathStyle;
	private int pathOffset;
	private float pathWidth;
	private String colorPathNormal;
	private String colorPathHighlight;

	// Controls
	private JRadioButton radioCityDefault, radioCityIcon;
	private JLabel lblCityNormal, lblCityHighlight;
	private JComboBox<String> cbCityNormal, cbCityHighlight;
	private JTextField txtCityNormal, txtCityHighlight;
	private JButton btnCityNormal, btnCityHighlight;

	private JComboBox<String> cbPathStyle;
	private JComboBox<String> cbPathNormal, cbPathHighlight;
	private JSlider sliderPathOffset, sliderPathWidth;
	private JButton btnOk, btnCancel;

	public ConfigUI(String title) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);

		// Previous settings
		this.CITY_ICON = Configuration.CITY_ICON;
		this.colorCityNormal = Configuration.colorCityNormal;
		this.colorCityHighlight = Configuration.colorCityHighlight;
		this.imageCityNormal = Configuration.imageCityNormal;
		this.imageCityHighlight = Configuration.imageCityHighlight;

		this.pathStyle = Configuration.pathStyle;
		this.pathOffset = Configuration.pathOffset;
		this.pathWidth = Configuration.pathWidth;
		this.colorPathNormal = Configuration.colorPathNormal;
		this.colorPathHighlight = Configuration.colorCityHighlight;

		getContentPane().setLayout(null);

		// ============== City configuration ===============
		JPanel panel_0 = new JPanel();
		panel_0.setBounds(0, 0, 484, 125);
		panel_0.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "City",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		getContentPane().add(panel_0);

		JPanel subPanel_01 = new JPanel();
		subPanel_01.setBorder(null);
		subPanel_01.setLayout(null);

		JLabel lblIcon = new JLabel("Icon");
		lblIcon.setBounds(30, 0, 40, 15);
		lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		subPanel_01.add(lblIcon);

		radioCityDefault = new JRadioButton("Default");
		radioCityDefault.setBounds(5, 30, 100, 20);
		subPanel_01.add(radioCityDefault);

		radioCityIcon = new JRadioButton("Image icon");
		radioCityIcon.setBounds(5, 70, 100, 20);
		subPanel_01.add(radioCityIcon);

		JPanel subPanel_02 = new JPanel();
		subPanel_02.setBorder(new MatteBorder(0, 1, 0, 1,
				(Color) SystemColor.inactiveCaption));
		subPanel_02.setLayout(null);

		JLabel lblSelect = new JLabel("Normal");
		lblSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelect.setBounds(70, 0, 40, 15);
		lblSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
		subPanel_02.add(lblSelect);

		lblCityNormal = new JLabel("Color:");
		lblCityNormal.setBounds(5, 30, 40, 20);
		subPanel_02.add(lblCityNormal);

		cbCityNormal = new JComboBox<String>();
		cbCityNormal.setBounds(45, 30, 126, 20);
		subPanel_02.add(cbCityNormal);

		txtCityNormal = new JTextField();
		txtCityNormal.setBounds(5, 70, 80, 20);
		subPanel_02.add(txtCityNormal);
		txtCityNormal.setColumns(10);

		btnCityNormal = new JButton("Browse");
		btnCityNormal.setBounds(90, 70, 80, 20);
		subPanel_02.add(btnCityNormal);

		JPanel subPanel_03 = new JPanel();
		subPanel_03.setLayout(null);

		JLabel lblDeselect = new JLabel("Highlight");
		lblDeselect.setBounds(65, 0, 50, 15);
		subPanel_03.add(lblDeselect);
		lblDeselect.setHorizontalAlignment(SwingConstants.CENTER);

		lblCityHighlight = new JLabel("Color:");
		lblCityHighlight.setBounds(5, 30, 40, 20);
		subPanel_03.add(lblCityHighlight);

		cbCityHighlight = new JComboBox<String>();
		cbCityHighlight.setBounds(45, 30, 126, 20);
		subPanel_03.add(cbCityHighlight);

		txtCityHighlight = new JTextField();
		txtCityHighlight.setColumns(10);
		txtCityHighlight.setBounds(5, 70, 80, 20);
		subPanel_03.add(txtCityHighlight);

		btnCityHighlight = new JButton("Browse");
		btnCityHighlight.setBounds(90, 70, 85, 20);
		subPanel_03.add(btnCityHighlight);
		GroupLayout gl_panel_0 = new GroupLayout(panel_0);
		gl_panel_0.setHorizontalGroup(gl_panel_0.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_0
						.createSequentialGroup()
						.addComponent(subPanel_01, GroupLayout.PREFERRED_SIZE,
								110, GroupLayout.PREFERRED_SIZE)
						.addComponent(subPanel_02, GroupLayout.PREFERRED_SIZE,
								180, GroupLayout.PREFERRED_SIZE)
						.addComponent(subPanel_03, GroupLayout.PREFERRED_SIZE,
								180, GroupLayout.PREFERRED_SIZE)));
		gl_panel_0.setVerticalGroup(gl_panel_0
				.createParallelGroup(Alignment.LEADING)
				.addComponent(subPanel_01, GroupLayout.PREFERRED_SIZE, 99,
						GroupLayout.PREFERRED_SIZE)
				.addComponent(subPanel_02, GroupLayout.PREFERRED_SIZE, 99,
						GroupLayout.PREFERRED_SIZE)
				.addComponent(subPanel_03, GroupLayout.PREFERRED_SIZE, 99,
						GroupLayout.PREFERRED_SIZE));
		panel_0.setLayout(gl_panel_0);

		// ============== Path configuration ===============
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 125, 484, 170);
		panel_1.setBorder(new TitledBorder(null, "Path", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(panel_1);

		JPanel subPanel_11 = new JPanel();
		subPanel_11.setBounds(6, 16, 234, 146);
		subPanel_11.setLayout(null);
		subPanel_11.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(
				153, 180, 209)));

		JLabel lblPathStyle = new JLabel("Style");
		lblPathStyle.setHorizontalAlignment(SwingConstants.CENTER);
		lblPathStyle.setAlignmentX(0.5f);
		lblPathStyle.setBounds(95, 0, 50, 15);
		subPanel_11.add(lblPathStyle);

		JPanel subPanel_12 = new JPanel();
		subPanel_12.setBounds(240, 16, 234, 146);
		subPanel_12.setLayout(null);

		JLabel lblColor = new JLabel("Color");
		lblColor.setHorizontalAlignment(SwingConstants.CENTER);
		lblColor.setBounds(95, 0, 50, 15);
		subPanel_12.add(lblColor);

		JLabel lblSelect_1 = new JLabel("Normal:");
		lblSelect_1.setBounds(5, 30, 60, 20);
		subPanel_12.add(lblSelect_1);

		cbPathNormal = new JComboBox<String>();
		cbPathNormal.setBounds(65, 30, 150, 20);
		subPanel_12.add(cbPathNormal);

		cbPathHighlight = new JComboBox<String>();
		cbPathHighlight.setBounds(65, 70, 150, 20);
		subPanel_12.add(cbPathHighlight);

		JLabel lblDeselect_1 = new JLabel("Highlight:");
		lblDeselect_1.setBounds(5, 70, 60, 20);
		subPanel_12.add(lblDeselect_1);
		panel_1.setLayout(null);
		panel_1.add(subPanel_11);

		JLabel lblStyle = new JLabel("Style:");
		lblStyle.setBounds(10, 30, 60, 20);
		subPanel_11.add(lblStyle);

		JLabel lblOffset = new JLabel("Offset:");
		lblOffset.setBounds(10, 70, 60, 20);
		subPanel_11.add(lblOffset);

		cbPathStyle = new JComboBox<String>();
		cbPathStyle.setBounds(65, 30, 150, 20);
		subPanel_11.add(cbPathStyle);

		sliderPathOffset = new JSlider();
		sliderPathOffset.setBounds(65, 70, 150, 26);
		subPanel_11.add(sliderPathOffset);

		JLabel lblLineWidth = new JLabel("Width:");
		lblLineWidth.setBounds(10, 110, 60, 20);
		subPanel_11.add(lblLineWidth);

		sliderPathWidth = new JSlider();
		sliderPathWidth.setBounds(65, 110, 150, 26);
		subPanel_11.add(sliderPathWidth);
		panel_1.add(subPanel_12);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 321, 484, 39);
		getContentPane().add(panel_2);

		// ============== Confirm buttons ===============
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_2
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnOk, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(5)
						.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_2
						.createSequentialGroup()
						.addGap(5)
						.addGroup(
								gl_panel_2
										.createParallelGroup(Alignment.LEADING)
										.addComponent(btnOk)
										.addComponent(btnCancel))
						.addContainerGap()));
		panel_2.setLayout(gl_panel_2);

		// Add data and event
		initializeData();
		addEvent();
	}

	public void showUI() {
		this.pack();
		this.setSize(new Dimension(Configuration.WIDTH_CONFIGURATION,
				Configuration.HEIGHT_CONFIGURATION));
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void initializeData() {
		// City icon event - Move to this in order to listen for the initial
		// choice
		radioCityDefault.addItemListener(this);
		radioCityIcon.addItemListener(this);

		// City icon type
		ButtonGroup group = new ButtonGroup();
		group.add(radioCityDefault);
		group.add(radioCityIcon);
		if (Configuration.CITY_ICON == 0)
			radioCityDefault.setSelected(true);
		else
			radioCityIcon.setSelected(true);

		// Path style
		for (String str : Configuration.STR_PATH_STYLE) {
			cbPathStyle.addItem(str);
		}
		cbPathStyle.setSelectedIndex(Configuration.pathStyle);

		// Path offset
		sliderPathOffset.setMinimum(-50);
		sliderPathOffset.setMaximum(50);
		sliderPathOffset.setValue(Configuration.pathOffset);

		// Path width
		sliderPathWidth.setMinimum(10);
		sliderPathWidth.setMaximum(100);
		sliderPathWidth.setValue((int) (Configuration.pathWidth * 10));

		// Color group
		for (String str : Configuration.STR_COLOR) {
			cbCityNormal.addItem(str);
			cbCityHighlight.addItem(str);
			cbPathNormal.addItem(str);
			cbPathHighlight.addItem(str);
		}
		cbCityNormal.setSelectedItem(Configuration.colorCityNormal);
		cbCityHighlight.setSelectedItem(Configuration.colorCityHighlight);
		cbPathNormal.setSelectedItem(Configuration.colorPathNormal);
		cbPathHighlight.setSelectedItem(Configuration.colorPathHighlight);

		// Icons path
		txtCityNormal.setText(Configuration.imageCityNormal);
		txtCityHighlight.setText(Configuration.imageCityHighlight);
	}

	private void addEvent() {
		// ComboBox listener
		cbCityNormal.addActionListener(this);
		cbCityHighlight.addActionListener(this);
		cbPathNormal.addActionListener(this);
		cbPathHighlight.addActionListener(this);
		cbPathStyle.addActionListener(this);

		// Icon browsing buttons
		btnCityNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String imgPath = FileSupport.openImageFile();
				Configuration.imageCityNormal = imgPath;
				txtCityNormal.setText(imgPath);

				Data.myUI.panelGraph.addTransformer();
			}
		});
		btnCityHighlight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String imgPath = FileSupport.openImageFile();
				Configuration.imageCityHighlight = imgPath;
				txtCityHighlight.setText(imgPath);

				Data.myUI.panelGraph.addTransformer();
			}
		});

		// Edge offset slider
		sliderPathOffset.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Configuration.pathOffset = sliderPathOffset.getValue();
				Data.myUI.panelGraph.addTransformer();
			}
		});

		// Edge width slider
		sliderPathWidth.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Configuration.pathWidth = (float) (sliderPathWidth.getValue() * 1.0 / 10);
				Data.myUI.panelGraph.addTransformer();
			}
		});

		// Text change listener
		txtCityNormal.getDocument().addDocumentListener(this);
		txtCityHighlight.getDocument().addDocumentListener(this);

		// Confirm buttons
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileSupport.saveConfigurationFile();
				setVisible(false);
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelChanges();
				setVisible(false);
				dispose();
			}
		});
		
		//Close button
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				cancelChanges();
				setVisible(false);
				dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});
	}

	private void cancelChanges() {
		// Previous settings
		Configuration.CITY_ICON = this.CITY_ICON;
		Configuration.colorCityNormal = this.colorCityNormal;
		Configuration.colorCityHighlight = this.colorCityHighlight;
		Configuration.imageCityNormal = this.imageCityNormal;
		Configuration.imageCityHighlight = this.imageCityHighlight;

		Configuration.pathStyle = this.pathStyle;
		Configuration.pathOffset = this.pathOffset;
		Configuration.pathWidth = this.pathWidth;
		Configuration.colorPathNormal = this.colorPathNormal;
		Configuration.colorCityHighlight = this.colorPathHighlight;

		Data.myUI.panelGraph.addTransformer();
	}

	public void actionPerformed(ActionEvent e) {// Only for JComboBox
		String choice = ((JComboBox) e.getSource()).getSelectedItem()
				.toString();
		if (e.getSource() == cbCityNormal) {
			Configuration.colorCityNormal = choice;
		}
		if (e.getSource() == cbCityHighlight) {
			Configuration.colorCityHighlight = choice;
		}
		if (e.getSource() == cbPathNormal) {
			Configuration.colorPathNormal = choice;
		}
		if (e.getSource() == cbPathHighlight) {
			Configuration.colorPathHighlight = choice;
		}
		if (e.getSource() == cbPathStyle) {
			int i = ((JComboBox) e.getSource()).getSelectedIndex();
			Configuration.pathStyle = i;
		}
		Data.myUI.panelGraph.addTransformer();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {// Only for radio buttons
		boolean flag;
		int state;
		if (e.getStateChange() == 1) {
			state = 0;
			flag = false;
		} else {
			state = 1;
			flag = true;
		}

		if (e.getSource() == radioCityIcon) {
			flag = !flag;
			state = -state + 1;
		}

		Configuration.CITY_ICON = state;

		txtCityNormal.setEnabled(flag);
		btnCityNormal.setEnabled(flag);
		txtCityHighlight.setEnabled(flag);
		btnCityHighlight.setEnabled(flag);

		lblCityNormal.setEnabled(!flag);
		lblCityHighlight.setEnabled(!flag);
		cbCityNormal.setEnabled(!flag);
		cbCityHighlight.setEnabled(!flag);

		Data.myUI.panelGraph.addTransformer();
	}

	// Text change listeners
	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {// Similarly to removeUpdate
		if (e.getDocument() == txtCityNormal.getDocument()) {
			Configuration.imageCityNormal = txtCityNormal.getText();
		} else if (e.getDocument() == txtCityHighlight.getDocument()) {
			Configuration.imageCityHighlight = txtCityHighlight.getText();
		}
		Data.myUI.panelGraph.addTransformer();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {// Similarly to insertUpdate
		if (e.getDocument() == txtCityNormal.getDocument()) {
			Configuration.imageCityNormal = txtCityNormal.getText();
		} else if (e.getDocument() == txtCityHighlight.getDocument()) {
			Configuration.imageCityHighlight = txtCityHighlight.getText();
		}
		Data.myUI.panelGraph.addTransformer();
	}
}