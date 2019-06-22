package org.iMage.iCatcher.gui.controller.handler;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.model.IModel;

/**
 * The base class of all handlers of {@link Events}
 *
 * @author Dominik Fuchss
 *
 */
public abstract class Handler {

  protected final IModel model;
  protected final IView view;
  protected final IController controller;

  /**
   * Set MVC parts in Handler.
   *
   * @param model
   *          the model
   * @param view
   *          the view
   * @param controller
   *          the controller
   */
  protected Handler(IModel model, IView view, IController controller) {
    this.model = model;
    this.view = view;
    this.controller = controller;
  }

  /**
   * Run/Execute the handler.
   */
  public abstract void run();

}
