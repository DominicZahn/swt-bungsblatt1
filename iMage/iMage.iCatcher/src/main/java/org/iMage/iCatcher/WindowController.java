package org.iMage.iCatcher;

import javax.swing.JFrame;

public class WindowController {
	public static void main(String[] args) {
		JFrame icw = new ICatcherWindow();
		icw.setVisible(true);
		icw.setSize(800, 700);
		icw.setLocationRelativeTo(null);
		icw.setResizable(false);
		icw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
