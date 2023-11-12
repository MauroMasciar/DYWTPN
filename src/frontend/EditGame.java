package frontend;

import database.ModelGames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class EditGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 2260581778638759215L;
    private DecimalFormat txtminsPlayedDecimal = new DecimalFormat("#.##");
    private JLabel lblGameName = new JLabel("Juego:");
    private JLabel lblminsPlayed = new JLabel("Horas jugadas:");
    private JLabel lblTimes = new JLabel("Veces jugado");
    private JLabel lblScore = new JLabel("Puntuacion");
    private JLabel lblPath = new JLabel("Ubicacion:");
    private JLabel lblCategory = new JLabel("Categoria:");
    private JLabel lblGhostGame = new JLabel("Juego fantasma");
    private JLabel lblCompletedGame = new JLabel("Juego terminado");
    private JTextField txtGameName = new JTextField(20);
    private JTextField txtTimes = new JTextField(20);
    private JTextField txtScore = new JTextField(20);
    private JTextField txtminsPlayed = new JTextField(20);
    private JTextField txtPath = new JTextField(20);
    private JComboBox<String> cbCategory = new JComboBox<String>();
    private JCheckBox cbGhostGame = new JCheckBox();
    private JCheckBox cbCompletedGame = new JCheckBox();
    private JButton btnEdit = new JButton("Guardar cambios");
    private JButton btnDel = new JButton("Borrar juego");
    private int gameId;

    public EditGame(int gameId) {
	ModelGames g = new ModelGames();
	txtGameName.setText(g.getNameFromId(gameId));
	setBounds(50, 50, 450, 240);
	String title = "Editar juego - " + txtGameName.getText();
	setTitle(title);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	this.gameId = gameId;
	double mins_played = g.getMinsPlayed(gameId);
	txtminsPlayed.setText(txtminsPlayedDecimal.format(mins_played/60));
	txtPath.setText(g.getPathFromGame(gameId));
	txtTimes.setText(String.valueOf(g.getTimes(gameId)));
	txtScore.setText(String.valueOf(g.getScore(gameId)));
	if(g.isGhost(gameId)) cbGhostGame.setSelected(true);
	else cbGhostGame.setSelected(false);
	if(g.isCompleted(gameId)) cbCompletedGame.setSelected(true);
	else cbCompletedGame.setSelected(false);
	ArrayList<String> category = g.getCategoryList();
	for(int i = 0; i<category.size(); i++) cbCategory.addItem(category.get(i));
	
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 1;
	gbc.ipady = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(lblGameName, gbc);
	gbc.gridx++;
	add(txtGameName, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblminsPlayed, gbc);
	gbc.gridx = 1;
	add(txtminsPlayed, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	add(lblTimes, gbc);
	gbc.gridx ++;
	add(txtTimes, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblScore, gbc);
	gbc.gridx ++;
	add(txtScore, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblPath, gbc);
	gbc.gridx = 1;
	add(txtPath, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	add(lblCategory, gbc);
	gbc.gridx ++;
	add(cbCategory, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblGhostGame, gbc);
	gbc.gridx++;
	add(cbGhostGame, gbc);
	gbc.gridy ++;
	gbc.gridx = 0;
	add(lblCompletedGame, gbc);
	gbc.gridx ++;
	add(cbCompletedGame, gbc);	
	gbc.gridy ++;
	gbc.gridx = 0;
	gbc.gridwidth = 1;
	add(btnDel, gbc);
	gbc.gridx++;
	add(btnEdit, gbc);

	btnEdit.addActionListener(this);
	btnDel.addActionListener(this);
	
	updateData();

	setVisible(true);
    }
    
    public void updateData() {
	ModelGames mg = new ModelGames();
	ArrayList<String> category = mg.getCategoryList();
	cbCategory.removeAllItems();
	for(int i = 0; i<category.size(); i++) cbCategory.addItem(category.get(i));
	
	cbCategory.setSelectedItem(mg.getGameCategory(gameId));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnEdit) {
	    ModelGames g = new ModelGames();
	    String ghostGame, minsPlayed, completed;
	    if(cbGhostGame.isSelected()) ghostGame = "1";
	    else ghostGame = "0";

	    if(cbCompletedGame.isSelected()) completed = "1";
	    else completed = "0";
	    int score;
	    try {
		score = Integer.parseInt(txtScore.getText());
		if(score > 10) score = 10;
		if(score < 0) score = 0;
	    } catch (NumberFormatException ex) {
		score = 0;
	    }
	    
	    minsPlayed = txtminsPlayed.getText().replaceAll(",", ".");
	    if(txtTimes.getText().isEmpty() || txtTimes.getText().isBlank()) txtTimes.setText("0");
	    if(txtGameName.getText().isEmpty() || txtGameName.getText().isBlank()) {
		JOptionPane.showMessageDialog(this,  "El juego debe tener un nombre", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    if(g.editGame(gameId, txtGameName.getText(), minsPlayed, txtPath.getText(), ghostGame, txtTimes.getText(), completed, score, cbCategory.getSelectedItem().toString()) == 1) {
		JOptionPane.showMessageDialog(this, "El juego ha sido editado satisfactoriamente", "Juego editado", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	    } else {
		JOptionPane.showMessageDialog(this,  "Ha habido un error al editar el juego", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    MainUI.UpdateGameList();
	} else if(e.getSource() == btnDel) {
	    int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar este juego?", "Borrar juego", JOptionPane.YES_NO_OPTION);
	    if(res == 0) {
		ModelGames g = new ModelGames();
		int confirm = g.deleteGame(gameId);
		if(confirm != 0) {
		    JOptionPane.showMessageDialog(this,  "El juego ha sido borrado", "Juego borrado", JOptionPane.ERROR_MESSAGE);
		    this.dispose();
		} else {
		    JOptionPane.showMessageDialog(this,  "Ha habido un error al borrar el juego", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	    MainUI.UpdateGameList();
	}
    }
}
