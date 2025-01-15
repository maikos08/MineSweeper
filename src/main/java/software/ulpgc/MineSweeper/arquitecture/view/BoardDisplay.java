package software.ulpgc.MineSweeper.arquitecture.view;

import software.ulpgc.MineSweeper.arquitecture.model.Board;

public interface BoardDisplay {

    void show(Board board);

    void on(Clicked clicked);

    record Cell(int x, int y, int length) {
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
