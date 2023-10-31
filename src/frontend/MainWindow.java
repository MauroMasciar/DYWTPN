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
import javax.swing.JOptionPane;

public class MainWindow extends JFrame implements ActionListener, WindowListener {
    private static final long serialVersionUID = -82854956961477559L;
    public static JFrame j = new JFrame();
    private JMenuBar menubar = new JMenuBar();
    private JMenu mnuConfig = new JMenu("Configuración");
    private JMenuItem mnuiConfigAdd = new JMenuItem("Añadir nuevo juego");
    private JMenuItem mnuiConfigRefresh = new JMenuItem("Actualizar datos");
    private JMenuItem mnuiConfigConfig = new JMenuItem("Configuración");
    private JMenu mnuPlayer = new JMenu("Jugador");
    private JMenuItem mnuiPlayerActivities = new JMenuItem("Actividad");
    private JMenu mnuHelp = new JMenu("Ayuda");
    private JMenuItem mnuHelpAbout = new JMenuItem("Acerca de");

    public MainWindow() {
	try {
	    j.setIconImage(new ImageIcon(getClass().getResource(
		    "/gfx/icon.png")).getImage());
	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(null,
		    "No se ha podido cargar algunos recursos.",
		    "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
	}

	j.setLayout(null);
	j.setTitle("DYWTPN");
	j.setBounds(30, 30, 1200, 800);
	j.setDefaultCloseOperation(EXIT_ON_CLOSE);
	//j.setExtendedState(MAXIMIZED_BOTH);

	menubar.add(mnuConfig);
	menubar.add(mnuPlayer);
	menubar.add(mnuHelp);
	mnuConfig.add(mnuiConfigAdd);
	mnuConfig.add(mnuiConfigRefresh);
	mnuConfig.add(mnuiConfigConfig);
	mnuPlayer.add(mnuiPlayerActivities);
	mnuHelp.add(mnuHelpAbout);

	mnuiConfigRefresh.addActionListener(this);
	mnuiConfigAdd.addActionListener(this);
	mnuiConfigConfig.addActionListener(this);
	mnuiPlayerActivities.addActionListener(this);

	j.add(new MainUI());

	j.setJMenuBar(menubar);

	j.setVisible(true);
	j.addWindowListener(this);

	/*new Thread(new Runnable() {
	    public void run() {
		while (true) {
		    try {
			Thread.sleep(1000);
			j.repaint();
		    } catch (InterruptedException ex) {
			ex.printStackTrace();
		    }
		}
	    }
	}).start();*/
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == mnuiConfigAdd) {
	    j.add(new AddGame());
	    j.repaint();
	} else if(e.getSource() == mnuiConfigConfig) {
	    j.add(new Config());
	    j.repaint();
	} else if(e.getSource() == mnuiPlayerActivities) {
	    j.add(new PlayerActivities());
	    j.repaint();
	} else if(e.getSource() == mnuiConfigRefresh) {
	    MainUI.LoadData();
	    MainUI.UpdateGameList();
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
