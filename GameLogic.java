import java.io.*;

class GameLogic {
    private Board board;
    private boolean blueTurn = true;
    private boolean rotated = false;

    public GameLogic(Board board) {
        this.board = board;
    }

    public void switchTurn() {
        blueTurn = !blueTurn;
        rotated = !rotated;
        board.addTurn();
    }

    public boolean isBlueTurn() {
        return blueTurn;
    }

    public boolean isRotated() {
        return rotated;
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
                        writer.write(i + "," + j + "," + piece.getSymbol() + "," + piece.getColor());
                        writer.newLine();
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
                    piece = new Ram(color);
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
