package com.trimaplebot.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.trimaplebot.model.Configuration;
import com.trimaplebot.model.Data;
import com.trimaplebot.model.Node;

public class FileSupport {
	/**
	 * Create file chooser for open file, save file
	 * 
	 * @param type
	 *            : button name
	 * @return file selected
	 */
	private static File createFileChooser(FileNameExtensionFilter filter,
			String button) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		chooser.setFileFilter(filter);
		chooser.setApproveButtonText(button);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile();
		return null;
	}

	/**
	 * Open a data file
	 * 
	 * @return name of the file opened
	 */
	public static String openDataFile() {
		String fileName = "";
		String content = "";
		// Open file
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Trimaple Bot", Configuration.DEFAULT_FILE_EXTENSION);
		File file = createFileChooser(filter, "Open");
		if (file != null && file.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				content = sb.toString();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Convert data
			boolean result = readData(content);
			if (result) {
				Configuration.currentFileDir = file.getAbsolutePath();
				Data.myUI.panelControl.updateData();
				Data.myUI.panelGraph.loadData();
				fileName = file.getName();
			} else {
				DialogSupport.InvalidData();
			}
		}
		return fileName;
	}

	/**
	 * Convert data read from the file to the program
	 * 
	 * @param content
	 *            : data read from the file
	 * @return a boolean value to know is the data legal or not
	 */
	private static boolean readData(String content) {
		// Remove all data
		Data.recoverDefault();
		// Read new data
		String[] lines = content.split("\n");
		if (!lines[0].equalsIgnoreCase(Configuration.DEFAULT_FILE_HEADER)) {
			return false;
		}
		try {
			// Number of cities
			int n = Integer.parseInt(lines[1]);
			int cur = 2;
			// City list
			for (int i = 0; i < n; i++) {
				String name = lines[cur++];
				String[] tmp = lines[cur++].split(" ");
				Data.nodeList.add(new Node(name, Integer.parseInt(tmp[0]),
						Integer.parseInt(tmp[1])));
			}
			// Path list
			String strPath = "";
			for (int i = 0; i < n; i++){
				strPath = strPath + lines[cur++] + "\n";
			}
			Data.pathList = MatrixSupport.toMatrix(strPath, n);
			//Heuristic
			String strHeuristic = "";
			for (int i = 0; i < n; i++){
				strHeuristic = strHeuristic + lines[cur++] + "\n";
			}
			Data.heuristic = MatrixSupport.toMatrix(strHeuristic, n);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Save data of the program to a file
	 * 
	 * @return name of the saved file
	 */
	public static String saveDataFile(boolean isSaveAs) {
		String fileName = "";
		String content = writeData();

		// Save file
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Trimaple Bot", Configuration.DEFAULT_FILE_EXTENSION);
		String extension = "." + Configuration.DEFAULT_FILE_EXTENSION;

		// Determine save or save as
		File file = new File(Configuration.currentFileDir);
		if (!file.isFile() || isSaveAs)
			file = createFileChooser(filter, "Save");

		// Save file
		if (file != null) {
			try {
				String path = file.getAbsolutePath();
				if (!path.contains(extension))
					path += extension;

				FileWriter writer = new FileWriter(path);
				writer.write(content);
				writer.close();

				fileName = file.getName().contains(extension) ? file.getName()
						: (file.getName() + extension);
				Configuration.currentFileDir = file.getAbsolutePath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	/**
	 * Convert data of a program to string
	 * 
	 * @return string represented all data
	 */
	private static String writeData() {
		StringBuilder sb = new StringBuilder();
		int n = Data.nodeList.size();
		// Header
		sb.append(Configuration.DEFAULT_FILE_HEADER + "\n");
		sb.append(n + "\n");
		// City list
		for (int i = 0; i < n; i++) {
			Node city = Data.nodeList.get(i);
			sb.append(city.getNodeName() + "\n");
			sb.append(city.getNodeX() + " " + city.getNodeY() + "\n");
		}
		// Path list
		sb.append(MatrixSupport.toString(Data.pathList) + "\n");
		//Heuristic
		sb.append(MatrixSupport.toString(Data.heuristic));
		return sb.toString();
	}

	/**
	 * Open an image file
	 * 
	 * @return path directory to the file
	 */
	public static String openImageFile() {
		String path = "";
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Image file", "jpg", "png");
		File file = createFileChooser(filter, "Open");
		if (file != null && file.isFile()) {
			path = file.getAbsolutePath();
		}
		return path;
	}

	/**
	 * Open the configuration file to get data
	 */
	public static void openConfigurationFile() {
		String url = System.getProperty("user.dir") + "\\"
				+ Configuration.CONFIGURATION_FILE;
		File file = new File(url);
		String content = "";
		if (file.isFile() && file.canRead()) {
			try {
				// Read the file
				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();

				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				content = sb.toString();

				// Convert the file data
				String[] config = content.split("\n");
				Configuration.CITY_ICON = Integer.parseInt(config[0]);
				Configuration.colorCityNormal = config[1];
				Configuration.colorCityHighlight = config[2];
				Configuration.imageCityNormal = config[3];
				Configuration.imageCityHighlight = config[4];

				Configuration.pathStyle = Integer.parseInt(config[5]);
				Configuration.pathOffset = Integer.parseInt(config[6]);
				Configuration.colorPathNormal = config[7];
				Configuration.colorPathHighlight = config[8];

				br.close();
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// If there is no valid file, save a new one
		DialogSupport.InvalidConfig();
		saveConfigurationFile();
	}

	/**
	 * Save current configuration data to a file
	 */
	public static void saveConfigurationFile() {
		// Create data
		StringBuilder sb = new StringBuilder();
		sb.append(Configuration.CITY_ICON + "\n");
		sb.append(Configuration.colorCityNormal + "\n");
		sb.append(Configuration.colorCityHighlight + "\n");
		sb.append(Configuration.imageCityNormal + "\n");
		sb.append(Configuration.imageCityHighlight + "\n");

		sb.append(Configuration.pathStyle + "\n");
		sb.append(Configuration.pathOffset + "\n");
		sb.append(Configuration.colorPathNormal + "\n");
		sb.append(Configuration.colorPathHighlight + "\n");

		// Write to file
		String url = System.getProperty("user.dir") + "\\"
				+ Configuration.CONFIGURATION_FILE;
		File file = new File(url);
		try {
			FileWriter writer = new FileWriter(file.getAbsolutePath());
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
