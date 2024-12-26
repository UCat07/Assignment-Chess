abstract class Piece {
    private String symbol;
    private String color;

    public Piece(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public abstract boolean isValidMove(Square start, Square end, Board board );
}
