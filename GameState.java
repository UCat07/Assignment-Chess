import java.util.List;

// GameState.java (to encapsulate game state)
class GameState {
    private int turn;
    private List<Piece> pieces;

    public GameState(int turn, List<Piece> pieces) {
        this.turn = turn;
        this.pieces = pieces;
    }

    public int getTurn() {
        return turn;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
