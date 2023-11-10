package frontend;

import java.io.IOException;

import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static Process p;
    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(new FlatLightLaf()); // https://www.formdev.com/flatlaf/
	} catch (Exception e) {
	    System.out.println("No se ha podido configurar el look and feel: " + e.getMessage());
	    e.printStackTrace();
	}

	boolean test = false;

	if(!test) {
	    ProcessBuilder pb;
	    pb = new ProcessBuilder("C:\\MiGestorDeJuegos\\core\\mysql\\bin\\mysqld_z.exe");
	    try {
		p = pb.start();
	    } catch (IOException e) {
		System.exit(0);
	    }

	    new Thread(new Runnable() {
		public void run() {
		    try {
			Thread.sleep(1000);
			new MainWindow();
		    } catch (InterruptedException ex) {
			System.exit(0);
		    }
		}
	    }).start();

	} else {
	    new MainWindow();
	}
    }
}
