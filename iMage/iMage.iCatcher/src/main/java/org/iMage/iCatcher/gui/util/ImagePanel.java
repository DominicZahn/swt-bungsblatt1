package org.iMage.iCatcher.gui.util;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Defines a {@link JPanel} which shows an image.
 *
 * @author Dominik Fuchss
 *
 */
public final class ImagePanel extends JPanel {
  private static final long serialVersionUID = 8641059952403890067L;
  private BufferedImage image;

  /**
   * Create the {@link JPanel}.
   *
   * @param image
   *          the initial image.
   */
  public ImagePanel(BufferedImage image) {
    this.image = image;
  }

  /**
   * Set the current image.
   *
   * @param image
   *          the new image
   */
  public void setImage(BufferedImage image) {
    this.image = image;
    this.getParent().repaint();
    this.getParent().revalidate();
  }

  /**
   * Get the current image.
   *
   * @return the current image
   */
  public BufferedImage getImage() {
    return this.image;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.image != null) {
      g.drawImage(this.image, 0, 0, this);
    }
  }

  /**
   * Add an {@link ActionListener} to this {@link JPanel}.
   * 
   * @param listener
   *          the listener
   */
  public void addActionListener(ActionListener listener) {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        listener.actionPerformed(
            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "clicked@" + this));
      }
    });

  }

}