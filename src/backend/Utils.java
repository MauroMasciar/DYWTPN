package backend;

import java.time.LocalDate;
import java.time.LocalTime;

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
}
