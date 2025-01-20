/**
 * Represents the "Ram" piece in the game.
 * Implements custom movement rules for this piece.
 * 
 * Author: Ban Jue Ye
 */
class Ram extends Piece {
    private boolean movingForward;

    /**
     * Constructor to initialize a Ram piece with a color.
     * @param color Color of the Ram piece.
     */
    public Ram(String color) {
        super("Ram", color);
        this.movingForward = true; // Initial direction is forward
    }

    /**
     * Checks if the Ram's move is valid based on its custom rules.
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

        // Determine direction based on movingForward flag and color
        int direction = (getColor().equals("RED")) ? (movingForward ? 1 : -1)
                : (movingForward ? -1 : 1);

        // Ram moves forward or backward by 1 square in the same column
        if (endX == startX && endY == startY + direction) {
            // Check if the end square is empty

            // Toggle direction if reaching the edge
            if ((endY == 0 || endY == 7) && !board.sameColor(start, end)) {
                movingForward = !movingForward; // Change direction
            }
            return true; // Valid move
        }
        return false; // Invalid move
    }

    /**
     * Sets the direction of the Ram's movement.
     * @param bool True for forward, false for backward.
     */
    public void setMovingForward(boolean bool) {
        movingForward = bool;
    }

    /**
     * Gets the current movement direction of the Ram.
     * @return True if moving forward, false otherwise.
     */
    public boolean getMovingForward() {
        return movingForward;
    }
}
