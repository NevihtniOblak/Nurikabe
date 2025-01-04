package si.um.feri.fras.global.loading;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class BoardConfiguration {
    private final int gridSize;
    private final Array<Island> islands;

    public BoardConfiguration() {
        this.gridSize = -1;
        this.islands = new Array<>();
    }

    public BoardConfiguration(int gridSize) {
        this.gridSize = gridSize;
        this.islands = new Array<>();
    }

    public BoardConfiguration(int gridSize, Array<Island> islands) {
        this.gridSize = gridSize;
        this.islands = islands;
    }

    public void addCell(Island island) {
        islands.add(island);
    }

    public int getGridSize() {
        return gridSize;
    }

    public Array<Island> getCells() {
        return islands;
    }

}
