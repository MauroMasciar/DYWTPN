package database;

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
import debug.Log;
import frontend.MainUI;

public class ModelGames {
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;
    private ArrayList<String> gameName = new ArrayList<>();

    public int newSession(int gameId) {
	String query = "SELECT play_count FROM games WHERE id = " + gameId;
	int play_count = 0;
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return play_count;
    }

    public DefaultTableModel getFilteredGameList(String name, String completed, String category) {
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

	if(completed == "Todos") comp = 2;
	else if (completed == "Completados") comp = 1;

	String query = "";

	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    Statement sstmt = conex.createStatement();

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
			+ getCategoryIdFromName(category) + " ORDER BY name";
	    } else if(category != "Todos" && comp != 2) {
		query = "SELECT name, ghost, ROUND(((time_played / 60)/60),2), play_count, category, completed, score, path FROM games WHERE category = "
			+ getCategoryIdFromName(category) + " AND completed = " + comp + " ORDER BY name";
	    }

	    ResultSet rrs = sstmt.executeQuery(query);

	    while(rrs.next()) {
		Object[] f = new Object[8];
		for (int i = 0; i < 8; i++) {
		    f[i] = rrs.getObject(i + 1);
		}
		m.addRow(f);
	    }
	    conex.close();
	    sstmt.close();
	    rrs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return minutesplayed;
    }

    public int getSecondsPlayed(int gameId) {
	String query = "SELECT time_played FROM games WHERE id = " + gameId;
	int time_played = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		time_played = rs.getInt("time_played");
	    } else {
		time_played = 0;
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return time_played;
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return minutes;
    }

    public int getSecondsTotalPlayed() {
	String query = "SELECT SUM(time_played) AS seconds FROM games";
	int seconds = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		seconds = rs.getInt("seconds");
	    } else {
		seconds = 0;
	    }
	    stmt.close();
	    conex.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return seconds;
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return play_count;
    }

    public int getLastDays(int gameId, int days, boolean hours) {
	int mins = 0;
	String query;
	if (gameId == 0) {
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
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		mins = rs.getInt("minutes");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	if(hours) mins = mins * 60;
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
		Log.Loguear(ex.getMessage());
		ex.printStackTrace();
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
	    if(rs.next()) {
		name = rs.getString("name");
	    } else {
		name = "ERROR";
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	    name = "ERROR";
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
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
	    if(rs.next()) {
		dateLastSession = rs.getString("Fecha");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	    dateLastSession = "ERROR";
	}
	return dateLastSession;
    }

    public ArrayList<String> getCategoryList() {
	ArrayList<String> category = new ArrayList<>();
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
	    Log.Loguear(ex.getMessage());
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
	    Log.Loguear(ex.getMessage());
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
	    if(rs.next()) {
		ghost = rs.getInt("ghost");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	if (ghost == 1) return true;
	return false;
    }

    public boolean isCompleted(int gameId) {
	String query = "SELECT completed FROM games WHERE id = " + gameId;
	int completed = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		completed = rs.getInt("completed");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	if(completed == 1) return true;
	return false; 
    }

    public boolean isHidden(int gameId) {
	String query = "SELECT hidden FROM games WHERE id = " + gameId;
	int hidden = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		hidden = rs.getInt("hidden");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	if (hidden == 1) return true;
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
		Log.Loguear(ex.getMessage());
		ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return r;
    }

    public void setLastPlayed(int gameId) {
	try {
	    String query = "UPDATE games SET last_played = ? WHERE id = ?";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, Utils.getFormattedDateTime());
	    p.setInt(2, gameId);
	    p.executeUpdate();

	    p.close();
	    conex.close();
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "No se ha podido guardar la sesion\n" + ex.getMessage(), "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
    }

    public int addSessionGame(int gameId, String name, int minsPlayed, String date) {
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
	    secs += minsPlayed * 60;
	    int r = setTimePlayed(gameId, secs);
	    if(r == 0) {
		JOptionPane.showMessageDialog(null, "No se ha podido sumar el tiempo jugado", "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
	    }
	    p.close();
	    conex.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	    return 0;
	}
	return resultado;
    }

    public int addGame(String name, int gameTime, String path, String ghost, int playCount, String completed,
	    int score, int category, int hide, int favorite, int broken, int portable, String releasedate,
	    String rating, String genre, String platform, String developer, String publisher, String series,
	    String region, String playMode, String version, String status, String lastPlayed,
	    String added, String modified, String completed_date, int library, String notes) {
	try {
	    String query = "INSERT INTO games (name, time_played, path, ghost, play_count, completed, score, category, hidden, favorite, broken, portable, release_date, "
		    + "rating, genre, platform, developer, publisher, series, region, play_mode, version, status, last_played, added, modified, completed_date, library, notes) "
		    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setInt(2, gameTime);
	    p.setString(3, path);
	    p.setString(4, ghost);
	    p.setInt(5, playCount);
	    p.setString(6, completed);
	    p.setInt(7, score);
	    p.setInt(8, category);
	    p.setInt(9, hide);
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
	    p.setString(24, lastPlayed);
	    p.setString(25, added);
	    p.setString(26, modified);
	    p.setString(27, completed_date);
	    p.setInt(28, library);
	    p.setString(29, notes);

	    int resultado = p.executeUpdate();
	    conex.close();
	    p.close();
	    return resultado;
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return 0;
    }

    public int editGame(int gameId, String name, int secondsPlayed, String path, String ghost, int playCount,
	    String completed, int score, int category, int hidden, int favorite, int broken, int portable,
	    String releasedate, String rating, String genre, String platform, String developer, String publisher,
	    String series, String region, String playMode, String version, String status,
	    String lastPlayed, String completed_date, int library, String notes) {
	try {
	    String query;

	    if(completed == "1" && !isCompleted(gameId)) {
		ModelPlayer mp = new ModelPlayer();
		String gameName = getNameFromId(gameId);
		String achievement = "Has terminado el juego " + gameName;
		mp.saveAchievement(achievement, gameName, gameId);
		MainUI.loadData();
		if(completed_date.equals("0000-00-00")) completed_date = Utils.getFormattedDate();
	    }
	    if(completed.equals("0")) completed_date = "0000-00-00";

	    query = "UPDATE games SET name = ?, time_played = ?, path = ?, ghost = ?, play_count = ?, completed = ?, score = ?, category = ?, hidden = ?, "
		    + "favorite = ?, broken = ?, portable = ?, release_date = ?, rating = ?, genre = ?, platform = ?, developer = ?, publisher = ?, series = ?, "
		    + "region = ?, play_mode = ?, version = ?, status = ?, last_played = ?, modified = ?, completed_date = ?, library = ?, notes = ? WHERE id = ?";

	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setInt(2, secondsPlayed);
	    p.setString(3, path);
	    p.setString(4, ghost);
	    p.setInt(5, playCount);
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
	    p.setString(24, lastPlayed);
	    p.setString(25, Utils.getFormattedDateTime());
	    p.setString(26, completed_date);
	    p.setInt(27, library);
	    p.setString(28, notes);
	    p.setInt(29, gameId);

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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
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
	    Log.Loguear(ex.getMessage());
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
	    if(rs.next()) {
		category = rs.getInt("category");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    if(rs.next()) {
		category = rs.getString("name_category");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    if(rs.next()) {
		res = rs.getString("added");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
    }

    public String getCompletedDate(int gameId) {
	String query = "SELECT completed_date FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		res = rs.getString("completed_date");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
    }
    
    public String getNotes(int gameId) {
	String query = "SELECT notes FROM games WHERE id = " + gameId;
	String res = "";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		res = rs.getString("notes");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
    }

    public int getNumberCompletedGames() {
	String query = "SELECT name FROM games WHERE completed = 1";
	int res = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    while(rs.next()) {
		res++;
	    }
	} catch(SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
    }

    public int getTotalSessions() {
	String query = "SELECT SUM(play_count) AS total_sessions FROM games";
	int res = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		res = rs.getInt("total_sessions");
	    }
	} catch(SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
    }
    
    public int getTotalGames() {
	String query = "SELECT count(name) AS total FROM games";
	int res = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		res = rs.getInt("total");
	    }
	} catch(SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
    }

    public int getCountGamesPlayed() {
	String query = "SELECT count(time_played) AS games_played FROM games WHERE time_played > 60";
	int res = 0;
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		res = rs.getInt("games_played");
	    }
	} catch(SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return res;
    }
    
    public ArrayList<String> getLibraryList() {
	ArrayList<String> library = new ArrayList<>();
	String query = "SELECT * FROM library ORDER BY id";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    while(rs.next()) {
		library.add(rs.getString("name"));
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return library;
    }

    public int addLibrary(String name) {
	int resultado = 0;
	try {
	    String query = "INSERT INTO library (name) VALUES (?)";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    resultado = p.executeUpdate();
	    conex.close();
	    p.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return resultado;
    }
    
    public int editLibrary(String oldName, String newName) {
	int resultado = 0;
	try {
	    String query = "UPDATE library SET name = ? WHERE name = ?";
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, newName);
	    p.setString(2, oldName);
	    resultado = p.executeUpdate();
	    conex.close();
	    p.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return resultado;
    }

    public String getLibraryName(int gameId) {
	String query = "SELECT library.name FROM `games` inner join library on library.id = games.library where games.id = " + gameId;
	String library = "Ninguna";
	try {
	    conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		library = rs.getString("name");
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return library;
    }

    public int getLibraryIdFromName(String name) {
	String query = "SELECT id FROM library WHERE name = '" + name + "'";
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
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return id;
    }     
}
