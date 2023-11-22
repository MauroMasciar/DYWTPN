package frontend;

import database.ModelGames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel lblGameName = new JLabel("Juego:");
    private JLabel lblSecondsPlayed = new JLabel("Tiempo jugado (En segundos):");
    private JLabel lblTimes = new JLabel("Veces jugado");
    private JLabel lblScore = new JLabel("Puntuacion");
    private JLabel lblPath = new JLabel("Ubicacion:");
    private JLabel lblCategory = new JLabel("Categoria:");
    private JLabel lblHidden = new JLabel("Juego oculto:");
    private JLabel lblGhostGame = new JLabel("Juego fantasma");
    private JLabel lblCompletedGame = new JLabel("Juego terminado");
    private JTextField txtGameName = new JTextField();
    private JTextField txtTimes = new JTextField();
    private JTextField txtScore = new JTextField();
    private JTextField txtSecondsPlayed = new JTextField();
    private JTextField txtPath = new JTextField();
    private JComboBox<String> cbCategory = new JComboBox<String>();
    private JCheckBox cbHiddenGame = new JCheckBox();
    private JCheckBox cbGhostGame = new JCheckBox();
    private JCheckBox cbCompletedGame = new JCheckBox();
    private JButton btnEdit = new JButton("Guardar cambios");
    private JButton btnDel = new JButton("Borrar juego");
	private int gameId;

    public EditGame(int gameId) {
	ModelGames mg = new ModelGames();
	txtGameName.setText(mg.getNameFromId(gameId));
	setBounds(50, 40, 450, 270);
	String title = "Editar juego - " + txtGameName.getText();
	setTitle(title);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	this.gameId = gameId;
	txtSecondsPlayed.setText(String.valueOf(mg.getSecondsPlayed(gameId)));
	txtPath.setText(mg.getPathFromGame(gameId));
	txtTimes.setText(String.valueOf(mg.getTimes(gameId)));
	txtScore.setText(String.valueOf(mg.getScore(gameId)));

	cbGhostGame.setSelected(mg.isGhost(gameId));
	cbCompletedGame.setSelected(mg.isCompleted(gameId));
	cbHiddenGame.setSelected(mg.IsHidden(gameId));

	ArrayList<String> category = mg.getCategoryList();
	for (int i = 0; i < category.size(); i++)
	    cbCategory.addItem(category.get(i));

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
	add(lblSecondsPlayed, gbc);
	gbc.gridx = 1;
	add(txtSecondsPlayed, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblTimes, gbc);
	gbc.gridx++;
	add(txtTimes, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblScore, gbc);
	gbc.gridx++;
	add(txtScore, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblPath, gbc);
	gbc.gridx = 1;
	add(txtPath, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblCategory, gbc);
	gbc.gridx++;
	add(cbCategory, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblHidden, gbc);
	gbc.gridx++;
	add(cbHiddenGame, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblGhostGame, gbc);
	gbc.gridx++;
	add(cbGhostGame, gbc);
	gbc.gridy++;
	gbc.gridx = 0;
	add(lblCompletedGame, gbc);
	gbc.gridx++;
	add(cbCompletedGame, gbc);
	gbc.gridy++;
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
	for (int i = 0; i < category.size(); i++)
	    cbCategory.addItem(category.get(i));

	cbCategory.setSelectedItem(mg.getGameCategoryName(gameId));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == btnEdit) {
	    ModelGames g = new ModelGames();
	    String ghostGame, completed;
	    int score, hidden, secondsPlayed = 0;
	    if (cbGhostGame.isSelected())
		ghostGame = "1";
	    else
		ghostGame = "0";

	    if (cbCompletedGame.isSelected())
		completed = "1";
	    else
		completed = "0";

	    if (cbHiddenGame.isSelected())
		hidden = 1;
	    else
		hidden = 0;

	    try {
		score = Integer.parseInt(txtScore.getText());
		if (score > 10)
		    score = 10;
		if (score < 0)
		    score = 0;
	    } catch (NumberFormatException ex) {
		score = 0;
	    }
	    if (!txtSecondsPlayed.getText().isEmpty() && !txtSecondsPlayed.getText().isBlank()) {
		secondsPlayed = Integer.parseInt(txtSecondsPlayed.getText());
	    }

	    if (txtTimes.getText().isEmpty() || txtTimes.getText().isBlank())
		txtTimes.setText("0");
	    if (txtGameName.getText().isEmpty() || txtGameName.getText().isBlank()) {
		JOptionPane.showMessageDialog(this, "El juego debe tener un nombre", "Error",
			JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    if (g.editGame(gameId, txtGameName.getText(), secondsPlayed, txtPath.getText(), ghostGame,
		    txtTimes.getText(), completed, score,
		    g.getCategoryIdFromName(cbCategory.getSelectedItem().toString()), hidden) == 1) {
		JOptionPane.showMessageDialog(this, "El juego ha sido editado satisfactoriamente", "Juego editado",
			JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	    } else {
		JOptionPane.showMessageDialog(this, "Ha habido un error al editar el juego", "Error",
			JOptionPane.ERROR_MESSAGE);
	    }
	    MainUI.UpdateGameList();
	} else if (e.getSource() == btnDel) {
	    int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar este juego?", "Borrar juego",
		    JOptionPane.YES_NO_OPTION);
	    if (res == 0) {
		ModelGames g = new ModelGames();
		int confirm = g.deleteGame(gameId);
		if (confirm != 0) {
		    JOptionPane.showMessageDialog(this, "El juego ha sido borrado", "Juego borrado",
			    JOptionPane.ERROR_MESSAGE);
		    this.dispose();
		} else {
		    JOptionPane.showMessageDialog(this, "Ha habido un error al borrar el juego", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	    MainUI.UpdateGameList();
	}
    }
}
