import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The BoardPanel class is responsible for rendering the game board on the GUI.
 * It handles user interactions, such as mouse clicks, and updates the board state
 * accordingly. This class interacts with the Board and GameLogic classes to
 * implement the game's behavior.
 * 
 * Author: Soo Wei Zhen
 */
public class BoardPanel extends JPanel {
    private Board board; // Represents the game board with its squares.
    private GameLogic gamelogic; // Manages game rules and player turns.
    private Square clickedSquare = null; // Tracks the currently selected square.
    private JLabel turnlabel; // Label displaying the current turn.
    private JLabel colorlabel; // Label displaying the current player's color.
    private Image image; // Holds the piece image for rendering.

    /**
     * Constructs a BoardPanel with the given board, game logic, and labels.
     * @param board the game board object
     * @param gamelogic the game logic object
     * @param turnlabel the label displaying the current turn
     * @param colorlabel the label displaying the current player's color
     */
    public BoardPanel(Board board, GameLogic gamelogic, JLabel turnlabel, JLabel colorlabel) {
        this.board = board;
        this.gamelogic = gamelogic;
        this.turnlabel = turnlabel;
        this.colorlabel = colorlabel;
        setPreferredSize(new Dimension(400, 640));
        // Add a MouseListener to the panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Get the mouse position on the panel
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Calculate the square that was clicked based on the mouse position
                int squareSize = Math.min(getWidth() / board.getWidth(), getHeight() / board.getHeight());
                int clickedRow = mouseY / squareSize;
                int clickedCol = mouseX / squareSize;

                // Adjust if the board is rotated
                if (gamelogic.isRotated()) {
                    clickedRow = board.getHeight() - 1 - clickedRow;
                    clickedCol = board.getWidth() - 1 - clickedCol;
                }

                Square square = board.getSquare(clickedRow, clickedCol);

                if (clickedSquare == null) {
                    // Handle selecting a square with a piece of the correct color.
                    if (square.getPiece() != null &&
                            ((gamelogic.isBlueTurn() && "BLUE".equals(square.getPiece().getColor())) ||
                                    (!gamelogic.isBlueTurn() && "RED".equals(square.getPiece().getColor())))) {
                        clickedSquare = square;
                        repaint();
                    }
                } else {
                    // Handle moving a piece to a valid destination.
                    Piece piece = clickedSquare.getPiece();
                    if (piece.isValidMove(clickedSquare, square, board) && !board.sameColor(clickedSquare, square)) {
                        board.movePiece(clickedSquare, square);
                        gamelogic.switchTurn();
                        labelchange();
                    }
                    clickedSquare = null;
                    repaint();
                }
            }
        });
    }

    /**
     * Updates the turn and player color labels.
     */
    private void labelchange() {
        turnlabel.setText("Turn: " + board.getTurn());
        colorlabel.setText("Player turn: " + (gamelogic.isBlueTurn() ? "Blue" : "Red"));
    }

    /**
     * Paints the game board and its pieces onto the panel.
     * @param g the Graphics object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int squareSize = Math.min(getWidth() / board.getWidth(), getHeight() / board.getHeight());

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                int drawRow = i;
                int drawCol = j;

                // Adjust drawing indices if the board is rotated
                if (gamelogic.isRotated()) {
                    drawRow = board.getHeight() - 1 - i;
                    drawCol = board.getWidth() - 1 - j;
                }

                // Get the square at the adjusted position
                Square square = board.getSquare(i, j); // i, j for actual board logic

                // Determine the color of the square

                Color squareColor = ((i + j) % 2 == 0) ? new Color(235, 236, 208) : new Color(119, 149, 86);
                g.setColor(squareColor);

                // Highlight the clicked square.
                if (clickedSquare != null && clickedSquare == square) {
                    g.setColor(Color.YELLOW);
                }

                // Calculate the x, y position based on drawRow and drawCol
                int x = drawCol * squareSize;
                int y = drawRow * squareSize;
                g.fillRect(x, y, squareSize, squareSize);

                // Draw the piece if present
                Piece piece = square.getPiece();
                if (piece != null) {
                    ImagePanel(piece);
                    Image scaledImage = image.getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH);
                    g.drawImage(scaledImage, x, y, this); // Draw the scaled image at the (x, y) position

                }
            }
        }
    }

    /**
     * Loads and sets the image for the given piece based on its type and state.
     * @param piece the piece whose image is to be loaded
     */
    public void ImagePanel(Piece piece) {
        try {
            if (!gamelogic.isRotated()) {
                if (piece instanceof Ram && piece.getColor().equals("RED")) {
                    Ram rampiece = (Ram) piece;
                    if (rampiece.getMovingForward()) {
                        image = ImageIO.read(new File("Piece PNG/Ram Red.png"));
                    } else {
                        image = ImageIO.read(new File("Piece PNG/Ram Red Rotated.png"));
                    }
                } else if (piece instanceof Sau && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Sau Red.png"));
                } else if (piece instanceof Biz && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Biz Red.png"));
                } else if (piece instanceof Tor && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Tor Red.png"));
                } else if (piece instanceof Xor && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Xor Red.png"));
                } else if (piece instanceof Ram && piece.getColor().equals("BLUE")) {
                    Ram rampiece = (Ram) piece;
                    if (rampiece.getMovingForward()) {
                        image = ImageIO.read(new File("Piece PNG/Ram Blue.png"));
                    } else {
                        image = ImageIO.read(new File("Piece PNG/Ram Blue Rotated.png"));
                    }
                } else if (piece instanceof Sau && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Sau Blue.png"));
                } else if (piece instanceof Biz && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Biz Blue.png"));
                } else if (piece instanceof Tor && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Tor Blue.png"));
                } else if (piece instanceof Xor && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Xor Blue.png"));
                }
            } else {
                if (piece instanceof Ram && piece.getColor().equals("RED")) {
                    Ram rampiece = (Ram) piece;
                    if (rampiece.getMovingForward()) {
                        image = ImageIO.read(new File("Piece PNG/Ram Red Rotated.png"));
                    } else {
                        image = ImageIO.read(new File("Piece PNG/Ram Red.png"));
                    }
                } else if (piece instanceof Sau && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Sau Red Rotated.png"));
                } else if (piece instanceof Biz && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Biz Red Rotated.png"));
                } else if (piece instanceof Tor && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Tor Red Rotated.png"));
                } else if (piece instanceof Xor && piece.getColor().equals("RED")) {
                    image = ImageIO.read(new File("Piece PNG/Xor Red Rotated.png"));
                } else if (piece instanceof Ram && piece.getColor().equals("BLUE")) {
                    Ram rampiece = (Ram) piece;
                    if (rampiece.getMovingForward()) {
                        image = ImageIO.read(new File("Piece PNG/Ram Blue Rotated.png"));
                    } else {
                        image = ImageIO.read(new File("Piece PNG/Ram Blue.png"));
                    }
                } else if (piece instanceof Sau && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Sau Blue Rotated.png"));
                } else if (piece instanceof Biz && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Biz Blue Rotated.png"));
                } else if (piece instanceof Tor && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Tor Blue Rotated.png"));
                } else if (piece instanceof Xor && piece.getColor().equals("BLUE")) {
                    image = ImageIO.read(new File("Piece PNG/Xor Blue Rotated.png"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
