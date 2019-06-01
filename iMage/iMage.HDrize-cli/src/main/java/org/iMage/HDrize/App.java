package org.iMage.HDrize;

import org.apache.commons.cli.*;
import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.matrix.MatrixCalculator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses all command line parameters and creates an HDRImage.
 */
public final class App {
  private App() {
    throw new IllegalAccessError();
  }

  private static final String CMD_OPTION_INPUT_IMAGES = "i";
  private static final String CMD_OPTION_OUTPUT_IMAGE = "o";
  private static final String CMD_OPTION_LAMBDA = "l";
  private static final String CMD_OPTION_SAMPLES = "s";

  private static final double DEFAULT_LAMBDA = 30;
  private static final int DEFAULT_SAMPLES = 142;

  /**
   * The main method.<br> Possible arguments:<br>
   * <b>i</b> - the path to the original images (folder)<br>
   * <b>o</b> - the path to the returned image<br>
   * <b>l</b> - the lambda value (default: 30)<br>
   * <b>s</b> - the number of samples (default: 142)
   *
   * @param args
   *     the command line arguments
   */
  public static void main(String[] args) {
    CommandLine cmd = null;
    try {
      cmd = App.doCommandLineParsing(args);
    } catch (ParseException e) {
      System.err.println("Wrong command line arguments given: " + e.getMessage());
      System.exit(1);
    }

    double lambda = cmd.hasOption(App.CMD_OPTION_LAMBDA) ?
        Double.parseDouble(cmd.getOptionValue(App.CMD_OPTION_LAMBDA)) :
        App.DEFAULT_LAMBDA;

    if (lambda <= 0 || lambda > 100) {
      System.err.println("Lambda is invalid: " + lambda);
      System.exit(1);
    }

    int samples = cmd.hasOption(App.CMD_OPTION_SAMPLES) ?
        Integer.parseInt(cmd.getOptionValue(App.CMD_OPTION_SAMPLES)) :
        App.DEFAULT_SAMPLES;

    if (samples < 1 || samples > 1000) {
      System.err.println("Invalid number of samples: " + samples);
      System.exit(1);
    }

    File[] input = null;

    try {
      input = App.processInputFiles(cmd.getOptionValue(App.CMD_OPTION_INPUT_IMAGES));

    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    EnhancedImage[] images = App.toEnhancedImages(input);

    HDrize hdrize = new HDrize();
    BufferedImage hdr = hdrize.createRGB(images, samples, lambda, new MatrixCalculator());
    if (hdr == null) {
      System.err.println("Some error occurred while creating hdr image");
      System.exit(1);
    }

    File output = null;
    try {
      output = App.ensureFile(cmd.getOptionValue(App.CMD_OPTION_OUTPUT_IMAGE), true);
      ImageIO.write(hdr, "png", output);
    } catch (IOException e) {
      System.err.println("Could not save image: " + e.getMessage());
      System.exit(1);
    }

  }

  private static EnhancedImage[] toEnhancedImages(File[] input) {
    EnhancedImage[] result = new EnhancedImage[input.length];
    for (int i = 0; i < result.length; i++) {
      try {
        result[i] = new EnhancedImage(new FileInputStream(input[i]));
      } catch (ImageReadException | IOException e) {
        System.err.println(e.getMessage());
        System.exit(1);
      }
    }

    return result;
  }

  private static File[] processInputFiles(String dir) throws IOException {
    File directory = App.ensureFile(dir, false);
    List<File> jpgs = new ArrayList<>();
    for (File file : directory.listFiles(f -> f.getName().endsWith(".jpg"))) {
      jpgs.add(file);
    }

    if (jpgs.size() % 2 == 0 || jpgs.size() <= 1) {
      System.err.println("Found " + jpgs.size() + " files. This isn't an odd value.");
      System.exit(1);
    }
    File[] result = jpgs.toArray(File[]::new);
    for (File image : result) {
      String name = image.getName();
      name = name.substring(0, name.length() - ".jpg".length());
      if (name.length() < 3 || !result[0].getName().startsWith(name.substring(0, 3))) {
        System.err.println("Naming violation: " + image.getName() + " & " + result[0].getName());
        System.exit(1);
      }
    }

    return result;
  }

  /**
   * Ensure that a file exists (or create if allowed by parameter).
   *
   * @param path
   *     the path to the file
   * @param create
   *     indicates whether creation is allowed
   * @return the file
   * @throws IOException
   *     if something went wrong
   */
  private static File ensureFile(String path, boolean create) throws IOException {
    File file = new File(path);
    if (file.exists()) {
      return file;
    }
    if (create) {
      file.createNewFile();
      return file;
    }

    // File not available
    throw new IOException("The specified file does not exist: " + path);
  }

  /**
   * Parse and check command line arguments
   *
   * @param args
   *     command line arguments given by the user
   * @return CommandLine object encapsulating all options
   * @throws ParseException
   *     if wrong command line parameters or arguments are given
   */
  private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
    Options options = new Options();
    Option opt;

    /*
     * Define command line options and arguments
     */
    opt = new Option(//
        App.CMD_OPTION_INPUT_IMAGES, "input-images", true, "path to folder with input images");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_OUTPUT_IMAGE, "image-output", true, "path to output image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_LAMBDA, "lambda", true, "the lambda value of algorithm");
    opt.setRequired(false);
    opt.setType(Double.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_SAMPLES, "samples", true, "the number of samples");
    opt.setRequired(false);
    opt.setType(Integer.class);
    options.addOption(opt);

    CommandLineParser parser = new DefaultParser();
    return parser.parse(options, args);
  }
}