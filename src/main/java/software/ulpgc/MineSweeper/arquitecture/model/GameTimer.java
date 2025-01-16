package software.ulpgc.MineSweeper.arquitecture.model;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;


public class GameTimer {
    private final Timer timer;
    private long startTime;
    private final Consumer<Long> timeConsumer;

    public GameTimer(Consumer<Long> timeConsumer) {
        this.timeConsumer = timeConsumer;
        this.startTime = System.currentTimeMillis();
        this.timer = new Timer(1000, this::updateElapsedTime);
    }

    private void updateElapsedTime(ActionEvent e) {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        timeConsumer.accept(elapsedTime);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        stop();
        startTime = System.currentTimeMillis();
        start();
    }
}
