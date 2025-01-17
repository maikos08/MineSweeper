package software.ulpgc.MineSweeper.arquitecture.model;

public class CustomDifficulty implements Difficulty {
    private final int rows;
    private final int columns;
    private final int mines;

    public CustomDifficulty(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getMineCount() {
        return mines;
    }

}
