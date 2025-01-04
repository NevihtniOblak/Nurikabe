package si.um.feri.fras.lwjgl3;

import com.badlogic.gdx.utils.Json;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.badlogic.gdx.utils.Array;


import si.um.feri.fras.global.loading.BoardConfiguration;
import si.um.feri.fras.global.loading.Island;

public class BoardsPacker {

    private static final String OUTPUT_DIRECTORY = "assets/boards";

    private final Array<BoardConfiguration> boards;
    public BoardsPacker() {
        boards = new Array<>();
    }

    // Method to add a board to the map
    public void addBoard(BoardConfiguration board) {
        boards.add(board);
    }

    public void writeToJson(String fileName) {
        createOutputDirectory();
        Json json = new Json();

        // Copy boards without any anonymous inner class
        Array<BoardConfiguration> boardcpy = new Array<>();
        for (BoardConfiguration board : boards) {
            boardcpy.add(board);
        }

        String jsonString = json.prettyPrint(boardcpy);
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

        Array<Island> islands = new Array<>();
        islands.add(new Island(0, 0, 1));
        islands.add(new Island(1, 1, 2));
        islands.add(new Island(1, 3, 2));
        islands.add(new Island(3, 2, 1));
        islands.add(new Island(4, 0, 1));
        islands.add(new Island(4, 4, 1));
        boardsPacker.addBoard(new BoardConfiguration(5, islands));

        islands.clear();



        // Save all boards to a JSON file
        boardsPacker.writeToJson("nurikabeBoards.json");
    }
}
