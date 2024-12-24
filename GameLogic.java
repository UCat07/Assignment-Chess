// Controller: Logic to manage the game

class GameLogic {
    private Board board;
    private boolean blueTurn;
    private int currentTurn;
    private boolean rotated;

    public GameLogic(Board board) {
        this.board = board;
        this.blueTurn = true;
        this.currentTurn = 1;
        this.rotated = false;
    }

    public void switchTurn() {
        blueTurn = !blueTurn;
        currentTurn++;
        board.rotate(); // Rotate the board after each turn
        board.updateBoardDisplay();  // Notify GUI to update the visual display after rotation
        rotated = !rotated;  // Toggle rotation state
    }

    public boolean isBlueTurn() {
        return blueTurn;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int turn) {
        this.currentTurn = turn;
        this.blueTurn = (turn % 2 == 1); // Assuming odd turns are blue's turn
    }

}
