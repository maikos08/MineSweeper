package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static int WINDOW_WIDTH = 800;
    private static int WINDOW_HEIGHT = 800;
    private BoardPresenter presenter;
    private SwingBoardDisplay boardDisplay;
    private Difficulty difficulty = Difficulty.MEDIUM;

    public MainFrame() {
        setResizable(false);
        adjustWindowSizeBasedOnDifficulty();
        setupMainFrame();
        initializeGame();
    }

    private void adjustWindowSizeBasedOnDifficulty() {
        switch (this.difficulty) {
            case EASY -> {
                WINDOW_WIDTH = 400;
                WINDOW_HEIGHT = 400;
            }
            case MEDIUM -> {
                WINDOW_WIDTH = 600;
                WINDOW_HEIGHT = 600;
            }
            case HARD -> {
                WINDOW_WIDTH = 1200;
                WINDOW_HEIGHT = 800;
            }
        }
    }

    private void setupMainFrame() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        addStatusBar();
    }

    private void addStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setLayout(new BorderLayout());
        statusBar.setBackground(Color.LIGHT_GRAY);

        JLabel mineCounter = new JLabel("Mines: 10", SwingConstants.CENTER);
        mineCounter.setPreferredSize(new Dimension(100, 30));

        JButton resetButton = new JButton(":)");
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(50, 30));
        //add reset method
        resetButton.addActionListener(e ->{
            presenter.initializeNewGame();
        });

        JLabel timerLabel = new JLabel("Time: 0", SwingConstants.CENTER);
        timerLabel.setPreferredSize(new Dimension(100, 30));

        statusBar.add(mineCounter, BorderLayout.WEST);
        statusBar.add(resetButton, BorderLayout.CENTER);
        statusBar.add(timerLabel, BorderLayout.EAST);

        add(statusBar, BorderLayout.NORTH);
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
        adjustWindowSizeBasedOnDifficulty();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
