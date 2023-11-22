package frontend;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static Process p;
    public static final String versionApp = "1.2.4";

    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(new FlatLightLaf()); // https://www.formdev.com/flatlaf/
	} catch (Exception e) {
	    System.out.println("No se ha podido configurar el look and feel: " + e.getMessage());
	    e.printStackTrace();
	}

	boolean test = false;//

	if(!test) {
	    ProcessBuilder pb;
	    pb = new ProcessBuilder("core\\mysql\\bin\\mysqld_z.exe");
	    try {
		p = pb.start();
	    } catch (IOException ex) {
		JOptionPane.showMessageDialog(null,"No se ha podido cargar los datos. Vuelve a intentarlo. Si el problema persiste, reinstale la aplicacion.\n\n" + ex.getMessage(), "Error al cargar", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	    }
	    new Thread(new Runnable() {
		public void run() {
		    try {
			Thread.sleep(500);
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
