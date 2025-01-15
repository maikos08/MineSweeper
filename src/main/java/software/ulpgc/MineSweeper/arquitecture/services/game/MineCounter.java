package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;

public class MineCounter {

    public Board countAdjacentMines(Board board) {
        Cell[][] cells = board.cells();
        int rows = board.rows();
        int columns = board.columns();

        Cell[][] updatedCells = new Cell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell currentCell = cells[i][j];

                if (currentCell.hasMine()) {
                    // If the cell has a mine, its adjacent count remains 0
                    updatedCells[i][j] = currentCell;
                } else {
                    // Count the adjacent mines
                    int adjacentMines = countMinesAround(cells, rows, columns, i, j);
                    updatedCells[i][j] = new Cell(false, currentCell.isFlagged(), currentCell.isRevealed(), adjacentMines);
                }
            }
        }

        // Return a new Board with the updated cells
        return new Board(rows, columns, board.mineCount(), updatedCells);
    }

    private int countMinesAround(Cell[][] cells, int rows, int columns, int x, int y) {
        int count = 0;

        // Iterate over the 8 possible neighbors
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue; // Skip the current cell
                }

                int nx = x + dx;
                int ny = y + dy;

                // Check if the neighbor is within bounds and has a mine
                if (nx >= 0 && nx < rows && ny >= 0 && ny < columns && cells[nx][ny].hasMine()) {
                    count++;
                }
            }
        }

        return count;
    }
}
