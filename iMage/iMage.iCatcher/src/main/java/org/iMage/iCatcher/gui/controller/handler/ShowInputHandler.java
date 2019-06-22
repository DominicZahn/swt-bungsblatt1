package org.iMage.iCatcher.gui.controller.handler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.gui.util.ImageDialog;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#SHOW_INPUT}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ShowInputHandler extends Handler {
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
  public ShowInputHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    BufferedImage[] inputs = this.model.getInput();
    if (inputs == null) {
      return;
    }

    BufferedImage input = new BufferedImage(inputs[0].getWidth() * inputs.length,
        inputs[0].getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D) input.getGraphics();
    for (int i = 0; i < inputs.length; i++) {
      g2d.drawImage(inputs[i], i * inputs[0].getWidth(), 0, null);
    }

    input.flush();
    new ImageDialog(this.view.asFrame(), this.model.getCommonPrefix() + "*-inputs", input)
        .setVisible(true);
  }

}
