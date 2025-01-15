package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public class ToggleFlagCommand {
    private final BoardPresenter presenter;


    public ToggleFlagCommand(BoardPresenter presenter) {
        this.presenter = presenter;
    }

    public void execute(int row, int col) {
        Cell cell = presenter.getGame().board().cells()[row][col];

        Cell updatedCell = cell.toggleFlag();
        presenter.updateCell(row, col, updatedCell);
    }
}
