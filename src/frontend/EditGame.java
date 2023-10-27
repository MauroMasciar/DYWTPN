package frontend;

import database.Games;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class EditGame extends JFrame implements ActionListener, WindowListener {
    private static final long serialVersionUID = 2260581778638759215L;
    private JFrame j = new JFrame();
    private DecimalFormat txtHoursPlayedDecimal = new DecimalFormat("#.##");
    private JLabel lblGameName = new JLabel("Juego:");
    private JLabel lblHoursPlayed = new JLabel("Horas jugadas:");
    private JLabel lblPath = new JLabel("Ubicacion:");
    private JLabel lblGhostGame = new JLabel("Juego fantasma");
    private JTextField txtGameName = new JTextField(20);
    private JTextField txtHoursPlayed = new JTextField(20);
    private JTextField txtPath = new JTextField(20);
    private JCheckBox cbGhostGame = new JCheckBox();
    private JButton btnEdit = new JButton("Editar");
    private int gameId;
    
    public EditGame(int gameId) {
	j.setSize(800, 600);
	String title = "Editar juego - "; //TODO: Poner nombre del juego
	j.setTitle(title);
	j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	j.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	this.gameId = gameId;
	
	Games g = new Games();
	txtGameName.setText(g.getNameFromId(gameId));
	txtHoursPlayed.setText(txtHoursPlayedDecimal.format(g.getHoursPlayed(gameId)));
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
	j.add(lblGameName, gbc);
	gbc.gridx++;
	j.add(txtGameName, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	j.add(lblHoursPlayed, gbc);
	gbc.gridx = 1;
	j.add(txtHoursPlayed, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	j.add(lblPath, gbc);
	gbc.gridx = 1;
	j.add(txtPath, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	j.add(lblGhostGame, gbc);
	gbc.gridx++;
	j.add(cbGhostGame, gbc);
	
	gbc.gridy ++;
	gbc.gridx = 0;
	gbc.gridwidth = 2;
	j.add(btnEdit, gbc);

	if(g.isGhost(gameId)) cbGhostGame.setSelected(true);
	else cbGhostGame.setSelected(false);
	
	j.addWindowListener(this);
	btnEdit.addActionListener(this);
	
	j.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnEdit) {
	    Games g = new Games();
	    String ghostGame, hoursPlayed;
	    if(cbGhostGame.isSelected()) ghostGame = "1";
	    else ghostGame = "0";
	    
	    hoursPlayed = txtHoursPlayed.getText().replaceAll(",", ".");

	    if(g.editGame(gameId, txtGameName.getText(), hoursPlayed, txtPath.getText(), ghostGame) == 1) {
		JOptionPane.showMessageDialog(null, "El juego ha sido editado satisfactoriamente", "Juego editado", JOptionPane.INFORMATION_MESSAGE);
	    } else {
		JOptionPane.showMessageDialog(null,  "Ha habido un error al editar el juego", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }

    @Override
    public void windowClosing(WindowEvent e) {
	MainWindow.j.setVisible(true);
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

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
