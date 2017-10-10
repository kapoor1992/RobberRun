package robberrun;

import robberrun.game.GameController;
import java.io.File;
import javax.swing.JOptionPane;

public class RobberRunMain {
    public static void main(String[] args) throws InterruptedException {
        // Set arguments to be passed.
        int aX = 600;
        int aY = 600;
        int pX = 30;
        int pY = 30;
        int gX = 20;
        int gY = 20;
        int oX = 15;
        int oY = 15;
        int s = 1;
        
        // Grab images.
        String pp = new File("robberrun/images/player.jpg").getAbsolutePath();
        String cp = new File("robberrun/images/goal_common.jpg").getAbsolutePath();
        String up = new File("robberrun/images/goal_uncommon.jpg").getAbsolutePath();
        String rp = new File("robberrun/images/goal_rare.jpg").getAbsolutePath();
        String op = new File("robberrun/images/obstacle.jpg").getAbsolutePath();
        String bp = new File("robberrun/images/background.jpg").getAbsolutePath();
        
        // Gives brief information and instructions.
        intro();
        
        // Start game.
        GameController gc = new GameController(aY, aX, pY, pX, gY, gX, oY, oX, s, 
                                               pp, cp, up, rp, op, bp);
    }
    
    public static void intro() {
        JOptionPane.showMessageDialog(null,
                                      "Welcome to Robber Run!\n\n" +
                                      "You are a bank robber on the run from the police.\n" +
                                      "Use the arrow keys or WASD to move around!\n\n" +
                                      "A normal drop is $1,000 and attracts police officers to all previous (and the current) drops.\n" + 
                                      "The timer is $5,000 and sends all police out of the neighbourhood.\n" + 
                                      "The super drop is $12,500 and triggers a neighbourhood sweep by police.\n\n" +
                                      "Happy robbing!",
                                      "Robber Run",
                                      JOptionPane.PLAIN_MESSAGE);
    }
}