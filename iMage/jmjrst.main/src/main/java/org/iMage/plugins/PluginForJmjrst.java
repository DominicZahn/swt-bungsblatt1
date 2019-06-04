package org.iMage.plugins;

/**
 * Abstract parent class for plug-ins for JMJRST
 *
 * @author Dominik Fuchss
 */
public abstract class PluginForJmjrst implements Comparable<PluginForJmjrst> {

  /**
   * Returns the name of this plug-in
   *
   * @return the name of the plug-in
   */
  public abstract String getName();

  /**
   * JMJRST pushes the main application to every subclass - so plug-ins are allowed to look at Main
   * as well.
   *
   * @param main
   *     JMJRST main application
   */
  public abstract void init(org.jis.Main main);

  /**
   * Runs plug-in
   */
  public abstract void run();

  /**
   * Returns whether the plug-in can be configured or not
   *
   * @return true if the plug-in can be configured.
   */
  public abstract boolean isConfigurable();

  /**
   * Open a configuration dialogue.
   */
  public abstract void configure();
  
  /**
   * The function returns a negative number if "this" should be behind "otherPlugin"
   */
  @Override 
  public int compareTo(PluginForJmjrst otherPlugin) {
	  String name1 = this.getName();
	  String name2 = otherPlugin.getName();
	  int diffrence = name1.length() - name2.length();
	  if (diffrence == 0) {
		  diffrence = name1.compareTo(name2);
	  }
	  return diffrence;
  }
}
