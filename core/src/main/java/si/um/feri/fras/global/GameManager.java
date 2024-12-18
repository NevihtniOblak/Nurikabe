package si.um.feri.fras.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    private static final String GRID_SIZE_KEY = "gridSize";
    private static final String DIFFICULTY_KEY = "difficulty";
    private static final int DEFAULT_GRID_SIZE = 7;
    private static final String DEFAULT_DIFFICULTY = "Normal";

    private final Preferences PREFS;
    private int gridSize;
    private Difficulty difficulty;

    private GameManager() {
        PREFS = Gdx.app.getPreferences("NurikabeGamePreferences");

        // Load grid size or set to default
        gridSize = PREFS.getInteger(GRID_SIZE_KEY, DEFAULT_GRID_SIZE);

        // Load difficulty or set to default
        String difficultyStr = PREFS.getString(DIFFICULTY_KEY, DEFAULT_DIFFICULTY);
        difficulty = Difficulty.fromString(difficultyStr);
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        PREFS.putInteger(GRID_SIZE_KEY, gridSize);
        PREFS.flush();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        PREFS.putString(DIFFICULTY_KEY, difficulty.name());
        PREFS.flush();
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }
}


