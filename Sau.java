/**
     * Constructor to initialize a Sau piece with a color.
     * @param color Color of the Sau piece.
     * 
     * Author: Ban Jue Ye
     */
public class Sau extends Piece {

    /**
     * Constructor to initialize a Sau piece with a color.
     * @param color Color of the Sau piece.
     */
    public Sau(String color) {
        super("Sau", color); 
    }

    /**
     * Checks if the Sau's move is valid based on its custom rules.
     * @param start Starting square.
     * @param end Ending square.
     * @param board Current game board.
     * @return True if the move is valid, false otherwise.
     */
    @Override
    public boolean isValidMove(Square start, Square end, Board board) {
        int startX = start.getXPos();
        int startY = start.getYPos();
        int endX = end.getXPos();
        int endY = end.getYPos();

        // Sau can move one step in any direction
        int xDiff = Math.abs(endX - startX);
        int yDiff = Math.abs(endY - startY);

        if (xDiff <= 1 && yDiff <= 1) {
            Piece targetPiece = end.getPiece();

            // Allow the move if the square is empty or has an enemy piece
            return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
        }

        return false; // Invalid move
    }
}
