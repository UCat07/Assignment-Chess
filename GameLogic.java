// Controller: Logic to manage the game

class GameLogic {
    private Board board;
    private boolean blueTurn;

    public GameLogic(Board board) {
        this.board = board;
        this.blueTurn = true;
    }

    public void switchTurn() {
        blueTurn = !blueTurn;
        board.rotate(); // Rotate the board after each turn
        board.updateBoardDisplay();  // Notify GUI to update the visual display after rotation
    }

    public boolean isBlueTurn() {
        return blueTurn;
    }
}
