package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public interface Observer {
    void notify(Cell cell);
}
