import java.io.*;

class GameLogic {
    private Board board;
    private boolean blueTurn = true;
    private boolean rotated = false;

    public GameLogic(Board board) {
        this.board = board;
    }

    public void switchTurn() {
        if(!blueTurn) {
            board.addTurn();
            handlePieceAlternation();
        }
        blueTurn = !blueTurn;
        rotated = !rotated;
    }

    public boolean isBlueTurn() {
        return blueTurn;
    }

    public boolean isRotated() {
        return rotated;
    }

    public void handlePieceAlternation() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                board.toggleTorXor(i, j);
            }
        }
    }

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
