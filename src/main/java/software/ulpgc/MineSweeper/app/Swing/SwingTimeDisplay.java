package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.model.GameTimer;

import javax.swing.*;
import java.awt.*;

public class SwingTimeDisplay extends JPanel {
    private final JLabel timerLabel = new JLabel("Time: 0");
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
        SwingUtilities.invokeLater(() -> timerLabel.setText("Time: " + seconds));
    }

}
