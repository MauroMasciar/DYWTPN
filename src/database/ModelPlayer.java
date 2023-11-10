package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import debug.Log;

public class ModelPlayer {
    private String database = "DYWTPN";
    private String hostname = "localhost";
    private String port = "3306";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private String username = "root";
    private String password = "123456";
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;

    public DefaultTableModel getActivities(String gameName) {
	DefaultTableModel m = new DefaultTableModel();
	m.addColumn("Actividad");

	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    if(gameName == "Todos") rs = stmt.executeQuery("SELECT description FROM player_activities ORDER BY id DESC");
	    else rs = stmt.executeQuery("SELECT description FROM player_activities WHERE game_name = '" + gameName + "' ORDER BY id DESC");

	    while(rs.next()) {
		Object[] f = new Object[1];
		for(int i = 0; i < 1; i++) {
		    f[i] = rs.getObject(i+1);
		}
		m.addRow(f);
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear("SQLException en DefaultTableModel getActivities(String gameName)");
	    ex.printStackTrace();
	}
	return m;
    }

    public DefaultTableModel getHistory(String gameName) {
	DefaultTableModel m = new DefaultTableModel();
	m.addColumn("Fecha");
	m.addColumn("Nombre");
	m.addColumn("Tiempo jugado");

	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    if(gameName == "Todos") rs = stmt.executeQuery("SELECT date_format(datetime, \"%d/%m/%Y\") as Fecha, game_name, ROUND((mins / 60),2) FROM `games_sessions_history` ORDER BY id DESC");
	    else rs = stmt.executeQuery("SELECT date_format(datetime, \"%d/%m/%Y\") as Fecha, game_name, ROUND((mins / 60),2) FROM `games_sessions_history` WHERE game_name = '" + gameName + "' ORDER BY id DESC");

	    while(rs.next()) {
		Object[] f = new Object[3];
		for(int i = 0; i < 3; i++) {
		    f[i] = rs.getObject(i+1);
		}
		m.addRow(f);
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear("SQLException en DefaultTableModel getHistory(String gameName)");
	    ex.printStackTrace();
	}
	return m;
    }

    public void saveAchievement(String achievement, String gamename, int gameid) {
	String query = "INSERT INTO player_activities (game_name, description, game_id) VALUES (?,?,?)";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);	    
	    p.setString(1, gamename);
	    p.setString(2, achievement);
	    p.setInt(3, gameid);
	    p.executeUpdate();
	    conex.close();
	    p.close();
	} catch (SQLException ex) {
	    Log.Loguear("SQLException en void saveAchievement(String achievement, String gamename)");
	    ex.printStackTrace();
	}
    }

    public String getLastAchievement() {
	String s = "Ninguna";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery("SELECT description AS descr FROM player_activities ORDER BY description ASC LIMIT 1");
	    if(rs.next()) {
		s = rs.getString(1);
	    }
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return s;
    }
}
