package org.iMage.iCatcher.gui.controller.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#SAVE_CURVE}.
 *
 * @author Dominik Fuchss
 *
 */
public final class SaveCurveHandler extends Handler {
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
  public SaveCurveHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    ICameraCurve cc = this.model.getCurve();
    if (cc == null || !cc.isCalculated()) {
      this.view.showMessage("Curve not calculated yet.",
          "Curve not calculated yet. Please run HDR first", true);
      return;
    }
    File file = this.view.saveCurveFileDialog();
    if (file == null) {
      return;
    }
    try {
      FileOutputStream fos = new FileOutputStream(file);
      cc.save(fos);
      fos.close();
    } catch (IOException e) {
      this.view.showMessage("Saving not possible", "Error occured " + e.getMessage(), true);
      return;
    }
    this.view.showMessage("Saved Curve", "The curve has been saved", false);

  }
}
