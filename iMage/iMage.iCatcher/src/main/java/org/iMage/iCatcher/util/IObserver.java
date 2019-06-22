package org.iMage.iCatcher.util;

/**
 * A simple Observer of a Subject.
 *
 * @author Dominik Fuchss
 *
 * @param <Subject>
 *          the subject type
 */
public interface IObserver<Subject> {
  /**
   * Notify about update of subject <em>s</em>.
   *
   * @param s
   *          the updated subject
   */
  void invokeUpdate(Subject s);
}
