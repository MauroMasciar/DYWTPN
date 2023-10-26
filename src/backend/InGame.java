package backend;

import database.Games;

public class InGame {
    Runnable runnable = null;
    private int gameIdLaunched = 0;
    private int gameTimePlayed = 0;

    public InGame(int IdLaunched) {
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
			System.out.println("ID del juego lanzado: " + gameIdLaunched + ". Sesion actual: " + gameTimePlayed);
			//Thread.sleep(60000);
			Thread.sleep(500);
			gameTimePlayed ++;
		    } catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		    }
		}
	    }
	}).start();
    }

    public void closeGame() {
	Games g = new Games();
	g.saveGameTime(gameIdLaunched, gameTimePlayed);
	gameIdLaunched = 0;
    }

    public int getGameTimePlayed() {
	return gameTimePlayed;
    }
}
