package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    public Connection conex;
    public Conn() {
        try {
            Class.forName("org.sqlite.JDBC");
            conex = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        } catch(ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
