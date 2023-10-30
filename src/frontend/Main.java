package frontend;

import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
	try {
	    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    UIManager.setLookAndFeel(new FlatLightLaf()); // https://www.formdev.com/flatlaf/
	} catch (Exception e) {
	    System.out.println("No se ha podido configurar el look and feel: " + e.getMessage());
	    e.printStackTrace();
	}
	new MainWindow();
    }
}
