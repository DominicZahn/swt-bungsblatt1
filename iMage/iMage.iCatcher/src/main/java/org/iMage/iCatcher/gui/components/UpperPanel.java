package org.iMage.iCatcher.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.gui.util.GBCBuilder;
import org.iMage.iCatcher.gui.util.ImagePanel;
import org.iMage.iCatcher.gui.util.ImageUtils;
import org.iMage.iCatcher.model.IModel;
import org.iMage.iCatcher.util.IObserver;

/**
 * Defines the upper part of the {@link IView}.
 *
 * @author Dominik Fuchss
 *
 */
public final class UpperPanel extends JPanel implements IObserver<IModel> {

  private static final long serialVersionUID = 4924642174819735950L;
  private ImagePanel input;
  private BufferedImage[] currentInputs;
  private ImagePanel result;
  private BufferedImage currentResult;

  private JButton btnShowCurve;
  private JButton btnSaveCurve;
  private JButton btnSaveHDR;

  /**
   * Create the upper part.
   */
  public UpperPanel() {
    GridBagLayout layout = new GridBagLayout();
    layout.rowWeights = new double[] { 1.0 };
    layout.columnWeights = new double[] { 1.0, 1.0 };
    this.setLayout(layout);

    this.createLeftPane();
    this.createRightPane();
  }

  @Override
  public void invokeUpdate(IModel model) {
    this.setInput(model);
    this.setResult(model);
  }

  /**
   * Initialize the part (link and load model data).
   *
   * @param model
   *          the model
   */
  public void init(IModel model) {
    model.registerObserver(this);
  }

  /**
   * Register controller to panel.
   *
   * @param controller
   *          the controller
   */
  public void register(IController controller) {
    this.btnSaveCurve.addActionListener(e -> controller.handleEvent(Events.SAVE_CURVE));
    this.btnShowCurve.addActionListener(e -> controller.handleEvent(Events.SHOW_CURVE));
    this.btnSaveHDR.addActionListener(e -> controller.handleEvent(Events.SAVE_HDR));
    this.input.addActionListener(e -> controller.handleEvent(Events.SHOW_INPUT));
    this.result.addActionListener(e -> controller.handleEvent(Events.SHOW_RESULT));
  }

  private void setResult(IModel model) {
    BufferedImage result = model.getResult();
    if (result == this.currentResult) {
      return;
    }

    if (result == null) {
      this.result.setImage(null);
      return;
    }

    this.currentResult = result;

    result = ImageUtils.getImage(result, 300, 300, Color.WHITE, 0);
    this.result.setPreferredSize(new Dimension(300, 300));
    this.result.setImage(result);
  }

  private void setInput(IModel model) {
    BufferedImage[] inputs = model.getInput();
    if (inputs == this.currentInputs) {
      return;
    }

    if (inputs == null) {
      this.input.setImage(null);
      return;
    }

    this.currentInputs = inputs;

    BufferedImage input = new BufferedImage(inputs[0].getWidth() * inputs.length,
        inputs[0].getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D) input.getGraphics();
    for (int i = 0; i < inputs.length; i++) {
      g2d.drawImage(inputs[i], i * inputs[0].getWidth(), 0, null);
    }

    input.flush();
    input = ImageUtils.getImage(input, 300 * inputs.length, 300, Color.WHITE, 0);
    this.input.setPreferredSize(new Dimension(300 * inputs.length, 300));
    this.input.setImage(input);

  }

  private void createRightPane() {
    JPanel rightPane = new JPanel();
    rightPane.setPreferredSize(new Dimension(300, 300));
    var gbc = new GBCBuilder().anchor(GridBagConstraints.EAST).insets(0, 0, 5, 0)
        .fill(GridBagConstraints.VERTICAL).gridx(1).build();
    this.add(rightPane, gbc);

    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[] { 1.0 };
    layout.rowWeights = new double[] { Double.MIN_VALUE, 1.0 };
    rightPane.setLayout(layout);

    this.createResult(rightPane);
    this.createButtonRight(rightPane);

  }

  private void createResult(JPanel rightPane) {
    this.result = new ImagePanel(null);
    this.result.setSize(new Dimension(300, 300));
    this.result.setPreferredSize(new Dimension(300, 300));

    var gbc = new GBCBuilder().insets(0, 0, 5, 0).fill(GridBagConstraints.BOTH).build();
    rightPane.add(this.result, gbc);

  }

  private void createButtonRight(JPanel rightPane) {

    JPanel buttonRight = new JPanel();
    var gbc = new GBCBuilder().anchor(GridBagConstraints.EAST).fill(GridBagConstraints.VERTICAL)
        .gridy(1).build();
    rightPane.add(buttonRight, gbc);

    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[] { 0.0, 0.0, 0.0 };
    layout.rowWeights = new double[] { 0.0 };
    buttonRight.setLayout(layout);

    this.btnShowCurve = new JButton("Show Curve");
    gbc = new GBCBuilder().insets(0, 0, 0, 5).build();
    buttonRight.add(this.btnShowCurve, gbc);

    this.btnSaveCurve = new JButton("Save Curve");
    gbc = new GBCBuilder().insets(0, 0, 0, 5).gridx(1).build();
    buttonRight.add(this.btnSaveCurve, gbc);

    this.btnSaveHDR = new JButton("Save HDR");
    gbc = new GBCBuilder().insets(0, 0, 0, 5).gridx(2).build();
    buttonRight.add(this.btnSaveHDR, gbc);

  }

  private void createLeftPane() {
    var gbc = new GBCBuilder().anchor(GridBagConstraints.WEST).fill(GridBagConstraints.VERTICAL)
        .insets(0, 0, 5, 5).build();

    this.input = new ImagePanel(null);
    this.input.setSize(new Dimension(300, 280));
    this.input.setPreferredSize(new Dimension(300, 300));

    ScrollPane leftPane = new ScrollPane();
    leftPane.setPreferredSize(new Dimension(300, 300));
    leftPane.add(this.input);
    this.add(leftPane, gbc);

  }

  /**
   * Lock all components.
   */
  public void lock() {
    this.btnSaveCurve.setEnabled(false);
    this.btnShowCurve.setEnabled(false);
    this.btnSaveHDR.setEnabled(false);

  }

  /**
   * Enable or Disable according to model state.
   *
   * @param canShowOrSaveCurve
   *          indicates that {@link ICameraCurve} can be saved or shown.
   * @param canSaveHDR
   *          indicates that a result is present
   */
  public void enOrDisableButtons(boolean canShowOrSaveCurve, boolean canSaveHDR) {
    this.btnSaveCurve.setEnabled(canShowOrSaveCurve);
    this.btnShowCurve.setEnabled(canShowOrSaveCurve);
    this.btnSaveHDR.setEnabled(canSaveHDR);
  }
}
