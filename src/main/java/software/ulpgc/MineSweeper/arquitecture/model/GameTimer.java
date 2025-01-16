package software.ulpgc.MineSweeper.arquitecture.model;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class GameTimer {
    private final Timer timer;
    private final long startTime;

    public GameTimer(Consumer<Long> timeConsumer) {
        this.startTime = System.currentTimeMillis();

        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                timeConsumer.accept(elapsedTime);
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
