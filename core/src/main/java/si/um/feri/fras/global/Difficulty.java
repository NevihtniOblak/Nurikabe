package si.um.feri.fras.global;

public enum Difficulty {
    EASY, NORMAL, HARD;

    public static String toString(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return "Easy";
            case NORMAL:
                return "Normal";
            case HARD:
                return "Hard";
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }
    }

    public static Difficulty fromString(String value) {
        switch (value.toLowerCase()) {
            case "easy":
                return EASY;
            case "normal":
                return NORMAL;
            case "hard":
                return HARD;
            default:
                throw new IllegalArgumentException("Unknown difficulty string: " + value);
        }
    }
}
