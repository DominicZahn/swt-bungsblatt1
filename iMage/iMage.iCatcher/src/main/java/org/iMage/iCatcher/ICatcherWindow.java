package org.iMage.iCatcher;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class ICatcherWindow extends JFrame {
	private GridLayout globalLayout = new GridLayout(6, 2);
	private JButton previewButton = new JButton();
	private JButton showCurve = new JButton("SHOW CURVE");
	private JButton saveCurve = new JButton("SAVE CURVE");
	private JButton saveHDR = new JButton("SAVE HDR");
	private JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
	private Label sliderValue = new Label("Samples (500)");
	private JComboBox dropDownCameraCurve = new JComboBox();
	private JComboBox dropDownToneMapping = new JComboBox();
	private JSlider slider = new JSlider();
	private JTextField textFieldLambda = new JTextField();
	private JButton loadDir = new JButton("LOAD DIR");
	private JButton loadCurve = new JButton("LOAD CURVE");
	private JButton runHDrize = new JButton("RUN HDRIZE");
	
	public ICatcherWindow() {
		super("iCatcher");
		setLayout(globalLayout);
		// original pictures
		add(new Label("original"), 0);
		// preview for the HDR-picture
		add(previewButton, 1);
		// scroll bar
		add(scrollBar, 2);
		// top buttons
		JPanel topButtons = new JPanel(new FlowLayout());
		topButtons.add(showCurve);
		topButtons.add(saveCurve);
		topButtons.add(saveHDR);
		showCurve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// show curve
			}
		});
		saveCurve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// save curve
			}
		});
		saveHDR.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// save HDR

			}
		});
		add(topButtons, 3);
		//Drop-Down title Camera Curve 
		add(new Label("Camera Curve"), 4);
		//titles for slider and Lambda
		JPanel titles = new JPanel(new FlowLayout());
		titles.add(sliderValue);
		titles.add(new Label("Lambda"));
		add(titles, 5);
		//Drop-Down Standard Curve
		add(dropDownCameraCurve, 6);
		//lambda text field and slider
		JPanel sliderAndLambda = new JPanel(new FlowLayout());
		sliderAndLambda.add(slider);
		sliderAndLambda.add(textFieldLambda);
		add(sliderAndLambda ,7);
		//bottom buttons
		JPanel bottomButtons = new JPanel();
		bottomButtons.add(loadDir);
		bottomButtons.add(loadCurve);
		bottomButtons.add(runHDrize);
		add(bottomButtons);
		//Drop-Down title Tone Mapping
		add(new Label("Tone Mapping"), 9);
		//Drop-Down Tone Mapping
		add(dropDownToneMapping, 10);
		
	}
}