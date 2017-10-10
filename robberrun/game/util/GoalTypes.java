package robberrun.game.util;

public final class GoalTypes {
    public static final char COMMON = 'c';    // normal drop
    public static final char UNCOMMON = 'u';  // timer
    public static final char RARE = 'r';      // super drop
    public static final char SEEK = 's';      // paired with common goal
    public static final char FLEE = 'f';      // paired with uncommon goal
    public static final char FORMATION = 'g'; // paired with rare goal
    public static final int COMMON_SCORE = 1000;
    public static final int UNCOMMON_SCORE = 5000;
    public static final int RARE_SCORE = 12500;
    
    private GoalTypes() {
    }
}
