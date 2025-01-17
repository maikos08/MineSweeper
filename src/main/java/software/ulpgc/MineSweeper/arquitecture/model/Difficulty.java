package software.ulpgc.MineSweeper.arquitecture.model;

public enum Difficulty {

    EASY(8, 8, 10),
    MEDIUM(16, 16, 40),
    HARD(24, 31, 99),
    PERSONALIZED(8,8,10);

    private int rows;
    private int columns;
    private int mineCount;

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


    public void setRows(int rows) {
        if (this == PERSONALIZED) {
            this.rows = rows;
        } else {
            throw new UnsupportedOperationException("No se pueden modificar los valores de dificultades predefinidas.");
        }
    }

    public void setColumns(int columns) {
        if (this == PERSONALIZED) {
            this.columns = columns;
        } else {
            throw new UnsupportedOperationException("No se pueden modificar los valores de dificultades predefinidas.");
        }
    }

    public void setMineCount(int mineCount) {
        if (this == PERSONALIZED) {
            this.mineCount = mineCount;
        } else {
            throw new UnsupportedOperationException("No se pueden modificar los valores de dificultades predefinidas.");
        }
    }
}
