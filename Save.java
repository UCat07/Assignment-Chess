import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Save.java
public class Save {

    // Save the game state to a file
    public static void saveGame(String fileName, GameLogic gameLogic, List<Piece> pieces) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the current turn
            writer.write("Turn:" + gameLogic.getCurrentTurn());
            writer.newLine();

            // Write each piece's data
            for (Piece piece : pieces) {
                writer.write(piece.serialize());
                writer.newLine();
            }

            System.out.println("Game saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    // Load the game state from a file
    public static GameState loadGame(String fileName) {
        int turn = 0;
        List<Piece> pieces = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Read the turn
            String line = reader.readLine();
            if (line.startsWith("Turn:")) {
                turn = Integer.parseInt(line.split(":")[1]);
            }

            // Read each piece's data
            while ((line = reader.readLine()) != null) {
                try {
                    pieces.add(Piece.deserialize(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Error deserializing piece data: " + e.getMessage());
                }
            }

            System.out.println("Game loaded successfully from " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File " + fileName + " not found.");
        } catch (IOException e) {
            System.err.println("Error loading game from " + fileName + ": " + e.getMessage());
        }

        return new GameState(turn, pieces);
    }
}
