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
    private final int HOUR_GAME = 3600;

    public InGame(int IdLaunched, String gameName, int gameTimePlayedTotal) {
	if(IdLaunched != 0) {
	    this.gameIdLaunched = IdLaunched;
	    this.gameName = gameName;
	    this.gameTimePlayedTotal = gameTimePlayedTotal;
	    hour = 0;
	    minute = 0;
	    second = 0;

	    ModelGames mg = new ModelGames();
	    mg.setLastPlayed(IdLaunched);

	    MainUI.txtGamePlaying.setText(" Jugando a '" + gameName + "'");
	    launchGame();
	}
    }

    private void launchGame() {
	new Thread(new Runnable() {
	    public void run() {
		MainUI.txtTimePlaying.setText(" Tiempo jugando: 0:00:00");
		if(gameIdLaunched == 0) Thread.currentThread().interrupt();
		while(gameIdLaunched != 0) {
		    if(MainUI.gamePaused) {
			try {
			    Thread.sleep(1000);
			} catch (InterruptedException ex) {
			    Log.Loguear(ex.getMessage());
			}
		    } else {
			try {
			    if(second == 0 && minute != 0) {
				String s = "ID del juego lanzado: " + gameIdLaunched + ". Sesion actual: " + gameTimePlayed + "(" + gameTimePlayed/60 + ")" 
					+ ". Total: " + gameTimePlayedTotal + "(" + (gameTimePlayedTotal/60)/60 + ")";

				Log.Loguear(s);
			    }
			    checkAchievement(0, 0);
			    Thread.sleep(1000);
			    second++;

			    if(second == 60) {
				second = 0;
				minute++;
			    }

			    if(minute == 60) {
				minute = 0;
				hour++;
			    }

			    if(second < 10) {
				sSecond = "0" + second;
			    } else {
				sSecond = String.valueOf(second);
			    }

			    if(minute < 10) {
				sMinute = "0" + minute;
			    } else {
				sMinute = String.valueOf(minute);
			    }

			    MainUI.txtTimePlaying.setText(" Tiempo jugando: " + hour + ":" + sMinute + ":" + sSecond);

			    gameTimePlayedTotal++;
			    gameTimePlayed++;
			    saveGameTime();
			} catch (InterruptedException ex) {
			    Log.Loguear(ex.getMessage());
			}
		    }
		}
	    }
	}).start();
    }

    private void checkAchievement(int sessionCount, int totalSessionCount) {
	ModelGames mg = new ModelGames();
	String achiev = "";
	if(gameTimePlayedTotal == 60) achiev = "Has jugado a " + mg.getNameFromId(gameIdLaunched) + " por primera vez";
	else if(gameTimePlayedTotal == HOUR_GAME) achiev = "Has alcanzado tu primera hora de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 5) achiev = "Has alcanzado 5 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 10) achiev = "Has alcanzado 10 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 25) achiev = "Has alcanzado 25 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 50) achiev = "Has alcanzado 50 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 100) achiev = "Has alcanzado 100 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 250) achiev = "Has alcanzado 250 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 500) achiev = "Has alcanzado 500 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 1000) achiev = "Has alcanzado 1000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 2000) achiev = "Has alcanzado 2000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 5000) achiev = "Has alcanzado 5000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
	else if(gameTimePlayedTotal == HOUR_GAME * 10000) achiev = "Has alcanzado 10000 horas de juego en " + mg.getNameFromId(gameIdLaunched);

	if(achiev != "") {
	    ModelPlayer mp = new ModelPlayer();
	    mp.saveAchievement(achiev, mg.getNameFromId(gameIdLaunched), gameIdLaunched);
	    MainUI.loadAchievs();
	}

	achiev = "";
	if(sessionCount != 0) {
	    if(sessionCount == 100) achiev = "Has jugado 100 veces a " + mg.getNameFromId(gameIdLaunched);
	    else if(sessionCount == 250) achiev = "Has jugado 250 veces a " + mg.getNameFromId(gameIdLaunched);
	    else if(sessionCount == 500) achiev = "Has jugado 500 veces a " + mg.getNameFromId(gameIdLaunched);
	    else if(sessionCount == 1000) achiev = "Has jugado 1000 veces a " + mg.getNameFromId(gameIdLaunched);
	    else if(sessionCount == 1500) achiev = "Has jugado 1500 veces a " + mg.getNameFromId(gameIdLaunched);
	    else if(sessionCount == 2500) achiev = "Has jugado 2500 veces a " + mg.getNameFromId(gameIdLaunched);
	    else if(sessionCount == 5000) achiev = "Has jugado 5000 veces a " + mg.getNameFromId(gameIdLaunched);
	    else if(sessionCount == 10000) achiev = "Has jugado 10000 veces a " + mg.getNameFromId(gameIdLaunched);

	    if(achiev != "") {
		ModelPlayer mp = new ModelPlayer();
		mp.saveAchievement(achiev, mg.getNameFromId(gameIdLaunched), gameIdLaunched);
		MainUI.loadAchievs();
	    }
	}

	achiev = "";
	if(totalSessionCount != 0) {
	    if(totalSessionCount == 100) achiev = "Has tenido 100 sesiones de juego hasta ahora";
	    else if(totalSessionCount == 250) achiev = "Has tenido 250 sesiones de juego hasta ahora";
	    else if(totalSessionCount == 500) achiev = "Has tenido 500 sesiones de juego hasta ahora";
	    else if(totalSessionCount == 1000) achiev = "Has tenido 1000 sesiones de juego hasta ahora";
	    else if(totalSessionCount == 1500) achiev = "Has tenido 1500 sesiones de juego hasta ahora";
	    else if(totalSessionCount == 2500) achiev = "Has tenido 2500 sesiones de juego hasta ahora";
	    else if(totalSessionCount == 5000) achiev = "Has tenido 5000 sesiones de juego hasta ahora";
	    else if(totalSessionCount == 10000) achiev = "Has tenido 10000 sesiones de juego hasta ahora";

	    if(achiev != "") {
		ModelPlayer mp = new ModelPlayer();
		mp.saveAchievement(achiev, mg.getNameFromId(gameIdLaunched), gameIdLaunched);
		MainUI.loadAchievs();
	    }
	}
    }

    public void closeGame() {
	if(gameIdLaunched != 0) {
	    String sGameTimePlayed = " Jugaste durante: " + hour + ":" + sMinute + ":" + sSecond;
	    ModelGames mg = new ModelGames();
	    mg.closeGame(gameIdLaunched, gameTimePlayed, gameName, sGameTimePlayed);
	    int count = 0;
	    if(gameTimePlayed > 60) {
		count = mg.newSession(gameIdLaunched);
	    }

	    checkAchievement(count, mg.getTotalSessions());

	    gameIdLaunched = 0;
	    gameName = "Nada";

	    new Thread(new Runnable() {
		public void run() {
		    try {
			Thread.sleep(1000);
			MainUI.loadGames();
			Thread.sleep(500);
			MainUI.loadStatistics();
			Thread.sleep(500);
			MainUI.loadLastDays();
			Thread.sleep(500);
			MainUI.loadTotal();
			Thread.sleep(500);
			MainUI.loadLast();
		    } catch (InterruptedException ex) {
			Log.Loguear(ex.getMessage());
		    }
		}
	    }).start();
	}
    }

    private void saveGameTime() {
	if(gameIdLaunched != 0) {
	    ModelGames g = new ModelGames();
	    g.saveGameTime(gameIdLaunched);
	}
    }

    public int getGameTimePlayed() {
	return gameTimePlayed;
    }
}
