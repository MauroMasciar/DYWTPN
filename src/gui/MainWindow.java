package gui;

import database.games;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener {
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
	private JButton btnLaunchGame = new JButton("Lanzar");

	private int gameIdSelected = 0;
	private int gameIdLaunched = 0;


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
		
		//j.add(jlistGames);
		j.add(scrListGame);
		jlistGames.setModel(modelList);
		UpdateGameList();

		jlistGames.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String s = jlistGames.getSelectedValue();
				txtGameName.setText(s);
				games g = new games();

				gameIdSelected = g.getIdFromGameName(txtGameName.getText());
				double hours_played = g.getHoursPlayed(gameIdSelected);
				txtHoursPlayed.setText(String.valueOf(hours_played));
			}
		});

		txtGameName.setEditable(false);
		txtHoursPlayed.setEditable(false);

		btnLaunchGame.addActionListener(this);

		j.add(txtGameName);
		j.add(txtHoursPlayed);
		j.add(btnLaunchGame);

		j.setVisible(true);
	}

	public void UpdateGameList() {
		jlistGames.removeAll();
		modelList.clear();

		games g = new games();
		ArrayList<String> listGames = new ArrayList<String>();
		listGames = g.getGamesNameList();
		for(int i=1;i<listGames.size();i++) {
			modelList.addElement(listGames.get(i));
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mnuiGamesRefresh) {
			UpdateGameList();
			System.out.println("Lista de juegos actualizada");
		} else if(e.getSource() == btnLaunchGame) {
			try {
				Runtime app = Runtime.getRuntime();
				games g = new games();
				String path = g.getPathFromGame(gameIdSelected);
				System.out.println(path);
				System.out.println(gameIdSelected);
				app.exec(path);
			} catch(Exception ex) {
				ex.getMessage();
			}
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Exception e) {
			System. out. println("No se ha podido configurar el look and feel: " + e.getMessage( ));
			e.printStackTrace();
       }
	   new MainWindow();
	}
}
