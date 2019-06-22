package org.iMage.iCatcher.gui.controller;

import java.util.HashMap;
import java.util.Map;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.handler.Handler;
import org.iMage.iCatcher.gui.controller.handler.InternalErrorHandler;
import org.iMage.iCatcher.gui.controller.handler.LoadCurveHandler;
import org.iMage.iCatcher.gui.controller.handler.LoadFolderHandler;
import org.iMage.iCatcher.gui.controller.handler.ParameterUpdateHandler;
import org.iMage.iCatcher.gui.controller.handler.RunHandler;
import org.iMage.iCatcher.gui.controller.handler.SaveCurveHandler;
import org.iMage.iCatcher.gui.controller.handler.SaveHandler;
import org.iMage.iCatcher.gui.controller.handler.ShowCurveHandler;
import org.iMage.iCatcher.gui.controller.handler.ShowInputHandler;
import org.iMage.iCatcher.gui.controller.handler.ShowResultHandler;
import org.iMage.iCatcher.model.IModel;

/**
 * Defines the {@link IController} for the MVC.
 *
 * @author Dominik Fuchss
 *
 */
final class Controller implements IController {
  private final IModel model;
  private final IView view;

  private final Map<Events, Handler> handlers;
  private final Handler internalError;

  /**
   * Create a controller by model and view.
   *
   * @param model
   *          the model
   * @param view
   *          the view
   */
  Controller(IModel model, IView view) {
    this.model = model;
    this.view = view;
    this.handlers = new HashMap<>();
    this.internalError = new InternalErrorHandler(model, view, this);
    this.initHandlers();
  }

  private void initHandlers() {
    this.handlers.put(Events.PARAM_UPDATE, new ParameterUpdateHandler(this.model, this.view, this));
    this.handlers.put(Events.LOAD_FOLDER, new LoadFolderHandler(this.model, this.view, this));
    this.handlers.put(Events.SAVE_HDR, new SaveHandler(this.model, this.view, this));

    this.handlers.put(Events.SHOW_INPUT, new ShowInputHandler(this.model, this.view, this));
    this.handlers.put(Events.SHOW_RESULT, new ShowResultHandler(this.model, this.view, this));

    this.handlers.put(Events.RUN_HDR, new RunHandler(this.model, this.view, this));

    this.handlers.put(Events.SHOW_CURVE, new ShowCurveHandler(this.model, this.view, this));
    this.handlers.put(Events.SAVE_CURVE, new SaveCurveHandler(this.model, this.view, this));
    this.handlers.put(Events.LOAD_CURVE, new LoadCurveHandler(this.model, this.view, this));
  }

  private boolean locked = false;

  @Override
  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  @Override
  public boolean isLocked() {
    return this.locked;
  }

  @Override
  public void handleEvent(Events event) {
    if (event == null || this.locked) {
      return;
    }
    Handler handler = this.handlers.getOrDefault(event, this.internalError);
    //    System.err.println("Execute: " + handler.getClass().getSimpleName() + "@" + event);
    handler.run();
  }

}