package backend;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JInternalFrame;

import debug.Log;
import frontend.Main;

public class Utils {
    public static String getFormattedDate() {
        String date;
        date = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth();
        return date;
    }

    public static String getFormattedDateTime() {
        String time, sHour, sMinute, sSecond;
        int hour, minute, second;
        hour = LocalTime.now().getHour();
        minute = LocalTime.now().getMinute();
        second = LocalTime.now().getSecond();

        if(second < 10) sSecond = "0" + second;
        else sSecond = String.valueOf(second);

        if(minute < 10) sMinute = "0" + minute;
        else sMinute = String.valueOf(minute);

        if(hour < 10) sHour = "0" + hour;
        else sHour = String.valueOf(hour);

        time = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth() + " " + sHour + ":" + sMinute + ":" + sSecond;
        return time;
    }

    public static String getTotalHoursFromSeconds(int seconds, boolean withSeconds) {
        String sHour, sMinute, sSecond;
        int seconds_final = 0;
        int minutes = seconds / 60;
        seconds_final = seconds % 60;
        int minutes_final = minutes % 60;
        int hours_final = minutes / 60;

        if(seconds_final < 10) sSecond = "0" + seconds_final;
        else sSecond = String.valueOf(seconds_final);

        if(minutes_final < 10) sMinute = "0" + minutes_final;
        else sMinute = String.valueOf(minutes_final);

        sHour = String.valueOf(hours_final);

        if(withSeconds) return sHour + "h " + sMinute + "m " + sSecond + "s";
        return sHour + "h " + sMinute + "m";
    }

    public static void logExceptions(Exception ex) {
        ex.printStackTrace();
        StackTraceElement[] e = ex.getStackTrace();
        Log.Loguear("Rastreo de la pila de getStackTrace:");
        Log.Loguear("Clase\t\t Archivo\t\tLínea\tMetodo");
        for(StackTraceElement element : e ) {
            Log.Loguear(element.getClassName() + "\t\t" + element.getFileName() + "\t\t" + String.valueOf(element.getLineNumber()) + "\t\t" + element.getMethodName());
        }
    }

    public static void getSize(JInternalFrame internalFrame) {
        new Thread(new Runnable() {
            public void run() {
                while(Main.test) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(internalFrame.getSize());
                    } catch (InterruptedException ex) {
                        Log.Loguear(ex.getMessage());
                    }		    
                }

            }
        }).start();
    }
}
