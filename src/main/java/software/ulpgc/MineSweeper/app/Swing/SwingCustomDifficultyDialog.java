package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.model.BaseDifficulty;
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


    public static Difficulty setPersonalizedTable() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));

        JTextField Rowsfield = createPanelRow("Rows:", panel);
        JTextField Columnsfield = createPanelRow("Columns:", panel);
        JTextField fieldMines = createPanelRow("Total mines:", panel);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Configure personalized table",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int rows = Integer.parseInt(Rowsfield.getText());
                int columns = Integer.parseInt(Columnsfield.getText());
                int mines = Integer.parseInt(fieldMines.getText());

                if (!areValidParameters(rows, columns, mines)) {
                    showErrorMessage("Please enter valid values." +
                            "\nThe number of mines must be positive and less than the third of the total number of cells."
                            +
                            "\nColumns must be between 8-32." +
                            "\nRows must be between 8-24.");
                } else {
                    return new CustomDifficulty(rows, columns, mines);

                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter valid values.");
            }
        } else if (result==JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            return BaseDifficulty.EASY;
        }
        throw new RuntimeException("Couldnt create table");
    }

    private static boolean areValidParameters(int rows, int columns, int mines) {
        return (8 <= columns && columns <= 32) &&
                (8 <= rows && rows <= 24) &&
                (mines > 0 && mines <= (rows * columns) / 3);
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
