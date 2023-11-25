package frontend;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import backend.Validations;
import database.ModelConfig;

public class UpdateGUI extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 7976244907769602999L;
    private JTextField txtSql = new JTextField(30);
    private JButton btnApply = new JButton("Aplicar");
    private JLabel lblInfo = new JLabel("Ingrese las instrucciones y haga clic en aplicar (Una linea a la vez)");
    
    public UpdateGUI() {
	setTitle("Aplicar actualizaciones");
	setBounds(30, 30, 500, 100);
	setClosable(true);
	setResizable(false);
	setLayout(new GridBagLayout());
	JPanel pnl = new JPanel();
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 100;
	gbc.ipady = 1;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;
	pnl.add(lblInfo, gbc);
	gbc.gridy++;
	pnl.add(txtSql, gbc);
	gbc.gridy++;
	pnl.add(btnApply, gbc);
	
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	add(pnl, gbc);
		
	txtSql.paste();
	
	btnApply.addActionListener(this);
	setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnApply) {
	    if(Validations.isEmpty(txtSql)) {
		JOptionPane.showMessageDialog(this, "Debe escribir el codigo para poder actualizar", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    ModelConfig mc = new ModelConfig();
	    int res = mc.Update(txtSql.getText()); //TODO: Probar si devuelve confirmacion
	    if(res == 1) {
		JOptionPane.showMessageDialog(this, "Actualizacion añadida", "Actualizacion", JOptionPane.INFORMATION_MESSAGE);
	    } else {
		JOptionPane.showMessageDialog(this, "Ha habido un error al actualizar", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    txtSql.setText("");
	}	
    }

}
