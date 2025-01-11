import java.io.*;

/**
 * The GameLogic class handles the game rules, player turns, and board state rotation.
 * It ensures that the game progresses correctly and alternates turns between players.
 * 
 * Author: Soo Wei Zhen, Ban Jue Ye
 */
class GameLogic {
    private Board board;
    private boolean blueTurn = true; // Tracks whether it's the blue player's turn.
    private boolean rotated = false; // Indicates whether the board is currently rotated.

    /**
     * Constructs a GameLogic instance and initializes the first turn to Blue.
     * Author: Soo Wei Zhen
     */
    public GameLogic(Board board) {
        this.board = board;
    }

    /**
     * Switches the turn to the other player.
     * Author: Soo Wei Zhen, Ban Jue Ye
     */
    public void switchTurn() {
        if(!blueTurn) {
            board.addTurn();
            handlePieceAlternation();
        }
        blueTurn = !blueTurn;
        rotated = !rotated;
    }

    /**
     * Checks if it's the blue player's turn.
     * @return true if it's Blue's turn, false otherwise.
     * Author: Soo Wei Zhen
     */
    public boolean isBlueTurn() {
        return blueTurn;
    }

    /**
     * Checks if the board is currently rotated.
     * @return true if the board is rotated, false otherwise.
     * Author: Soo Wei Zhen
     */
    public boolean isRotated() {
        return rotated;
    }

    /**
     * Handles the alternation of pieces on the board.
     * Author: Ban Jue Ye
     */
    public void handlePieceAlternation() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                board.toggleTorXor(i, j);
            }
        }
    }

    /**
     * Checks if the move is valid based on the piece's rules.
     * @param start Starting square.
     * @param end Ending square.
     * @return true if the move is valid, false otherwise.
     * Author: Soo Wei Zhen
     */
    public void saveGame(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Save turn
            writer.write("Turn:" + board.getTurn());
            writer.newLine();

            // Save blueTurn state
            writer.write("BlueTurn:" + blueTurn);
            writer.newLine();

            // Save isRotated state
            writer.write("IsRotated:" + rotated);
            writer.newLine();

            // Save board state
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    Square square = board.getSquare(i, j);
                    Piece piece = square.getPiece();
                    if (piece != null) {
                        if (piece instanceof Ram) {
                            Ram ramPiece = (Ram) piece;
                            writer.write(i + "," + j + "," + ramPiece.getSymbol() + "," + ramPiece.getColor() + ","
                                    + String.valueOf(ramPiece.getMovingForward()));
                            writer.newLine();
                        } else if (piece instanceof Biz) {
                            Biz bizPiece = (Biz) piece;
                            writer.write(i + "," + j + "," + bizPiece.getSymbol() + "," + bizPiece.getColor());
                            writer.newLine();
                        } else if (piece instanceof Sau) {
                            Sau sauPiece = (Sau) piece;
                            writer.write(i + "," + j + "," + sauPiece.getSymbol() + "," + sauPiece.getColor());
                            writer.newLine();
                        } else if (piece instanceof Xor) {
                            Xor xorPiece = (Xor) piece;
                            writer.write(i + "," + j + "," + xorPiece.getSymbol() + "," + xorPiece.getColor());
                            writer.newLine();
                        } else if (piece instanceof Tor) {
                            Tor torPiece = (Tor) piece;
                            writer.write(i + "," + j + "," + torPiece.getSymbol() + "," + torPiece.getColor());
                            writer.newLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game state from a file.
     * @param fileName Name of the file to load the game state from.
     * Author: Soo Wei Zhen
     */
    public void loadGame(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read turn
            line = reader.readLine();
            if (line.startsWith("Turn:")) {
                int turn = Integer.parseInt(line.split(":")[1]);
                board.setTurn(turn);
            }

            // Read blueTurn state
            line = reader.readLine();
            if (line.startsWith("BlueTurn:")) {
                blueTurn = Boolean.parseBoolean(line.split(":")[1]);
            }

            // Read isRotated state
            line = reader.readLine();
            if (line.startsWith("IsRotated:")) {
                rotated = Boolean.parseBoolean(line.split(":")[1]);
            }

            // Clear the board
            board.initializeSquare();

            // Read pieces
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int y = Integer.parseInt(parts[0]);
                int x = Integer.parseInt(parts[1]);
                String symbol = parts[2];
                String color = parts[3];

                Piece piece = null;
                if (symbol.equals("Biz")) {
                    piece = new Biz(color);
                } else if (symbol.equals("Ram")) {
                    boolean movingForward = Boolean.parseBoolean(parts[4]);
                    Ram rampiece = new Ram(color);
                    rampiece.setMovingForward(movingForward);
                    piece = rampiece;
                } else if (symbol.equals("Sau")) {
                    piece = new Sau(color);
                } else if (symbol.equals("Tor")) {
                    piece = new Tor(color);
                } else if (symbol.equals("Xor")) {
                    piece = new Xor(color);
                }

                if (piece != null) {
                    board.setSquarePiece(piece, y, x);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
