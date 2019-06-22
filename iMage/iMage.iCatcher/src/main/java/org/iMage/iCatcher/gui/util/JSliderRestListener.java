package org.iMage.iCatcher.gui.util;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A simple listener for a {@link JSlider}, which will fire event after modification (if slider
 * rests).
 *
 * @author Dominik Fuchss
 *
 */
public final class JSliderRestListener {

  private final ChangeListener delegate;
  private final JSlider slider;

  /**
   * Create a {@link JSliderRestListener} and attach {@link ChangeListener}.
   *
   * @param slider
   *          the slider
   * @param delegate
   *          the delegate
   * @return this {@link JSliderRestListener}
   */
  public static JSliderRestListener create(JSlider slider, ChangeListener delegate) {
    return new JSliderRestListener(slider, delegate);
  }

  private JSliderRestListener(JSlider slider, ChangeListener delegate) {
    this.delegate = delegate;
    this.slider = slider;
    slider.addChangeListener(this::handleChangeEvent);
  }

  private void handleChangeEvent(ChangeEvent e) {
    if (!this.slider.getValueIsAdjusting()) {
      this.delegate.stateChanged(e);
    }
  }
}
