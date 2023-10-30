package frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import backend.InGame;
import database.ModelConfig;
import database.ModelGames;
import database.ModelPlayer;
import debug.Log;

public class MainUI extends JInternalFrame implements ActionListener, ListSelectionListener {
    private static final long serialVersionUID = 1L;
    private static JList<String> jlistGames = new JList<String>();
    private JScrollPane scrListGame = new JScrollPane(jlistGames);
    private static DefaultListModel<String> modelList = new DefaultListModel<String>();
    private static JTextField txtGameName = new JTextField(20);
    private static JTextField txtMinsPlayed = new JTextField(20);
    private JTextField txtGhostGame = new JTextField(20);
    private JTextField txtPathGame = new JTextField(20);
    private DecimalFormat txtMinsPlayedDecimal = new DecimalFormat("#.##");
    private static JButton btnLaunchGame = new JButton("Lanzar");
    private JButton btnEditGame = new JButton("Editar");
    public static JTextField txtTimePlaying = new JTextField(20);
    public static JTextField txtGamePlaying = new JTextField(20);
    private static JTextArea txtStatistics = new JTextArea();
    private static JTextArea txtLastAchie = new JTextArea();

    public int gameIdSelected = 0;
    public int gameIdLaunched = 0;

    public MainUI() {	
	setTitle("DYWTPN");
	//setBounds(600, 300, 800, 280);
	setBounds(300, 300, 800, 280);
	setClosable(false);
	setResizable(true);
	setIconifiable(false);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	// Interfaz izquierda
	JPanel pnlLeft = new JPanel();
	pnlLeft.setLayout(new GridBagLayout());
	// Panel izquierdo
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 3.0;
	gbc.fill = GridBagConstraints.BOTH;
	pnlLeft.add(scrListGame, gbc);
	gbc.gridy = 1;
	gbc.weighty = 1.0;
	pnlLeft.add(txtGamePlaying, gbc);
	gbc.gridy = 2;	
	pnlLeft.add(txtTimePlaying, gbc);

	// Interfaz superior
	JPanel pnlTop = new JPanel();
	pnlTop.setLayout(new GridBagLayout());
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	pnlTop.add(txtStatistics, gbc);
	gbc.gridy = 1;
	pnlTop.add(txtLastAchie, gbc);
	
	// Interfaz inferior - controles
	JPanel pnlBottom = new JPanel();
	pnlBottom.setLayout(new GridBagLayout());

	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	pnlBottom.add(txtGameName, gbc);
	gbc.gridx ++;
	pnlBottom.add(txtMinsPlayed, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	pnlBottom.add(txtGhostGame, gbc);
	gbc.gridx ++;
	pnlBottom.add(txtPathGame, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	pnlBottom.add(btnEditGame, gbc);
	gbc.gridx ++;
	gbc.gridx = 1;
	pnlBottom.add(btnLaunchGame, gbc);

	// Paneles
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 4;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnlLeft, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	gbc.gridwidth = 3; //ancho
	gbc.gridheight = 1;
	gbc.weightx = 3.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnlTop, gbc);

	gbc.gridx = 1;
	gbc.gridy = 1;
	gbc.gridwidth = 2; //ancho
	gbc.gridheight = 3;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnlBottom, gbc);

	jlistGames.setModel(modelList);
	jlistGames.addListSelectionListener(this);

	btnLaunchGame.addActionListener(this);
	btnEditGame.addActionListener(this);
	

	txtGameName.setEditable(false);
	txtMinsPlayed.setEditable(false);
	txtStatistics.setText(" Nombre: Usuario | Total de juegos: 0 | Horas jugadas en total: 0");
	txtStatistics.setEditable(false);
	txtGamePlaying.setText(" No estas jugando a nada");
	txtGamePlaying.setEditable(false);
	txtTimePlaying.setText(" Tiempo jugado: X");
	txtTimePlaying.setEditable(false);
	txtLastAchie.setText(" Ultima hazaña: -");
	txtLastAchie.setEditable(false);
	
	txtPathGame.setEnabled(false);
	txtGhostGame.setEnabled(false);

	btnLaunchGame.setEnabled(false);
	btnEditGame.setEnabled(false);

	UpdateGameList();
	LoadData();
	
	setVisible(true);
	
	new Thread(new Runnable() {
	    public void run() {
		while (true) {
		    try {
			if(gameIdLaunched != 0) LoadData();
			Thread.sleep(600000);
		    } catch (InterruptedException ex) {
			Log.Loguear(ex.getMessage());
		    }
		}
	    }
	}).start();
    }
    
    public static void LoadData() {
	ModelConfig mc = new ModelConfig();
	ModelPlayer mp = new ModelPlayer();
	txtStatistics.setText(" Nombre: " + mc.getNameUser() + " | Total de juegos: " + modelList.size() + " | Horas jugadas en total: " + mc.getMinutesTotalPlayed()/60);
	txtLastAchie.setText(" Ultima hazaña: " + mp.getLastAchievement());
    }
    
    public static void UpdateGameList() {
	btnLaunchGame.setEnabled(false);
	jlistGames.removeAll();
	modelList.clear();
	txtGameName.setText("");
	txtMinsPlayed.setText("");

	ModelGames g = new ModelGames();
	ArrayList<String> listGames = new ArrayList<String>();
	listGames = g.getGamesNameList();
	try {
	    for (int i = 1; i < listGames.size(); i++) {
		modelList.addElement(listGames.get(i));
	    }
	} catch (ArrayIndexOutOfBoundsException ex) {
	    UpdateGameList();
	}
	String s = "Juegos cargados: " + (listGames.size() - 1);
	Log.Loguear(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnEditGame) {
	    MainWindow.j.add(new EditGame(gameIdSelected));
	    MainWindow.j.repaint();
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
			ModelGames mg = new ModelGames();
			InGame ig = new InGame(gameIdSelected, txtGameName.getText(), mg.getMinsPlayed(gameIdSelected));
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
		double mins_played = g.getMinsPlayed(gameIdSelected);
		txtMinsPlayed.setText(txtMinsPlayedDecimal.format(mins_played/60));
	    }
	}
    }
}
