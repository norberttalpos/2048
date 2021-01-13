package Windows;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Shows a screenshot in a dialog window (the Board screenshot)
 * extends JDialog to work as a JDialog (dialog window)
 */
public class ViewBoardWindow extends JDialog {

    /* Methods */

    /**
     * Constructor of ViewBoardWindow class
     * initializes components
     * @param image: the image we want to show in the dialog window
     */
    public ViewBoardWindow(BufferedImage image){

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle("Board");
        this.setResizable(false);

        this.addKeyListener(new ViewBoardWindowController());

        JLabel pictureLabel = new JLabel(new ImageIcon(image));
        this.add(pictureLabel);

        this.setLocation(500, 100);
        this.setVisible(true);

        this.setSize(new Dimension(image.getWidth() + 15, image.getHeight() + 35));

        this.setFocusable(true);
    }

    /**
     * Responds to keys
     */
    private class ViewBoardWindowController implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            //closes the dialog window if enter or escape is pressed
            if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_ESCAPE)
                dispose();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}