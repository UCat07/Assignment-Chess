import javax.swing.JOptionPane;

public class Board {
    private Square[][] square;
    private int height = 8;
    private int width = 5;
    private int turn = 0;

    public Board() {
        initializeSquare();
        initializePiece();
    }

    public void initializeSquare() {
        square = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                square[i][j] = new Square(i, j);
            }
        }
    }

    public void initializePiece() {

        square[0][0].setPiece(new Tor("RED"));
        square[0][4].setPiece(new Xor("RED"));
        square[7][4].setPiece(new Tor("BLUE"));
        square[7][0].setPiece(new Xor("BLUE"));

        square[0][2].setPiece(new Sau("RED"));
        square[7][2].setPiece(new Sau("BLUE"));

        square[0][1].setPiece(new Biz("RED"));
        square[0][3].setPiece(new Biz("RED"));
        square[7][1].setPiece(new Biz("BLUE"));
        square[7][3].setPiece(new Biz("BLUE"));

        for (int i = 0; i < width; i++) {
            square[1][i].setPiece(new Ram("RED"));
            square[6][i].setPiece(new Ram("BLUE"));
        }

    }

    public void toggleTorXor(int y, int x) {
        Square square = getSquare(y, x);
        Piece currentPiece = square.getPiece();

        if (currentPiece instanceof Tor && getTurn() % 4 == 2) {
            // Change Tor to Xor after 2 turns
            square.setPiece(new Xor(currentPiece.getColor()));
        } else if (currentPiece instanceof Xor && getTurn() % 4 == 0) {
            // Change Xor to Tor after 2 more turns
            square.setPiece(new Tor(currentPiece.getColor()));
        }
    }

    public void movePiece(Square from, Square to) {
        Piece movingPiece = from.getPiece();
        Piece targetPiece = to.getPiece();

        // Check if the target piece is a Sau
        if (targetPiece instanceof Sau) {
            String winner = targetPiece.getColor().equals("RED") ? "Blue" : "Red";
            endGame(winner);
            return;
        }

        to.setPiece(movingPiece);
        from.setPiece(null);
    }

    private void endGame(String winner) {
        JOptionPane.showMessageDialog(null, winner + " wins! Game over.");

        System.exit(0);
    }

    public Boolean sameColor(Square from, Square to) {
        if (to.getPiece() == null) {
            return false;
        } else {
            return (from.getPiece().getColor().equals(to.getPiece().getColor()));
        }
    }

    public void setSquarePiece(Piece piece, int y, int x) {
        square[y][x].setPiece(piece);
    }

    public Square getSquare(int y, int x) {
        return square[y][x];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addTurn() {
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
