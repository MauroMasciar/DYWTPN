package frontend;

import backend.InGame;
import database.Games;

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
    private JMenu mnuGames = new JMenu("Juegos");
    private JMenuItem mnuiGamesAdd = new JMenuItem("Añadir nuevo juego");
    private JMenuItem mnuiGamesRefresh = new JMenuItem("Actualizar lista de juegos");
    private JMenu mnuHelp = new JMenu("Ayuda");
    private JMenuItem mnuHelpAbout = new JMenuItem("Acerca de");
    private JList<String> jlistGames = new JList<String>();
    private JScrollPane scrListGame = new JScrollPane(jlistGames);
    private DefaultListModel<String> modelList = new DefaultListModel<String>();
    private JTextField txtGameName = new JTextField(20);
    private JTextField txtHoursPlayed = new JTextField(20);
    private DecimalFormat txtHoursPlayedDecimal = new DecimalFormat("#.##");
    private JButton btnLaunchGame = new JButton("Lanzar");
    private JButton btnEditGame = new JButton("Editar");

    public int gameIdSelected = 0;
    public int gameIdLaunched = 0;

    public MainWindow() {
	j.setTitle("DYWTPN");
	j.setSize(800, 600);
	j.setDefaultCloseOperation(EXIT_ON_CLOSE);
	j.setLayout(new GridBagLayout());
	GridBagConstraints g = new GridBagConstraints();
	
	menubar.add(mnuGames);
	menubar.add(mnuHelp);
	mnuGames.add(mnuiGamesAdd);
	mnuGames.add(mnuiGamesRefresh);
	mnuHelp.add(mnuHelpAbout);
	
	j.setJMenuBar(menubar);

	mnuiGamesRefresh.addActionListener(this);
	mnuiGamesAdd.addActionListener(this);

	j.add(scrListGame);
	jlistGames.setModel(modelList);

	jlistGames.addListSelectionListener(this);

	btnLaunchGame.addActionListener(this);
	btnEditGame.addActionListener(this);

	txtGameName.setEditable(false);
	txtHoursPlayed.setEditable(false);

	j.add(txtGameName);
	j.add(txtHoursPlayed);
	j.add(btnLaunchGame);
	j.add(btnEditGame);

	btnLaunchGame.setEnabled(false);
	btnEditGame.setEnabled(false);

	j.addWindowListener(this);

	LoadData();

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
	txtHoursPlayed.setText("");

	Games g = new Games();
	ArrayList<String> listGames = new ArrayList<String>();
	listGames = g.getGamesNameList();
	for (int i = 1; i < listGames.size(); i++) {
	    modelList.addElement(listGames.get(i));
	}
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == mnuiGamesRefresh) {
	    UpdateGameList();
	    System.out.println("Lista de juegos actualizada");
	} else if(e.getSource() == mnuiGamesAdd) {
	    j.setVisible(false);
	    new AddGame();
	} else if(e.getSource() == btnEditGame) {
	    j.setVisible(false);
	    new EditGame(gameIdSelected);
	} else if(e.getSource() == btnLaunchGame) {
	    Games g = new Games();
	    String path = g.getPathFromGame(gameIdSelected);
	    System.out.println(path);
	    if(path == "null") {
		btnLaunchGame.setEnabled(false);
	    } else {
		ProcessBuilder pb;
		if(g.isGhost(gameIdSelected)) {
		    pb = new ProcessBuilder("notepad.exe");
		    JOptionPane.showMessageDialog(this, "Se ha abierto el bloc de notas. Cuando termine de jugar cierrelo"); // Hacer un .exe con un cartel
		} else {
		    pb = new ProcessBuilder(path);
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
				    System.out.println("Estado del proceso al cerrar: " + statusProcess);
				    System.out.println("Tiempo en la ultima sesion: " + ig.getGameTimePlayed());
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
	    Games g = new Games();

	    gameIdSelected = g.getIdFromGameName(txtGameName.getText());
	    double hours_played = g.getHoursPlayed(gameIdSelected);
	    txtHoursPlayed.setText(txtHoursPlayedDecimal.format(hours_played));
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
