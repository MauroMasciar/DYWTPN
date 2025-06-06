package database;

import debug.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import backend.Utils;

public class ModelPlayer {
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;

    public DefaultTableModel getActivities(String gameName) {
        Log.Loguear("getActivities(String gameName)");
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("Actividad");

        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            if(gameName == "Todos") {
                rs = stmt.executeQuery("SELECT description, date_format(date, \"%d/%m/%Y %H:%i\") AS date FROM player_activities ORDER BY id DESC");
            } else {
                rs = stmt.executeQuery("SELECT description, date_format(date, \"%d/%m/%Y %H:%i\") AS date FROM player_activities WHERE game_name = '" + gameName + "' ORDER BY id DESC");
            }

            while (rs.next()) {
                String description, date;
                Object[] f = new Object[2];
                for(int i = 0; i < 2; i++) {
                    description = rs.getString("description");
                    date = rs.getString("date");
                    f[0] = description + " el " + date;
                }
                m.addRow(f);   
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return m;
    }

    public DefaultTableModel getHistory(String gameName) {
        Log.Loguear("getHistory(String gameName)");
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("ID");
        m.addColumn("Fecha");
        m.addColumn("Nombre");
        m.addColumn("Tiempo jugado");

        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            if(gameName == "Todos") {
                rs = stmt.executeQuery("SELECT id as ID, date_format(datetime_start, \"%d/%m/%Y %H:%i\") as Fecha, game_name, seconds FROM `games_sessions_history` ORDER BY id DESC");
            } else {
                rs = stmt.executeQuery("SELECT id as ID, date_format(datetime_start, \"%d/%m/%Y %H:%i\") as Fecha, game_name, seconds FROM `games_sessions_history` WHERE game_name = '"
                        + gameName + "' ORDER BY id DESC");
            }
            
            while (rs.next()) {
                Object[] f = new Object[4];
                for (int i = 0; i < 4; i++) {
                    if(i == 3) {
                    	f[i] = Utils.getTotalHoursFromSeconds(rs.getInt(i + 1), false);
                    } else {
                    	f[i] = rs.getObject(i + 1);
                    }
                }
                m.addRow(f);
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return m;
    }

    public void saveAchievement(String achievement, String gamename, int gameid) {
        Log.Loguear("saveAchievement(String achievement, String gamename, int gameid)");
        String query = "INSERT INTO player_activities (game_name, description, game_id) VALUES (?,?,?)";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setString(1, gamename);
            p.setString(2, achievement);
            p.setInt(3, gameid);
            p.executeUpdate();
            conex.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getLastAchievement() {
        Log.Loguear("getLastAchievement()");
        String s = "", achiev = "", date = "";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery("SELECT description, DATE_FORMAT(date, '%d/%m/%Y') AS date FROM player_activities ORDER BY id desc LIMIT 1");
            if(rs.next()) {
                achiev = rs.getString(1);
                date = rs.getString(2);
            }
            s = achiev + " el " + date;
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return s;
    }
    
    public void checkAchievTotalCompletedGames() {
        Log.Loguear("checkAchievTotalCompletedGames()");
        int i = getTotalCompletedGames();
        boolean achiev = false;
        if(i == 10) achiev = true;
        else if(i == 50) achiev = true;
        else if(i == 100) achiev = true;
        else if(i == 250) achiev = true;
        else if(i == 500) achiev = true;
        else if(i == 1000) achiev = true;
        else if(i == 1500) achiev = true;
        else if(i == 2000) achiev = true;
        if(achiev) {
            String string = "Has completado " + i + " juegos";
            saveAchievement(string, "Ninguno", 0);
        }
    }

    public int getTotalCompletedGames() {
        Log.Loguear("getTotalCompletedGames()");
        int i = 0;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery("SELECT count(completed) AS n FROM games WHERE completed = 1");
            if(rs.next()) {
                i = rs.getInt("n");
            }
            conex.close();
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return i;
    }
    
    public static String getTimeLastDay() {
    	Log.Loguear("getTimeLastDay()");
    	ModelGames mg = new ModelGames();
    	int last = mg.getLastDays(0, 1, false);
    	String string = Utils.getTotalHoursFromSeconds(last, false);
    	return string;
    }
    
    public static String getTimeLastWeek() {
    	Log.Loguear("getTimeLastWeek()");
    	ModelGames mg = new ModelGames();
    	int last = mg.getLastDays(0, 7, false);
    	String string = Utils.getTotalHoursFromSeconds(last, false);
    	return string;
    }
    
    public static String getTimeLastFourteen() {
    	Log.Loguear("getTimeLastFourteen()");
    	ModelGames mg = new ModelGames();
    	int last = mg.getLastDays(0, 14, false);
    	String string = Utils.getTotalHoursFromSeconds(last, false);
    	return string;
    }
    
    public static String getTimeLastMonth() {
    	Log.Loguear("getTimeLastMonth()");
    	ModelGames mg = new ModelGames();
    	int last = mg.getLastDays(0, 30, false);
    	String string = Utils.getTotalHoursFromSeconds(last, false);
    	return string;
    }
    
    public static String getTimeLastYear() {
    	Log.Loguear("getTimeLastYear()");
    	ModelGames mg = new ModelGames();
    	int last = mg.getLastDays(0, 365, false);
    	String string = Utils.getTotalHoursFromSeconds(last, false);
    	return string;
    }
    
    public static String getTotalTimePlayer() {
    	Log.Loguear("getTotalTimePlayer()");
    	ModelGames mg = new ModelGames();
    	String totalTimePlayed = Utils.getTotalHoursFromSeconds(mg.getSecondsTotalPlayed(), true);
    	return totalTimePlayed;
    }
}
