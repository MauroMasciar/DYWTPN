package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class games {
    public static Connection conex;
    private static Statement stmt;
    private static ResultSet rs;
    private ArrayList<String> gameName = new ArrayList<String>();
    private ArrayList<Double> gameHoursPlayed = new ArrayList<Double>();
    private ArrayList<String> gamePath = new ArrayList<String>();

    public games() {
        gameName.add("null");
        gameHoursPlayed.add(0.0);
        gamePath.add("null");
        String query = "SELECT name, hours_played, path FROM games";
        Conn c = new Conn();
        try {
            stmt = c.conex.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                gameName.add(rs.getString("name"));
                gameHoursPlayed.add(rs.getDouble("hours_played"));
                gamePath.add(rs.getString("path"));
            }
            c.conex.close();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public int getIdFromGameName(String name) {
        Conn c = new Conn();
        String query = "SELECT id FROM games WHERE name = '" + name + "'";
        try {
            stmt = c.conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                return rs.getInt("id");
            } else return 0;
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
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
        //for(int i=0; i<gameName.size();i++) System.out.println(gameName.get(i));
        return gameName;
    }
}
