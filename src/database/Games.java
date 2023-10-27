package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Games {
    private String database = "DYWTPN";
    private String hostname = "localhost";
    private String port = "3306";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private String username = "root";
    private String password = "123456";
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;
    private ArrayList<String> gameName = new ArrayList<String>();
    private ArrayList<Double> gameHoursPlayed = new ArrayList<Double>();
    private ArrayList<String> gamePath = new ArrayList<String>();

    public Games() {
	try {
	    conex = DriverManager.getConnection(url, username, password);
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	gameName.add("null");
	gameHoursPlayed.add(0.0);
	gamePath.add("null");
	String query = "SELECT name, hours_played, path FROM games";
	Conn c = new Conn();
	try {
	    stmt = c.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    while (rs.next()) {
		gameName.add(rs.getString("name"));
		gameHoursPlayed.add(rs.getDouble("hours_played"));
		gamePath.add(rs.getString("path"));
	    }
	    c.conex.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
    }

    public String getPathFromGame(int gameid) {
	String path = gamePath.get(gameid);
	System.out.println(path);
	return path;
    }

    public double getHoursPlayed(int gameid) {
	double hoursplayed = gameHoursPlayed.get(gameid);
	return hoursplayed;
    }

    public ArrayList<String> getGamesNameList() {
	return gameName;
    }

    public int getIdFromGameName(String name) {
	String query = "SELECT id FROM games WHERE name = '" + name + "'";
	Conn c = new Conn();
	try {
	    stmt = c.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		return rs.getInt("id");
	    } else
		return 0;
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return 0;
    }
    public String getNameFromId(int gameId) {
	String query = "SELECT name FROM games WHERE id = " + gameId;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) return rs.getString("name");
	    else return "ERROR";
	} catch (SQLException ex) {
	    ex.printStackTrace();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return "ERROR";
    }

    public void saveGameTime(int gameId, int sessionPlayed) {
	Conn c = new Conn();
	String query = "SELECT hours_played FROM games WHERE id = '" + gameId + "'";
	System.out.println(query);
	try {
	    Statement stmt;
	    stmt = c.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		try {
		    double hoursPlayed = rs.getDouble(1);
		    double sessionHoursPlayed = (double)sessionPlayed / 60;
		    double totalHoursPlayed = (double)hoursPlayed + sessionHoursPlayed;
		    System.out.println("Horas jugadas anteriormente: " + hoursPlayed + " Horas de ultima sesion: " + sessionHoursPlayed + " Total: " + totalHoursPlayed);
		    query = "UPDATE games SET hours_played = " + totalHoursPlayed + " WHERE id = " + gameId;
		    stmt.execute(query);
		    System.out.println(query);
		    query = "INSERT INTO games_sessions_history (hours, game_id) VALUES (" + sessionHoursPlayed + ", " + gameId + ")";
		    stmt.execute(query);
		    System.out.println(query);
		} catch (Exception ex) {
		    System.out.println(ex.getMessage());
		}
	    } else {
		String string = "No se ha podido guardar el tiempo jugado. Puede sumarlo manualmente\nUltima sesion: "
			+ sessionPlayed + " minutos.";
		JOptionPane.showMessageDialog(null, string, "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
	    }
	} catch (Exception ex) {
	    ex.getMessage();
	}
    }
    public int addGame(String name, String hoursPlayed, String path) {
	try {
	    String query = "INSERT INTO games (name, hours_played, path) VALUES (?,?,?)";
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setString(2, hoursPlayed);
	    p.setString(3, path);
	    int resultado = p.executeUpdate();
	    if(resultado == 1) return 1;
	    else return 0;
	} catch (SQLException ex) {
	    System.out.println("No se ha podido conectar a la BD");
	}
	return 0;
    }
    
    public int editGame(int gameId, String name, String hoursPlayed, String path) {
	try {
	    String query = "UPDATE games SET name = ?, hours_played = ?, path = ? WHERE id = ?";
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setString(2, hoursPlayed);
	    p.setString(3, path);
	    p.setInt(4, gameId);
	    int res = p.executeUpdate();
	    if(res == 1) {
		
	    } else {
		
	    }
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return 1;
    }
}
