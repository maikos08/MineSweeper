package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;

public class MineCounter {

    public Cell[][] countAdjacentMines(Cell[][] cells) {
        int rows = cells.length;
        int columns = cells[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int count = getCountOfMines(cells, i, j, rows, columns);
                cells[i][j] = cells[i][j].withAdjacentMines(count);
            }
        }

        return cells;
    }

    private int getCountOfMines(Cell[][] cells, int i, int j, int rows, int columns) {
        int count = 0;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (InBounds(rows, columns, x, y)) {
                    if (cells[x][y].hasMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean InBounds(int rows, int columns, int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < columns;
    }

}
