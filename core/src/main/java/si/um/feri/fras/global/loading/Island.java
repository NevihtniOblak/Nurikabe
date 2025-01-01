package si.um.feri.fras.global.loading;

public class Island {
    private final int row;
    private final int col;
    private final int island;

    public Island() {
        this.row = -1;
        this.col = -1;
        this.island = -1;
    }

    public Island(int row, int col, int island) {
        this.row = row;
        this.col = col;
        this.island = island;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getIsland() {
        return island;
    }
}
