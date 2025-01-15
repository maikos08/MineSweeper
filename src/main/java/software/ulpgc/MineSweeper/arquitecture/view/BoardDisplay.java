package software.ulpgc.MineSweeper.arquitecture.view;

import software.ulpgc.MineSweeper.app.Swing.SwingBoardDisplay;
import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public interface BoardDisplay {

    void show(Game game);

    void on(String event, Clicked clicked);

    record Square(int x, int y, int length) {
        public boolean isAt(int x, int y) {
            return Math.abs(x - this.x) < length && Math.abs(y - this.y) < length;
        }
    }

    interface Clicked {
        Clicked Null = point -> {
        };

        void on(java.awt.Point point);
    }

    void showWin();

    void showLose();
}
