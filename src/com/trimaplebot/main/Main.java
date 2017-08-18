package com.trimaplebot.main;

import com.trimaplebot.model.Data;
import com.trimaplebot.support.FileSupport;

public class Main {
    public static void main(String[] args) {
    	FileSupport.openConfigurationFile();
		Data.myUI.showUI();
    }
}