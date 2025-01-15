package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.control.Command;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
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
    private JPanel boardPanel; // El panel donde se mostrará el tablero

    public MainFrame() {
        commands = new HashMap<>();
        setResizable(false);  // Permitir que se pueda cambiar el tamaño
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
        setLocationRelativeTo(null); // Centra la ventana al inicio
        setLayout(new BorderLayout());
        addStatusBar();

        // Listener para mantener la ventana centrada cuando se cambia el tamaño
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setLocationRelativeTo(null); // Centra la ventana después de cambiar el tamaño
            }
        });
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
        resetButton.addActionListener(e -> {
            // Reinicia el juego con la nueva dificultad seleccionada
            initializeGame(difficulty);
        });

        JLabel timerLabel = new JLabel("Time: 0", SwingConstants.CENTER);
        timerLabel.setPreferredSize(new Dimension(100, 30));

        statusBar.add(toolbar(), BorderLayout.NORTH);
        statusBar.add(mineCounter, BorderLayout.WEST);
        statusBar.add(resetButton, BorderLayout.CENTER);
        statusBar.add(timerLabel, BorderLayout.EAST);

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

        // Action listener para cambiar la dificultad seleccionada desde el ComboBox
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
                        // Maneja la opción personalizada aquí si es necesario
                        break;
                }

                // Reinicia el juego con la nueva dificultad
                initializeGame(difficulty);
            }
        });

        return comboBox;
    }

    private void initializeGame(Difficulty difficulty) {
        // Elimina el tablero anterior si existe
        if (boardPanel != null) {
            remove(boardPanel); // Eliminar el tablero anterior
        }

        // Crea y agrega el nuevo tablero
        Game game = new Game(difficulty);
        SwingBoardDisplay boardDisplay = new SwingBoardDisplay(game);
        presenter = new BoardPresenter(boardDisplay, game);

        boardPanel = new JPanel(new BorderLayout());
        boardPanel.add(boardDisplay, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.CENTER);

        // Actualiza la interfaz para reflejar los cambios
        revalidate();
        repaint();
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