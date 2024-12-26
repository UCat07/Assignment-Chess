class Biz extends Piece {
    public Biz(String color) {
        super("Biz", color);
    }

    @Override
    public boolean isValidMove(Square start, Square end, Board board) {
        // Knight moves in L-shape
        int dx = Math.abs(start.getXPos() - end.getXPos());
        int dy = Math.abs(start.getYPos() - end.getYPos());
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}
