import java.awt.Color;

public class ChessPieceFactory {
    public static Piece createPiece(String symbol, Color color, int x, int y) {
        switch (symbol) {
            case "R":
                return new Ram(color, x, y);
            case "B":
                return new Biz(color, x, y);
            // Add other cases for Rook, Knight, Bishop, etc.
            default:
                throw new IllegalArgumentException("Unknown piece type: " + symbol);
        }
    }
}
