package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

public class BoardPresenter {
    private final BoardDisplay display;
    private Game game;

    public BoardPresenter(BoardDisplay display, Game game) {
        this.display = display;
        this.game = game;
        initializeDisplay();
    }

    private void initializeDisplay() {
        display.on(this::handleCellClick);
        display.show(game.board());
    }

    private void handleCellClick(int offset) {
        int rows = game.board().rows();
        int columns = game.board().columns();

        int row = offset / columns;
        int col = offset % columns;

        if (row < rows && col < columns) {
            updateGameBoard(row, col);
        }

        switch (game.checkStatus()) {
            case GameStatus.Win -> display.showWin();
            case GameStatus.Lose -> display.showLose();
            case GameStatus.Current -> display.show(game.board());
        }

    }

    private void updateGameBoard(int row, int col) {
        Board updatedBoard = game.board().updateCell(row, col);
        game = game.updateBoard(updatedBoard);

        

        display.show(updatedBoard);
    }

    public Game getGame() {
        return game;
    }

    public void updateCell(int row, int col) {
        Board updatedBoard = game.board().updateCell(row, col);

        game = game.updateBoard(updatedBoard);

        display.show(updatedBoard);
    }

    public void updateGame(Game newGame) {
        this.game = newGame;

        display.show(newGame.board());
    }

}
