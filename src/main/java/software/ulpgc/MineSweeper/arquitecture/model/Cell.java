package software.ulpgc.MineSweeper.arquitecture.model;

public record Cell(boolean hasMine, boolean isFlagged, boolean isRevealed, int adjacentMines) {
    public Cell {
        if (isRevealed && isFlagged) {
            throw new IllegalArgumentException("A cell cannot be revealed and flagged at the same time.");
        }
        if (adjacentMines < 0) {
            throw new IllegalArgumentException("Adjacent mines count cannot be negative.");
        }
    }

    public Cell reveal() {
        if (this.isRevealed()) {
            throw new IllegalStateException("Cell is already revealed");
        }
        return new Cell(this.hasMine(), this.isFlagged(), true, this.adjacentMines());
    }

    public Cell toggleFlag() {
        if (this.isRevealed) {
            throw new IllegalStateException("Cannot toggle flag on a revealed cell.");
        }
        return new Cell(this.hasMine, !this.isFlagged, false, this.adjacentMines);
    }
}

