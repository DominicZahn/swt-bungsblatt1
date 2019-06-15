package org.iMage.iCatcher;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
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
	private GridBagLayout globalLayout = new GridBagLayout();
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
		GridBagConstraints gbc = new GridBagConstraints();
		
		// original pictures
		addGBC(new Label("original"), gbc, 0, 0);
		// preview for the HDR-picture
		addGBC(previewButton, gbc, 1, 0);
		// scroll bar
		addGBC(scrollBar, gbc, 0, 1);
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
		addGBC(topButtons, gbc, 1, 1);
		//Drop-Down title Camera Curve
		addGBC(new Label("Camera Curve"), gbc, 0, 2);
		//titles for slider and Lambda
		JPanel titles = new JPanel(new FlowLayout());
		titles.add(sliderValue);
		titles.add(new Label("Lambda"));
		addGBC(titles, gbc, 1, 2);
		//Drop-Down Standard Curve
		addGBC(dropDownCameraCurve, gbc, 0, 3);
		//lambda text field and slider
		JPanel sliderAndLambda = new JPanel(new FlowLayout());
		sliderAndLambda.add(slider);
		sliderAndLambda.add(textFieldLambda);
		addGBC(sliderAndLambda, gbc, 1, 3);
		//bottom buttons
		JPanel bottomButtons = new JPanel();
		bottomButtons.add(loadDir);
		bottomButtons.add(loadCurve);
		bottomButtons.add(runHDrize);
		addGBC(bottomButtons, gbc, 1, 4);
		//Drop-Down title Tone Mapping
		addGBC(new Label("Tone Mapping"), gbc, 0, 4);
		//Drop-Down Tone Mapping
		addGBC(dropDownToneMapping, gbc, 0, 5);
		}
	
	private void addGBC(Component component, GridBagConstraints gbc, int gridx, int gridy) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		add(component, gbc);
	}
}