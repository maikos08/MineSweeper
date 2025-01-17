package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.io.FileImageLoader;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;
import software.ulpgc.MineSweeper.arquitecture.services.FlagCounter;
import software.ulpgc.MineSweeper.arquitecture.services.observers.GameStatusObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class MainFrame extends JFrame {
    private static int WINDOW_WIDTH = 800;
    private static int WINDOW_HEIGHT = 800;
    private BoardPresenter presenter;
    private Difficulty difficulty = Difficulty.EASY;
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
        switch (this.difficulty) {
            case EASY -> {
                WINDOW_WIDTH = 288;
                WINDOW_HEIGHT = 375;
            }
            case MEDIUM -> {
                WINDOW_WIDTH = 576;
                WINDOW_HEIGHT = 690;
            }
            case HARD -> {
                WINDOW_WIDTH = 1150;
                WINDOW_HEIGHT = 1000;
            }
            case PERSONALIZED -> {
                WINDOW_WIDTH = difficulty.getColumns()*36;
                WINDOW_HEIGHT = difficulty.getRows()*40+50;
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
        comboBox.addItem("Personalized");

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) comboBox.getSelectedItem();
                switch (requireNonNull(selectedDifficulty)) {
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
                        setPersonalizedDifficulty();
                        break;
                }
                initializeGame(difficulty);
            }
        });

        return comboBox;
    }

    private void setPersonalizedDifficulty() {

        JPanel setPersonalizedPanel1 = new JPanel(new GridLayout(3,2,5,5));

        JLabel labelRows = new JLabel("Filas:");
        JTextField fieldRows = new JTextField();
        JLabel labelColumns = new JLabel("Columnas:");
        JTextField fieldColumns = new JTextField();
        JLabel labelMines = new JLabel("Minas:");
        JTextField fieldMines = new JTextField();

        // Añadir componentes al panel
        setPersonalizedPanel1.add(labelRows);
        setPersonalizedPanel1.add(fieldRows);
        setPersonalizedPanel1.add(labelColumns);
        setPersonalizedPanel1.add(fieldColumns);
        setPersonalizedPanel1.add(labelMines);
        setPersonalizedPanel1.add(fieldMines);
        JPanel setPersonalizedPanel = setPersonalizedPanel1;

        int result = JOptionPane.showConfirmDialog(null, setPersonalizedPanel, "Configurar Dificultad Personalizada",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Recoger valores ingresados
                    int rows = Integer.parseInt(fieldRows.getText());
                    int columns = Integer.parseInt(fieldColumns.getText());
                    int mines = Integer.parseInt(fieldMines.getText());

                    // Validar los valores (ejemplo)
                    if (rows < 8 || rows > 24 || columns < 8 || columns > 32) {
                        throw new IllegalArgumentException("Filas y columnas deben estar entre 1 y 50.");
                    }
                    if (mines < 1 || mines >= (rows * columns)*0.3) {
                        throw new IllegalArgumentException("El número de minas debe ser mayor que 0 y menor que el total de celdas.");
                    }

                    Difficulty.PERSONALIZED.setRows(rows);
                    Difficulty.PERSONALIZED.setColumns(columns);
                    Difficulty.PERSONALIZED.setMineCount(mines);

                    setDifficulty(Difficulty.PERSONALIZED);

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }






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
}
