package org.iMage.iCatcher.gui.util;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.iMage.HDrize.base.ICameraCurve;

/**
 * Defines a visualization of a {@link ICameraCurve}.
 *
 * @author Dominik Fuchss
 *
 */
public final class Graph extends JDialog {

  private static final long serialVersionUID = 5723437277327416358L;
  private final Canvas canvas;
  private final ICameraCurve cc;
  private static final int CANVAS_SIZE = 600;
  private static final int GRAPH_SIZE = 500;
  private static final int XY_OFFSET = 55;
  private static final int TEXT_SHIFT_PER_LETTER = 3;
  private static final int Y_STEPS = 5;

  /**
   * Create graph dialog for {@link ICameraCurve}.
   *
   * @param owner
   *          the owner of the dialog.
   * @param cc
   *          the camera curve
   * @param title
   *          the title of the dialog
   */
  public Graph(Frame owner, ICameraCurve cc, String title) {
    super(owner, title);
    this.setResizable(false);
    this.cc = Objects.requireNonNull(cc);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setLayout(new GridBagLayout());
    this.setSize(875, 620);
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
    this.setContentPane(contentPane);

    this.canvas = new Canvas() {
      private static final long serialVersionUID = -6009770344652622453L;

      @Override
      public void paint(Graphics g) {
        super.paint(g);
        Graph.this.render((Graphics2D) g);
      }
    };
    this.canvas.setSize(Graph.CANVAS_SIZE, Graph.CANVAS_SIZE);
    this.canvas.setPreferredSize(new Dimension(Graph.CANVAS_SIZE, Graph.CANVAS_SIZE));
    contentPane.add(this.canvas, new GBCBuilder().build());
  }

  private void render(Graphics2D g2d) {
    if (this.cc.isCalculated()) {
      this.drawGraph(g2d);
    } else {

      try {
        var image = ImageIO.read(//
            this.getClass().getResourceAsStream("/gui/curve-not-calculated.png"));
        g2d.drawImage(image, Graph.CANVAS_SIZE / 2 - image.getWidth() / 2, 20, null);
      } catch (IOException e) {
        e.printStackTrace();
        this.dispose();
      }

    }
  }

  private void drawGraph(Graphics2D g2d) {
    double[] r = new double[256];
    double[] g = new double[256];
    double[] b = new double[256];
    double max = Double.MIN_VALUE;

    for (int i = 0; i < 256; i++) {
      float[] resp = this.cc.getResponse(new int[] { i, i, i });
      r[i] = resp[0];
      if (r[i] > max) {
        max = r[i];
      }
      g[i] = resp[1];
      if (g[i] > max) {
        max = g[i];
      }
      b[i] = resp[2];
      if (b[i] > max) {
        max = b[i];
      }

    }

    max = Math.ceil(max) + 0.5;

    g2d.setStroke(new BasicStroke(1));

    g2d.setColor(Color.BLACK);
    for (int i = 0; i < Graph.Y_STEPS; i++) {
      double yVal = max / Graph.Y_STEPS * i;
      int y = Graph.GRAPH_SIZE - (int) ((yVal / max) * Graph.GRAPH_SIZE) - Graph.XY_OFFSET;
      String text = String.format("%.1f", yVal);
      g2d.drawString(text, 0, y - Graph.TEXT_SHIFT_PER_LETTER * text.length());
      g2d.drawLine(0, y, Graph.GRAPH_SIZE + Graph.XY_OFFSET, y);
    }

    for (int i = 0; i < 256; i++) {
      int x = (int) ((i) * (Graph.GRAPH_SIZE / 256.f)) + Graph.XY_OFFSET;

      g2d.setColor(Color.RED);
      int y = Graph.GRAPH_SIZE - (int) ((r[i] / max) * Graph.GRAPH_SIZE) - Graph.XY_OFFSET;
      g2d.drawOval(x, y, 2, 2);

      g2d.setColor(Color.GREEN);
      y = Graph.GRAPH_SIZE - (int) ((g[i] / max) * Graph.GRAPH_SIZE) - Graph.XY_OFFSET;
      g2d.drawOval(x, y, 4, 4);

      g2d.setColor(Color.BLUE);
      y = Graph.GRAPH_SIZE - (int) ((b[i] / max) * Graph.GRAPH_SIZE) - Graph.XY_OFFSET;
      g2d.drawOval(x, y, 3, 3);

      if (i % 50 == 0) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(i),
            x - Graph.TEXT_SHIFT_PER_LETTER * String.valueOf(i).length(), Graph.GRAPH_SIZE);
        g2d.drawLine(x, 0, x, Graph.GRAPH_SIZE - 20);
      }

    }
  }

}
