package org.iMage.iCatcher;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ImageHDRWindow extends JFrame {
	private BufferedImage bufferedImage;
	
	public ImageHDRWindow(BufferedImage bufferedImage, String prefix) {
		super(prefix + "_HDR");
		this.bufferedImage = bufferedImage;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(new JLabel(new ImageIcon(bufferedImage)));
		add(scrollPane);
	}

}
