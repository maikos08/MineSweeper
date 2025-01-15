package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;

public class MineCounter {

    public Cell[][] countAdjacentMines(Cell[][] cells) {
        int rows = cells.length;
        int columns = cells[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int count = 0;
                for (int x = i - 1; x <= i + 1; x++) {
                    for (int y = j - 1; y <= j + 1; y++) {
                        if (x >= 0 && x < rows && y >= 0 && y < columns) {
                            if (cells[x][y].hasMine()) {
                                count++;
                            }
                        }
                    }
                }
                cells[i][j] = cells[i][j].withAdjacentMines(count);
            }
        }

        return cells;
    }

}
