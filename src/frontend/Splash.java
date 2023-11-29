package frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class Splash extends JDialog {
    private static final long serialVersionUID = 8022358665918705197L;
    private final JLabel lblImg = new JLabel();

    public Splash() {
	setSize(600, 400);
	setUndecorated(true);
	setLocationRelativeTo(null);
	setLayout(new GridBagLayout());
	ImageIcon imagen  = new ImageIcon(this.getClass().getResource("/gfx/splash.png"));
	lblImg.setIcon(imagen);

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
	add(lblImg, gbc);

	setVisible(true);
	
	new Thread(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(2500);
		    new MainWindow();
		    Thread.sleep(500);
		    MainWindow.showWindow();
		    dispose();
		} catch (InterruptedException ex) {
		    System.exit(0);
		}
	    }
	}).start();
    }
}
