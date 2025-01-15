package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public class RevealCellCommand {
    private final BoardPresenter presenter;

    public RevealCellCommand(BoardPresenter presenter) {
        this.presenter = presenter;
    }

    public void execute(int row, int col) {
        Cell cell = presenter.getGame().board().cells()[row][col];

        if (cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        Cell updatedCell = cell.reveal();
        presenter.updateCell(row, col);
    }
}
