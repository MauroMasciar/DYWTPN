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
	    
	    String query = "TRUNCATE category";
	    Log.Loguear(query);
	    stmt.execute(query);
	    
	    query = "TRUNCATE config";
	    Log.Loguear(query);
	    stmt.execute(query);

	    query = "TRUNCATE games";
	    Log.Loguear(query);
	    stmt.execute(query);

	    query = "TRUNCATE games_sessions_history";
	    Log.Loguear(query);
	    stmt.execute(query);
	    
	    query = "TRUNCATE player_activities";
	    Log.Loguear(query);
	    stmt.execute(query);

	    query = "INSERT INTO config (name) VALUES ('Usuario')";
	    Log.Loguear(query);
	    stmt.execute(query);
	    
	    query = "INSERT INTO category (name) VALUES ('Ninguna')";
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
		name = "ERROR";
	    }
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return name;
    }
       
    public String getLastGame() {
	String query = "SELECT last_game FROM config";
	String last_game = "";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		last_game = rs.getString("last_game");
	    } else {
		last_game = "ERROR";
	    }
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return last_game;
    }
    
    public String getLastSessionTime() {
	String query = "SELECT last_session_time FROM config";
	String last_session_time = "";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		last_session_time = rs.getString("last_session_time");
	    } else {
		last_session_time = "ERROR";
	    }
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return last_session_time;
    }
    
    public boolean getIsHidden() {
	String query = "SELECT show_hidden FROM config";
	int sH = 0;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		sH = rs.getInt("show_hidden");
	    }
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	if(sH == 1) return true;
	else return false;
    }
    
    public void setIsHidden(int args) {
	String query = "UPDATE config SET show_hidden = " + args;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    stmt.execute(query);
	    System.out.println(query);
	    stmt.close();
	    conex.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
    
    
    public void saveUserName(String newName) {
	String query = "UPDATE config SET name = '" + newName + "';";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    stmt.execute(query);
	    stmt.close();
	    conex.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
