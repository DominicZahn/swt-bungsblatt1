package org.iMage.iCatcher.gui.controller.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.iCatcher.gui.IView;
import org.iMage.iCatcher.gui.controller.Events;
import org.iMage.iCatcher.gui.controller.IController;
import org.iMage.iCatcher.model.IModel;

/**
 * This handler belongs to {@link Events#LOAD_FOLDER}.
 *
 * @author Dominik Fuchss
 *
 */
public final class LoadFolderHandler extends Handler {
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
  public LoadFolderHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    File folder = this.view.openDirectoryDialog();
    if (folder == null) {
      return;
    }
    File[] imageFiles = null;
    try {
      imageFiles = LoadFolderHandler.processInputFiles(folder);
    } catch (IOException e) {
      this.view.showMessage("Error", "Error while loading directory: " + e.getMessage(), true);
      return;
    }

    EnhancedImage[] images = this.toEnhancedImages(imageFiles);
    if (images == null) {
      return;
    }

    BufferedImage[] originals = this.toOriginals(imageFiles);
    if (originals == null) {
      return;
    }

    String longestCommenPrefix = this.findPrefix(imageFiles);
    this.model.setInput(longestCommenPrefix, originals, images);

  }

  private String findPrefix(File[] imageFiles) {

    String[] names = new String[imageFiles.length];
    for (int i = 0; i < imageFiles.length; i++) {
      String name = imageFiles[i].getName();
      name = name.substring(0, name.length() - ".jpg".length());
      names[i] = name;
    }

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < names[0].length(); i++) {
      char current = names[0].charAt(i);
      for (String n : names) {
        if (n.charAt(i) != current) {
          return builder.toString();
        }
      }
      builder.append(current);
    }

    return builder.toString();
  }

  private BufferedImage[] toOriginals(File[] input) {
    BufferedImage[] result = new BufferedImage[input.length];
    for (int i = 0; i < result.length; i++) {
      try {
        result[i] = ImageIO.read(new FileInputStream(input[i]));
      } catch (IOException e) {
        this.view.showMessage("Error", "Error while loading directory: " + e.getMessage(), true);
        return null;
      }
    }
    return result;
  }

  private EnhancedImage[] toEnhancedImages(File[] input) {
    EnhancedImage[] result = new EnhancedImage[input.length];
    for (int i = 0; i < result.length; i++) {
      try {
        result[i] = new EnhancedImage(new FileInputStream(input[i]));
      } catch (ImageReadException | IOException e) {
        this.view.showMessage("Error", "Error while loading directory: " + e.getMessage(), true);
        return null;
      }
    }

    return result;
  }

  private static File[] processInputFiles(File directory) throws IOException {
    List<File> jpgs = new ArrayList<>();
    for (File file : directory.listFiles(f -> f.getName().endsWith(".jpg"))) {
      jpgs.add(file);
    }

    if (jpgs.size() % 2 == 0 || jpgs.size() <= 1) {
      throw new IOException("Found " + jpgs.size() + " files. This isn't an odd value.");
    }
    File[] result = jpgs.toArray(File[]::new);
    for (File image : result) {
      String name = image.getName();
      name = name.substring(0, name.length() - ".jpg".length());
      if (name.length() < 3 || !result[0].getName().startsWith(name.substring(0, 3))) {
        throw new IOException("Naming violation: " + image.getName() + " & " + result[0].getName());
      }
    }

    return result;
  }
}
