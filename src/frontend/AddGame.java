package frontend;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import backend.Utils;
import backend.Validations;
import database.ModelGames;

public class AddGame extends JInternalFrame implements ActionListener {
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
    private final JLabel lblSource = new JLabel("Fuente:");
    private final JLabel lblLastPlayed = new JLabel("Ultima sesion:");
    private final JLabel lblAdded = new JLabel(" Añadido:");
    private final JLabel lblModified = new JLabel(" Modificado:");
    private final JLabel lblGameTime = new JLabel(" Tiempo jugado:");
    private final JLabel lblPlayCount = new JLabel(" Veces jugado:");
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
    private final JSpinner spinPlayCount = new JSpinner();
    private final JSpinner spinScore = new JSpinner();
    private final JSpinner spinGameTime = new JSpinner();
    private final JButton btnSave = new JButton("Guardar");

    public AddGame() {
	try {
	    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/new_game.png"));
	    this.setFrameIcon(icon);
	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
	}
	setTitle("Añadir nuevo juego");
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
	pnlDetails.add(lblPlayCount, gbc);
	gbc.gridx++;
	pnlDetails.add(spinPlayCount, gbc);
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
	gbc.gridy++;
	gbc.gridx = 0;
	pnlDetails.add(lblCategory, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	pnlDetails.add(cbCategory, gbc);
	gbc.gridx += 3;
	gbc.gridwidth = 1;
	pnlDetails.add(lblScore, gbc);
	gbc.gridx++;
	pnlDetails.add(spinScore, gbc);	

	add(pnlDetails);
	add(btnSave);

	btnSave.addActionListener(this);
	txtAdded.setEditable(false);
	txtModified.setEditable(false);
	LoadCategory();

	chGhost.setToolTipText("Especifica si quieres iniciar el juego manualmente en vez de que lo inicie la aplicacion");

	spinnerNumberModelScore.setMinimum(0);
	spinnerNumberModelScore.setMaximum(10);
	spinnerNumberModelGameTime.setMinimum(0);
	spinnerNumberModelPlayCount.setMinimum(0);
	spinScore.setModel(spinnerNumberModelScore);
	spinGameTime.setModel(spinnerNumberModelGameTime);
	spinPlayCount.setModel(spinnerNumberModelPlayCount);
	txtAdded.setText(Utils.getDate());
	txtModified.setText(Utils.getDateTime());

	if(Validations.isEmpty(txtReleaseDate)) txtReleaseDate.setText("1900-01-01");
	if(Validations.isEmpty(txtLastPlayed)) txtLastPlayed.setText("1900-01-01 00:00:00");

	setVisible(true);
    }
    
    public void LoadCategory() {
	cbCategory.removeAllItems();
	ArrayList<String> listCategory = new ArrayList<String>();
	listCategory.clear();
	ModelGames mg = new ModelGames();
	listCategory = mg.getCategoryList();
	for(int i = 1; i < listCategory.size(); i++) {
	    cbCategory.addItem(listCategory.get(i));
	}
    }
    
    private void SaveData() {
	if(Validations.isEmpty(txtReleaseDate) || Validations.isEmpty(txtRating) || Validations.isEmpty(txtGenre) || Validations.isEmpty(txtPlatform) ||
		Validations.isEmpty(txtDeveloper) || Validations.isEmpty(txtPublisher) || Validations.isEmpty(txtSeries) || Validations.isEmpty(txtRegion) ||
		Validations.isEmpty(txtPlayMode) || Validations.isEmpty(txtVersion) || Validations.isEmpty(txtStatus) || Validations.isEmpty(txtSource) ||
		Validations.isEmpty(txtLastPlayed) || Validations.isEmpty(txtGameName)) {
	    JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Campos incompletos", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	if(Validations.isEmpty(txtPath)) txtPath.setText("N/A");

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
	String name = txtGameName.getText();
	String added = Utils.getDate();
	String modified = Utils.getDateTime();
	int score = (Integer) spinScore.getValue();
	int gameTime = (Integer) spinGameTime.getValue();
	int playCount = (Integer) spinPlayCount.getValue();
	int category = mg.getCategoryIdFromName(cbCategory.getSelectedItem().toString());

	int res = mg.addGame(name, gameTime, path, ghost, playCount, completed, score, category, hide, 
		favorite, broken, portable, releasedate, rating, genre, platform, developer, publisher, series, region, 
		playMode, version, status, source, lastPlayed, added, modified);
	if(res == 1) {
	    JOptionPane.showMessageDialog(this, "El juego ha sido guardado satisfactoriamente", "Juego editado", JOptionPane.INFORMATION_MESSAGE);
	    MainUI.UpdateGameList();
	    dispose();
	} else {
	    JOptionPane.showMessageDialog(this, "Ha habido un error al guardado el juego", "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnSave) {
	    SaveData();
	}
    }
}