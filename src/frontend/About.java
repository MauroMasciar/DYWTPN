package frontend;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class About extends JInternalFrame {
	private static final long serialVersionUID = 7342188362929540330L;
	private final JPanel pnlInfo = new JPanel();
	private final JLabel lblFirstLine = new JLabel();
	private final JLabel lblSecondLine = new JLabel();

	public About() {
		try {
			ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/about.png"));
			this.setFrameIcon(icon);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.",
					"Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
		}
		setBounds(90, 70, 420, 100);
		setTitle("Acerca de");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		lblFirstLine.setText("Software desarrollado por Mauro Masciadro - MASCIAR - Version " + Main.VERSION_APP);
		lblSecondLine.setText("Codigo fuente disponible en github.com/MauroMasciar/DYWTPN");

		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.ipadx = 40;
		gbc.ipady = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlInfo.add(lblFirstLine, gbc);
		gbc.gridy++;
		pnlInfo.add(lblSecondLine, gbc);

		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.ipadx = 40;
		gbc.ipady = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(pnlInfo, gbc);

		setVisible(true);
	}
}
