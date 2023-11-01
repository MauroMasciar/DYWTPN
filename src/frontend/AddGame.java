package frontend;

import database.ModelGames;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 6759981496451639858L;
    private JLabel lblGameName = new JLabel("Juego:");
    private JLabel lblHoursPlayed = new JLabel("Horas jugadas:");
    private JLabel lblTimes = new JLabel("Veces jugado:");
    private JLabel lblPath = new JLabel("Ubicacion:");
    private JLabel lblCompleted = new JLabel("Completado:");
    private JLabel lblGhost = new JLabel("Fantasma:");
    private JTextField txtGameName = new JTextField(20);
    private JTextField txtHoursPlayed = new JTextField(20);
    private JTextField txtTimes = new JTextField(20);
    private JTextField txtPath = new JTextField(20);
    private JCheckBox cbGhost = new JCheckBox();
    private JCheckBox cbCompleted = new JCheckBox();
    private JButton btnAdd = new JButton("Añadir");
    public AddGame() {
	setTitle("Añadir nuevo juego");
	setBounds(200, 200, 400, 170);
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
	add(lblHoursPlayed, gbc);
	gbc.gridx = 1;
	add(txtHoursPlayed, gbc);
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
	
	txtHoursPlayed.setText("0");
	txtTimes.setText("0");
	cbCompleted.setSelected(false);

	btnAdd.addActionListener(this);

	setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnAdd) {
	    if(txtGameName.getText().isEmpty()) {
		JOptionPane.showMessageDialog(this, "Debes al menos especificar el nombre del juego", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    try {
		ModelGames g = new ModelGames();
		int c, comp;
		if(cbGhost.isSelected()) c = 1;
		else c = 0;
		if(cbCompleted.isSelected()) comp = 1;
		else comp = 0;
		String hoursPlayed = txtHoursPlayed.getText().replaceAll(",", ".");
		int a = g.addGame(txtGameName.getText(), hoursPlayed, txtPath.getText(), c, comp);
		if(a == 1) {
		    JOptionPane.showMessageDialog(this, "Se ha añadido el juego correctamente", "Juego añadido", JOptionPane.INFORMATION_MESSAGE);
		    txtGameName.setText("");
		    txtHoursPlayed.setText("");
		    txtPath.setText("");
		    MainUI.UpdateGameList();
		} else {
		    JOptionPane.showMessageDialog(this, "No se ha podido añadir el juego. Revisa que los datos sean correctos", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
		}
	    } catch (NumberFormatException ex) {
		JOptionPane.showMessageDialog(this, "El tiempo jugado debe ser un numero entero o decimal.", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
}
