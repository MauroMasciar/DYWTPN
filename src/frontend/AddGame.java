package frontend;

import database.Games;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class AddGame extends JFrame implements ActionListener, WindowListener {
    private static final long serialVersionUID = 6759981496451639858L;
    private JFrame j = new JFrame();
    private JLabel lblGameName = new JLabel("Juego:");
    private JLabel lblHoursPlayed = new JLabel("Horas jugadas:");
    private JLabel lblPath = new JLabel("Ubicacion:");
    private JLabel lblGhost = new JLabel("Fantasma:");
    private JTextField txtGameName = new JTextField(20);
    private JTextField txtHoursPlayed = new JTextField(20);
    private JTextField txtPath = new JTextField(20);
    private JCheckBox cbGhost = new JCheckBox();
    private JButton btnAdd = new JButton("Añadir");
    public AddGame() {
	j.setTitle("DYWTPN - Añadir nuevo juego");
	j.setSize(400, 150);
	j.setResizable(false);
	j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	j.setLayout(new GridBagLayout());
	
	JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 1;
	gbc.ipady = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	panel.add(lblGameName, gbc);
	gbc.gridx = 1;
	panel.add(txtGameName, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	panel.add(lblHoursPlayed, gbc);
	gbc.gridx = 1;
	panel.add(txtHoursPlayed, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	panel.add(lblPath, gbc);
	gbc.gridx = 1;
	panel.add(txtPath, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	panel.add(lblGhost, gbc);
	gbc.gridx = 1;
	panel.add(cbGhost, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	gbc.gridwidth = 2;
	panel.add(btnAdd, gbc);
	
	j.add(panel);
	j.addWindowListener(this);
	btnAdd.addActionListener(this);
	
	j.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnAdd) { 
	    //TODO: Verificar que MinsPlayed sea numeros
	    Games g = new Games();
	    int c;
	    if(cbGhost.isSelected()) c = 1;
	    else c = 0;
	    String hoursPlayed = txtHoursPlayed.getText().replaceAll(",", ".");
	    int a = g.addGame(txtGameName.getText(), hoursPlayed, txtPath.getText(), c);
	    if(a == 1) {
		JOptionPane.showMessageDialog(null, "Se ha añadido el juego correctamente", "Juego añadido", JOptionPane.INFORMATION_MESSAGE);
		txtGameName.setText("");
		txtHoursPlayed.setText("");
		txtPath.setText("");

		j.setVisible(false);
		MainWindow.j.setVisible(true);
	    } else {
		JOptionPane.showMessageDialog(null, "No se ha podido añadir el juego. Revisa que los datos sean correctos", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
	MainWindow.j.setVisible(true);
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }
}
