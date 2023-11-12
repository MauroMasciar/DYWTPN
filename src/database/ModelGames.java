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
import javax.swing.table.DefaultTableModel;

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

    public ModelGames(int gameId) {
	String query = "SELECT times FROM games WHERE id = " + gameId;
	int times;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    rs.next();
	    times = rs.getInt("times");
	    times++;
	    query = "UPDATE games SET times = " + times + " WHERE id = " + gameId;
	    stmt.execute(query);
	    stmt.close();
	    rs.close();
	    conex.close();
	} catch (Exception ex) {
	    Log.Loguear("SQLException en ModelGames.ModelGames(int gameId)");
	}
    }

    public DefaultTableModel getFilteredGameList(String name, String completed, String category) {
	DefaultTableModel m = new DefaultTableModel();
	m.addColumn("Juego");
	m.addColumn("Fantasma");
	m.addColumn("Tiempo");
	m.addColumn("Veces ejecutado");
	m.addColumn("Categoria");
	m.addColumn("Completado");
	m.addColumn("Path");
	int comp;
	if(completed == "Todos") comp = 2;
	else if(completed == "Completados") comp = 1;
	else comp = 0;

	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();

	    if(name == "Todos" && comp == 2) {
		rs = stmt.executeQuery("SELECT name, ghost, ROUND((mins_played / 60),2), times, category, completed, path FROM games ORDER BY name");
	    } else if(name == "Todos" && comp != 2) {
		rs = stmt.executeQuery("SELECT name, ghost, ROUND((mins_played / 60),2), times, category, completed, path FROM games WHERE completed = " + comp + "  ORDER BY name");
	    } else if(name != "Todos") {
		rs = stmt.executeQuery("SELECT name, ghost, ROUND((mins_played / 60),2), times, category, completed, path FROM games WHERE name = '" + name + "' ORDER BY name");
	    }
	    while(rs.next()) {
		Object[] f = new Object[7];
		for(int i = 0; i < 7; i++) {
		    f[i] = rs.getObject(i+1);
		}
		m.addRow(f);
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return m;
    }

    public String getPathFromGame(int gameId) {
	String query = "SELECT path FROM games WHERE id = " + gameId;
	String path = "";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) path = rs.getString("path");
	    else path = "ERROR";
	    stmt.close();
	    rs.close();
	    conex.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return path;
    }

    public int getMinsPlayed(int gameId) {
	String query = "SELECT mins_played FROM games WHERE id = " + gameId;
	//String s = "getMinsPlayed(int " + gameId + ")";
	//Log.Loguear(s);
	int minutesplayed = 0;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) minutesplayed = rs.getInt("mins_played");
	    else minutesplayed = 0;
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return minutesplayed;
    }

    public int getMinutesTotalPlayed() {
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

    public int getTimes(int gameId) {
	String query = "SELECT times FROM games WHERE id = " + gameId;
	int times = 0;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) times = rs.getInt("times");
	    else times = 0;
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return times;
    }

    public int getLastDays(int gameId, int days) {
	int mins = 0;
	String query;
	if(gameId == 0) {
	    if(days == 1) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-1) AND now()";
	    else if(days == 7) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-7) AND now()";
	    else if(days == 14) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-14) AND now()";
	    else query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-30) AND now()";
	} else {
	    if(days == 1) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-1) AND now() AND game_id = " + gameId;
	    else if(days == 7) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-7) AND now() AND game_id = " + gameId;
	    else if(days == 14) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-14) AND now() AND game_id = " + gameId;
	    else query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-30) AND now() AND game_id = " + gameId;
	}
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) mins = rs.getInt("minutes");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}	
	return mins;
    }

    public ArrayList<String> getGamesNameList() {
	return gameName;
    }

    public int getIdFromGameName(String name) {
	if(name != "") {
	    String query = "SELECT id FROM games WHERE name = '" + name + "'";
	    //Log.Loguear(query);
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
		conex.close();
		stmt.close();
		rs.close();
	    } catch (Exception ex) {
		ex.getMessage();
	    }
	    return id;
	}
	return 0;
    }

    public String getNameFromId(int gameId) {
	String query = "SELECT name FROM games WHERE id = " + gameId;
	String name;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) name = rs.getString("name");
	    else name = "ERROR";
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	    name = "ERROR";
	} catch (Exception ex) {
	    ex.printStackTrace();
	    name = "ERROR";
	}
	return name;
    }

    public String getDateLastSession(int gameId) {
	String dateLastSession = "Nunca";

	String query = "SELECT date_format(datetime, \"%d/%m/%Y\") as Fecha FROM `games_sessions_history` WHERE game_id = " + gameId + " order by datetime desc limit 1";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) dateLastSession = rs.getString("Fecha");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	    dateLastSession = "ERROR";
	}
	return dateLastSession;
    }
    
    public ArrayList<String> getCategoryList() {
	ArrayList<String> category = new ArrayList<String>();
	String query = "SELECT * FROM category";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    while(rs.next()) {
		category.add(rs.getString("name"));
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return category;
    }
    
    /*public int getCategoryIdFromNameC(String name) {
	int categoryId = 0;
	String query = "SELECT * FROM category WHERE name = '" + name + "'";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) categoryId = rs.getInt("id");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}	
	return categoryId;
    }*/
    
    public int getScore(int gameId) {
	int score = 0;
	String query = "SELECT score FROM games WHERE id = " + gameId;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) score = rs.getInt("score");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return score;
    }

    public boolean isGhost(int gameId) {
	String query = "SELECT ghost FROM games WHERE id = " + gameId;
	int ghost = 1;
	//Log.Loguear(query);
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) ghost = rs.getInt("ghost");
	    conex.close();
	    stmt.close();
	    rs.close();	    
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	if(ghost == 1) return true;
	else return false;
    }

    public boolean isCompleted(int gameId) {
	String query = "SELECT completed FROM games WHERE id = " + gameId;
	int completed = 0;
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) completed = rs.getInt("completed");
	    conex.close();
	    stmt.close();
	    rs.close();	 
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	if(completed == 1) return true;
	else return false;
    }

    public void closeGame(int gameIdLaunched, int gameTimePlayed, String gameName, String sGameTimePlayed)  {
	if(gameTimePlayed >= 1) {
	    String query = "INSERT INTO games_sessions_history (game_id, mins, game_name) VALUES (" + gameIdLaunched + "," +  gameTimePlayed + ",'" + gameName + "')";
	    //Log.Loguear(query);
	    try {
		conex = DriverManager.getConnection(url, username, password);
		stmt = conex.createStatement();
		stmt.execute(query);
		String sGameName = " Ultimo juego: " + gameName;		
		query = "UPDATE config SET last_game = '" + sGameName + "', last_session_time = '" + sGameTimePlayed + "'";
		stmt.execute(query);
		stmt.close();
		conex.close();
	    } catch (SQLException ex) {
		Log.Loguear("SQLException en ModelGames.closeGame(int gameIdLaunched, int gameTimePlayed)");
	    }
	}
    }

    public void saveGameTime(int gameId) {
	if(gameId != 0) {
	    String query = "SELECT mins_played FROM games WHERE id = '" + gameId + "'";
	    //Log.Loguear(query);
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
			//Log.Loguear(query);
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

    public void setMinsPlayed(int gameId, int minsPlayed) {
	try {
	    String query = "UPDATE games SET mins_played = ? WHERE id = ?";
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setInt(1, minsPlayed);
	    p.setInt(2, gameId);
	    p.executeUpdate();

	    p.close();
	    conex.close();
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "No se ha podido guardar la sesion", "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
	}
    }

    public int addSessionGame(int gameId, String name, String minsPlayed, String date) {
	int resultado = 0;
	try {
	    String query = "INSERT INTO games_sessions_history (game_id, game_name, mins, datetime) VALUES (?,?,?,?)";
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setInt(1, gameId);
	    p.setString(2, name);
	    p.setString(3, String.valueOf(minsPlayed));
	    p.setString(4, date);
	    resultado = p.executeUpdate();
	    int mins = getMinsPlayed(gameId);
	    mins += Integer.parseInt(minsPlayed);
	    setMinsPlayed(gameId, mins);
	    p.close();
	    conex.close();
	} catch (SQLException ex) {
	    return 0;
	}
	return resultado;	
    }

    public int addGame(String name, String MinsPlayed, String path, int ghost, int completed, String category, int score) {
	try {
	    String query = "INSERT INTO games (name, mins_played, path, ghost, completed, category, score) VALUES (?,?,?,?,?,?,?)";
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);
	    double hoursPlayed = Double.parseDouble(MinsPlayed) * 60;
	    p.setString(1, name);
	    p.setString(2, String.valueOf(hoursPlayed));
	    p.setString(3, path);
	    p.setInt(4, ghost);
	    p.setInt(5, completed);
	    p.setString(6, category);
	    p.setInt(7, score);
	    int resultado = p.executeUpdate();
	    conex.close();
	    p.close();
	    return resultado;
	} catch (SQLException ex) {
	    Log.Loguear("SQLException en int ModelGames.addGame(String name, String MinsPlayed, String path, int ghost)");
	    ex.getMessage();
	}
	return 0;
    }
    
    public int addCategory(String name) {
	int resultado = 0;
	try {
	    String query = "INSERT INTO category (name) VALUES (?)";
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    resultado = p.executeUpdate();
	    conex.close();
	    p.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return resultado;
    }
    
    public int editCategory(String oldName, String newName) {
	int resultado = 0;
	try {
	    String query = "UPDATE category SET name = ? WHERE name = ?";
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, newName);
	    p.setString(2, oldName);
	    resultado = p.executeUpdate();
	    query = "UPDATE games SET category = ? WHERE category = ?";
	    p.setString(1, newName);
	    p.setString(2, oldName);
	    p.executeUpdate();
	    conex.close();
	    p.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return resultado;
    }
    
    public String getGameCategory(int gameId) {
	String query = "SELECT category FROM games WHERE id = " + gameId;
	String category = "";	
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) category = rs.getString("category");
	    conex.close();
	    stmt.close();
	    rs.close();	 
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return category;
    }
    
    public int editGame(int gameId, String name, String minsPlayed, String path, String ghost, String times, String completed, int score, String category) {
	try {
	    String query = "UPDATE games SET name = ?, mins_played = ?, path = ?, ghost = ?, times = ?, completed = ?, score = ?, category = ? WHERE id = ?";
	    double hoursPlayed = Double.parseDouble(minsPlayed) * 60;
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setString(2, String.valueOf(hoursPlayed));
	    p.setString(3, path);
	    p.setString(4, ghost);
	    p.setString(5, times);
	    p.setString(6, completed);
	    p.setInt(7, score);
	    p.setString(8, category);
	    p.setInt(9, gameId);
	    int res = p.executeUpdate();
	    query = "UPDATE games_sessions_history SET game_name = ? WHERE game_id = ?";
	    p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setInt(2, gameId);
	    p.executeUpdate();
	    query = "UPDATE player_activities SET game_name = ? WHERE game_id = ?";
	    p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setInt(2, gameId);
	    p.executeUpdate();
	    p.close();
	    stmt.close();
	    conex.close();
	    return res;
	} catch (SQLException ex) {
	    Log.Loguear("SQLException en int ModelGames.editGame(int gameId, String name, String MinsPlayed, String path, String ghost)");
	    ex.printStackTrace();
	}
	return 0;
    }

    public int deleteGame(int gameId) {
	int res = 0;
	try {
	    String query = "DELETE FROM games WHERE id = ?";
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setInt(1, gameId);
	    res = p.executeUpdate();

	    query = "DELETE FROM games_sessions_history WHERE game_id = ?";
	    p = conex.prepareStatement(query);
	    p.setInt(1, gameId);
	    p.executeUpdate();

	    query = "DELETE FROM player_activities WHERE game_id = ?";
	    p = conex.prepareStatement(query);
	    p.setInt(1, gameId);
	    p.executeUpdate();

	    p.close();
	    conex.close();
	} catch (Exception ex) {
	    Log.Loguear("SQLException en int ModelGames.deleteGame(int gameId)");
	    ex.printStackTrace();
	}
	return res;
    }
}
