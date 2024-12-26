class Ram extends Piece {
    private boolean movingForward;

    public Ram(String color) {
        super("Ram", color);
        this.movingForward = true; // Initial direction is forward
    }

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

    public void setMovingForward(boolean bool) {
        movingForward = bool;
    }

    public boolean getMovingForward() {
        return movingForward;
    }
}
