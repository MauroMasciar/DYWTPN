package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import database.ModelConfig;
import debug.Log;

public class MainWindow extends JFrame implements ActionListener, WindowListener, WindowStateListener, ComponentListener {
	private static final long serialVersionUID = -82854956961477559L;
	public static final JFrame j = new JFrame();
	private final JMenuBar menubar = new JMenuBar();
	private final JMenu mnuGames = new JMenu("Juegos");
	private final JMenuItem mnuiGamesAdd = new JMenuItem("Añadir nuevo juego", new ImageIcon("gfx/new_game.png"));
	private final JMenuItem mnuiGamesAddSession = new JMenuItem("Añadir sesion", new ImageIcon("gfx/new_session.png"));
	private final JMenuItem mnuiGamesEdit = new JMenuItem("Editar juego");
	private final JMenuItem mnuiGamesRefresh = new JMenuItem("Actualizar datos", new ImageIcon("gfx/refresh.png"));
	private final JMenuItem mnuiGamesList = new JMenuItem("Ver lista de juegos", new ImageIcon("gfx/games_list.png"));
	private final JCheckBoxMenuItem mnuiGamesHidden = new JCheckBoxMenuItem("Ver juegos ocultos");
	private final JMenuItem mnuiGamesCategory = new JMenuItem("Ver categorias", new ImageIcon("gfx/category.png"));
	private final JMenuItem mnuiGamesCollections = new JMenuItem("Ver colecciones", new ImageIcon("gfx/collections.png"));
	private final JMenuItem mnuiGamesLibrary = new JMenuItem("Ver bibliotecas", new ImageIcon("gfx/library.png"));
	private final JMenu mnuGamesStatistics = new JMenu("Estadisticas");
	private final JMenuItem mnuiGamesStatisticsPlayCount = new JMenuItem("Sesiones");
	private final JMenuItem mnuiGamesStatisticsTotalHours = new JMenuItem("Tiempo");
	private final JMenu mnuPlayer = new JMenu("Jugador");
	private final JMenuItem mnuiPlayerActivities = new JMenuItem("Actividad", new ImageIcon("gfx/history.png"));
	private final JMenuItem mnuiPlayerHistory = new JMenuItem("Historial", new ImageIcon("gfx/activity.png"));
	private final JMenu mnuHelp = new JMenu("Ayuda");
	private final JMenuItem mnuiHelpConfig = new JMenuItem("Configuración", new ImageIcon("gfx/config.png"));
	private final JMenuItem mnuiHelpUpdate = new JMenuItem("Actualizar", new ImageIcon("gfx/update.png"));
	private final JMenuItem mnuiHelpAbout = new JMenuItem("Acerca de", new ImageIcon("gfx/about.png"));
	private final JMenuItem mnuiHelpDebug = new JMenuItem("Debug", new ImageIcon("gfx/debug.png"));
	private final JMenuItem mnuiGamesExit = new JMenuItem("Salir");

	private final JPanel statusBar = new JPanel();
	private static JLabel lblStatusMessage = new JLabel("STATUS BAR", SwingConstants.LEFT);

	public MainWindow() {
		try {
			j.setIconImage(new ImageIcon(getClass().getResource("/gfx/icon.png")).getImage());
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
		}

		j.setLayout(null);
		String title = "DYWTPN v" + Main.VERSION_APP;
		j.setTitle(title);
		j.setBounds(30, 30, 1200, 800);
		j.setDefaultCloseOperation(EXIT_ON_CLOSE);
		j.setLocationRelativeTo(null);
		// j.setExtendedState(MAXIMIZED_BOTH);

		menubar.add(mnuGames);
		menubar.add(mnuPlayer);
		menubar.add(mnuHelp);
		mnuGames.add(mnuiGamesAdd);
		mnuGames.add(mnuiGamesAddSession);
		mnuGames.add(mnuiGamesEdit);
		mnuGames.add(mnuiGamesList);
		mnuGames.add(mnuiGamesHidden);
		mnuGames.add(mnuiGamesCategory);
		mnuGames.add(mnuiGamesCollections);
		mnuGames.add(mnuiGamesLibrary);
		mnuGames.addSeparator();
		mnuGames.add(mnuiGamesRefresh);
		mnuGames.add(mnuiGamesExit);
		mnuPlayer.add(mnuiPlayerHistory);
		mnuPlayer.add(mnuiPlayerActivities);
		mnuPlayer.add(mnuGamesStatistics);
		mnuGamesStatistics.add(mnuiGamesStatisticsPlayCount);
		mnuGamesStatistics.add(mnuiGamesStatisticsTotalHours);
		mnuHelp.add(mnuiHelpConfig);
		mnuHelp.add(mnuiHelpDebug);
		mnuHelp.add(mnuiHelpUpdate);
		mnuHelp.addSeparator();
		mnuHelp.add(mnuiHelpAbout);

		mnuiGamesExit.addActionListener(this);
		mnuiGamesRefresh.addActionListener(this);
		mnuiGamesAdd.addActionListener(this);
		mnuiGamesEdit.addActionListener(this);
		mnuiGamesAddSession.addActionListener(this);
		mnuiGamesCollections.addActionListener(this);
		mnuiGamesCategory.addActionListener(this);
		mnuiGamesLibrary.addActionListener(this);
		mnuiGamesList.addActionListener(this);
		mnuiGamesHidden.addActionListener(this);
		mnuiHelpConfig.addActionListener(this);
		mnuiPlayerActivities.addActionListener(this);
		mnuiGamesStatisticsPlayCount.addActionListener(this);
		mnuiGamesStatisticsTotalHours.addActionListener(this);
		mnuiPlayerHistory.addActionListener(this);
		mnuiHelpAbout.addActionListener(this);
		mnuiHelpUpdate.addActionListener(this);
		mnuiHelpDebug.addActionListener(this);

		statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		j.add(new MainUI());
		updateGuiStatusBar();
		j.setJMenuBar(menubar);
		j.addWindowListener(this);
		j.addWindowStateListener(this);
		j.addComponentListener(this);
	}

	public static void showWindow() {
		j.setVisible(true);
		j.setLayout(null);
	}

	public void updateGuiStatusBar() {
		j.setLayout(new BorderLayout());
		lblStatusMessage.setPreferredSize(new Dimension(j.getWidth() - 20, 15));
		statusBar.add(lblStatusMessage);
		statusBar.setAlignmentX(LEFT_ALIGNMENT);
		j.add(statusBar, BorderLayout.SOUTH);
	}

	public static void updateStatusBar(String s) {
		lblStatusMessage.setText(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		j.setLayout(null);
		if(e.getSource() == mnuiGamesAdd) {
			j.add(new AddGame());
		} else if(e.getSource() == mnuiGamesEdit) {
			j.add(new EditGame(0));
		} else if(e.getSource() == mnuiGamesAddSession) {
			j.add(new AddSessionGame());
		} else if (e.getSource() == mnuiGamesList) {
			j.add(new GameList());
		} else if(e.getSource() == mnuiGamesHidden) {
			ModelConfig mc = new ModelConfig();
			if(mnuiGamesHidden.isSelected()) {
				mc.setIsHidden(1);
			} else {
				mc.setIsHidden(0);
			}
			MainUI.loadData();
		} else if(e.getSource() == mnuiGamesExit) {
			if (!Main.test) {
				ExitApplication();
			}
		} else if(e.getSource() == mnuiGamesCategory) {
			j.add(new Category());
		} else if(e.getSource() == mnuiGamesCollections) {
			j.add(new Collections());
		} else if(e.getSource() == mnuiGamesLibrary) {
			j.add(new Library());
		} else if (e.getSource() == mnuiHelpConfig) {
			j.add(new Config());
		} else if (e.getSource() == mnuiPlayerActivities) {
			j.add(new PlayerActivities());
		} else if(e.getSource() == mnuiGamesStatisticsPlayCount) {
			j.add(new StatisticsPlayCount());
		} else if(e.getSource() == mnuiGamesStatisticsTotalHours) {
			j.add(new StatisticsTotalHours());
		} else if(e.getSource() == mnuiPlayerHistory) {
			j.add(new PlayerHistory());
		} else if(e.getSource() == mnuiGamesRefresh) {
			MainUI.loadData();
		} else if(e.getSource() == mnuiHelpUpdate) {
			j.add(new UpdateGUI());
		} else if(e.getSource() == mnuiHelpAbout) {
			j.add(new About());
		} else if(e.getSource() == mnuiHelpDebug) {
			j.add(new debug.LogGUI());
		}

		j.repaint();
	}

	private static void ExitApplication() {
		try {
			Main.p.destroy();
		} catch (NullPointerException ex) {
			Log.Loguear(ex.getMessage());
			ex.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		ModelConfig mc = new ModelConfig();
		mnuiGamesHidden.setSelected(mc.getIsHidden());
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(!Main.test) {
			ExitApplication();
		}
	}

	public void windowStateChanged(WindowEvent e) {
		updateGuiStatusBar();
	}

	public void componentResized(ComponentEvent e) {
		updateGuiStatusBar();
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
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentShown(ComponentEvent e) {

	}

	public void componentHidden(ComponentEvent e) {

	}
}
