import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
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
                Piece movedPiece = selectedSquare.getPiece();
                square.setPiece(movedPiece);
                movedPiece.updatePosition(square.getXPos(), square.getYPos());
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

    // Load the game and re-attach action listeners
    public void loadGame(GameState loadedState) {
        board.clear();  // Clear all pieces from the board
        // Set the turn and pieces based on the loaded state
        gameLogic.setCurrentTurn(loadedState.getTurn());
        gameLogic.setBlueTurn(loadedState.isBlueTurn()); // Set blueTurn state  

        for (Piece piece : loadedState.getPieces()) {
            
            int xPos = piece.getXPos();
            int yPos = piece.getYPos();
    
            // Ensure the coordinates are within the board's bounds
            if (xPos >= 0 && xPos < board.getWidth() && yPos >= 0 && yPos < board.getHeight()) {
                Square square = board.getSquare(yPos, xPos); // Make sure to pass (y, x) since y is the row and x is the column
                square.setPiece(piece);
            } else {
                // Log an error or handle invalid coordinates
                System.out.println("Error: Piece at (" + xPos + ", " + yPos + ") is out of bounds!");
            }
        }

        // Apply rotation based on the turn after loading the game
        if (gameLogic.getCurrentTurn() % 2 == 1) {  // Odd turn = Blue's turn, rotate board
        board.rotate();
        }

        // After loading the game, add action listeners to squares again
        addActionListeners();  // Re-bind action listeners to all squares
    }
    
    public void saveGame() {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                Piece piece = board.getSquare(i, j).getPiece();
                if (piece != null) {
                    pieces.add(piece); // Add only non-null pieces to the list
                }
            }
        }
        Save.saveGame("savefile.txt", gameLogic.getCurrentTurn(), gameLogic.isBlueTurn(), pieces);
    }
}
