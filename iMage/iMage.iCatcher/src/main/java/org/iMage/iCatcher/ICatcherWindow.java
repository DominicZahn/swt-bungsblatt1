package org.iMage.iCatcher;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollBar;

public class ICatcherWindow extends JFrame {
	private GridLayout globalLayout = new GridLayout(2, 6);
	private FlowLayout topButtons = new FlowLayout();
	private JButton showCurve = new JButton("SHOW CURVE");
	private JButton saveCurve = new JButton("SAVE CURVE");
	private JButton saveHDR = new JButton("SAVE HDR");
	private JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
	
	public ICatcherWindow() {
		super("iCatcher");
		setLayout(globalLayout);
		//original pictures
		add(new Label("original"),0);
		//preview for the HDR-picture
		add(new Label("preview"), 1);
		//scroll bar
		add(scrollBar, 2);
		//buttons
		JPanel buttons = new JPanel(topButtons);
		buttons.add(showCurve);
		buttons.add(saveCurve);
		buttons.add(saveHDR);
		showCurve.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//show curve
			}
		});
		saveCurve.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//save curve
				
			}
		});
		saveHDR.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//save HDR
				
			}
		});
		add(buttons, 3);
		
	}
}