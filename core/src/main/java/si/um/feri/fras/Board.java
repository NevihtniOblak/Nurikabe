package si.um.feri.fras;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;

import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.assets.RegionNames;
import si.um.feri.fras.global.CellState;
import si.um.feri.fras.global.GameManager;
import si.um.feri.fras.global.loading.Island;

public class Board extends Actor {
    private final int rows;
    private final int cols;
    private final float cellSize;
    private final CellState[][] cellStates; // 2D array to represent cell states
    private final TextureAtlas gameplayAtlas; // Reference to gameplayAtlas
    private final TextureRegion neutralCellTexture;
    private final TextureRegion blackCellTexture;
    private final TextureRegion markedCellTexture;

    private final Sound putBlackSound;

    private final Sound putDotSound;

    private final ArrayList<Island> islands;

    public Board(int rows, int cols, float cellSize, AssetManager assetManager, ArrayList<Island> islands) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.gameplayAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);
        this.islands = islands;

        cellStates = new CellState[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellStates[row][col] = CellState.NEUTRAL;
            }
        }

        //TODO Da so številke
        System.out.println(islands);
        for (Island island : islands) {
            cellStates[island.getRow()][island.getCol()] = CellState.BLACK;
        }

        neutralCellTexture = gameplayAtlas.findRegion(RegionNames.TILE_NEUTRAL);
        blackCellTexture = gameplayAtlas.findRegion(RegionNames.TILE_BLACK);
        markedCellTexture = gameplayAtlas.findRegion(RegionNames.TILE_MARKED);

        putBlackSound = assetManager.get(AssetDescriptors.PUT_BLACK_SOUND_2);
        putBlackSound.setVolume(1,1f);
        putDotSound = assetManager.get(AssetDescriptors.PUT_DOT_SOUND_2);
        putDotSound.setVolume(0, 1f);


        setWidth(cols * cellSize);
        setHeight(rows * cellSize);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Convert touch coordinates from Board's local space
                int[] cell = getCellFromCoordinates(x, y);
                if (cell != null) {
                    int row = cell[0];
                    int col = cell[1];
                    handleCellClick(row, col); // Call your method to handle the click
                }
                return true; // Indicate the touch was handled
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion cellTexture;

        // Loop through all cells
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                // Get the current cell's state
                CellState cellState = cellStates[row][col];  // Assuming cellStates[row][col] is not null

                // Determine the texture based on the cell's state
                switch (cellState) {
                    case NEUTRAL:
                        cellTexture = neutralCellTexture;
                        break;
                    case BLACK:
                        cellTexture = blackCellTexture;
                        break;
                    case MARKED:
                        cellTexture = markedCellTexture;
                        break;
                    default:
                        throw new IllegalStateException("Unknown cell state: " + cellState);
                }

                // Draw the cell with the appropriate texture
                batch.draw(
                    cellTexture,
                    getX() + col * cellSize,
                    getY() + row * cellSize,
                    cellSize,
                    cellSize
                );
            }
        }
    }


    public CellState[][] getCellStates() {
        return cellStates;
    }


    private int[] getCellFromCoordinates(float x, float y) {
        // Calculate the cell clicked based on the touch position
        int col = (int) (x / cellSize);
        int row = (int) (y / cellSize);

        // Ensure the clicked position is within bounds
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return new int[]{row, col};
        }
        return null; // Return null if the click is outside the grid bounds
    }

    private void handleCellClick(int row, int col) {
        // Handle the click on the cell
        System.out.println("Cell clicked: Row " + row + ", Column " + col);

        // Get the current state of the cell
        CellState currentState = cellStates[row][col];

        // Use a switch statement to cycle through the states
        switch (currentState) {
            case NEUTRAL:
                cellStates[row][col] = CellState.BLACK;  // Change to BLACK if it was NEUTRAL
                if(GameManager.INSTANCE.areSoundEffectsEnabled()) {
                    putBlackSound.play();
                }
                break;
            case BLACK:
                cellStates[row][col] = CellState.MARKED;  // Change to MARKED if it was BLACK
                if(GameManager.INSTANCE.areSoundEffectsEnabled()) {
                    putDotSound.play();
                }
                break;
            case MARKED:
                cellStates[row][col] = CellState.NEUTRAL;  // Change to NEUTRAL if it was MARKED
                break;
            default:
                System.out.println("Immutable or unknown cell state!");
        }
    }


    private void validateSolution() {
        // Check if the current board state is a solution
        // Implement your solution validation logic here
    }

    private void loadBoard() {
        // Load a board from a JSON file
        // Implement your board loading logic here
    }


}

