package gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainWindow extends JFrame {
	private JFrame j = new JFrame();
	private JMenuBar menubar = new JMenuBar();
	private JMenu mnuGames = new JMenu("Juegos");
	private JMenuItem mnuiGamesRefresh = new JMenuItem("Actualizar");
	public MainWindow() {
		j.setTitle("DYWTPN");
		j.setSize(800, 600);
		j.setDefaultCloseOperation(EXIT_ON_CLOSE);

		mnuGames.add(mnuiGamesRefresh);
		menubar.add(mnuGames);
		j.setJMenuBar(menubar);

		j.setVisible(true);
	}

	public static void main(String[] args) {
		new MainWindow();
	}
}
