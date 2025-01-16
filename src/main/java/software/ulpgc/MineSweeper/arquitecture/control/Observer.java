package software.ulpgc.MineSweeper.arquitecture.control;

import software.ulpgc.MineSweeper.arquitecture.model.Cell;

public interface Observer {
    void notify(Cell cell);
}
