package org.iMage.iCatcher.gui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * This class contains some useful methods to work with {@link BufferedImage BufferedImages}.
 *
 * @author Dominik Fuchss
 * @version 2
 */
public final class ImageUtils {

  private ImageUtils() {
    throw new IllegalAccessError();
  }

  /**
   * Scale Image by width (set width and the height will calculated).
   *
   * @param input
   *          image to scale
   *
   * @param width
   *          the target width
   *
   * @return scaled image
   */
  public static BufferedImage scaleWidth(BufferedImage input, int width) {
    if (width <= 0) {
      throw new IllegalArgumentException("width cannot be <= 0");
    }
    Image scaled = input.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
    int height = scaled.getHeight(null);
    if (height <= 0) {
      throw new IllegalArgumentException("height would be 0");
    }
    BufferedImage res = new BufferedImage(width, height, input.getType());
    Graphics2D g2d = res.createGraphics();
    g2d.drawImage(scaled, 0, 0, null);
    g2d.dispose();
    res.flush();
    return res;
  }

  /**
   * Scale Image by width (set height and the width will calculated).
   *
   * @param input
   *          image to scale
   *
   * @param height
   *          the target width
   *
   * @return scaled image
   */
  public static BufferedImage scaleHeight(BufferedImage input, int height) {
    if (height <= 0) {
      throw new IllegalArgumentException("width cannot be <= 0");
    }
    Image scaled = input.getScaledInstance(-1, height, Image.SCALE_SMOOTH);
    int width = scaled.getWidth(null);
    if (width <= 0) {
      throw new IllegalArgumentException("width would be 0");
    }
    BufferedImage res = new BufferedImage(width, height, input.getType());
    Graphics2D g2d = res.createGraphics();
    g2d.drawImage(scaled, 0, 0, null);
    g2d.dispose();
    res.flush();
    return res;
  }

  /**
   * Create an image by path and dimensions.
   *
   * @param input
   *          the image
   * @param targetWidth
   *          the icon width
   * @param targetHeight
   *          the icon height
   * @param background
   *          the background color of the icon
   * @param padding
   *          the padding (xy in pixels) of the icon
   * @return the image
   */
  public static BufferedImage getImage(BufferedImage input, int targetWidth, int targetHeight,
      Color background, int padding) {
    BufferedImage image = input;
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    if (imageWidth > imageHeight) {
      // Landscape
      image = ImageUtils.scaleWidth(image, targetWidth - 2 * padding);
    } else {
      // Portrait
      image = ImageUtils.scaleHeight(image, targetHeight - 2 * padding);
    }

    BufferedImage result = new BufferedImage(targetWidth, targetHeight,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = result.createGraphics();
    g2d.setColor(background);
    g2d.fillRect(0, 0, targetWidth, targetHeight);
    int x = (targetWidth - 2 * padding - image.getWidth()) / 2 + padding;
    int y = (targetHeight - 2 * padding - image.getHeight()) / 2 + padding;
    g2d.drawImage(image, x, y, null);
    g2d.dispose();
    result.flush();

    return result;
  }

}
