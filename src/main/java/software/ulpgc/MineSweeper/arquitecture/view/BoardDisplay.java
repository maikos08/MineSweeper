package software.ulpgc.MineSweeper.arquitecture.view;

import software.ulpgc.MineSweeper.app.Swing.SwingBoardDisplay;
import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Game;

public interface BoardDisplay {

    void show(Board board);

    void on(Clicked clicked);

    record Square(int x, int y, int length) {
        public boolean isAt(int x, int y) {
            return Math.abs(x - this.x) < length
                    && Math.abs(y - this.y) < length;
        }
    }

    interface Clicked {
        Clicked Null = offset -> {
        };

        void offset(int offset);
    }

    public void showWin();

    public void showLose();

}
