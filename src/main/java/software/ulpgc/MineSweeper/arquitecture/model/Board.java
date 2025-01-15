package software.ulpgc.MineSweeper.arquitecture.model;

import software.ulpgc.MineSweeper.arquitecture.services.game.MinePlacer;

import java.util.Arrays;

public record Board(int rows, int columns, int mineCount, Cell[][] cells) {
    private static final MinePlacer minePlacer = new MinePlacer();

    public Board(int rows, int columns, int mineCount) {
        this(rows, columns, mineCount, initializeCells(rows, columns, mineCount));
    }

    private static Cell[][] initializeCells(int rows, int columns, int mineCount) {
        Cell[][] cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(false, false, false, 0);
            }
        }
        return minePlacer.placeMines(cells, mineCount);
    }

    public Board updateCell(int row, int col, Cell updatedCell) {
        Cell[][] newCells = Arrays.copyOf(this.cells, this.cells.length);
        newCells[row][col] = updatedCell;
        return new Board(rows, columns, mineCount, newCells);
    }

}