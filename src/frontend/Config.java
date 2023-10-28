package frontend;

import database.ModelConfig;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Config extends JFrame implements ActionListener, WindowListener {
	private static final long serialVersionUID = 6985809216237042311L;
	private JFrame j = new JFrame();
	private JLabel lblName = new JLabel("Nombre");
	private JTextField txtName = new JTextField(20);
	private JButton btnTruncate = new JButton("Borrar datos");
	public Config() {
		j.setTitle("DYWTPN");
		j.setSize(800, 400);
		j.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		j.add(lblName);
		j.add(txtName);
		j.add(btnTruncate);
		
		j.addWindowListener(this);
		btnTruncate.addActionListener(this);
		
		j.setVisible(true);
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
	@Override
	public void windowOpened(WindowEvent e) {
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		MainWindow.j.setVisible(true);
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
