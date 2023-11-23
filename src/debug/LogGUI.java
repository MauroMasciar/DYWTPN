package debug;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

public class LogGUI extends JInternalFrame {
	private static final long serialVersionUID = -670193207184414527L;
	public static JTextArea txtaDebug = new JTextArea();
	private JScrollPane scr = new JScrollPane(txtaDebug);

	public LogGUI() {
		setBounds(30, 30, 500, 500);
		setTitle("DEBUG");
		setClosable(true);
		setResizable(true);
		setLayout(new GridBagLayout());
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
		add(scr, gbc);

		txtaDebug.setEditable(false);

		setVisible(true);
	}

	public static void addText(String t) {
		txtaDebug.append(t);
		txtaDebug.append("\n");
	}
}
