package frontend;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import database.ModelGames;

public class AddSessionGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = -7862927860955738026L;
    private JLabel lblCbGame = new JLabel("Seleccione el juego");
    private JLabel lblTime = new JLabel("Tiempo jugado (En minutos)");
    private JLabel lblDate = new JLabel("Fecha: (YYYY-MM-DD HH:MM:SS)");
    private JComboBox<String> cbGame = new JComboBox<String>();
    private JTextField txtTime = new JTextField();
    private JTextField txtDate = new JTextField();
    private JButton btnAdd = new JButton("Añadir");
    
    ModelGames mg = new ModelGames();
    public AddSessionGame() {
	setTitle("Añadir nueva sesion");
	setBounds(100, 100, 400, 160);
	setClosable(true);
	setResizable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	add(lblCbGame, gbc);
	gbc.gridx ++;
	add(cbGame, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	add(lblTime, gbc);
	gbc.gridx ++;
	add(txtTime, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	add(lblDate, gbc);
	gbc.gridx ++;
	add(txtDate, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	gbc.gridwidth = 2;
	add(btnAdd, gbc);
	
	btnAdd.addActionListener(this);
	
	ArrayList<String> gameList = new ArrayList<String>();
	gameList = mg.getGamesNameList();
	for(int i=1; i<gameList.size(); i++) {
	    cbGame.addItem(gameList.get(i));
	}
		
	setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnAdd) {
	    if(txtTime.getText().isEmpty()) {
		JOptionPane.showMessageDialog(this, "Debes especificar cuanto tiempo jugaste", "Faltan datos", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    String hoursPlayed = txtTime.getText().replaceAll(",", ".");
	    int gameId = mg.getIdFromGameName(cbGame.getSelectedItem().toString());
	    if(mg.addSessionGame(gameId, cbGame.getSelectedItem().toString(), hoursPlayed, txtDate.getText()) == 1) {
		txtTime.setText("");
		txtDate.setText("");
		this.dispose();
		JOptionPane.showMessageDialog(this, "La sesion de juego se ha agregado satisfactoriamente", "Sesion añadida", JOptionPane.INFORMATION_MESSAGE);
	    } else {
		JOptionPane.showMessageDialog(this, "La sesion de juego no se ha podido agregar. Verifica que todos los datos sean correctos", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	}
    }
}
