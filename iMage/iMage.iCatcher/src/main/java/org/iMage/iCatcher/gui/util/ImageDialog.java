package org.iMage.iCatcher.gui.util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
 * A simple {@link JDialog} which shows an image.
 *
 * @author Dominik Fuchss
 *
 */
public final class ImageDialog extends JDialog {

  private static final long serialVersionUID = -7890644581009122605L;

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  private ImagePanel imagePanel;

  /**
   * Create {@link ImageDialog} by owner title and image.
   *
   * @param owner
   *          the parent/owner of this dialog
   * @param title
   *          the title of the dialog
   * @param image
   *          the image
   */
  public ImageDialog(Frame owner, String title, BufferedImage image) {
    super(owner);
    this.setSize(ImageDialog.WIDTH, ImageDialog.HEIGHT);
    this.setResizable(true);
    this.setModalityType(ModalityType.APPLICATION_MODAL);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setTitle(title);
    this.setAlwaysOnTop(true);
    this.createContents(image);
  }

  private void createContents(BufferedImage image) {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 0 };
    gridBagLayout.rowHeights = new int[] { 0 };
    gridBagLayout.columnWeights = new double[] { 1.0 };
    gridBagLayout.rowWeights = new double[] { 1.0 };
    this.getContentPane().setLayout(gridBagLayout);

    this.imagePanel = new ImagePanel(image);
    this.imagePanel.setSize(image.getWidth(), image.getHeight());
    this.imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    this.getContentPane().add(
        new JScrollPane(this.imagePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS),
        new GBCBuilder().fill(GridBagConstraints.BOTH).build());
  }

}
