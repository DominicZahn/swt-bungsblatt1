package org.iMage.iCatcher.gui.util;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A simple FileChooser for files.
 *
 * @author Dominik Fuchss
 *
 */
public final class FileChooser {

  private final JFileChooser jfc;
  private final String validExtension;

  private final Component parent;

  /**
   * Create chooser by parent.
   *
   * @param parent
   *          the parent component
   * @param type
   *          the name of type to load
   * @param validExtension
   *          the extension of the type
   */
  public FileChooser(Component parent, String type, String validExtension) {
    this.parent = parent;
    this.validExtension = validExtension;
    FileNameExtensionFilter filter = new FileNameExtensionFilter(type, validExtension);
    this.jfc = new JFileChooser();
    this.jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    this.jfc.setAcceptAllFileFilterUsed(false);
    this.jfc.setFileFilter(filter);

  }

  /**
   * Open file dialog.
   *
   * @return the file or {@code null}
   */
  public File openFile() {
    int result = this.jfc.showOpenDialog(this.parent);
    File selected = this.jfc.getSelectedFile();
    if (result == JFileChooser.APPROVE_OPTION && selected != null && selected.exists()) {
      return selected;
    }
    return null;
  }

  /**
   * Save file dialog.
   *
   * @return the file or {@code null}
   */
  public File saveFile() {
    int result = this.jfc.showSaveDialog(this.parent);
    File selected = this.jfc.getSelectedFile();
    if (result == JFileChooser.APPROVE_OPTION && selected != null) {
      if (selected.getName().endsWith(this.validExtension)) {
        return selected;
      } else {
        return new File(selected.getAbsolutePath() + "." + this.validExtension);
      }
    }
    return null;

  }

}
