package database;

import debug.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelConfig {
    private String database = "DYWTPN";
    private String hostname = "localhost";
    private String port = "3306";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private String username = "root";
    private String password = "123456";
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;
    public void truncateData() {
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();

	    String query = "TRUNCATE games";
	    Log.Loguear(query);
	    stmt.execute(query);

	    query = "TRUNCATE config";
	    Log.Loguear(query);
	    stmt.execute(query);

	    query = "TRUNCATE games_sessions_history";
	    Log.Loguear(query);
	    stmt.execute(query);

	    query = "INSERT INTO config (name) VALUES ('Usuario')";
	    Log.Loguear(query);
	    stmt.execute(query);

	    stmt.close();
	    conex.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	}
    }

    public String getNameUser() {
	String query = "SELECT name FROM config";
	String name = "";

	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		name = rs.getString("name");
	    } else {
		name = "User";
	    }
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	
	return name;
    }
    
    public int GetHoursTotalPlayed() {
	String query = "SELECT SUM(mins_played) AS minutes FROM games";
	int minutes = 0;

	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		minutes = rs.getInt("minutes");
	    } else {
		minutes = 0;
	    }
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return minutes;
    }
}
