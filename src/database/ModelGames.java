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
            checkAchievement(gameId, play_count);
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return play_count;
    }

    public int changeGameName(int gameId, String newName) {
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

    private void checkAchievement(int gameId, int play_count) {
        String achiev = "";
        if(play_count == 1) achiev = "Has jugado a " + getNameFromId(gameId) + " por primera vez";
        else if(play_count == 100) achiev = "Has jugado 100 veces a " + getNameFromId(gameId);
        else if(play_count == 250) achiev = "Has jugado 250 veces a " + getNameFromId(gameId);
        else if(play_count == 500) achiev = "Has jugado 500 veces a " + getNameFromId(gameId);
        else if(play_count == 1000) achiev = "Has jugado 1000 veces a " + getNameFromId(gameId);
        else if(play_count == 1500) achiev = "Has jugado 1500 veces a " + getNameFromId(gameId);
        else if(play_count == 2500) achiev = "Has jugado 2500 veces a " + getNameFromId(gameId);
        else if(play_count == 5000) achiev = "Has jugado 5000 veces a " + getNameFromId(gameId);
        else if(play_count == 10000) achiev = "Has jugado 10000 veces a " + getNameFromId(gameId);

        if(achiev != "") {
            ModelPlayer mp = new ModelPlayer();
            mp.saveAchievement(achiev, getNameFromId(gameId), gameId);
            MainUI.loadAchievs();
        }

        achiev = "";
        int totalSessionCount = getTotalSessions();
        if(totalSessionCount != 0) {
            if(totalSessionCount == 100) achiev = "Has tenido 100 sesiones de juego hasta ahora";
            else if(totalSessionCount == 250) achiev = "Has tenido 250 sesiones de juego hasta ahora";
            else if(totalSessionCount == 500) achiev = "Has tenido 500 sesiones de juego hasta ahora";
            else if(totalSessionCount == 1000) achiev = "Has tenido 1000 sesiones de juego hasta ahora";
            else if(totalSessionCount == 1500) achiev = "Has tenido 1500 sesiones de juego hasta ahora";
            else if(totalSessionCount == 2500) achiev = "Has tenido 2500 sesiones de juego hasta ahora";
            else if(totalSessionCount == 5000) achiev = "Has tenido 5000 sesiones de juego hasta ahora";
            else if(totalSessionCount == 10000) achiev = "Has tenido 10000 sesiones de juego hasta ahora";

            if(achiev != "") {
                ModelPlayer mp = new ModelPlayer();
                mp.saveAchievement(achiev, getNameFromId(gameId), gameId);
                MainUI.loadAchievs();
            }
        }
    }

    public DefaultTableModel getFilteredGameList(String name, String completed, String category, String filter, boolean lastPlayed) {
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
                        f[i] = getLibraryName(getIdFromGameName(rrs.getString("name")));
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

    public int getTimeLastSession(int gameId) {
        String query = "SELECT mins FROM `games_sessions_history` WHERE game_id = " + gameId + " ORDER BY datetime DESC limit 1";
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

    public ArrayList<String> getGamesNameList(boolean hidden, boolean orderByDate) {
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query;
            if(hidden) {
                query = "SELECT name FROM games ORDER BY name";
                if(orderByDate) {
                    query = "SELECT * FROM games ORDER BY last_played DESC";
                }
            } else {
                query = "SELECT name FROM games WHERE hidden = 0 ORDER BY name";
                if(orderByDate) {
                    query = "SELECT name FROM games WHERE hidden = 0 ORDER BY last_played DESC, name ASC";
                }
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

    public ArrayList<String> getGamesNameListCategory(int category) {
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

    public ArrayList<String> getStatisticsGamesNameList() {
        ArrayList<String> gameName = new ArrayList<>();
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            gameName.add("null");
            String query;
            query = "SELECT name FROM games WHERE statistic = 1 ORDER BY name";
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

    public void saveGameHistory(int gameIdLaunched, int gameTimePlayed, String gameName) {
        int minsPlayed = gameTimePlayed / 60;
        String query = "INSERT INTO games_sessions_history (game_id, mins, game_name) VALUES (" + gameIdLaunched + "," + minsPlayed + ",'" + gameName + "')";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            stmt.execute(query);
            stmt.close();
            conex.close();
            //saveLastGame(gameName, sGameTimePlayed);
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveLastGame(String gameName, String sGameTimePlayed) {
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
        try {
            String query = "UPDATE games SET last_played = ? WHERE id = ?";
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setString(1, Utils.getFormattedDateTime());
            p.setInt(2, gameId);
            p.executeUpdate();
            System.out.println("-----------");

            p.close();
            conex.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se ha podido guardar la sesión\n" + ex.getMessage(), "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public int addSessionGame(int gameId, String name, int minsPlayed, String date) {
        int resultado = 0;
        try {
            String query = "INSERT INTO games_sessions_history (game_id, game_name, mins, datetime) VALUES (?,?,?,?)";
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
            } else {
                addTimeLibrary(gameId, minsPlayed * 60);
                addSessionLibrary(gameId);
                addTimePlatform(gameId, minsPlayed * 60);
                addSessionPlatform(gameId);
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

    public void addTimeLibrary(int gameId, int secs) {
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
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addTimePlatform(int gameId, int secs) {
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
                mp.saveAchievement("Ahora tienes " + name + " en " + getLibraryName(gameId), name, gameId);
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
        try {
            String query;

            if(completed == "1" && !isCompleted(gameId)) {
                ModelPlayer mp = new ModelPlayer();
                String gameName = getNameFromId(gameId);
                String achievement = "Has terminado el juego " + gameName + " en " + Utils.getTotalHoursFromSeconds(secondsPlayed, true);
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

    public int addRating(String name) {
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

    public String getGamePlatformName(int gameId) {
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

    public String getGameRatingName(int gameId) {
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

    public int getPlatform(int gameId) {
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

    public boolean isStatistic(int gameId) {
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

    public int deleteGame(int gameId, boolean dropAll) {
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

    private int getLibraryIdFromGame(int gameId) {
        int id = 0;
        String query = "SELECT library FROM games WHERE id = " + gameId;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                id = rs.getInt("library");
            }
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }

    private int getPlatformIdFromGame(int gameId) {
        int id = 0;
        String query = "SELECT platform FROM games WHERE id = " + gameId;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                id = rs.getInt("platform");
            }
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }

    public ArrayList<String> getLibraryList() {
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

    public int getLibraryTimePlayed(int library_id) {
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

    public void initSession(int current_session_number, int game_id) {
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
        String query = "DELETE FROM games_sessions_backup WHERE session_number = " + current_session_number;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.executeUpdate();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItemHistory(int id) {
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
            System.out.println(query);
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
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery("SELECT id FROM games_sessions_backup");
            if(rs.next()) return true;
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public String getLostSession() {
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
                        } catch (InterruptedException | SQLException ex) {
                            Log.Loguear(ex.getMessage());
                        }
                    }
                }).start();


            }
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }

        return s;
    }

    public void newWish(String name) {
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

    public ArrayList<String> getWishlist() {
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
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return gameName;
    }
}
