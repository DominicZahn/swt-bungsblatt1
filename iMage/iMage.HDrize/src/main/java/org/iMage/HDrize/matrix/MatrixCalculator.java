package org.iMage.HDrize.matrix;

import org.iMage.HDrize.base.matrix.IMatrix;
import org.iMage.HDrize.base.matrix.IMatrixCalculator;

/**
 * This class represents a simple implementation of {@link IMatrixCalculator} for {@link Matrix}.
 *
 * @author Dominik Fuchss
 *
 */
public class MatrixCalculator implements IMatrixCalculator<Matrix> {

  @Override
  public Matrix inverse(Matrix mtx) {
    double[][] inverse = this.inverse(mtx.copy());
    if (inverse != null) {
      return new Matrix(inverse);
    }
    return null;
  }

  // Fast Algorithm:
  // See https://github.com/williamfiset/Algorithms/blob/master/com/williamfiset/algorithms/linearalgebra/MatrixInverse.java
  private double[][] inverse(double[][] matrix) {
    if (matrix.length != matrix[0].length) {
      return null;
    }
    int n = matrix.length;
    double[][] augmented = new double[n][n * 2];
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < n; c++) {
        augmented[r][c] = matrix[r][c];
      }
      augmented[r][r + n] = 1;
    }
    this.solve(augmented);
    double[][] inv = new double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        inv[i][j] = augmented[i][j + n];
      }
    }

    IMatrix product = this.multiply(new Matrix(matrix), new Matrix(inv));
    if (!this.isIdentity(product)) {
      return null;
    }

    return inv;
  }

  private boolean isIdentity(IMatrix mtx) {
    if (mtx.cols() != mtx.rows()) {
      return false;
    }

    for (int r = 0; r < mtx.rows(); r++) {
      for (int c = 0; c < mtx.cols(); c++) {
        if (r == c && Math.abs(mtx.get(r, c) - 1) >= 1E-8) {
          return false;
        } else if (r != c && Math.abs(mtx.get(r, c)) >= 1E-8) {
          return false;
        }
      }
    }

    return true;
  }

  // Solves a system of linear equations as an augmented matrix
  // with the rightmost column containing the constants. The answers
  // will be stored on the rightmost column after the algorithm is done.
  // NOTE: make sure your matrix is consistent and does not have multiple
  // solutions before you solve the system if you want a unique valid answer.
  // Time Complexity: O(r²c)
  // See https://github.com/williamfiset/Algorithms/blob/master/com/williamfiset/algorithms/linearalgebra/MatrixInverse.java
  private void solve(double[][] augmentedMatrix) {
    int rows = augmentedMatrix.length;
    int cols = augmentedMatrix[0].length;
    int lead = 0;
    for (int r = 0; r < rows; r++) {

      // Find leading column ..
      if (lead >= cols) {
        break;
      }
      int i = r;
      while (Math.abs(augmentedMatrix[i][lead]) < 1E-8) {
        if (++i == rows) {
          i = r;
          if (++lead == cols) {
            return;
          }
        }
      }

      this.swap(augmentedMatrix, r, i);

      double lv = augmentedMatrix[r][lead];
      for (int j = 0; j < cols; j++) {
        augmentedMatrix[r][j] /= lv;
      }

      for (i = 0; i < rows; i++) {
        if (i != r) {
          lv = augmentedMatrix[i][lead];
          for (int j = 0; j < cols; j++) {
            augmentedMatrix[i][j] -= lv * augmentedMatrix[r][j];
          }
        }
      }
      lead++;
    }
  }

  private void swap(double[][] mtx, int i, int j) {
    double[] tmp = mtx[i];
    mtx[i] = mtx[j];
    mtx[j] = tmp;
  }

  @Override
  public Matrix multiply(Matrix a, Matrix b) {
    if (a.cols() != b.rows()) {
      return null;
    }

    Matrix res = new Matrix(a.rows(), b.cols());

    for (int c = 0; c < a.rows(); c++) {
      for (int d = 0; d < b.cols(); d++) {
        double sum = 0;
        for (int k = 0; k < b.rows(); k++) {
          sum = sum + a.get(c, k) * b.get(k, d);
        }

        res.set(c, d, sum);
      }
    }
    return res;
  }

  @Override
  public Matrix transpose(Matrix mtx) {
    Matrix res = new Matrix(mtx.cols(), mtx.rows());
    for (int r = 0; r < res.rows(); r++) {
      for (int c = 0; c < res.cols(); c++) {
        res.set(r, c, mtx.get(c, r));
      }
    }
    return res;
  }

}
