package software.ulpgc.MineSweeper.arquitecture.services.observers;

import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.control.Observer;

public class GameStatusChecker implements Observer {
    private Game game;
    private final int revealedCells = 0;

    public GameStatusChecker(Game game) {
        this.game = game;

    }

    @Override
    public void notify(Cell cell) {

    }


}
