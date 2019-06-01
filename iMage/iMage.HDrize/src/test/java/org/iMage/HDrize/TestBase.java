package org.iMage.HDrize;

import java.awt.image.BufferedImage;

import org.iMage.HDrize.base.matrix.IMatrix;
import org.iMage.HDrize.matrix.Matrix;
import org.junit.Assert;

/**
 * The base of all tests.
 *
 * @author Dominik Fuchss
 *
 */
public class TestBase {
  /**
   * The Epsilon for float comparison.
   */
  protected static final double EPS = 1E-8;

  /**
   * Check whether two matrices are equal.
   *
   * @param expected
   *          the expected matrix
   * @param actual
   *          the given matrix
   */
  protected final void assertEquals(double[][] expected, Matrix actual) {
    Assert.assertEquals(expected.length, actual.rows());
    Assert.assertEquals(expected[0].length, actual.cols());

    for (int r = 0; r < actual.rows(); r++) {
      for (int c = 0; c < actual.cols(); c++) {
        Assert.assertEquals("Matrix differ at " + r + "," + c, //
            expected[r][c], actual.get(r, c), TestBase.EPS);
      }
    }

  }

  /**
   * Check whether two matrices are equal.
   *
   * @param expected
   *          the expected matrix
   * @param actual
   *          the given matrix
   */
  protected final void assertEquals(Matrix expected, Matrix actual) {
    Assert.assertEquals(expected.rows(), actual.rows());
    Assert.assertEquals(expected.cols(), actual.cols());

    for (int r = 0; r < actual.rows(); r++) {
      for (int c = 0; c < actual.cols(); c++) {
        Assert.assertEquals("Matrix differ at " + r + "," + c, //
            expected.get(r, c), actual.get(r, c), TestBase.EPS);
      }
    }
  }

  /**
   * Check whether a matrix is the identity.
   *
   * @param mtx
   *          the matrix
   * @return the indicator
   */
  protected final boolean isIdentity(IMatrix mtx) {
    if (mtx.cols() != mtx.rows()) {
      return false;
    }

    for (int r = 0; r < mtx.rows(); r++) {
      for (int c = 0; c < mtx.cols(); c++) {
        if (r == c && Math.abs(mtx.get(r, c) - 1) >= TestBase.EPS) {
          return false;
        } else if (r != c && Math.abs(mtx.get(r, c)) >= TestBase.EPS) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Check if two images are identical - pixel wise.
   *
   * @param expected
   *          the expected image
   * @param actual
   *          the actual image
   */
  protected static void assertImageEquals(BufferedImage expected, BufferedImage actual) {
    if (expected == null || actual == null) {
      Assert.fail("One or more was null");
    }

    if (expected.getHeight() != actual.getHeight()) {
      Assert.fail("Height mismatch");
    }

    if (expected.getWidth() != actual.getWidth()) {
      Assert.fail("Width mismatch");
    }

    for (int i = 0; i < expected.getHeight(); i++) {
      for (int j = 0; j < expected.getWidth(); j++) {
        if (expected.getRGB(j, i) != actual.getRGB(j, i)) {
          Assert.fail("RGB mismatch at " + j + "," + i);
        }
      }
    }
  }
}
