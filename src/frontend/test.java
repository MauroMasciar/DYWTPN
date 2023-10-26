package frontend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class test {
    public static void main(String[] args) {
	String database = "test";
	String hostname = "localhost";
	String port = "3306";
	String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
	String username = "root";
	String password = "123456";
	Connection conex;
	String query = "INSERT INTO tabla (tstv) VALUES (?)";
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    PreparedStatement queryInsert = conex.prepareStatement(query);
	    System.out.println(query);
	    queryInsert.setString(1, "hola mundo");
	    System.out.println(query);
	    int resultado = queryInsert.executeUpdate();
	    if(resultado == 1) System.out.println("Añadido");
	} catch (SQLException e) {
	    System.out.println("No se ha podido conectar a la BD");
	}
    }
}