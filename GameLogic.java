// Controller: Logic to manage the game

class GameLogic {
    private Board board;
    private boolean blueTurn;
    private ChessController ctr;  // Reference to the ChessController to update the display

    public GameLogic(Board board, ChessController ctr) {
        this.board = board;
        this.ctr = ctr;  // Pass the ChessController reference
        this.blueTurn = true;
    }

    public void switchTurn() {
        blueTurn = !blueTurn;
        board.rotate(); // Rotate the board after each turn
        board.updateBoardDisplay();  // Notify Controller to update the visual display after rotation
    }

    public boolean isBlueTurn() {
        return blueTurn;
    }
}
