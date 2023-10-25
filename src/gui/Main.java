package gui;

import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("No se ha podido configurar el look and feel: " + e.getMessage());
			e.printStackTrace();
		}
		new MainWindow();
	}
}
