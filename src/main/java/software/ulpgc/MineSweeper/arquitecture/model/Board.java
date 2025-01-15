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
        boolean[][] visited = new boolean[rows][columns];
        Cell[][] updatedCells = Arrays.stream(cells)
                .map(rowCells -> Arrays.copyOf(rowCells, rowCells.length))
                .toArray(Cell[][]::new);

        revealCells(row, col, updatedCells, visited);

        return new Board(rows, columns, mineCount, updatedCells);
    }

    private void revealCells(int row, int col, Cell[][] updatedCells, boolean[][] visited) {
        if (row < 0 || row >= rows || col < 0 || col >= columns || visited[row][col]) {
            return;
        }

        Cell cell = updatedCells[row][col];
        if (cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        // Mark as visited
        visited[row][col] = true;

        // Reveal the cell
        updatedCells[row][col] = cell.reveal();

        // If the cell has no adjacent mines, reveal its neighbors
        if (cell.adjacentMines() == 0) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    revealCells(i, j, updatedCells, visited);
                }
            }
        }
    }

    public Board setFlag(int row, int col) {
        Cell cell = cells[row][col];
        if (cell.isRevealed()) {
            return this;
        }

        Cell updatedCell = cell.toggleFlag();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                sb.append(cell.isRevealed() ? (cell.hasMine() ? "X" : cell.adjacentMines()) : ".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Board revealMines() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[1].length; j++) {
                if (cells[i][j].hasMine())
                    cells[i][j] = new Cell(cells[i][j].hasMine(), false, true, cells[i][j].adjacentMines());
            }
        }
        return new Board(rows, columns, mineCount, cells);
    }
}