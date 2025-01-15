package software.ulpgc.MineSweeper.app;

import software.ulpgc.MineSweeper.app.Swing.MainFrame;
import software.ulpgc.MineSweeper.arquitecture.model.*;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
