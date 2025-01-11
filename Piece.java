/**
 * Abstract base class for all game pieces.
 * Defines the common properties and behaviors of pieces.
 * Part of the Template Method design pattern.
 * 
 * Author: Siao Wei Cheng
 */
abstract class Piece {
    private String symbol;
    private String color;

    /**
     * Constructor to initialize the piece with a symbol and color.
     * @param symbol Symbol representing the piece.
     * @param color Color of the piece (e.g., "Red", "Blue").
     */
    public Piece(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    /**
     * Getter for the symbol of the piece.
     * @return Symbol of the piece.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter for the color of the piece.
     * @return Color of the piece.
     */
    public String getColor() {
        return color;
    }

    /**
     * Abstract method to determine if a move is valid.
     * Must be implemented by all concrete subclasses.
     * @param start Starting square of the move.
     * @param end Ending square of the move.
     * @param board Current state of the board.
     * @return True if the move is valid, false otherwise.
     */
    public abstract boolean isValidMove(Square start, Square end, Board board );
}
