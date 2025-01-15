package software.ulpgc.MineSweeper.app;

import software.ulpgc.MineSweeper.arquitecture.model.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Define difficulty (rows, columns, and mine count)
        System.out.println("Select Difficulty: 1 - Easy, 2 - Medium, 3 - Hard");
        int choice = scanner.nextInt();
        Difficulty difficulty;
        switch (choice) {
            case 2 -> difficulty = Difficulty.MEDIUM;
            case 3 -> difficulty = Difficulty.HARD;
            default -> difficulty = Difficulty.EASY;
        }

        // Initialize the game
        Game game = new Game(difficulty);
        System.out.println("Initial Board:");
        Board board = game.board();
        printBoard(board, false);

        while (game.checkStatus() == GameStatus.Current) {
            System.out.println("\nEnter row and column to reveal (e.g., 2 3):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            board = board.updateCell(row, col);
            game = game.updateBoard(board);

            printBoard(board, true);
            System.out.println("\nGame Status: " + game.checkStatus());
        }

        if (game.checkStatus() == GameStatus.Win) {
            printBoard(board, true);
            System.out.println("Congratulations! You win!");
        } else {
            printBoard(board, true);
            System.out.println("Game Over! You hit a mine.");
        }

        scanner.close();
    }

    private static void printBoard(Board board, boolean reveal) {
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.columns(); j++) {
                Cell cell = board.cells()[i][j];
                if (reveal && cell.hasMine() && cell.isRevealed()) {
                    System.out.print("M ");
                } else if (reveal && cell.isRevealed()) {
                    System.out.print(cell.adjacentMines() + " ");
                } else {
                    System.out.print(reveal ? ". " : "* ");
                }
            }
            System.out.println();
        }
    }
}
