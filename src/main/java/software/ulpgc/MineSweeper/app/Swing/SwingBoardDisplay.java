package software.ulpgc.MineSweeper.app.Swing;

import software.ulpgc.MineSweeper.arquitecture.control.BoardPresenter;
import software.ulpgc.MineSweeper.arquitecture.control.Command;
import software.ulpgc.MineSweeper.arquitecture.control.ToggleFlagCommand;
import software.ulpgc.MineSweeper.arquitecture.io.FileImageLoader;
import software.ulpgc.MineSweeper.arquitecture.model.Board;
import software.ulpgc.MineSweeper.arquitecture.model.Cell;
import software.ulpgc.MineSweeper.arquitecture.model.Game;
import software.ulpgc.MineSweeper.arquitecture.model.Image;
import software.ulpgc.MineSweeper.arquitecture.view.BoardDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Map;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class SwingBoardDisplay extends JPanel implements BoardDisplay {
    private final BoardPresenter presenter;
    private final Game game;
    private software.ulpgc.MineSweeper.arquitecture.model.Cell[][] cells;
    private Map<String, Image> images;
    private Position selectedPosition;

    public SwingBoardDisplay(BoardPresenter presenter, Game game) {
        this.presenter = presenter;
        this.game = game;
        this.cells = game.board().cells();
        loadIcons();
        addMouseListener(createMouseListener());
        setDoubleBuffered(true);
    }

    private void loadIcons() {
        images = new FileImageLoader().load();
    }

    @Override
    public void show(Board board) {
        this.cells = board.cells();
        repaint();
    }

    @Override
    public void on(Clicked clicked) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getCellWidth();
        int cellHeight = getCellHeight();

        for (int row = 0; row < game.board().rows(); row++) {
            for (int col = 0; col < game.board().columns(); col++) {
                int x = col * cellWidth + 5;
                int y = row * cellHeight + 5;
                software.ulpgc.MineSweeper.arquitecture.model.Cell current = cells[row][col];

                if (selectedPosition != null && (selectedPosition.x == row && selectedPosition.y == col)) {
                    paintSelectedCell(g, x, y, cellWidth, cellHeight);
                } else if (current.isRevealed()) {
                    if (current.hasMine()) {
                        try {
                            paintMine(g, x, y, cellWidth, cellHeight);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        paintCounter(g, current, x, y, cellWidth, cellHeight);
                    }
                } else if (current.isFlagged()) {
                    paintFlag(g, x, y, cellWidth, cellHeight);
                } else {
                    paintNoneRevealedCell(g, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private int getCellWidth() {
        return (getWidth() - 10) / game.board().columns();
    }

    private int getCellHeight() {
        return (getHeight() - 10) / game.board().rows();
    }

    private void paintSelectedCell(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        g.drawImage((java.awt.Image) images.get("Revealed.png"), x, y, cellWidth, cellHeight, null);
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void paintFlag(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        paintNoneRevealedCell(g, x, y, cellWidth, cellHeight);
        g.drawImage((java.awt.Image) images.get("flag.png"), x, y, cellWidth, cellHeight, null);
    }

    private void paintMine(Graphics g, int x, int y, int cellWidth, int cellHeight) throws IOException {
        g.drawImage((java.awt.Image) images.get("mine.png"), x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2,
                cellHeight / 2, null);
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void paintCounter(Graphics g, software.ulpgc.MineSweeper.arquitecture.model.Cell current, int x, int y,
            int cellWidth, int cellHeight) {
        if (current.adjacentMines() > 0) {
            String result = current.adjacentMines() + ".png";
            g.drawImage((java.awt.Image) images.get(result), x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2,
                    cellHeight / 2, null);
        }
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void paintNoneRevealedCell(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        g.drawImage((java.awt.Image) images.get("Default.png"), x, y, cellWidth, cellHeight, null);
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private int toRow(int y) {
        return (y - 5) / getCellHeight();
    }

    private int toColumn(int x) {
        return (x - 5) / getCellWidth();
    }

    private MouseListener createMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int currentRow = toRow(e.getY());
                int currentColumn = toColumn(e.getX());
                if (e.getButton() == MouseEvent.BUTTON1) {
                    
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    // no se que hacer
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedPosition = new Position(toRow(e.getY()), toColumn(e.getX()));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedPosition = null;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
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
}
