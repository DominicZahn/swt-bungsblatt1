package org.iMage.iCatcher.gui;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;

import org.iMage.HDrize.CameraCurve;
import org.iMage.HDrize.HDrize;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.iCatcher.model.CurveMode;
import org.iMage.iCatcher.model.IModel;

/**
 * Defines the interface of the view of the MVC.
 */
public interface IView {
  /**
   * Get a new {@link IView} by {@link IModel}.
   *
   * @param model
   *          the model
   * @return the new {@link IView}
   */
  static IView create(IModel model) {
    return new MainFrame(model);
  }

  /**
   * Start / Show the view.
   */
  void start();

  /**
   * Show a message to the user.
   *
   * @param title
   *          the title
   * @param message
   *          the message
   * @param error
   *          indicates whether error or not
   */
  void showMessage(String title, String message, boolean error);

  /**
   * Show an image to the user.
   *
   * @param title
   *          the tile of the image
   * @param image
   *          the image
   */
  void showImage(String title, BufferedImage image);

  /**
   * Start a DirDialog (open) for input files.
   *
   * @return the directory or {@code null}
   */
  File openDirectoryDialog();

  /**
   * Start a FileDialog (save) for Images.
   *
   * @return the image file or {@code null}
   */
  File saveImageFileDialog();

  /**
   * Start a FileDialog (open) for {@link CameraCurve}.
   *
   * @return the CameraCurve file or {@code null}
   */
  File openCurveFileDialog();

  /**
   * Start a FileDialog (save) for {@link CameraCurve}.
   *
   * @return the CameraCurve file or {@code null}
   */
  File saveCurveFileDialog();

  /**
   * Present a Y/N-Question to the user.
   *
   * @param title
   *          the title
   * @param question
   *          the question
   * @return indicates a <em>yes</em>
   */
  boolean askYesNoQuestion(String title, String question);

  // For controller interaction

  /**
   * Get the selected {@link CurveMode}.
   *
   * @return the selected {@link CurveMode}
   */
  CurveMode getCameraCurveMode();

  /**
   * Get the current lambda.
   *
   * @return the current lambda
   */
  String getLambda();

  /**
   * Get the current samples.
   *
   * @return the current samples
   */
  int getSamples();

  /**
   * Get the current {@link ToneMapping}.
   *
   * @return the current {@link ToneMapping}
   */
  ToneMapping getToneMapping();

  /**
   * Set whether {@link #getLambda()} is invalid.
   *
   * @param invalid
   *          indicator
   */
  void setLambdaToInvalid(boolean invalid);

  /**
   * Get the {@link IView} as {@link Frame}.
   *
   * @return the {@link IView} as {@link Frame}
   */
  Frame asFrame();

  /**
   * Lock all components.
   */
  void lock();

  /**
   * Enable or Disable according to model state.
   *
   * @param canRun
   *          indicates that {@link HDrize} can be executed.
   * @param canShowOrSaveCurve
   *          indicates that {@link ICameraCurve} can be saved or shown.
   * @param canSaveHDR
   *          indicates that a result is present
   */
  void enOrDisableButtons(boolean canRun, boolean canShowOrSaveCurve, boolean canSaveHDR);

}
