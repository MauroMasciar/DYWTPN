package frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import backend.Utils;
import backend.Validations;
import database.ModelConfig;

public class Config extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 6985809216237042311L;
    private final JLabel lblName = new JLabel("Nuevo nombre");
    private final JTextField txtName = new JTextField(10);
    private final JButton btnSave = new JButton("Guardar datos");
    private final JButton btnTruncate = new JButton("Resetear datos");
    private final JLabel lblMainUIx = new JLabel("Main X");
    private final JTextField txtMainUIx = new JTextField(10);
    private final JLabel lblMainUIy = new JLabel("Main Y");
    private final JTextField txtMainUIy = new JTextField(10);
    private final JLabel lblActivityx = new JLabel("Actividad X");
    private final JTextField txtActivityx = new JTextField(10);
    private final JLabel lblActivityy = new JLabel("Actividad Y");
    private final JTextField txtActivityy = new JTextField(10);
    private final JLabel lblHistoryx = new JLabel("Historial X");
    private final JTextField txtHistoryx = new JTextField(10);
    private final JLabel lblHistoryy = new JLabel("Historial Y");
    private final JTextField txtHistoryy = new JTextField(10);
    private final JComboBox<String> cbTheme = new JComboBox<String>();
    private final JLabel lblTheme = new JLabel("(Requiere reincio)");
    private final JPanel pnl = new JPanel();

    public Config() {
	try {
	    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/config.png"));
	    this.setFrameIcon(icon);
	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
	}
	setTitle("Configuracion");
	setSize(550, 270);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setResizable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	pnl.setLayout(new GridBagLayout());
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 10;
	gbc.ipady = 10;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;
	pnl.add(lblName, gbc);
	gbc.gridx++;
	gbc.gridwidth = 3;
	gbc.weightx = 3.0;
	pnl.add(txtName, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridx = 0;
	gbc.gridy++;
	pnl.add(lblMainUIx, gbc);
	gbc.gridx++;	
	pnl.add(txtMainUIx, gbc);
	gbc.gridx++;
	pnl.add(lblMainUIy, gbc);
	gbc.gridx++;
	pnl.add(txtMainUIy, gbc);
	gbc.gridx = 0;
	gbc.gridy++;	
	pnl.add(lblActivityx, gbc);
	gbc.gridx++;
	pnl.add(txtActivityx, gbc);
	gbc.gridx++;
	pnl.add(lblActivityy, gbc);
	gbc.gridx++;
	pnl.add(txtActivityy, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnl.add(lblHistoryx, gbc);
	gbc.gridx++;
	pnl.add(txtHistoryx, gbc);
	gbc.gridx++;
	pnl.add(lblHistoryy, gbc);
	gbc.gridx++;
	pnl.add(txtHistoryy, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnl.add(cbTheme, gbc);
	gbc.gridx++;
	pnl.add(lblTheme, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	pnl.add(btnSave, gbc);
	gbc.gridx += 3;
	pnl.add(btnTruncate, gbc);
	
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 10;
	gbc.ipady = 10;
	gbc.fill = GridBagConstraints.NONE;
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(pnl, gbc);	

	btnSave.addActionListener(this);
	btnTruncate.addActionListener(this);
	cbTheme.addActionListener(this);

	loadData();

	setVisible(true);
	
	Utils.getSize(this);
    }

    public void loadData() {
	cbTheme.addItem("Claro");
	cbTheme.addItem("Oscuro");
	ModelConfig mc = new ModelConfig();
	txtName.setText(mc.getUsername());
	txtMainUIx.setText(String.valueOf(mc.getBounds_x("MainUI")));
	txtMainUIy.setText(String.valueOf(mc.getBounds_y("MainUI")));
	txtActivityx.setText(String.valueOf(mc.getBounds_y("Activity")));
	txtActivityy.setText(String.valueOf(mc.getBounds_y("Activity")));
	txtHistoryx.setText(String.valueOf(mc.getBounds_y("History")));
	txtHistoryy.setText(String.valueOf(mc.getBounds_y("History")));
	int theme = mc.getTheme();
	if(theme == 1) cbTheme.setSelectedItem("Claro");
	else if(theme == 2) cbTheme.setSelectedItem("Oscuro");
    }

    public void setTheme() {
	ModelConfig mc = new ModelConfig();
	if(cbTheme.getSelectedItem().equals("Claro")) mc.setTheme(1);
	else if(cbTheme.getSelectedItem().equals("Oscuro")) mc.setTheme(2);
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
	    setTheme();

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
	} else if(e.getSource() == cbTheme) {
	    setTheme();
	}
    }
}
