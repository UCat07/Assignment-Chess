import java.awt.event.*;
import java.awt.Color;

public class GameController {
    private Board board;
    private GameLogic gameLogic;
    private Square selectedSquare;

    public GameController(Board board, GameLogic gameLogic) {
        this.board = board;
        this.gameLogic = gameLogic;
        this.selectedSquare = null;

        // Add action listeners to all squares
        addActionListeners();
    }

    private void addActionListeners() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                Square square = board.getSquare(i, j);
                square.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleSquareClick(square);
                    }
                });
            }
        }
    }

    private void handleSquareClick(Square square) {
        if (selectedSquare == null) {
            // Select the square if it contains a piece and it is the player's turn
            if (square.getPiece() != null &&
                    ((gameLogic.isBlueTurn() && square.getPiece().getColor() == Color.CYAN) ||
                            (!gameLogic.isBlueTurn() && square.getPiece().getColor() == Color.RED))) {
                selectedSquare = square;
                square.setBackground(Color.YELLOW); // Highlight selected square
            }
        } else {
            // Ensure the piece is not attempting to move to a square with a piece of the same color
            if (square.getPiece() != null && square.getPiece().getColor() == selectedSquare.getPiece().getColor()) {
                // Do not allow the move if the piece on the square has the same color
                resetSelectedSquareColor();
                selectedSquare = null;
                return; // Exit the method if the move is invalid
            }

            // If the move is valid and the path is clear (for non-knight pieces)
            if (selectedSquare.getPiece().isValidMove(selectedSquare, square, board)) {
                square.setPiece(selectedSquare.getPiece());
                selectedSquare.setPiece(null);
                resetSelectedSquareColor();
                gameLogic.switchTurn(); // Rotate board and update display handled here
            } else {
                resetSelectedSquareColor();
            }
            selectedSquare = null; // Deselect the square
        }
    }

    // Reset the background color to the original checkerboard pattern
    private void resetSelectedSquareColor() {
        selectedSquare.setBackground(
                (selectedSquare.getXPos() + selectedSquare.getYPos()) % 2 == 0 ? new Color(235, 236, 208)
                        : new Color(119, 149, 86)); // Reset color to original
    }
}
