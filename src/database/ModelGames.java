package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import backend.Utils;
import debug.Log;
import frontend.MainUI;

public class ModelGames {
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;

    public int saveSession(int gameId, int time, String date_start) {
        Log.Loguear("saveSession(int gameId, int time, String date_start)");
        String query = "SELECT play_count FROM games WHERE id = " + gameId;
        int play_count = 0, library_id = 0, platform_id = 0;
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
            
            String sGameTimePlayed = " Jugaste durante: " + Utils.getTotalHoursFromSeconds(time, true);
            String game_name = getNameFromId(gameId);
            String date_end = "";
            library_id = getLibraryIdFromGame(gameId);
            platform_id = getPlatformIdFromGame(gameId);
            
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date;
                date = format.parse(date_start);
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, time / 60);
                date_end = format.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            saveGameHistory(gameId, time, game_name, library_id, platform_id, date_start, date_end);
            saveGameTime(gameId, time);
            setLastPlayed(gameId);
            saveLastGame(game_name, sGameTimePlayed);
            addTimeLibrary(gameId, time);
            addSessionLibrary(gameId);
            addTimePlatform(gameId, time);
            addSessionPlatform(gameId);
            checkAchievement(gameId, play_count);
            
            MainUI.loadData(false, true);
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return play_count;
    }
    
    private void checkAchievement(int gameId, int play_count) {
        Log.Loguear("checkAchievement(int gameId, int play_count)");
        String achiev = "";
        if(play_count == 1) achiev = "Has jugado a " + getNameFromId(gameId) + " por primera vez";
        else if(play_count == 100) achiev = "Has alcanzado 100 sesiones en " + getNameFromId(gameId);
        else if(play_count == 250) achiev = "Has alcanzado 250 sesiones en " + getNameFromId(gameId);
        else if(play_count == 500) achiev = "Has alcanzado 500 sesiones en " + getNameFromId(gameId);
        else if(play_count == 1000) achiev = "Has alcanzado 1000 sesiones en " + getNameFromId(gameId);
        else if(play_count == 1500) achiev = "Has alcanzado 1500 sesiones en " + getNameFromId(gameId);
        else if(play_count == 2500) achiev = "Has alcanzado 2500 sesiones en " + getNameFromId(gameId);
        else if(play_count == 5000) achiev = "Has alcanzado 5000 sesiones en " + getNameFromId(gameId);
        else if(play_count == 7500) achiev = "Has alcanzado 7500 sesiones en " + getNameFromId(gameId);
        else if(play_count == 10000) achiev = "Has alcanzado 10000 sesiones en " + getNameFromId(gameId);

        if(achiev != "") {
            ModelPlayer mp = new ModelPlayer();
            mp.saveAchievement(achiev, getNameFromId(gameId), gameId);
            MainUI.loadAchievs();
        }

        achiev = "";
        int totalSessionCount = getTotalSessions();
        if(totalSessionCount != 0) {
            if(totalSessionCount == 100) achiev = "Has alcanzado 100 sesiones de juego";
            else if(totalSessionCount == 250) achiev = "Has alcanzado 250 sesiones de juego";
            else if(totalSessionCount == 500) achiev = "Has alcanzado 500 sesiones de juego";
            else if(totalSessionCount == 1000) achiev = "Has alcanzado 1000 sesiones de juego";
            else if(totalSessionCount == 1500) achiev = "Has alcanzado 1500 sesiones de juego";
            else if(totalSessionCount == 2500) achiev = "Has alcanzado 2500 sesiones de juego";
            else if(totalSessionCount == 5000) achiev = "Has alcanzado 5000 sesiones de juego";
            else if(totalSessionCount == 7500) achiev = "Has alcanzado 7500 sesiones de juego";
            else if(totalSessionCount == 10000) achiev = "Has alcanzado 10000 sesiones de juego";

            if(achiev != "") {
                ModelPlayer mp = new ModelPlayer();
                mp.saveAchievement(achiev, getNameFromId(gameId), gameId);
                MainUI.loadAchievs();
            }
        }
        
        achiev = "";
        int totalSessionCountLibrary = getLibraryTotalSessions(getLibraryIdFromGame(gameId));
        if(totalSessionCountLibrary != 0) {
            if(totalSessionCountLibrary == 100) achiev = "Has alcanzado 100 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 250) achiev = "Has alcanzado 250 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 500) achiev = "Has alcanzado 500 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 1000) achiev = "Has alcanzado 1000 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 1500) achiev = "Has alcanzado 1500 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 2500) achiev = "Has alcanzado 2500 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 5000) achiev = "Has alcanzado 5000 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 7500) achiev = "Has alcanzado 7500 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);
            else if(totalSessionCountLibrary == 10000) achiev = "Has alcanzado 10000 sesiones de juego en la biblioteca " + getLibraryNameFromGameId(gameId);

            if(achiev != "") {
                ModelPlayer mp = new ModelPlayer();
                mp.saveAchievement(achiev, getNameFromId(gameId), 0);
                MainUI.loadAchievs();
            }
        }
        
        achiev = "";
        int totalSessionCountPlatform = getPlatformTotalSessions(getPlatformIdFromGame(gameId));
        if(totalSessionCountPlatform != 0) {
            if(totalSessionCountPlatform == 100) achiev = "Has alcanzado 100 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 250) achiev = "Has alcanzado 250 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 500) achiev = "Has alcanzado 500 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 1000) achiev = "Has alcanzado 1000 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 1500) achiev = "Has alcanzado 1500 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 2500) achiev = "Has alcanzado 2500 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 5000) achiev = "Has alcanzado 5000 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 7500) achiev = "Has alcanzado 7500 sesiones de juego en la plataforma " + getGamePlatformName(gameId);
            else if(totalSessionCountPlatform == 10000) achiev = "Has alcanzado 10000 sesiones de juego en la plataforma " + getGamePlatformName(gameId);

            if(achiev != "") {
                ModelPlayer mp = new ModelPlayer();
                mp.saveAchievement(achiev, getNameFromId(gameId), 0);
                MainUI.loadAchievs();
            }
        }
    }
    
    public int changeGameName(int gameId, String newName) {
        Log.Loguear("changeGameName(int gameId, String newName)");
        String query = "SELECT name FROM games WHERE name = '" + newName + "'";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                stmt.close();
                rs.close();
                conex.close();
                return 3;
            }
            query = "UPDATE games SET name = '" + newName + "' WHERE id = " + gameId;
            stmt.execute(query);
            query = "UPDATE games_sessions_history SET game_name = '" + newName + "' WHERE game_id = " + gameId;
            stmt.execute(query);
            stmt.close();
            rs.close();
            conex.close();
            return 1;
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
            return 2;
        }
    }

    public DefaultTableModel getFilteredGameList(String name, String completed, String category, String filter, boolean lastPlayed) {
        Log.Loguear("getFilteredGameList(String name, String completed, String category, String filter, boolean lastPlayed)");
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("Juego");
        m.addColumn("Tiempo");
        m.addColumn("Veces");
        m.addColumn("Categoria");
        m.addColumn("Completado");
        m.addColumn("Puntos");
        m.addColumn("Biblioteca");
        int comp = 0;

        if(completed == "Todos") comp = 2;
        else if (completed == "Completados") comp = 1;

        String query = "";

        if(filter == "Tiempo") filter = "time_played DESC";
        else if(filter == "Veces") filter = "play_count DESC";
        else if(filter == "Categoria") filter = "category DESC";
        else if(filter == "Completado") filter = "completed DESC";
        else if(filter == "Puntos") filter = "score DESC";
        else filter = "name ASC"; 
        if(lastPlayed) filter = "last_played DESC, name ASC";

        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            Statement sstmt = conex.createStatement();

            if(name == "Todos" && comp == 2 && category == "Todos") {
                query = "SELECT name, time_played, play_count, category.name_category, completed, score, library FROM `games` INNER JOIN category ON category.id = games.category ORDER BY " + filter;
            } else if(name == "Todos" && comp != 2 && category == "Todos") {
                query = "SELECT name, time_played, play_count, category.name_category, completed, score, library FROM `games` INNER JOIN category ON category.id = games.category WHERE completed = "
                        + comp + " ORDER BY " + filter;
            } else if(name != "Todos") {
                query = "SELECT name, time_played, play_count, category.name_category, completed, score, library FROM `games` INNER JOIN category ON category.id = games.category WHERE name = '"
                        + name + "' ORDER BY " + filter;
            } else if(category != "Todos" && comp == 2) {
                query = "SELECT name, time_played, play_count, category, completed, score, library FROM games WHERE category = "
                        + getCategoryIdFromName(category) + " ORDER BY " + filter;
            } else if(category != "Todos" && comp != 2) {
                query = "SELECT name, time_played, play_count, category, completed, score, library FROM games WHERE category = "
                        + getCategoryIdFromName(category) + " AND completed = " + comp + " ORDER BY " + filter;
            }

            ResultSet rrs = sstmt.executeQuery(query);

            while(rrs.next()) {
                Object[] f = new Object[7];
                for (int i = 0; i < 7; i++) {
                    if(i == 1) {
                        f[i] = Utils.getTotalHoursFromSeconds(rrs.getInt(i + 1), true);
                    } else if(i == 4) {
                        if(rrs.getInt(i + 1) == 1) f[i] = "Si";
                        else f[i] = "No";
                    } else if(i == 6) {
                        f[i] = getLibraryNameFromGameId(getIdFromGameName(rrs.getString("name")));
                    } else {
                        f[i] = rrs.getObject(i + 1);
                    }
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
        Log.Loguear("getPathFromGame(int gameId)");
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
        Log.Loguear("getMinsPlayed(int gameId)");
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
        Log.Loguear("getSecondsPlayed(int gameId)");
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
        Log.Loguear("getMinutesTotalPlayed()");
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
        Log.Loguear("getSecondsTotalPlayed()");
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
        Log.Loguear("getPlayCount(int gameId)");
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

    public int getTimeLastSession(int gameId) {
        Log.Loguear("getTimeLastSession(int gameId)");
        String query = "SELECT mins FROM `games_sessions_history` WHERE game_id = " + gameId + " ORDER BY datetime_start DESC limit 1";
        int time = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                time = rs.getInt("mins");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return time;
    }

    public int getLastDays(int gameId, int days, boolean hours) {
        Log.Loguear("getLastDays(int gameId, int days, boolean hours)");
        int mins = 0;
        String query;
        if (gameId == 0) {
            if(days == 1) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-1) AND now()";
            else if(days == 7) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-7) AND now()";
            else if(days == 14) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-14) AND now()";
            else query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-30) AND now()";
        } else {
            if(days == 1) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-1) AND now() AND game_id = " + gameId;
            else if(days == 7) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-7) AND now() AND game_id = " + gameId;
            else if(days == 14) query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-14) AND now() AND game_id = " + gameId;
            else query = "SELECT SUM(mins) as minutes FROM games_sessions_history WHERE `datetime_start` BETWEEN adddate(now(),-30) AND now() AND game_id = " + gameId;
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

    public ArrayList<String> getGamesNameList(boolean hidden, boolean orderByDate, boolean init) {
        Log.Loguear("getGamesNameList(boolean hidden, boolean orderByDate)");
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query = "SELECT name FROM games ORDER BY name";
            
            if(hidden && init && !orderByDate) {
                query = "SELECT name FROM games WHERE play_count > 0 ORDER BY name ASC";
                System.out.println("1");
            } else if(!hidden && init && !orderByDate) {
                query = "SELECT name FROM games WHERE play_count > 0 AND hidden = 0 ORDER BY name ASC";
                System.out.println("2");
            } else if(hidden && orderByDate && init) {
                query = "SELECT name FROM games WHERE play_count > 0 ORDER BY last_played DESC";
                System.out.println("3");
            } else if(hidden && orderByDate && !init) {
                query = "SELECT name FROM games ORDER BY last_played DESC";
                System.out.println("4");
            } else if(hidden && !orderByDate && init) {
                query = "SELECT name FROM games WHERE play_count > 0 ORDER BY name ASC";
                System.out.println("5");
            } else if(!hidden && orderByDate && init) {
                query = "SELECT name FROM games WHERE play_count > 0 AND hidden = 0 ORDER BY last_played DESC";
                System.out.println("5");
            } else if(!hidden && orderByDate && !init) {
                query = "SELECT name FROM games WHERE hidden = 0 ORDER BY last_played DESC";
                System.out.println("5");
            } else if(!hidden && !orderByDate && !init) {
                query = "SELECT name FROM games WHERE hidden = 0 ORDER BY name ASC";
                System.out.println("5");
            }
            
            System.out.println(query);
            
            /*
            if(hidden) {
                query = "SELECT name FROM games ORDER BY name";
                if(orderByDate && !init) {
                    query = "SELECT * FROM games ORDER BY last_played DESC";
                } else if(orderByDate && init) {
                    query = "SELECT * FROM games WHERE play_count > 0 ORDER BY last_played DESC";
                }
            } else {
                query = "SELECT name FROM games WHERE hidden = 0 ORDER BY name";
                if(orderByDate && init) {
                    query = "SELECT name FROM games WHERE hidden = 0 ORDER BY last_played DESC, name ASC";
                } else if(orderByDate && !init) {
                    query = "SELECT name FROM games WHERE hidden = 0, play_count > 0 ORDER BY last_played DESC, name ASC";
                }
            }
            */
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

    public ArrayList<String> getGamesNameListCategory(int category) {
        Log.Loguear("getGamesNameListCategory(int category)");
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query = "SELECT name FROM games WHERE category = " + category + " ORDER BY name";
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

    public ArrayList<String> getGamesNameListPlatform(int platform) {
        Log.Loguear("getGamesNameListPlatform(int platform)");
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query = "SELECT name FROM games WHERE platform = " + platform + " ORDER BY name";
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

    public ArrayList<String> getGamesNameListLibrary(int library) {
        Log.Loguear("getGamesNameListLibrary(int library)");
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query = "SELECT name FROM games WHERE library = " + library + " ORDER BY name";
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

    public ArrayList<String> getGamesNameListRating(int rating) {
        Log.Loguear("getGamesNameListRating(int rating)");
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query = "SELECT name FROM games WHERE rating = " + rating + " ORDER BY name";
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

    public ArrayList<String> getGameNameLike(String name) {
        Log.Loguear("getGameNameLike(String name)");
        ArrayList<String> gameName = new ArrayList<>();
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

    public ArrayList<String> getStatisticsGamesNameList(boolean filtered) {
        Log.Loguear("getStatisticsGamesNameList(boolean filtered)");
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query;
            if(filtered) query = "SELECT name FROM games WHERE statistic = 1 ORDER BY name";
            else query = "SELECT name FROM games ORDER BY name";
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

    public int getIdFromGameName(String name) {
        Log.Loguear("getIdFromGameName(String name)");
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
        Log.Loguear("getNameFromId(int gameId)");
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
        Log.Loguear("getDateLastSession(int gameId)");
        String dateLastSession = "Nunca";

        String query = "SELECT date_format(datetime_start, \"%d/%m/%Y\") as Fecha FROM `games_sessions_history` WHERE game_id = "
                + gameId + " order by datetime_start desc limit 1";
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
        Log.Loguear("getCategoryList()");
        ArrayList<String> category = new ArrayList<>();
        String query = "SELECT * FROM category ORDER BY name_category";
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

    public ArrayList<String> getRatingList() {
        Log.Loguear("getRatingList()");
        ArrayList<String> rating = new ArrayList<>();
        String query = "SELECT * FROM rating ORDER BY id";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                rating.add(rs.getString("name"));
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return rating;
    }

    public ArrayList<String> getPlatformList() {
        Log.Loguear("getPlatformList()");
        ArrayList<String> platform = new ArrayList<>();
        String query = "SELECT * FROM platforms ORDER BY name";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                platform.add(rs.getString("name"));
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return platform;
    }

    public int getPlatformTimePlayed(int library_id) {
        Log.Loguear("getPlatformTimePlayed(int library_id)");
        String query = "SELECT time_played FROM platforms WHERE id = " + library_id;
        int hours = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                hours = rs.getInt("time_played");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return hours;
    }

    public int getScore(int gameId) {
        Log.Loguear("getScore(int gameId)");
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
        Log.Loguear("isGhost(int gameId)");
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
        Log.Loguear("isCompleted(int gameId)");
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
        Log.Loguear("isHidden(int gameId)");
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

    public void saveGameHistory(int gameIdLaunched, int gameTimePlayed, String gameName, int library_id, int platform_id, String date_start, String date_end) {
        Log.Loguear("saveGameHistory(int gameIdLaunched, int gameTimePlayed, String gameName, int library_id, int platform_id, String date_start, String date_end)");
        int minsPlayed = gameTimePlayed / 60;
        String query = "INSERT INTO games_sessions_history (game_id, mins, game_name, library_id, platform_id, datetime_start, datetime_end) VALUES (" + gameIdLaunched + "," + minsPlayed + ",'" + gameName + "', " + library_id + ", " + platform_id + ", '" + date_start + "', '" + date_end + "')";
        System.out.println(query);
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            stmt.execute(query);
            stmt.close();
            conex.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveLastGame(String gameName, String sGameTimePlayed) {
        Log.Loguear("saveLastGame(String gameName, String sGameTimePlayed)");
        String sGameName = " Último juego: " + gameName;
        String query = "UPDATE config SET last_game = '" + sGameName + "', last_session_time = '" + sGameTimePlayed + "'";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();			
            stmt.execute(query);
            stmt.close();
            conex.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveGameTime(int gameId, int time) {
        Log.Loguear("saveGameTime(int gameId, int time)");
        if(gameId != 0) {
            String query = "SELECT time_played FROM games WHERE id = '" + gameId + "'";
            try {
                conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
                stmt = conex.createStatement();
                rs = stmt.executeQuery(query);
                if(rs.next()) {
                    try {
                        int timePlayed = rs.getInt(1);
                        int totalTimePlayed = timePlayed + time;
                        query = "UPDATE games SET time_played = " + totalTimePlayed + " WHERE id = " + gameId;
                        stmt.execute(query);
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
                Log.Loguear(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public int setTimePlayed(int gameId, int timePlayed) {
        Log.Loguear("setTimePlayed(int gameId, int timePlayed)");
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
            JOptionPane.showMessageDialog(null, "No se ha podido guardar la sesión\n" + ex.getMessage(), "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return r;
    }

    public void setLastPlayed(int gameId) {
        Log.Loguear("setLastPlayed(int gameId)");
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
            JOptionPane.showMessageDialog(null, "No se ha podido guardar la sesión\n" + ex.getMessage(), "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addTimeLibrary(int gameId, int secs) {
        Log.Loguear("addTimeLibrary(int gameId, int secs)");
        int time_played = 0;
        int library_id = getLibraryIdFromGame(gameId);
        try {
            String query = "SELECT time_played FROM library WHERE id = " + library_id;
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                time_played = rs.getInt("time_played");
                time_played += secs;
                query = "UPDATE library SET time_played = " + time_played + " WHERE id = " + library_id;
                stmt.execute(query);
            }            
            rs.close();
            stmt.close();
            conex.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addSessionLibrary(int gameId) {
        Log.Loguear("addSessionLibrary(int gameId)");
        int total_sessions;
        int library_id = getLibraryIdFromGame(gameId);
        try {
            String query = "SELECT total_sessions FROM library WHERE id = " + library_id;
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                total_sessions = rs.getInt("total_sessions");
                total_sessions ++;
                query = "UPDATE library SET total_sessions = " + total_sessions + " WHERE id = " + library_id;
                stmt.execute(query);
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addTimePlatform(int gameId, int secs) {
        Log.Loguear("addTimePlatform(int gameId, int secs)");
        int time_played = 0;
        int platform_id = getPlatformIdFromGame(gameId);
        try {
            String query = "SELECT time_played FROM platforms WHERE id = " + platform_id;
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                time_played = rs.getInt("time_played");
                time_played += secs;
                query = "UPDATE platforms SET time_played = " + time_played + " WHERE id = " + platform_id;
                stmt.execute(query);
            }            
            rs.close();
            stmt.close();
            conex.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addSessionPlatform(int gameId) {
        Log.Loguear("addSessionPlatform(int gameId)");
        int total_sessions;
        int platform_id = getPlatformIdFromGame(gameId);
        try {
            String query = "SELECT total_sessions FROM platforms WHERE id = " + platform_id;
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                total_sessions = rs.getInt("total_sessions");
                total_sessions ++;
                query = "UPDATE platforms SET total_sessions = " + total_sessions + " WHERE id = " + platform_id;
                stmt.execute(query);
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public int addGame(String name, int gameTime, String path, String ghost, int playCount, String completed,
            int score, int category, int hide, int favorite, int statistic, int portable, String releasedate,
            int rating, int platform, String developer, String publisher, String series,
            String region, String playMode, String version, String status, String lastPlayed,
            String added, String modified, String completed_date, int library, String notes) {
        Log.Loguear("addGame(......)");
        try {
            String query = "INSERT INTO games (name, time_played, path, ghost, play_count, completed, score, category, hidden, favorite, statistic, portable, release_date, "
                    + "rating, platform, developer, publisher, series, region, play_mode, version, status, last_played, added, modified, completed_date, library, notes) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            p.setInt(11, statistic);
            p.setInt(12, portable);
            p.setString(13, releasedate);
            p.setInt(14, rating);
            p.setInt(15, platform);
            p.setString(16, developer);
            p.setString(17, publisher);
            p.setString(18, series);
            p.setString(19, region);
            p.setString(20, playMode);
            p.setString(21, version);
            p.setString(22, status);
            p.setString(23, lastPlayed);
            p.setString(24, added);
            p.setString(25, modified);
            p.setString(26, completed_date);
            p.setInt(27, library);
            p.setString(28, notes);

            int result = p.executeUpdate();

            if(result == 1) {
                int gameId = getIdFromGameName(name);
                ModelPlayer mp = new ModelPlayer();
                mp.saveAchievement("Obtuviste " + name + " en " + getLibraryNameFromGameId(gameId), name, gameId);
            }

            conex.close();
            p.close();
            return result;
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return 0;
    }

    public int editGame(int gameId, String name, int secondsPlayed, String path, String ghost, int playCount,
            String completed, int score, int category, int hidden, int favorite, int statistic, int portable,
            String releasedate, int rating, int platform, String developer, String publisher,
            String series, String region, String playMode, String version, String status,
            String lastPlayed, String completed_date, int library, String notes) {
        Log.Loguear("editGame(..........)");
        try {
            String query;

            if(completed == "1" && !isCompleted(gameId)) {
                ModelPlayer mp = new ModelPlayer();
                String gameName = getNameFromId(gameId);
                String achievement = "Has terminado el juego " + gameName + " en " + Utils.getTotalHoursFromSeconds(secondsPlayed, false);
                mp.saveAchievement(achievement, gameName, gameId);
                MainUI.loadData(false, true);
                if(completed_date.equals("0000-00-00")) completed_date = Utils.getFormattedDate();
            }
            if(completed.equals("0")) completed_date = "0000-00-00";

            query = "UPDATE games SET name = ?, time_played = ?, path = ?, ghost = ?, play_count = ?, completed = ?, score = ?, category = ?, hidden = ?, "
                    + "favorite = ?, statistic = ?, portable = ?, release_date = ?, rating = ?, platform = ?, developer = ?, publisher = ?, series = ?, "
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
            p.setInt(11, statistic);
            p.setInt(12, portable);
            p.setString(13, releasedate);
            p.setInt(14, rating);
            p.setInt(15, platform);
            p.setString(16, developer);
            p.setString(17, publisher);
            p.setString(18, series);
            p.setString(19, region);
            p.setString(20, playMode);
            p.setString(21, version);
            p.setString(22, status);
            p.setString(23, lastPlayed);
            p.setString(24, Utils.getFormattedDateTime());
            p.setString(25, completed_date);
            p.setInt(26, library);
            p.setString(27, notes);
            p.setInt(28, gameId);

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
            if(completed.equals("1")) {
                ModelPlayer mp = new ModelPlayer();
                mp.checkAchievTotalCompletedGames();
            }
            return res;
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return 0;
    }
    
    public String getLastAchiev(int gameId) {
        Log.Loguear("getLastAchiev(int gameId)");
        String query = "SELECT description, DATE_FORMAT(date, '%d/%m/%Y') AS date FROM `player_activities` WHERE game_id = " + gameId + " ORDER BY id DESC LIMIT 1";
        String achiev = "", desc = "", date = "";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                desc = rs.getString("description");
                date = rs.getString("date");
            }
            if(desc == "") achiev = "Ninguna";
            else achiev = desc + " el " + date;
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return achiev;
    }

    public int addCategory(String name) {
        Log.Loguear("addCategory(String name)");
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

    public int addRating(String name) {
        Log.Loguear("addRating(String name)");
        int resultado = 0;
        try {
            String query = "INSERT INTO rating (name) VALUES (?)";
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

    public int addPlatform(String name) {
        Log.Loguear("addPlatform(String name)");
        int resultado = 0;
        try {
            String query = "INSERT INTO platforms (name) VALUES (?)";
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
        Log.Loguear("editCategory(String oldName, String newName)");
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

    public int editRating(String oldName, String newName) {
        Log.Loguear("editRating(String oldName, String newName)");
        int resultado = 0;
        try {
            String query = "UPDATE rating SET name = ? WHERE name = ?";
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

    public int editPlatform(String oldName, String newName) {
        Log.Loguear("editPlatform(String oldName, String newName)");
        int resultado = 0;
        try {
            String query = "UPDATE platforms SET name = ? WHERE name = ?";
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
        Log.Loguear("getGameCategoryId(int gameId)");
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
        Log.Loguear("getGameCategoryName(int gameId)");
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

    public String getGameRatingName(int gameId) {
        Log.Loguear("getGameRatingName(int gameId)");
        String query = "SELECT rating.name FROM `games` inner join rating on rating.id = games.rating where games.id = "
                + gameId;
        String rating = "Ninguna";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                rating = rs.getString("name");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return rating;
    }

    public int getCategoryIdFromName(String name) {
        Log.Loguear("getCategoryIdFromName(String name)");
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

    public int getRatingIdFromName(String name) {
        Log.Loguear("getRatingIdFromName(String name)");
        String query = "SELECT id FROM rating WHERE name = '" + name + "'";
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

    public int getPlatformIdFromName(String name) {
        Log.Loguear("getPlatformIdFromName(String name)");
        String query = "SELECT id FROM platforms WHERE name = '" + name + "'";
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
        Log.Loguear("getReleaseDate(int gameId)");
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
        Log.Loguear("getRating(int gameId)");
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

    public int getPlatform(int gameId) {
        Log.Loguear("getPlatform(int gameId)");
        String query = "SELECT platform FROM games WHERE id = " + gameId;
        int res = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                res = rs.getInt("platform");
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
        Log.Loguear("getDeveloper(int gameId)");
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
        Log.Loguear("getPublisher(int gameId)");
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
        Log.Loguear("getSeries(int gameId)");
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
        Log.Loguear("getRegion(int gameId)");
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
        Log.Loguear("getPlayMode(int gameId)");
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
        Log.Loguear("getVersion(int gameId)");
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
        Log.Loguear("getStatus(int gameId)");
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
        Log.Loguear("getSource(int gameId)");
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
        Log.Loguear("getLastPlayed(int gameId)");
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
        Log.Loguear("isFavorite(int gameId)");
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

    public boolean isStatistic(int gameId) {
        Log.Loguear("isStatistic(int gameId)");
        String query = "SELECT statistic FROM games WHERE id = " + gameId;
        boolean res = false;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                res = rs.getBoolean("statistic");
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
        Log.Loguear("isPortable(int gameId)");
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
        Log.Loguear("getAddedDate(int gameId)");
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
        Log.Loguear("getCompletedDate(int gameId)");
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
        Log.Loguear("getNotes(int gameId)");
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
        Log.Loguear("getModified(int gameId)");
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
        Log.Loguear("getNumberCompletedGames()");
        String query = "SELECT name FROM games WHERE completed = 1";
        int res = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                res++;
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch(SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public int getTotalSessions() {
        Log.Loguear("getTotalSessions()");
        String query = "SELECT SUM(play_count) AS total_sessions FROM games";
        int res = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                res = rs.getInt("total_sessions");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch(SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public int getTotalGames() {
        Log.Loguear("getTotalGames()");
        String query = "SELECT count(name) AS total FROM games";
        int res = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                res = rs.getInt("total");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch(SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public int getCountGamesPlayed() {
        Log.Loguear("getCountGamesPlayed()");
        String query = "SELECT count(time_played) AS games_played FROM games WHERE time_played > 60";
        int res = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                res = rs.getInt("games_played");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch(SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public int deleteGame(int gameId, boolean dropAll) {
        Log.Loguear("deleteGame(int gameId, boolean dropAll)");
        int res = 0;
        try {
            String query = "DELETE FROM games WHERE id = ?";
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setInt(1, gameId);
            res = p.executeUpdate();

            if(dropAll) {
                query = "DELETE FROM games_sessions_history WHERE game_id = ?";
                p = conex.prepareStatement(query);
                p.setInt(1, gameId);
                p.executeUpdate();

                query = "DELETE FROM player_activities WHERE game_id = ?";
                p = conex.prepareStatement(query);
                p.setInt(1, gameId);
                p.executeUpdate();
            }

            p.close();
            conex.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    private int getPlatformIdFromGame(int gameId) {
        Log.Loguear("getPlatformIdFromGame(int gameId)");
        int id = 0;
        String query = "SELECT platform FROM games WHERE id = " + gameId;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                id = rs.getInt("platform");
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
    
    public String getGamePlatformName(int gameId) {
        Log.Loguear("getGamePlatformName(int gameId)");
        String query = "SELECT platforms.name FROM `games` inner join platforms on platforms.id = games.platform where games.id = "
                + gameId;
        String platform = "Ninguna";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                platform = rs.getString("name");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return platform;
    }
    
    public int getPlatformTotalSessions(int platform_id) {
        Log.Loguear("getPlatformTotalSessions(int platform_id)");
        String query = "SELECT total_sessions FROM platforms WHERE id = " + platform_id;
        int total_sessions = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                total_sessions = rs.getInt("total_sessions");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return total_sessions;
    }

    public ArrayList<String> getLibraryList() {
        Log.Loguear("ArrayList<String> getLibraryList()");
        ArrayList<String> library = new ArrayList<>();
        String query = "SELECT * FROM library ORDER BY name";
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
    
    private int getLibraryIdFromGame(int gameId) {
        Log.Loguear("getLibraryIdFromGame(int gameId)");
        int id = 0;
        String query = "SELECT library FROM games WHERE id = " + gameId;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                id = rs.getInt("library");
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

    public int addLibrary(String name) {
        Log.Loguear("addLibrary(String name)");
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
        Log.Loguear("editLibrary(String oldName, String newName)");
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

    public String getLibraryNameFromGameId(int gameId) {
        Log.Loguear("getLibraryNameFromGameId(int gameId)");
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
        Log.Loguear("getLibraryIdFromName(String name)");
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
    
    public int getLibraryTimePlayed(int library_id) {
        Log.Loguear("getLibraryTimePlayed(int library_id)");
        String query = "SELECT time_played FROM library WHERE id = " + library_id;
        int hours = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                hours = rs.getInt("time_played");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return hours;
    }
    
    public int getLibraryTotalSessions(int library_id) {
        Log.Loguear("getLibraryTotalSessions(int library_id)");
        String query = "SELECT total_sessions FROM library WHERE id = " + library_id;
        int total_sessions = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                total_sessions = rs.getInt("total_sessions");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return total_sessions;
    }

    public void initSession(int current_session_number, int game_id) {
        Log.Loguear("initSession(int current_session_number, int game_id)");
        try {
            String query = "INSERT INTO games_sessions_backup (session_number, game_id) VALUES (?, ?)";
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setInt(1, current_session_number);
            p.setInt(2, game_id);
            p.executeUpdate();
            conex.close();
            p.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void updateSession(int current_session_number, int seconds) {
        Log.Loguear("updateSession(int current_session_number, int seconds)");
        String query = "UPDATE games_sessions_backup SET seconds = " + seconds + " WHERE session_number = " + current_session_number;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            stmt.execute(query);
            stmt.close();
            rs.close();
            conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }

    public void deleteSessionBackup(int current_session_number) {
        Log.Loguear("deleteSessionBackup(int current_session_number)");
        String query = "DELETE FROM games_sessions_backup WHERE session_number = " + current_session_number;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.executeUpdate();
            conex.close();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItemHistory(int id) {
        Log.Loguear("deleteItemHistory(int id)");
        String query = "SELECT * FROM games_sessions_history WHERE id = " + id;
        System.out.println(query);
        int game_id = 0, mins_history = 0, play_count = 0, time_played = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                game_id = rs.getInt("game_id");
                mins_history = rs.getInt("mins");
            }

            query = "SELECT time_played, play_count FROM games WHERE id = " + game_id;
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                time_played = rs.getInt("time_played");
                play_count = rs.getInt("play_count");
            }

            mins_history = mins_history * 60;
            time_played -= mins_history;
            play_count--;

            query = "UPDATE games SET time_played = " + time_played + ", play_count = " + play_count + " WHERE id = " + game_id;
            System.out.println(query);
            stmt.execute(query);
            query = "DELETE FROM games_sessions_history WHERE id = " + id;
            stmt.execute(query);

            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean verifyLostSession() {
        Log.Loguear("verifyLostSession()");
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery("SELECT id FROM games_sessions_backup");
            if(rs.next()) return true;
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public String getLostSession() {
        Log.Loguear("getLostSession()");
        String s = "", datetime = "";
        int session_number, game_id, seconds;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery("SELECT * FROM games_sessions_backup");
            if(rs.next()) {
                session_number = rs.getInt("session_number");
                game_id = rs.getInt("game_id");
                datetime = rs.getString("datetime");
                seconds = rs.getInt("seconds");

                s = "Datos perdidos: Sesión: " + session_number + " de " + getNameFromId(game_id) + " el " + datetime + ". Tiempo: " + Utils.getTotalHoursFromSeconds(seconds, true);

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            stmt.execute("DELETE FROM games_sessions_backup");
                            stmt.close();
                            conex.close();
                            rs.close();
                        } catch (InterruptedException | SQLException ex) {
                            Log.Loguear(ex.getMessage());
                        }
                    }
                }).start();
            } else {
                stmt.close();
                conex.close();
                rs.close();
            }
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }

        return s;
    }

    public void newWish(String name) {
        Log.Loguear("newWish(String name)");
        try {
            String query = "INSERT INTO wishlist (name) VALUES (?)";
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setString(1, name);
            p.executeUpdate();
            conex.close();
            p.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void deleteWish(String name) {
        Log.Loguear("deleteWish(String name)");
        try {
            String query = "DELETE FROM wishlist WHERE name = ?";
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setString(1, name);
            p.execute();
            conex.close();
            p.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public ArrayList<String> getWishlist() {
        Log.Loguear("getWishlist()");
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query = "SELECT name FROM wishlist";
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                gameName.add(rs.getString("name"));
            }
            stmt.close();
            conex.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return gameName;
    }
}
