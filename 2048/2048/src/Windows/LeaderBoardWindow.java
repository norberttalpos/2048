package Windows;

import Logic.LeaderBoard;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The representation of the leaderboard window
 * extends JPanel to be able to add to a JFrame
 */
public class LeaderBoardWindow extends JPanel {

    /* Attributes */

    //the leaderboard model (data)
    private final LeaderBoard leaderBoard;

    //the window of the application
    private final Frame window;

    //visualizes the leaderboard
    private final JTable table;

    /* Methods */

    /**
     * Constructor of LeaderBoardWindow class
     * initializes the components
     * @param lb: the leaderboard model we want to visualize (LeaderBoard)
     * @param frame: the window of the program (Frame)
     */
    public LeaderBoardWindow(LeaderBoard lb, Frame frame) {

        this.leaderBoard = lb;
        this.window = frame;

        Color background_color = new Color(243, 237, 216);
        Color button_color = new Color(187, 173, 160);

        this.addKeyListener(new LeaderBoardController());

        //Table

        this.table = new JTable(leaderBoard);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setDefaultRenderer(String.class, new LeaderBoardTableCellRenderer(table.getDefaultRenderer(String.class)));
        table.setDefaultRenderer(Integer.class, new LeaderBoardTableCellRenderer(table.getDefaultRenderer(Integer.class)));

        table.setFont(new Font("Tahoma", Font.PLAIN, 17));
        this.setLayout(new BorderLayout());

        table.getTableHeader().setBackground(button_color);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));

        //Scrollpane

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        //Buttons

        LeaderBoardButtonClickedListener lbbcl = new LeaderBoardButtonClickedListener();

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        exitButton.setBackground(button_color);
        exitButton.setForeground(Color.WHITE);
        exitButton.setPreferredSize(new Dimension(150, 30));

        exitButton.addActionListener(lbbcl);
        exitButton.setActionCommand("exit clicked");
        exitButton.setFocusable(false);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        deleteButton.setBackground(button_color);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(150, 30));

        deleteButton.addActionListener(lbbcl);
        deleteButton.setActionCommand("delete clicked");
        deleteButton.setFocusable(false);

        JButton viewBoardButton = new JButton("View");
        viewBoardButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        viewBoardButton.setBackground(button_color);
        viewBoardButton.setForeground(Color.WHITE);
        viewBoardButton.setPreferredSize(new Dimension(150, 30));

        viewBoardButton.addActionListener(lbbcl);
        viewBoardButton.setActionCommand("view clicked");
        viewBoardButton.setFocusable(false);

        //JPanel

        JPanel inputPanel = new JPanel();

        inputPanel.setBackground(background_color);

        inputPanel.add(exitButton);
        inputPanel.add(deleteButton);
        inputPanel.add(viewBoardButton);

        this.add(inputPanel, BorderLayout.SOUTH);

    }

    /**
     * Makes a new window with the screenshot of the game of the row selected
     * @param row: the row selected on the leaderboard (int)
     */
    private void viewBoard(int row){
        ViewBoardWindow viewBoardWindow = new ViewBoardWindow(leaderBoard.getBoardImage(row));
    }

    /**
     * Go back to the menu
     */
    private void exit() {
        window.backToMenu(this);
    }

    /**
     * Custom renderer of the table to be able to set the cell colors and alignment
     */
    class LeaderBoardTableCellRenderer implements TableCellRenderer {

        private final TableCellRenderer renderer;

        public LeaderBoardTableCellRenderer(TableCellRenderer defRenderer) {
            this.renderer = defRenderer;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            component.setBackground(new Color(243, 237, 216));

            JLabel label = (JLabel) component;
            label.setHorizontalAlignment( JLabel.CENTER );

            return component;
        }
    }

    /**
     * A listener for the buttons
     */
    private class LeaderBoardButtonClickedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //go back to menu if exit button clicked
            if(e.getActionCommand().equals("exit clicked")){
                exit();
            }

            //delete selected row if delete button clicked
            else if (e.getActionCommand().equals("delete clicked")) {

                //if no rows selected, don't delete
                if(table.getSelectedRow() >= 0){
                    leaderBoard.deleteRow(table.getSelectedRow());
                    try{
                        leaderBoard.save();
                    }
                    catch(Exception exc){
                        exc.printStackTrace();
                    }

                    table.clearSelection();
                    requestFocusInWindow();

                    table.repaint();
                }
            }

            //view board screenshot if view button clicked
            else if(e.getActionCommand().equals("view clicked")){

                //if no rows selected, do nothing
                if(table.getSelectedRow() >= 0){

                    viewBoard(table.getSelectedRow());

                    table.clearSelection();
                    requestFocusInWindow();
                }

            }
        }
    }

    /**
     * Responds to keys
     */
    private class LeaderBoardController implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch(key){
                //quit if escape pressed (only works when the focus is not on the table)
                case KeyEvent.VK_ESCAPE:
                    exit();
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}