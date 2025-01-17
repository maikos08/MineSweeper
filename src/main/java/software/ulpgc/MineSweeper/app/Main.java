package software.ulpgc.MineSweeper.app;

import software.ulpgc.MineSweeper.app.Swing.MainFrame;
import software.ulpgc.MineSweeper.arquitecture.control.SelectDifficultyCommand;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            mainFrame.put("select difficulty", new SelectDifficultyCommand(mainFrame.getPresenter()));
        });
    }
}
