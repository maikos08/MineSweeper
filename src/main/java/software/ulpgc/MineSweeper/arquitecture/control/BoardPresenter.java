package software.ulpgc.MineSweeper.arquitecture.control;

import java.awt.geom.Point2D;

import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.services.observers.GameStatusChecker;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

public class BoardPresenter {
    private final BoardDisplay display;
    private Game game;

    public BoardPresenter(BoardDisplay display, Game game) {
        this.display = display;
        this.game = game;
        initializeDisplay();
    }

    public void initializeNewGame() {
        game = new Game(game.difficulty());
        display.show(game);
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
            updateGameBoard(row, col);
        }
    }

    private void handleCellClickRigth(Point2D point) {
        if (game.checkStatus() != GameStatus.Current) {
            return;
        }
        
        System.out.println("Clicked on " + point);

        int row = (int) point.getY();
        int col = (int) point.getX();

        int rows = game.board().rows();
        int columns = game.board().columns();

        if (row < rows && col < columns) {
            setFlag(row, col);
        }
    }

    private void setFlag(int row, int col) {
        Board updatedBoard = game.board().setFlag(row, col);

        System.out.println("Updated board: ");
        System.out.println(updatedBoard);

        this.game = game.updateBoard(updatedBoard);

        display.show(game);
    }

    private void updateGameBoard(int row, int col) {
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

    public void updateGameStatusChecker(GameStatus gameStatus){
        game = new Game(game.board(), game.difficulty(), gameStatus);

        switch (gameStatus) {
            case GameStatus.Win -> display.showWin();
            case GameStatus.Lose -> display.showLose();
            case GameStatus.Current -> display.show(game);
        }
    }
}
