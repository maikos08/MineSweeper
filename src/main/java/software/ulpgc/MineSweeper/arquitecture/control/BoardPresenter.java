package software.ulpgc.MineSweeper.arquitecture.control;

import java.awt.geom.Point2D;

import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

public class BoardPresenter {
    private final BoardDisplay display;
    private Game game;
    private boolean firstClick = true;

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
            updateGameBoard(row, col, firstClick);
        }

        firstClick = false;

        switch (game.checkStatus()) {
            case GameStatus.Win -> display.showWin();
            case GameStatus.Lose -> display.showLose();
            case GameStatus.Current -> display.show(game);
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

        switch (game.checkStatus()) {
            case GameStatus.Win -> display.showWin();
            case GameStatus.Lose -> display.showLose();
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

    public void updateGame(Game newGame) {
        this.game = newGame;
        display.show(game);
    }

}
