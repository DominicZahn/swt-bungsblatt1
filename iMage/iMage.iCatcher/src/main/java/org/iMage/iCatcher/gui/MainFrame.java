package org.iMage.iCatcher.gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.iCatcher.gui.components.LowerPanel;
import org.iMage.iCatcher.gui.components.UpperPanel;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.gui.util.DirChooser;
import org.iMage.iCatcher.gui.util.FileChooser;
import org.iMage.iCatcher.gui.util.GBCBuilder;
import org.iMage.iCatcher.gui.util.ImageDialog;
import org.iMage.iCatcher.model.CurveMode;
import org.iMage.iCatcher.model.IModel;

/**
 * The MainFrame of the project (the {@link IView} of MVC).
 *
 * @author Dominik Fuchss
 *
 */
final class MainFrame extends JFrame implements IView {

  private static final long serialVersionUID = 2190905544097716983L;

  private JPanel contentPane;

  private UpperPanel upper;
  private LowerPanel lower;

  private final FileChooser ifc;
  private final FileChooser cfc;
  private final DirChooser dc;

  /**
   * Create the main frame.
   *
   * @param model
   *          the model
   */
  MainFrame(IModel model) {
    this.ifc = new FileChooser(this, "Image Files", "png");
    this.cfc = new FileChooser(this, "Curve Files", "bin");
    this.dc = new DirChooser(this);

    this.createContentPane();

    this.init(model);
    this.createController(model);
  }

  private void init(IModel model) {
    this.lower.init(model);
    this.upper.init(model);
  }

  private void createController(IModel model) {
    IController controller = IController.create(model, this);
    this.upper.register(controller);
    this.lower.register(controller);
    controller.handleEvent(Events.PARAM_UPDATE);
  }

  private void createContentPane() {
    this.setTitle("iCatcher");
    this.setResizable(false);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setBounds(-1, -1, 650, 600);
    this.setLocationRelativeTo(null);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.setContentPane(this.contentPane);
    GridBagLayout gblContentPane = new GridBagLayout();
    gblContentPane.rowWeights = new double[] { 1.0, 1.0 };
    gblContentPane.columnWeights = new double[] { 1 };
    this.contentPane.setLayout(gblContentPane);

    this.createUpperPanel();
    this.createLowerPanel();

  }

  private void createUpperPanel() {
    this.upper = new UpperPanel();
    var gbc = new GBCBuilder().fill(GridBagConstraints.BOTH).insets(0, 0, 5, 0).build();
    this.contentPane.add(this.upper, gbc);
  }

  private void createLowerPanel() {
    this.lower = new LowerPanel();
    this.contentPane.add(this.lower,
        new GBCBuilder().fill(GridBagConstraints.BOTH).gridy(1).build());
  }

  @Override
  public void start() {
    this.setVisible(true);
  }

  @Override
  public void showMessage(String title, String message, boolean error) {
    JOptionPane.showMessageDialog(this, message, title,
        error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showImage(String title, BufferedImage image) {
    new ImageDialog(this, title, image).setVisible(true);
  }

  @Override
  public boolean askYesNoQuestion(String title, String question) {
    int result = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION);
    return result == JOptionPane.YES_OPTION;
  }

  @Override
  public CurveMode getCameraCurveMode() {
    return this.lower.getCameraCurveMode();
  }

  @Override
  public String getLambda() {
    return this.lower.getLambda();
  }

  @Override
  public int getSamples() {
    return this.lower.getSamples();
  }

  @Override
  public void setLambdaToInvalid(boolean invalid) {
    this.lower.setLambdaToInvalid(invalid);
  }

  @Override
  public File openDirectoryDialog() {
    return this.dc.selectDirectory();
  }

  @Override
  public File saveImageFileDialog() {
    return this.ifc.saveFile();
  }

  @Override
  public File openCurveFileDialog() {
    return this.cfc.openFile();
  }

  @Override
  public File saveCurveFileDialog() {
    return this.cfc.saveFile();
  }

  @Override
  public ToneMapping getToneMapping() {
    return this.lower.getToneMapping();
  }

  @Override
  public Frame asFrame() {
    return this;
  }

  @Override
  public void lock() {
    this.lower.lock();
    this.upper.lock();
  }

  @Override
  public void enOrDisableButtons(boolean canRun, boolean canShowOrSaveCurve, boolean canSaveHDR) {
    this.lower.enOrDisableButtons(canRun);
    this.upper.enOrDisableButtons(canShowOrSaveCurve, canSaveHDR);
  }

}
