import javax.swing.JOptionPane;

/**
 * The Board class represents the game board for the chess-like game.
 * It manages the squares, pieces, and game turns.
 * This class is part of the MVC pattern and acts as the model.
 * 
 * Author: Soo Wei Zhen, Ban Jue Ye
 */
public class Board {
    private Square[][] square;
    private int height = 8;
    private int width = 5;
    private int turn = 1;

    /**
     * Constructor to initialize the board with squares and initial pieces.
     * Author: Soo Wei Zhen
     */
    public Board() {
        initializeSquare();
        initializePiece();
    }

    /**
     * Initializes the squares on the board.
     * Each square is represented as an instance of the Square class.
     * Author: Soo Wei Zhen
     */
    public void initializeSquare() {
        square = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                square[i][j] = new Square(i, j);
            }
        }
    }

    /**
     * Places initial pieces on the board at their starting positions.
     * Author: Soo Wei Zhen, Ban Jue Ye
     */
    public void initializePiece() {
        // Place Tor and Xor pieces
        square[0][0].setPiece(new Tor("RED"));
        square[0][4].setPiece(new Xor("RED"));
        square[7][4].setPiece(new Tor("BLUE"));
        square[7][0].setPiece(new Xor("BLUE"));

        // Place Sau pieces
        square[0][2].setPiece(new Sau("RED"));
        square[7][2].setPiece(new Sau("BLUE"));

        // Place Biz pieces
        square[0][1].setPiece(new Biz("RED"));
        square[0][3].setPiece(new Biz("RED"));
        square[7][1].setPiece(new Biz("BLUE"));
        square[7][3].setPiece(new Biz("BLUE"));

        // Place Ram pieces
        for (int i = 0; i < width; i++) {
            square[1][i].setPiece(new Ram("RED"));
            square[6][i].setPiece(new Ram("BLUE"));
        }

    }

    /**
     * Toggles a Tor piece to Xor and vice versa based on the turn count.
     * @param from The square where the piece is currently located.
     * @param to The square where the piece will be moved.
     * Author: Ban Jue Ye
     */
    public void toggleTorXor(int y, int x) {
        Square square = getSquare(y, x);
        Piece currentPiece = square.getPiece();

        if (currentPiece instanceof Tor && (getTurn() - 1) % 2 == 0) {
            // Change Tor to Xor after 2 turns
            square.setPiece(new Xor(currentPiece.getColor()));
        } else if (currentPiece instanceof Xor && (getTurn() - 1) % 2 == 0) {
            // Change Xor to Tor after 2 turns
            square.setPiece(new Tor(currentPiece.getColor()));
        }
    }

    /**
     * Moves a piece from one square to another.
     * Ends the game if the Sau piece is captured.
     * @param from The square where the piece is currently located.
     * @param to The square where the piece will be moved.
     * Author: Soo Wei Zhen, Ban Jue Ye
     */
    public void movePiece(Square from, Square to) {
        Piece movingPiece = from.getPiece();
        Piece targetPiece = to.getPiece();

        // Check if the target piece is a Sau
        if (targetPiece instanceof Sau) {
            String winner = targetPiece.getColor().equals("RED") ? "Blue" : "Red";
            endGame(winner);
            return;
        }

        to.setPiece(movingPiece);
        from.setPiece(null);
    }

    /**
     * Ends the game with a message showing the winner.
     * @param winner The color of the winning player.
     * Author: Soo Wei Zhen
     */
    private void endGame(String winner) {
        JOptionPane.showMessageDialog(null, winner + " wins! Game over.");

        System.exit(0);
    }

    /**
     * Checks if two squares have pieces of the same color.
     * @param from The first square.
     * @param to The second square.
     * @return True if both pieces are of the same color, false otherwise.
     * Author: Soo Wei Zhen
     */
    public Boolean sameColor(Square from, Square to) {
        if (to.getPiece() == null) {
            return false;
        } else {
            return (from.getPiece().getColor().equals(to.getPiece().getColor()));
        }
    }

    /**
     * Sets a piece at a specific square.
     * @param piece The piece to place.
     * @param y The y-coordinate of the square.
     * @param x The x-coordinate of the square.
     * Author: Soo Wei Zhen
     */
    public void setSquarePiece(Piece piece, int y, int x) {
        square[y][x].setPiece(piece);
    }

    /**
     * Gets the piece at a specific square.
     * @param y The y-coordinate of the square.
     * @param x The x-coordinate of the square.
     * @return The piece at the square.
     * Author: Soo Wei Zhen
     */
    public Square getSquare(int y, int x) {
        return square[y][x];
    }

    /**
     * @return The height of the board.
     * Author: Soo Wei Zhen
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The width of the board.
     * Author: Soo Wei Zhen
     */
    public int getWidth() {
        return width;
    }

    /**
     * Increments the turn count.
     * Author: Soo Wei Zhen
     */
    public void addTurn() {
        turn++;
    }

    /**
     * @return The current turn count.
     * Author: Soo Wei Zhen
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Sets the turn count.
     * @param turn The turn count to set.
     * Author: Soo Wei Zhen
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }
}
