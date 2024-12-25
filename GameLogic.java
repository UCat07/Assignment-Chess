// Controller: Logic to manage the game

class GameLogic {
    private Board board;
    private boolean blueTurn;
    private int turn;
    private boolean rotated;

    public GameLogic(Board board) {
        this.board = board;
        this.blueTurn = true;
        this.turn = 1;
        this.rotated = false;
    }

    public void switchTurn() {
        blueTurn = !blueTurn;
        // Increment fullTurn only when Blue's turn starts
        if (blueTurn) {
            turn++;
        }
        board.rotate(); // Rotate the board after each turn
        board.updateBoardDisplay();  // Notify GUI to update the visual display after rotation
        rotated = !rotated;  // Toggle rotation state
    }

    public boolean isBlueTurn() {
        return blueTurn;
    }

    public int getCurrentTurn() {
        return turn;
    }

    public void setCurrentTurn(int turn) {
        this.turn = turn;
        this.blueTurn = true;
    }

    public void setBlueTurn(boolean blueTurn) {
        this.blueTurn = blueTurn;
    }

}
