import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Board {
    private Square[][] square;
    private int width = 5;
    private int height = 8;
    private JPanel boardPanel;
    private JFrame frame;
    private boolean rotated = false;
    private GameLogic gameLogic;

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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you want to save the game before exiting?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    Save.saveGame("savefile.txt", gameLogic.getCurrentTurn(), gameLogic.isBlueTurn(), getAllPieces());
                    System.exit(0); // Exit after saving
                } else if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0); // Exit without saving
                }
                // If CANCEL or close the dialog, do nothing
            }
        });

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

    public List<Piece> getAllPieces() {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (square[i][j].getPiece() != null) {
                    pieces.add(square[i][j].getPiece());
                }
            }
        }
        return pieces;
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
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds: (" + y + ", " + x + ")");
        }
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

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                square[i][j].setPiece(null); // Remove all pieces from the squares
            }
        }
    }
}
