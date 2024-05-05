package frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import database.ModelConfig;

public class Splash extends JDialog {
    private static final long serialVersionUID = 8022358665918705197L;
    private final JLabel lblImg = new JLabel();

    public Splash() {
        setSize(600, 400);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        ImageIcon splash_image  = new ImageIcon(this.getClass().getResource("/gfx/splash.png"));
        lblImg.setIcon(splash_image);

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
            @SuppressWarnings("unused")
            public void run() {
                try {
                    if(Main.test) {
                        MainWindow mainWindow = new MainWindow();
                        MainWindow.showWindow();
                        Thread.sleep(500);
                        ModelConfig mc = new ModelConfig();
                        ModelConfig.loadTheme(mc.getTheme());
                        Thread.sleep(250);
                        MainUI.loadData(true);
                        Thread.sleep(1000);
                        dispose();
                    } else {
                        Thread.sleep(500);
                        ModelConfig mc = new ModelConfig();
                        ModelConfig.loadTheme(mc.getTheme());
                        Thread.sleep(250);
                        MainUI.loadData(true);
                        Thread.sleep(1000);
                        MainWindow mainWindow = new MainWindow();
                        MainWindow.showWindow();
                        Thread.sleep(7000);
                        dispose();
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
        }).start();
    }
}
