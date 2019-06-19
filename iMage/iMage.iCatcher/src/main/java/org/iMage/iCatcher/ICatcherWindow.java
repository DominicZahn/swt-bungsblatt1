package org.iMage.iCatcher;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.annotation.processing.FilerException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.CameraCurve;
import org.iMage.HDrize.HDrize;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImageIO;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.base.matrix.IMatrixCalculator;
import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MatrixCalculator;
import org.ojalgo.type.StandardType;

public class ICatcherWindow extends JFrame {
	static final long serialVersionUID = 7126097627133805714L;
	private final int LEFTSIDEX = 30;
	private final int RIGHTSIDEX = 410;
	private final String SIMPLEMAP = "Simple Map";
	private final String STANDARDGAMMA = "Standard Gamma";
	private final String SRGBGAMMA = "SRGB Gamma";
	private final double LAMBDADEFAULT = 20;
	private final int SAMPLESDEFAULT = 500;
	private final double LAMBDAMIN = 0;
	private final double LAMBDAMAX = 100;
	private final String STANDARDCURVE ="standard curve";
	private final String CALCULATEDCURVE = "calculated curve";
	private final String LOADEDCURVE = "loaded curve";
	
	private JScrollPane originalSlideShow = new JScrollPane();
	private JButton buttonPreview = new JButton("preview");
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
	private JFileChooser chooser = new JFileChooser(new java.io.File("."));
	private JFileChooser chooserSave = new JFileChooser(new java.io.File("."));

	// global variables
	private BufferedImage previewPic;
	private double lambda = LAMBDADEFAULT;
	private int samples = SAMPLESDEFAULT;
	private ToneMapping toneMappingMode = ToneMapping.SimpleMap;
	private File selectedDir;
	private boolean keyPressed = false;
	private BufferedImage[] bufferedArray;
	private EnhancedImage[] enhancedImages;
	private String prefix;
	private String curveMode = STANDARDCURVE;

	public ICatcherWindow() {
		super("iCatcher");
		setRawStructure();
		setActionListeners();
	}

	private void setActionListeners() {

		// preview picture / button
		buttonPreview.addActionListener(new previewButtonListener());

		// button SHOW CURVE

		// button SAVE CURVE
		buttonSaveCurve.addActionListener(new saveCurveListener());

		// button SAVE HDR
		buttonSaveHDR.addActionListener(new saveHDRListener());

		// drop-down Camera Curve
		comboBoxCameraCurve.addActionListener(new standardCameraCurveListener());

		// drop-down Tone Mapping
		comboBoxToneMapping.addActionListener(new toneMappingListener());

		// samples slider
		sliderSamples.addChangeListener(new samplesListener());

		// textFieldLambda
		textFieldLabmda.addActionListener(new lambdaListener());

		// button LOAD DIR
		buttonLoadDir.addActionListener(new loadDirListener());

		// button LOAD CURVE
		buttonLoadCurve.addActionListener(new loadCurveListener());

		// button RUN HDrize
		buttonRunHDrize.addActionListener(new runHDrizwListener());

	}

	class saveHDRListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (previewPic == null) {
				showErrorDialog("The HDR picture is not calculated yet.", "No HDR picture");
				triggerBlueScreen();
				return;
			}
			File selectedFile;
			String absolutPath = "";
			chooserSave.setDialogTitle("LOAD DIR");
			chooserSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooserSave.setAcceptAllFileFilterUsed(false);
			chooserSave.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
			if (chooserSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooserSave.getSelectedFile();
			} else {
				selectedFile = null;
				return;
			}
			try {
				File file = new File(selectedFile.getAbsolutePath() + ".png");
				ImageIO.write(previewPic, "png", file);
			} catch (IOException io) {
				System.out.println(io.getMessage());
				triggerBlueScreen();
			}
		}
	}
	
	class saveCurveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!curveMode.equals(CALCULATEDCURVE)) {
				showErrorDialog("If you want to save your custom curve please select calculated curve in the drop down menu.", "wrong drop-down selection");
				triggerBlueScreen();
				return;
			}
			if (previewPic == null) {
				showErrorDialog("You have to run HDrize before saveing the curve.", "HDrize was not used yet");
			}
			File selectedFile;
			String absolutPath = "";
			chooserSave.setDialogTitle("SAVE CURVE");
			chooserSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooserSave.setAcceptAllFileFilterUsed(false);
			chooserSave.setFileFilter(new FileNameExtensionFilter("BIN file", "bin"));
			if (chooserSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooserSave.getSelectedFile();
			} else {
				selectedFile = null;
				return;
			}
			CameraCurve cc = new CameraCurve(enhancedImages, samples, lambda, new MatrixCalculator());
			try {
				File file = new File(selectedFile.getAbsolutePath() + ".bin");
				cc.save(new FileOutputStream(file));
			} catch (IOException io) {
				System.out.println(io.getMessage());
				triggerBlueScreen();
			}
		}
	}

	class previewButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame hdr = new ImageHDRWindow(previewPic, prefix);
			hdr.setVisible(true);
			hdr.setSize(previewPic.getWidth(), previewPic.getHeight());
			hdr.setLocationRelativeTo(null);
			hdr.setResizable(false);
		    hdr.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}

	class standardCameraCurveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			curveMode = comboBoxCameraCurve.getSelectedItem().toString();
			readCurveFromFile();
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

	class loadDirListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// read directory
			chooser.setDialogTitle("LOAD DIR");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				selectedDir = chooser.getSelectedFile();
			} else {
				selectedDir = null;
				return;
			}

			// control images
			ArrayList<File> jpgs = new ArrayList<>();
			for (File file : selectedDir.listFiles(f -> f.getName().endsWith(".jpg"))) {
				jpgs.add(file);
			}

			if (jpgs.size() % 2 == 0 || jpgs.size() <= 1) {
				System.err.println("Found " + jpgs.size() + " files. This isn't an odd value.");
				triggerBlueScreen();
			}
			File[] result = jpgs.toArray(File[]::new);
			for (File image : result) {
				String name = image.getName();
				name = name.substring(0, name.length() - ".jpg".length());
				if (name.length() < 3 || !result[0].getName().startsWith(name.substring(0, 3))) {
					System.err.println("Naming violation: " + image.getName() + " & " + result[0].getName());
					triggerBlueScreen();
				}
			}
			// creating prefix
			String arr[] = new String[jpgs.size()];
			int pos = 0;
			for (File image : jpgs) {
				arr[pos] = image.getName();
				pos++;
			}
			prefix = commonPrefix(arr, arr.length);
			displayOriginalImages(jpgs);
		}
	}
	
	class loadCurveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			readCurveFromFile();
			comboBoxCameraCurve.setSelectedItem(LOADEDCURVE);
			comboBoxCameraCurve.repaint();
			curveMode = LOADEDCURVE;
		}
	}
	
	private void readCurveFromFile() {
		File file;
		JFileChooser curveChooser = new JFileChooser();
		curveChooser.setDialogTitle("Select Curve");
		curveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		curveChooser.setAcceptAllFileFilterUsed(false);
		curveChooser.addChoosableFileFilter(new FileNameExtensionFilter("bin file", "bin"));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		} else {
			file = null;
			return;
		}
		CameraCurve cc;
		try {
			cc = new CameraCurve(new FileInputStream(file));	
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			showErrorDialog("The selected file is not there.", "File not found");
			triggerBlueScreen();
			return;
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			showErrorDialog(e.getMessage(), "Class not found");
			triggerBlueScreen();
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			showErrorDialog(e.getMessage(), "IOException");
			triggerBlueScreen();
			return;
		}
		//lambda = ...
		//samples = ...
		//toneMapping = ...
	}

	private String commonPrefix(String arr[], int n) {
		int index = findMinLength(arr, n);
		String prefix = ""; // Our resultant string

		// We will do an in-place binary search on the
		// first string of the array in the range 0 to
		// index
		int low = 0, high = index;
		while (low <= high) {

			// Same as (low + high)/2, but avoids
			// overflow for large low and high
			int mid = low + (high - low) / 2;

			if (allContainsPrefix(arr, n, arr[0], low, mid)) {
				// If all the strings in the input array
				// contains this prefix then append this
				// substring to our answer
				prefix = prefix + arr[0].substring(low, mid + 1);

				// And then go for the right part
				low = mid + 1;
			} else // Go for the left part
			{
				high = mid - 1;
			}
		}

		return prefix;
	}

	private boolean allContainsPrefix(String arr[], int n, String str, int start, int end) {
		for (int i = 0; i <= (n - 1); i++) {
			String arr_i = arr[i];

			for (int j = start; j <= end; j++)
				if (arr_i.charAt(j) != str.charAt(j))
					return false;
		}
		return true;
	}

	private int findMinLength(String arr[], int n) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i <= (n - 1); i++) {
			if (arr[i].length() < min) {
				min = arr[i].length();
			}
		}
		return min;
	}

	class runHDrizwListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			HDrize hdrize = new HDrize();
			if (!checkArguments()) {
				return;
			}
			if (enhancedImages == null) {
				showErrorDialog("Please insert the original picutres before running HDrize.", "No originals");
				triggerBlueScreen();
				return;
			}
			if (curveMode.equals(STANDARDCURVE)) {
				// uses the standard curve
				previewPic = hdrize.createRGB(enhancedImages, new CameraCurve(), toneMappingMode);
			} 
			if(curveMode.equals(CALCULATEDCURVE)) {
				// uses the custom settings for the curve
				previewPic = hdrize.createRGB(enhancedImages, samples, lambda, new MatrixCalculator(),
						toneMappingMode);
			}
			Image resized = previewPic.getScaledInstance(350, 250, 1);
			buttonPreview.setIcon(new ImageIcon(resized));
			buttonPreview.repaint();
		}

	}

	private void showErrorDialog(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	private boolean checkArguments() {
		if (bufferedArray == null) {
			showErrorDialog("Please load a set of images.", "Original pictures Error");
			return false;
		}
		if (lambda <= LAMBDAMIN || lambda > LAMBDAMAX) {
			showErrorDialog("Don't know how you did it but you managed to input a wrong value!", "Lambda Error");
			return false;
		}
		if (samples <= 0 || samples > 1000) {
			showErrorDialog("Don't know how you did it but you managed to input a wrong value!", "Lambda Error");
			return false;
		}
		return true;
	}

	/**
	 * displays all the original pictures of the selected directory in the left
	 * corner
	 */
	private void displayOriginalImages(ArrayList<File> jpgs) {
		int pos = 0;
		// create allImages and an array of all BufferedImages and an array of all
		// EnhancedImages
		bufferedArray = new BufferedImage[jpgs.size()];
		enhancedImages = new EnhancedImage[jpgs.size()];
		// calculate width and height
		int allWidth = 0;
		int allHeight = 0;
		for (File image : jpgs) {
			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(image);
				enhancedImages[pos] = new EnhancedImage(new FileInputStream(image));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				triggerBlueScreen();
				continue;
			} catch (ImageReadException ire) {
				System.out.println(ire.getMessage());
				triggerBlueScreen();
				continue;
			}
			// add to array
			bufferedArray[pos] = bufferedImage;
			// width
			allWidth += bufferedImage.getWidth();
			// height
			if (allHeight < bufferedImage.getHeight()) {
				allHeight = bufferedImage.getHeight();
			}
			pos++;
		}
		int currentYPos = 0;
		BufferedImage allImages = new BufferedImage(allWidth, allHeight, BufferedImage.TYPE_INT_RGB);
		for (File image : jpgs) {
			try {
				allImages = addImage(allImages, ImageIO.read(image), currentYPos);
				currentYPos += ImageIO.read(image).getWidth();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				triggerBlueScreen();
			}
		}
		allImages = resize(allImages, 250, 350 * jpgs.size());
		originalSlideShow.setViewportView(new JLabel(new ImageIcon(allImages)));
		originalSlideShow.repaint();
	}

	/**
	 * adds a new image to the row of images in one image
	 * 
	 * @param allImages  the BIG image
	 * @param addedImage the image which should be added
	 * @param startWidth the position were the new image starts
	 * @return the modified allImages
	 */
	private BufferedImage addImage(BufferedImage allImages, BufferedImage addedImage, int startWidth) {
		for (int x = 0; x < addedImage.getWidth(); x++) {
			for (int y = 0; y < addedImage.getHeight(); y++) {
				if ((x + startWidth) < allImages.getWidth() && y < allImages.getHeight()) {
					allImages.setRGB(x + startWidth, y, addedImage.getRGB(x, y));
				}
			}
		}
		return allImages;
	}

	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	private void setRawStructure() {
		setLayout(null);

		// original pictures
		originalSlideShow.setLocation(LEFTSIDEX, 30);
		originalSlideShow.setSize(350, 250);
		originalSlideShow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		originalSlideShow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		add(originalSlideShow);

		// preview picture / button
		buttonPreview.setLocation(RIGHTSIDEX, 30);
		buttonPreview.setSize(350, 250);
		add(buttonPreview);

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
		comboBoxCameraCurve.addItem(STANDARDCURVE);
		comboBoxCameraCurve.addItem(CALCULATEDCURVE);
		comboBoxCameraCurve.addItem(LOADEDCURVE);
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
		sliderSamples.setMinimum(1);
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

	/**
	 * nothing important...
	 */
	private void triggerBlueScreen() {
		JFrame bsod = new JFrame();
		bsod.setExtendedState(JFrame.MAXIMIZED_BOTH);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		bsod.setUndecorated(true);
		device.setFullScreenWindow(bsod);
		ImageIcon blueScreen = new ImageIcon("./src/main/resources/BlueScreen.jpg");
		JLabel label = new JLabel(blueScreen);
		bsod.add(label);
		bsod.addKeyListener(new DestroyListener(bsod));
		bsod.setVisible(true);
		bsod.toFront();
	}

	class DestroyListener implements KeyListener {

		JFrame activeFrame = null;

		DestroyListener(JFrame activeFrame) {
			this.activeFrame = activeFrame;
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			activeFrame.dispose();
			// open error message
			JOptionPane.showMessageDialog(null, "Abort, Retry, Fail?", "Error message", JOptionPane.ERROR_MESSAGE);
		}
	}

}