package si.um.feri.fras.global;

public class Result {
    public int time;
    public Difficulty difficulty;
    public int boardSize;

    float K = -37/9000f;
    float N = 1111/150f;

    Result(int time, Difficulty difficulty, int boardSize){
        this.time = time;
        this.difficulty = difficulty;
        this.boardSize = boardSize;
    }

    @Override
    public String toString() {
        return "Result{" +
            "time=" + time +
            ", difficulty=" + difficulty +
            ", boardSize=" + boardSize +
            '}';
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
