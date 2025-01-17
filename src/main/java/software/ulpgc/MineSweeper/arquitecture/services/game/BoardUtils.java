package software.ulpgc.MineSweeper.arquitecture.services.game;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;

import java.util.Arrays;

public class BoardUtils {
    public static Cell[][] cloneCells(Cell[][] cells) {
        return Arrays.stream(cells)
                .map(row -> Arrays.copyOf(row, row.length))
                .toArray(Cell[][]::new);
    }

    public static boolean isValidPosition(int row, int col, int rows, int columns) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }
}
