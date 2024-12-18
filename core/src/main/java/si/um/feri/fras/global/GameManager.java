package si.um.feri.fras.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    private static final String GRID_SIZE_KEY = "gridSize";
    private static final int DEFAULT_GRID_SIZE = 7;

    private final Preferences PREFS;
    private int gridSize = DEFAULT_GRID_SIZE;

    private GameManager() {
        PREFS = Gdx.app.getPreferences("NurikabeGamePreferences");

        gridSize = PREFS.getInteger(GRID_SIZE_KEY, DEFAULT_GRID_SIZE);
    }


    // Get the grid size setting
    public int getGridSize() {
        return gridSize;
    }

    // Set the grid size setting
    public void setGridSize(int size) {
        if (size < 5 || size > 10) {
            throw new IllegalArgumentException("Grid size must be between 5 and 10.");
        }
        gridSize = size;
        PREFS.putInteger(GRID_SIZE_KEY, size);
        PREFS.flush();
    }
}

