package com.trimaplebot.support;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.trimaplebot.model.Configuration;

public class GraphicSupport {
	static int DEFAULT_SIZE_SHIFT = 20;
	static int DEFAULT_ICON_SIZE = 40;

	/**
	 * Change size of an icon
	 * 
	 * @param source
	 *            : the icon
	 * @param isIncrease
	 *            : increase or decrease size
	 * @return resized image icon
	 */
	public static ImageIcon resize(Icon source, boolean isIncrease) {
		int shift = isIncrease ? DEFAULT_SIZE_SHIFT : -DEFAULT_SIZE_SHIFT;
		Image img = ((ImageIcon) source).getImage();
		int width = source.getIconWidth() + shift;
		int height = source.getIconHeight() + shift;
		Image resizeImg = img.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		return new ImageIcon(resizeImg);
	}

	/**
	 * Create an icon from an url
	 * @param url: url to the icon
	 * @return the icon after resizing
	 */
	public static ImageIcon toIcon(String url) {
		Icon icon = null;
		try {
			File file = new File(url);
			if (!file.isFile())
				return null;
			
			Image img = (new ImageIcon(file.toURI().toURL())).getImage();
			img = img.getScaledInstance(DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE,
					Image.SCALE_SMOOTH);
			icon = new ImageIcon(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ImageIcon) icon;
	}
	
	public static Color getColor(String colorName){
		if (colorName.equalsIgnoreCase(Configuration.STR_COLOR[0]))
			return Color.BLUE;
		if (colorName.equalsIgnoreCase(Configuration.STR_COLOR[1]))
			return Color.CYAN;
		if (colorName.equalsIgnoreCase(Configuration.STR_COLOR[2]))
			return Color.GREEN;
		if (colorName.equalsIgnoreCase(Configuration.STR_COLOR[3]))
			return Color.MAGENTA;
		if (colorName.equalsIgnoreCase(Configuration.STR_COLOR[4]))
			return Color.ORANGE;
		if (colorName.equalsIgnoreCase(Configuration.STR_COLOR[5]))
			return Color.RED;
		if (colorName.equalsIgnoreCase(Configuration.STR_COLOR[6]))
			return Color.YELLOW;
		return Color.BLACK;
	}
}
