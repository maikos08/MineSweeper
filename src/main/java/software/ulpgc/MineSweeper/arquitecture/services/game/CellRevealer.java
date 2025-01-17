package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.control.Observer;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;

import java.util.List;

public class CellRevealer {
    public static Cell[][] revealCells(int row, int col, Cell[][] cells, List<Observer> observers) {
        boolean[][] visited = new boolean[cells.length][cells[0].length];
        Cell[][] updatedCells = BoardUtils.cloneCells(cells);

        revealCellsRecursive(row, col, updatedCells, visited, observers);
        return updatedCells;
    }

    private static void revealCellsRecursive(int row, int col, Cell[][] cells, boolean[][] visited, List<Observer> observers) {
        if (!BoardUtils.isValidPosition(row, col, cells.length, cells[0].length) || visited[row][col]) {
            return;
        }

        Cell cell = cells[row][col];
        if (cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        visited[row][col] = true;
        cells[row][col] = cell.revealCell();
        notifyObservers(cell, observers);

        if (cell.adjacentMines() == 0) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    revealCellsRecursive(i, j, cells, visited, observers);
                }
            }
        }
    }

    private static void notifyObservers(Cell cell, List<Observer> observers) {
        for (Observer observer : observers) {
            observer.notify(cell);
        }
    }
}
