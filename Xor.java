
/**
 * Constructor to initialize a Xor piece with a color.
 * @param color Color of the Xor piece.
 * The Xor piece moves diagonally, meaning it can move across both rows and columns 
 * simultaneously, with the absolute difference in the x and y coordinates being equal.
 * This class follows the Strategy design pattern, where the isValidMove method 
 * defines the specific movement rules for this piece.
 * 
 * Author: Ban Jue Ye
 **/
public class Xor extends Piece {

    /**
     * Constructor to initialize a Xor piece with a color.
     * @param color Color of the Xor piece.
     */
    public Xor(String color) {
        super("Xor", color);
    }

    /**
     * Checks if the Xor's move is valid based on its custom rules.
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

        // Xor must move diagonally (absolute difference in x and y must be equal)
        if (Math.abs(endX - startX) != Math.abs(endY - startY)) {
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

        // Check the target square
        Piece targetPiece = end.getPiece();
        if (targetPiece == null) {
            return true; // Move to an empty square
        } else if (!targetPiece.getColor().equals(this.getColor())) {
            return true; // Capture an enemy piece
        }

        return false; // Invalid move (same color piece blocking the path)
    }
}
