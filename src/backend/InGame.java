package backend;

import database.ModelGames;
import database.ModelPlayer;
import debug.Log;
import frontend.MainUI;

public class InGame {
    Runnable runnable = null;
    private int gameIdLaunched = 0;
    private int gameTimePlayed = 0;
    private int gameTimePlayedTotal = 0;
    private String gameName = "Nada";
    private final int HOUR_GAME = 60;

    public InGame(int IdLaunched, String gameName, int gameTimePlayedTotal) {
	if(IdLaunched != 0) {
	    this.gameIdLaunched = IdLaunched;
	    this.gameName = gameName;
	    this.gameTimePlayedTotal = gameTimePlayedTotal;
	    LaunchGame(IdLaunched);
	}
    }

    public void LaunchGame(int IdLaunched) {
	new Thread(new Runnable() {
	    public void run() {
		while (gameIdLaunched != 0) {
		    try {
			String s = "ID del juego lanzado: " + gameIdLaunched + ". Sesion actual: " + gameTimePlayed + ". Total: " + gameTimePlayedTotal;
			MainUI.txtGamePlaying.setText(" Jugando a '" + gameName + "'");
			MainUI.txtTimePlaying.setText(" Tiempo: " + gameTimePlayed + " minutos");
			CheckAchievement();
			Log.Loguear(s);
			Thread.sleep(60000);
			//Thread.sleep(500);
			gameTimePlayedTotal ++;
			gameTimePlayed ++;
			saveGameTime();
		    } catch (InterruptedException ex) {
			Log.Loguear(ex.getMessage());
		    }
		}
	    }
	}).start();
    }

    public void CheckAchievement() {
	ModelGames mg = new ModelGames();
	String achiev = "";
	if(gameTimePlayedTotal == 1) achiev = "Has jugado a " + mg.getNameFromId(gameIdLaunched) + " por primera vez";
	else if(gameTimePlayedTotal == HOUR_GAME) achiev = "Has alcanzado tu primera hora de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*5) achiev = "Has alcanzado 5 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*10) achiev = "Has alcanzado 10 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*25) achiev = "Has alcanzado 25 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*50) achiev = "Has alcanzado 50 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*100) achiev = "Has alcanzado 100 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*250) achiev = "Has alcanzado 250 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*500) achiev = "Has alcanzado 500 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*1000) achiev = "Has alcanzado 1000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*2000) achiev = "Has alcanzado 2000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*5000) achiev = "Has alcanzado 5000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME*10000) achiev = "Has alcanzado 10000 horas de juego en " + mg.getNameFromId(gameIdLaunched);

	if(achiev != "") {
	    ModelPlayer mp = new ModelPlayer();
	    mp.saveAchievement(achiev, mg.getNameFromId(gameIdLaunched));
	    MainUI.LoadData();
	}
    }

    public void closeGame() {
	if(gameIdLaunched != 0) {
	    ModelGames g = new ModelGames();
	    g.closeGame(gameIdLaunched, gameTimePlayed);
	    gameIdLaunched = 0;
	    MainUI.txtGamePlaying.setText(" Ultimo juego ejecutado: " + gameName + ".");
	    MainUI.txtTimePlaying.setText(" Jugaste durante: " + gameTimePlayed + " minutos.");
	    MainUI.LoadData();
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
