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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Map;

public class SwingBoardDisplay extends JPanel implements BoardDisplay {
    private final Game game;
    private final Map<String, Image> images;
    private Position selectedPosition;

    public SwingBoardDisplay(Game game) {
        this.game = game;
        this.images = loadIcons();
        setDoubleBuffered(true);
        addMouseListener(createMouseListener());
    }

    private Map<String, Image> loadIcons() {
        return new FileImageLoader().load();
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
        if (cell.isRevealed()) {
            drawRevealedCell(g, cell, square);
        } else {
            drawHiddenCell(g, cell, square);
        }
    }
    
    private void drawRevealedCell(Graphics g, Cell cell, Square square) {
        g.setColor(cell.hasMine() ? Color.RED : Color.LIGHT_GRAY);
        g.fillRect(square.x(), square.y(), square.length(), square.length());
        g.setColor(Color.BLACK);
        g.drawRect(square.x(), square.y(), square.length(), square.length());
    }
    
    private void drawHiddenCell(Graphics g, Cell cell, Square square) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(square.x(), square.y(), square.length(), square.length());
        g.setColor(Color.BLACK);
        g.drawRect(square.x(), square.y(), square.length(), square.length());
    }
    



    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = toRow(e.getY());
                int col = toColumn(e.getX());

                if (e.getButton() == MouseEvent.BUTTON1) {
                    // handleLeftClick(row, col);
                    System.out.println("Row: " + row + " Col: " + col);

                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    // handleRightClick(row, col);
                    System.out.println("Right click");
                }
            }
        };
    }

    // private void handleLeftClick(int row, int col) {
    //     if (row < 0 || row >= game.board().rows() || col < 0 || col >= game.board().columns()) return;

    //     selectedPosition = new Position(row, col);
    //     Command revealCommand = game.createRevealCommand(row, col);
    //     revealCommand.execute();
    //     repaint(); // Repaint the board after a click
    // }

    // private void handleRightClick(int row, int col) {
    //     if (row < 0 || row >= game.board().rows() || col < 0 || col >= game.board().columns()) return;

    //     Command flagCommand = new ToggleFlagCommand(game, row, col);
    //     flagCommand.execute();
    //     repaint(); // Repaint the board after a click
    // }

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
}
