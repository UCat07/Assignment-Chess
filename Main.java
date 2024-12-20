public class Main {
    public static void main(String[] args) {
        // Create the board and game logic
        Board board = new Board();
        GameLogic gameLogic = new GameLogic(board);

        // Create the game controller
        GameController controller = new GameController(board, gameLogic);

        // The game is now set up, and the GUI is ready to interact with the user
    }
}
