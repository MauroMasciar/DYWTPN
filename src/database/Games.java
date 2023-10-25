package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Games {
    public static Connection conex;
    private static Statement stmt;
    private static ResultSet rs;
    private ArrayList<String> gameName = new ArrayList<String>();
    private ArrayList<Double> gameHoursPlayed = new ArrayList<Double>();
    private ArrayList<String> gamePath = new ArrayList<String>();

    public Games() {
        gameName.add("null");
        gameHoursPlayed.add(0.0);
        gamePath.add("null");
        String query = "SELECT name, hours_played, path FROM games";
        Conn c = new Conn();
        try {
            stmt = c.Conn().createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                gameName.add(rs.getString("name"));
                gameHoursPlayed.add(rs.getDouble("hours_played"));
                gamePath.add(rs.getString("path"));
            }
            c.Conn().close();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    
    public String getPathFromGame(int gameid) {
        String path = gamePath.get(gameid);
        System.out.println(path);
        return path;
    }

    public double getHoursPlayed(int gameid) {
        double hoursplayed = gameHoursPlayed.get(gameid);
        return hoursplayed;
    }

    public ArrayList<String> getGamesNameList() {
        return gameName;
    }

    public int getIdFromGameName(String name) {
        Conn c = new Conn();
        String query = "SELECT id FROM games WHERE name = '" + name + "'";
        try {
        	stmt = c.Conn().createStatement();
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

    public void saveGameTime(int gameId, int sesionPlayed) {
    	Conn c = new Conn();
        String query = "SELECT id, hours_played FROM games WHERE id = '" + gameId + "'";
        System.out.println(query);
        try {
        	Statement stmt;
        	stmt = c.Conn().createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {               
               new Thread(new Runnable() {
                   public void run() {
                       try {
                           double hoursPlayed = rs.getDouble(2);
                           hoursPlayed += sesionPlayed;
                           
                           String query;
                           query = "UPDATE games SET hours_played = " + hoursPlayed + " WHERE id = " + gameId;
                           stmt.execute(query);
                           System.out.println(query);
                       } catch (Exception ex) {
                           System.out.println(ex.getMessage());
                       }
                   }
               }).start();
            } else {
            	String string = "No se ha podido guardar el tiempo jugado. Puede sumarlo manualmente\nUltima sesion: " + sesionPlayed + " segundos.";
            	JOptionPane.showMessageDialog(null, string, "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);            	
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
