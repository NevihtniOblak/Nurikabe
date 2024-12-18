package si.um.feri.fras.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    private static final String GRID_SIZE_KEY = "gridSize";
    private static final String DIFFICULTY_KEY = "difficulty";

    private static final String MUSIC_ENABLED_KEY = "musicEnabled";

    private static final boolean DEFAULT_MUSIC_ENABLED = true;
    private static final boolean DEFAULT_SOUND_EFFECTS_ENABLED = true;

    private static final String SOUND_EFFECTS_ENABLED_KEY = "soundEffectsEnabled";
    private static final int DEFAULT_GRID_SIZE = 7;
    private static final String DEFAULT_DIFFICULTY = "Normal";

    private final Preferences PREFS;
    private int gridSize;
    private Difficulty difficulty;

    private boolean musicEnabled;

    private boolean soundEffectsEnabled;

    private ArrayList<Result> results = new ArrayList<>();;

    private GameManager() {
        PREFS = Gdx.app.getPreferences("NurikabeGamePreferences");

        // Load grid size or set to default
        gridSize = PREFS.getInteger(GRID_SIZE_KEY, DEFAULT_GRID_SIZE);

        // Load difficulty or set to default
        String difficultyStr = PREFS.getString(DIFFICULTY_KEY, DEFAULT_DIFFICULTY);
        difficulty = Difficulty.fromString(difficultyStr);

        musicEnabled = PREFS.getBoolean(MUSIC_ENABLED_KEY, DEFAULT_MUSIC_ENABLED);
        soundEffectsEnabled = PREFS.getBoolean(SOUND_EFFECTS_ENABLED_KEY, DEFAULT_SOUND_EFFECTS_ENABLED);

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


    public void addResult(int time){
        results.add(new Result(time, difficulty, gridSize));
    }

    public ArrayList<Result> getResults(){
        return results;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public boolean areSoundEffectsEnabled() {
        return soundEffectsEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled){
        this.musicEnabled = musicEnabled;
        PREFS.putBoolean(MUSIC_ENABLED_KEY, musicEnabled);
        PREFS.flush();
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled){
        this.soundEffectsEnabled = soundEffectsEnabled;
        PREFS.putBoolean(SOUND_EFFECTS_ENABLED_KEY, soundEffectsEnabled);
        PREFS.flush();
    }
}


