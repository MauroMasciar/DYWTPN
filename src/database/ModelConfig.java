package database;

import debug.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelConfig {
	private Connection conex = null;
	private static Statement stmt;
	private static ResultSet rs;

	public int truncateData() {
		try {
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();

			String query = "TRUNCATE category";
			stmt.execute(query);

			query = "TRUNCATE config";
			stmt.execute(query);

			query = "TRUNCATE games";
			stmt.execute(query);

			query = "TRUNCATE games_sessions_history";
			stmt.execute(query);

			query = "TRUNCATE library";
			stmt.execute(query);

			query = "TRUNCATE player_activities";
			stmt.execute(query);

			query = "INSERT INTO config (name) VALUES ('Usuario')";
			stmt.execute(query);

			query = "INSERT INTO category (name_category) VALUES ('Ninguna')";
			stmt.execute(query);

			query = "INSERT INTO `dywtpn`.`library` (`id`, `name`) VALUES (NULL, 'Ninguna');";
			stmt.execute(query);

			stmt.close();
			conex.close();
			Log.Loguear("Datos borrados");
			return 1;
		} catch (SQLException ex) {
			Log.Loguear(ex.getMessage());
		}
		return 0;
	}

	public String getUsername() {
		String query = "SELECT name FROM config";
		String name = "";
		try {
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
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
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
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
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
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
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				sH = rs.getInt("show_hidden");
			}
			stmt.close();
			conex.close();
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(sH == 1)
			return true;
		else
			return false;
	}

	public void setIsHidden(int args) {
		String query = "UPDATE config SET show_hidden = " + args;
		try {
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			stmt.execute(query);
			stmt.close();
			conex.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveUserName(String newName) {
		String query = "UPDATE config SET name = '" + newName + "';";
		try {
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			stmt.execute(query);
			stmt.close();
			conex.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getBounds_x(String window) {
		int x = 30;
		String query = "";
		try {
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			if(window.equals("MainUI")) query = "SELECT MainUI_x FROM config";
			else if(window.equals("Activity")) query = "SELECT Activity_x FROM config";
			else if(window.equals("History")) query = "SELECT History_x FROM config";

			rs = stmt.executeQuery(query);
			if(rs.next()) x = rs.getInt(1);
			stmt.close();
			conex.close();
			rs.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}

	public int getBounds_y(String window) {
		int y = 30;
		String query = "";
		try {
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			stmt = conex.createStatement();
			if(window.equals("MainUI")) query = "SELECT MainUI_y FROM config";
			else if(window.equals("Activity")) query = "SELECT Activity_y FROM config";
			else if(window.equals("History")) query = "SELECT History_y FROM config";

			rs = stmt.executeQuery(query);
			if(rs.next()) y = rs.getInt(1);
			stmt.close();
			conex.close();
			rs.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return y;
	}

	public void setSavedBounds(String window, double d, double e) {
		String query = "";
		int x = (int)d;
		int y = (int)e;
		if(window.equals("MainUI")) query = "UPDATE config SET MainUI_x = ?, MainUI_y = ?";	
		if(window.equals("Activity")) query = "UPDATE config SET Activity_x = ?, Activity_y = ?";
		if(window.equals("History")) query = "UPDATE config SET History_x = ?, History_y = ?";

		try {
			conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
			PreparedStatement p = conex.prepareStatement(query);
			p.setInt(1, x);
			p.setInt(2, y);
			p.executeUpdate();
			conex.close();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
