package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	public String driver = "com.mysql.cj.jdbc.Driver";
    public String database = "DYWTPN";
    public String hostname = "localhost";
    public String port = "3306";
    public String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    public String username = "root";
    public String password = "123456";
    
    public Connection Conn() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}