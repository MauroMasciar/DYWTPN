package database;

import java.io.File;
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

public class ModelConfig {
    private Connection conex = null;
    private static Statement stmt;
    private static ResultSet rs;

    public int truncateData() {
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = conex.createStatement();

            String query = "TRUNCATE category";
            stmt.execute(query);

            query = "TRUNCATE config";
            stmt.execute(query);

            query = "TRUNCATE games";
            stmt.execute(query);
            
            query = "TRUNCATE wishlist";
            stmt.execute(query);

            query = "TRUNCATE games_sessions_history";
            stmt.execute(query);
            
            query = "TRUNCATE games_sessions_backup";
            stmt.execute(query);

            query = "TRUNCATE library";
            stmt.execute(query);

            query = "TRUNCATE player_activities";
            stmt.execute(query);
            
            query = "TRUNCATE platforms";
            stmt.execute(query);

            query = "INSERT INTO config (name) VALUES ('Usuario')";
            stmt.execute(query);
            
            query = "INSERT INTO platforms (name) VALUES ('PC')";
            stmt.execute(query);

            query = "INSERT INTO category (name_category) VALUES ('Ninguna')";
            stmt.execute(query);

            query = "INSERT INTO `dywtpn`.`library` (`id`, `name`) VALUES (NULL, 'Ninguna');";
            stmt.execute(query);

            conex.close();
            stmt.close();
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
        Log.Loguear("getUsername()");
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
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return name;
    }

    public String getLastGame() {
        Log.Loguear("getLastGame()");
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
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return last_game;
    }

    public String getLastSessionTime() {
        Log.Loguear("getLastSessionTime()");
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
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return last_session_time;
    }

    public boolean getIsHidden() {
        Log.Loguear("getIsHidden()");
        String query = "SELECT show_hidden FROM config";
        int sH = 0;
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) sH = rs.getInt("show_hidden");
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        if(sH == 1) return true;
        return false;
    }

    public void setIsHidden(int args) {
        Log.Loguear("setIsHidden()");
        String query = "UPDATE config SET show_hidden = " + args;
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            stmt.execute(query);
            conex.close();
            stmt.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void setIsViewInit(int args) {
        Log.Loguear("setIsViewInit()");
        String query = "UPDATE config SET show_init = " + args;
        System.out.println(query);
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            stmt.execute(query);
            conex.close();
            stmt.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public boolean getIsViewInit() {
        Log.Loguear("getIsViewInit()");
        String query = "SELECT show_init FROM config";
        int sI = 0;
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) sI = rs.getInt("show_init");
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        if(sI == 1) return true;
        return false;
    }
    
    public boolean getOrderByDate() {
        Log.Loguear("getOrderByDate()");
        String query = "SELECT show_orderbydate FROM config";
        int sH = 0;
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) sH = rs.getInt("show_orderbydate");
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        if(sH == 1) return true;
        return false;
    }    
    
    public void setOrderByDate(int args) {
        Log.Loguear("setOrderByDate(int args)");
        String query = "UPDATE config SET show_orderbydate = " + args;
        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            stmt.execute(query);
            conex.close();
            stmt.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveUserName(String newName) {
        Log.Loguear("saveUserName(String newName)");
        String query = "UPDATE config SET name = '" + newName + "';";
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            stmt.execute(query);
            stmt.close();
            conex.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public int getBounds_x(String window) {
        Log.Loguear("getBounds_x(String window)");
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
            conex.close();
            rs.close();
        } catch(Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return x;
    }

    public int getBounds_y(String window) {
        Log.Loguear("getBounds_y(String window)");
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
            conex.close();
            rs.close();
        } catch(Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return y;
    }

    public void setSavedBounds(String window, double d, double e) {
        Log.Loguear("setSavedBounds(String window, double d, double e)");
        String query = "";
        int x = (int)d;
        int y = (int)e;
        if(window.equals("MainUI")) query = "UPDATE config SET MainUI_x = ?, MainUI_y = ?";	
        if(window.equals("Activity")) query = "UPDATE config SET Activity_x = ?, Activity_y = ?";
        if(window.equals("History")) query = "UPDATE config SET History_x = ?, History_y = ?";

        try {
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = this.conex.prepareStatement(query);
            p.setInt(1, x);
            p.setInt(2, y);
            p.executeUpdate();
            conex.close();
            p.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public int update(String query) {
        Log.Loguear("update(String query)");
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
        Log.Loguear("getTheme()");
        String query = "SELECT theme FROM config";
        int r = 1;
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) r = rs.getInt("theme");
            conex.close();
            stmt.close();
            rs.close();
            Log.Loguear("Cargando el theme " + r);
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        return r;
    }

    public void setTheme(int theme) {
        Log.Loguear("setTheme(int theme)");
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
        Log.Loguear("loadTheme(int theme)");
        try {
            if(theme == 1) UIManager.setLookAndFeel(new FlatIntelliJLaf());
            else if(theme == 2) UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean getOrderByDateSession() {
        Log.Loguear("getOrderByDateSession()");
        String query = "SELECT orderbydate_newsession FROM config";
        int r = 0;
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) r = rs.getInt("orderbydate_newsession");
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        if(r == 0) return false;
        return true;
    }
    
    public boolean getOrderByDateAchiev() {
        Log.Loguear("getOrderByDateAchiev()");
        String query = "SELECT orderbydate_newachiev FROM config";
        int r = 0;
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            stmt = this.conex.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()) r = rs.getInt("orderbydate_newachiev");
            conex.close();
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
        if(r == 0) return false;
        return true;
    }
    
    public void setOrderByDateNewSession(int n) {
        Log.Loguear("setOrderByDateNewSession(int n)");
        String query = "UPDATE config SET orderbydate_newsession = ?";
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = this.conex.prepareStatement(query);
            p.setInt(1, n);
            p.executeUpdate();
            this.conex.close();
            p.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void setOrderByDateNewAchiev(int n) {
        Log.Loguear("setOrderByDateNewAchiev(int n)");
        String query = "UPDATE config SET orderbydate_newachiev = ?";
        try {
            this.conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = this.conex.prepareStatement(query);
            p.setInt(1, n);
            p.executeUpdate();
            this.conex.close();
            p.close();
        } catch (Exception ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void deleteDebugLog() {
        File f = new File("debug.log");
        f.delete();
    }
}
