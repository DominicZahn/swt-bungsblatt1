package org.iMage.iCatcher.gui.controller;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDrize;
import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.model.IModel;

/**
 * Defines the different Events a {@link IController} has to handle.
 *
 * @author Dominik Fuchss
 *
 */
public enum Events {
  /**
   * Raise an internal error.
   */
  INTERNAL_ERROR,
  /**
   * Raise parameter sync between {@link IModel} and {@link IView}.
   */
  PARAM_UPDATE,
  /**
   * Raise loading of input files.
   */
  LOAD_FOLDER,
  /**
   * Raise execution of {@link IHDrize}.
   */
  RUN_HDR,
  /**
   * Raise saving of result.
   */
  SAVE_HDR,
  /**
   * Raise opening of popup for input image.
   */
  SHOW_INPUT,
  /**
   * Raise opening of popup for result image.
   */
  SHOW_RESULT,

  /**
   * Raise loading of {@link ICameraCurve} from file.
   */
  LOAD_CURVE,
  /**
   * Raise saving of {@link ICameraCurve} to file.
   */
  SAVE_CURVE,
  /**
   * Raise opening of popup for the {@link ICameraCurve}.
   */
  SHOW_CURVE
}