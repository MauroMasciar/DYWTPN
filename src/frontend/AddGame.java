package frontend;

import database.ModelGames;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AddGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 6759981496451639858L;
    private JLabel lblGameName = new JLabel("Juego:");
    private JLabel lblSecondsPlayed = new JLabel("Tiempo jugado (En segundos)");
    private JLabel lblScore = new JLabel("Puntuacion");
    private JLabel lblCategory = new JLabel("Categoria");
    private JLabel lblTimes = new JLabel("Veces jugado:");
    private JLabel lblPath = new JLabel("Ubicacion:");
    private JLabel lblCompleted = new JLabel("Completado:");
    private JLabel lblGhost = new JLabel("Fantasma:");
    private JTextField txtGameName = new JTextField();
    private JTextField txtSecondsPlayed = new JTextField();
    private JTextField txtTimes = new JTextField();
    private JTextField txtScore = new JTextField();
    private JComboBox<String> cbCategory = new JComboBox<String>();
    private JTextField txtPath = new JTextField();
    private JCheckBox cbGhost = new JCheckBox();
    private JCheckBox cbCompleted = new JCheckBox();
    private JButton btnAdd = new JButton("Añadir");
    public AddGame() {
	setTitle("Añadir nuevo juego");
	setBounds(100, 100, 400, 230);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setLayout(new GridBagLayout());

	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 40;
	gbc.ipady = 40;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(lblGameName, gbc);
	gbc.gridx = 1;
	add(txtGameName, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblSecondsPlayed, gbc);
	gbc.gridx = 1;
	add(txtSecondsPlayed, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblScore, gbc);
	gbc.gridx = 1;
	add(txtScore, gbc);	
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblCategory, gbc);
	gbc.gridx = 1;
	add(cbCategory, gbc);	
	gbc.gridx = 0;
	gbc.gridy ++;
	add(lblTimes, gbc);
	gbc.gridx ++;
	add(txtTimes, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblPath, gbc);
	gbc.gridx = 1;
	add(txtPath, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(lblGhost, gbc);
	gbc.gridx = 1;
	add(cbGhost, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	add(lblCompleted, gbc);
	gbc.gridx ++;
	add(cbCompleted, gbc);	
	gbc.gridx = 0;
	gbc.gridy++;
	gbc.gridwidth = 2;
	add(btnAdd, gbc);
	
	txtSecondsPlayed.setText("0");
	txtTimes.setText("0");
	cbCompleted.setSelected(false);
	ModelGames mg = new ModelGames();
	ArrayList<String> category = mg.getCategoryList();
	for(int i = 0; i<category.size(); i++) cbCategory.addItem(category.get(i));
	
	btnAdd.addActionListener(this);

	setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnAdd) {
	    if(txtGameName.getText().isEmpty() || txtGameName.getText().isBlank()) {
		JOptionPane.showMessageDialog(this, "Debes al menos especificar el nombre del juego", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    if(txtScore.getText().isEmpty() || txtScore.getText().isBlank()) {
		txtScore.setText("0");
	    }
	    if(txtSecondsPlayed.getText().isEmpty() || txtSecondsPlayed.getText().isBlank()) {
		txtSecondsPlayed.setText("0");
	    }
	    try {
		ModelGames g = new ModelGames();
		int c, comp, score, secondsPlayed = 0;
		score = Integer.parseInt(txtScore.getText());
		if(score > 10) score = 10;
		else if(score < 0) score = 0;
		if(cbGhost.isSelected()) c = 1;
		else c = 0;
		if(cbCompleted.isSelected()) comp = 1;
		else comp = 0;
		try {
		    secondsPlayed = Integer.parseInt(txtSecondsPlayed.getText());
		} catch (NumberFormatException ex) {
		    secondsPlayed = 0;
		}
		
		int a = g.addGame(txtGameName.getText(), secondsPlayed, txtPath.getText(), c, comp, g.getCategoryIdFromName(cbCategory.getSelectedItem().toString()), score);
		if(a == 1) {
		    JOptionPane.showMessageDialog(this, "Se ha añadido el juego correctamente", "Juego añadido", JOptionPane.INFORMATION_MESSAGE);
		    txtGameName.setText("");
		    txtSecondsPlayed.setText("");
		    txtPath.setText("");
		    MainUI.UpdateGameList();
		    this.dispose();
		} else {
		    JOptionPane.showMessageDialog(this, "No se ha podido añadir el juego. Revisa que los datos sean correctos", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
		}
	    } catch (NumberFormatException ex) {
		JOptionPane.showMessageDialog(this, "Inserte los datos correctamente", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
}
