package Windows;

import Logic.Game;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * The representation of the name input dialog window
 * extends JDialog to work as a JDialog (dialog window)
 */
public class NameInputWindow extends JDialog{

    /* Attributes */

    //the game model (data)
    private final Game game;

    //the file path to the screenshot of the board we want to save with the game state and player name
    private final File boardImagePath;

    //the string typed into the input panel
    private String choosenName;

    //the input panel (textfield)
    private final JTextField inputField;

    /* Methods */

    /**
     * Constructor of name input window
     * initializes components
     * @param game: game model (data) of the program (Game)
     * @param file: the file path to the board screenshot to save the game state with
     */
    public NameInputWindow(Game game, File file){

        this.game = game;
        this.boardImagePath = file;

        //Colors

        Color background_color = new Color(243, 237, 216);
        Color board_color = new Color(187, 173, 160);
        Color dark_brown = new Color(119, 110, 100);
        Color button_color = new Color(187, 173, 160);

        //JDialog

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle("Save");
        this.setResizable(false);
        this.setSize(new Dimension(300, 200));
        this.setLocation(650, 300);
        this.setBackground(background_color);

        this.addKeyListener(new NameInputWindowController());

        GridLayout layout = new GridLayout(3, 1);

        JPanel panel = new JPanel(layout);
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setBackground(background_color);

        JLabel label = new JLabel("enter your name:");
        label.setBackground(background_color);
        label.setForeground(dark_brown);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setFocusable(false);

        this.inputField = new JTextField();
        inputField.setBackground(board_color);
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Tahoma", Font.PLAIN, 25));

        inputField.setHorizontalAlignment(JTextField.CENTER);

        inputField.getDocument().addDocumentListener(new InputFieldChangedListener());
        inputField.addKeyListener(new NameInputWindowController());

        JButton saveButton = new JButton("save");
        saveButton.setBackground(button_color);
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(150, 45));
        saveButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        saveButton.setFocusable(false);
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);
        saveButton.setVerticalAlignment(SwingConstants.CENTER);

        saveButton.setActionCommand("save button pressed");
        saveButton.addActionListener(new SaveButtonListener());

        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);
        saveButtonPanel.setBackground(background_color);

        panel.add(label, BorderLayout.CENTER);
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(saveButtonPanel, BorderLayout.CENTER);

        this.add(panel);

        this.requestFocusInWindow();
        this.setVisible(true);
    }

    /**
     * Saves the game state to the leaderboard with the
     * name typed in and the screenshot of the board
     */
    private void save(){
        game.saveBoard(choosenName, boardImagePath);
    }

    /**
     * Closes the dialog window
     */
    private void exit(){
        dispose();
    }

    /**
     * A listener for the input field
     */
    private class InputFieldChangedListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            choosenName = inputField.getText();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            choosenName = inputField.getText();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            choosenName = inputField.getText();
        }
    }

    /**
     * A listener for the save button
     */
    private class SaveButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //if save button pressed, save the game state with the name typed in
            if(e.getActionCommand().equals("save button pressed")){
                save();
                exit();
            }
        }
    }

    /**
     * Responds to keys
     */
    private class NameInputWindowController implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            //save and exit if enter pressed
            if(keyCode == KeyEvent.VK_ENTER){
                save();
                exit();
            }
            //exit if escape pressed
            else if(keyCode == KeyEvent.VK_ESCAPE){
                exit();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
