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

    public games() {
        String query = "SELECT name, hours_played FROM games";
        Conn c = new Conn();
        try {
            stmt =  c.conex.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                gameName.add(rs.getString("name"));
                gameHoursPlayed.add(rs.getDouble("hours_played"));
            }

            // testing
            for(int i=0; i<gameName.size();i++) {
                System.out.println("nombre: " + i + " " + gameName.get(i));
            }
            for(int i=0; i<gameHoursPlayed.size();i++) {
                System.out.println("horas: " + i + " " + gameHoursPlayed.get(i));
            }
            // testing fin
            c.conex.close();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
