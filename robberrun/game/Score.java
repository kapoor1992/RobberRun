package robberrun.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import robberrun.game.util.GoalTypes;

// Keeps running total of money robbed.
public class Score extends GameElement {
    // Used for score positioning.
    public static final int HEIGHT_OFFSET = 25;
    public static final int WIDTH_OFFSET = 50;
    private int score;
    private int x;
    private int y;
    
    public Score(int height, int width) {
        super(height, width);
        
        score = 0;
        
        x = WIDTH_OFFSET;
        y = HEIGHT_OFFSET;
    }
    
    public void updateScore(char type) {
        if (type == GoalTypes.COMMON)
            score += GoalTypes.COMMON_SCORE;
        else if (type == GoalTypes.UNCOMMON) {
            score += GoalTypes.UNCOMMON_SCORE;
        } else {
            score += GoalTypes.RARE_SCORE;
        }
    }
    
    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics.setColor(Color.BLACK);
        graphics2D.drawString("$" + score, x, y);
    }
}