package main.org.jis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
  public void rotateImageNull0() {
    assertEquals(null, generator.rotateImage(null, 0.0));
  }

  /**
   * JUnit test that tests rotateImage with the inputs bufferedImage and 0.0.
   */
  @Test
  public void rotateImageBufferedImage0() {
    assertEquals(bufferedImage, generator.rotateImage(bufferedImage, 0.0));
  }
  
  /**
   * JUnit test that tests rotateImage with the inputs bufferedImage and 0.42
   */
  @Test(expected = IllegalArgumentException.class)
  public void rotateImageExceptionRotation042() {
    generator.rotateImage(bufferedImage, 0.42);
  }
  
  /**
   * JUnit test that checks if the picture is still the same after the rotation (90°).
   */
  @Test
  public void rotateImage90() {
    BufferedImage rotatedImage = generator.rotateImage(bufferedImage, Math.toRadians(90));
    boolean widthBool = bufferedImage.getWidth() == rotatedImage.getHeight();
    boolean heightBool = bufferedImage.getHeight() == rotatedImage.getWidth();
    assertTrue(widthBool);
    assertTrue(heightBool);
    if (heightBool && widthBool) {
      for (int x = 0; x < bufferedImage.getWidth(); x++) {
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
          int rotatedX = rotatedImage.getWidth() - y - 1;
          int rotatedY = x;
          assertEquals(bufferedImage.getRGB(x, y), rotatedImage.getRGB(rotatedX, rotatedY));
        }
      }
    }
  }
  
  /**
   * JUnit test that checks if the picture is still the same after the rotation (90°).
   */
  @Test
  public void rotateImage270() {
    BufferedImage rotatedImage = generator.rotateImage(bufferedImage, Math.toRadians(270));
    boolean widthBool = bufferedImage.getWidth() == rotatedImage.getHeight();
    boolean heightBool = bufferedImage.getHeight() == rotatedImage.getWidth();
    assertTrue(widthBool);
    assertTrue(heightBool);
    if (heightBool && widthBool) {
      for (int x = 0; x < bufferedImage.getWidth(); x++) {
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
          int rotatedX = y;
          int rotatedY = rotatedImage.getHeight() - x - 1;
          assertEquals(bufferedImage.getRGB(x, y), rotatedImage.getRGB(rotatedX, rotatedY));
        }
      }
    }
  }
  
  /**
   * JUnit test that checks if -90° generates the same picture as 270°.
   */
  @Test
  public void rotateImageNegativ90() {
    System.out.println("negativ 90");
    BufferedImage rotatedImageNegativ = generator.rotateImage(bufferedImage, Math.toRadians(-90));
    BufferedImage rotatedImagePositiv = generator.rotateImage(bufferedImage, Math.toRadians(270));
    assertEquals(rotatedImageNegativ.getHeight(), bufferedImage.getWidth());
    assertEquals(rotatedImageNegativ.getWidth(), bufferedImage.getHeight());
    assertTrue(bufferedImageEquals(rotatedImageNegativ, rotatedImagePositiv));
  }
  
  /**
   * JUnit test that checks if -270° generates the same picture as 90°.
   */
  @Test
  public void rotateImageNegativ270() {
    System.out.println("negativ 270");
    BufferedImage rotatedImageNegativ = generator.rotateImage(bufferedImage, Math.toRadians(-270));
    BufferedImage rotatedImagePositiv = generator.rotateImage(bufferedImage, Math.toRadians(90));
    assertEquals(rotatedImageNegativ.getHeight(), bufferedImage.getWidth());
    assertEquals(rotatedImageNegativ.getWidth(), bufferedImage.getHeight());
    assertTrue(bufferedImageEquals(rotatedImageNegativ, rotatedImagePositiv));
  }
  
  private boolean bufferedImageEquals(BufferedImage image1, BufferedImage image2) {
    if (image1.getHeight() != image2.getHeight()) {
      return false;
    }
    if (image1.getWidth() != image2.getWidth()) {
      return false;
    }
    for (int x = 0; x < image1.getWidth(); x++) {
      for (int y = 0; y < image2.getHeight(); y++) {
        int rgb1 = image1.getRGB(x, y);
        int rgb2 = image2.getRGB(x, y);
        if (rgb1 != rgb2) {
          return false;
        }
      }
    }
    return true;
  }
  
}