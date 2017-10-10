package robberrun.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import robberrun.game.util.GoalTypes;

// Manages police officers.
public class Obstacles extends GameElement {
    private ArrayList obstacles = new ArrayList(); // Holds position of all officers.
    private boolean[] grouped;                     // Checks if officers are ready for a sweep.
    private int total;
    private int obstacleHeight;
    private int obstacleWidth;
    private char mode;
    private boolean sweeping;
    
    public Obstacles(int areaHeight, int areaWidth, String path, 
                     int obstacleHeight, int obstacleWidth) {
        super(areaHeight, areaWidth, path);
        
        this.obstacleHeight = obstacleHeight;
        this.obstacleWidth = obstacleWidth;
        mode = ' ';
        sweeping = false;
        total = obstacles.size();
    }
    
    // Sets AI mode (seek, flee, or formation).
    public void setMode(char mode) {
        this.mode = mode;
        Obstacle.initializeSpeed();
        Obstacle.initializeLeader();
        grouped = new boolean[total];
        sweeping = false;
    }
    
    // Checks if a sweep needs to be done.
    public boolean areGrouped() {
        for (int i = 0; i < grouped.length; i++) {
            if (!grouped[i]) {
                return false;
            }
        }
        return true;
    }
    
    public void newObstacle(int goalX, int goalY) {
        Obstacle p = new Obstacle(goalX, goalY, areaHeight, areaWidth, total);
        obstacles.add(p);
        total = obstacles.size();
        grouped = new boolean[total];
    }
    
    public boolean didCollide(int playerX, int playerY, int playerWidth, int playerHeight) {
        Obstacle p;
        int x;
        int y;
        
        // Compare with each obstacle.
        for (int i = 0; i < obstacles.size(); i++) {
            p = (Obstacle)obstacles.get(i);
            x = p.getX();
            y = p.getY();
        
            // These statements ensure any touch to be a collision.
            if (x >= playerX && x <= playerX + playerWidth &&
                y >= playerY && y <= playerY + playerHeight) {
                return true;
            }
            if (x + obstacleWidth >= playerX && x + obstacleWidth <= playerX + playerWidth && 
                y + obstacleHeight >= playerY && y + obstacleHeight <= playerY + playerWidth) {
                return true;
            }
            if (x >= playerX && x <= playerX + playerWidth &&
                y + obstacleHeight >= playerY && y + obstacleHeight <= playerY + playerWidth) {
                return true;
            }
            if (x + obstacleWidth >= playerX && x + obstacleWidth <= playerX + playerWidth &&
                y >= playerY && y <= playerY + playerHeight) {
                return true;
            }
        }
        
        return false;
    }
    
    public void draw(Graphics graphics) {
        Obstacle p;
        int x;
        int y;
        graphics.setColor(Color.RED);
        
        // Draw each obstacle.
        for (int i = 0; i < obstacles.size(); i++) {
            p = (Obstacle)obstacles.get(i);
            
            if (mode == GoalTypes.SEEK) {
                p.seek();
            } else if (mode == GoalTypes.FLEE) {
                p.flee();
            } else if (mode == GoalTypes.FORMATION) {
                // If we're already sweeping, keep sweeping.
                if (sweeping) {
                    p.sweep();
                // If we're not sweeping, check if we should.
                } else {
                    grouped[i] = p.group();
                    sweeping = areGrouped();
                }
            }
            
            x = p.getX();
            y = p.getY();
            graphics.drawImage(image, x, y, null);
        }
    }
}
