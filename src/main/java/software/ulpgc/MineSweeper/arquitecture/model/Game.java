package software.ulpgc.MineSweeper.arquitecture.model;

import software.ulpgc.MineSweeper.arquitecture.control.Observer;

public record Game(Board board, Difficulty difficulty, GameStatus gameStatus) {

    public Game(Difficulty difficulty) {
        this(
                new Board(
                        difficulty.getRows(),
                        difficulty.getColumns(),
                        difficulty.getMineCount()),
                difficulty,
                GameStatus.Current);
    }

    public GameStatus checkStatus() {
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

    public Game updateBoard(Board newBoard) {
        return new Game(newBoard, difficulty, gameStatus);
    }

}
