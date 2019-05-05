package main.org.jis;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.jis.generator.Generator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RotateTest {
  private Generator generator;
  private BufferedImage bufferedImage;
  private final String imagePath = "src/test/resources/image.jpg";

  /**
   * Creates a new generator object and reads the bufferedImage.
   */
  @Before
  public void setUp() {
    generator = new Generator(null, 0);
    try {
      bufferedImage = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * JUnit test that tests rotateImage with the inputs null and 0.0.
   */
  @Test
  public void rotateImageNullRotation0() {
    assertEquals(null, generator.rotateImage(null, 0.0));
  }

  /**
   * JUnit test that tests rotateImage with the inputs bufferedImage and 0.0.
   */
  @Test
  public void rotateImageBufferedImageRotation0() {
    assertEquals(bufferedImage, generator.rotateImage(bufferedImage, 0.0));
  }
  
  /**
   * JUnit test that tests rotateImage with the inputs bufferedImage and 0.42
   */
  @Test(expected = IllegalArgumentException.class)
  public void rotateImageBufferedImageRotation042() {
    generator.rotateImage(bufferedImage, 0.42);
  }
}