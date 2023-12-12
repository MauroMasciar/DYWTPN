package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import debug.Log;
import frontend.Main;

public class ModelConfig {
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;

    public int truncateData() {
	try {
	    this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();

	    String query = "TRUNCATE category";
	    stmt.execute(query);

	    query = "TRUNCATE config";
	    stmt.execute(query);

	    query = "TRUNCATE games";
	    stmt.execute(query);

	    query = "TRUNCATE games_sessions_history";
	    stmt.execute(query);

	    query = "TRUNCATE library";
	    stmt.execute(query);

	    query = "TRUNCATE player_activities";
	    stmt.execute(query);

	    query = "INSERT INTO config (name) VALUES ('Usuario')";
	    stmt.execute(query);

	    query = "INSERT INTO category (name_category) VALUES ('Ninguna')";
	    stmt.execute(query);

	    query = "INSERT INTO `dywtpn`.`library` (`id`, `name`) VALUES (NULL, 'Ninguna');";
	    stmt.execute(query);

	    stmt.close();
	    this.conex.close();
	    Log.Loguear("Datos borrados");
	    JOptionPane.showMessageDialog(null, "La aplicacion se cerrara", "Datos borrados", JOptionPane.INFORMATION_MESSAGE);
	    System.exit(0);
	    return 1;
	} catch (SQLException ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return 0;
    }

    public String getUsername() {
	String query = "SELECT name FROM config";
	String name = "";
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		name = rs.getString("name");
	    } else {
		name = "ERROR";
	    }
	    stmt.close();
	    this.conex.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return name;
    }

    public String getLastGame() {
	String query = "SELECT last_game FROM config";
	String last_game = "";
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		last_game = rs.getString("last_game");
	    } else {
		last_game = "ERROR";
	    }
	    stmt.close();
	    this.conex.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return last_game;
    }

    public String getLastSessionTime() {
	String query = "SELECT last_session_time FROM config";
	String last_session_time = "";
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) {
		last_session_time = rs.getString("last_session_time");
	    } else {
		last_session_time = "ERROR";
	    }
	    stmt.close();
	    this.conex.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return last_session_time;
    }

    public boolean getIsHidden() {
	String query = "SELECT show_hidden FROM config";
	int sH = 0;
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) sH = rs.getInt("show_hidden");
	    stmt.close();
	    this.conex.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	if(sH == 1) return true;
	return false;
    }

    public void setIsHidden(int args) {
	String query = "UPDATE config SET show_hidden = " + args;
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    stmt.execute(query);
	    stmt.close();
	    this.conex.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
    }

    public void saveUserName(String newName) {
	String query = "UPDATE config SET name = '" + newName + "';";
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    stmt.execute(query);
	    stmt.close();
	    this.conex.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
    }

    public int getBounds_x(String window) {
	int x = 30;
	String query = "";
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    if(window.equals("MainUI")) query = "SELECT MainUI_x FROM config";
	    else if(window.equals("Activity")) query = "SELECT Activity_x FROM config";
	    else if(window.equals("History")) query = "SELECT History_x FROM config";

	    rs = stmt.executeQuery(query);
	    if(rs.next()) x = rs.getInt(1);
	    stmt.close();
	    this.conex.close();
	    rs.close();
	} catch(Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(null, "Ha habido un error al conectar con la base de datos.\nReinstalar la aplicacion puede solucionar el problema.\n\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    try {
		Main.p.destroy();
	    } catch(NullPointerException exx) {
		exx.printStackTrace();
		System.exit(0);
	    }
	}
	return x;
    }

    public int getBounds_y(String window) {
	int y = 30;
	String query = "";
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    if(window.equals("MainUI")) query = "SELECT MainUI_y FROM config";
	    else if(window.equals("Activity")) query = "SELECT Activity_y FROM config";
	    else if(window.equals("History")) query = "SELECT History_y FROM config";

	    rs = stmt.executeQuery(query);
	    if(rs.next()) y = rs.getInt(1);
	    stmt.close();
	    this.conex.close();
	    rs.close();
	} catch(Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return y;
    }

    public void setSavedBounds(String window, double d, double e) {
	String query = "";
	int x = (int)d;
	int y = (int)e;
	if(window.equals("MainUI")) query = "UPDATE config SET MainUI_x = ?, MainUI_y = ?";	
	if(window.equals("Activity")) query = "UPDATE config SET Activity_x = ?, Activity_y = ?";
	if(window.equals("History")) query = "UPDATE config SET History_x = ?, History_y = ?";

	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = this.conex.prepareStatement(query);
	    p.setInt(1, x);
	    p.setInt(2, y);
	    p.executeUpdate();
	    this.conex.close();
	    p.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
    }

    public int update(String query) {
	int r = 0;
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    r = stmt.executeUpdate(query);
	    stmt.close();
	    this.conex.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	Log.Loguear(String.valueOf(r));
	return r;	
    }

    public int getTheme() {
	String query = "SELECT theme FROM config";
	int r = 1;
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    stmt = this.conex.createStatement();
	    rs = stmt.executeQuery(query);
	    if(rs.next()) r = rs.getInt("theme");
	    stmt.close();
	    this.conex.close();
	    rs.close();
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
	return r;
    }

    public void setTheme(int theme) {
	String query = "UPDATE config SET theme = ?";
	try {
	  this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
	    PreparedStatement p = this.conex.prepareStatement(query);
	    p.setInt(1, theme);
	    p.executeUpdate();
	    this.conex.close();
	    p.close();
	    loadTheme(theme);
	} catch (Exception ex) {
	    Log.Loguear(ex.getMessage());
	    ex.printStackTrace();
	}
    }

    public static void loadTheme(int theme) {
	try {
	    if(theme == 1) UIManager.setLookAndFeel(new FlatIntelliJLaf());
	    else if(theme == 2) UIManager.setLookAndFeel(new FlatMacDarkLaf());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
