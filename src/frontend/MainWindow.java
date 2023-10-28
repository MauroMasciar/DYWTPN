package frontend;

import backend.InGame;
import database.ModelGames;
import debug.Log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener, ListSelectionListener, WindowListener {
	private static final long serialVersionUID = -82854956961477559L;
	public static JFrame j = new JFrame();
	private JMenuBar menubar = new JMenuBar();
	private JMenu mnuConfig = new JMenu("Configuración");
	private JMenuItem mnuiConfigAdd = new JMenuItem("Añadir nuevo juego");
	private JMenuItem mnuiConfigRefresh = new JMenuItem("Actualizar lista de juegos");
	private JMenuItem mnuiConfigConfig = new JMenuItem("Configuración");
	private JMenu mnuHelp = new JMenu("Ayuda");
	private JMenuItem mnuHelpAbout = new JMenuItem("Acerca de");
	private JList<String> jlistGames = new JList<String>();
	private JScrollPane scrListGame = new JScrollPane(jlistGames);
	private DefaultListModel<String> modelList = new DefaultListModel<String>();
	private JTextField txtGameName = new JTextField(20);
	private JTextField txtMinsPlayed = new JTextField(20);
	private DecimalFormat txtMinsPlayedDecimal = new DecimalFormat("#.#");
	private JButton btnLaunchGame = new JButton("Lanzar");
	private JButton btnEditGame = new JButton("Editar");

	public int gameIdSelected = 0;
	public int gameIdLaunched = 0;

	public MainWindow() {
		j.setTitle("DYWTPN");
		j.setSize(630, 200);
		j.setDefaultCloseOperation(EXIT_ON_CLOSE);
		j.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		g.ipadx = 0;

		menubar.add(mnuConfig);
		menubar.add(mnuHelp);
		mnuConfig.add(mnuiConfigAdd);
		mnuConfig.add(mnuiConfigRefresh);
		mnuConfig.add(mnuiConfigConfig);
		mnuHelp.add(mnuHelpAbout);

		j.setJMenuBar(menubar);

		mnuiConfigRefresh.addActionListener(this);
		mnuiConfigAdd.addActionListener(this);
		mnuiConfigConfig.addActionListener(this);

		j.add(scrListGame);
		jlistGames.setModel(modelList);

		jlistGames.addListSelectionListener(this);

		btnLaunchGame.addActionListener(this);
		btnEditGame.addActionListener(this);

		txtGameName.setEditable(false);
		txtMinsPlayed.setEditable(false);

		j.add(txtGameName);
		j.add(txtMinsPlayed);
		j.add(btnLaunchGame);
		j.add(btnEditGame);

		btnLaunchGame.setEnabled(false);
		btnEditGame.setEnabled(false);

		j.addWindowListener(this);

		j.setVisible(true);
	}

	public void LoadData() {
		UpdateGameList();
	}

	public void UpdateGameList() {
		btnLaunchGame.setEnabled(false);
		jlistGames.removeAll();
		modelList.clear();
		txtGameName.setText("");
		txtMinsPlayed.setText("");

		ModelGames g = new ModelGames();
		ArrayList<String> listGames = new ArrayList<String>();
		listGames = g.getGamesNameList();
		for (int i = 1; i < listGames.size(); i++) {
			modelList.addElement(listGames.get(i));
		}
		String s = "Juegos cargados: " + listGames.size();
		Log.Loguear(s);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mnuiConfigRefresh) {
			UpdateGameList();
			Log.Loguear("Lista de juegos actualizada");
		} else if(e.getSource() == mnuiConfigAdd) {
			j.setVisible(false);
			new AddGame();
		} else if(e.getSource() == mnuiConfigConfig) {
			j.setVisible(false);
			new Config();
		} else if(e.getSource() == btnEditGame) {
			j.setVisible(false);
			new EditGame(gameIdSelected);
		} else if(e.getSource() == btnLaunchGame) {
			ModelGames g = new ModelGames();
			String path = g.getPathFromGame(gameIdSelected);
			if(path == "null") {
				btnLaunchGame.setEnabled(false);
			} else {
				ProcessBuilder pb;
				if(g.isGhost(gameIdSelected)) {
					pb = new ProcessBuilder("C:\\MiGestorDeJuegos\\GhostGame.exe");
					Log.Loguear("Lanzando juego fantasma");
				} else {
					pb = new ProcessBuilder(path);
					Log.Loguear("Lanzando juego");
				}
				Process p;
				try {
					p = pb.start();
					if (p.isAlive()) {
						InGame ig = new InGame(gameIdSelected);
						gameIdLaunched = gameIdSelected;

						new Thread(new Runnable() {
							public void run() {
								int statusProcess;
								try {
									statusProcess = p.waitFor();
									Log.Loguear("Estado del proceso al cerrar: " + statusProcess);
									Log.Loguear("Tiempo en la ultima sesion: " + ig.getGameTimePlayed());
									ig.closeGame();
									UpdateGameList();
								} catch (InterruptedException e) {
									JOptionPane.showMessageDialog(null, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n(1)", "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
								}
							}
						}).start();
					}
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n(1)", "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource() == jlistGames) {
			btnLaunchGame.setEnabled(true);
			btnEditGame.setEnabled(true);
			String s = jlistGames.getSelectedValue();
			txtGameName.setText(s);
			ModelGames g = new ModelGames();

			gameIdSelected = g.getIdFromGameName(txtGameName.getText());
			if(gameIdSelected != 0) {
				double hours_played = g.getMinsPlayed(gameIdSelected);
				txtMinsPlayed.setText(txtMinsPlayedDecimal.format(hours_played/60));
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		LoadData();
	}

	@Override
	public void windowOpened(WindowEvent e) {	
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
