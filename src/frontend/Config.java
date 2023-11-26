package frontend;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import backend.Validations;
import database.ModelConfig;

public class Config extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 6985809216237042311L;
    private JLabel lblName = new JLabel("Nuevo nombre");
    private JTextField txtName = new JTextField(10);
    private JButton btnSave = new JButton("Guardar datos");
    private JButton btnTruncate = new JButton("Resetear datos");
    private JLabel lblMainUIx = new JLabel("Main X");
    private JTextField txtMainUIx = new JTextField(10);
    private JLabel lblMainUIy = new JLabel("Main Y");
    private JTextField txtMainUIy = new JTextField(10);
    private JLabel lblActivityx = new JLabel("Actividad X");
    private JTextField txtActivityx = new JTextField(10);
    private JLabel lblActivityy = new JLabel("Actividad Y");
    private JTextField txtActivityy = new JTextField(10);
    private JLabel lblHistoryx = new JLabel("Historial X");
    private JTextField txtHistoryx = new JTextField(10);
    private JLabel lblHistoryy = new JLabel("Historial Y");
    private JTextField txtHistoryy = new JTextField(10);

    public Config() {
	try {
	    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/config.png"));
	    this.setFrameIcon(icon);
	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
	}
	setTitle("Configuracion");
	setSize(800, 400);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setLayout(new FlowLayout());
	setClosable(true);

	add(lblName);
	add(txtName);
	add(lblMainUIx);
	add(txtMainUIx);
	add(lblMainUIy);
	add(txtMainUIy);
	add(lblActivityx);
	add(txtActivityx);
	add(lblActivityy);
	add(txtActivityy);
	add(lblHistoryx);
	add(txtHistoryx);
	add(lblHistoryy);
	add(txtHistoryy);
	add(btnSave);
	add(btnTruncate);

	btnSave.addActionListener(this);
	btnTruncate.addActionListener(this);

	ModelConfig mc = new ModelConfig();
	txtName.setText(mc.getUsername());
	txtMainUIx.setText(String.valueOf(mc.getBounds_x("MainUI")));
	txtMainUIy.setText(String.valueOf(mc.getBounds_y("MainUI")));
	txtActivityx.setText(String.valueOf(mc.getBounds_y("Activity")));
	txtActivityy.setText(String.valueOf(mc.getBounds_y("Activity")));
	txtHistoryx.setText(String.valueOf(mc.getBounds_y("History")));
	txtHistoryy.setText(String.valueOf(mc.getBounds_y("History")));

	setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == btnTruncate) {
	    int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar todos los datos? Esto borrara todo tu historial de sesiones, datos de usuario, juegos, etc", "Borrado de datos", JOptionPane.YES_NO_OPTION);
	    if (res == 0) {
		ModelConfig mc = new ModelConfig();
		int dataTruncated = mc.truncateData();
		if(dataTruncated == 1) {
		    JOptionPane.showMessageDialog(this, "Los datos han sido reseteados. Debe reiniciar la aplicacion", "Datos cerrados", JOptionPane.INFORMATION_MESSAGE);
		} else {
		    JOptionPane.showMessageDialog(this, "No se han podido borrar los datos. Prueba a reiniciar la aplicacion y volver a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	} else if (e.getSource() == btnSave) {
	    ModelConfig mc = new ModelConfig();
	    if(Validations.isEmpty(txtName) || Validations.isEmpty(txtMainUIx) || Validations.isEmpty(txtMainUIy) || Validations.isEmpty(txtActivityx) || Validations.isEmpty(txtActivityy)
		    || Validations.isEmpty(txtHistoryx) || Validations.isEmpty(txtHistoryy)) {
		JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    mc.saveUserName(txtName.getText());
	    try {
		mc.setSavedBounds("MainUI", Double.parseDouble(txtMainUIx.getText()), Double.parseDouble(txtMainUIy.getText()));
		mc.setSavedBounds("Activity", Double.parseDouble(txtActivityx.getText()), Double.parseDouble(txtActivityy.getText()));
		mc.setSavedBounds("History", Double.parseDouble(txtHistoryx.getText()), Double.parseDouble(txtHistoryy.getText()));
		JOptionPane.showMessageDialog(this, "Los datos han sido guardados", "Datos guardados", JOptionPane.INFORMATION_MESSAGE);
	    } catch(NumberFormatException ex) {
		JOptionPane.showMessageDialog(this, "Algunos campos tienen datos incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
}
