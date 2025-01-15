package software.ulpgc.MineSweeper.arquitecture.model;

import software.ulpgc.MineSweeper.arquitecture.services.game.MineCounter;
import software.ulpgc.MineSweeper.arquitecture.services.game.MinePlacer;

import java.util.Arrays;

public record Board(int rows, int columns, int mineCount, Cell[][] cells) {

    private static final MinePlacer minePlacer = new MinePlacer();
    private static final MineCounter mineCounter = new MineCounter();

    public Board(int rows, int columns, int mineCount) {
        this(rows, columns, mineCount, initializeCells(rows, columns, mineCount));
    }

    private static Cell[][] initializeCells(int rows, int columns, int mineCount) {
        Cell[][] cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(false, false, false, 0);
            }
        }
        Cell[][] cellsWithMines = minePlacer.placeMines(cells, mineCount);

        return mineCounter.countAdjacentMines(cellsWithMines);
    }

    public Board updateCell(int row, int col) {
        Cell cell = cells[row][col];
        if (cell.isRevealed() || cell.isFlagged()) {
            return this;
        }

        Cell updatedCell = cell.reveal();
        Cell[][] updatedCells = Arrays.stream(cells)
                .map(rowCells -> Arrays.copyOf(rowCells, rowCells.length))
                .toArray(Cell[][]::new);
        updatedCells[row][col] = updatedCell;

        return new Board(rows, columns, mineCount, updatedCells);
    }

    public int adjacentMines(int row, int col) {
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

    public static void main(String[] args) {
        // Create a Board instance
        int rows = 5;
        int columns = 5;
        int mineCount = 5;

        Board board = new Board(rows, columns, mineCount);

        System.out.println("Initial Board:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = board.cells()[i][j];
                System.out.print(cell.hasMine() ? "M " : ". ");
            }
            System.out.println();
        }

        System.out.println("\nUpdating cell at (2, 2)...");
        board = board.updateCell(2, 2);

        System.out.println("Updated Board:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = board.cells()[i][j];
                if (cell.isRevealed()) {
                    System.out.print(cell.adjacentMines() + " ");
                } else if (cell.hasMine()) {
                    System.out.print("M ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

}