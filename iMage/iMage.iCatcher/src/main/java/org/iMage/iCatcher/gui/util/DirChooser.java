package org.iMage.iCatcher.gui.util;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * A simple Directory Chooser.
 *
 * @author Dominik Fuchss
 *
 */
public final class DirChooser {

  private final JFileChooser jfc;

  private final Component parent;

  /**
   * Create chooser by parent.
   * 
   * @param parent
   *          the parent component
   */
  public DirChooser(Component parent) {
    this.parent = parent;
    this.jfc = new JFileChooser();
    this.jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    this.jfc.setAcceptAllFileFilterUsed(true);
  }

  /**
   * Open file dialog.
   *
   * @return the file or {@code null}
   */
  public File selectDirectory() {
    int result = this.jfc.showOpenDialog(this.parent);
    File selected = this.jfc.getSelectedFile();
    if (result == JFileChooser.APPROVE_OPTION && selected != null && selected.exists()) {
      return selected;
    }
    return null;
  }

}
