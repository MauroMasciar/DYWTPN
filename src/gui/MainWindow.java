package gui;

import backend.InGame;
import database.Games;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class MainWindow extends JFrame implements ActionListener, ListSelectionListener {
    /**
     * 
     */
    private static final long serialVersionUID = -82854956961477559L;
    private JFrame j = new JFrame();
    private JMenuBar menubar = new JMenuBar();
    private JMenu mnuGames = new JMenu("Juegos");
    private JMenuItem mnuiGamesAdd = new JMenuItem("Añadir nuevo juego");
    private JMenuItem mnuiGamesRefresh = new JMenuItem("Actualizar lista de juegos");
    private JList<String> jlistGames = new JList<String>();
    private JScrollPane scrListGame = new JScrollPane(jlistGames);
    private DefaultListModel<String> modelList = new DefaultListModel<String>();
    private JTextField txtGameName = new JTextField(20);
    private JTextField txtHoursPlayed = new JTextField(20);
    private DecimalFormat txtHoursPlayedDecimal = new DecimalFormat("#.##");
    private JButton btnLaunchGame = new JButton("Lanzar");

    public int gameIdSelected = 0;
    public int gameIdLaunched = 0;

    public MainWindow() {
	j.setTitle("DYWTPN");
	j.setSize(800, 600);
	j.setDefaultCloseOperation(EXIT_ON_CLOSE);
	j.setLayout(new FlowLayout());

	mnuGames.add(mnuiGamesAdd);
	mnuGames.add(mnuiGamesRefresh);
	menubar.add(mnuGames);
	j.setJMenuBar(menubar);

	mnuiGamesRefresh.addActionListener(this);

	j.add(scrListGame);
	jlistGames.setModel(modelList);

	jlistGames.addListSelectionListener(this);

	btnLaunchGame.addActionListener(this);

	txtGameName.setEditable(false);
	txtHoursPlayed.setEditable(false);

	j.add(txtGameName);
	j.add(txtHoursPlayed);
	j.add(btnLaunchGame);

	btnLaunchGame.setEnabled(false);

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
	if (e.getSource() == mnuiGamesRefresh) {
	    UpdateGameList();
	    System.out.println("Lista de juegos actualizada");
	} else if (e.getSource() == btnLaunchGame) { //TODO: Que al no tener ningun juego seleccionado no intente lanzarlo
	    Games g = new Games();
	    String path = g.getPathFromGame(gameIdSelected); // Aca recibe el null si el juego no esta seleccionado
	    System.out.println(path);
	    if(path == "null") {
		btnLaunchGame.setEnabled(false);
	    } else {
		ProcessBuilder pb = new ProcessBuilder(path);
		Process p;
		try {
		    p = pb.start();
		    if (p.isAlive()) {
			InGame ig = new InGame(gameIdSelected);
			gameIdLaunched = gameIdSelected;

			int statusProcess;
			statusProcess = p.waitFor();
			System.out.println("Estado del proceso al cerrar: " + statusProcess);
			System.out.println("Tiempo en la ultima sesion: " + ig.getGameTimePlayed());
			ig.closeGame();
			UpdateGameList();
		    }
		} catch (IOException ex) {
		    JOptionPane.showMessageDialog(null, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n(1)", "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
		} catch (InterruptedException ex) {
		    JOptionPane.showMessageDialog(null, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n(2)", "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
	if (e.getSource() == jlistGames) {
	    btnLaunchGame.setEnabled(true);
	    String s = jlistGames.getSelectedValue();
	    txtGameName.setText(s);
	    Games g = new Games();

	    gameIdSelected = g.getIdFromGameName(txtGameName.getText());
	    double hours_played = g.getHoursPlayed(gameIdSelected);
	    txtHoursPlayed.setText(txtHoursPlayedDecimal.format(hours_played));
	}
    }
}
