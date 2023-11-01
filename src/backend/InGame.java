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
    private int hour, minute, second;
    private String gameName = "Nada", sMinute, sSecond;
    private final int HOUR_GAME = 60;

    public InGame(int IdLaunched, String gameName, int gameTimePlayedTotal) {
	if(IdLaunched != 0) {
	    this.gameIdLaunched = IdLaunched;
	    this.gameName = gameName;
	    this.gameTimePlayedTotal = gameTimePlayedTotal;
	    hour = 0;
	    minute = 0;
	    second = 0;
	    
	    LaunchGame(IdLaunched);
	    @SuppressWarnings("unused")
	    ModelGames mg = new ModelGames(IdLaunched);
	}
    }

    public void LaunchGame(int IdLaunched) {
	new Thread(new Runnable() {
	    public void run() {
		while (gameIdLaunched != 0) {
		    try {
			String s = "ID del juego lanzado: " + gameIdLaunched + ". Sesion actual: " + gameTimePlayed + ". Total: " + gameTimePlayedTotal;
			Log.Loguear(s);
			MainUI.txtGamePlaying.setText(" Jugando a '" + gameName + "'");
			CheckAchievement();
			Thread.sleep(60000);
			//Thread.sleep(1000);
			gameTimePlayedTotal ++;
			gameTimePlayed ++;
			saveGameTime();
		    } catch (InterruptedException ex) {
			Log.Loguear(ex.getMessage());
		    }
		}
	    }
	}).start();

	new Thread(new Runnable() {
	    public void run() {
		MainUI.txtTimePlaying.setText(" Tiempo jugando: 0:00:00");
		while (gameIdLaunched != 0) {
		    try {
			Thread.sleep(1000);
			second ++;
			if(second == 60) {
			    second = 0;
			    minute ++;
			}
			if(minute == 60) {
			    minute = 0;
			    hour ++;
			}
			if(second < 10) sSecond = "0" + second;
			else sSecond = String.valueOf(second);
			if(minute < 10) sMinute = "0" + minute;
			else sMinute = String.valueOf(minute);
			MainUI.txtTimePlaying.setText(" Tiempo jugando: " + hour + ":" + sMinute + ":" + sSecond);
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
	    mp.saveAchievement(achiev, mg.getNameFromId(gameIdLaunched), gameIdLaunched);
	    MainUI.LoadData();
	}
    }

    public void closeGame() {
	if(gameIdLaunched != 0) {
	    String sGameName = " Ultimo juego: " + gameName;
	    String sGameTimePlayed = " Jugaste durante: " + hour + ":" + sMinute + ":" + sSecond;
	    ModelGames g = new ModelGames();
	    g.closeGame(gameIdLaunched, gameTimePlayed, gameName, sGameTimePlayed);
	    gameIdLaunched = 0;
	    MainUI.txtGamePlaying.setText(sGameName);
	    MainUI.txtGames.setText("");
	    MainUI.LoadData();
	    gameName = "Nada";
	    new Thread(new Runnable() {
		public void run() {
		    try {
			Thread.sleep(500);
			MainUI.txtTimePlaying.setText(sGameTimePlayed);
		    } catch (InterruptedException ex) {
			Log.Loguear(ex.getMessage());
		    }
		}
	    }).start();
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
