package org.iMage.iCatcher.gui.controller.handler;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.model.CurveMode;
import org.iMage.iCatcher.model.IModel;
import org.iMage.iCatcher.util.IObserver;

/**
 * This handler belongs to {@link Events#PARAM_UPDATE}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ParameterUpdateHandler extends Handler implements IObserver<IModel> {

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
  public ParameterUpdateHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
    model.registerObserver(this);
  }

  @Override
  public void invokeUpdate(IModel model) {
    // ReCheck states in view .. (enabled/disabled etc.)
    if (!this.controller.isLocked()) {
      this.run();
    }
  }

  @Override
  public void run() {
    this.controller.setLocked(true);

    if (this.loadingOfCurveNeeded()) {
      this.model.informObservers();
      this.controller.setLocked(false);
      this.controller.handleEvent(Events.LOAD_CURVE);
      return;
    }

    this.setCameraCurve();
    boolean validLambda = this.setLambda();
    this.setSamples();
    this.setToneMapping();

    boolean canRun = this.model.getInput() != null && validLambda;
    boolean canShowOrSaveCurve = this.model.getCurve() != null
        && this.model.getCurve().isCalculated();
    boolean canSaveHDR = this.model.getResult() != null;
    this.view.enOrDisableButtons(canRun, canShowOrSaveCurve, canSaveHDR);

    this.controller.setLocked(false);
  }

  private boolean loadingOfCurveNeeded() {
    return this.view.getCameraCurveMode() == CurveMode.LoadedCurve && !this.model.isCurveLoaded();
  }

  private void setToneMapping() {
    this.model.setMapping(this.view.getToneMapping());
  }

  private boolean setLambda() {
    String lambda = this.view.getLambda();
    Float l;
    try {
      l = Float.parseFloat(lambda);
    } catch (NumberFormatException | NullPointerException e) {
      l = null;
    }

    boolean invalid = l == null || !this.model.setLambda(l);
    this.view.setLambdaToInvalid(invalid);
    return !invalid;
  }

  private void setSamples() {
    this.model.setSamples(this.view.getSamples());
  }

  private void setCameraCurve() {
    this.model.setCameraCurveMode(this.view.getCameraCurveMode());

  }
}
