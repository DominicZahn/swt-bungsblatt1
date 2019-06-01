package org.iMage.HDrize.matrix;

import org.iMage.HDrize.base.matrix.IMatrix;

/**
 * This class represents a simple matrix.
 *
 * @author Dominik Fuchss
 */
public final class Matrix implements IMatrix {
  private final int rows;
  private final int cols;
  private final double[][] data;

  /**
   * Create a new matrix.
   *
   * @param mtx the original matrix
   */
  public Matrix(IMatrix mtx) {
    this(mtx.copy());
  }

  /**
   * Create a new matrix.
   *
   * @param mtx the original matrix mtx[Rows][Cols]
   */
  public Matrix(double[][] mtx) {
    this(mtx.length, mtx.length == 0 ? 0 : mtx[0].length);
    for (int r = 0; r < this.rows; r++) {
      if (mtx[r].length != this.cols) {
        throw new IllegalArgumentException("Rows have not equal lengths.");
      }
      for (int c = 0; c < this.cols; c++) {
        this.set(r, c, mtx[r][c]);
      }
    }
  }

  /**
   * Create a matrix (only zeros).
   *
   * @param rows the amount of rows
   * @param cols the amount of columns
   */
  public Matrix(int rows, int cols) {
    if (rows < 1 || cols < 1) {
      throw new IllegalArgumentException("rows and cols have to be >= 1");
    }
    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows][cols];
  }

  @Override
  public double[][] copy() {
    double[][] copy = new double[this.rows][this.cols];
    for (int r = 0; r < this.rows; r++) {
      for (int c = 0; c < this.cols; c++) {
        copy[r][c] = this.data[r][c];
      }
    }
    return copy;
  }

  @Override
  public int rows() {
    return this.rows;
  }

  @Override
  public int cols() {
    return this.cols;
  }

  @Override
  public void set(int r, int c, double v) {
    this.data[r][c] = v;

  }

  @Override
  public double get(int r, int c) {
    return this.data[r][c];
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{\n");
    for (int r = 0; r < this.rows(); r++) {
      builder.append("{");
      for (int c = 0; c < this.cols(); c++) {
        builder.append(this.get(r, c));
        if (c != this.cols() - 1) {
          builder.append(", ");
        }
      }
      builder.append("}\n");
    }
    builder.append("}");
    return builder.toString();
  }

}
