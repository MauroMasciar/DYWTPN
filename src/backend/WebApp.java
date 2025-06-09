package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

public class WebApp {
	public static void sendStatus(String username, String gamename, String user_id, int secondsBeetwenTimes, int state) {
		String url = "https://dywtpn.fun/controller/data_app.php?userid=" + user_id + "&name=" + username + "&state=" + state + "&game=" + gamename + "&time=" + Utils.getTotalHoursFromSeconds(secondsBeetwenTimes, false);
		//String url = "http://localhost/www-dywtpn/controller/data_app.php?userid=" + user_id + "&name=" + username + "&state=" + state + "&game=" + gamename + "&time=" + Utils.getTotalHoursFromSeconds(secondsBeetwenTimes, false);
        url = url.replace(" ", "%20");
        send(url);
	}
	
	public static void sendSession(String user_id, String username, String game_name, int library_id, int platform_id, String datetime_start, String date_end, int seconds) {
		//String url = "https://dywtpn.fun/controller/receivegamesession.php?user_id=" + user_id + "&name=" + username + "&game_name=" + game_name + "&library_id=" + library_id + "&platform_id=" + platform_id + "&datetime_start=" + datetime_start + "&datetime_end=" + date_end + "&seconds="+ seconds;
		String url = "http://localhost/www-dywtpn/controller/receivegamesession.php?user_id=" + user_id + "&name=" + username  + "&game_name=" + game_name + "&library_id=" + library_id + "&platform_id=" + platform_id + "&datetime_start=" + datetime_start + "&datetime_end=" + date_end + "&seconds="+ seconds;
		url = url.replace(" ", "%20");
        send(url);
	}
	
	private static void send(String url) {
		try {			
			@SuppressWarnings("deprecation")
			URL obj = new URL(url);
	        HttpURLConnection con;
	        
			con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
	        con.setRequestProperty("cache-control", "no-cache");
	        con.setRequestProperty("X-API-KEY", "myApiKey");
	        con.setRequestProperty("X-API-EMAIL", "myEmail@mail.com");

	        int responseCode = con.getResponseCode();
	        System.out.println("\nSending 'GET' request to URL : " + url);
	        System.out.println("Response Code : " + responseCode);

	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        System.out.println(response.toString());
	        if(response.toString().equals("OK")) JOptionPane.showMessageDialog(null, "Sesión enviada a la web", "Exito", JOptionPane.INFORMATION_MESSAGE);
	        else JOptionPane.showMessageDialog(null, "Fallo al enviar la sesión. Verifica si no la has enviado ya o vuelve a intentar mas tarde.\nSi el problema persiste contacte con soporte.", "Fallo", JOptionPane.ERROR_MESSAGE);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
