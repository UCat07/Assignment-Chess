import java.awt.Color;

abstract class Piece {
    protected String symbol;
    protected Color color;

    public Piece(String symbol, Color color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean isValidMove(Square start, Square end, Board board);
}
