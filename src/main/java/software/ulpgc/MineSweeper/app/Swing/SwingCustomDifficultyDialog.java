package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.model.CustomDifficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.view.CustomDifficultyDialog;

import javax.swing.*;
import java.awt.*;

public class SwingCustomDifficultyDialog implements CustomDifficultyDialog {





    @Override
    public Difficulty getCustomDifficulty() {
        throw new UnsupportedOperationException("Unimplemented method 'getCustomDifficulty'");
    }


    public static CustomDifficulty setPersonalizedTable() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));

        JTextField fieldWidth = createPanelRow("Width:", panel);
        JTextField fieldHeight = createPanelRow("Height:", panel);
        JTextField fieldMines = createPanelRow("Total mines:", panel);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Configure personalized table",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int width = Integer.parseInt(fieldWidth.getText());
                int height = Integer.parseInt(fieldHeight.getText());
                int mines = Integer.parseInt(fieldMines.getText());

                if (!areValidParameters(width, height, mines)) {
                    showErrorMessage("Please enter valid values." +
                            "\nThe number of mines must be positive and less than the third of the total number of cells."
                            +
                            "\nWidth must be between 8-32." +
                            "\nHeight must be between 8-24.");
                } else {
                    return new CustomDifficulty(width, height, mines);

                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter valid values.");
            }
        }
        throw new RuntimeException("Couldn't create table");
    }

    private static boolean areValidParameters(int width, int height, int mines) {
        return (8 <= width && width <= 32) &&
                (8 <= height && height <= 24) &&
                (mines > 0 && mines <= (width * height) / 3);
    }

    private static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private static JTextField createPanelRow(String text, JPanel panel) {
        JLabel labelName = new JLabel(text);
        JTextField fieldName = new JTextField();
        panel.add(labelName);
        panel.add(fieldName);
        return fieldName;
    }

}
