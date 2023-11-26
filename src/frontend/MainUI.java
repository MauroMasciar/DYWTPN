package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import backend.InGame;
import database.ModelConfig;
import database.ModelGames;
import database.ModelPlayer;
import debug.Log;

public class MainUI extends JInternalFrame implements ActionListener, ListSelectionListener, MouseListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private static JList<String> jlistGames = new JList<String>();
    private JScrollPane scrListGame = new JScrollPane(jlistGames);
    private static DefaultListModel<String> modelList = new DefaultListModel<String>();
    private JTextField txtSearch = new JTextField(20);
    private static JTextField txtGameName = new JTextField(20);
    private static JTextField txtMinsPlayed = new JTextField(20);
    private static JTextField txtCategory = new JTextField(20);
    private JTextField txtPathGame = new JTextField(20);
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private static JButton btnLaunchGame = new JButton("Lanzar");
    private JButton btnEditGame = new JButton("Editar");
    public static JTextField txtTimePlaying = new JTextField(20);
    public static JTextField txtGamePlaying = new JTextField(20);
    private static JTextArea txtStatistics = new JTextArea();
    private static JTextArea txtLastDays = new JTextArea();
    private static JTextArea txtLastAchie = new JTextArea();
    public static JTextArea txtGames = new JTextArea();
    public static JTextArea txtGamesTime = new JTextArea();
    public JTextArea txtSeparator = new JTextArea();
    private JPopupMenu popUpMenu = new JPopupMenu();
    private JMenuItem mnuiLaunch = new JMenuItem("Lanzar");
    private JMenuItem mnuiEdit = new JMenuItem("Editar");

    public int gameIdSelected = 0;
    public static int gameIdLaunched = 0;
    private static boolean showHidden = false;

    public MainUI() {
	setTitle("DYWTPN");
	ModelConfig mc = new ModelConfig();
	setBounds(mc.getBounds_x("MainUI"), mc.getBounds_y("MainUI"), 800, 280);
	setClosable(false);
	setResizable(true);
	setIconifiable(false);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	popUpMenu.add(mnuiLaunch);
	popUpMenu.add(mnuiEdit);
	mnuiLaunch.addActionListener(this);
	mnuiEdit.addActionListener(this);

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
	gbc.gridy++;
	pnlLeft.add(txtSearch, gbc);
	gbc.gridy++;
	gbc.weighty = 1.0;
	pnlLeft.add(txtGamePlaying, gbc);
	gbc.gridy++;
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
	gbc.gridy++;
	pnlTop.add(txtLastDays, gbc);
	gbc.gridy++;
	pnlTop.add(txtLastAchie, gbc);
	gbc.gridy++;
	pnlTop.add(txtSeparator, gbc);
	gbc.gridy++;
	pnlTop.add(txtGames, gbc);
	gbc.gridy++;
	pnlTop.add(txtGamesTime, gbc);

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
	gbc.gridx++;
	pnlBottom.add(txtMinsPlayed, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnlBottom.add(txtCategory, gbc);
	gbc.gridx++;
	pnlBottom.add(txtPathGame, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnlBottom.add(btnEditGame, gbc);
	gbc.gridx++;
	gbc.gridx = 1;
	pnlBottom.add(btnLaunchGame, gbc);

	// Paneles
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 5;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnlLeft, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	gbc.gridwidth = 3; // ancho
	gbc.gridheight = 1;
	gbc.weightx = 3.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnlTop, gbc);

	gbc.gridx = 1;
	gbc.gridy = 1;
	gbc.gridwidth = 2; // ancho
	gbc.gridheight = 3;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnlBottom, gbc);

	jlistGames.addListSelectionListener(this);
	jlistGames.addMouseListener(this);
	jlistGames.setComponentPopupMenu(popUpMenu);

	btnLaunchGame.addActionListener(this);
	btnEditGame.addActionListener(this);

	txtSearch.addKeyListener(this);

	txtGameName.setEditable(false);
	txtMinsPlayed.setEditable(false);
	txtStatistics.setForeground(Color.RED);
	txtTimePlaying.setForeground(Color.RED);
	txtGamePlaying.setForeground(Color.RED);
	txtLastAchie.setForeground(Color.RED);
	txtLastDays.setForeground(Color.RED);
	txtStatistics.setText(" CARGANDO ...");
	txtStatistics.setEditable(false);
	txtLastDays.setText(" CARGANDO ...");
	txtLastDays.setEditable(false);
	txtGames.setEditable(false);
	txtGamesTime.setEditable(false);
	txtGamePlaying.setEditable(false);
	txtGamePlaying.setText(" CARGANDO ...");
	txtTimePlaying.setEditable(false);
	txtTimePlaying.setText(" CARGANDO ...");
	txtLastAchie.setText(" CARGANDO ...");
	txtLastAchie.setEditable(false);
	txtSeparator.setEditable(false);
	txtSeparator.setText(
		"______________________________________________________________________________________________________");
	txtCategory.setForeground(Color.BLACK);

	txtStatistics.setFont(new Font("Serief", Font.BOLD, 12));
	txtGamePlaying.setFont(new Font("Serief", Font.BOLD, 12));
	txtTimePlaying.setFont(new Font("Serief", Font.BOLD, 12));
	txtLastAchie.setFont(new Font("Serief", Font.BOLD, 12));
	txtLastDays.setFont(new Font("Serief", Font.BOLD, 12));

	txtPathGame.setEnabled(false);
	txtCategory.setEditable(false);

	btnEditGame.setEnabled(false);

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

	new Thread(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(500);
		    LoadData();
		} catch (InterruptedException ex) {
		    ex.printStackTrace();
		}
	    }
	}).start();

	setVisible(true);
    }

    public static void LoadData() {
	ModelConfig mc = new ModelConfig();
	ModelPlayer mp = new ModelPlayer();
	ModelGames mg = new ModelGames();
	double totalHours = mg.getMinutesTotalPlayed();

	showHidden = mc.getIsHidden();
	if (gameIdLaunched == 0) UpdateGameList();

	txtStatistics.setText(" Nombre: " + mc.getUsername() + " | Total de juegos: " + modelList.size() + " | Total de horas: " + decimalFormat.format(totalHours / 60));

	double uno = mg.getLastDays(0, 1);
	double siete = mg.getLastDays(0, 7);
	double catorce = mg.getLastDays(0, 14);
	double treinta = mg.getLastDays(0, 30);

	txtLastDays.setText(" Horas el ultimo dia: " + decimalFormat.format(uno / 60) + " | Semana: "
		+ decimalFormat.format(siete / 60) + " | 2 semanas: " + decimalFormat.format(catorce / 60) + " | Mes: "
		+ decimalFormat.format(treinta / 60));
	
	txtLastAchie.setText(" Ultima hazaña: " + mp.getLastAchievement());
	PlayerHistory.tbPlayerHistory.removeAll();
	PlayerHistory.tbPlayerHistory.setModel(mp.getHistory("Todos"));
	PlayerActivities.tbPlayerActivities.removeAll();
	PlayerActivities.tbPlayerActivities.setModel(mp.getActivities("Todos"));
	GameList.tblGames.removeAll();
	GameList.tblGames.setModel(mg.getFilteredGameList("Todos", "Todos", "Todos"));
	if(gameIdLaunched == 0) {
	    txtGames.setText("");
	    txtGamesTime.setText("");
	    // LoadLastSession
	    txtGamePlaying.setText(mc.getLastGame());
	    txtTimePlaying.setText(mc.getLastSessionTime());
	    if (mc.getUsername().equals("PRUEBAS")) {
		txtStatistics.setForeground(Color.RED);
		txtTimePlaying.setForeground(Color.RED);
		txtGamePlaying.setForeground(Color.RED);
		txtLastAchie.setForeground(Color.RED);
		txtLastDays.setForeground(Color.RED);
	    } else {
		txtTimePlaying.setForeground(Color.BLACK);
		txtGamePlaying.setForeground(Color.BLACK);
		txtStatistics.setForeground(Color.BLACK);
		txtLastAchie.setForeground(Color.BLACK);
		txtLastDays.setForeground(Color.BLACK);
	    }
	}
    }

    public static void UpdateGameList() {
	jlistGames.removeAll();
	modelList.clear();
	txtGameName.setText("");
	txtMinsPlayed.setText("");
	txtCategory.setText("");

	ModelGames g = new ModelGames();
	ArrayList<String> listGames = new ArrayList<String>();
	listGames = g.getGamesNameList(showHidden);
	for (int i = 1; i < listGames.size(); i++) modelList.addElement(listGames.get(i));
	jlistGames.setModel(modelList);
	Log.Loguear("Actualizando datos");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnEditGame || e.getSource() == mnuiEdit) {
	    if(gameIdSelected == 0) {
		JOptionPane.showMessageDialog(this, "Primero selecciona que juego quieres editar", "Error al editar juego", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    MainWindow.j.add(new EditGame(gameIdSelected));
	    MainWindow.j.repaint();
	} else if(e.getSource() == btnLaunchGame || e.getSource() == mnuiLaunch) {
	    LaunchGame();
	}
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
	if(e.getSource() == jlistGames) {
	    btnEditGame.setEnabled(true);
	    String s = jlistGames.getSelectedValue();
	    txtGameName.setText(s);
	    ModelGames mg = new ModelGames();

	    gameIdSelected = mg.getIdFromGameName(txtGameName.getText());
	    if(gameIdSelected != 0) {
		double mins_played = mg.getMinsPlayed(gameIdSelected);
		txtMinsPlayed.setText(decimalFormat.format(mins_played / 60));
		txtGames.setText(" Juego: " + txtGameName.getText() + " | Horas jugadas: " + txtMinsPlayed.getText()
		+ " | Veces jugado: " + mg.getPlayCount(gameIdSelected) + " | Ultima sesion: "
		+ mg.getDateLastSession(gameIdSelected));

		double uno = mg.getLastDays(gameIdSelected, 1);
		double siete = mg.getLastDays(gameIdSelected, 7);
		double catorce = mg.getLastDays(gameIdSelected, 14);
		double treinta = mg.getLastDays(gameIdSelected, 30);

		txtGamesTime.setText(" Horas ultimo dia: " + decimalFormat.format(uno / 60) + " | Semana: " + decimalFormat.format(siete / 60) + " | 2 semanas: " + decimalFormat.format(catorce / 60)
		+ " | Mes: " + decimalFormat.format(treinta / 60));
		txtCategory.setText(mg.getGameCategoryName(gameIdSelected));
	    }
	}
    }

    private void LaunchGame() {
	if(txtGameName.getText().isEmpty()) {
	    JOptionPane.showMessageDialog(this, "Primero selecciona que juego quieres lanzar", "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	if(gameIdLaunched == 0) {
	    ModelGames g = new ModelGames();
	    String path = g.getPathFromGame(gameIdSelected);
	    ProcessBuilder pb;
	    if (g.isGhost(gameIdSelected)) {
		pb = new ProcessBuilder("GhostGame.exe");
	    } else {
		pb = new ProcessBuilder(path);
	    }
	    Process p;
	    try {
		p = pb.start();
		if (p.isAlive()) {
		    ModelGames mg = new ModelGames();
		    InGame ig = new InGame(gameIdSelected, txtGameName.getText(), mg.getSecondsPlayed(gameIdSelected));
		    gameIdLaunched = gameIdSelected;

		    new Thread(new Runnable() {
			public void run() {
			    int statusProcess;
			    try {
				statusProcess = p.waitFor();
				Log.Loguear("Estado del proceso al cerrar: " + statusProcess);
				Log.Loguear("Tiempo en la ultima sesion: " + ig.getGameTimePlayed());
				gameIdLaunched = 0;
				ig.closeGame();
				UpdateGameList();
			    } catch (InterruptedException ex) {
				JOptionPane.showMessageDialog(null, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n\n" + ex.getMessage(), "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
			    }
			}
		    }).start();
		}
	    } catch (IOException ex) {
		JOptionPane.showMessageDialog(this, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n\n" + ex.getMessage(), "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
	    }
	} else {
	    JOptionPane.showMessageDialog(this, "No se ha podido lanzar el juego porque ya tienes uno ejecutandose", "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	if(e.getSource() == jlistGames && e.getClickCount() == 2) {
	    LaunchGame();
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
	if(txtSearch.getText().isEmpty()) {
	    UpdateGameList();
	} else {
	    jlistGames.removeAll();
	    modelList.clear();
	    txtGameName.setText("");
	    txtMinsPlayed.setText("");
	    txtCategory.setText("");

	    ModelGames g = new ModelGames();
	    ArrayList<String> listGames = new ArrayList<String>();
	    listGames = g.getGamesNameList(txtSearch.getText());
	    for (int i = 1; i < listGames.size(); i++)
		modelList.addElement(listGames.get(i));
	    jlistGames.setModel(modelList);
	}
    }
}
