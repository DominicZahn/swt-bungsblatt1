package org.iMage.iCatcher;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.iMage.HDrize.HDrize;
import org.iMage.HDrize.base.images.HDRImageIO;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;

public class ICatcherWindow extends JFrame {
	static final long serialVersionUID = 7126097627133805714L;
	private final int LEFTSIDEX = 30;
	private final int RIGHTSIDEX = 410;
	private final String SIMPLEMAP = "Simple Map";
	private final String STANDARDGAMMA = "Standard Gamma";
	private final String SRGBGAMMA = "SRGB Gamma";
	// just so there is something
	private Label originalSlideShow = new Label("original");
	//
	private JButton buttonPreview = new JButton("preview");
	private JScrollBar scrollBarOriginal = new JScrollBar(JScrollBar.HORIZONTAL);
	private JButton buttonShowCurve = new JButton("SHOW CURVE");
	private JButton buttonSaveCurve = new JButton("SAVE CURVE");
	private JButton buttonSaveHDR = new JButton("SAVE HDR");
	private Label labelCameraCurve = new Label("Camera Curve");
	private JComboBox<String> comboBoxCameraCurve = new JComboBox<String>();
	private Label labelToneMapping = new Label("Tone Mapping");
	private JComboBox<String> comboBoxToneMapping = new JComboBox<String>();
	private Label labelSamplesValue = new Label("Samples(500)");
	private JSlider sliderSamples = new JSlider();
	private Label labelLambda = new Label("Lambda");
	private JTextField textFieldLabmda = new JTextField("20.0");
	private JButton buttonLoadDir = new JButton("LOAD DIR");
	private JButton buttonLoadCurve = new JButton("LOAD CURVE");
	private JButton buttonRunHDrize = new JButton("RUN HDrize");

	private final double LAMBDADEFAULT = 20;
	private final int SAMPLESDEFAULT = 500;
	private final double LAMBDAMIN = 0;
	private final double LAMBDAMAX = 100;
	// global variables
	private BufferedImage previewPic;
	private boolean standardCurve = true;
	private double lambda = LAMBDADEFAULT;
	private int samples = SAMPLESDEFAULT;
	private ToneMapping toneMappingMode = ToneMapping.SimpleMap;

	public ICatcherWindow() {
		super("iCatcher");
		setRawStructure();
		setActionListeners();
	}

	private void setActionListeners() {

		// original pictures

		// preview picture / button

		// scroll bar for the original pictures

		// button SHOW CURVE

		// button SAVE CURVE

		// button SAVE HDR

		// drop-down Camera Curve
		comboBoxCameraCurve.addActionListener(new standardCameraCurveListener());

		// drop-down Tone Mapping
		comboBoxToneMapping.addActionListener(new toneMappingListener());

		// samples slider
		sliderSamples.addChangeListener(new samplesListener());

		// textFieldLambda
		textFieldLabmda.addActionListener(new lambdaListener());

		// button LOAD DIR

		// button LOAD CURVE

		// button RUN HDrize

	}

	class standardCameraCurveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboBoxCameraCurve.getSelectedItem().equals("Standard Curve")) {
				standardCurve = true;
			} else {
				standardCurve = false;
			}
		}
	}

	class toneMappingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String selected = comboBoxToneMapping.getSelectedItem().toString();
			switch (selected) {
			case SIMPLEMAP:
				toneMappingMode = ToneMapping.SimpleMap;
				break;
			case STANDARDGAMMA:
				toneMappingMode = ToneMapping.StandardGamma;
				break;
			case SRGBGAMMA:
				toneMappingMode = ToneMapping.SRGBGamma;
				break;
			}
		}
	}

	class samplesListener implements ChangeListener {
		public synchronized void stateChanged(ChangeEvent e) {
			samples = sliderSamples.getValue();
			labelSamplesValue.setText("Samples(" + samples + ")");
		}
	}

	class lambdaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String input = textFieldLabmda.getText();
			boolean parsingPosible = true;
			double nLambda = -1;
			try {
				nLambda = Double.parseDouble(input);
			} catch (NumberFormatException ne) {
				parsingPosible = false;
			}
			if (parsingPosible && 0 < nLambda && nLambda <= 100) {
				lambda = nLambda;
				textFieldLabmda.setForeground(Color.BLACK);
				textFieldLabmda.setText(Double.toString(lambda));
			} else {
				textFieldLabmda.setForeground(Color.RED);
			}
		}
	}

	private void setRawStructure() {
		setLayout(null);

		// original pictures
		originalSlideShow.setLocation(LEFTSIDEX, 30);
		originalSlideShow.setSize(350, 250);
		add(originalSlideShow);

		// preview picture / button
		buttonPreview.setLocation(RIGHTSIDEX, 30);
		buttonPreview.setSize(350, 250);
		add(buttonPreview);

		// scroll bar for the original pictures
		scrollBarOriginal.setMinimum(0);
		scrollBarOriginal.setMaximum(100);// 3 * width of the picture
		scrollBarOriginal.setLocation(LEFTSIDEX, 290);
		scrollBarOriginal.setSize(350, 20);
		add(scrollBarOriginal);

		final int GAP = 5; // gap between the three following buttons
		// button SHOW CURVE
		buttonShowCurve.setLocation(RIGHTSIDEX, 290);
		buttonShowCurve.setSize(120, 20);
		add(buttonShowCurve);

		// button SAVE CURVE
		buttonSaveCurve.setLocation(RIGHTSIDEX + 120 + GAP, 290);
		buttonSaveCurve.setSize(120, 20);
		add(buttonSaveCurve);

		// button SAVE HDR
		buttonSaveHDR.setLocation(RIGHTSIDEX + (120 + GAP) * 2, 290);
		buttonSaveHDR.setSize(100, 20);
		add(buttonSaveHDR);

		// label drop-down Camera Curve
		labelCameraCurve.setLocation(LEFTSIDEX, 330);
		labelCameraCurve.setSize(350, 20);
		add(labelCameraCurve);

		// drop-down Camera Curve
		comboBoxCameraCurve.setLocation(LEFTSIDEX, 350);
		comboBoxCameraCurve.setSize(350, 20);
		comboBoxCameraCurve.addItem("Standard Curve");
		comboBoxCameraCurve.addItem("Calculated Curve");
		add(comboBoxCameraCurve);

		// label drop-down Tone Mapping
		labelToneMapping.setLocation(LEFTSIDEX, 390);
		labelToneMapping.setSize(350, 20);
		add(labelToneMapping);

		// drop-down Tone Mapping
		comboBoxToneMapping.setLocation(LEFTSIDEX, 410);
		comboBoxToneMapping.setSize(350, 20);
		comboBoxToneMapping.addItem(SIMPLEMAP);
		comboBoxToneMapping.addItem(STANDARDGAMMA);
		comboBoxToneMapping.addItem(SRGBGAMMA);
		add(comboBoxToneMapping);

		final int SLIDERWIDTH = 250;
		// label which displays the current value of the slider
		labelSamplesValue.setAlignment(Label.CENTER);
		labelSamplesValue.setLocation(RIGHTSIDEX, 330);
		labelSamplesValue.setSize(SLIDERWIDTH, 20);
		add(labelSamplesValue);

		// samples slider
		sliderSamples.setLocation(RIGHTSIDEX, 350);
		sliderSamples.setSize(SLIDERWIDTH, 20);
		sliderSamples.setMinimum(0);
		sliderSamples.setMaximum(1000);
		sliderSamples.setValue(500);
		add(sliderSamples);

		final int POSXLAMBDA = RIGHTSIDEX + SLIDERWIDTH + 30;
		final int WIDTHLAMBDA = 350 - (SLIDERWIDTH + 30);
		// label lambda
		labelLambda.setAlignment(Label.RIGHT);
		labelLambda.setLocation(POSXLAMBDA, 330);
		labelLambda.setSize(WIDTHLAMBDA, 20);
		add(labelLambda);

		// text field lambda
		textFieldLabmda.setLocation(POSXLAMBDA, 350);
		textFieldLabmda.setSize(WIDTHLAMBDA, 20);
		textFieldLabmda.setHorizontalAlignment(JTextField.RIGHT);
		add(textFieldLabmda);

		// button LOAD DIR
		buttonLoadDir.setLocation(RIGHTSIDEX, 410);
		buttonLoadDir.setSize(100, 20);
		add(buttonLoadDir);

		// button LOAD CURVE
		buttonLoadCurve.setLocation(RIGHTSIDEX + 105, 410);
		buttonLoadCurve.setSize(120, 20);
		add(buttonLoadCurve);

		// button RUN HDrize
		buttonRunHDrize.setLocation(RIGHTSIDEX + 230, 410);
		buttonRunHDrize.setSize(120, 20);
		add(buttonRunHDrize);
	}
}