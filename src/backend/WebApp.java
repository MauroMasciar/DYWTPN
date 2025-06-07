package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebApp {
	public static void send(String username, String gamename, String user_id, int secondsBeetwenTimes, int state) {
		String url = "http://localhost/www-dywtpn/controller/data_app.php?userid=" + user_id + "&name=" + username + "&state=" + state + "&game=" + gamename + "&time=" + Utils.getTotalHoursFromSeconds(secondsBeetwenTimes, false);
        url = url.replace(" ", "%20");

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
