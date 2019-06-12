package org.iMage.plugins.instagrim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

/**
 * The Instagrim Plugin.
 *
 * @author Dominik Fuchss
 *
 */
@MetaInfServices(PluginForJmjrst.class)
public class InstagrimPlugin extends PluginForJmjrst {

  private Main main;
  private List<String> comments;
  private Random rand = new Random();

  /**
   * Create the Instagrim Plugin.
   */
  public InstagrimPlugin() {
    this.comments = new ArrayList<>();
    Scanner rscan = new Scanner(this.getClass().getResourceAsStream("/comments.txt"));
    while (rscan.hasNextLine()) {
      String line = rscan.nextLine();
      if (line == null || line.trim().isEmpty()) {
        continue;
      }
      this.comments.add(line);
    }
    rscan.close();
  }

  @Override
  public String getName() {
    return "Instagrim";
  }

  @Override
  public void init(Main main) {
    this.main = main;
    System.err.println(""//
        + "iMage: Der Bildverschönerer, dem Influencer vertrauen! " //
        + "Jetzt bist auch Du Teil unseres Teams, " + System.getProperty("user.name") //
    );
  }

  @Override
  public void run() {
    System.out.println(this.comments.get(this.rand.nextInt(this.comments.size())));
  }

  @Override
  public boolean isConfigurable() {
    return true;
  }

  @Override
  public void configure() {

    JOptionPane.showMessageDialog(this.main, //
        "All Comments:\n\n" + String.join("\n", this.comments), //
        "iMage: Der Bildverschönerer, dem Influencer vertrauen!", JOptionPane.INFORMATION_MESSAGE);

  }

}
