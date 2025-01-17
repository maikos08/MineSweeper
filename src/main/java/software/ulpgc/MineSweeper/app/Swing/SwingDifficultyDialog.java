package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.model.BaseDifficulty;
import software.ulpgc.MineSweeper.arquitecture.model.CustomDifficulty;
import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.view.SelectDifficultyDialog;

import javax.swing.*;
import java.util.List;

public class SwingDifficultyDialog extends JPanel implements SelectDifficultyDialog {
    private final List<String> difficulties;
    private final JComboBox<String> selector;


    public SwingDifficultyDialog(List<String> difficulties) {
        this.difficulties = difficulties;
        this.add(selector = selector());
    }

    private JComboBox<String> selector() {
        JComboBox<String> comboBox = new JComboBox<>();
        for (String difficulty : difficulties){
            comboBox.addItem(difficulty);
        }

        return comboBox;
    }

    @Override
    public Difficulty getDifficulty() {

        switch (difficulties.get(selector.getSelectedIndex())){
            case "Easy" -> {
                return BaseDifficulty.EASY;
            }
            case "Medium" -> {
                return BaseDifficulty.MEDIUM;
            }
            case "Hard" -> {
                return BaseDifficulty.HARD;
            }
            case "Personalized" -> {
                Difficulty customDifficulty = SwingCustomDifficultyDialog.setPersonalizedTable();
                return customDifficulty;
            }
            default -> {
                return null;
            }
        }
    }
    public JComboBox<String> getSelector() {
        return selector;
    }
}
