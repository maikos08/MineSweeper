package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.services.game.MineCounter;
import software.ulpgc.MineSweeper.arquitecture.services.game.MinePlacer;

public class BoardInitializer {
    private static final MinePlacer minePlacer = new MinePlacer();
    private static final MineCounter mineCounter = new MineCounter();

    public static Cell[][] initializeCells(int rows, int columns, int mineCount) {
        Cell[][] cells = createEmptyCells(rows, columns);
        Cell[][] cellsWithMines = minePlacer.placeMines(cells, mineCount);
        return mineCounter.countAdjacentMines(cellsWithMines);
    }

    public static Cell[][] initializeCellsAvoidingPosition(int rows, int columns, int mineCount, int avoidRow, int avoidCol) {
        Cell[][] cells = createEmptyCells(rows, columns);
        Cell[][] cellsWithMines = minePlacer.placeMines(cells, mineCount, avoidRow, avoidCol);
        return mineCounter.countAdjacentMines(cellsWithMines);
    }

    private static Cell[][] createEmptyCells(int rows, int columns) {
        Cell[][] cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(false, false, false, 0);
            }
        }
        return cells;
    }
}
