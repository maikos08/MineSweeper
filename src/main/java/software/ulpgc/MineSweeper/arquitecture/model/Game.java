package software.ulpgc.MineSweeper.arquitecture.model;

import software.ulpgc.MineSweeper.arquitecture.control.Observer;

public record Game(Board board, Difficulty difficulty, GameStatus gameStatus) {

    public Game(Difficulty difficulty) {
        this(
                new Board(
                        difficulty.getRows(),
                        difficulty.getColumns(),
                        difficulty.getMineCount()
                ),
                difficulty,
                GameStatus.Current
        );
    }

    private GameStatus updateGameStatus(Board board) {
        // Logic to determine if the game is won, lost, or ongoing
        return gameStatus; // Placeholder
    }

    public Game updateBoard(Board newBoard) {
        return new Game(newBoard, difficulty, gameStatus);
    }

}
