package org.iMage.iCatcher.gui.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * A builder for {@link GridBagConstraints}.
 *
 * @author Dominik Fuchss
 *
 */
public final class GBCBuilder {

  private GridBagConstraints gbc = new GridBagConstraints();

  /**
   * Set {@link GridBagConstraints#insets}.
   *
   * @param top
   *          {@link Insets#top}
   * @param left
   *          {@link Insets#left}
   * @param bottom
   *          {@link Insets#bottom}
   * @param right
   *          {@link Insets#right}
   * @return {@code this}
   */
  public GBCBuilder insets(int top, int left, int bottom, int right) {
    this.gbc.insets = new Insets(top, left, bottom, right);
    return this;
  }

  /**
   * Set {@link GridBagConstraints#fill}.
   *
   * @param fill
   *          {@link GridBagConstraints#fill}
   * @return {@code this}
   */
  public GBCBuilder fill(int fill) {
    this.gbc.fill = fill;
    return this;
  }

  /**
   * Set {@link GridBagConstraints#gridx}.
   *
   * @param gridx
   *          {@link GridBagConstraints#gridx}
   * @return {@code this}
   */
  public GBCBuilder gridx(int gridx) {
    this.gbc.gridx = gridx;
    return this;
  }

  /**
   * Set {@link GridBagConstraints#gridy}.
   *
   * @param gridy
   *          {@link GridBagConstraints#gridy}
   * @return {@code this}
   */
  public GBCBuilder gridy(int gridy) {
    this.gbc.gridy = gridy;
    return this;
  }

  /**
   * Set {@link GridBagConstraints#gridwidth}.
   *
   * @param gridwidth
   *          {@link GridBagConstraints#gridwidth}
   * @return {@code this}
   */
  public GBCBuilder gridW(int gridwidth) {
    this.gbc.gridwidth = gridwidth;
    return this;
  }

  /**
   * Set {@link GridBagConstraints#anchor}.
   *
   * @param anchor
   *          {@link GridBagConstraints#anchor}
   * @return {@code this}
   */
  public GBCBuilder anchor(int anchor) {
    this.gbc.anchor = anchor;
    return this;
  }

  /**
   * Create the {@link GridBagConstraints}.
   *
   * @return a copy of the current configuration ({@link GridBagConstraints})
   */
  public GridBagConstraints build() {
    return (GridBagConstraints) this.gbc.clone();
  }

}
