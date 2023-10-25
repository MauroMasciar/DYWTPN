package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Model {
    public static Connection conex;

    public void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conex = DriverManager.getConnection("jdbc:sqlite:database.db");
            System.out.println("Conectado");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error en la conexión de la base de datos");
            // JOptionPane.showMessageDialog(null, "Error en la conexión a la base de
            // datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
