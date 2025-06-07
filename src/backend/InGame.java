package backend;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;

import database.ModelConfig;
import database.ModelGames;
import database.ModelPlayer;
import debug.Log;
import frontend.MainUI;

public class InGame {
    private int gameIdLaunched = 0;
    private final int HOUR_GAME = 3600;
    private int gameTimePlayedInitCurrentGame, gameTimePlayedCurrentGame, gameTimePlayedTotalInit, gameTimePlayedTotal;
    private LocalDateTime initTime;
    private String initDate;
    private int current_session_number = 0;

    public InGame(int IdLaunched) {
        if(IdLaunched != 0) {
            this.gameIdLaunched = IdLaunched;

            initTime = LocalDateTime.now();
            initDate = Utils.getFormattedDateTime();

            ModelGames mg = new ModelGames();
            gameTimePlayedInitCurrentGame = mg.getSecondsPlayed(IdLaunched);
            gameTimePlayedTotalInit = mg.getSecondsTotalPlayed();
            current_session_number = mg.getTotalSessions()  + 1;
            mg.initSession(current_session_number, gameIdLaunched);
            
            ModelConfig mc = new ModelConfig();
            
            launchGame(mc.getUsername(), mg.getNameFromId(IdLaunched), mc.getUserid());
        }
    }

    private void launchGame(String username, String gamename, String user_id) {
        new Thread(new Runnable() {
            public void run() {
                if(gameIdLaunched == 0) Thread.currentThread().interrupt();
                while(gameIdLaunched != 0) {
                    try {
                        checkAchievement();
                        LocalDateTime currentTime = LocalDateTime.now();

                        int secondsBeetwenTimes = (int) ChronoUnit.SECONDS.between(initTime, currentTime);

                        MainUI.loadStatistics(gameIdLaunched, secondsBeetwenTimes);

                        gameTimePlayedCurrentGame = gameTimePlayedInitCurrentGame + secondsBeetwenTimes;
                        gameTimePlayedTotal = gameTimePlayedTotalInit + secondsBeetwenTimes;
                        System.out.println("Sesión: " + gameTimePlayedInitCurrentGame + " - " + gameTimePlayedCurrentGame + " - Total: " + gameTimePlayedTotalInit + " - " + gameTimePlayedTotal + " -- " + initTime + " - " + currentTime);

                        if(secondsBeetwenTimes % 30 == 0) {
                            ModelGames mg = new ModelGames();
                            mg.updateSession(current_session_number, secondsBeetwenTimes);
                        }
                        if(secondsBeetwenTimes % 60 == 0) {
                            WebApp.send(username, gamename, user_id, secondsBeetwenTimes, 1);
                        }

                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Log.Loguear(ex.getMessage());
                    }
                }
            }
        }).start();
    }

    private void checkAchievement() {
        ModelGames mg = new ModelGames();
        String achiev = "";
        if(gameTimePlayedCurrentGame == 240) achiev = "Has jugado a " + mg.getNameFromId(gameIdLaunched) + " por primera vez";
        else if(gameTimePlayedCurrentGame == HOUR_GAME) achiev = "Has alcanzado tu primera hora de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 5) achiev = "Has alcanzado 5 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 10) achiev = "Has alcanzado 10 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 25) achiev = "Has alcanzado 25 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 50) achiev = "Has alcanzado 50 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 100) achiev = "Has alcanzado 100 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 250) achiev = "Has alcanzado 250 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 500) achiev = "Has alcanzado 500 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 1000) achiev = "Has alcanzado 1000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 2000) achiev = "Has alcanzado 2000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 5000) achiev = "Has alcanzado 5000 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 7500) achiev = "Has alcanzado 7500 horas de juego en " + mg.getNameFromId(gameIdLaunched);
        else if(gameTimePlayedCurrentGame == HOUR_GAME * 10000) achiev = "Has alcanzado 10000 horas de juego en " + mg.getNameFromId(gameIdLaunched);

        if(achiev != "") {
            ModelPlayer mp = new ModelPlayer();
            mp.saveAchievement(achiev, mg.getNameFromId(gameIdLaunched), gameIdLaunched);
            MainUI.loadAchievs();
        }

        achiev = "";
        if(gameTimePlayedTotal % 60 == 0) {
            int minutesTotalPlayed = gameTimePlayedTotal / 60;
            if(minutesTotalPlayed == 60) achiev = "Has alcanzado tu primera hora de juego en total";
            else if(minutesTotalPlayed == 60 * 5) achiev = "Has alcanzado 5 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 10) achiev = "Has alcanzado 10 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 25) achiev = "Has alcanzado 25 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 50) achiev = "Has alcanzado 50 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 100) achiev = "Has alcanzado 100 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 250) achiev = "Has alcanzado 250 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 500) achiev = "Has alcanzado 500 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 1000) achiev = "Has alcanzado 1000 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 2000) achiev = "Has alcanzado 2000 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 5000) achiev = "Has alcanzado 5000 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 7500) achiev = "Has alcanzado 7500 horas de juego en total";
            else if(minutesTotalPlayed == 60 * 10000) achiev = "Has alcanzado 10000 horas de juego en total";

            if(achiev != "") {
                ModelPlayer mp = new ModelPlayer();
                mp.saveAchievement(achiev, mg.getNameFromId(gameIdLaunched), 0);
                MainUI.loadAchievs();
            }
        }
    }

    public void closeGame() {
        if(gameIdLaunched != 0) {
            LocalDateTime closeTime = LocalDateTime.now();

            long secondsBeetwenTimes = ChronoUnit.SECONDS.between(initTime, closeTime);
            int totalSecondsSession = (int) secondsBeetwenTimes;

            ModelGames mg = new ModelGames();

            if(totalSecondsSession > 300) {
                mg.saveSession(gameIdLaunched, totalSecondsSession, initDate);
            } else {
                new Thread(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, "El tiempo de juego fue muy corto y no se han guardado datos", "Sesión muy corta", JOptionPane.INFORMATION_MESSAGE);
                    }
                }).start();
            }

            mg.deleteSessionBackup(current_session_number);
            
            ModelConfig mc = new ModelConfig();
            WebApp.send(mc.getUsername(), mg.getNameFromId(gameIdLaunched), mc.getUserid(), (int)secondsBeetwenTimes, 0);

            gameIdLaunched = 0;

            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                        MainUI.loadStatistics(0, 0);
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
}
