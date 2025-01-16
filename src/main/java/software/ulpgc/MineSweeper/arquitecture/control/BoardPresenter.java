package software.ulpgc.MineSweeper.arquitecture.control;

import java.awt.geom.Point2D;
import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

public class BoardPresenter {
    private final BoardDisplay display;
    private Game game;
    private GameTimer gameTimer;

    public BoardPresenter(BoardDisplay display, Game game, GameTimer gameTimer) {
        this.display = display;
        this.game = game;
        this.gameTimer = gameTimer;
        initializeDisplay();
    }


    private void initializeDisplay() {
        display.on("cell-click", this::handleCellClick);
        display.on("cell-right-click", this::handleCellClickRight);
        display.show(game);
    }

    private void handleCellClick(Point2D point) {
        if (game.checkStatus() != GameStatus.Current) return;

        int row = (int) point.getY();
        int col = (int) point.getX();
        int rows = game.board().rows();
        int columns = game.board().columns();

        if (row < rows && col < columns) {
            updateGameBoard(row, col);
        }

        checkGameStatus();
    }

    private void handleCellClickRight(Point2D point) {
        if (game.checkStatus() != GameStatus.Current) return;

        int row = (int) point.getY();
        int col = (int) point.getX();
        int rows = game.board().rows();
        int columns = game.board().columns();

        if (row < rows && col < columns) {
            setFlag(row, col);
        }

        checkGameStatus();
    }

    private void setFlag(int row, int col) {
        Board updatedBoard = game.board().setFlag(row, col);
        this.game = game.updateBoard(updatedBoard);
        display.show(game);
    }

    private void updateGameBoard(int row, int col) {
        Board updatedBoard = game.board().updateCell(row, col);
        this.game = game.updateBoard(updatedBoard);
        display.show(game);
    }

    private void checkGameStatus() {
        switch (game.checkStatus()) {
            case Win -> {
                display.showWin();
                gameTimer.stop();
            }
            case Lose -> {
                display.showLose();
                gameTimer.stop();
            }
            case Current -> display.show(game);
        }
    }

    public Game getGame() {
        return game;
    }

    public void updateCell(int row, int col) {
        updateGameBoard(row, col);
    }

    public void updateGame(Game newGame) {
        this.game = newGame;
        display.show(game);
    }
}
