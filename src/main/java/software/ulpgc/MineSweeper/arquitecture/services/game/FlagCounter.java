package software.ulpgc.MineSweeper.arquitecture.services.game;

import java.util.ArrayList;
import java.util.List;

public class FlagCounter {
    private static FlagCounter instance;
    private int flags;
    private int mines;
    private final List<Runnable> listeners = new ArrayList<>();

    private FlagCounter() {
        this.flags = 0;
        this.mines = 0;
    }

    public static FlagCounter getInstance() {
        if (instance == null) {
            instance = new FlagCounter();
        }
        return instance;
    }

    public int getRemainingFlags() {
        return mines - flags;
    }

    public void removeFlag() {
        flags--;
        notifyListeners();
    }

    public void addFlag() {
        flags++;
        notifyListeners();
    }

    public void setMines(int mines) {
        this.mines = mines;
        flags = 0;
        notifyListeners();
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
