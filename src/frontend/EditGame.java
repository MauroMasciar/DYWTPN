package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.ModelGames;
import backend.Validations;

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
    private final JLabel lblModified = new JLabel(" Modificado:");
    private final JLabel lblGameTime = new JLabel(" Tiempo jugado:");
    private final JLabel lblCompletedDate = new JLabel(" Fecha de completado:");
    private final JLabel lblPlayCount = new JLabel(" Veces jugado:");
    private final JLabel lblPath = new JLabel("Directorio:");
    private final JLabel lblScore = new JLabel(" Puntaje:");
    private final JLabel lblCategory = new JLabel("Categoria:");
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
    private final JTextField txtAdded = new JTextField(10);
    private final JTextField txtModified = new JTextField(10);
    private final JTextField txtPath = new JTextField(10);
    private final JTextField txtCompletedDate = new JTextField(10);
    private final JCheckBox chFavorite = new JCheckBox("Favorito");
    private final JCheckBox chCompleted = new JCheckBox("Completado");
    private final JCheckBox chBroken = new JCheckBox("Roto");
    private final JCheckBox chGhost = new JCheckBox("Fantasma");
    private final JCheckBox chPortable = new JCheckBox("Portable");
    private final JCheckBox chHide = new JCheckBox("Oculto");
    private final JComboBox<String> cbCategory = new JComboBox<String>();
    private final SpinnerNumberModel spinnerNumberModelScore = new SpinnerNumberModel();
    private final SpinnerNumberModel spinnerNumberModelGameTime = new SpinnerNumberModel();
    private final SpinnerNumberModel spinnerNumberModelPlayCount = new SpinnerNumberModel();
    private final JSpinner spinScore = new JSpinner();
    private final JSpinner spinGameTime = new JSpinner();
    private final JSpinner spinPlayCount = new JSpinner();
    private final JButton btnSave = new JButton("Guardar");
    private int gameId;

    public EditGame(int gameId) {
	setTitle("Editar juegos");
	setSize(850, 400);
	setClosable(true);
	setResizable(true);
	setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	setLayout(new FlowLayout());
	this.gameId = gameId;

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
	pnlDetails.add(txtAdded, gbc);
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
	pnlDetails.add(lblModified, gbc);
	gbc.gridx++;
	pnlDetails.add(txtModified, gbc);
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
	pnlDetails.add(lblCompletedDate, gbc);
	gbc.gridx++;
	pnlDetails.add(txtCompletedDate, gbc);
	gbc.gridx++;
	pnlDetails.add(chBroken, gbc);
	gbc.gridx++;
	pnlDetails.add(chGhost, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnlDetails.add(lblPath, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(txtPath, gbc);
	gbc.gridwidth = 1;
	gbc.gridx += 3;
	pnlDetails.add(lblGameTime, gbc);
	gbc.gridx++;
	pnlDetails.add(spinGameTime, gbc);
	gbc.gridx++;
	pnlDetails.add(lblScore, gbc);
	gbc.gridx++;
	pnlDetails.add(spinScore, gbc);
	gbc.gridy++;
	gbc.gridx = 0;
	pnlDetails.add(lblCategory, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(cbCategory, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblPlayCount, gbc);
	gbc.gridx++;
	pnlDetails.add(spinPlayCount, gbc);

	add(pnlDetails);
	add(btnSave);

	cbTitle.addActionListener(this);
	btnSave.addActionListener(this);
	chCompleted.addActionListener(this);
	txtAdded.setEditable(false);
	txtModified.setEditable(false);
	LoadCategory();
	LoadGameList();

	chGhost.setToolTipText("Especifica si quieres iniciar el juego manualmente en vez de que lo inicie la aplicacion");

	spinnerNumberModelScore.setMinimum(0);
	spinnerNumberModelScore.setMaximum(10);
	spinnerNumberModelGameTime.setMinimum(0);
	spinnerNumberModelPlayCount.setMinimum(0);
	spinScore.setModel(spinnerNumberModelScore);
	spinGameTime.setModel(spinnerNumberModelGameTime);
	spinPlayCount.setModel(spinnerNumberModelPlayCount);

	if(gameId != 0) {
	    ModelGames mg = new ModelGames();
	    cbTitle.setSelectedItem(mg.getNameFromId(gameId));
	}

	if(Validations.isEmpty(txtReleaseDate)) txtReleaseDate.setText("1900-01-01");
	if(Validations.isEmpty(txtLastPlayed)) txtLastPlayed.setText("1900-01-01 00:00:00");
	if(Validations.isEmpty(txtGenre)) txtGenre.setText("-");
	if(Validations.isEmpty(txtDeveloper)) txtDeveloper.setText("-");
	if(Validations.isEmpty(txtSeries)) txtSeries.setText("-");
	if(Validations.isEmpty(txtPlayMode)) txtPlayMode.setText("-");
	if(Validations.isEmpty(txtStatus)) txtStatus.setText("-");
	if(Validations.isEmpty(txtSource)) txtSource.setText("-");
	if(Validations.isEmpty(txtPath)) txtPath.setText("-");
	if(Validations.isEmpty(txtRating)) txtRating.setText("-");
	if(Validations.isEmpty(txtPlatform)) txtPlatform.setText("-");
	if(Validations.isEmpty(txtPublisher)) txtPublisher.setText("-");
	if(Validations.isEmpty(txtRegion)) txtRegion.setText("-");
	if(Validations.isEmpty(txtVersion)) txtVersion.setText("-");
	if(Validations.isEmpty(txtCompletedDate)) txtCompletedDate.setText("0000-00-00");

	setVisible(true);
    }

    private void LoadCategory() {
	cbCategory.removeAllItems();
	ArrayList<String> listCategory = new ArrayList<String>();
	listCategory.clear();
	ModelGames mg = new ModelGames();
	listCategory = mg.getCategoryList();
	for(int i = 1; i < listCategory.size(); i++) {
	    cbCategory.addItem(listCategory.get(i));
	}
    }

    private void LoadGameList() {
	cbTitle.removeAllItems();
	ArrayList<String> listGames = new ArrayList<String>();
	listGames.clear();
	ModelGames mg = new ModelGames();
	listGames = mg.getGamesNameList(true);
	for(int i = 1; i < listGames.size(); i++) {
	    cbTitle.addItem(listGames.get(i));
	}
    }

    private void LoadData(String gameName) {
	ModelGames mg = new ModelGames();
	gameId = mg.getIdFromGameName(gameName);
	int secondsPlayed = mg.getSecondsPlayed(gameId);
	int playCount = mg.getPlayCount(gameId);
	int score = mg.getScore(gameId);

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
	txtPath.setText(mg.getPathFromGame(gameId));	
	chFavorite.setSelected(mg.isFavorite(gameId));
	chCompleted.setSelected(mg.isCompleted(gameId));
	chBroken.setSelected(mg.isBroken(gameId));
	chGhost.setSelected(mg.isGhost(gameId));
	chPortable.setSelected(mg.isPortable(gameId));
	chHide.setSelected(mg.isHidden(gameId));
	txtAdded.setText(mg.getAddedDate(gameId));
	spinScore.setValue(score);
	spinGameTime.setValue(secondsPlayed);
	spinPlayCount.setValue(playCount);
	txtModified.setText(mg.getModified(gameId));
	cbCategory.setSelectedItem(mg.getGameCategoryName(gameId));
	txtCompletedDate.setText(mg.getCompletedDate(gameId));
	if(chCompleted.isSelected()) {
	    txtCompletedDate.setEditable(true);
	} else {
	    txtCompletedDate.setEditable(false);
	}
    }

    private void SaveData(int gameId) {
	if(Validations.isEmpty(txtReleaseDate) || Validations.isEmpty(txtRating) || Validations.isEmpty(txtGenre) || Validations.isEmpty(txtPlatform) ||
		Validations.isEmpty(txtDeveloper) || Validations.isEmpty(txtPublisher) || Validations.isEmpty(txtSeries) || Validations.isEmpty(txtRegion) ||
		Validations.isEmpty(txtPlayMode) || Validations.isEmpty(txtVersion) || Validations.isEmpty(txtStatus) || Validations.isEmpty(txtSource) ||
		Validations.isEmpty(txtLastPlayed) || Validations.isEmpty(txtCompletedDate)) {
	    JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Campos incompletos", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	if(Validations.isEmpty(txtPath)) txtPath.setText("-");

	String completed = "0", ghost = "0";
	int hide = 0, favorite = 0, broken = 0, portable = 0;
	ModelGames mg = new ModelGames();

	if(chFavorite.isSelected()) favorite = 1;
	if(chCompleted.isSelected()) completed = "1";
	if(chBroken.isSelected()) broken = 1;
	if(chGhost.isSelected()) ghost = "1";
	if(chPortable.isSelected()) portable = 1;
	if(chHide.isSelected()) hide = 1;

	String releasedate = txtReleaseDate.getText();
	String rating = txtRating.getText();
	String genre = txtGenre.getText();
	String platform = txtPlatform.getText();
	String developer = txtDeveloper.getText();
	String publisher = txtPublisher.getText();
	String series = txtSeries.getText();
	String region = txtRegion.getText();
	String playMode = txtPlayMode.getText();
	String version = txtVersion.getText();
	String status = txtStatus.getText();
	String source = txtSource.getText();
	String lastPlayed = txtLastPlayed.getText();
	String path = txtPath.getText();
	String completedDate = txtCompletedDate.getText();
	int score = (Integer) spinScore.getValue();
	int gameTime = (Integer) spinGameTime.getValue();
	int playCount = (Integer) spinPlayCount.getValue();
	int category = mg.getCategoryIdFromName(cbCategory.getSelectedItem().toString());

	int res = mg.editGame(gameId, cbTitle.getSelectedItem().toString(), gameTime, path, ghost, playCount, completed, score, category, hide, 
		favorite, broken, portable, releasedate, rating, genre, platform, developer, publisher, series, region, 
		playMode, version, status, source, lastPlayed, completedDate);
	if(res == 1) {
	    JOptionPane.showMessageDialog(this, "El juego ha sido editado satisfactoriamente", "Juego editado", JOptionPane.INFORMATION_MESSAGE);
	    MainUI.UpdateGameList();
	    dispose();
	} else {
	    JOptionPane.showMessageDialog(this, "Ha habido un error al editar el juego", "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == cbTitle) {
	    LoadData(cbTitle.getSelectedItem().toString());
	} else if(e.getSource() == btnSave) {
	    ModelGames mg = new ModelGames();
	    SaveData(mg.getIdFromGameName(cbTitle.getSelectedItem().toString()));
	} else if(e.getSource() == chCompleted) {
	    if(chCompleted.isSelected()) {
		txtCompletedDate.setEditable(true);
	    } else {
		txtCompletedDate.setEditable(false);
	    }
	}
    }
}
