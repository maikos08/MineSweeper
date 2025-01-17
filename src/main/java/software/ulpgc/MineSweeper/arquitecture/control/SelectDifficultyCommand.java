package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public class SelectDifficultyCommand implements Command<Difficulty> {
    private final BoardPresenter presenter;

    public SelectDifficultyCommand(BoardPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Difficulty difficulty) {
        Game newGame = new Game(difficulty);
        presenter.updateGame(newGame);
    }
}
