import java.util.List;

// GameState.java (to encapsulate game state)
class GameState {
    private int turn;
    private boolean blueTurn;
    private List<Piece> pieces;

    public GameState(int turn, boolean blueTurn, List<Piece> pieces) {
        this.turn = turn;
        this.blueTurn = blueTurn;
        this.pieces = pieces;
    }

    public boolean isBlueTurn() {
        return blueTurn;
    }

    public int getTurn() {
        return turn;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

}
