package org.iMage.iCatcher.gui.controller.handler;

import java.awt.EventQueue;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#RUN_HDR}.
 *
 * @author Dominik Fuchss
 *
 */
public final class RunHandler extends Handler {
  /**
   * Create handler by MVC.
   *
   * @param model
   *          the model
   * @param view
   *          the view
   * @param controller
   *          the controller
   */
  public RunHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    this.view.lock();
    this.controller.setLocked(true);
    EventQueue.invokeLater(this::runHDR);
  }

  private void runHDR() {
    System.out.println("Starting HDR processing ..");
    boolean result = this.model.runHdr();
    this.view.showMessage("Result",
        "Creation was" + (result ? " successful :)" : " not successful :("), !result);
    // Unlock buttons ..
    this.controller.setLocked(false);
    this.controller.handleEvent(Events.PARAM_UPDATE);
  }

}
