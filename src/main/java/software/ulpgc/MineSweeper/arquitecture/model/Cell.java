package software.ulpgc.MineSweeper.arquitecture.model;

public record Cell(boolean hasMine, boolean isFlagged, boolean isRevealed, int adjacentMines) {
    public Cell {
        validateState(isRevealed, isFlagged, adjacentMines);
    }

    private static void validateState(boolean isRevealed, boolean isFlagged, int adjacentMines) {
        if (isRevealed && isFlagged) {
            throw new IllegalArgumentException("A cell cannot be revealed and flagged at the same time.");
        }
        if (adjacentMines < 0) {
            throw new IllegalArgumentException("Adjacent mines count cannot be negative.");
        }
    }

    public Cell revealCell() {
        if (this.isRevealed()) {
            throw new IllegalStateException("Cell is already revealed");
        }
        return new Cell(this.hasMine(), this.isFlagged(), true, this.adjacentMines());
    }

    public Cell toggleCellFlag() {
        if (this.isRevealed) {
            throw new IllegalStateException("Cannot toggle flag on a revealed cell.");
        }
        return new Cell(this.hasMine, !this.isFlagged, false, this.adjacentMines);
    }

    public Cell updateAdjacentMines(int newAdjacentMines) {
        return new Cell(this.hasMine, this.isFlagged, this.isRevealed, newAdjacentMines);
    }
}
