package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.control.Command;
import software.ulpgc.MineSweeper.arquitecture.io.FileImageLoader;
import software.ulpgc.MineSweeper.arquitecture.model.BaseDifficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;
import software.ulpgc.MineSweeper.arquitecture.services.FlagCounter;
import software.ulpgc.MineSweeper.arquitecture.services.observers.GameStatusObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainFrame extends JFrame {
    private static int WINDOW_WIDTH = 800;
    private static int WINDOW_HEIGHT = 800;
    private BoardPresenter presenter;
    private Difficulty difficulty = BaseDifficulty.EASY;
    private final Map<String, Command<Difficulty>> commands;


    private JPanel boardPanel;
    private SwingTimeDisplay timeDisplay;
    private GameTimer gameTimer;
    private JLabel mineAndFlagCounter;

    public MainFrame() {
        this.commands = new HashMap<>();
        setResizable(false);
        adjustWindowSizeBasedOnDifficulty();
        setupMainFrame();
        initializeGame(difficulty);
    }

    private void adjustWindowSizeBasedOnDifficulty() {
        switch (difficulty) {
            case BaseDifficulty.EASY -> {
                WINDOW_WIDTH = 288;
                WINDOW_HEIGHT = 380;
            }
            case BaseDifficulty.MEDIUM -> {
                WINDOW_WIDTH = 576;
                WINDOW_HEIGHT = 690;
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
        mineAndFlagCounter.setOpaque(true);

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
        List<String> difficulties = new ArrayList<>();
        difficulties.add("Easy");
        difficulties.add("Medium");
        difficulties.add("Hard");
        difficulties.add("Personalized");
        SwingDifficultyDialog swingDifficultyDialog = new SwingDifficultyDialog(difficulties);

        JComboBox<String> selector = swingDifficultyDialog.getSelector();
        selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Difficulty selectedDifficulty = swingDifficultyDialog.getDifficulty();
                commands.get("select difficulty").execute(selectedDifficulty);
                difficulty = selectedDifficulty;
                initializeGame(difficulty);
                adjustWindowSizeBasedOnDifficulty();
                setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            }
        });
        return swingDifficultyDialog;
    }

    private Component setupGameTimer() {
        timeDisplay = SwingTimeDisplay.createWithTimer();
        return timeDisplay;
    }

    private void initializeGame(Difficulty difficulty) {
        if (gameTimer != null) {
            gameTimer.resetTimer();
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
    public void put(String name, Command<Difficulty> command){
        commands.put(name, command);
    }
    public BoardPresenter getPresenter() {
        return presenter;
    }

}
