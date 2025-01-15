package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Game;

public interface Observer {
    void updated(Game game);
}
