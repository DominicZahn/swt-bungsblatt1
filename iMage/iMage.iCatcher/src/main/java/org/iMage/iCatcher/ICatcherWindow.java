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
	private final int LEFTSIDEX = 30;
	private final int RIGHTSIDEX = 410;
	//just so there is something
	private Label originalSlideShow = new Label("original");
	//
	private JButton buttonPreview = new JButton("preview");
	private JScrollBar scrollBarOriginal = new JScrollBar(JScrollBar.HORIZONTAL);
	private JButton buttonShowCurve = new JButton("SHOW CURVE");
	private JButton buttonSaveCurve = new JButton("SAVE CURVE");
	private JButton buttonSaveHDR = new JButton("SAVE HDR");
	private Label labelCameraCurve = new Label("Camera Curve");
	private JComboBox comboBoxCameraCurve = new JComboBox();
	private Label labelToneMapping = new Label("Tone Mapping");
	private JComboBox comboBoxToneMapping = new JComboBox();
	private Label labelSamplesValue = new Label("Samples(500)");
	private JSlider sliderSamples = new JSlider();
	private Label labelLambda = new Label("Lambda");
	private JTextField textFieldLabmda = new JTextField("20.0");
	private JButton buttonLoadDir = new JButton("LOAD DIR");
	private JButton buttonLoadCurve = new JButton("LOAD CURVE");
	private JButton buttonRunHDrize = new JButton("RUN HDrize");

	public ICatcherWindow() {
		super("iCatcher");
		setLayout(null);
		
		//original pictures
		originalSlideShow.setLocation(LEFTSIDEX, 30);
		originalSlideShow.setSize(350, 250);
		add(originalSlideShow);
		
		//preview picture / button
		buttonPreview.setLocation(RIGHTSIDEX, 30);
		buttonPreview.setSize(350, 250);
		add(buttonPreview);
		
		//slider for the original pictures
		scrollBarOriginal.setMinimum(0);
		scrollBarOriginal.setMaximum(1050);//3 *  width of the picture
		scrollBarOriginal.setLocation(LEFTSIDEX, 290);
		scrollBarOriginal.setSize(350, 20);
		add(scrollBarOriginal);
		
		final int GAP = 5; //gap between the three following buttons
		//button SHOW CURVE
		buttonShowCurve.setLocation(RIGHTSIDEX, 290);
		buttonShowCurve.setSize(120, 20);
		add(buttonShowCurve);
		
		//button SAVE CURVE
		buttonSaveCurve.setLocation(RIGHTSIDEX + 120 + GAP, 290);
		buttonSaveCurve.setSize(120, 20);
		add(buttonSaveCurve);
		
		//button SAVE HDR
		buttonSaveHDR.setLocation(RIGHTSIDEX + (120 + GAP) * 2, 290);
		buttonSaveHDR.setSize(100, 20);
		add(buttonSaveHDR);
		
		//label drop-down Camera Curve
		labelCameraCurve.setLocation(LEFTSIDEX, 330);
		labelCameraCurve.setSize(350, 20);
		add(labelCameraCurve);
		
		//drop-down Camera Curve
		comboBoxCameraCurve.setLocation(LEFTSIDEX, 350);
		comboBoxCameraCurve.setSize(350, 20);
		add(comboBoxCameraCurve);
		
		//label drop-down Tone Mapping 
		labelToneMapping.setLocation(LEFTSIDEX, 390);
		labelToneMapping.setSize(350, 20);
		add(labelToneMapping);
		
		//drop-down Tone Mapping
		comboBoxToneMapping.setLocation(LEFTSIDEX, 410);
		comboBoxToneMapping.setSize(350, 20);
		add(comboBoxToneMapping);
		
		final int SLIDERWIDTH = 250;
		//label which displays the current value of the slider
		labelSamplesValue.setAlignment(Label.CENTER);
		labelSamplesValue.setLocation(RIGHTSIDEX, 330);
		labelSamplesValue.setSize(SLIDERWIDTH, 20);
		add(labelSamplesValue);
		
		//samples slider
		sliderSamples.setLocation(RIGHTSIDEX, 350);
		sliderSamples.setSize(SLIDERWIDTH, 20);
		sliderSamples.setMinimum(0);
		sliderSamples.setMaximum(1000);
		sliderSamples.setValue(500);
		add(sliderSamples);
		
		final int POSXLAMBDA = RIGHTSIDEX + SLIDERWIDTH + 30;
		final int WIDTHLAMBDA = 350 - (SLIDERWIDTH + 30);
		//label lambda
		labelLambda.setAlignment(Label.RIGHT);
		labelLambda.setLocation(POSXLAMBDA, 330);
		labelLambda.setSize(WIDTHLAMBDA, 20);
		add(labelLambda);
		
		//text field lambda
		textFieldLabmda.setLocation(POSXLAMBDA, 350);
		textFieldLabmda.setSize(WIDTHLAMBDA, 20);
		textFieldLabmda.setHorizontalAlignment(JTextField.RIGHT);
		add(textFieldLabmda);
		
		//button LOAD DIR
		buttonLoadDir.setLocation(RIGHTSIDEX, 410);
		buttonLoadDir.setSize(100, 20);
		add(buttonLoadDir);
		//button LOAD CURVE
		buttonLoadCurve.setLocation(RIGHTSIDEX + 105, 410);
		buttonLoadCurve.setSize(120, 20);
		add(buttonLoadCurve);
		
		//button RUN HDrize
		buttonRunHDrize.setLocation(RIGHTSIDEX + 230, 410);
		buttonRunHDrize.setSize(120, 20);
		add(buttonRunHDrize);
	}
}