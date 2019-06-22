package org.iMage.iCatcher;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.model.IModel;

/**
 * The main class of the project.
 * 
 * @author Dominik Fuchss
 *
 */
public final class Main {
  private Main() {
    throw new IllegalAccessError();
  }

  /**
   * The main method of iDeal.
   *
   * @param args
   *          the command line arguments
   */
  public static void main(String[] args) {
    Main.setSystemLookAndFeel();
    IModel model = IModel.create();
    SwingUtilities.invokeLater(IView.create(model)::start);
  }

  private static void setSystemLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      System.err.println("Cannot set system's look and feel ..");
    }
  }

}
