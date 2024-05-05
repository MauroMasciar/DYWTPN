package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import com.raven.datechooser.DateChooser;

import database.ModelGames;
import backend.Utils;
import backend.Validations;

public class EditGame extends JInternalFrame implements ActionListener, ChangeListener {
    private static final long serialVersionUID = 4203707721315187637L;
    private final JPanel pnlDetails = new JPanel();
    private final JLabel lblTitle = new JLabel("Titulo:");
    private final JLabel lblReleaseDate = new JLabel("Fecha de lanzamiento:");
    private final JLabel lblRating = new JLabel(" Rating:");
    private final JLabel lblGenre = new JLabel("Género:");
    private final JLabel lblPlatform = new JLabel(" Plataforma:");
    private final JLabel lblDeveloper = new JLabel("Desarrollador:");
    private final JLabel lblPublisher = new JLabel(" Publicador:");
    private final JLabel lblSeries = new JLabel("Serie:");
    private final JLabel lblRegion = new JLabel(" Región:");
    private final JLabel lblPlayMode = new JLabel("Modo de juego:");
    private final JLabel lblVersion = new JLabel(" Versión:");
    private final JLabel lblStatus = new JLabel("Estado:");
    private final JLabel lblLibrary = new JLabel("Biblioteca:");
    private final JLabel lblLastPlayed = new JLabel("Ultima sesion:");
    private final JLabel lblAdded = new JLabel(" Añadido:");
    private final JLabel lblModified = new JLabel(" Modificado:");
    private final JLabel lblGameTime = new JLabel(" Tiempo jugado:");
    private final JLabel lblCompletedDate = new JLabel(" Fecha de completado:");
    private final JLabel lblPlayCount = new JLabel(" Veces jugado:");
    private final JLabel lblConvertedSeconds = new JLabel();
    private final JLabel lblPath = new JLabel("Directorio:");
    private final JLabel lblScore = new JLabel(" Puntaje:");
    private final JLabel lblCategory = new JLabel("categoría:");
    private final JTextField txtReleaseDate = new JTextField(20);
    private final JTextField txtGenre = new JTextField(10);
    private final JTextField txtDeveloper = new JTextField(10);
    private final JTextField txtPublisher = new JTextField(10);
    private final JTextField txtSeries = new JTextField(10);
    private final JTextField txtRegion = new JTextField(10);
    private final JTextField txtPlayMode = new JTextField(10);
    private final JTextField txtVersion = new JTextField(10);
    private final JTextField txtStatus = new JTextField(10);
    private final JTextField txtLastPlayed = new JTextField(10);
    private final JTextField txtAdded = new JTextField(10);
    private final JTextField txtModified = new JTextField(10);
    private final JTextField txtPath = new JTextField(10);
    private final JTextField txtCompletedDate = new JTextField(10);
    private final JCheckBox chFavorite = new JCheckBox("Favorito");
    private final JCheckBox chCompleted = new JCheckBox("Completado");
    private final JCheckBox chStatistic = new JCheckBox("Estadísticas");
    private final JCheckBox chGhost = new JCheckBox("Fantasma");
    private final JCheckBox chPortable = new JCheckBox("Portable");
    private final JCheckBox chHide = new JCheckBox("Oculto");
    private final JComboBox<String> cbRating = new JComboBox<>();
    private final JComboBox<String> cbPlatform = new JComboBox<>();
    private final JComboBox<String> cbCategory = new JComboBox<>();
    private final JComboBox<String> cbLibrary = new JComboBox<>();
    private final JComboBox<String> cbTitle = new JComboBox<>();
    private final SpinnerNumberModel spinnerNumberModelScore = new SpinnerNumberModel();
    private final SpinnerNumberModel spinnerNumberModelGameTime = new SpinnerNumberModel();
    private final SpinnerNumberModel spinnerNumberModelPlayCount = new SpinnerNumberModel();
    private final JSpinner spinScore = new JSpinner();
    private final JSpinner spinGameTime = new JSpinner();
    private final JSpinner spinPlayCount = new JSpinner();
    private final JPanel pnlNotes = new JPanel();
    private final JTextArea txtaNotes = new JTextArea();
    private final JScrollPane scrNotes = new JScrollPane(txtaNotes);
    private final JButton btnSave = new JButton("Guardar");
    private final JButton btnRename = new JButton("...");
    private final JButton btnDrop = new JButton("X");
    private final DateChooser dcCompletedDate = new DateChooser();
    private final DateChooser dcReleaseDate = new DateChooser();
    private int gameId;

    public EditGame(int gameId) {
        ModelGames mg = new ModelGames();
        if(mg.getTotalGames() == 0) {
            JOptionPane.showMessageDialog(this, "No tienes juegos en tu biblioteca", "No hay juegos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(gameId == 0) gameId = 1;
        setTitle("Editar juegos");
        setBounds(50, 50, 870, 550);
        setClosable(true);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlDetails.add(lblTitle, gbc);
        gbc.gridwidth = 5;
        gbc.gridx++;
        pnlDetails.add(cbTitle, gbc);
        gbc.gridx += 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        pnlDetails.add(btnRename, gbc);
        gbc.gridx++;
        pnlDetails.add(btnDrop, gbc);
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
        pnlDetails.add(cbRating, gbc);
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
        pnlDetails.add(cbPlatform, gbc);
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
        pnlDetails.add(lblLibrary, gbc);
        gbc.gridx++;
        gbc.gridwidth = 3;
        pnlDetails.add(cbLibrary, gbc);
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
        pnlDetails.add(chStatistic, gbc);
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
        pnlDetails.add(lblConvertedSeconds, gbc);	
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
        gbc.gridx++;
        pnlDetails.add(lblScore, gbc);
        gbc.gridx++;
        pnlDetails.add(spinScore, gbc);
        // Panel notes
        pnlNotes.setLayout(new GridBagLayout());
        pnlNotes.setBorder(BorderFactory.createTitledBorder("Notas"));
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 1;
        gbc.ipady = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlNotes.add(scrNotes, gbc);
        setLayout(new GridBagLayout());
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 1;
        gbc.ipady = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(pnlDetails, gbc);
        gbc.gridy++;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        add(pnlNotes, gbc);
        gbc.gridy += 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        add(btnSave, gbc);

        cbTitle.addActionListener(this);
        btnSave.addActionListener(this);
        btnRename.addActionListener(this);
        btnDrop.addActionListener(this);
        txtCompletedDate.addActionListener(this);
        txtReleaseDate.addActionListener(this);
        chCompleted.addActionListener(this);
        spinGameTime.addChangeListener(this);
        txtAdded.setEditable(false);
        txtModified.setEditable(false);
        txtaNotes.setLineWrap(true);
        txtaNotes.setWrapStyleWord(true);
        
        txtGenre.setEditable(false);
        
        loadCategory();
        loadLibrary();
        loadPlatform();
        loadRating();
        loadGameList();

        chGhost.setToolTipText("Especifica si quieres iniciar el juego manualmente en vez de que lo inicie la aplicación");
        txtPath.setToolTipText("Especifique la ruta completa al ejecutable");

        spinnerNumberModelScore.setMinimum(0);
        spinnerNumberModelScore.setMaximum(100);
        spinnerNumberModelGameTime.setMinimum(0);
        spinnerNumberModelPlayCount.setMinimum(0);
        spinScore.setModel(spinnerNumberModelScore);
        spinGameTime.setModel(spinnerNumberModelGameTime);
        spinPlayCount.setModel(spinnerNumberModelPlayCount);

        dcCompletedDate.setDateFormat("yyyy-MM-dd");
        dcCompletedDate.setTextRefernce(txtCompletedDate);
        dcCompletedDate.hidePopup();
        dcCompletedDate.setSelectedDate(new Date(System.currentTimeMillis()));
        dcReleaseDate.setDateFormat("yyyy-MM-dd");
        dcReleaseDate.setTextRefernce(txtReleaseDate);
        dcReleaseDate.hidePopup();
        

        txtLastPlayed.setEditable(false);

        if(gameId != 0) {
            cbTitle.setSelectedItem(mg.getNameFromId(gameId));
        }

        if(Validations.isEmpty(txtReleaseDate)) txtReleaseDate.setText("1900-01-01");
        if(Validations.isEmpty(txtLastPlayed)) txtLastPlayed.setText("1900-01-01 00:00:00");
        if(Validations.isEmpty(txtDeveloper)) txtDeveloper.setText("-");
        if(Validations.isEmpty(txtSeries)) txtSeries.setText("-");
        if(Validations.isEmpty(txtPlayMode)) txtPlayMode.setText("-");
        if(Validations.isEmpty(txtStatus)) txtStatus.setText("-");
        if(Validations.isEmpty(txtPath)) txtPath.setText("-");
        if(Validations.isEmpty(txtPublisher)) txtPublisher.setText("-");
        if(Validations.isEmpty(txtRegion)) txtRegion.setText("-");
        if(Validations.isEmpty(txtVersion)) txtVersion.setText("-");
        if(Validations.isEmpty(txtCompletedDate)) txtCompletedDate.setText("0000-00-00");

        setVisible(true);
    }

    private void loadCategory() {
        cbCategory.removeAllItems();
        ArrayList<String> listCategory = new ArrayList<>();
        listCategory.clear();
        ModelGames mg = new ModelGames();
        listCategory = mg.getCategoryList();
        for(int i = 0; i < listCategory.size(); i++) {
            cbCategory.addItem(listCategory.get(i));
        }
    }

    private void loadLibrary() {
        cbLibrary.removeAllItems();
        ArrayList<String> listLibrary = new ArrayList<>();
        listLibrary.clear();
        ModelGames mg = new ModelGames();
        listLibrary = mg.getLibraryList();
        for(int i = 0; i < listLibrary.size(); i++) {
            cbLibrary.addItem(listLibrary.get(i));
        }
    }
    
    private void loadPlatform() {
        cbPlatform.removeAllItems();
        ArrayList<String> listPlatform = new ArrayList<>();
        listPlatform.clear();
        ModelGames mg = new ModelGames();
        listPlatform = mg.getPlatformList();
        for(int i = 0; i < listPlatform.size(); i++) {
            cbPlatform.addItem(listPlatform.get(i));
        }
    }
    
    private void loadRating() {
        cbRating.removeAllItems();
        ArrayList<String> listRating = new ArrayList<>();
        listRating.clear();
        ModelGames mg = new ModelGames();
        listRating = mg.getRatingList();
        for(int i = 0; i < listRating.size(); i++) {
            cbRating.addItem(listRating.get(i));
        }
    }

    private void loadGameList() {
        cbTitle.removeAllItems();
        ArrayList<String> listGames = new ArrayList<>();
        listGames.clear();
        ModelGames mg = new ModelGames();
        listGames = mg.getGamesNameList(true, false);
        for(int i = 1; i < listGames.size(); i++) {
            cbTitle.addItem(listGames.get(i));
        }
    }

    private void loadData(String gameName) {
        ModelGames mg = new ModelGames();
        gameId = mg.getIdFromGameName(gameName);
        int secondsPlayed = mg.getSecondsPlayed(gameId);
        int playCount = mg.getPlayCount(gameId);
        int score = mg.getScore(gameId);

        txtReleaseDate.setText(mg.getReleaseDate(gameId));
        txtGenre.setText(mg.getGenre(gameId));
        cbPlatform.setSelectedItem(mg.getGamePlatformName(gameId));
        cbRating.setSelectedItem(mg.getGameRatingName(gameId));
        txtDeveloper.setText(mg.getDeveloper(gameId));
        txtPublisher.setText(mg.getPublisher(gameId));
        txtSeries.setText(mg.getSeries(gameId));
        txtRegion.setText(mg.getRegion(gameId));
        txtPlayMode.setText(mg.getPlayMode(gameId));
        txtVersion.setText(mg.getVersion(gameId));
        txtStatus.setText(mg.getStatus(gameId));
        cbLibrary.setSelectedItem(mg.getLibraryName((gameId)));
        txtLastPlayed.setText(mg.getLastPlayed(gameId));
        txtPath.setText(mg.getPathFromGame(gameId));	
        chFavorite.setSelected(mg.isFavorite(gameId));
        chCompleted.setSelected(mg.isCompleted(gameId));
        chStatistic.setSelected(mg.isStatistic(gameId));
        chGhost.setSelected(mg.isGhost(gameId));
        chPortable.setSelected(mg.isPortable(gameId));
        chHide.setSelected(mg.isHidden(gameId));
        txtAdded.setText(mg.getAddedDate(gameId));
        txtaNotes.setText(mg.getNotes(gameId));
        spinScore.setValue(score);
        spinGameTime.setValue(secondsPlayed);
        spinPlayCount.setValue(playCount);
        txtModified.setText(mg.getModified(gameId));
        cbCategory.setSelectedItem(mg.getGameCategoryName(gameId));
        txtCompletedDate.setText(mg.getCompletedDate(gameId));
        lblConvertedSeconds.setText(" (" + Utils.getTotalHoursFromSeconds(secondsPlayed, true) + ")");
        if(chCompleted.isSelected()) {
            txtCompletedDate.setEditable(true);
        } else {
            txtCompletedDate.setEditable(false);
        }
    }

    private void saveData(int gameId) {
        if(Validations.isEmpty(txtReleaseDate) || Validations.isEmpty(txtGenre) ||
                Validations.isEmpty(txtDeveloper) || Validations.isEmpty(txtPublisher) || Validations.isEmpty(txtSeries) || Validations.isEmpty(txtRegion) ||
                Validations.isEmpty(txtPlayMode) || Validations.isEmpty(txtVersion) || Validations.isEmpty(txtStatus) ||
                Validations.isEmpty(txtLastPlayed) || Validations.isEmpty(txtCompletedDate)) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Campos incompletos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(Validations.isEmpty(txtPath)) txtPath.setText("-");
        if(Validations.isEmpty(txtaNotes)) txtaNotes.setText(" ");

        String completed = "0", ghost = "0";
        int hide = 0, favorite = 0, statistic = 0, portable = 0;
        ModelGames mg = new ModelGames();

        if(chFavorite.isSelected()) favorite = 1;
        if(chCompleted.isSelected()) completed = "1";
        if(chStatistic.isSelected()) statistic = 1;
        if(chGhost.isSelected()) ghost = "1";
        if(chPortable.isSelected()) portable = 1;
        if(chHide.isSelected()) hide = 1;

        String releasedate = txtReleaseDate.getText();
        int rating = mg.getRatingIdFromName(cbRating.getSelectedItem().toString());
        int platform = mg.getPlatformIdFromName(cbPlatform.getSelectedItem().toString());
        String developer = txtDeveloper.getText();
        String publisher = txtPublisher.getText();
        String series = txtSeries.getText();
        String region = txtRegion.getText();
        String playMode = txtPlayMode.getText();
        String version = txtVersion.getText();
        String status = txtStatus.getText();
        int library = mg.getLibraryIdFromName(cbLibrary.getSelectedItem().toString());
        String lastPlayed = txtLastPlayed.getText();
        String path = txtPath.getText();
        String completedDate = txtCompletedDate.getText();
        String notes = txtaNotes.getText();
        int score = (Integer) spinScore.getValue();
        int gameTime = (Integer) spinGameTime.getValue();
        int playCount = (Integer) spinPlayCount.getValue();
        int category = mg.getCategoryIdFromName(cbCategory.getSelectedItem().toString());

        int res = mg.editGame(gameId, cbTitle.getSelectedItem().toString(), gameTime, path, ghost, playCount, completed, score, category, hide, 
                favorite, statistic, portable, releasedate, rating, platform, developer, publisher, series, region, 
                playMode, version, status, lastPlayed, completedDate, library, notes);
        if(res == 1) {
            JOptionPane.showMessageDialog(this, "El juego ha sido editado satisfactoriamente", "Juego editado", JOptionPane.INFORMATION_MESSAGE);
            MainUI.loadData(false);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ha habido un error al editar el juego", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cbTitle) {
        	try {
        		loadData(cbTitle.getSelectedItem().toString());
        	} catch(NullPointerException ex) {
        		ex.getMessage();
        	}            
        } else if(e.getSource() == txtCompletedDate) {
            dcCompletedDate.showPopup();
        } else if(e.getSource() == txtReleaseDate) {
            dcReleaseDate.showPopup();
        } else if(e.getSource() == btnSave) {
            ModelGames mg = new ModelGames();
            saveData(mg.getIdFromGameName(cbTitle.getSelectedItem().toString()));
        } else if(e.getSource() == chCompleted) {
            if(chCompleted.isSelected()) {
                txtCompletedDate.setEditable(true);
            } else {
                txtCompletedDate.setEditable(false);
            }
        } else if(e.getSource() == btnRename) {
        	String newName = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre del juego", cbTitle.getSelectedItem().toString(), JOptionPane.QUESTION_MESSAGE);
        	if(newName == null || newName.length() == 0) return;
        	ModelGames mg = new ModelGames();
        	int res = mg.changeGameName(mg.getIdFromGameName(cbTitle.getSelectedItem().toString()), newName);
        	if(res == 1) {
        		JOptionPane.showMessageDialog(this, "El nombre del juego ha sido cambiado", "Cambiar nombre", JOptionPane.INFORMATION_MESSAGE);
        		MainUI.loadGames();
        		loadGameList();
        		loadData(newName);
        		cbTitle.setSelectedItem(newName);
        	} else if(res == 2) {
        		JOptionPane.showMessageDialog(this, "Ha habido un error al cambiar el nombre del juego. Vuelva a intentarlo o reinicie la aplicación", "Cambiar nombre", JOptionPane.ERROR_MESSAGE);
        		return;
        	} else if(res == 3) {
        		JOptionPane.showMessageDialog(this, "Ya existe un juego con ese nombre", "Cambiar nombre", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        } else if(e.getSource() == btnDrop) {
        	int opcDropGame, opcDropHistory;
        	opcDropGame = JOptionPane.showInternalConfirmDialog(null, "¿Seguro que desea borrar este juego?", "Borrar juego", JOptionPane.YES_NO_OPTION);
        	if(opcDropGame == 0) {
        		ModelGames mg = new ModelGames();
        		opcDropHistory = JOptionPane.showInternalConfirmDialog(null, "¿Desea borrar tambien el historial y actividad?", "Borrar juego", JOptionPane.YES_NO_OPTION);
        		if(opcDropHistory == 0) mg.deleteGame(mg.getIdFromGameName(cbTitle.getSelectedItem().toString()), true);
        		else mg.deleteGame(mg.getIdFromGameName(cbTitle.getSelectedItem().toString()), false);
        		MainUI.loadData(false);
                dispose();
        	}
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == spinGameTime) {
            lblConvertedSeconds.setText(" (" + Utils.getTotalHoursFromSeconds((Integer)spinGameTime.getValue(), true) + ")");
        }
    }
}
