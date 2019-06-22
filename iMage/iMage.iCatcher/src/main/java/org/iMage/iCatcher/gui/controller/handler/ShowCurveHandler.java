package org.iMage.iCatcher.gui.controller.handler;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.gui.util.Graph;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#SHOW_CURVE}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ShowCurveHandler extends Handler {
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
  public ShowCurveHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    ICameraCurve curve = this.model.getCurve();
    if (curve == null) {
      this.view.showMessage("Error", "Curve not available.", true);
      return;
    }
    String title = this.model.getCurveName();
    new Graph(this.view.asFrame(), curve, title).setVisible(true);
  }

}
