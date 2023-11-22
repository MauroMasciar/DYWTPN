package debug;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class Log {
    public static FileWriter fw = null;
    public static PrintWriter pw = null;
    public static LocalDate c = LocalDate.now();
    public static LocalTime t = LocalTime.now();

    public Log(String string) {
	if (string != null)
	    Loguear(string);
    }

    public static void Loguear(String string) {
	c = LocalDate.now();
	t = LocalTime.now();
	try {
	    fw = new FileWriter("debug.log", true);
	    pw = new PrintWriter(fw);
	    String s = c + " " + t + ": " + string;
	    pw.println(s);
	    System.out.println(s);
	    fw.close();
	    LogGUI.addText(s);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
