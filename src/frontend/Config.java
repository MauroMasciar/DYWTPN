package frontend;

import database.ModelConfig;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Config extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 6985809216237042311L;
    private JLabel lblName = new JLabel("Nuevo nombre");
    private JTextField txtName = new JTextField(10);
    private JButton btnSave = new JButton("Guardar datos");
    private JButton btnTruncate = new JButton("Resetear datos");
    
    public Config() {
	setTitle("Configuracion");
	setSize(800, 400);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setLayout(new FlowLayout());
	setClosable(true);

	add(lblName);
	add(txtName);
	add(btnSave);
	add(btnTruncate);

	btnSave.addActionListener(this);
	btnTruncate.addActionListener(this);

	setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == btnTruncate) {
	    int res = JOptionPane.showConfirmDialog(this,
		    "¿Seguro que desea borrar todos los datos? Esto borrara todo tu historial de sesiones, datos "
			    + "de usuario, juegos, etc",
			    "Borrado de datos", JOptionPane.YES_NO_OPTION);
	    if (res == 0) {
		ModelConfig mc = new ModelConfig();
		int dataTruncated = mc.truncateData();
		if(dataTruncated == 1) {
		    JOptionPane.showMessageDialog(this, "Los datos han sido reseteados", "Datos cerrados", JOptionPane.INFORMATION_MESSAGE);
		    MainUI.LoadData();
		    MainUI.UpdateGameList();
		} else {
		    JOptionPane.showMessageDialog(this, "No se han podido borrar los datos. Prueba a reiniciar la aplicacion y volver a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	} else if (e.getSource() == btnSave) {
	    ModelConfig mc = new ModelConfig();
	    if (txtName.getText().isEmpty()) {
		JOptionPane.showMessageDialog(this, "Debe introducir un nombre de usuario", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    mc.saveUserName(txtName.getText());
	    JOptionPane.showMessageDialog(this, "Su nombre de usuario ha sido cambiado", "Nombre cambiado", JOptionPane.INFORMATION_MESSAGE);
	}
    }
}
