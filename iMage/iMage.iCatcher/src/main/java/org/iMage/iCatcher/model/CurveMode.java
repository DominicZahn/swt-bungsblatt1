package org.iMage.iCatcher.model;

import org.iMage.HDrize.CameraCurve;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.matrix.IMatrixCalculator;

/**
 * Defines the different sources of an {@link ICameraCurve}.
 *
 * @author Dominik Fuchss
 *
 */
public enum CurveMode {
  /**
   * The standard curve ({@link CameraCurve#CameraCurve()}).
   */
  StandardCurve,
  /**
   * The calculated curve
   * ({@link CameraCurve#CameraCurve(EnhancedImage[], int, double, IMatrixCalculator)}).
   */
  CalculatedCurve,
  /**
   * The loaded curve ({@link CameraCurve#CameraCurve(java.io.InputStream)}).
   */
  LoadedCurve;
}
