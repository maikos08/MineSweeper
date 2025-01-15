package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public class SelectDifficultyCommand {
    private final BoardPresenter presenter;

    public SelectDifficultyCommand(BoardPresenter presenter) {
        this.presenter = presenter;
    }

    public void execute(Difficulty difficulty) {
        Game newGame = new Game(difficulty);

        presenter.updateGame(newGame);
    }
}
