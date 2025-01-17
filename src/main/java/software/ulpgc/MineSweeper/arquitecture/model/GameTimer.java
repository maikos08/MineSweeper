package software.ulpgc.MineSweeper.arquitecture.model;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class GameTimer {
    private final Timer timer;
    private long startTime;
    private final Consumer<Long> timeConsumer;
    private boolean running;

    public GameTimer(Consumer<Long> timeConsumer) {
        this.timeConsumer = timeConsumer;
        this.startTime = System.currentTimeMillis();
        this.timer = new Timer(1000, this::updateElapsedSeconds);
        this.running = false;
    }

    private void updateElapsedSeconds(ActionEvent event) {
        long elapsedSeconds = calculateElapsedSeconds();
        timeConsumer.accept(elapsedSeconds);
    }

    private long calculateElapsedSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public void startTimer() {
        if (!running) {
            startTime = System.currentTimeMillis();
            timer.start();
            running = true;
        }
    }

    public void stopTimer() {
        if (running) {
            timer.stop();
            running = false;
        }
    }

    public void resetTimer() {
        stopTimer();
        timeConsumer.accept(0L);
        startTime = System.currentTimeMillis();
        if (running) {
            timer.restart();
        }
    }

    @Override
    public String toString() {
        return "GameTimer{" +
                "startTime=" + startTime +
                ", running=" + running +
                '}';
    }
}
