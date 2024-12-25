import java.awt.Color;

public class Tor extends Piece {

    public Tor(Color color, int x, int y) {
        super("T", color, x, y); // "T" represents Tor
    }

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
