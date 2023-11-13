package frontend;

import javax.swing.JInternalFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Collections extends JInternalFrame {
    private static final long serialVersionUID = -66520709315604880L;
    public Collections() {
	setTitle("Colecciones");
	setBounds(100, 100, 400, 230);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 40;
	gbc.ipady = 40;
	gbc.fill = GridBagConstraints.BOTH;
	
	setVisible(true);
    }
}
