package org.iMage.iCatcher;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;

public class ICatcherWindow extends JFrame {
	private GridLayout globalLayout = new GridLayout(2, 6);
	private FlowLayout topButtons = new FlowLayout();
	private JButton showCurve = new JButton();
	private JButton saveCurve = new JButton();
	private JButton saveHDR = new JButton();
	
	public ICatcherWindow() {
		super("iCatcher");
		setLayout(globalLayout);
		//original pictures
		add(new Label("original"),0);
		//preview for the HDR-picture
		add(new Label("preview"), 1);
		//slider
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(10);
		slider.setMaximum(100);
		slider.setMinimum(0);
		add(slider, 2);
		//buttons
		JPanel buttons = new JPanel(topButtons);
		showCurve.setText("SHOW CURVE");
		saveCurve.setText("SAVE CURVE");
		saveHDR.setText("SAVE HDR");
		add(buttons, 3);
	}
}