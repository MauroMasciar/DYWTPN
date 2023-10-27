package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

class Conn {
    public String database = "DYWTPN";
    public String hostname = "localhost";
    public String port = "3306";
    public String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    public String username = "root";
    public String password = "123456";
    Connection conex = null;
    
    public Conn() {
	try {
	    conex = DriverManager.getConnection(url, username, password);
	} catch (SQLException e) {
	    JOptionPane.showMessageDialog(null, "No se ha podido conectar a la base de datos\nSe reintentara en un momento", "Error de conexion", JOptionPane.ERROR_MESSAGE);
	    //TODO: Reintentar conectar a la base de datos
	}
    }    
}