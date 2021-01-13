package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

/**
 * The representation of the menu window
 * extends JPanel to be able to add to a JFrame
 */
public class MenuWindow extends JPanel{

    /* Attributes */

    //slider for choosing the board size
    private final JSlider slider;

    //the window of the application
    private final Frame window;

    /* Methods */

    /**
     * Constructor of MenuWindow class
     * initializes the components of the window
     * @param frame: the window of the program (Frame)
     */
    public MenuWindow(Frame frame){

        //to be able to access the containing frame
        this.window = frame;

        this.addKeyListener(new MenuController());

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setPreferredSize(new Dimension(450, 650));

        //Colors
        Color background_color = new Color(243, 237, 216);
        Color text_color = new Color(119, 110, 100);
        Color button_color = new Color(187, 173, 160);


        //Panels
        JPanel panel_2048 = new JPanel();
        panel_2048.setBackground(background_color);

        JPanel panel_game = new JPanel();
        panel_game.setBackground(background_color);

        JPanel panel_leaderboard = new JPanel();
        panel_leaderboard.setBackground(background_color);

        JPanel panel_slider_text = new JPanel();
        panel_slider_text.setBackground(background_color);

        JPanel panel_slider = new JPanel(new GridLayout(2, 1));
        panel_slider.setBackground(background_color);

        JPanel panel_slider_layout = new JPanel(new GridLayout(2, 1));
        panel_slider_layout.setBackground(background_color);


        this.setLayout(new GridLayout(4, 1));
        this.setBackground(background_color);


        //Texts
        JLabel text_2048 = new JLabel("2048");
        text_2048.setFont(new Font("Tahoma", Font.BOLD, 90));
        text_2048.setForeground(text_color);

        JLabel text_slider = new JLabel("choose board size");
        text_slider.setFont(new Font("Tahoma", Font.BOLD, 25));
        text_slider.setForeground(text_color);
        text_slider.setHorizontalAlignment(SwingConstants.CENTER);
        text_slider.setVerticalAlignment(SwingConstants.CENTER);


        //Game button
        JButton button_game = new JButton("game");
        button_game.setFont(new Font("Tahoma", Font.BOLD, 35));
        button_game.setBackground(button_color);
        button_game.setForeground(Color.WHITE);
        button_game.setPreferredSize(new Dimension(250, 80));

        button_game.setActionCommand("game button pressed");
        button_game.setFocusable(false);

        MenuButtonClickedListener mbcl = new MenuButtonClickedListener();

        button_game.addActionListener(mbcl);


        //Leaderboard button
        JButton button_leaderboard = new JButton("leaderboard");
        button_leaderboard.setFont(new Font("Tahoma", Font.BOLD, 35));
        button_leaderboard.setBackground(button_color);
        button_leaderboard.setForeground(Color.WHITE);
        button_leaderboard.setPreferredSize(new Dimension(250, 80));

        button_leaderboard.setActionCommand("leaderboard button pressed");
        button_leaderboard.setFocusable(false);

        button_leaderboard.addActionListener(mbcl);


        //Slider
        this.slider = new JSlider(4, 7);
        slider.setBackground(background_color);
        slider.setFocusable(false);

        Hashtable<Integer, JLabel> labeltable = new Hashtable<>();

        Font sliderfont = new Font("Tahoma", Font.BOLD, 25);

        JLabel label4 = new JLabel("4");
        label4.setFont(sliderfont);
        label4.setForeground(text_color);

        JLabel label5 = new JLabel("5");
        label5.setFont(sliderfont);
        label5.setForeground(text_color);

        JLabel label6 = new JLabel("6");
        label6.setFont(sliderfont);
        label6.setForeground(text_color);

        JLabel label7 = new JLabel("7");
        label7.setFont(sliderfont);
        label7.setForeground(text_color);

        labeltable.put(4, label4);
        labeltable.put(5, label5);
        labeltable.put(6, label6);
        labeltable.put(7, label7);

        slider.setLabelTable(labeltable);

        slider.setValue(5);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setVisible(true);


        //Panels
        panel_2048.add(text_2048);
        panel_game.add(button_game);
        panel_leaderboard.add(button_leaderboard);
        panel_slider_text.add(text_slider);
        panel_slider.add(slider);
        panel_slider_layout.add(slider);
        panel_slider_layout.add(text_slider);

        this.add(panel_2048);
        this.add(panel_game);
        this.add(panel_leaderboard);
        this.add(panel_slider_layout);
    }

    /**
     * A listener for the buttons
     */
    private class MenuButtonClickedListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //if the game button was pressed, start a new game with the board size choosen on the slider
            if(e.getActionCommand().equals("game button pressed")){

                window.startGame(slider.getValue());
            }

            //if the leaderboard button was pressed, show the leaderboard
            else if(e.getActionCommand().equals("leaderboard button pressed")){

                window.showLeaderBoard();
            }
        }
    }

    /**
     * Responds to keys
     */
    private class MenuController implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch(key){
                //close the window of the program when escape pressed
                case KeyEvent.VK_ESCAPE:
                    window.closeWindow();
                    break;

                //start a new game with the board size choosen on the slider when enter pressed
                case KeyEvent.VK_ENTER:
                    window.startGame(slider.getValue());
                    break;

                //decrement the value of the slider when left arrow pressed
                case KeyEvent.VK_LEFT:
                    slider.setValue(slider.getValue() - 1);
                    break;

                //increment the value of the slider when right arrow pressed
                case KeyEvent.VK_RIGHT:
                    slider.setValue(slider.getValue() + 1);
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}

