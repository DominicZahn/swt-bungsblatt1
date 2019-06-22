package org.iMage.iCatcher.gui.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.iMage.HDrize.HDrize;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.gui.util.DocumentActionListener;
import org.iMage.iCatcher.gui.util.GBCBuilder;
import org.iMage.iCatcher.gui.util.JSliderRestListener;
import org.iMage.iCatcher.model.CurveMode;
import org.iMage.iCatcher.model.IModel;
import org.iMage.iCatcher.util.IObserver;

/**
 * Defines the lower part of the {@link IView}.
 *
 * @author Dominik Fuchss
 *
 */
public final class LowerPanel extends JPanel implements IObserver<IModel> {

  private static final long serialVersionUID = 2515150426211180789L;

  private JComboBox<CurveMode> curveBox;
  private JTextField lambdaText;
  private JSlider samples;

  private JComboBox<ToneMapping> toneMapping;

  private JButton loadFolder;
  private JButton btnLoadCurve;
  private JButton btnRunHdr;

  /**
   * Create the lower part.
   */
  public LowerPanel() {
    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[] { 1.0, 0.0 };
    layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
    this.setLayout(layout);
    this.createCameraCurve();
    this.createToneMapping();
    this.createButtons();
  }

  @Override
  public void invokeUpdate(IModel model) {
    this.setCameraCurveMode(model);
  }

  /**
   * Initialize the part (link and load model data).
   *
   * @param model
   *          the model
   */
  public void init(IModel model) {
    this.setLambda(model);
    this.setSamples(model);
    model.registerObserver(this);
  }

  /**
   * Register controller to panel.
   *
   * @param controller
   *          the controller
   */
  public void register(IController controller) {
    JSliderRestListener.create(this.samples, e -> controller.handleEvent(Events.PARAM_UPDATE));
    DocumentActionListener.create(this.lambdaText.getDocument(),
        e -> controller.handleEvent(Events.PARAM_UPDATE));
    this.curveBox.addActionListener(e -> controller.handleEvent(Events.PARAM_UPDATE));
    this.toneMapping.addActionListener(e -> controller.handleEvent(Events.PARAM_UPDATE));
    this.loadFolder.addActionListener(e -> controller.handleEvent(Events.LOAD_FOLDER));
    this.btnLoadCurve.addActionListener(e -> controller.handleEvent(Events.LOAD_CURVE));
    this.btnRunHdr.addActionListener(e -> controller.handleEvent(Events.RUN_HDR));
  }

  private void createButtons() {
    JPanel buttons = new JPanel();
    this.add(buttons, new GBCBuilder()//
        .anchor(GridBagConstraints.EAST).gridW(2).insets(0, 0, 5, 5).gridx(1).gridy(3).build());

    GridBagLayout gblButtons = new GridBagLayout();
    gblButtons.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
    gblButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
    buttons.setLayout(gblButtons);

    this.loadFolder = new JButton("Load Folder");
    buttons.add(this.loadFolder, new GBCBuilder().insets(0, 0, 0, 5).build());

    this.btnLoadCurve = new JButton("Load Curve");
    buttons.add(this.btnLoadCurve, new GBCBuilder().insets(0, 0, 0, 5).gridx(1).build());

    this.btnRunHdr = new JButton("Run HDR");
    buttons.add(this.btnRunHdr, new GBCBuilder().gridx(2).build());

  }

  private void createToneMapping() {
    JLabel lblToneMapping = new JLabel("Tone Mapping");
    this.add(lblToneMapping,
        new GBCBuilder().anchor(GridBagConstraints.WEST).insets(0, 0, 5, 5).gridy(2).build());

    this.toneMapping = new JComboBox<>();
    for (ToneMapping tm : ToneMapping.values()) {
      this.toneMapping.addItem(tm);
    }

    this.add(this.toneMapping,
        new GBCBuilder().fill(GridBagConstraints.HORIZONTAL).insets(0, 0, 5, 5).gridy(3).build());
  }

  private void createCameraCurve() {
    JLabel lblCameraCurve = new JLabel("Camera Curve");
    this.add(lblCameraCurve,
        new GBCBuilder().fill(GridBagConstraints.HORIZONTAL).insets(0, 0, 5, 5).build());

    JLabel lblSamples = new JLabel("Samples (50)");
    this.add(lblSamples, new GBCBuilder().insets(0, 0, 5, 5).gridx(1).build());

    JLabel lblLambda = new JLabel("Lambda");
    this.add(lblLambda, new GBCBuilder().insets(0, 0, 5, 0).gridx(2).build());

    this.curveBox = new JComboBox<>();
    for (CurveMode cm : CurveMode.values()) {
      this.curveBox.addItem(cm);
    }
    this.add(this.curveBox, new GBCBuilder().anchor(GridBagConstraints.NORTH).insets(0, 0, 5, 5)
        .fill(GridBagConstraints.HORIZONTAL).gridy(1).build());

    this.samples = new JSlider();
    this.samples
        .addChangeListener(l -> lblSamples.setText("Samples (" + this.samples.getValue() + ")"));
    this.samples.setMinimum(1);
    this.samples.setMaximum(1000);
    this.samples.setLabelTable(this.getSliderDictionary());
    this.samples.setMajorTickSpacing(249);
    this.samples.setPaintTicks(true);
    this.samples.setPaintLabels(true);
    this.add(this.samples, new GBCBuilder().insets(0, 0, 5, 5).gridx(1).gridy(1).build());

    this.lambdaText = new JTextField();
    this.lambdaText.setColumns(10);
    this.add(this.lambdaText, new GBCBuilder().anchor(GridBagConstraints.NORTH).insets(0, 0, 5, 0)
        .fill(GridBagConstraints.HORIZONTAL).gridx(2).gridy(1).build());

  }

  private Dictionary<Integer, JLabel> getSliderDictionary() {
    final int steps = 250;
    Hashtable<Integer, JLabel> labels = new Hashtable<>();
    labels.put(1, new JLabel("1"));
    labels.put(1000, new JLabel("1000"));
    for (int i = steps; i < 1000; i += steps) {
      labels.put(i, new JLabel(String.valueOf(i)));
    }

    return labels;
  }

  /**
   * Set the current {@link CurveMode} according to {@link IModel}.
   *
   * @param model
   *          the model
   */
  public void setCameraCurveMode(IModel model) {
    this.curveBox.setSelectedItem(model.getCurveMode());
  }

  private void setLambda(IModel model) {
    this.lambdaText.setText(String.valueOf(model.getLambda()));
  }

  private void setSamples(IModel model) {
    this.samples.setValue(model.getSamples());
  }

  /**
   * Get the selected {@link CurveMode}.
   *
   * @return the selected {@link CurveMode}
   */
  public CurveMode getCameraCurveMode() {
    return (CurveMode) this.curveBox.getSelectedItem();
  }

  /**
   * Get the current lambda.
   *
   * @return the current lambda
   */
  public String getLambda() {
    return this.lambdaText.getText();
  }

  /**
   * Get the current samples.
   *
   * @return the current samples
   */
  public int getSamples() {
    return this.samples.getValue();
  }

  /**
   * Get the current {@link ToneMapping}.
   *
   * @return the current {@link ToneMapping}
   */
  public ToneMapping getToneMapping() {
    return (ToneMapping) this.toneMapping.getSelectedItem();
  }

  /**
   * Set whether {@link #getLambda()} is invalid.
   *
   * @param invalid
   *          indicator
   */
  public void setLambdaToInvalid(boolean invalid) {
    this.lambdaText.setForeground(invalid ? Color.RED : Color.BLACK);
  }

  /**
   * Lock all components.
   */
  public void lock() {
    this.btnRunHdr.setEnabled(false);
    this.samples.setEnabled(false);
    this.lambdaText.setEnabled(false);

    this.btnLoadCurve.setEnabled(false);
    this.loadFolder.setEnabled(false);
    this.curveBox.setEnabled(false);
    this.toneMapping.setEnabled(false);
  }

  /**
   * Enable or Disable according to model state.
   *
   * @param canRun
   *          indicates that {@link HDrize} can be executed.
   */
  public void enOrDisableButtons(boolean canRun) {
    this.btnLoadCurve.setEnabled(true);
    this.loadFolder.setEnabled(true);
    this.curveBox.setEnabled(true);
    this.toneMapping.setEnabled(true);

    this.btnRunHdr.setEnabled(canRun);
    this.samples.setEnabled(this.getCameraCurveMode() == CurveMode.CalculatedCurve);
    this.lambdaText.setEnabled(this.getCameraCurveMode() == CurveMode.CalculatedCurve);
  }

}
