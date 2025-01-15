package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class SwingBoardDisplay extends JPanel implements BoardDisplay {
    private final Game game;
    private final Map<String, ImageIcon> images;
    private Position selectedPosition;

    public SwingBoardDisplay(Game game) {
        this.game = game;
        this.images = loadIcons();
        setDoubleBuffered(true);
        addMouseListener(createMouseListener());
    }

    private Map<String, ImageIcon> loadIcons() {
        Map<String, ImageIcon> icons = new HashMap<>();
        try {
            icons.put("default", new ImageIcon("src/images/default.png"));
            icons.put("flag", new ImageIcon("src/images/flag.png"));
            icons.put("mine", new ImageIcon("src/images/mine.png"));
            icons.put("revealed", new ImageIcon("src/images/Revealed.png"));
            for (int i = 1; i <= 8; i++) {
                icons.put(String.valueOf(i), new ImageIcon("src/images/" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading images: " + e.getMessage());
        }
        return icons;
    }

    @Override
    public void show(Board board) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        int cellWidth = getCellWidth();
        int cellHeight = getCellHeight();

        for (int row = 0; row < game.board().rows(); row++) {
            for (int col = 0; col < game.board().columns(); col++) {
                Cell cell = game.board().cells()[row][col];
                Square square = new Square(col * cellWidth + 5, row * cellHeight + 5, cellWidth);
                drawCell(g, cell, square);
            }
        }
    }

    private void drawCell(Graphics g, Cell cell, Square square) {
        ImageIcon icon;
        if (cell.isRevealed()) {
            if (cell.hasMine()) {
                icon = images.get("mine");
            } else {
                String adjacent = String.valueOf(cell.adjacentMines());
                icon = images.getOrDefault(adjacent, images.get("revealed"));
            }
        } else if (cell.isFlagged()) {
            icon = images.get("flag");
        } else {
            icon = images.get("default");
        }

        if (icon != null) {
            g.drawImage(icon.getImage(), square.x(), square.y(), square.length(), square.length(), this);
        } else {
            // Fallback in case of missing image
            g.setColor(Color.DARK_GRAY);
            g.fillRect(square.x(), square.y(), square.length(), square.length());
        }
    }

    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = toRow(e.getY());
                int col = toColumn(e.getX());

                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("Row: " + row + " Col: " + col);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Right click");
                }
            }
        };
    }

    private int getCellWidth() {
        return (getWidth() - 10) / game.board().columns();
    }

    private int getCellHeight() {
        return (getHeight() - 10) / game.board().rows();
    }

    private int toRow(int y) {
        return (y - 5) / getCellHeight();
    }

    private int toColumn(int x) {
        return (x - 5) / getCellWidth();
    }

    private record Position(int x, int y) {
    }

    @Override
    public void showWin() {
        JOptionPane.showMessageDialog(this, "You win!");
    }

    @Override
    public void showLose() {
        JOptionPane.showMessageDialog(this, "You lose!");
    }

    @Override
    public void on(Clicked clicked) {
        System.out.println("Clicked");
    }

    private record Square(int x, int y, int length) {
    }
}
