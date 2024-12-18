package si.um.feri.fras.global;

public class Result {
    public int time;
    public Difficulty difficulty;
    public int boardSize;

    public float K = -37/9000f;
    public float N = 1111/150f;

    // No-argument constructor with default values
    public Result() {
        this.time = 0; // Default value for time
        this.difficulty = Difficulty.NORMAL; // Default difficulty (assuming Difficulty is an enum)
        this.boardSize = 7; // Default value for boardSize
    }

    // Constructor with arguments
    Result(int time, Difficulty difficulty, int boardSize) {
        this.time = time;
        this.difficulty = difficulty;
        this.boardSize = boardSize;
    }


    public float getDifficultyFactor() {
        switch (difficulty) {
            case EASY:
                return 0.5f;
            case NORMAL:
                return 1f;
            case HARD:
                return 1.5f;
            default:
                throw new IllegalStateException("Unknown difficulty");
        }
    }

    public int calcScore(){
        return (int) (boardSize * boardSize * getDifficultyFactor() * (K * time + N));
    }

}
