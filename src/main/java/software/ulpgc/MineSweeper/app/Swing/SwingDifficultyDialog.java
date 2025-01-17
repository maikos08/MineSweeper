package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.model.Difficulty;
import software.ulpgc.MineSweeper.arquitecture.view.SelectDifficultyDialog;

import javax.swing.*;
import java.util.List;

public class SwingDifficultyDialog extends JPanel implements SelectDifficultyDialog {
    private final List<Difficulty> difficulties;
    private final JComboBox<Difficulty> selector;

    public SwingDifficultyDialog(List<Difficulty> difficulties) {
        this.difficulties = difficulties;
        this.add(selector = selector());
    }

    private JComboBox<Difficulty> selector() {
        JComboBox<Difficulty> comboBox = new JComboBox<>();

        for (Difficulty difficulty : difficulties){
            comboBox.addItem(difficulty);
        }

        return comboBox;
    }

    @Override
    public Difficulty getDifficulty() {
        return difficulties.get(selector.getSelectedIndex());
    }

    public JComboBox<Difficulty> getSelector() {
        return selector;
    }
}
