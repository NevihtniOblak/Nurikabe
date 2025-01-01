package si.um.feri.fras.lwjgl3;

import com.badlogic.gdx.utils.Json;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import si.um.feri.fras.global.loading.BoardConfiguration;
import si.um.feri.fras.global.loading.Island;

public class BoardsPacker {

    private static final String OUTPUT_DIRECTORY = "assets/boards";

    private final ArrayList<BoardConfiguration> boards;
    public BoardsPacker() {
        boards = new ArrayList<>();
    }

    // Method to add a board to the map
    public void addBoard(BoardConfiguration board) {
        boards.add(board);
    }

    public void writeToJson(String fileName) {
        createOutputDirectory();
        Json json = new Json();
        String jsonString = json.prettyPrint(boards);

        File file = new File(OUTPUT_DIRECTORY, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
            System.out.println("Boards saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write boards to JSON: " + e.getMessage());
        }
    }


    private void createOutputDirectory() {
        File directory = new File(OUTPUT_DIRECTORY);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created output directory: " + OUTPUT_DIRECTORY);
            } else {
                System.err.println("Failed to create output directory: " + OUTPUT_DIRECTORY);
            }
        }
    }

    public static void main(String[] args) {
        BoardsPacker boardsPacker = new BoardsPacker();

        boardsPacker.addBoard(new BoardConfiguration(5, new ArrayList<Island>() {{
            add(new Island(0, 0, 1));
            add(new Island(0, 1, 2));
            add(new Island(1, 0, 3));
            add(new Island(1, 1, 4));
        }}));


        // Save all boards to a JSON file
        boardsPacker.writeToJson("nurikabeBoards.json");
    }
}
