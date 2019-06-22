package org.iMage.iCatcher.gui.controller;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.model.IModel;

/**
 * Defines the interface for the controller of MVC.
 *
 * @author Dominik Fuchss
 *
 */
public interface IController {
  /**
   * Create a new {@link IController} by {@link IModel} and {@link IView}.
   *
   * @param model
   *          the model
   * @param view
   *          the view
   * @return the new {@link IController}
   */
  static IController create(IModel model, IView view) {
    return new Controller(model, view);
  }

  /**
   * Prevent any raise of an event.
   *
   * @param locked
   *          indicator
   */
  void setLocked(boolean locked);

  /**
   * Indicates whether the controller is locked.
   *
   * @return indicator
   */
  boolean isLocked();

  /**
   * Handle an event.
   *
   * @param event
   *          the event
   */
  void handleEvent(Events event);

}
