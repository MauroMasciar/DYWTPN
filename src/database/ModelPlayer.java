package database;

import debug.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class ModelPlayer {
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;

    public DefaultTableModel getActivities(String gameName) {
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("Actividad");

        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            if(gameName == "Todos") {
                rs = stmt.executeQuery("SELECT description FROM player_activities ORDER BY id DESC");
            } else {
                rs = stmt.executeQuery("SELECT description FROM player_activities WHERE game_name = '" + gameName + "' ORDER BY id DESC");
            }

            while (rs.next()) {
                Object[] f = new Object[1];
                for (int i = 0; i < 1; i++) {
                    f[i] = rs.getObject(i + 1);
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
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("Fecha");
        m.addColumn("Nombre");
        m.addColumn("Tiempo jugado");
        m.addColumn("Minutos");

        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            if(gameName == "Todos") {
                rs = stmt.executeQuery("SELECT date_format(datetime, \"%d/%m/%Y\") as Fecha, game_name, ROUND((mins / 60),2), mins FROM `games_sessions_history` ORDER BY id DESC");
            } else {
                rs = stmt.executeQuery("SELECT date_format(datetime, \"%d/%m/%Y\") as Fecha, game_name, ROUND((mins / 60),2), mins FROM `games_sessions_history` WHERE game_name = '"
                        + gameName + "' ORDER BY id DESC");
            }

            while (rs.next()) {
                Object[] f = new Object[4];
                for (int i = 0; i < 4; i++) {
                    f[i] = rs.getObject(i + 1);
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
        String query = "INSERT INTO player_activities (game_name, description, game_id) VALUES (?,?,?)";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setString(1, gamename);
            p.setString(2, achievement);
            p.setInt(3, gameid);
            p.executeUpdate();
            conex.close();
            p.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getLastAchievement() {
        String s = "Ninguna";
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();
            rs = stmt.executeQuery("SELECT description FROM player_activities ORDER BY id desc LIMIT 1");
            if(rs.next()) {
                s = rs.getString(1);
            }
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return s;
    }
}
