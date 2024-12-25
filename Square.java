class Square {
    private int y;
    private int x;
    private Piece piece;

    public Square(int y, int x) {
        this.y = y;
        this.x = x;
        this.piece = null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getYPos() {
        return y;
    }

    public int getXPos() {
        return x;
    }
}
