package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class ModelPlayer {
    private String database = "DYWTPN";
    private String hostname = "localhost";
    private String port = "3306";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private String username = "root";
    private String password = "123456";
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;
    
    public DefaultTableModel getActivities() {
	DefaultTableModel m = new DefaultTableModel();
	m.addColumn("Nombre");
	m.addColumn("Descripcion");
	
	try {
	    conex = DriverManager.getConnection(url, username, password);
	    stmt = conex.createStatement();
	    rs = stmt.executeQuery("SELECT * FROM player_activities");
	    while(rs.next()) {
		Object[] f = new Object[3];
		for(int i = 0; i < 3; i++) {
		    f[i] = rs.getObject(i+1);
		}
		m.addRow(f);
	    }
	    conex.close();
	    stmt.close();
	    rs.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	
	return m;
    }
    
    
	
	
	

}
