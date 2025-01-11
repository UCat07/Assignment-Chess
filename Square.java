/**
 * Represents a square (cell) on the game board.
 * 
 * Author: Soo Wei Zhen
 */
class Square {
    private int y;
    private int x;
    private Piece piece;

    /**
     * Constructor to initialize a square with its position.
     * @param y Row position.
     * @param x Column position.
     */
    public Square(int y, int x) {
        this.y = y;
        this.x = x;
        this.piece = null;
    }

    /**
     * Sets the piece occupying the square.
     * @param piece Piece to be placed on the square.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Gets the piece currently occupying the square.
     * @return Piece on the square.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Gets the row position of the square.
     * @return Row position.
     */
    public int getYPos() {
        return y;
    }

    /**
     * Gets the column position of the square.
     * @return Column position.
     */
    public int getXPos() {
        return x;
    }
}
