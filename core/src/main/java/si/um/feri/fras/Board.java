package si.um.feri.fras;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import si.um.feri.fras.assets.RegionNames;

public class Board extends Actor {
    private final int rows;
    private final int cols;
    private final float cellSize;
    private final int[][] cellStates; // 2D array to represent cell states
    private final TextureAtlas gameplayAtlas; // Reference to gameplayAtlas
    private final TextureRegion cellTexture; // Example texture for cells

    public Board(int rows, int cols, float cellSize, TextureAtlas gameplayAtlas) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.gameplayAtlas = gameplayAtlas;

        // Initialize the 2D array for cell states
        cellStates = new int[rows][cols];

        // Example: Get a texture region from the gameplayAtlas (adjust the name as needed)
        //TODO Posodobi at some point
        cellTexture = gameplayAtlas.findRegion(RegionNames.TILE_NEUTRAL);

        // Set the size of the actor
        setWidth(cols * cellSize);
        setHeight(rows * cellSize);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                batch.draw(
                    cellTexture, // Use the texture from the gameplayAtlas
                    getX() + col * cellSize,
                    getY() + row * cellSize,
                    cellSize, cellSize
                );
            }
        }
    }

    // Getter for the 2D array
    public int[][] getCellStates() {
        return cellStates;
    }

}

