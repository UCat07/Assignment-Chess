import javax.swing.*;
import java.awt.*;

public class Board {
    private Square[][] square;
    private int width = 5;
    private int height = 8;
    private JPanel boardPanel;
    private JFrame frame;
    private boolean rotated = false;

    public Board() {
        square = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                square[i][j] = new Square(i, j);
            }
        }
        initialize();
        setupInitialPieces();
    }

    public void initialize() {
        frame = new JFrame("Chess Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(height, width));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Square square = getSquare(i, j);
                square.setFont(new Font("Arial", Font.BOLD, 24));
                boardPanel.add(square);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void setupInitialPieces() {
        // Add Ram
        for (int i = 0; i < width; i++) {
            addPiece(new Ram(Color.CYAN, i, 7), 7, i);
            addPiece(new Ram(Color.RED, i, 0), 0, i);
        }

        // Add Biz for BLUE and red
        for (int i = 0; i < width; i++) {
            addPiece(new Biz(Color.CYAN, i, 6), 6, i);
            addPiece(new Biz(Color.RED, i, 1), 1, i);
        }
    }

    public void updateBoardDisplay() {
        // Clear the current board panel
        boardPanel.removeAll();

        // Re-add the squares with their updated piece positions
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Square square = getSquare(i, j);

                // Reset the square's background color based on its position
                if (!rotated) {
                    square.setBackground((i + j) % 2 == 0 ? new Color(235, 236, 208) : new Color(119, 149, 86));
                } else {
                    square.setBackground((i + j) % 2 == 0 ? new Color(119, 149, 86) : new Color(235, 236, 208));
                }
                square.setFont(new Font("Arial", Font.BOLD, 24));

                // Set the piece on the square (if any)
                Piece piece = square.getPiece();
                square.setText(piece != null ? piece.getSymbol() : "");
                square.setForeground(piece != null ? piece.getColor() : Color.BLACK);

                // Add the square to the panel
                boardPanel.add(square);
            }
        }

        // Refresh the display
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void addPiece(Piece piece, int y, int x) {
        square[y][x].setPiece(piece);
    }

    public Square getSquare(int y, int x) {
        return square[y][x];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void rotate() {
        rotated = !rotated;
        Square[][] rotatedSquare = new Square[height][width];

        // Rotate the squares and ensure pieces are transferred correctly
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rotatedSquare[height - 1 - i][width - 1 - j] = square[i][j];
            }
        }
        square = rotatedSquare;
    }
}
