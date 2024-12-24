import javax.swing.JOptionPane;

public class Main {
    public Main() {
        // Prompt the user to load a saved game or start a new one
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Do you want to load the previous game?",
                "Load Game",
                JOptionPane.YES_NO_OPTION
        );

        // Create the board
        Board board = new Board();

        // Create the game logic and associate it with the board
        GameLogic gameLogic = new GameLogic(board);
        board.setGameLogic(gameLogic);

        if (choice == JOptionPane.YES_OPTION) {
            // Attempt to load the saved game
            GameState loadedState = Save.loadGame("savefile.txt");
            if (loadedState != null) {
                
                // Restore the game state
                board.clear();
                gameLogic.setCurrentTurn(loadedState.getTurn());
                
                 // Apply the rotation based on the turn
                if (loadedState.getTurn() % 2 == 1) { // If the turn is odd, rotate the board
                    board.rotate();
                }

                // Add pieces to the board
                for (Piece piece : loadedState.getPieces()) {
                    board.addPiece(piece, piece.getYPos(), piece.getXPos());
                }
                
                // Update the board's display
                board.updateBoardDisplay();
                
                // After loading the game, reattach action listeners
                GameController gameController = new GameController(board, gameLogic);
                gameController.loadGame(loadedState);  // Call loadGame here to reset action listeners
            } else {
                JOptionPane.showMessageDialog(null, "No saved game found.");
            }
        } else {
            // Create a new game if the user chose not to load a saved game
            new GameController(board, gameLogic);  
        }
    }

    public static void main(String[] args) {
        new Main(); // Instantiate Main to run the setup logic
    }
}
