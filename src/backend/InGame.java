package backend;

import javax.imageio.ImageIO;

import database.Games2;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InGame {
    Runnable runnable = null;
    private int gameIdLaunched = 0;
    private int gameTimePlayed = 0;

    public InGame(int IdLaunched) {
    	this.gameIdLaunched = IdLaunched;
        new Thread(new Runnable() {
            public void run() {
                while (gameIdLaunched != 0) {
                    try {
                    	gameTimePlayed ++;
                        System.out.println("id juego lanzado: " + gameIdLaunched + ". Tiempo jugando: " + gameTimePlayed);
                        Thread.sleep(1000); //TODO El contador deberia ser de 60 segundos
                        
                        /*Games g = new Games();
                    	g.saveGameTime(gameIdLaunched, gameTimePlayed);*/
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }).start();
    }

    public void closeGame() {
    	Games2 g = new Games2();
    	g.saveGameTime(gameIdLaunched, gameTimePlayed);
        gameIdLaunched = 0;
    }
    
    public int getGameTimePlayed() {
    	return gameTimePlayed;
    }

    public void takeScreenshot() {
    	if(gameIdLaunched != 0) {
    		try {
                Robot bot = new Robot();
                BufferedImage screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                File f = new File("screen.jpg");
                try {
                    ImageIO.write(screen, "jpg", f);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } catch (AWTException ex) {
                System.out.println(ex.getMessage());
            }
    	}        
    }
}
