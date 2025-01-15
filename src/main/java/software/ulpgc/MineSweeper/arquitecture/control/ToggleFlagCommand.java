package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public class ToggleFlagCommand {
    private final BoardPresenter presenter;

    public ToggleFlagCommand(BoardPresenter presenter) {
        this.presenter = presenter;
    }

    public void execute(int row, int col) {
        presenter.updateCell(row, col);
    }
}
