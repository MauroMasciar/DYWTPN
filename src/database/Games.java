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
    private ArrayList<Double> gameMinsPlayed = new ArrayList<Double>();
    private ArrayList<String> gamePath = new ArrayList<String>();
    private ArrayList<Integer> gameGhost = new ArrayList<Integer>();

    public Games() {
	try {
	    conex = DriverManager.getConnection(url, username, password);
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	gameName.add("null");
	gameMinsPlayed.add(0.0);
	gamePath.add("null");
	gameGhost.add(0);
	String query = "SELECT name, mins_played, path, ghost FROM games";
	Conn c = new Conn();
	try {
	    stmt = c.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    while (rs.next()) {
		gameName.add(rs.getString("name"));
		gameMinsPlayed.add(rs.getDouble("mins_played")/60);
		gamePath.add(rs.getString("path"));
		gameGhost.add(rs.getInt("ghost"));
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

    public double getMinsPlayed(int gameid) {
	double MinsPlayed = gameMinsPlayed.get(gameid);
	return MinsPlayed;
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

    public boolean isGhost(int gameId) {
	if(gameGhost.get(gameId) == 1) return true;
	else return false;
    }

    public void saveGameTime(int gameId) {
	if(gameId != 0) {
	    Conn c = new Conn();
	    String query = "SELECT mins_played FROM games WHERE id = '" + gameId + "'";
	    System.out.println(query);
	    try {
		Statement stmt;
		stmt = c.conex.createStatement();
		rs = stmt.executeQuery(query);
		if (rs.next()) {
		    try {
			int MinsPlayed = rs.getInt(1);
			int totalMinsPlayed = MinsPlayed + 1;
			query = "UPDATE games SET mins_played = " + totalMinsPlayed + " WHERE id = " + gameId;
			stmt.execute(query);
			System.out.println(query);
		    } catch (Exception ex) {
			System.out.println(ex.getMessage());
		    }
		} else {
		    JOptionPane.showMessageDialog(null, "No se ha podido guardar el tiempo jugado", "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
		}
	    } catch (Exception ex) {
		ex.getMessage();
	    }
	}
    }

    public int addGame(String name, String MinsPlayed, String path, int ghost) {
	try {
	    String query = "INSERT INTO games (name, mins_played, path, ghost) VALUES (?,?,?,?)";
	    PreparedStatement p = conex.prepareStatement(query);
	    double hoursPlayed = Double.parseDouble(MinsPlayed) * 60;
	    p.setString(1, name);
	    p.setString(2, String.valueOf(hoursPlayed));
	    p.setString(3, path);
	    p.setInt(4, ghost);
	    int resultado = p.executeUpdate();
	    if(resultado == 1) return 1;
	    else return 0;
	} catch (SQLException ex) {
	    System.out.println("No se ha podido conectar a la BD");
	    ex.getMessage();
	}
	return 0;
    }

    public int editGame(int gameId, String name, String MinsPlayed, String path, String ghost) {
	try {
	    String query = "UPDATE games SET name = ?, mins_played = ?, path = ?, ghost = ? WHERE id = ?";
	    double hoursPlayed = Double.parseDouble(MinsPlayed) * 60;
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setString(2, String.valueOf(hoursPlayed));
	    p.setString(3, path);
	    p.setString(4, ghost);
	    p.setInt(5, gameId);
	    int res = p.executeUpdate();
	    if(res == 1) return 1;
	    else return 0;
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return 1;
    }
}
