package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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
import backend.Utils;
import database.ModelConfig;
import database.ModelGames;
import database.ModelPlayer;
import debug.Log;

public class MainUI extends JInternalFrame implements ActionListener, ListSelectionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private static JList<String> jlistGames = new JList<>();
    private final JScrollPane scrListGame = new JScrollPane(jlistGames);
    private static DefaultListModel<String> modelList = new DefaultListModel<>();
    private static final JTextField txtGameName = new JTextField(20);
    private final static JTextField txtLibrary = new JTextField(20);
    private static final JTextField txtCategory = new JTextField(20);
    private final JTextField txtPathGame = new JTextField(20);
    private static final JButton btnLaunchGame = new JButton("Lanzar");
    private final JButton btnEditGame = new JButton("Editar");
    //public static final JTextField txtTimePlaying = new JTextField(20);
    //public static final JTextField txtGamePlaying = new JTextField(20);
    private static final JTextArea txtStatistics = new JTextArea();
    private static final JTextArea txtTotalInfo = new JTextArea();
    private static final JTextArea txtLastDays = new JTextArea();
    private static final JTextArea txtLastAchie = new JTextArea();
    public static final JTextArea txtGames = new JTextArea();
    public static final JTextArea txtGamesTime = new JTextArea();
    // private final JLabel lblPortrait = new JLabel();
    private final JTextArea txtSeparator = new JTextArea();
    private final JPopupMenu popUpMenu = new JPopupMenu();
    private final JMenuItem mnuiLaunch = new JMenuItem("Lanzar");
    private final JMenuItem mnuiEdit = new JMenuItem("Editar");

    public int gameIdSelected = 0;
    public static boolean gamePaused = false;
    public static int gameIdLaunched = 0;
    private static boolean showHidden = false;
    private static boolean orderByDate = false;

    public MainUI() {
        setTitle("DYWTPN");
        ModelConfig mc = new ModelConfig();
        // setBounds(mc.getBounds_x("MainUI"), mc.getBounds_y("MainUI"), 1100, 280);
        setBounds(mc.getBounds_x("MainUI"), mc.getBounds_y("MainUI"), 900, 280);
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
        gbc.gridheight = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.BOTH;
        pnlLeft.add(scrListGame, gbc);
        /*gbc.gridy+=3;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 1.0;

		pnlLeft.add(txtSearch, gbc);

		gbc.fill = GridBagConstraints.BOTH;


		gbc.gridy++;
		gbc.weighty = 1.0;
		pnlLeft.add(txtGamePlaying, gbc);
		gbc.gridy++;
		pnlLeft.add(txtTimePlaying, gbc);*/

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
        pnlTop.add(txtTotalInfo, gbc);
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
        pnlBottom.add(txtLibrary, gbc);
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

        // Interfaz izquierda
        // Portada

        /*JPanel pnlPortrait = new JPanel();
	pnlPortrait.setLayout(new GridBagLayout());
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	try {
	    ImageIcon imgIcon = new ImageIcon(getClass().getResource("/gfx/test.jpg"));
	    Image imgEscalada = imgIcon.getImage().getScaledInstance(200,230,
		    Image.SCALE_SMOOTH);
	    ImageIcon iconoEscalado = new ImageIcon(imgEscalada);
	    lblPortrait.setIcon(iconoEscalado);
	} catch(NullPointerException ex) {
         * System.out.println("No carga la portada");
	}
	pnlPortrait.add(lblPortrait, gbc);*/


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
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(pnlBottom, gbc);

        /*gbc.gridx = 4;
	gbc.gridy = 0;
	gbc.gridwidth = 2;
	gbc.gridheight = 4;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnlPortrait, gbc);*/

        jlistGames.addListSelectionListener(this);
        jlistGames.addMouseListener(this);
        jlistGames.setComponentPopupMenu(popUpMenu);

        btnLaunchGame.addActionListener(this);
        btnEditGame.addActionListener(this);

        txtStatistics.setText(" CARGANDO ...");
        txtLastDays.setText(" CARGANDO ...");
        txtTotalInfo.setText(" CARGANDO ...");
        txtLastAchie.setText(" CARGANDO ...");


        txtGameName.setEditable(false);
        txtLibrary.setEditable(false);
        txtStatistics.setEditable(false);
        txtLastDays.setEditable(false);
        txtTotalInfo.setEditable(false);
        txtGames.setEditable(false);
        txtGamesTime.setEditable(false);
        txtLastAchie.setEditable(false);
        txtCategory.setEditable(false);
        txtSeparator.setEditable(false);

        txtSeparator.setText("__________________________________________________________________________________________________________________");

        txtGameName.setFont(new Font("Serief", Font.BOLD, 12));
        txtStatistics.setFont(new Font("Serief", Font.BOLD, 12));
        txtLastAchie.setFont(new Font("Serief", Font.BOLD, 12));
        txtLastDays.setFont(new Font("Serief", Font.BOLD, 12));
        txtTotalInfo.setFont(new Font("Serief", Font.BOLD, 12));
        txtGames.setFont(new Font("Serief", Font.BOLD, 12));
        txtGamesTime.setFont(new Font("Serief", Font.BOLD, 12));
        txtLibrary.setFont(new Font("Serief", Font.BOLD, 12));
        txtCategory.setFont(new Font("Serief", Font.BOLD, 12));

        txtPathGame.setEnabled(false);
        btnEditGame.setEnabled(false);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        if(gameIdLaunched != 0) {
                            loadData();
                        }
                        Thread.sleep(600000);
                    } catch (InterruptedException ex) {
                        Log.Loguear(ex.getMessage());
                    }
                }
            }
        }).start();

        /*new Thread(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(1000);
		    loadData();
		} catch (InterruptedException ex) {
		    ex.printStackTrace();
		}
	    }
	}).start();*/

        setVisible(true);
    }

    public static void loadGames() {
        ModelConfig mc = new ModelConfig();
        showHidden = mc.getIsHidden();
        orderByDate = mc.getOrderByDate();
        updateGameList();
    }

    public static void loadStatistics(int gameId, String sessionTime) {
        ModelConfig mc = new ModelConfig();
        ModelGames mg = new ModelGames();
        int totalSeconds = mg.getSecondsTotalPlayed();
        String totalTimePlayed = Utils.getTotalHoursFromSeconds(totalSeconds, true);
        String statistics = "", name = mc.getUsername();

        txtStatistics.setText(" Nombre: " + name + " | Tiempo total: " + totalTimePlayed);

        if(gameIdLaunched == 0) {
            statistics = " Nombre: " + name + " | Tiempo total: " + totalTimePlayed + " | " + mc.getLastGame() + " - " + mc.getLastSessionTime();
        } else {
            statistics = " Nombre: " + name + " | Tiempo total: " + totalTimePlayed + " | Jugando a: " + mg.getNameFromId(gameId) + " - Tiempo jugando: " + sessionTime;
        }

        String statusBar = statistics;
        MainWindow.updateStatusBar(statusBar);
    }

    public static void loadLastDays() {
        ModelGames mg = new ModelGames();
        int tuno = mg.getLastDays(0, 1, true);
        int tsiete = mg.getLastDays(0, 7, true);
        int tcatorce = mg.getLastDays(0, 14, true);
        int ttreinta = mg.getLastDays(0, 30, true);

        String uno = Utils.getTotalHoursFromSeconds(tuno, false);
        String siete = Utils.getTotalHoursFromSeconds(tsiete, false);
        String catorce = Utils.getTotalHoursFromSeconds(tcatorce, false);
        String treinta = Utils.getTotalHoursFromSeconds(ttreinta, false);

        txtLastDays.setText(" Horas el ultimo dia: " + uno + " | Semana: " + siete + " | 2 semanas: " + catorce + " | Mes: " + treinta);
    }

    public static void loadTotal() {
        ModelGames mg = new ModelGames();
        String totalInfo = " Total de juegos: " + mg.getTotalGames() + " | Iniciados: " + String.valueOf(mg.getCountGamesPlayed()) + " | Completados: "
                + String.valueOf(mg.getNumberCompletedGames()) + " | Sesiones: " + mg.getTotalSessions();
        txtTotalInfo.setText(totalInfo);
    }

    public static void loadAchievs() {
        ModelPlayer mp = new ModelPlayer();
        String lastAchie = " Ultima hazaña: " + mp.getLastAchievement();
        txtLastAchie.setText(lastAchie);
    }

    public static void loadTables() {
        ModelPlayer mp = new ModelPlayer();
        ModelGames mg = new ModelGames();
        PlayerHistory.tbPlayerHistory.removeAll();
        PlayerHistory.tbPlayerHistory.setModel(mp.getHistory("Todos"));
        PlayerActivities.tbPlayerActivities.removeAll();
        PlayerActivities.tbPlayerActivities.setModel(mp.getActivities("Todos"));
        GameList.tblGames.removeAll();
        GameList.tblGames.setModel(mg.getFilteredGameList("Todos", "Todos", "Todos", "Nombre"));
    }

    public static void loadLast() {
        //ModelConfig mc = new ModelConfig();
        txtGames.setText("");
        txtGamesTime.setText("");
    }

    public static void paint() {
        ModelConfig mc = new ModelConfig();
        if(gameIdLaunched == 0) {	    
            if(mc.getUsername().equals("PRUEBAS")) {
                txtStatistics.setForeground(Color.RED);
                txtLastAchie.setForeground(Color.RED);
                txtLastDays.setForeground(Color.RED);
                txtTotalInfo.setForeground(Color.RED);
                txtGames.setForeground(Color.RED);
                txtGamesTime.setForeground(Color.RED);
            } else {
                int theme = mc.getTheme();
                if(theme == 1) {
                    txtStatistics.setForeground(Color.BLACK);
                    txtLastAchie.setForeground(Color.BLACK);
                    txtLastDays.setForeground(Color.BLACK);
                    txtTotalInfo.setForeground(Color.BLACK);
                    txtGames.setForeground(Color.BLACK);
                    txtGamesTime.setForeground(Color.BLACK);
                }
            }
        }
    }

    public static void loadData() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if(gameIdLaunched == 0) loadGames();
                    Thread.sleep(500);
                    loadStatistics(0, "0");
                    Thread.sleep(500);
                    loadLastDays();
                    Thread.sleep(500);
                    loadTotal();
                    Thread.sleep(500);
                    loadAchievs();
                    Thread.sleep(500);
                    if(gameIdLaunched == 0) loadLast();
                    Thread.sleep(500);
                    paint();
                    Thread.sleep(500);
                    loadTables();
                    Thread.sleep(500);
                    verifyLoadStatistics();
                    Thread.sleep(500);
                    verifyLoadLastDays();
                    Thread.sleep(500);
                    verifyLoadTotal();
                    /*Thread.sleep(500);
					verifyLoadLast();*/
                    Thread.sleep(500);
                    verifyLoadLastAchie();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private static void verifyLoadStatistics() {
        if(txtStatistics.getText().equals(" CARGANDO ...")) {
            loadStatistics(0, "0");
        }
    }

    private static void verifyLoadLastDays() {
        if(txtLastDays.getText().equals(" CARGANDO ...")) {
            loadLastDays();
        }
    }

    private static void verifyLoadTotal() {
        if(txtTotalInfo.getText().equals(" CARGANDO ...")) {
            loadTotal();
        }
    }

    /*private static void verifyLoadLast() {
		if(txtGamesTime.getText().equals(" CARGANDO ...") || txtGamePlaying.getText().equals(" CARGANDO ...") || 
				txtGames.getText().equals(" CARGANDO ...") || txtTimePlaying.getText().equals(" CARGANDO ...")) {
			loadLast();
		}
	}*/

    private static void verifyLoadLastAchie() {
        if(txtLastAchie.getText().equals(" CARGANDO ...")) {
            loadAchievs();
        }
    }

    private static void updateGameList() {
        jlistGames.removeAll();
        modelList.clear();
        txtGameName.setText("");
        txtLibrary.setText("");
        txtCategory.setText("");

        ModelGames g = new ModelGames();
        ArrayList<String> listGames = new ArrayList<>();
        listGames = g.getGamesNameList(showHidden, orderByDate);
        for (int i = 1; i < listGames.size(); i++) {
            modelList.addElement(listGames.get(i));
        }
        jlistGames.setModel(modelList);
    }

    /*public void act() {
	KeyEvent k = new KeyEvent(txtSearch, 87, 1, 0, 87, 'w',KeyEvent.KEY_LOCATION_STANDARD );
	keyReleased(k);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindow.j.setLayout(null);
        if(e.getSource() == btnEditGame || e.getSource() == mnuiEdit) {
            if(gameIdSelected == 0) {
                JOptionPane.showMessageDialog(this, "Primero selecciona que juego quieres editar", "Error al editar juego", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MainWindow.j.add(new EditGame(gameIdSelected));
            MainWindow.j.repaint();
        } else if(e.getSource() == btnLaunchGame || e.getSource() == mnuiLaunch) {
            if(gameIdLaunched == 0) launchGame();
            else {
                if(gamePaused) {
                    gamePaused = false;
                    btnLaunchGame.setText("Pausar");
                } else {
                    gamePaused = true;
                    btnLaunchGame.setText("Continuar");
                }
            }
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
                String totalPlayed = Utils.getTotalHoursFromSeconds(mg.getSecondsPlayed(gameIdSelected), false);
                txtGames.setText(" Juego: " + txtGameName.getText() + " | Tiempo: " + totalPlayed + " | Veces jugado: " + mg.getPlayCount(gameIdSelected) + " | Ultima sesion: "
                        + mg.getDateLastSession(gameIdSelected));

                int tuno = mg.getLastDays(gameIdSelected, 1, true);
                int tsiete = mg.getLastDays(gameIdSelected, 7, true);
                int tcatorce = mg.getLastDays(gameIdSelected, 14, true);
                int ttreinta = mg.getLastDays(gameIdSelected, 30, true);
                String uno = Utils.getTotalHoursFromSeconds(tuno, false);
                String siete = Utils.getTotalHoursFromSeconds(tsiete, false);
                String catorce = Utils.getTotalHoursFromSeconds(tcatorce, false);
                String treinta = Utils.getTotalHoursFromSeconds(ttreinta, false);
                txtGamesTime.setText(" Horas el ultimo dia: " + uno + " | 7 dias: " + siete + " | 14 dias: " + catorce + " | 30 dias: " + treinta);
                txtCategory.setText(mg.getGameCategoryName(gameIdSelected));
                txtLibrary.setText(mg.getLibraryName(gameIdSelected));
            }
        }
    }

    private void launchGame() {
        if(txtGameName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero selecciona que juego quieres lanzar", "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ModelGames mg = new ModelGames();
        String path = mg.getPathFromGame(gameIdSelected);
        ProcessBuilder pb;
        if (mg.isGhost(gameIdSelected)) {
            pb = new ProcessBuilder("GhostGame.exe");
        } else {
            pb = new ProcessBuilder(path);
        }
        Process p;
        try {
            p = pb.start();
            if (p.isAlive()) {
                InGame ig = new InGame(gameIdSelected, txtGameName.getText(), mg.getSecondsPlayed(gameIdSelected));
                gameIdLaunched = gameIdSelected;
                btnLaunchGame.setText("Pausar");

                new Thread(new Runnable() {
                    public void run() {
                        int statusProcess;
                        try {
                            statusProcess = p.waitFor();
                            Log.Loguear("Estado del proceso al cerrar: " + statusProcess);
                            Log.Loguear("Tiempo en la ultima sesion: " + ig.getGameTimePlayed());
                            gameIdLaunched = 0;
                            gamePaused = false;
                            btnLaunchGame.setText("Lanzar");
                            ig.closeGame();
                            loadData();
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(null, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n\n" + ex.getMessage(), "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }).start();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se ha podido lanzar el juego. Verifique que la ruta sea correcta.\n\n" + ex.getMessage(), "Error al lanzar juego", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == jlistGames && e.getClickCount() == 2) {
            launchGame();
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
}
