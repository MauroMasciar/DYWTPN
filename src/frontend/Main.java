package frontend;

import java.io.IOException;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import database.ModelConfig;

public class Main {
    public static Process p;
    public static final String VERSION_APP = "1.2.4.160";
    public static boolean test = false;

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        if(!test) {
            new Thread(new Runnable() {
                @SuppressWarnings("deprecation")
                public void run() {
                    try {
                        String string = "core\\mysql\\bin\\mysqld_z.exe --port 3308";
                        p = Runtime.getRuntime().exec(string);
                        Splash splash = new Splash();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "main() No se ha podido cargar los datos, vuelva a intentarlo. Si el problema persiste, reinstale la aplicación.\n\n" + ex.getMessage(), "Error al cargar", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
            }).start();
        } else {
            ModelConfig mc = new ModelConfig();
            ModelConfig.loadTheme(mc.getTheme());
            Splash splash = new Splash();
        }
    }
}
