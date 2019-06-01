package org.iMage.HDrize;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDRCombine;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;

/**
 * Implementation of {@link IHDRCombine}.
 */
public class HDRCombine implements IHDRCombine {

  private static final int CHANNELS = 3;

  @Override
  public HDRImage createHDR(ICameraCurve curve, EnhancedImage[] imageList) {
    int width = imageList[0].getWidth();
    int height = imageList[0].getHeight();

    float[][][] result = new float[][][] { //
        new float[height][width], //
        new float[height][width], //
        new float[height][width]//
    };

    float[][][] numerator = new float[][][] { //
        new float[height][width], //
        new float[height][width], //
        new float[height][width]//
    };

    float[][][] denominator = new float[][][] { //
        new float[height][width], //
        new float[height][width], //
        new float[height][width]//
    };

    float[] w = this.calculateWeights();

    for (EnhancedImage ei : imageList) {
      float exposure = ei.getExposureTime();

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          int[] color = ei.getRGB(x, y);
          float[] resp = curve.getResponse(color);

          for (int c = 0; c < HDRCombine.CHANNELS; c++) {
            numerator[c][y][x] += w[color[c]] * resp[c] / exposure;
            denominator[c][y][x] += w[color[c]];
          }

        }
      }

    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int c = 0; c < HDRCombine.CHANNELS; c++) {
          result[c][y][x] = numerator[c][y][x] / denominator[c][y][x];
        }
      }
    }

    return new HDRImage(result[0], result[1], result[2]);
  }

  @Override
  public float[] calculateWeights() {
    final int offset = 20;

    float[] weight = new float[256];
    weight[0] = 1f;

    for (int a = 1; a < offset; a++) {
      weight[a] = (float) (weight[a - 1] + (1.0 / offset));
    }
    for (int a = offset; a < (256 - offset); a++) {
      weight[a] = 2;
    }

    for (int a = 256 - offset; a < 256; a++) {
      weight[a] = weight[256 - a - 1];
    }

    return weight;
  }

}
