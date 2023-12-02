package frontend;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class Main {
    public static Process p;
    public static final String VERSIONAPP = "1.2.4.56";
    public static boolean test = true;

    public static void main(String[] args) {
	if(!test) {
	    new Thread(new Runnable() {
		public void run() {
		    try {
			ProcessBuilder pb = new ProcessBuilder("core\\mysql\\bin\\mysqld_z.exe");
			p = pb.start();
			@SuppressWarnings("unused")
			Splash splash = new Splash();
			/*Thread.sleep(1000);
			ModelConfig mc = new ModelConfig();
			ModelConfig.loadTheme(mc.getTheme());*/
			try {
			    UIManager.setLookAndFeel(new FlatMacDarkLaf());
			} catch (UnsupportedLookAndFeelException ex) {
			    ex.printStackTrace();
			}
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
		//UIManager.setLookAndFeel(new FlatIntelliJLaf());
		UIManager.setLookAndFeel(new FlatMacDarkLaf());
		@SuppressWarnings("unused")
		MainWindow mainWindow = new MainWindow();
		MainWindow.showWindow();
	    } catch (UnsupportedLookAndFeelException ex) {
		ex.printStackTrace();
	    }
	}
    }
}
