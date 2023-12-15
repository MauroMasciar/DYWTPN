package frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.raven.datechooser.DateChooser;

import backend.Utils;
import backend.Validations;
import database.ModelGames;

public class AddGame extends JInternalFrame implements ActionListener, ChangeListener {
    private static final long serialVersionUID = 2765307118548151447L;
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
    private final JLabel lblLibrary = new JLabel("Biblioteca:");
    private final JLabel lblLastPlayed = new JLabel("Ultima sesion:");
    private final JLabel lblAdded = new JLabel(" Añadido:");
    private final JLabel lblModified = new JLabel(" Modificado:");
    private final JLabel lblGameTime = new JLabel(" Tiempo jugado:");
    private final JLabel lblCompletedDate = new JLabel(" Fecha de completado:");
    private final JLabel lblPlayCount = new JLabel(" Veces jugado:");
    private final JLabel lblConvertedSeconds = new JLabel(" (00h 00m 00s)");
    private final JLabel lblPath = new JLabel("Directorio:");
    private final JLabel lblScore = new JLabel(" Puntaje:");
    private final JLabel lblCategory = new JLabel("Categoria:");
    private final JTextField txtGameName = new JTextField(20);
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
    private final JCheckBox chStatistic = new JCheckBox("Estadisticas");
    private final JCheckBox chGhost = new JCheckBox("Fantasma");
    private final JCheckBox chPortable = new JCheckBox("Portable");
    private final JCheckBox chHide = new JCheckBox("Oculto");
    private final JComboBox<String> cbCategory = new JComboBox<>();
    private final JComboBox<String> cbLibrary = new JComboBox<>();
    private final SpinnerNumberModel spinnerNumberModelScore = new SpinnerNumberModel();
    private final SpinnerNumberModel spinnerNumberModelGameTime = new SpinnerNumberModel();
    private final SpinnerNumberModel spinnerNumberModelPlayCount = new SpinnerNumberModel();
    private final JSpinner spinPlayCount = new JSpinner();
    private final JSpinner spinScore = new JSpinner();
    private final JSpinner spinGameTime = new JSpinner();
    private final JPanel pnlNotes = new JPanel();
    private final JTextArea txtaNotes = new JTextArea();
    private final JScrollPane scrNotes = new JScrollPane(txtaNotes);
    private final DateChooser dcCompletedDate = new DateChooser();
    private final DateChooser dcReleaseDate = new DateChooser();
    private final JButton btnSave = new JButton("Guardar");

    public AddGame() {
	try {
	    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/new_game.png"));
	    this.setFrameIcon(icon);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
	}
	setTitle("Añadir nuevo juego");
	setSize(850, 550);
	setClosable(true);
	setResizable(false);
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setLayout(new GridBagLayout());

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
	pnlDetails.add(txtGameName, gbc);
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

	txtCompletedDate.addActionListener(this);
	txtReleaseDate.addActionListener(this);
	btnSave.addActionListener(this);
	spinGameTime.addChangeListener(this);
	txtAdded.setEditable(false);
	txtModified.setEditable(false);
	txtaNotes.setLineWrap(true);
	loadCategory();
	loadLibrary();

	chGhost.setToolTipText("Especifica si quieres iniciar el juego manualmente en vez de que lo inicie la aplicacion");
	txtPath.setToolTipText("Especifique la ruta completa al ejecutable");

	spinnerNumberModelScore.setMinimum(0);
	spinnerNumberModelScore.setMaximum(10);
	spinnerNumberModelGameTime.setMinimum(0);
	spinnerNumberModelPlayCount.setMinimum(0);
	spinScore.setModel(spinnerNumberModelScore);
	spinGameTime.setModel(spinnerNumberModelGameTime);
	spinPlayCount.setModel(spinnerNumberModelPlayCount);
	txtAdded.setText(Utils.getFormattedDate());
	txtModified.setText(Utils.getFormattedDateTime());
	
	dcCompletedDate.setDateFormat("yyyy-MM-dd");
	dcCompletedDate.setTextRefernce(txtCompletedDate);
	dcCompletedDate.hidePopup();
	dcReleaseDate.setDateFormat("yyyy-MM-dd");
	dcReleaseDate.setTextRefernce(txtReleaseDate);
	dcReleaseDate.hidePopup();
	dcCompletedDate.setSelectedDate(new Date(1));
	dcReleaseDate.setSelectedDate(new Date(1));
	
	txtLastPlayed.setEditable(false);
	
	chStatistic.setSelected(true);

	if(Validations.isEmpty(txtReleaseDate)) txtReleaseDate.setText("1900-01-01");
	if(Validations.isEmpty(txtLastPlayed)) txtLastPlayed.setText("1900-01-01 00:00:00");

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

    private void SaveData() {
	if(Validations.isEmpty(txtReleaseDate)) txtReleaseDate.setText("1900-01-01");
	if(Validations.isEmpty(txtRating)) txtRating.setText("N/A");
	if(Validations.isEmpty(txtGenre)) txtGenre.setText("N/A");
	if(Validations.isEmpty(txtPlatform)) txtPlatform.setText("N/A");
	if(Validations.isEmpty(txtDeveloper)) txtDeveloper.setText("N/A");
	if(Validations.isEmpty(txtPublisher)) txtPublisher.setText("N/A");
	if(Validations.isEmpty(txtSeries)) txtSeries.setText("N/A");
	if(Validations.isEmpty(txtStatus)) txtStatus.setText("N/A");
	if(Validations.isEmpty(txtSource)) txtSource.setText("N/A");
	if(Validations.isEmpty(txtPlayMode)) txtPlayMode.setText("N/A");
	if(Validations.isEmpty(txtVersion)) txtVersion.setText("N/A");
	if(Validations.isEmpty(txtRegion)) txtRegion.setText("N/A");
	if(Validations.isEmpty(txtPath)) txtPath.setText("N/A");
	if(Validations.isEmpty(txtCompletedDate)) txtCompletedDate.setText("1900-01-01");
	if(Validations.isEmpty(txtLastPlayed)) txtLastPlayed.setText("1900-01-01");
	if(Validations.isEmpty(txtaNotes)) txtaNotes.setText("N/A");
	
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
	int library = mg.getLibraryIdFromName(cbLibrary.getSelectedItem().toString());
	String lastPlayed = txtLastPlayed.getText();
	String path = txtPath.getText();
	String name = txtGameName.getText();
	String added = Utils.getFormattedDate();
	String modified = Utils.getFormattedDateTime();
	String completed_date = txtCompletedDate.getText();
	String notes = txtaNotes.getText();
	int score = (Integer) spinScore.getValue();
	int gameTime = (Integer) spinGameTime.getValue();
	int playCount = (Integer) spinPlayCount.getValue();
	int category = mg.getCategoryIdFromName(cbCategory.getSelectedItem().toString());

	int res = mg.addGame(name, gameTime, path, ghost, playCount, completed, score, category, hide, 
		favorite, statistic, portable, releasedate, rating, genre, platform, developer, publisher, series, region, 
		playMode, version, status, lastPlayed, added, modified, completed_date, library, notes);
	if(res == 1) {
	    JOptionPane.showMessageDialog(this, "El juego ha sido guardado satisfactoriamente", "Juego editado", JOptionPane.INFORMATION_MESSAGE);
	    MainUI.loadData();
	    dispose();
	} else {
	    JOptionPane.showMessageDialog(this, "Ha habido un error al guardado el juego", "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnSave) {
	    SaveData();
	} else if(e.getSource() == txtCompletedDate) {
	    dcCompletedDate.showPopup();
	} else if(e.getSource() == txtReleaseDate) {
	    dcReleaseDate.showPopup();
	} 
    }

    @Override
    public void stateChanged(ChangeEvent e) {
	if(e.getSource() == spinGameTime) {
	    lblConvertedSeconds.setText(" (" + Utils.getTotalHoursFromSeconds((Integer)spinGameTime.getValue(), true) + ")");
	}
    }
}