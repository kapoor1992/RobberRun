package robberrun.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import robberrun.game.util.GoalTypes;

// Created for the money drops and timer.
public class Goal extends GameElement {
    private static final int PLACEMENT_BUFFER = 100; // Avoids placement over the police station.
    private BufferedImage common;
    private BufferedImage uncommon;
    private BufferedImage rare;
    private int goalHeight;
    private int goalWidth;
    private int x;
    private int y;
    private char type;
    
    public Goal(int areaHeight, int areaWidth, String commonPath, String uncommonPath,
                String rarePath, int goalHeight, int goalWidth) {
        super(areaHeight, areaWidth);
        
        this.goalHeight = goalHeight;
        this.goalWidth = goalWidth;
        
        try {
            common = ImageIO.read(new File(commonPath));
            uncommon = ImageIO.read(new File(uncommonPath));
            rare = ImageIO.read(new File(rarePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        newGoal();
    }
    
    public void newGoal() {
        x = Math.round((float)(Math.random() * (areaWidth - PLACEMENT_BUFFER)));
        y = Math.round((float)(Math.random() * (areaHeight - PLACEMENT_BUFFER)));
        
        int typeRoll = (int)(Math.random() * 20 + 1);
        
        // 20% chance for a super drop.
        if (typeRoll < 5) {
            type = GoalTypes.RARE;
        // 30% chance for a timer.
        } else if (typeRoll < 11) {
            type = GoalTypes.UNCOMMON;
        // 50% chance for a normal drop.
        } else {
            type = GoalTypes.COMMON;
        }
    }
    
    public char getType() {
        return type;
    }
    
    public int getXPos() {
        return x;
    }
    
    public int getYPos() {
        return y;
    }
    
    public char didCollide(int playerX, int playerY, int playerWidth, int playerHeight) {
        // Any touch counts as a collision in these statements.
        if (x >= playerX && x <= playerX + playerWidth &&
            y >= playerY && y <= playerY + playerHeight) {
            return type;
        }
        if (x + goalWidth >= playerX && x + goalWidth <= playerX + playerWidth && 
            y + goalHeight >= playerY && y + goalHeight <= playerY + playerWidth) {
            return type;
        }
        if (x >= playerX && x <= playerX + playerWidth &&
            y + goalHeight >= playerY && y + goalHeight <= playerY + playerWidth) {
            return type;
        }
        if (x + goalWidth >= playerX && x + goalWidth <= playerX + playerWidth &&
            y >= playerY && y <= playerY + playerHeight) {
            return type;
        }
        
        return ' ';
    }
    
    public void draw(Graphics graphics) {
        if (type == GoalTypes.COMMON) {
            graphics.drawImage(common, x, y, null);
        } else if (type == GoalTypes.UNCOMMON) {
            graphics.drawImage(uncommon, x, y, null);
        } else {
            graphics.drawImage(rare, x, y, null);
        }
    }
}