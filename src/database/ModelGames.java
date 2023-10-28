package database;

import debug.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ModelGames {
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

	public ModelGames() {
		try {
			conex = DriverManager.getConnection(url, username, password);
			gameName.add("null");
			String query = "SELECT name FROM games";
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				gameName.add(rs.getString("name"));
			}
			conex.close();
			stmt.close();
			rs.close();
		} catch (Exception ex) {
			ex.getMessage();
		}
	}

	public String getPathFromGame(int gameId) {
		String query = "SELECT path FROM games WHERE id = " + gameId;
		try {
			conex = DriverManager.getConnection(url, username, password);
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) return rs.getString("path");
			else return "ERROR";
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "ERROR";
	}

	public double getMinsPlayed(int gameId) {
		String query = "SELECT mins_played FROM games WHERE id = " + gameId;
		String s = "getMinsPlayed(int " + gameId + ")";
		Log.Loguear(s);
		int minutesplayed = 0;
		try {
			conex = DriverManager.getConnection(url, username, password);
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				minutesplayed = rs.getInt("mins_played");
			} else
				minutesplayed = 0;
		} catch (Exception ex) {
			ex.getMessage();
		}
		query = "minutosplayed=" + minutesplayed;
		return minutesplayed;

	}

	public ArrayList<String> getGamesNameList() {
		return gameName;
	}

	public int getIdFromGameName(String name) {
		if(name != "") {
			String query = "SELECT id FROM games WHERE name = '" + name + "'";
			Log.Loguear(query);
			int id = 0;
			try {
				conex = DriverManager.getConnection(url, username, password);
				stmt = conex.createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					id = rs.getInt("id");
				} else {
					id = 0;
				}
			} catch (Exception ex) {
				ex.getMessage();
			}
			return id;
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
		String query = "SELECT ghost FROM games WHERE id = " + gameId;
		int ghost = 1;
		Log.Loguear(query);
		try {
			conex = DriverManager.getConnection(url, username, password);
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) ghost = rs.getInt("ghost");
			if(ghost == 1) return true;
			else return false;
		} catch (SQLException ex) {
			ex.getMessage();
		}
		return false;
	}

	public void closeGame(int gameIdLaunched, int gameTimePlayed)  {
		if(gameTimePlayed >= 1) {
			String query = "INSERT INTO games_sessions_history (game_id, mins) VALUES (" + gameIdLaunched + "," +  gameTimePlayed + ")";
			Log.Loguear(query);
			try {
				conex = DriverManager.getConnection(url, username, password);
				stmt = conex.createStatement();
				stmt.execute(query);
				stmt.close();
				conex.close();
			} catch (SQLException ex) {
				Log.Loguear("SQLException en Games.closeGame(int gameIdLaunched, int gameTimePlayed)");
			}
		}
	}

	public void saveGameTime(int gameId) {
		if(gameId != 0) {
			String query = "SELECT mins_played FROM games WHERE id = '" + gameId + "'";
			Log.Loguear(query);
			try {
				conex = DriverManager.getConnection(url, username, password);
				stmt = conex.createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					try {
						int MinsPlayed = rs.getInt(1);
						int totalMinsPlayed = MinsPlayed + 1;
						query = "UPDATE games SET mins_played = " + totalMinsPlayed + " WHERE id = " + gameId;
						stmt.execute(query);
						Log.Loguear(query);
						stmt.close();
						rs.close();
						conex.close();
					} catch (Exception ex) {
						Log.Loguear(ex.getMessage());
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
			conex = DriverManager.getConnection(url, username, password);
			PreparedStatement p = conex.prepareStatement(query);
			double hoursPlayed = Double.parseDouble(MinsPlayed) * 60;
			p.setString(1, name);
			p.setString(2, String.valueOf(hoursPlayed));
			p.setString(3, path);
			p.setInt(4, ghost);
			int resultado = p.executeUpdate();
			conex.close();
			p.close();
			return resultado;
		} catch (SQLException ex) {
			Log.Loguear("SQLException en int addGame(String name, String MinsPlayed, String path, int ghost)");
			ex.getMessage();
		}
		return 0;
	}

	public int editGame(int gameId, String name, String MinsPlayed, String path, String ghost) {
		try {
			String query = "UPDATE games SET name = ?, mins_played = ?, path = ?, ghost = ? WHERE id = ?";
			double hoursPlayed = Double.parseDouble(MinsPlayed) * 60;
			conex = DriverManager.getConnection(url, username, password);
			PreparedStatement p = conex.prepareStatement(query);
			p.setString(1, name);
			p.setString(2, String.valueOf(hoursPlayed));
			p.setString(3, path);
			p.setString(4, ghost);
			p.setInt(5, gameId);
			int res = p.executeUpdate();
			p.close();
			conex.close();
			return res;
		} catch (SQLException ex) {
			Log.Loguear("SQLException en int editGame(int gameId, String name, String MinsPlayed, String path, String ghost)");
			ex.printStackTrace();
		}
		return 1;
	}
}
