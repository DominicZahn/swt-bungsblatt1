package org.iMage.iCatcher.gui.controller.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#SAVE_HDR}.
 *
 * @author Dominik Fuchss
 *
 */
public final class SaveHandler extends Handler {
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
  public SaveHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    BufferedImage result = this.model.getResult();
    if (result == null) {
      this.view.showMessage("Result not calculated yet.",
          "Result not calculated yet. Please run HDR first", true);
      return;
    }
    File file = this.view.saveImageFileDialog();
    if (file == null) {
      return;
    }
    try {
      ImageIO.write(result, "png", file);
    } catch (IOException e) {
      this.view.showMessage("Saving not possible", "Error occured " + e.getMessage(), true);
      return;
    }
    this.view.showMessage("Saved HDR Image", "The HDR Image has been saved", false);

  }
}
