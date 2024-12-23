import java.awt.*;

class ChessController {
    private Board board;
    private GameLogic gameLogic;
    private Square selectedSquare;

    public ChessController() {
        board = new Board();
        gameLogic = new GameLogic(board, this); // Pass 'this' to GameLogic as ChessController reference
        initialize();
    }

    private void initialize() {
        // Set up action listeners for the squares on the board
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                Square square = board.getSquare(i, j);
                square.addActionListener(e -> handleSquareClick(square)); // Handle square click
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

            boolean pathClear = true;
            if (!(selectedSquare.getPiece() instanceof Biz)) {
                pathClear = isPathClear(selectedSquare, square); // Only check path for non-knight pieces
            }

            // If the move is valid and the path is clear (for non-knight pieces)
            if (selectedSquare.getPiece().isValidMove(selectedSquare, square, board) && pathClear) {
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

    private void resetSelectedSquareColor() {
        selectedSquare.setBackground(
                (selectedSquare.getXPos() + selectedSquare.getYPos()) % 2 == 0 ? new Color(235, 236, 208)
                        : new Color(119, 149, 86)); // Reset color to original
    }

    private boolean isPathClear(Square start, Square end) {
        int startX = start.getXPos();
        int startY = start.getYPos();
        int endX = end.getXPos();
        int endY = end.getYPos();

        int dx = Integer.compare(endX, startX); // Direction of movement along X (1 for right, -1 for left, 0 for no movement)
        int dy = Integer.compare(endY, startY); // Direction of movement along Y (1 for down, -1 for up, 0 for no movement)

        int x = startX + dx;
        int y = startY + dy;

        // Check if the movement is along a straight line or diagonal and if there are any pieces blocking the path
        while (x != endX || y != endY) {
            Square square = board.getSquare(x, y);
            if (square.getPiece() != null) {
                return false; // Path is blocked
            }
            x += dx;
            y += dy;
        }
        return true; // Path is clear
    }
    
}
