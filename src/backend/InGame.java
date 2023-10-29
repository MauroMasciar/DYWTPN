package backend;

import database.ModelGames;
import debug.Log;
import frontend.MainWindow;

public class InGame {
    Runnable runnable = null;
    private int gameIdLaunched = 0;
    private int gameTimePlayed = 0;
    private String gameName = "Nada";

    public InGame(int IdLaunched, String gameName) {
	if(IdLaunched != 0) {
	    this.gameIdLaunched = IdLaunched;
	    this.gameName = gameName;
	    LaunchGame(IdLaunched);
	}
    }

    public void LaunchGame(int IdLaunched) {
	new Thread(new Runnable() {
	    public void run() {
		while (gameIdLaunched != 0) {
		    try {
			String s = "ID del juego lanzado: " + gameIdLaunched + ". Sesion actual: " + gameTimePlayed;
			MainWindow.txtGamePlaying.setText("Jugando a '" + gameName + "'");
			MainWindow.txtTimePlaying.setText("'Tiempo: " + gameTimePlayed + " minutos");
			Log.Loguear(s);
			Thread.sleep(60000);
			//Thread.sleep(1000);
			gameTimePlayed ++;
			saveGameTime();
		    } catch (InterruptedException ex) {
			Log.Loguear(ex.getMessage());
		    }
		}
	    }
	}).start();
    }

    public void closeGame() {
	if(gameIdLaunched != 0) {
	    ModelGames g = new ModelGames();
	    g.closeGame(gameIdLaunched, gameTimePlayed);
	    gameIdLaunched = 0;
	    MainWindow.txtGamePlaying.setText("Ultimo juego ejecutado: " + gameName + ".");
	    MainWindow.txtTimePlaying.setText("Jugaste durante: " + gameTimePlayed + " minutos.");
	    gameName = "Nada";
	}
    }

    public void saveGameTime() {
	if(gameIdLaunched != 0) {
	    ModelGames g = new ModelGames();
	    g.saveGameTime(gameIdLaunched);
	}
    }

    public int getGameTimePlayed() {
	return gameTimePlayed;
    }
}
