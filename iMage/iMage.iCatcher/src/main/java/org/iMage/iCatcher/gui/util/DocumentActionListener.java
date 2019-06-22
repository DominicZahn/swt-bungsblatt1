package org.iMage.iCatcher.gui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * A {@link DocumentListener} which delegates all events to an {@link ActionListener}.
 *
 * @author Dominik Fuchss
 *
 */
public final class DocumentActionListener implements DocumentListener {
  /**
   * Create the connection between {@link Document} and {@link ActionListener}.
   *
   * @param document
   *          the document
   * @param delegate
   *          the action listener
   * @return this {@link DocumentActionListener}
   */
  public static DocumentActionListener create(Document document, ActionListener delegate) {
    DocumentActionListener dl = new DocumentActionListener(delegate);
    document.addDocumentListener(dl);
    return dl;
  }

  private final ActionListener handler;

  private DocumentActionListener(ActionListener handler) {
    this.handler = handler;
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    this.handler.actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, "insertUpdate"));
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    this.handler.actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, "removeUpdate"));
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    this.handler.actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, "changedUpdate"));
  }

}
