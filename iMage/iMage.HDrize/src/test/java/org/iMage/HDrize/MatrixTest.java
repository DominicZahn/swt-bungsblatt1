package org.iMage.HDrize;

import org.iMage.HDrize.base.matrix.IMatrix;
import org.iMage.HDrize.matrix.Matrix;
import org.junit.Assert;
import org.junit.Test;

/**
 * The tests for {@link Matrix}.
 *
 * @author Dominik Fuchss
 */
public class MatrixTest extends TestBase {
  /**
   * Test creation with invalid arguments (one dim = -1).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor1A1() {
    new Matrix(-1, 1);
  }

  /**
   * Test creation with invalid arguments (one dim = 0).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor1A0() {
    new Matrix(0, 1);
  }

  /**
   * Test creation with invalid arguments (one dim = -1).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor1B1() {
    new Matrix(1, -1);
  }

  /**
   * Test creation with invalid arguments (one dim = 0).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor1B0() {
    new Matrix(1, 0);
  }

  /**
   * Test creation with invalid arguments (matrix = null).
   */
  @Test(expected = NullPointerException.class)
  public void testIllegalConstructor2() {
    new Matrix((IMatrix) null);
  }

  /**
   * Test creation with invalid arguments (double[][] = null).
   */
  @Test(expected = NullPointerException.class)
  public void testIllegalConstructor3() {
    new Matrix((double[][]) null);
  }

  /**
   * Test creation with invalid arguments (double[0][0]).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor4() {
    new Matrix(new double[0][0]);
  }

  /**
   * Test creation with invalid arguments (double[]{double[1],double[2]}).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor5a() {
    new Matrix(new double[][] { new double[1], new double[2] });
  }

  /**
   * Test creation with invalid arguments (double[]{double[2],double[1]}).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor5b() {
    new Matrix(new double[][] { new double[2], new double[1] });
  }

  /**
   * Test order of dimensions.
   */
  @Test
  public void testDimensions() {
    double[][] shall = new double[][] { //
        { 1, 0, 0 }, //
        { 0, 2, 0 }, //
    };

    Matrix matrix = new Matrix(3, 1);
    Assert.assertEquals(3, matrix.rows());
    Assert.assertEquals(1, matrix.cols());

    matrix = new Matrix(shall);
    Assert.assertEquals(2, matrix.rows());
    Assert.assertEquals(3, matrix.cols());

    matrix = new Matrix(matrix);
    Assert.assertEquals(2, matrix.rows());
    Assert.assertEquals(3, matrix.cols());
  }

  /**
   * Test whether matrix operations creating copies.
   */
  @Test
  public void testCopy() {
    double[][] shall = new double[][] { //
        { 1, 0, 0 }, //
        { 0, 0, 0 }, //
        { 0, 0, 0 }, //
    };

    Matrix matrix = new Matrix(3, 3);
    matrix.set(0, 0, 1);
    // Check Matrix
    this.assertEquals(this.copy(shall), matrix);

    // Check copy
    double[][] copy = matrix.copy();
    Assert.assertArrayEquals(shall, copy);

    // Now modify
    copy[0][0] = 42;
    this.assertEquals(shall, matrix);

    // Now check copy operations for constructor.
    copy = this.copy(shall);
    matrix = new Matrix(copy);
    matrix.set(0, 0, 42);
    Assert.assertArrayEquals(shall, copy);
  }

  /**
   * Test {@link Matrix#Matrix(IMatrix)}.
   */
  @Test
  public void testMatrixToMatrix() {
    double[][] shall = new double[][] { //
        { 1, 2, 3 }, //
        { 6, 5, 4 }, //
        { 7, 8, 9 }, //
    };

    Matrix matrix = new Matrix(this.copy(shall));
    Matrix copy = new Matrix(matrix);

    this.assertEquals(shall, matrix);
    this.assertEquals(shall, copy);
  }

  private double[][] copy(double[][] mtx) {
    double[][] copy = new double[mtx.length][mtx[0].length];
    for (int r = 0; r < mtx.length; r++) {
      for (int c = 0; c < mtx[r].length; c++) {
        copy[r][c] = mtx[r][c];
      }
    }
    return copy;
  }

}
