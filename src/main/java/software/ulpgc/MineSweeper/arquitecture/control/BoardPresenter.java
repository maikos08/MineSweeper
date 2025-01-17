package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

import java.awt.geom.Point2D;

public class BoardPresenter {
    private final BoardDisplay display;
    private Game game;
    private boolean firstClick = true;
    private GameTimer gameTimer;

    public BoardPresenter(BoardDisplay display, Game game, GameTimer gameTimer) {
        this.display = display;
        this.game = game;
        this.gameTimer = gameTimer;
        initializeDisplay();
    }

    private void initializeDisplay() {
        display.on("cell-click", this::handleCellClick);
        display.on("cell-right-click", this::handleCellClickRigth);
        display.show(game);
    }

    private void handleCellClick(Point2D point) {
        if (gameIsOver()) return;

        ClickPosition clickPosition = getClickPosition(point);

        if (inBounds(clickPosition)) {
            updateGameBoard(clickPosition.clickedRow(), clickPosition.clickedCol(), firstClick);
        }

        startTimer();

        checkGameStatus();
    }

    private void handleCellClickRigth(Point2D point) {
        if (gameIsOver()) return;

        ClickPosition clickPosition = getClickPosition(point);

        if (inBounds(clickPosition)) {
            setFlag(clickPosition.clickedRow(), clickPosition.clickedCol());
        }

    }

    private void setFlag(int row, int col) {
        Board updatedBoard = game.board().setFlag(row, col);
        this.game = game.updateBoard(updatedBoard);

        display.show(game);
    }

    private void updateGameBoard(int row, int col, boolean firstClick) {
        if (firstClick) {
            game = game.updateBoard(new Board(game.board().rows(), game.board().columns(), game.board().mineCount(),
                    row, col, game.board().observers()));
        }

        Board updatedBoard = game.board().updateCell(row, col);
        this.game = game.updateBoard(updatedBoard);
        display.show(game);
    }

    public Game getGame() {
        return game;
    }

    public void updateGameStatusChecker(GameStatus gameStatus) {
        game = new Game(game.board(), game.difficulty(), gameStatus);
    }

    public void updateGame(Game newGame) {
        this.game = newGame;
        display.show(game);
    }

    private boolean gameIsOver() {
        if (game.checkStatus() != GameStatus.Current) {
            return true;
        }
        return false;
    }

    private void startTimer() {
        if (firstClick) {
            gameTimer.resetTimer();
            gameTimer.startTimer();
            firstClick = false;
        }
    }

    private record ClickPosition(int clickedRow, int clickedCol, int rows, int columns) { }

    private ClickPosition getClickPosition(Point2D point) {
        int row = (int) point.getY();
        int col = (int) point.getX();

        int rows = game.board().rows();
        int columns = game.board().columns();
        ClickPosition result = new ClickPosition(row, col, rows, columns);
        return result;
    }

    private boolean inBounds(ClickPosition clickPosition) {
        return clickPosition.clickedRow() < clickPosition.rows() && clickPosition.clickedCol() < clickPosition.columns();
    }

    private void checkGameStatus() {
        switch (game.checkStatus()) {
            case GameStatus.Win -> {
                display.showWin();
                gameTimer.stopTimer();
            }
            case GameStatus.Lose -> {
                display.showLose();
                gameTimer.stopTimer();
            }
            case GameStatus.Current -> display.show(game);
        }
    }
}
