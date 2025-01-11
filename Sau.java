public class Sau extends Piece {

    public Sau(String color) {
        super("Sau", color); // "S" represents Sau
    }

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
