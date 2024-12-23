import java.awt.Color;

abstract class Piece {
    protected String symbol;
    protected Color color;
    protected int x, y;

    public Piece(String symbol, Color color, int x, int y) {
        this.symbol = symbol;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    // Serialize piece data to a string
    public String serialize() {
        return symbol + "," + color + "," + x + "," + y;
    }

    // Deserialize piece data from a string
    public static Piece deserialize(String data) {
        String[] parts = data.split(",");
        String symbol = parts[0];
        Color color;
        switch (parts[1].toUpperCase()) {
            case "RED":
                color = Color.RED;
                break;
            case "CYAN":
                color = Color.CYAN;
                break;
            default:
                throw new IllegalArgumentException("Unknown color: " + parts[1]);
        }
        int x = Integer.parseInt(parts[2]);
        int y = Integer.parseInt(parts[3]);
    
        return ChessPieceFactory.createPiece(symbol, color, x, y);
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean isValidMove(Square start, Square end, Board board);
}

