package frontend;

import database.ModelGames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class EditGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 2260581778638759215L;
    private DecimalFormat txtminsPlayedDecimal = new DecimalFormat("#.##");
    private JLabel lblGameName = new JLabel("Juego:");
    private JLabel lblminsPlayed = new JLabel("Horas jugadas:");
    private JLabel lblPath = new JLabel("Ubicacion:");
    private JLabel lblGhostGame = new JLabel("Juego fantasma");
    private JTextField txtGameName = new JTextField(20);
    private JTextField txtminsPlayed = new JTextField(20);
    private JTextField txtPath = new JTextField(20);
    private JCheckBox cbGhostGame = new JCheckBox();
    private JButton btnEdit = new JButton("Editar");
    private int gameId;

    public EditGame(int gameId) {
	setBounds(50, 50, 450, 160);
	String title = "Editar juego - "; //TODO: Poner nombre del juego
	setTitle(title);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	this.gameId = gameId;

	ModelGames g = new ModelGames();
	txtGameName.setText(g.getNameFromId(gameId));
	double mins_played = g.getMinsPlayed(gameId);
	txtminsPlayed.setText(txtminsPlayedDecimal.format(mins_played/60));
	txtPath.setText(g.getPathFromGame(gameId));

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
	gbc.gridy++;
	add(lblPath, gbc);
	gbc.gridx = 1;
	add(txtPath, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblGhostGame, gbc);
	gbc.gridx++;
	add(cbGhostGame, gbc);

	gbc.gridy ++;
	gbc.gridx = 0;
	gbc.gridwidth = 2;
	add(btnEdit, gbc);

	if(g.isGhost(gameId)) cbGhostGame.setSelected(true);
	else cbGhostGame.setSelected(false);

	btnEdit.addActionListener(this);

	setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnEdit) {
	    ModelGames g = new ModelGames();
	    String ghostGame, minsPlayed;
	    if(cbGhostGame.isSelected()) ghostGame = "1";
	    else ghostGame = "0";

	    minsPlayed = txtminsPlayed.getText().replaceAll(",", ".");

	    if(g.editGame(gameId, txtGameName.getText(), minsPlayed, txtPath.getText(), ghostGame) == 1) {
		JOptionPane.showMessageDialog(null, "El juego ha sido editado satisfactoriamente", "Juego editado", JOptionPane.INFORMATION_MESSAGE);
	    } else {
		JOptionPane.showMessageDialog(null,  "Ha habido un error al editar el juego", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    MainUI.UpdateGameList();
	}
    }
}
