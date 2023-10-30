package frontend;

import database.ModelConfig;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Config extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 6985809216237042311L;
    private JLabel lblName = new JLabel("Nombre");
    private JTextField txtName = new JTextField(20);
    private JButton btnTruncate = new JButton("Borrar datos");
    public Config() {
	setTitle("DYWTPN");
	setSize(800, 400);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);

	add(lblName);
	add(txtName);
	add(btnTruncate);
	
	btnTruncate.addActionListener(this);

	setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnTruncate) {
	    int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar todos los datos? Esto borrara todo tu historial de sesiones, datos "
		    + "de usuario, juegos, etc","Borrado de datos",JOptionPane.YES_NO_OPTION);
	    if(res == 0) {
		ModelConfig mc = new ModelConfig();
		mc.truncateData();
	    }
	}
    }
}
