package main.org.jis;

import org.jis.generator.Generator;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
   * creates a new generator object and reads the bufferedImage
   */
  @BeforeClass
  public void setUp() {
    generator = new Generator(null, 0);
    try {
      bufferedImage = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}