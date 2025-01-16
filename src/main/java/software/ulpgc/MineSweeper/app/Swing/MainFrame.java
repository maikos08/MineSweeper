package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.control.Command;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;
import software.ulpgc.MineSweeper.arquitecture.view.SelectDifficultyDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final Map<String, Command> commands;
    private static int WINDOW_WIDTH = 800;
    private static int WINDOW_HEIGHT = 800;
    private BoardPresenter presenter;
    private SelectDifficultyDialog selectDifficultyDialog;
    private Difficulty difficulty = Difficulty.EASY;
    private JPanel boardPanel;
    private SwingTimeDisplay timeDisplay;
    private GameTimer gameTimer;
    private JLabel timerLabel;

    public MainFrame() {
        commands = new HashMap<>();
        setResizable(true);
        adjustWindowSizeBasedOnDifficulty();
        setupMainFrame();
        initializeGame(difficulty);

    }

    private void adjustWindowSizeBasedOnDifficulty() {
        switch (this.difficulty) {
            case EASY -> {
                WINDOW_WIDTH = 400;
                WINDOW_HEIGHT = 500;
            }
            case MEDIUM -> {
                WINDOW_WIDTH = 750;
                WINDOW_HEIGHT = 750;
            }
            case HARD -> {
                WINDOW_WIDTH = 1150;
                WINDOW_HEIGHT = 700;
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


        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setLocationRelativeTo(null);
            }
        });
    }

    private void addStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setLayout(new BorderLayout());
        statusBar.setBackground(Color.LIGHT_GRAY);

        JLabel mineCounter = new JLabel("Mines: 10", SwingConstants.CENTER);
        mineCounter.setPreferredSize(new Dimension(100, 30));

        Map<String, ImageIcon> icons = new HashMap<>();
        try {
            icons.put("facedefault", new ImageIcon("src/images/face_image.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ImageIcon originalIcon = icons.get("facedefault");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        JButton resetButton = new JButton(finalIcon);
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(50, 50));
        resetButton.addActionListener(e -> {
            initializeGame(difficulty);
        });


        statusBar.add(toolbar(), BorderLayout.NORTH);
        statusBar.add(mineCounter, BorderLayout.WEST);
        statusBar.add(resetButton, BorderLayout.CENTER);
        statusBar.add(setupGameTimer(), BorderLayout.EAST);

        add(statusBar, BorderLayout.NORTH);
    }

    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.add(selector());
        return panel;
    }

    private Component selector() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Easy");
        comboBox.addItem("Medium");
        comboBox.addItem("Hard");
        comboBox.addItem("Personalized");

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) comboBox.getSelectedItem();
                switch (selectedDifficulty) {
                    case "Easy":
                        setDifficulty(Difficulty.EASY);
                        break;
                    case "Medium":
                        setDifficulty(Difficulty.MEDIUM);
                        break;
                    case "Hard":
                        setDifficulty(Difficulty.HARD);
                        break;
                    case "Personalized":
                        break;
                }

                initializeGame(difficulty);
            }
        });

        return comboBox;
    }

    private Component setupGameTimer() {
        timeDisplay = SwingTimeDisplay.createWithTimer();
        return timeDisplay;
    }

    private void initializeGame(Difficulty difficulty) {
        if (boardPanel != null) {
            remove(boardPanel);
        }

        if (gameTimer != null) {
            gameTimer.stop();
        }

        Game newGame = new Game(difficulty);
        gameTimer = new GameTimer(timeDisplay::updateTime);
        SwingBoardDisplay boardDisplay = new SwingBoardDisplay(newGame);
        presenter = new BoardPresenter(boardDisplay, newGame, gameTimer);
        boardPanel = new JPanel(new BorderLayout());
        boardPanel.add(boardDisplay, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.CENTER);
        gameTimer.start();
        revalidate();
        repaint();
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

    public SelectDifficultyDialog getSelectDifficultyDialog() {
        return selectDifficultyDialog;
    }
}
