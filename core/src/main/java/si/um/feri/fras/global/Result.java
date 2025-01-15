package si.um.feri.fras.global;

public class Result {
    public int time;
    public int boardSize;

    // No-argument constructor with default values
    public Result() {
        this.time = 0; // Default value for time
        this.boardSize = 7; // Default value for boardSize
    }

    // Constructor with arguments
    Result(int time, int boardSize) {
        this.time = time;
        this.boardSize = boardSize;
    }

    public int calcScore(){
        int MAX_TIME = 600;
        int MAX_SCORE = 100;
        int MIN_TIME = 10;
        float timeFactor=1;
        if(time <= MIN_TIME){
            timeFactor = MAX_SCORE;
        }
        else if (time >= MAX_TIME){
            timeFactor = 0;
        }
        else{
            float k = (float) MAX_SCORE / (float) (MIN_TIME - MAX_TIME); // Proper float division
            float n = -k * MAX_TIME;
            timeFactor = k* time + n;

        }

        //System.out.println("Time factor: " + timeFactor);
        return (int) (boardSize * timeFactor);
    }

}
