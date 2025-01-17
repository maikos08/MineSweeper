package software.ulpgc.MineSweeper.arquitecture.model;

import software.ulpgc.MineSweeper.arquitecture.control.Observer;
import software.ulpgc.MineSweeper.arquitecture.services.game.BoardInitializer;
import software.ulpgc.MineSweeper.arquitecture.services.game.CellRevealer;
import software.ulpgc.MineSweeper.arquitecture.services.game.CellToogler;

import java.util.Arrays;
import java.util.List;

public record Board(int rows, int columns, int mineCount, Cell[][] cells, List<Observer> observers) {

    public Board(int rows, int columns, int mineCount, List<Observer> observers) {
        this(rows, columns, mineCount, BoardInitializer.initializeCells(rows, columns, mineCount), observers);
    }

    public Board(int rows, int columns, int mineCount, int avoidRow, int avoidCol, List<Observer> observers) {
        this(rows, columns, mineCount, BoardInitializer.initializeCellsAvoidingPosition(rows, columns, mineCount, avoidRow, avoidCol), observers);
    }

    public Board updateCell(int row, int col) {
        Cell[][] updatedCells = CellRevealer.revealCells(row, col, cells, observers);
        return new Board(rows, columns, mineCount, updatedCells, observers);
    }

    public Board setFlag(int row, int col) {
        Cell[][] updatedCells = CellToogler.toggleFlag(row, col, cells);
        return new Board(rows, columns, mineCount, updatedCells, observers);
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public Board revealMines() {
        Cell[][] updatedCells = Arrays.stream(cells)
                .map(row -> Arrays.stream(row)
                        .map(cell -> cell.hasMine()
                                ? new Cell(true, false, true, cell.adjacentMines())
                                : cell)
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);

        return new Board(rows, columns, mineCount, updatedCells, observers);
    }

}
