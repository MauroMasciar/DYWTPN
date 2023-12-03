package frontend;

import java.io.IOException;
import javax.swing.JOptionPane;

import database.ModelConfig;

public class Main {
    public static Process p;
    public static final String VERSIONAPP = "1.2.4.60";
    public static boolean test = false;

    public static void main(String[] args) {
	if(!test) {
	    new Thread(new Runnable() {
		public void run() {
		    try {
			ProcessBuilder pb = new ProcessBuilder("core\\mysql\\bin\\mysqld_z.exe");
			p = pb.start();
			@SuppressWarnings("unused")
			Splash splash = new Splash();
			Thread.sleep(1000);
			ModelConfig mc = new ModelConfig();
			ModelConfig.loadTheme(mc.getTheme());
			Thread.sleep(2000);
			MainUI.loadData();
		    } catch (InterruptedException | IOException ex) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar los datos, vuelva a intentarlo. Si el problema persiste, reinstale la aplicacion.\n\n" + ex.getMessage(), "Error al cargar", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		    }
		}
	    }).start();
	} else {
	    try {
		@SuppressWarnings("unused")
		Splash splash = new Splash();
		Thread.sleep(1000);
		ModelConfig mc = new ModelConfig();
		ModelConfig.loadTheme(mc.getTheme());

		Thread.sleep(2000);
		MainUI.loadData();
	    } catch (InterruptedException ex) {
		JOptionPane.showMessageDialog(null, "No se ha podido cargar los datos, vuelva a intentarlo. Si el problema persiste, reinstale la aplicacion.\n\n" + ex.getMessage(), "Error al cargar", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	    }
	}
    }
}
