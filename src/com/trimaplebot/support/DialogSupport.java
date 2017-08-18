package com.trimaplebot.support;

import javax.swing.JOptionPane;

public class DialogSupport {
	
	private static void createErrorDialog(String message){
		JOptionPane.showMessageDialog(null, message, "Error!",
				JOptionPane.ERROR_MESSAGE);
	}
	
	private static boolean createConfirmDialog(String message){
		int option = JOptionPane.showConfirmDialog(null, message, "Confirm",
				JOptionPane.OK_CANCEL_OPTION);
		return (option == JOptionPane.OK_OPTION);
	}
	
	private static void createInformationDialog(String message){
		JOptionPane.showMessageDialog(null, message, "Success!",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void InvalidName() {
		createErrorDialog("Name already exists");
	}

	public static void InvalidData() {
		createErrorDialog("Invalid data content");
	}

	public static void InvalidConfig() {
		createErrorDialog("Invalid config data");
	}
	
	public static void InvalidWay(){
		createErrorDialog("No way");
	}
	
	public static boolean ConfirmExit(){
		return createConfirmDialog("Continue without saving?");
	}
}
