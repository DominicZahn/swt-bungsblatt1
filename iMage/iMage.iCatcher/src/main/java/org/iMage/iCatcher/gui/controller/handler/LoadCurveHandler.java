package org.iMage.iCatcher.gui.controller.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.iMage.HDrize.CameraCurve;
import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#LOAD_CURVE}.
 *
 * @author Dominik Fuchss
 *
 */
public final class LoadCurveHandler extends Handler {
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
  public LoadCurveHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {

    File file = this.view.openCurveFileDialog();
    if (file == null) {
      return;
    }

    try {
      FileInputStream fis = new FileInputStream(file);
      CameraCurve cc = new CameraCurve(fis);
      fis.close();
      this.controller.setLocked(true);
      this.model.loadCurve(file.getAbsolutePath(), cc);
      this.controller.setLocked(false);
      this.controller.handleEvent(Events.PARAM_UPDATE);
    } catch (IOException | ClassNotFoundException e) {
      this.view.showMessage("Loading not possible", "Error occured " + e.getMessage(), true);
      return;
    }

    this.view.showMessage("Loaded Curve", "The curve has been loaded", false);

  }
}
