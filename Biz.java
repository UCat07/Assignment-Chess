/**
 * The Biz class represents a specific type of game piece that moves in an L-shape,
 * similar to a knight in chess. It extends the Piece class.
 * This class does not explicitly follow a design pattern but is part of the overall
 * game structure that may implement the Strategy pattern for movement rules.
 * 
 * Author: Ban Jue Ye
 */
public class Biz extends Piece {

    /**
     * Constructor for the Biz class.
     * 
     * @param color The color of the Biz piece (e.g., "white" or "black").
     *              Initializes the piece with its name and color.
     */
    public Biz(String color) {
        super("Biz", color);
    }

    /**
     * Determines if a move is valid for the Biz piece.
     * A Biz piece moves in an L-shape: two squares in one direction and 
     * one square perpendicular to that, or vice versa.
     * 
     * @param start The starting square of the move.
     * @param end The ending square of the move.
     * @param board The game board on which the move is performed.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean isValidMove(Square start, Square end, Board board) {
        int dx = Math.abs(start.getXPos() - end.getXPos());
        int dy = Math.abs(start.getYPos() - end.getYPos());
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}
