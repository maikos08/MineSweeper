package software.ulpgc.MineSweeper.arquitecture.control;

public interface Command<T> {
    void execute(T t);
}