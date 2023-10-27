package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Games;

public class InGame {
    Runnable runnable = null;
    private Connection conex = null;
    private String database = "DYWTPN";
    private String hostname = "localhost";
    private String port = "3306";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private String username = "root";
    private String password = "123456";
    private int gameIdLaunched = 0;
    private int gameTimePlayed = 0;

    public InGame(int IdLaunched) {
	try {
	    conex = DriverManager.getConnection(url, username, password);
	} catch (SQLException ex) {
	    ex.getMessage();
	}
	if(IdLaunched != 0) {
	    this.gameIdLaunched = IdLaunched;
	    LaunchGame(IdLaunched);
	}
    }

    public void LaunchGame(int IdLaunched) {
	new Thread(new Runnable() {
	    public void run() {
		while (gameIdLaunched != 0) {
		    try {
			//System.out.println("ID del juego lanzado: " + gameIdLaunched + ". Sesion actual: " + gameTimePlayed);
			//Thread.sleep(60000);
			Thread.sleep(1000);
			gameTimePlayed ++;
			saveGameTime();
		    } catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		    }
		}
	    }
	}).start();
    }

    public void closeGame() {
	if(gameTimePlayed >= 1) {
	    try {
		String query = "INSERT INTO games_sessions_history (game_id, hours) VALUES (?,?)";
		PreparedStatement p = conex.prepareStatement(query);
		p.setInt(1, gameIdLaunched);
		p.setInt(2, gameTimePlayed);
		p.executeUpdate();
	    } catch (SQLException ex) {
		ex.getMessage();
	    }
	    gameIdLaunched = 0;
	}
    }

    public void saveGameTime() {
	Games g = new Games();
	g.saveGameTime(gameIdLaunched, gameTimePlayed);
    }

    public int getGameTimePlayed() {
	return gameTimePlayed;
    }
}
