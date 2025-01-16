package software.ulpgc.MineSweeper.arquitecture.model;

import java.util.ArrayList;

public record Game(Board board, Difficulty difficulty, GameStatus gameStatus) {

    public Game(Difficulty difficulty) {
        this(
                new Board(
                        difficulty.getRows(),
                        difficulty.getColumns(),
                        difficulty.getMineCount(),
                        new ArrayList<>()),
                difficulty,
                GameStatus.Current);
    }


    public Game updateBoard(Board newBoard) {
        return new Game(newBoard, difficulty, gameStatus);
    }

    public GameStatus checkStatus() {
        return gameStatus;
    }

    public Game revealMines() {
        return new Game(board.revealMines(), difficulty, gameStatus);
    }


}
