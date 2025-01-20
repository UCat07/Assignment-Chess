import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Main class to initialize and manage the Chess Board game.
 * Handles GUI setup and integrates game logic.
 * Using Model-View-Controller (MVC) design pattern.
 * 
 * Author: Soo Wei Zhen, Siao Wei Cheng
 */
public class Main {
    private static final String SAVE_DIR = "saved_games";

    public static void main(String[] args) {
        // Create the board and game logic objects
        Board board = new Board();
        JLabel turnlabel = new JLabel("Turn: " + board.getTurn());
        JLabel colorlabel = new JLabel("Player turn: Blue");
        GameLogic gamelogic = new GameLogic(board);

        Controller boardGUI = new Controller(board, gamelogic, turnlabel, colorlabel);

        // Ensure the save directory exists
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        // Ask the user if they want to load a previous game
        File[] savedGames = saveDir.listFiles((_, name) -> name.endsWith(".txt"));
        if (savedGames != null && savedGames.length > 0) {
            String[] options = Arrays.stream(savedGames)
                                     .map(File::getName)
                                     .toArray(String[]::new);
            String selectedGame = (String) JOptionPane.showInputDialog(
                    null,
                    "Select a saved game to load:",
                    "Load Game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (selectedGame != null) {
                gamelogic.loadGame(SAVE_DIR + "/" + selectedGame);
                turnlabel.setText("Turn: " + board.getTurn());
                colorlabel.setText("Player turn: " + (gamelogic.isBlueTurn() ? "Blue" : "Red"));
            }
        }

        // Create the main frame
        JFrame frame = new JFrame("Chess Board");
        frame.setLayout(new BorderLayout());
        frame.add(colorlabel, BorderLayout.SOUTH);
        frame.add(turnlabel, BorderLayout.NORTH);
        frame.add(boardGUI, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Prompt the user to save the game when closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String fileName = JOptionPane.showInputDialog(null,
                        "Enter a name for your saved game:",
                        "Save Game",
                        JOptionPane.PLAIN_MESSAGE);

                if (fileName != null && !fileName.trim().isEmpty()) {
                    fileName = fileName.trim() + ".txt";
                    gamelogic.saveGame(SAVE_DIR + "/" + fileName);
                    JOptionPane.showMessageDialog(null, "Game saved as " + fileName, "Game Saved", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
