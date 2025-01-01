package si.um.feri.fras.global.loading;

import java.util.ArrayList;
import java.util.List;

public class BoardConfiguration {
    private final int gridSize;
    private final ArrayList<Island> islands;

    public BoardConfiguration() {
        this.gridSize = -1;
        this.islands = new ArrayList<>();
    }

    public BoardConfiguration(int gridSize) {
        this.gridSize = gridSize;
        this.islands = new ArrayList<>();
    }

    public BoardConfiguration(int gridSize, ArrayList<Island> islands) {
        this.gridSize = gridSize;
        this.islands = islands;
    }

    public void addCell(Island island) {
        islands.add(island);
    }

    public int getGridSize() {
        return gridSize;
    }

    public ArrayList<Island> getCells() {
        return islands;
    }
}
