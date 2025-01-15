package si.um.feri.fras;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.assets.RegionNames;
import si.um.feri.fras.global.CellState;
import si.um.feri.fras.global.ColorTheme;
import si.um.feri.fras.global.GameManager;
import si.um.feri.fras.global.loading.Island;

public class Board extends Actor {
    private final int rows;
    private final int cols;
    private final float cellSize;
    private final CellState[][] cellStates; // 2D array to represent cell state

    private final boolean[][] visited; // 2D array to represent visited cells
    private final TextureAtlas gameplayAtlas; // Reference to gameplayAtlas
    private final TextureRegion neutralCellTexture;
    private final TextureRegion blackCellTexture;
    private final TextureRegion markedCellTexture;

    private final TextureRegion island1Texture;
    private final TextureRegion island2Texture;
    private final TextureRegion island3Texture;
    private final TextureRegion island4Texture;
    private final TextureRegion island5Texture;
    private final TextureRegion island6Texture;
    private final TextureRegion island7Texture;
    private final TextureRegion island8Texture;
    private final TextureRegion island9Texture;
    private final TextureRegion island10Texture;

    //MAGMA

    private final TextureRegion neutralMagmaTexture;
    private final TextureRegion markedMagmaTexture;
    private final TextureRegion liquidMagmaTexture;
    private final TextureRegion island1TextureMagma;
    private final TextureRegion island2TextureMagma;
    private final TextureRegion island3TextureMagma;
    private final TextureRegion island4TextureMagma;
    private final TextureRegion island5TextureMagma;
    private final TextureRegion island6TextureMagma;
    private final TextureRegion island7TextureMagma;
    private final TextureRegion island8TextureMagma;
    private final TextureRegion island9TextureMagma;
    private final TextureRegion island10TextureMagma;

    //AQUA

    private final TextureRegion neutralAquaTexture;

    private final TextureRegion liquidAquaTexture;

    private final TextureRegion markedAquaTexture;

    private final TextureRegion island1TextureAqua;

    private final TextureRegion island2TextureAqua;

    private final TextureRegion island3TextureAqua;

    private final TextureRegion island4TextureAqua;

    private final TextureRegion island5TextureAqua;

    private final TextureRegion island6TextureAqua;

    private final TextureRegion island7TextureAqua;

    private final TextureRegion island8TextureAqua;

    private final TextureRegion island9TextureAqua;

    private final TextureRegion island10TextureAqua;

    private final Sound putBlackSound;

    private final Sound putDotSound;

    private final Array<Island> islands;

    public Board(int rows, int cols, float cellSize, AssetManager assetManager, Array<Island> islands) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.gameplayAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);
        this.islands = islands;

        cellStates = new CellState[rows][cols];
        visited = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellStates[row][col] = CellState.NEUTRAL;
                visited[row][col] = false;
            }
        }

        //TODO Da so številke
        for (Island island : islands) {
            cellStates[island.getRow()][island.getCol()] = CellState.ISLAND;
        }

        //Classic
        neutralCellTexture = gameplayAtlas.findRegion(RegionNames.TILE_NEUTRAL);
        blackCellTexture = gameplayAtlas.findRegion(RegionNames.TILE_BLACK);
        markedCellTexture = gameplayAtlas.findRegion(RegionNames.TILE_MARKED);
        island1Texture = gameplayAtlas.findRegion(RegionNames.TILE_1);
        island2Texture = gameplayAtlas.findRegion(RegionNames.TILE_2);
        island3Texture = gameplayAtlas.findRegion(RegionNames.TILE_3);
        island4Texture = gameplayAtlas.findRegion(RegionNames.TILE_4);
        island5Texture = gameplayAtlas.findRegion(RegionNames.TILE_5);
        island6Texture = gameplayAtlas.findRegion(RegionNames.TILE_6);
        island7Texture = gameplayAtlas.findRegion(RegionNames.TILE_7);
        island8Texture = gameplayAtlas.findRegion(RegionNames.TILE_8);
        island9Texture = gameplayAtlas.findRegion(RegionNames.TILE_9);
        island10Texture = gameplayAtlas.findRegion(RegionNames.TILE_10);
        //Magma
        neutralMagmaTexture = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_NEUTRAL);
        markedMagmaTexture = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_MARKED);
        liquidMagmaTexture = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_LIQUID);
        island1TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_1);
        island2TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_2);
        island3TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_3);
        island4TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_4);
        island5TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_5);
        island6TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_6);
        island7TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_7);
        island8TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_8);
        island9TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_9);
        island10TextureMagma = gameplayAtlas.findRegion(RegionNames.TILE_MAGMA_10);
        //Aqua
        neutralAquaTexture = gameplayAtlas.findRegion(RegionNames.TILE_WATER_NEUTRAL);
        liquidAquaTexture = gameplayAtlas.findRegion(RegionNames.TILE_WATER_LIQUID);
        markedAquaTexture = gameplayAtlas.findRegion(RegionNames.TILE_WATER_MARKED);
        island1TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_1);
        island2TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_2);
        island3TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_3);
        island4TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_4);
        island5TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_5);
        island6TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_6);
        island7TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_7);
        island8TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_8);
        island9TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_9);
        island10TextureAqua = gameplayAtlas.findRegion(RegionNames.TILE_WATER_10);

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
                ColorTheme colorTheme = GameManager.INSTANCE.getColorTheme();

                if(colorTheme == ColorTheme.BASIC){
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
                        case ISLAND:
                            cellTexture = getIslandTexture(row,col);
                            break;
                        default:
                            throw new IllegalStateException("Unknown cell state: " + cellState);
                    }
                }
                else if(colorTheme == ColorTheme.MAGMA){
                    switch (cellState) {
                        case NEUTRAL:
                            cellTexture = neutralMagmaTexture;
                            break;
                        case BLACK:
                            cellTexture = liquidMagmaTexture;
                            break;
                        case MARKED:
                            cellTexture = markedMagmaTexture;
                            break;
                        case ISLAND:
                            cellTexture = getIslandTexture(row,col);
                            break;
                        default:
                            throw new IllegalStateException("Unknown cell state: " + cellState);
                    }
                }
                else if(colorTheme == ColorTheme.AQUA){
                    switch (cellState) {
                        case NEUTRAL:
                            cellTexture = neutralAquaTexture;
                            break;
                        case BLACK:
                            cellTexture = liquidAquaTexture;
                            break;
                        case MARKED:
                            cellTexture = markedAquaTexture;
                            break;
                        case ISLAND:
                            cellTexture = getIslandTexture(row,col);
                            break;
                        default:
                            throw new IllegalStateException("Unknown cell state: " + cellState);
                    }
                }
                else{
                    cellTexture = null;
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

    private TextureRegion getIslandTexture(int row, int col) {
        for (Island island : islands) {
            if (island.getRow() == row && island.getCol() == col) {
                int islandNumber = island.getIsland();
                ColorTheme colorTheme = GameManager.INSTANCE.getColorTheme();

                if(colorTheme == ColorTheme.BASIC){
                    switch (islandNumber){
                        case 1:
                            return island1Texture;
                        case 2:
                            return island2Texture;
                        case 3:
                            return island3Texture;
                        case 4:
                            return island4Texture;
                        case 5:
                            return island5Texture;
                        case 6:
                            return island6Texture;
                        case 7:
                            return island7Texture;
                        case 8:
                            return island8Texture;
                        case 9:
                            return island9Texture;
                        case 10:
                            return island10Texture;
                    }
                }
                else if(colorTheme == ColorTheme.MAGMA){
                    switch (islandNumber){
                        case 1:
                            return island1TextureMagma;
                        case 2:
                            return island2TextureMagma;
                        case 3:
                            return island3TextureMagma;
                        case 4:
                            return island4TextureMagma;
                        case 5:
                            return island5TextureMagma;
                        case 6:
                            return island6TextureMagma;
                        case 7:
                            return island7TextureMagma;
                        case 8:
                            return island8TextureMagma;
                        case 9:
                            return island9TextureMagma;
                        case 10:
                            return island10TextureMagma;
                    }
                }
                else if(colorTheme == ColorTheme.AQUA){
                    switch (islandNumber){
                        case 1:
                            return island1TextureAqua;
                        case 2:
                            return island2TextureAqua;
                        case 3:
                            return island3TextureAqua;
                        case 4:
                            return island4TextureAqua;
                        case 5:
                            return island5TextureAqua;
                        case 6:
                            return island6TextureAqua;
                        case 7:
                            return island7TextureAqua;
                        case 8:
                            return island8TextureAqua;
                        case 9:
                            return island9TextureAqua;
                        case 10:
                            return island10TextureAqua;
                    }
                }
                else{
                    return null;
                }
            }
        }
        return null;
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


    public boolean validateSolution() {
        int blackCells = 0;
        int randomBlackCell = -1;
        //Check for 2x2
        for(int row = 0; row < rows - 1; row++) {
            for(int col = 0; col < cols - 1; col++) {
                if(cellStates[row][col] == CellState.BLACK &&
                    cellStates[row + 1][col] == CellState.BLACK &&
                    cellStates[row][col + 1] == CellState.BLACK &&
                    cellStates[row + 1][col + 1] == CellState.BLACK) {
                    return false;
                }
            }
        }
        System.out.println("2x2 check passed");
        //count black cells
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(cellStates[row][col] == CellState.BLACK) {
                    blackCells++;
                    randomBlackCell = row * cols + col;
                }
            }
        }

        if(blackCells == 0) {
            return false;
        }

        System.out.println("Black cells count passed");
        //Bfs for black cells
        int blackCellsFound = 1;
        resetVisited();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(randomBlackCell);
        visited[randomBlackCell / cols][randomBlackCell % cols] = true;
        while(!queue.isEmpty()) {
            int currentCell = queue.poll();
            int row = currentCell / cols;
            int col = currentCell % cols;
            if(row > 0 && cellStates[row - 1][col] == CellState.BLACK && !visited[row - 1][col]) {
                queue.add((row - 1) * cols + col);
                visited[row - 1][col] = true;
                blackCellsFound++;
            }
            if(row < rows - 1 && cellStates[row + 1][col] == CellState.BLACK && !visited[row + 1][col]) {
                queue.add((row + 1) * cols + col);
                visited[row + 1][col] = true;
                blackCellsFound++;
            }
            if(col > 0 && cellStates[row][col - 1] == CellState.BLACK && !visited[row][col - 1]) {
                queue.add(row * cols + col - 1);
                visited[row][col - 1] = true;
                blackCellsFound++;
            }
            if(col < cols - 1 && cellStates[row][col + 1] == CellState.BLACK && !visited[row][col + 1]) {
                queue.add(row * cols + col + 1);
                visited[row][col + 1] = true;
                blackCellsFound++;
            }
        }
        if(blackCellsFound != blackCells) {
            return false;
        }

        System.out.println("Black cells bfs passed");

        //Bfs for each island
        resetVisited();
        for (Island island : islands) {
            int islandCellsFound = 0;
            int islandNumber = island.getIsland();
            int islandRow = island.getRow();
            int islandCol = island.getCol();
            queue.clear();
            queue.add(islandRow * cols + islandCol);
            visited[islandRow][islandCol] = true;
            while(!queue.isEmpty()) {
                int currentCell = queue.poll();
                int row = currentCell / cols;
                int col = currentCell % cols;
                islandCellsFound++;

                if(row > 0 && !visited[row - 1][col]) {
                    if(cellStates[row - 1][col] == CellState.ISLAND) {
                        return false;
                    }
                    else if(cellStates[row - 1][col] == CellState.MARKED) {
                        queue.add((row - 1) * cols + col);
                        visited[row - 1][col] = true;
                    }
                }
                if(row < rows - 1 && !visited[row + 1][col]) {
                    if(cellStates[row + 1][col] == CellState.ISLAND) {
                        return false;
                    }
                    else if(cellStates[row + 1][col] == CellState.MARKED) {
                        queue.add((row + 1) * cols + col);
                        visited[row + 1][col] = true;
                    }
                }
                if(col > 0 && !visited[row][col - 1]) {
                    if(cellStates[row][col - 1] == CellState.ISLAND) {
                        return false;
                    }
                    else if(cellStates[row][col - 1] == CellState.MARKED) {
                        queue.add(row * cols + col - 1);
                        visited[row][col - 1] = true;
                    }
                }
                if(col < cols - 1 &&  !visited[row][col + 1]) {
                    if(cellStates[row][col + 1] == CellState.ISLAND) {
                        return false;
                    }
                    else if(cellStates[row][col + 1] == CellState.MARKED) {
                        queue.add(row * cols + col + 1);
                        visited[row][col + 1] = true;
                    }
                }
            }
            if(islandCellsFound != islandNumber) {
                System.out.println(islandCellsFound);
                System.out.println(islandNumber);

                System.out.println("Island " + islandNumber + " check failed");

                return false;
            }
        }

        System.out.println("Islands bfs check passed");

        return true;

    }

    private void resetVisited() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                visited[row][col] = false;
            }
        }
    }

}

