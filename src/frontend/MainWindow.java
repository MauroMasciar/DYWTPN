package frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;

import database.ModelConfig;

public class MainWindow extends JFrame implements ActionListener, WindowListener {
    private static final long serialVersionUID = -82854956961477559L;
    public static JFrame j = new JFrame();
    private JMenuBar menubar = new JMenuBar();
    private JMenu mnuGames = new JMenu("Juegos");
    private JMenuItem mnuiGamesAdd = new JMenuItem("Añadir nuevo juego");
    private JMenuItem mnuiGamesAddSession = new JMenuItem("Añadir sesion");
    private JMenuItem mnuiGamesRefresh = new JMenuItem("Actualizar datos");
    private JMenuItem mnuiGamesList = new JMenuItem("Ver lista de juegos");
    private JCheckBoxMenuItem mnuiGamesHidden = new JCheckBoxMenuItem("Ver juegos ocultos");
    private JMenuItem mnuiGamesCategory = new JMenuItem("Ver categorias");
    private JMenuItem mnuiGamesCollections = new JMenuItem("Ver colecciones");
    private JMenu mnuPlayer = new JMenu("Jugador");
    private JMenuItem mnuiPlayerActivities = new JMenuItem("Actividad");
    private JMenuItem mnuiPlayerHistory = new JMenuItem("Historial");
    private JMenu mnuHelp = new JMenu("Ayuda");
    private JMenuItem mnuiHelpConfig = new JMenuItem("Configuración");
    private JMenuItem mnuiHelpAbout = new JMenuItem("Acerca de");

    public MainWindow() {
	try {
	    j.setIconImage(new ImageIcon(getClass().getResource(
		    "/gfx/icon.png")).getImage());
	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(this,
		    "No se ha podido cargar algunos recursos.",
		    "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
	}



	new Thread(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(1000);
		    ModelConfig mc = new ModelConfig();
		    mnuiGamesHidden.setSelected(mc.getIsHidden());
		} catch (InterruptedException ex) {
		    ex.printStackTrace();
		}
	    }
	}).start();

	j.setLayout(null);
	j.setTitle("DYWTPN v1.1.20");
	j.setBounds(30, 30, 1200, 800);
	j.setDefaultCloseOperation(EXIT_ON_CLOSE);
	//j.setExtendedState(MAXIMIZED_BOTH);

	menubar.add(mnuGames);
	menubar.add(mnuPlayer);
	menubar.add(mnuHelp);
	mnuGames.add(mnuiGamesAdd);
	mnuGames.add(mnuiGamesAddSession);
	mnuGames.add(mnuiGamesList);
	mnuGames.add(mnuiGamesHidden);
	mnuGames.add(mnuiGamesCategory);
	mnuGames.add(mnuiGamesCollections);
	mnuGames.addSeparator();
	mnuGames.add(mnuiGamesRefresh);
	mnuPlayer.add(mnuiPlayerHistory);
	mnuPlayer.add(mnuiPlayerActivities);
	mnuHelp.add(mnuiHelpConfig);
	mnuHelp.add(mnuiHelpAbout);

	mnuiGamesRefresh.addActionListener(this);
	mnuiGamesAdd.addActionListener(this);
	mnuiGamesAddSession.addActionListener(this);
	mnuiGamesCollections.addActionListener(this);
	mnuiGamesCategory.addActionListener(this);
	mnuiGamesList.addActionListener(this);
	mnuiGamesHidden.addActionListener(this);
	mnuiHelpConfig.addActionListener(this);
	mnuiPlayerActivities.addActionListener(this);
	mnuiPlayerHistory.addActionListener(this);
	mnuiHelpAbout.addActionListener(this);

	j.add(new MainUI());

	j.setJMenuBar(menubar);
	j.addWindowListener(this);

	j.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == mnuiGamesAdd) {
	    j.add(new AddGame());
	    j.repaint();
	} else if(e.getSource() == mnuiGamesAddSession) {
	    j.add(new AddSessionGame());
	    j.repaint();
	} else if(e.getSource() == mnuiGamesList) {
	    j.add(new GameList());
	    j.repaint();
	} else if(e.getSource() == mnuiGamesHidden) {
	    ModelConfig mc = new ModelConfig();
	    if(mnuiGamesHidden.isSelected()) {
		mc.setIsHidden(1);
	    } else {
		mc.setIsHidden(0);
	    }
	    MainUI.LoadData();
	    MainUI.UpdateGameList();
	} else if(e.getSource() == mnuiGamesCategory) {
	    j.add(new Category());
	    j.repaint();
	} else if(e.getSource() == mnuiGamesCollections) {
	    j.add(new Collections());
	    j.repaint();
	} else if(e.getSource() == mnuiHelpConfig) {
	    j.add(new Config());
	    j.repaint();
	} else if(e.getSource() == mnuiPlayerActivities) {
	    j.add(new PlayerActivities());
	    j.repaint();
	} else if(e.getSource() == mnuiPlayerHistory) {
	    j.add(new PlayerHistory());
	    j.repaint();
	} else if(e.getSource() == mnuiGamesRefresh) {
	    MainUI.LoadData();
	    MainUI.UpdateGameList();
	    MainUI.LoadLastSession();
	} else if(e.getSource() == mnuiHelpAbout) {
	    j.add(new About());
	    j.repaint();
	}
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
	try {
	    Main.p.destroy();
	} catch (Exception ex) {
	    System.exit(0);
	}
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
}
