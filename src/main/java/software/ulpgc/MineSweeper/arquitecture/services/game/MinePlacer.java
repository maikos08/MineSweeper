package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;

import java.util.Random;

public class MinePlacer {

    public Cell[][] placeMines(Cell[][] cells, int mineCount) {
        int rows = cells.length;
        int columns = cells[0].length;
        Random random = new Random();

        int placedMines = 0;
        while (placedMines < mineCount) {
            int x = random.nextInt(rows);
            int y = random.nextInt(columns);

            if (!cells[x][y].hasMine()) {
                cells[x][y] = new Cell(true, false, false, 0);
                placedMines++;
            }
        }

        return cells;
    }

    public Cell[][] placeMines(Cell[][] cells, int mineCount, int avoidRow, int avoidCol) {
        int rows = cells.length;
        int columns = cells[0].length;
        Random random = new Random();

        int placedMines = 0;
        while (placedMines < mineCount) {
            int x = random.nextInt(rows);
            int y = random.nextInt(columns);

            if (!cells[x][y].hasMine() && (x < avoidRow - 1 || x > avoidRow + 1 || y < avoidCol - 1 || y > avoidCol + 1)) {
                cells[x][y] = new Cell(true, false, false, 0);
                placedMines++;
            }
        }

        return cells;
    }
}
