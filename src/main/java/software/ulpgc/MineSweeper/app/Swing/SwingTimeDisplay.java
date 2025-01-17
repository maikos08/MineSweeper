package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;

import javax.swing.*;
import java.awt.*;

public class SwingTimeDisplay extends JPanel {
    private final JLabel timerLabel = new JLabel("Time: 00:00");
    private GameTimer gameTimer;

    private SwingTimeDisplay() {
        setPreferredSize(new Dimension(100, 30));
        setLayout(new BorderLayout());
        add(timerLabel, BorderLayout.CENTER);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static SwingTimeDisplay createWithTimer() {
        SwingTimeDisplay display = new SwingTimeDisplay();
        display.gameTimer = new GameTimer(display::updateTime);
        return display;
    }

    public void updateTime(long seconds) {
        SwingUtilities.invokeLater(() -> timerLabel.setText(formatTime(seconds)));
    }

    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("Time: %02d:%02d", minutes, remainingSeconds);
    }

}
