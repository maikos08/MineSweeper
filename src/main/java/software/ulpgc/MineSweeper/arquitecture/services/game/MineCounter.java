package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;

public class MineCounter {

    public Cell[][] countAdjacentMines(Cell[][] cells) {
        int rows = cells.length;
        int columns = cells[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int count = calculateAdjacentMines(i, j, cells, rows, columns);
                cells[i][j] = cells[i][j].updateAdjacentMines(count);
            }
        }

        return cells;
    }

    public int calculateAdjacentMines(int row, int col, Cell[][] cells, int rows, int columns) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    if (cells[i][j].hasMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
