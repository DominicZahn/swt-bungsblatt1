package org.iMage.iCatcher.gui.controller.handler;

import java.awt.image.BufferedImage;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.gui.util.ImageDialog;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#SHOW_RESULT}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ShowResultHandler extends Handler {
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
  public ShowResultHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    BufferedImage result = this.model.getResult();
    if (result == null) {
      return;
    }
    new ImageDialog(this.view.asFrame(), this.model.getCommonPrefix() + "_HDR", result)
        .setVisible(true);
  }

}
