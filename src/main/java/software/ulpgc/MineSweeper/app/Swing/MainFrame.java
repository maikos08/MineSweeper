package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.control.Command;
import software.ulpgc.MineSweeper.arquitecture.io.FileImageLoader;
import software.ulpgc.MineSweeper.arquitecture.model.BaseDifficulty;
import software.ulpgc.MineSweeper.arquitecture.model.CustomDifficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;
import software.ulpgc.MineSweeper.arquitecture.services.FlagCounter;
import software.ulpgc.MineSweeper.arquitecture.services.observers.GameStatusObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class MainFrame extends JFrame {
    private static int WINDOW_WIDTH = 800;
    private static int WINDOW_HEIGHT = 800;
    private BoardPresenter presenter;
    private Difficulty difficulty = BaseDifficulty.EASY;

    private JPanel boardPanel;
    private SwingTimeDisplay timeDisplay;
    private GameTimer gameTimer;
    private JLabel mineAndFlagCounter;

    public MainFrame() {
        setResizable(false);
        adjustWindowSizeBasedOnDifficulty();
        setupMainFrame();
        initializeGame(difficulty);
    }

    private void adjustWindowSizeBasedOnDifficulty() {
        switch (difficulty) {
            case BaseDifficulty.EASY -> {
                WINDOW_WIDTH = 400;
                WINDOW_HEIGHT = 510;
            }
            case BaseDifficulty.MEDIUM -> {
                WINDOW_WIDTH = 750;
                WINDOW_HEIGHT = 830;
            }
            case BaseDifficulty.HARD -> {
                WINDOW_WIDTH = 1150;
                WINDOW_HEIGHT = 700;
            }

            default -> createCustomWindow(difficulty);
        }
    }

    private void createCustomWindow(Difficulty difficulty) {
        WINDOW_WIDTH = difficulty.getColumns() * 36;
        WINDOW_HEIGHT = difficulty.getRows() * 40 + 50;
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

        mineAndFlagCounter = new JLabel("Mines: 10 | Flags: 0", SwingConstants.CENTER);
        mineAndFlagCounter.setPreferredSize(new Dimension(100, 30));

        FlagCounter.getInstance().addListener(this::updateMineAndFlagCounter);

        Map<String, ImageIcon> images = new FileImageLoader("src/images").load();
        ImageIcon originalIcon = images.get("face_image.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        JButton resetButton = new JButton(finalIcon);
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(50, 50));
        resetButton.addActionListener(e -> initializeGame(difficulty));

        statusBar.add(toolbar(), BorderLayout.NORTH);
        statusBar.add(mineAndFlagCounter, BorderLayout.WEST);
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
        comboBox.addItem("Custom");

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) comboBox.getSelectedItem();
                switch (requireNonNull(selectedDifficulty)) {
                    case "Easy":
                        setDifficulty(BaseDifficulty.EASY);
                        break;
                    case "Medium":
                        setDifficulty(BaseDifficulty.MEDIUM);
                        break;
                    case "Hard":
                        setDifficulty(BaseDifficulty.HARD);
                        break;
                    case "Custom":
                        setPersonalizedTable();
                        break;
                }
                initializeGame(difficulty);
            }
        });

        return comboBox;
    }

    private void setPersonalizedTable() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));

        JTextField fieldWidth = createPanelRow("Width:", panel);
        JTextField fieldHeight = createPanelRow("Height:", panel);
        JTextField fieldMines = createPanelRow("Total mines:", panel);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Configure personalized table",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int width = Integer.parseInt(fieldWidth.getText());
                int height = Integer.parseInt(fieldHeight.getText());
                int mines = Integer.parseInt(fieldMines.getText());

                if (!areValidParameters(width, height, mines)) {
                    showErrorMessage("Please enter valid values." +
                            "\nThe number of mines must be positive and less than the third of the total number of cells."
                            +
                            "\nWidth must be between 8-32." +
                            "\nHeight must be between 8-24.");
                } else {
                    CustomDifficulty personalizedDifficulty = new CustomDifficulty(width, height, mines);
                    setDifficulty(personalizedDifficulty);
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter valid values.");
            }
        }
    }

    private static boolean areValidParameters(int width, int height, int mines) {
        return (8 <= width && width <= 32) &&
                (8 <= height && height <= 24) &&
                (mines > 0 && mines <= (width * height) / 3);
    }

    private static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private static JTextField createPanelRow(String text, JPanel panel) {
        JLabel labelName = new JLabel(text);
        JTextField fieldName = new JTextField();
        panel.add(labelName);
        panel.add(fieldName);
        return fieldName;
    }

    private Component setupGameTimer() {
        timeDisplay = SwingTimeDisplay.createWithTimer();
        return timeDisplay;
    }

    private void initializeGame(Difficulty difficulty) {
        if (gameTimer != null) {
            gameTimer.reset();
        }

        if (boardPanel != null) {
            remove(boardPanel);
        }

        Game newGame = new Game(difficulty);

        gameTimer = new GameTimer(seconds -> timeDisplay.updateTime(seconds));
        SwingBoardDisplay boardDisplay = new SwingBoardDisplay(newGame);
        presenter = new BoardPresenter(boardDisplay, newGame, gameTimer);
        boardPanel = new JPanel(new BorderLayout());
        boardPanel.add(boardDisplay, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.CENTER);

        FlagCounter.getInstance().setMines(difficulty.getMineCount());

        updateMineAndFlagCounter();

        GameStatusObserver gameStatusObserver = new GameStatusObserver(presenter);
        newGame.board().addObserver(gameStatusObserver);

        revalidate();
        repaint();
    }

    private void updateMineAndFlagCounter() {
        SwingUtilities.invokeLater(() -> {
            int totalMines = FlagCounter.getInstance().getRemainingFlags();
            mineAndFlagCounter.setText("Mines: " + totalMines);
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

    public BoardPresenter getPresenter() {
        return presenter;
    }
}
