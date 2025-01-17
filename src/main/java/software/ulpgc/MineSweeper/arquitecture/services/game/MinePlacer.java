package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;

import java.util.Random;

public class MinePlacer {

    private final Random random = new Random();

    public Cell[][] placeMines(Cell[][] cells, int mineCount) {
        return placeMines(cells, mineCount, -1, -1);
    }

    public Cell[][] placeMines(Cell[][] cells, int mineCount, int avoidRow, int avoidCol) {
        int rows = cells.length;
        int columns = cells[0].length;

        int placedMines = 0;
        while (placedMines < mineCount) {
            int x = random.nextInt(rows);
            int y = random.nextInt(columns);

            if (isValidMinePosition(cells, x, y, avoidRow, avoidCol)) {
                placeMine(cells, x, y);
                placedMines++;
            }
        }
        return cells;
    }

    private boolean isValidMinePosition(Cell[][] cells, int x, int y, int avoidRow, int avoidCol) {
        return !cells[x][y].hasMine() && isOutsideReservedArea(avoidRow, avoidCol, x, y);
    }
    private boolean isOutsideReservedArea(int avoidRow, int avoidCol, int x, int y) {
        if (avoidRow == -1 || avoidCol == -1) {
            return true;
        }
        return x < avoidRow - 1 || x > avoidRow + 1 || y < avoidCol - 1 || y > avoidCol + 1;
    }

    private void placeMine(Cell[][] cells, int x, int y) {
        cells[x][y] = new Cell(true, false, false, 0);
    }
}
