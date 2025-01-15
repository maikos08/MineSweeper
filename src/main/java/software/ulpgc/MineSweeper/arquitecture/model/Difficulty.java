package software.ulpgc.MineSweeper.arquitecture.model;

public enum Difficulty {

    EASY(8, 8, 10),       // 8x8 board with 10 mines
    MEDIUM(16, 16, 40),   // 16x16 board with 40 mines
    HARD(16, 31, 99);     // 24x24 board with 99 mines

    private final int rows;
    private final int columns;
    private final int mineCount;

    Difficulty(int rows, int columns, int mineCount) {
        this.rows = rows;
        this.columns = columns;
        this.mineCount = mineCount;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMineCount() {
        return mineCount;
    }
}
