package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.ModelGames;

public class EditGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 4203707721315187637L;
    private final JPanel pnlDetails = new JPanel();
    private final JLabel lblTitle = new JLabel("Titulo:");
    private final JLabel lblReleaseDate = new JLabel("Fecha de lanzamiento:");
    private final JLabel lblRating = new JLabel(" Rating:");
    private final JLabel lblGenre = new JLabel("Genero:");
    private final JLabel lblPlatform = new JLabel(" Plataforma:");
    private final JLabel lblDeveloper = new JLabel("Desarrollador:");
    private final JLabel lblPublisher = new JLabel(" Publicador:");
    private final JLabel lblSeries = new JLabel("Serie:");
    private final JLabel lblRegion = new JLabel(" Region:");
    private final JLabel lblPlayMode = new JLabel("Modo de juego:");
    private final JLabel lblVersion = new JLabel(" Version:");
    private final JLabel lblStatus = new JLabel("Estado:");
    private final JLabel lblSource = new JLabel("Fuente:");
    private final JLabel lblLastPlayed = new JLabel("Ultima sesion:");
    private final JLabel lblAdded = new JLabel(" Añadido:");
    private final JLabel lblAddedDate = new JLabel("04/10/2023");
    private final JLabel lblGameTime = new JLabel(" Tiempo jugado:");
    private final JLabel lblPlayCount = new JLabel(" Veces jugado:");
    private final JComboBox<String> cbTitle = new JComboBox<String>();
    private final JTextField txtReleaseDate = new JTextField(20);
    private final JTextField txtRating = new JTextField(20);
    private final JTextField txtGenre = new JTextField(10);
    private final JTextField txtPlatform = new JTextField(10);
    private final JTextField txtDeveloper = new JTextField(10);
    private final JTextField txtPublisher = new JTextField(10);
    private final JTextField txtSeries = new JTextField(10);
    private final JTextField txtRegion = new JTextField(10);
    private final JTextField txtPlayMode = new JTextField(10);
    private final JTextField txtVersion = new JTextField(10);
    private final JTextField txtStatus = new JTextField(10);
    private final JTextField txtSource = new JTextField(10);
    private final JTextField txtLastPlayed = new JTextField(10);
    private final JTextField txtGameTime = new JTextField(10);
    private final JTextField txtPlayCount = new JTextField(10);
    private final JCheckBox chFavorite = new JCheckBox("Favorito");
    private final JCheckBox chCompleted = new JCheckBox("Completado");
    private final JCheckBox chBroken = new JCheckBox("Roto");
    private final JCheckBox chGhost = new JCheckBox("Fantasma");
    private final JCheckBox chPortable = new JCheckBox("Portable");
    private final JCheckBox chHide = new JCheckBox("Oculto");

    public EditGame(int gameId) {
	setTitle("Editar juegos");
	setSize(800, 400);
	setClosable(true);
	setResizable(true);
	setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	setLayout(new FlowLayout());

	pnlDetails.setLayout(new GridBagLayout());
	pnlDetails.setBorder(BorderFactory.createTitledBorder("Detalles"));

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 8;
	gbc.ipady = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	pnlDetails.add(lblTitle, gbc);
	gbc.gridx++;
	gbc.gridwidth = 7;
	pnlDetails.add(cbTitle, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblReleaseDate, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtReleaseDate, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblRating, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtRating, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblGenre, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtGenre, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblPlatform, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtPlatform, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblDeveloper, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtDeveloper, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblPublisher, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtPublisher, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblSeries, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtSeries, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblRegion, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtRegion, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblPlayMode, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtPlayMode, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblVersion, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtVersion, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblStatus, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtStatus, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblAdded, gbc);
	gbc.gridx++;
	gbc.gridwidth = 1;
	pnlDetails.add(lblAddedDate, gbc);
	gbc.gridx++;
	pnlDetails.add(chFavorite, gbc);
	gbc.gridx++;
	pnlDetails.add(chPortable, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblSource, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtSource, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblGameTime, gbc);
	gbc.gridx++;
	pnlDetails.add(txtGameTime, gbc);
	gbc.gridx++;	
	pnlDetails.add(chCompleted, gbc);
	gbc.gridx++;
	pnlDetails.add(chHide, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblLastPlayed, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtLastPlayed, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblPlayCount, gbc);
	gbc.gridx++;
	pnlDetails.add(txtPlayCount, gbc);
	gbc.gridx++;
	pnlDetails.add(chBroken, gbc);
	gbc.gridx++;
	pnlDetails.add(chGhost, gbc);

	add(pnlDetails);

	cbTitle.addActionListener(this);
	LoadGameList();
	
	if (gameId != 0) {
	    ModelGames mg = new ModelGames();
	    cbTitle.setSelectedItem(mg.getNameFromId(gameId));
	}

	setVisible(true);
    }

    public void LoadGameList() {
	cbTitle.removeAllItems();
	ArrayList<String> listGames = new ArrayList<String>();
	listGames.clear();
	ModelGames mg = new ModelGames();
	listGames = mg.getGamesNameList(true);
	for (int i = 1; i < listGames.size(); i++) {
	    cbTitle.addItem(listGames.get(i));
	}
    }
    
    public void LoadData(String gameName) {
	ModelGames mg = new ModelGames();
	int gameId = mg.getIdFromGameName(gameName);
	int secondsPlayed = mg.getSecondsPlayed(gameId);
	int playCount = mg.getPlayCount(gameId);
	
	txtReleaseDate.setText(mg.getReleaseDate(gameId));
	txtRating.setText(mg.getRating(gameId));
	txtGenre.setText(mg.getGenre(gameId));
	txtPlatform.setText(mg.getPlatform(gameId));
	txtDeveloper.setText(mg.getDeveloper(gameId));
	txtPublisher.setText(mg.getPublisher(gameId));
	txtSeries.setText(mg.getSeries(gameId));
	txtRegion.setText(mg.getRegion(gameId));
	txtPlayMode.setText(mg.getPlayMode(gameId));
	txtVersion.setText(mg.getVersion(gameId));
	txtStatus.setText(mg.getStatus(gameId));
	txtSource.setText(mg.getSource(gameId));
	txtLastPlayed.setText(mg.getLastPlayed(gameId));
	txtGameTime.setText(String.valueOf(secondsPlayed));
	txtPlayCount.setText(String.valueOf(playCount));	
	chFavorite.setSelected(mg.isFavorite(gameId));
	chCompleted.setSelected(mg.isCompleted(gameId));
	chBroken.setSelected(mg.isBroken(gameId));
	chGhost.setSelected(mg.isGhost(gameId));
	chPortable.setSelected(mg.isPortable(gameId));
	chHide.setSelected(mg.isHidden(gameId));
	lblAddedDate.setText(mg.getAddedDate(gameId));
    }
    
    public void SaveData() {
	
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == cbTitle) {
	    LoadData(cbTitle.getSelectedItem().toString());
	}	
    }
}
