package robberrun.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import robberrun.game.util.GoalTypes;

// The brain of the game.
public class GameController implements ActionListener {
    private Timer timer;
    private JFrame window;
    private int areaHeight;
    private int areaWidth;
    private int playerHeight;
    private int playerWidth;
    private int goalHeight;
    private int goalWidth;
    private int obstacleHeight;
    private int obstacleWidth;
    private int speed;
    private String playerPath;
    private String commonPath;
    private String uncommonPath;
    private String rarePath;
    private String obstaclePath;
    private String backgroundPath;
    private Player player;
    private Goal goal;
    private Obstacles obstacles;
    private Score score;
    private Background background;
    private Painter painter;
    
    // Yes, it's one heck of a constructor.
    public GameController (int areaHeight, int areaWidth, int playerHeight, 
                           int playerWidth, int goalHeight, int goalWidth,
                           int obstacleHeight, int obstacleWidth, int speed, 
                           String playerPath, String commonPath, String uncommonPath,
                           String rarePath, String obstaclePath, 
                           String backgroundPath) {
        timer = new Timer(5, this);
        
        window = new JFrame("Robber Run");
        
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.playerHeight = playerHeight;
        this.playerWidth = playerWidth;
        this.goalHeight = goalHeight;
        this.goalWidth = goalWidth;
        this.obstacleHeight = obstacleHeight;
        this.obstacleWidth = obstacleWidth;
        this.speed = speed;
        this.playerPath = playerPath;
        this.commonPath = commonPath;
        this.uncommonPath = uncommonPath;
        this.rarePath = rarePath;
        this.obstaclePath = obstaclePath;
        this.backgroundPath = backgroundPath;
        
        initElements();
        attachElements();
        formatWindow();
        
        timer.start();
    }
    
    // Used to restart game.
    private void reset() {
        window = new JFrame("Robber Run");
        initElements();
        attachElements();
        formatWindow();
    }
    
    // Instantiate other package classes.
    private void initElements() {
        player = new Player(areaHeight, areaWidth, playerPath, playerHeight, playerWidth, speed);
        goal = new Goal(areaHeight, areaWidth, commonPath, uncommonPath, rarePath, goalHeight, goalWidth);
        obstacles = new Obstacles(areaHeight, areaWidth, obstaclePath, obstacleHeight, obstacleWidth);
        score = new Score(areaHeight, areaWidth);
        background = new Background(areaHeight, areaWidth, backgroundPath);
        painter = new Painter(areaHeight, areaWidth, player, goal, obstacles, score, background);
    }
    
    // Layer the game window.
    private void attachElements() {
        window.add(painter);
        painter.add(background);
        background.add(obstacles);
        obstacles.add(goal);
        goal.add(player);
        player.add(score);
    }
    
    // Adjust window parameters.
    private void formatWindow() {
        window.setResizable(false);
        window.setSize(areaHeight, areaWidth);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        char type = goal.didCollide(player.getXPos(), player.getYPos(), playerWidth, playerHeight);
        
        // Did we reach a goal?
        if (type != ' ') {
            score.updateScore(goal.getType());
            obstacles.newObstacle(player.getXPos(), player.getYPos());
            
            if (type == GoalTypes.COMMON)
                obstacles.setMode(GoalTypes.SEEK);
            else if (type == GoalTypes.UNCOMMON)
                obstacles.setMode(GoalTypes.FLEE);
            else if (type == GoalTypes.RARE)
                obstacles.setMode(GoalTypes.FORMATION);
            
            do {
                goal.newGoal(); // Do not place a goal on top of an obstacle!
            } while (obstacles.didCollide(goal.getXPos(), goal.getYPos(), goalWidth, goalHeight));
        }
        
        // Did we run into an obstacle?
        if (obstacles.didCollide(player.getXPos(), player.getYPos(), playerWidth, playerHeight)) {
            try {
                Thread.sleep(2000); // Give a couple seconds for the player to process mistake.
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            int response = JOptionPane.showConfirmDialog(null, "Would you like to try again?",
                                                     "Robber Run", JOptionPane.YES_NO_OPTION);
            if (response == 0)
                reset();
            else 
                System.exit(0); // Goodbye!
        }
        
        player.actionPerformed(event);
        
        painter.repaint();
    }
}