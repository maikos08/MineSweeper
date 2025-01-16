package software.ulpgc.MineSweeper.arquitecture.control;

import java.awt.geom.Point2D;
import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;
import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;

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
        if (game.checkStatus() != GameStatus.Current) {
            return;
        }

        System.out.println("Clicked on " + point);

        int row = (int) point.getY();
        int col = (int) point.getX();

        int rows = game.board().rows();
        int columns = game.board().columns();

        if (row < rows && col < columns) {
            updateGameBoard(row, col, firstClick);
        }


        if (firstClick) {
            gameTimer.reset();
            gameTimer.start();
            firstClick = false;
        }

        switch (game.checkStatus()) {
            case GameStatus.Win -> {
                display.showWin();
                gameTimer.stop();
            }
            case GameStatus.Lose -> {
                display.showLose();
                gameTimer.stop();
            }
            case GameStatus.Current -> display.show(game);
        }
    }


    private void handleCellClickRigth(Point2D point) {
        if (game.checkStatus() != GameStatus.Current) {
            return;
        }

        System.out.println("Right-clicked on " + point);

        int row = (int) point.getY();
        int col = (int) point.getX();

        int rows = game.board().rows();
        int columns = game.board().columns();

        if (row < rows && col < columns) {
            setFlag(row, col);
        }

        switch (game.checkStatus()) {
            case GameStatus.Win -> display.showWin();
            case GameStatus.Lose -> {
                display.showLose();
                gameTimer.stop(); // Stop the timer when the game is lost
            }
            case GameStatus.Current -> display.show(game);
        }
    }

    private void setFlag(int row, int col) {
        Board updatedBoard = game.board().setFlag(row, col);

        System.out.println("Updated board: ");
        System.out.println(updatedBoard);

        this.game = game.updateBoard(updatedBoard);

        display.show(game);
    }

    private void updateGameBoard(int row, int col, boolean firstClick) {
        if (firstClick && game.board().cells()[row][col].hasMine()) {
            System.out.println("First click on mine, moving mine...");
            game = game.updateBoard(new Board(game.board().rows(), game.board().columns(), game.board().mineCount(), row, col));
        }

        Board updatedBoard = game.board().updateCell(row, col);

        System.out.println("Updated board: ");
        System.out.println(updatedBoard);

        this.game = game.updateBoard(updatedBoard);

        display.show(game);
    }

    public Game getGame() {
        return game;
    }

    public void updateCell(int row, int col) {
        Board updatedBoard = game.board().updateCell(row, col);

        game = game.updateBoard(updatedBoard);

        display.show(game);
    }

    public void updateGame(Game newGame) {
        this.game = newGame;

        display.show(game);
    }
}