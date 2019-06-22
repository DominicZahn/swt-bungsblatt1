package org.iMage.iCatcher.model;

import java.awt.image.BufferedImage;

import org.iMage.HDrize.CameraCurve;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDrize;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.iCatcher.util.IObserver;

/**
 * Defines the interface to the model of MVC.
 *
 * @author Dominik Fuchss
 *
 */
public interface IModel {
  /**
   * Get a new {@link IModel} of iDeal.
   *
   * @return a new {@link IModel}
   */
  static IModel create() {
    return new Model();
  }

  /**
   * Register an {@link IObserver}.
   *
   * @param observer
   *          the observer
   */
  void registerObserver(IObserver<? super IModel> observer);

  /**
   * Get the current {@link CurveMode}.
   *
   * @return the current mode
   */
  CurveMode getCurveMode();

  /**
   * Set the current {@link CurveMode}.
   *
   * @param cameraCurveMode
   *          the new mode
   */
  void setCameraCurveMode(CurveMode cameraCurveMode);

  /**
   * Get the current lambda.
   *
   * @return the current lambda
   */
  float getLambda();

  /**
   * Set the current lambda.
   *
   * @param lambda
   *          the new lambda
   * @return indicator of success
   */
  boolean setLambda(float lambda);

  /**
   * Get the current samples.
   *
   * @return the current samples
   */
  int getSamples();

  /**
   * Set the current samples.
   *
   * @param samples
   *          the new samples
   * @return indicator of success
   */
  boolean setSamples(int samples);

  /**
   * Set the current input files.
   *
   * @param longestCommenPrefix
   *          the longest common prefix of the file names
   * @param originals
   *          the original {@link BufferedImage Images}
   * @param images
   *          the images as {@link EnhancedImage}
   */
  void setInput(String longestCommenPrefix, BufferedImage[] originals, EnhancedImage[] images);

  /**
   * Get the current input files.
   *
   * @return the current input files
   */
  BufferedImage[] getInput();

  /**
   * Set the current {@link ToneMapping}.
   *
   * @param mapping
   *          the current mapping
   */
  void setMapping(ToneMapping mapping);

  /**
   * Get the longest common prefix of the input files.
   *
   * @return the longest common prefix of the input files
   */
  String getCommonPrefix();

  /**
   * Get the result as image.
   *
   * @return the result
   */
  BufferedImage getResult();

  /**
   * Execute {@link IHDrize}.
   *
   * @return indicator of success
   */
  boolean runHdr();

  /**
   * Get the current camera curve.
   *
   * @return the current camera curve
   */
  ICameraCurve getCurve();

  /**
   * Get a name for the current curve.
   *
   * @return the name of the current curve
   */
  String getCurveName();

  /**
   * Load a camera curve.
   *
   * @param name
   *          the name of the camera curve
   * @param cc
   *          the curve
   */
  void loadCurve(String name, CameraCurve cc);

  /**
   * Indicates whether a loaded curve exists.
   *
   * @return indicator
   */
  boolean isCurveLoaded();

  /**
   * Inform all observers.
   */
  void informObservers();

}
