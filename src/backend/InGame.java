package backend;

import database.ModelGames;
import debug.Log;

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
						String s = "ID del juego lanzado: " + gameIdLaunched + ". Sesion actual: " + gameTimePlayed;
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
