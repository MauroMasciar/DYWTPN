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
    
    /*public void insert() {
	String query = "INSERT INTO games (name, hours_played, path) VALUES (?,?,?)";
	PreparedStatement queryInsert = conex.prepareStatement(query);
	queryInsert.setString(1, "name");
	queryInsert.setString(2, "hoursPlayed");
	queryInsert.setString(3, "path"1);
	int resultado = queryInsert.executeUpdate();
	if(resultado == 1) return 1;
	else return 0;
    }*/
    
    /*try {
	    String query = "UPDATE games SET name = ?, hours_played = ?, path = ? WHERE id = ?";
	    PreparedStatement p = conex.prepareStatement(query);
	    p.setString(1, name);
	    p.setString(2, hoursPlayed);
	    p.setString(3, path);
	    p.setInt(4, gameId);
	    int res = p.executeUpdate();
	    if(res == 1) {
		
	    } else {
		
	    }
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	*/
}