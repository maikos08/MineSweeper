package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.services.FlagCounter;
import software.ulpgc.MineSweeper.arquitecture.services.game.BoardUtils;

public class CellToogler {
    public static Cell[][] toggleFlag(int row, int col, Cell[][] cells) {
        Cell cell = cells[row][col];
        if (cell.isRevealed()) {
            return cells;
        }

        Cell updatedCell = cell.toggleCellFlag();
        Cell[][] updatedCells = BoardUtils.cloneCells(cells);
        updatedCells[row][col] = updatedCell;

        updateFlagCounter(cell);

        return updatedCells;
    }

    private static void updateFlagCounter(Cell cell) {
        FlagCounter flagCounter = FlagCounter.getInstance();
        modifyFlagCounter(cell, flagCounter);
    }

    private static void modifyFlagCounter(Cell cell, FlagCounter flagCounter) {
        if (!cell.isFlagged()) {
            flagCounter.addFlag();
        } else {
            flagCounter.removeFlag();
        }
    }
}
