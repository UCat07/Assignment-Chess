import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Main class to initialize and manage the Chess Board game.
 * Handles GUI setup and integrates game logic.
 * Part of the Model-View-Controller (MVC) design pattern.
 * - This class acts as the "Controller".
 * 
 * Author: Soo Wei Zhen, Siao Wei Cheng
 */
public class Main {
    public static void main(String[] args) {
        // Create the board and game logic objects
        Board board = new Board();
        JLabel turnlabel = new JLabel("Turn:" + board.getTurn());
        JLabel colorlabel = new JLabel("Player turn: Blue");
        GameLogic gamelogic = new GameLogic(board);

        Controller boardGUI = new Controller(board, gamelogic, turnlabel, colorlabel);

        // Ask the user if they want to load a previous game
        int loadOption = JOptionPane.showConfirmDialog(null, "Do you want to load the previous game?", "Load Game",
                JOptionPane.YES_NO_OPTION);
        if (loadOption == JOptionPane.YES_OPTION) {
            // Load the previous game if the user selects Yes
            gamelogic.loadGame("save.txt");
            turnlabel.setText("Turn: " + board.getTurn());
            colorlabel.setText("Player turn: " + (gamelogic.isBlueTurn() ? "Blue" : "Red"));
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
                int saveOption = JOptionPane.showConfirmDialog(null, "Do you want to save the current game?",
                        "Save Game", JOptionPane.YES_NO_OPTION);
                if (saveOption == JOptionPane.YES_OPTION) {
                    gamelogic.saveGame("save.txt");
                }
            }
        });
    }
}
