package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.control.SelectDifficultyCommand;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static int WINDOW_WIDTH = 800;
    private static int WINDOW_HEIGHT = 800;
    private BoardPresenter presenter;
    private SwingBoardDisplay boardDisplay;
    private Difficulty difficulty = Difficulty.EASY;


    public MainFrame() {
        
        setResizable(false);


        switch (this.difficulty) {
            case EASY -> {
                WINDOW_WIDTH = 400;
                WINDOW_HEIGHT = 400;
                break;
            }
            case MEDIUM -> {
                WINDOW_WIDTH = 600;
                WINDOW_HEIGHT = 600;
                break;
            }
            case HARD -> {
                WINDOW_WIDTH = 1200;
                WINDOW_HEIGHT = 800;
                break;
            }
        }


        setupMainFrame();
        initializeGame();
    }

    private void setupMainFrame() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initializeGame() {
        Game game = new Game(difficulty);
        boardDisplay = new SwingBoardDisplay(game);
        presenter = new BoardPresenter(boardDisplay, game);

        add(boardDisplay, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
    

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
