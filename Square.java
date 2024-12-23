import javax.swing.*;
import java.awt.*;

class Square extends JButton {
    private int x, y;
    private Piece piece;

    public Square(int y, int x) {
        this.x = x;
        this.y = y;
        this.piece = null;
        setBackground((x + y) % 2 == 0 ? new Color(235, 236, 208) : new Color(119, 149, 86));
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        setText(piece != null ? piece.getSymbol() : "");
        setForeground(piece != null ? piece.getColor() : Color.BLACK);
    }

    public Piece getPiece() {
        return piece;
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }
}
