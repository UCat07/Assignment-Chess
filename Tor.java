/**
 * Constructor to initialize a Tor piece with a color.
 * @param color Color of the Tor piece.
 * The Tor piece moves orthogonally, meaning it can move horizontally or vertically, 
 * but not diagonally. It stops at either an empty square or an enemy piece.
 * This class follows the Strategy design pattern, where the isValidMove method 
 * defines the specific movement rules for this piece.
 * 
 * Author: Ban Jue Ye
 **/
public class Tor extends Piece {

    /**
     * Constructor to initialize a Tor piece with a color.
     * @param color Color of the Tor piece.
     */
    public Tor(String color) {
        super("Tor", color); 
    }

    /**
     * Checks if the Tor's move is valid based on its custom rules.
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

        // Tor must move orthogonally (either same row or same column)
        if (startX != endX && startY != endY) {
            return false;
        }

        // Determine the step direction
        int stepX = Integer.signum(endX - startX);
        int stepY = Integer.signum(endY - startY);

        int currX = startX + stepX;
        int currY = startY + stepY;

        // Check the path for any blocking pieces
        while (currX != endX || currY != endY) {
            if (board.getSquare(currY, currX).getPiece() != null) {
                return false; // Path is blocked
            }
            currX += stepX;
            currY += stepY;
        }

        // The destination must either be empty or contain an enemy piece
        Piece targetPiece = end.getPiece();
        return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
    }
}
