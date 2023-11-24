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

import backend.Utils;

public class ModelGames {
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;
    private ArrayList<String> gameName = new ArrayList<String>();

    public void newSession(int gameId) {
	String query = "SELECT play_count FROM games WHERE id = " + gameId;
	int play_count;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    rs.next();
	    play_count = rs.getInt("play_count");
	    play_count++;
	    query = "UPDATE games SET play_count = " + play_count + " WHERE id = " + gameId;
	    stmt.execute(query);
	    stmt.close();
	    rs.close();
	    conex.close();
	} catch (Exception ex) {
	    Log.Loguear("SQLException en ModelGames.ModelGames(int gameId)");
	}
    }

    public DefaultTableModel getFilteredGameList(String name, String completed, String category) { // TODO: No filtra por categorias con la nueva version de la bd
	DefaultTableModel m = new DefaultTableModel();
	m.addColumn("Juego");
	m.addColumn("Fantasma");
	m.addColumn("Tiempo");
	m.addColumn("Veces ejecutado");
	m.addColumn("Categoria");
	m.addColumn("Completado");
	m.addColumn("Puntuacion");
	m.addColumn("Path");
	int comp = 0;
	if(completed == "Todos")
	    comp = 2;
	else if (completed == "Completados")
	    comp = 1;
	String query = "";

	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();

	    if(name == "Todos" && comp == 2 && category == "Todos") {
		query = "SELECT name, ghost, ROUND(((time_played / 60)/60),2), play_count, category.name_category, completed, score, path FROM `games` INNER JOIN category ON category.id = games.category ORDER BY name";
	    } else if(name == "Todos" && comp != 2 && category == "Todos") {
		query = "SELECT name, ghost, ROUND(((time_played / 60)/60),2), play_count, category.name_category, completed, score, path FROM `games` INNER JOIN category ON category.id = games.category WHERE completed = "
			+ comp + " ORDER BY name";
	    } else if(name != "Todos") {
		query = "SELECT name, ghost, ROUND(((time_played / 60)/60),2), play_count, category.name_category, completed, score, path FROM `games` INNER JOIN category ON category.id = games.category WHERE name = '"
			+ name + "'";
	    } else if(category != "Todos" && comp == 2) {
		query = "SELECT name, ghost, ROUND(((time_played / 60)/60),2), play_count, category, completed, score, path FROM games WHERE category = "
			+ category + " ORDER BY name";
	    } else if(category != "Todos" && comp != 2) {
		query = "SELECT name, ghost, ROUND(((time_played / 60)/60),2), play_count, category, completed, score, path FROM games WHERE category = "
			+ category + " AND completed = " + comp + " ORDER BY name";
	    }
	    rs = stmt.executeQuery(query);

	    while(rs.next()) {
		Object[] f = new Object[8];
		for (int i = 0; i < 8; i++) {
		    f[i] = rs.getObject(i + 1);
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
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		path = rs.getString("path");
	    } else {
		path = "ERROR";
	    }
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
	String query = "SELECT time_played FROM games WHERE id = " + gameId;
	int secondsPlayed = 0, minutesplayed = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		secondsPlayed = rs.getInt("time_played");
	    } else {
		secondsPlayed = 0;
	    }
	    minutesplayed = secondsPlayed / 60;
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return minutesplayed;
    }

    public int getSecondsPlayed(int gameId) {
	String query = "SELECT time_played FROM games WHERE id = " + gameId;
	int secondsPlayed = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		secondsPlayed = rs.getInt("time_played");
	    } else {
		secondsPlayed = 0;
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return secondsPlayed;
    }

    public int getMinutesTotalPlayed() {
	String query = "SELECT SUM(time_played) AS seconds FROM games";
	int seconds = 0, minutes = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		seconds = rs.getInt("seconds");
	    } else {
		seconds = 0;
	    }
	    minutes = seconds / 60;
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return minutes;
    }

    public int getPlayCount(int gameId) {
	String query = "SELECT play_count FROM games WHERE id = " + gameId;
	int play_count = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		play_count = rs.getInt("play_count");
	    } else {
		play_count = 0;
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return play_count;
    }

    public int getLastDays(int gameId, int days) {
	int mins = 0;
	String query;
	if (gameId == 0) {
	    if(days == 1)
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-1) AND now()";
	    else if(days == 7)
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-7) AND now()";
	    else if(days == 14)
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-14) AND now()";
	    else
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-30) AND now()";
	} else {
	    if(days == 1)
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-1) AND now() AND game_id = "
			+ gameId;
	    else if(days == 7)
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-7) AND now() AND game_id = "
			+ gameId;
	    else if(days == 14)
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-14) AND now() AND game_id = "
			+ gameId;
	    else
		query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime` BETWEEN adddate(now(),-30) AND now() AND game_id = "
			+ gameId;
	}
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		mins = rs.getInt("minutes");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return mins;
    }

    public ArrayList<String> getGamesNameList(boolean hidden) {
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    gameName.add("null");
	    String query;
	    if(hidden) {
		query = "SELECT name FROM games ORDER BY name";
	    } else {
		query = "SELECT name FROM games WHERE hidden = 0 ORDER BY name";
	    }
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
	return gameName;
    }

    public ArrayList<String> getGamesNameList(String name) {
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    gameName.add("null");
	    String query;
	    query = "SELECT name FROM `games` WHERE name LIKE '%" + name + "%' ORDER BY name";
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    while(rs.next()) {
		gameName.add(rs.getString("name"));
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    ex.getMessage();
	}
	return gameName;
    }

    public int getIdFromGameName(String name) {
	if (name != "") {
	    String query = "SELECT id FROM games WHERE name = '" + name + "'";
	    int id = 0;
	    try {
		conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
		stmt = conex.createStatement();
		rs = stmt.executeQuery(query);
		if(rs.next()) {
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
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		name = rs.getString("name");
	    else
		name = "ERROR";
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

	String query = "SELECT date_format(datetime, \"%d/%m/%Y\") as Fecha FROM `games_sessions_history` WHERE game_id = "
		+ gameId + " order by datetime desc limit 1";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		dateLastSession = rs.getString("Fecha");
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
	String query = "SELECT * FROM category ORDER BY id";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    while(rs.next()) {
		category.add(rs.getString("name_category"));
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return category;
    }

    public int getScore(int gameId) {
	int score = 0;
	String query = "SELECT score FROM games WHERE id = " + gameId;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		score = rs.getInt("score");
	    }
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
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		ghost = rs.getInt("ghost");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	if (ghost == 1)
	    return true;
	else
	    return false;
    }

    public boolean isCompleted(int gameId) {
	String query = "SELECT completed FROM games WHERE id = " + gameId;
	int completed = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		completed = rs.getInt("completed");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	if (completed == 1)
	    return true;
	else
	    return false;
    }

    public boolean isHidden(int gameId) {
	String query = "SELECT hidden FROM games WHERE id = " + gameId;
	int hidden = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		hidden = rs.getInt("hidden");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	if (hidden == 1)
	    return true;
	else
	    return false;
    }

    public void closeGame(int gameIdLaunched, int gameTimePlayed, String gameName, String sGameTimePlayed) {
	int minsPlayed = gameTimePlayed / 60;
	String query = "INSERT INTO games_sessions_history (game_id, mins, game_name) VALUES (" + gameIdLaunched + ","
		+ minsPlayed + ",'" + gameName + "')";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
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

    public void saveGameTime(int gameId) {
	if(gameId != 0) {
	    String query = "SELECT time_played FROM games WHERE id = '" + gameId + "'";
	    try {
		conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
		stmt = conex.createStatement();
		rs = stmt.executeQuery(query);
		if(rs.next()) {
		    try {
			int timePlayed = rs.getInt(1);
			int totalTimePlayed = timePlayed + 1;
			query = "UPDATE games SET time_played = " + totalTimePlayed + " WHERE id = " + gameId;
			stmt.execute(query);
			stmt.close();
			rs.close();
			conex.close();
		    } catch (Exception ex) {
			Log.Loguear(ex.getMessage());
		    }
		} else {
		    JOptionPane.showMessageDialog(null, "No se ha podido guardar el tiempo jugado",
			    "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
		}
	    } catch (Exception ex) {
		ex.getMessage();
	    }
	}
    }

    public int setTimePlayed(int gameId, int timePlayed) {
	int r = 0;
	try {
	    String query = "UPDATE games SET time_played = ? WHERE id = ?";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setInt(1, timePlayed);
	    p.setInt(2, gameId);
	    r = p.executeUpdate();

	    p.close();
	    conex.close();
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "No se ha podido guardar la sesion\n" + ex.getMessage(), "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
	}
	return r;
    }
    
    public void setLastPlayed(int gameId) {
	try {
	    String query = "UPDATE games SET last_played = ? WHERE id = ?";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, Utils.getDateTime());
	    p.setInt(2, gameId);
	    p.executeUpdate();

	    p.close();
	    conex.close();
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "No se ha podido guardar la sesion\n" + ex.getMessage(), "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
	}
    }

    public int addSessionGame(int gameId, String name, String minsPlayed, String date) {
	int resultado = 0;
	try {
	    String query = "INSERT INTO games_sessions_history (game_id, game_name, mins, datetime) VALUES (?,?,?,?)";
	    Log.Loguear(query);
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setInt(1, gameId);
	    p.setString(2, name);
	    p.setString(3, String.valueOf(minsPlayed));
	    p.setString(4, date);
	    resultado = p.executeUpdate();
	    int secs = getSecondsPlayed(gameId);
	    secs += Integer.parseInt(minsPlayed) * 60;
	    int r = setTimePlayed(gameId, secs);
	    if(r == 0) {
		JOptionPane.showMessageDialog(null, "No se ha podido sumar el tiempo jugado", "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
	    }
	    p.close();
	    conex.close();
	} catch (SQLException ex) {
	    return 0;
	}
	return resultado;
    }

    public int addGame(String name, int timePlayed, String path, int ghost, int completed, int category, int score) {
	try {
	    String query = "INSERT INTO games (name, time_played, path, ghost, completed, category, score) VALUES (?,?,?,?,?,?,?)";
	    Log.Loguear(query);
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setInt(2, timePlayed);
	    p.setString(3, path);
	    p.setInt(4, ghost);
	    p.setInt(5, completed);
	    p.setInt(6, category);
	    p.setInt(7, score);
	    int resultado = p.executeUpdate();
	    conex.close();
	    p.close();
	    return resultado;
	} catch (SQLException ex) {
	    Log.Loguear("SQLException en int ModelGames.addGame(String name, String MinsPlayed, String path, int ghost)" + ex.getMessage());
	    ex.getMessage();
	}
	return 0;
    }

    public int addCategory(String name) {
	int resultado = 0;
	try {
	    String query = "INSERT INTO category (name_category) VALUES (?)";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
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
	    String query = "UPDATE category SET name_category = ? WHERE name_category = ?";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, newName);
	    p.setString(2, oldName);
	    resultado = p.executeUpdate();
	    conex.close();
	    p.close();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
	return resultado;
    }

    public int getGameCategoryId(int gameId) {
	String query = "SELECT category FROM games WHERE id = " + gameId;
	int category = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		category = rs.getInt("category");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return category;
    }

    public String getGameCategoryName(int gameId) {
	String query = "SELECT category.name_category FROM `games` inner join category on category.id = games.category where games.id = "
		+ gameId;
	String category = "Ninguna";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next())
		category = rs.getString("name_category");
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return category;
    }

    public int getCategoryIdFromName(String name) {
	String query = "SELECT id FROM category WHERE name_category = '" + name + "'";
	int id = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		id = rs.getInt("id");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return id;
    }

    public String getReleaseDate(int gameId) {
	String query = "SELECT release_date FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		res = rs.getString("release_date");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getRating(int gameId) {
	String query = "SELECT rating FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("rating");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getGenre(int gameId) {
	String query = "SELECT genre FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("genre");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getPlatform(int gameId) {
	String query = "SELECT platform FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("platform");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getDeveloper(int gameId) {
	String query = "SELECT developer FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("developer");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getPublisher(int gameId) {
	String query = "SELECT publisher FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("publisher");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getSeries(int gameId) {
	String query = "SELECT series FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("series");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getRegion(int gameId) {
	String query = "SELECT region FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("region");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getPlayMode(int gameId) {
	String query = "SELECT play_mode FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("play_mode");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getVersion(int gameId) {
	String query = "SELECT version FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("version");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getStatus(int gameId) {
	String query = "SELECT status FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("status");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getSource(int gameId) {
	String query = "SELECT source FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("source");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getLastPlayed(int gameId) {
	String query = "SELECT last_played FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("last_played");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public boolean isFavorite(int gameId) {
	String query = "SELECT favorite FROM games WHERE id = " + gameId;
	boolean res = false;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getBoolean("favorite");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public boolean isBroken(int gameId) {
	String query = "SELECT broken FROM games WHERE id = " + gameId;
	boolean res = false;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getBoolean("broken");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public boolean isPortable(int gameId) {
	String query = "SELECT portable FROM games WHERE id = " + gameId;
	boolean res = false;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getBoolean("portable");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public String getAddedDate(int gameId) {
	String query = "SELECT added FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("added");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }
    
    public String getModified(int gameId) {
	String query = "SELECT modified FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if (rs.next()) {
		res = rs.getString("modified");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	return res;
    }

    public int editGame(int gameId, String name, int secondsPlayed, String path, String ghost, String play_count,
	    String completed, int score, int category, int hidden, int favorite, int broken, int portable,
	    String releasedate, String rating, String genre, String platform, String developer, String publisher,
	    String series, String region, String playMode, String version, String status, String source,
	    String lastPlayed) {
	try {
	    String query = "UPDATE games SET name = ?, time_played = ?, path = ?, ghost = ?, play_count = ?, completed = ?, score = ?, category = ?, hidden = ?, "
		    + "favorite = ?, broken = ?, portable = ?, release_date = ?, rating = ?, genre = ?, platform = ?, developer = ?, publisher = ?, series = ?, "
		    + "region = ?, play_mode = ?, version = ?, status = ?, source = ?, last_played = ?, modified = ? WHERE id = ?";    

	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setInt(2, secondsPlayed);
	    p.setString(3, path);
	    p.setString(4, ghost);
	    p.setString(5, play_count);
	    p.setString(6, completed);
	    p.setInt(7, score);
	    p.setInt(8, category);
	    p.setInt(9, hidden);
	    p.setInt(10, favorite);
	    p.setInt(11, broken);
	    p.setInt(12, portable);
	    p.setString(13, releasedate);
	    p.setString(14, rating);
	    p.setString(15, genre);
	    p.setString(16, platform);
	    p.setString(17, developer);
	    p.setString(18, publisher);
	    p.setString(19, series);
	    p.setString(20, region);
	    p.setString(21, playMode);
	    p.setString(22, version);
	    p.setString(23, status);
	    p.setString(24, source);
	    p.setString(25, lastPlayed);
	    p.setString(26, Utils.getDateTime());
	    p.setInt(27, gameId);

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
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
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
