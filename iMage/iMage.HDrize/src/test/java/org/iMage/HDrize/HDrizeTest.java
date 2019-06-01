package org.iMage.HDrize;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImageIO;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.matrix.MatrixCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The tests of {@link HDrize}.
 *
 * @author Dominik Fuchss
 *
 */
public class HDrizeTest extends TestBase {
  private HDrize hdrize;

  private EnhancedImage[] images;

  /**
   * Setup tests.
   *
   * @throws Exception
   *           iff setup not possible
   */
  @Before
  public void setup() throws Exception {
    this.hdrize = new HDrize();
    this.images = new EnhancedImage[] {
        new EnhancedImage(this.getClass().getResourceAsStream("/image/1_10.jpg")),
        new EnhancedImage(this.getClass().getResourceAsStream("/image/1_25.jpg")),
        new EnhancedImage(this.getClass().getResourceAsStream("/image/1_80.jpg")) };
  }

  /**
   * Test {@link HDrize} with 800 samples (quite complex).
   *
   * @throws IOException
   *           iff image could not be read.
   */
  @Test
  @Ignore("this might take a long time .. ~300s (if parallel) => ~900s")
  public void manySamplesWorkflow() throws IOException {
    var hdr = this.hdrize.createHDR(this.images, 800, 20, new MatrixCalculator());
    Assert.assertNotNull(hdr);
    var result = HDRImageIO.createRGB(hdr, ToneMapping.SRGBGamma);
    var expected = ImageIO.read(this.getClass().getResourceAsStream("/result.png"));
    TestBase.assertImageEquals(expected, result);
  }

  /**
   * Test {@link HDrize} with 24 samples (quite simple).
   *
   * @throws IOException
   *           iff image could not be read.
   */
  @Test
  public void smallImageWorkflowA() throws IOException {
    var hdr = this.hdrize.createHDR(this.images, 24, 20, new MatrixCalculator());
    Assert.assertNotNull(hdr);
    var result = HDRImageIO.createRGB(hdr, ToneMapping.SRGBGamma);
    var expected = ImageIO.read(this.getClass().getResourceAsStream("/result-24-20.png"));
    TestBase.assertImageEquals(expected, result);
  }

  /**
   * Test {@link HDrize} with 24 samples (quite simple, given curve).
   *
   * @throws IOException
   *           iff image could not be read.
   * @throws ClassNotFoundException
   *           iff loading of curve was not possible
   */
  @Test
  public void smallImageWorkflowB() throws IOException, ClassNotFoundException {
    CameraCurve cc = new CameraCurve(this.getClass().getResourceAsStream("/curve-24-20.0.bin"));
    var hdr = this.hdrize.createHDR(this.images, cc);
    Assert.assertNotNull(hdr);
    var result = HDRImageIO.createRGB(hdr, ToneMapping.SRGBGamma);
    var expected = ImageIO.read(this.getClass().getResourceAsStream("/result-24-20.png"));
    TestBase.assertImageEquals(expected, result);
  }

}
