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
        // Validate coordinates
        if (x < 0 || x >= 5 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + ")");
        }

        String colorStr = color == Color.RED ? "255, 0, 0" : "0, 255, 255"; // Red or Cyan
        return symbol + "," + colorStr + "," + x + "," + y;
    }

    // Deserialize piece data from a string
    public static Piece deserialize(String data) {
        String[] parts = data.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid piece data: " + data);
        }
        String symbol = parts[0].trim();
        int r = Integer.parseInt(parts[1].trim());
        int g = Integer.parseInt(parts[2].trim());
        int b = Integer.parseInt(parts[3].trim());
        Color color = new Color(r, g, b);  // Use the RGB values directly

        int x = Integer.parseInt(parts[4].trim());
        int y = Integer.parseInt(parts[5].trim());
        
        // Validate coordinates before creating the piece
        if (x < 0 || x >= 5 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Invalid coordinates during deserialization: (" + x + ", " + y + ")");
        }

        return ChessPieceFactory.createPiece(symbol, color, x, y);
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    // Getter for X position
    public int getXPos() {
        return x;
    }

    // Getter for Y position
    public int getYPos() {
        return y;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isValidMove(Square start, Square end, Board board);
}

