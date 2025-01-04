package si.um.feri.fras.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

import si.um.feri.fras.global.loading.BoardConfiguration;
import si.um.feri.fras.global.loading.Island;

public class GameManager {

    private static final String DATA_FILE = "results.json";

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

    private ArrayList<Result> results = new ArrayList<>();

    private Array<BoardConfiguration> boards = new Array<>();

    private GameManager() {
        loadResults();

        loadBoards();

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

    public void saveResults() {
        FileHandle fileHandle = Gdx.files.local(DATA_FILE);  // Save file in local storage
        Json json = new Json();  // Create a new Json object

        // Serialize the results ArrayList into a JSON string
        String jsonData = json.toJson(results);

        // Write the JSON string to the file
        fileHandle.writeString(jsonData, false);
    }


    public void loadResults() {
        FileHandle fileHandle = Gdx.files.local(DATA_FILE);  // Load file from local storage

        if (fileHandle.exists()) {
            Json json = new Json();  // Create a new Json object

            // Read the JSON string from the file
            String jsonData = fileHandle.readString();

            // Deserialize the JSON string into an ArrayList<Result>
            results = json.fromJson(ArrayList.class, Result.class, jsonData);
        }
    }

    public void loadBoards() {
        // Load file from assets/boards/ directory
        FileHandle fileHandle = Gdx.files.internal("boards/nurikabeBoards.json");

        if (fileHandle.exists()) {
            // Create a new Json object
            Json json = new Json();

            // Read the JSON string from the file
            String jsonData = fileHandle.readString();

            // Deserialize the JSON string into an ArrayList<BoardConfiguration>
            boards = json.fromJson(Array.class, BoardConfiguration.class, jsonData);
            System.out.println(boards.get(0).getCells().size);
        } else {
            System.out.println("File not found: boards/nurikabeBoards.json");
        }
    }


    public Array<BoardConfiguration> getBoards() {
        return boards;
    }

    public Array<Island> getRandomBoardBySize(int size) {
        System.out.println(size);
        System.out.println(boards);
        //TODO Make random
        Array<Island> islands = new Array<>();
        for (BoardConfiguration board : boards) {
            if (board.getGridSize() == size) {
                islands = board.getCells();
            }
        }

        return islands;
    }

}


