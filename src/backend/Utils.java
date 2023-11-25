package backend;

import java.time.LocalDate;
import java.time.LocalTime;

public class Utils {
    public static String getDate() {
	String date;
	date = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth();
	return date;
    }

    public static String getDateTime() {
	String time;
	time = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth() + " " + LocalTime.now().getHour() + ":" 
		+ LocalTime.now().getMinute() + ":" + LocalTime.now().getSecond();
	return time;
    }
}
