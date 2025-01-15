package software.ulpgc.MineSweeper.arquitecture.services.observers;

import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.control.Observer;

public class GameStatusChecker implements Observer {

    @Override
    public void updated(Game game) {
        Board board = game.board();
        GameStatus currentStatus = checkStatus(board);

        if (currentStatus != game.gameStatus()) {
            game = new Game(board, game.difficulty(), currentStatus);
        }
    }

    private GameStatus checkStatus(Board board) {
        boolean allCellsRevealed = true;
        boolean mineRevealed = false;

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.columns(); j++) {
                var cell = board.cells()[i][j];

                if (cell.hasMine() && cell.isRevealed()) {
                    mineRevealed = true;
                }

                if (!cell.hasMine() && !cell.isRevealed()) {
                    allCellsRevealed = false;
                }
            }
        }

        if (mineRevealed) {
            return GameStatus.Lose;
        } else if (allCellsRevealed) {
            return GameStatus.Win;
        } else {
            return GameStatus.Current;
        }
    }
}
