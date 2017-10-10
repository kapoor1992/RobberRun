package robberrun.game;

// Created for the police.
class Obstacle {
    private static final double ACCELERATION = 0.05;
    private static final int FORMATION_SPACING = 20;
    private static final int MAX_SPEED = 1;
    private static final int FLEE_BUFFER = 30;
    private static final int SWEEP_BUFFER = 50;
    private static int leaderSlotX;
    private static int leaderSlotY;
    private static double speed;
    private int id;
    private int x;
    private int y;
    private int goalX;
    private int goalY;
    private int areaHeight;
    private int areaWidth;
    
    public Obstacle(int goalX, int goalY, int areaHeight, int areaWidth, int id) {
        this.x = areaWidth;
        this.y = areaHeight;
        this.goalX = goalX;
        this.goalY = goalY;
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.id = id;
        
        initializeSpeed();
        initializeLeader();
    }
    
    public static void initializeSpeed() {
        speed = 0.5;
    }
    
    // Leader slot is in the top left (used for formations).
    public static void initializeLeader() {
        leaderSlotX = 20;
        leaderSlotY = 20;
    }
    
    // Used for a police officer to seek out where a drop was taken from.
    public void seek() {
        if (x == goalX && y == goalY)
            return;
        
        speed += ACCELERATION;
        
        if (speed > MAX_SPEED)
            speed = MAX_SPEED;
        
        if (goalX > x)
            x += speed;
        else if (goalX < x)
            x -= speed;
        
        if (goalY > y)
            y += speed;
        else if (goalY < y)
            y -= speed;
    }
    
    // Used for police officers to leave the neighbourhood.
    public void flee() {
        // They don't leave very far from the area.
        if (x == 0 || x == areaWidth || y == 0 || y == areaHeight)
            return;
        
        speed += ACCELERATION;
        
        if (speed > MAX_SPEED)
            speed = MAX_SPEED;
        
        if (goalX <= areaWidth / 2)
            x -= speed;
        else if (goalX > areaWidth / 2)
            x += speed;
        
        if (goalY <= areaHeight / 2)
            y -= speed;
        else if (goalY > areaHeight / 2)
            y += speed;
    }
    
    // Gets police officers in a formation in preparation for a sweep.
    public boolean group() {
        int slotX = leaderSlotX + (id * FORMATION_SPACING);
        
        if (x == slotX && y == leaderSlotY) {
            return true;
        }
        
        speed += ACCELERATION;
        
        if (speed > MAX_SPEED)
            speed = MAX_SPEED;
        
        if (x > slotX)
            x -= speed;
        if (x < slotX)
            x += speed;
        
        if (y > leaderSlotY)
            y -= speed;
        if (y < leaderSlotY)
            y += speed;
        
        return false;
    }
    
    // Police officers do a sweep.
    public void sweep() {
        if (y > areaHeight - SWEEP_BUFFER) {
            y = areaHeight - SWEEP_BUFFER;
            return;
        }
        
        y += 1;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
