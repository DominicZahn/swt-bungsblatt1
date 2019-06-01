package org.iMage.HDrize;

import javax.imageio.ImageIO;

import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImageIO;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link HDRCombine}.
 *
 * @author Dominik Fuchss
 *
 */
public class HDRCombineTest extends TestBase {
  private HDRCombine combine;

  private EnhancedImage[] images;

  /**
   * Setup tests.
   *
   * @throws Exception
   *           iff setup not possible
   */
  @Before
  public void setup() throws Exception {
    this.combine = new HDRCombine();
    this.images = new EnhancedImage[] {
        new EnhancedImage(this.getClass().getResourceAsStream("/image/1_10.jpg")),
        new EnhancedImage(this.getClass().getResourceAsStream("/image/1_25.jpg")),
        new EnhancedImage(this.getClass().getResourceAsStream("/image/1_80.jpg")) };
  }

  /**
   * Test whether the weights of {@link HDRCombine#calculateWeights()} calculated correctly.
   */
  @Test
  public void testWeights() {
    float[] results = this.combine.calculateWeights();
    //@formatter:off
    float[] expected = new float[] {1.0F, 1.05F, 1.0999999F, 1.1499999F, 1.1999998F, 1.2499998F, 1.2999997F, 1.3499997F, 1.3999996F, 1.4499996F, 1.4999995F, 1.5499995F, 1.5999994F, 1.6499994F, 1.6999993F, 1.7499993F, 1.7999992F, 1.8499992F, 1.8999991F, 1.9499991F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 1.9499991F, 1.8999991F, 1.8499992F, 1.7999992F, 1.7499993F, 1.6999993F, 1.6499994F, 1.5999994F, 1.5499995F, 1.4999995F, 1.4499996F, 1.3999996F, 1.3499997F, 1.2999997F, 1.2499998F, 1.1999998F, 1.1499999F, 1.0999999F, 1.05F, 1.0F};
    //@formatter:on

    Assert.assertArrayEquals(expected, results, (float) TestBase.EPS);
  }

  /**
   * Test the {@link HDRCombine#createHDR(org.iMage.HDrize.base.ICameraCurve, EnhancedImage[])}
   * based on a given curve and given images.
   *
   * @throws Exception
   *           iff test went wrong
   */
  @Test
  public void testResults() throws Exception {
    CameraCurve cc = new CameraCurve(this.getClass().getResourceAsStream("/curve-800-20.0.bin"));
    var hdr = this.combine.createHDR(cc, this.images);
    var result = HDRImageIO.createRGB(hdr, ToneMapping.SRGBGamma);
    var expected = ImageIO.read(this.getClass().getResourceAsStream("/result.png"));

    TestBase.assertImageEquals(expected, result);
  }
}
