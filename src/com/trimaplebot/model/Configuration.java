package com.trimaplebot.model;

public class Configuration {
	//Window size
	public static int WIDTH_MAIN = 800;
	public static int HEIGHT_MAIN = 600;
	
	public static int WIDTH_CONFIGURATION = 500;
	public static int HEIGHT_CONFIGURATION = 400;
	
	public static int WIDTH_HEURISTIC = 500;
	public static int HEIGHT_HEURISTIC = 400;
	
	public static boolean isChanging = false;
	
	// File configuration
	public static String DEFAULT_FILE_HEADER = "trimaplebot-cs420final";
	public static String DEFAULT_FILE_NAME = "NewMap";
	public static String DEFAULT_FILE_EXTENSION = "maptb";
	public static String CONFIGURATION_FILE = "trimaplebot.config";
	public static String DEFAULT_IMAGE_ICON = "cityIcon.png";
	public static String currentFileDir = "";

	// Default data
	public static double DEFAULT_WEIGHT = 1.0;
	public static String DEFAULT_NODE_NAME = "City ";
	public static String DEFAULT_EDGE_NAME = "DEFAULT_EDGE";

	// Default options
	public static String[] STR_MOUSE = new String[] { "Editing", "Picking",
			"Transforming" };
	public static String[] STR_ALGORITHM = new String[] { "UCS", "IDS", "A*" };

	// Configuration
	public static String[] STR_COLOR = new String[] { "Blue", "Cyan", "Green",
			"Magenta", "Orange", "Red", "Yellow", "Black" };
	public static String[] STR_PATH_STYLE = new String[] { "Line", "Bent Line",
			"Quad Curve", "Cubic Curve" };

	public static int CITY_ICON = 0; // 0: Color; 1: Image
	public static String colorCityNormal = "Yellow";
	public static String colorCityHighlight = "Red";
	public static String imageCityNormal = "";
	public static String imageCityHighlight = "";

	public static int pathStyle = 2;
	public static int pathOffset = 20;
	public static float pathWidth = 1.0f;
	public static String colorPathNormal = "Black";
	public static String colorPathHighlight = "Red";
	
	//Data change listener
	public static void dataChange(){
		if (!Configuration.isChanging) {
			Configuration.isChanging = true;
			String title = Data.myUI.panelTab.getTitleAt(0);
			Data.myUI.panelTab.setTitleAt(0, title + "*");
		}
	}
}
