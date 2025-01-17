package software.ulpgc.MineSweeper.arquitecture.services.observers;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.control.Observer;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.GameStatus;

public class GameStatusObserver implements Observer {
    private final BoardPresenter boardPresenter;
    private int revealedCells = 0;

    public GameStatusObserver(BoardPresenter boardPresenter) {
        this.boardPresenter = boardPresenter;
    }

    @Override
    public void notify(Cell cell) {
        checkStatus(cell);
    }

    private void checkStatus(Cell cell) {
        if (isLoseCondition(cell)){
            boardPresenter.updateGameStatusChecker(GameStatus.Lose);
        } else if (isWinCondition()){
            boardPresenter.updateGameStatusChecker(GameStatus.Win);
        }
    }

    public boolean isLoseCondition(Cell cell){
        return cell.hasMine();
    }

    public boolean isWinCondition(){
        ++revealedCells;
        int totalCells = boardPresenter.getGame().board().columns() * boardPresenter.getGame().board().rows();
        int minesCount = boardPresenter.getGame().board().mineCount();
        return totalCells == revealedCells + minesCount;
    }
}