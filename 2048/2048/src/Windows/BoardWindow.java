package Windows;

import Logic.Board;
import Logic.Game;
import Logic.Position;
import Logic.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The representation of the game window
 * extends JPanel to be able to add to a JFrame
 */
public class BoardWindow extends JPanel {

    /* Attributes */

    //size attributes
    private final int tile_size = 65;
    private final int tile_margin = 15;
    private final int tile_radius = 15;
    private final int window_width;
    private final int window_height;
    private final int window_margin = 20;
    private final int board_size;
    private final int scoreboard_height = 60;

    //true if a key is pressed in, false if not
    private boolean keyPressed;

    //true if there a name input dialog window is opened, false if not
    private boolean freeze;

    //color scheme
    private Color background_color = new Color(243, 237, 216);
    private Color dark_brown = new Color(119, 110, 100);
    private final Color board_color = new Color(187, 173, 160);
    private final Color number_color = new Color(79, 72, 66);
    private final Color menu_color = new Color(187, 173, 160);

    //the game model of the application
    private final Game game;

    //the window of the application
    private final Frame window;

    //the board of the actual game
    private Board board;

    //the menu shown in the game
    private final JMenuBar menuBar;

    /* Methods */

    /**
     * Contructor of BoardWindow
     * initializes components
     * @param g: the game model (data) of the program (Game)
     * @param frame: the window of the program (Frame)
     */
    public BoardWindow(Game g, Frame frame){

        this.window = frame;
        this.game = g;
        this.board = game.getCurrentBoard();

        //Menu
        this.menuBar = new JMenuBar();

        JMenuItem menuExit = new JMenuItem("exit");
        JMenuItem menuNew = new JMenuItem("new");
        JMenuItem menuSave = new JMenuItem("save");

        Font menuFont = new Font("Tahoma", Font.BOLD, 15);
        menuExit.setFont(menuFont);
        menuNew.setFont(menuFont);
        menuSave.setFont(menuFont);

        menuExit.setBackground(menu_color);
        menuExit.setForeground(Color.WHITE);
        menuNew.setBackground(menu_color);
        menuNew.setForeground(Color.WHITE);
        menuSave.setBackground(menu_color);
        menuSave.setForeground(Color.WHITE);

        MenuBarListener menuBarListener = new MenuBarListener();

        menuExit.setActionCommand("menuExit pressed");
        menuNew.setActionCommand("menuNew pressed");
        menuSave.setActionCommand("menuSave pressed");

        menuExit.addActionListener(menuBarListener);
        menuNew.addActionListener(menuBarListener);
        menuSave.addActionListener(menuBarListener);

        menuBar.add(Box.createHorizontalGlue());

        menuBar.add(menuExit);
        menuBar.add(menuNew);
        menuBar.add(menuSave);

        menuBar.setBackground(menu_color);
        menuBar.setForeground(Color.WHITE);

        window.setJMenuBar(menuBar);

        //Listener and booleans

        this.addKeyListener(new BoardWindowController());

        this.keyPressed = false;
        this.freeze = false;

        //Size

        int boardSize = board.getBoardSize();
        this.board_size = boardSize * (tile_size + tile_margin) + tile_margin;
        this.window_width = board_size + 2 * window_margin;
        this.window_height = window_margin * 3 + board_size + scoreboard_height;

        this.setPreferredSize(new Dimension(window_width, window_height));
        window.setResizable(false);
    }

    /**
     * Visualizes the game
     * @param g: the class that is responsible for drawing (Graphics)
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawBackground(graphics);
        drawBoard(graphics);
        drawScoreBoard(graphics);

        graphics.dispose();
    }

    /**
     * Draws the background
     * @param g: the class that is responsible for drawing (Graphics)
     */
    private void drawBackground(Graphics g){
        g.setColor(background_color);
        g.fillRect(0, 0, window_width, window_height);
    }

    /**
     * Draws the scoreboard
     * @param g: the class that is responsible for drawing (Graphics)
     */
    private void drawScoreBoard(Graphics g){
        g.translate(-window_margin, -(window_height - window_margin - board_size));

        int scoreboard_width = 175;
        int posX = window_width - window_margin - scoreboard_width;
        int posY = window_margin;

        g.setColor(dark_brown);
        g.fillRoundRect(posX, posY, scoreboard_width, scoreboard_height, tile_radius, tile_radius);

        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.setColor(Color.WHITE);
        g.drawString("SCORE", posX + 62, posY + 20);


        Font font = new Font("Tahoma", Font.BOLD, 25);
        g.setFont(font);

        String s = String.valueOf(board.getScore());
        final FontMetrics fm = g.getFontMetrics(font);

        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        g.setColor(Color.WHITE);
        g.drawString(s, posX + (scoreboard_width - w) / 2, posY + scoreboard_height - (scoreboard_height - h) / 2 + 5);
    }

    /**
     * Draws a tile
     * @param g: the class that is responsible for drawing (Graphics)
     */
    private void drawTile(Graphics g, Position pos){
        Tile tile = board.getTileAt(pos);

        int posX = tile_margin + pos.getColumn() * (tile_margin + tile_size);
        int posY = tile_margin + pos.getRow() * (tile_margin + tile_size);

        g.setColor(tile.getColor());
        g.fillRoundRect(posX, posY, tile_size, tile_size, tile_radius, tile_radius);


        if (tile.getValue() != 0) {

            final int size;

            if(tile.getValue() < 100){
                size = 35;
            }
            else if(tile.getValue() < 1000){
                size = 32;
            }
            else if(tile.getValue() < 10000){
                size = 25;
            }
            else{
                size = 20;
            }

            Font font = new Font("Tahoma", Font.BOLD, size);
            g.setFont(font);

            String s = String.valueOf(tile.getValue());
            final FontMetrics fm = g.getFontMetrics(font);

            final int w = fm.stringWidth(s);
            final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

            if(tile.getValue() >= 128)
                g.setColor(Color.WHITE);
            else
                g.setColor(number_color);

            g.drawString(s, posX + (tile_size - w) / 2 , posY + tile_size - (tile_size - h) / 2 - 3);
        }
    }

    /**
     * Draws the board
     * @param g: the class that is responsible for drawing (Graphics)
     */
    private void drawBoard(Graphics g){

        g.translate(window_margin, window_height - window_margin - board_size);
        g.setColor(board_color);

        g.fillRoundRect(0, 0, board_size, board_size, tile_radius, tile_radius);


        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                drawTile(g, new Position(row, col));
            }
        }
    }

    /**
     * When the game has ended, this changes the color scheme of the game
     */
    private void enlightenColors() {
        background_color = new Color(243, 237, 216, 50);
        dark_brown = new Color(119, 110, 100, 50);
    }

    /**
     * Sets the color scheme back to default
     */
    private void originalColors() {
        background_color = new Color(243, 237, 216);
        dark_brown = new Color(119, 110, 100);
    }

    /**
     * Go back to the menu
     */
    private void exitGame(){

        window.remove(menuBar);
        menuBar.setVisible(false);

        window.backToMenu(this);
    }

    /**
     * Saving the actual state of the board as an image to be able to load and show in leaderboardwindow
     * @return the path to the screenshot just made (File)
     */
    private File getScreenShotPath(){
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(this.getBounds());
        } catch (AWTException ae) {
            ae.printStackTrace();
        }

        Graphics2D g = image.createGraphics();

        paint(g);

        File file = new File(System.getProperty("user.dir") + File.separator + "screenshots" +
                             File.separator + game.getLeaderBoard().getNextImageName() + ".jpeg");

        try {
            ImageIO.write(image,"jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * Makes a new dialog window to be able to type in the player name
     */
    private void save(){

        if(!freeze) {
            freeze = true;

            NameInputWindow nameInputWindow = new NameInputWindow(game, this.getScreenShotPath());

            //to be able to do operations when closing the dialog window
            nameInputWindow.addWindowListener(new NameInputWindowListener());
        }
    }

    /**
     * Starts a new game
     */
    private void newGame(){
        int boardSize = game.getCurrentBoard().getBoardSize();
        game.newBoard(boardSize);

        board = game.getCurrentBoard();

        originalColors();
        repaint();
    }

    /**
     * Responds to keys
     */
    private class BoardWindowController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            //if not saving a game state and there isn't any other pressed key
            if(!freeze && !keyPressed) {

                int key = e.getKeyCode();

                //if the game hasn't ended yet
                if (!board.getGameEnded()) {

                    switch (key) {
                        case KeyEvent.VK_UP:
                            board.moveBoardUp();
                            break;

                        case KeyEvent.VK_DOWN:
                            board.moveBoardDown();
                            break;

                        case KeyEvent.VK_LEFT:
                            board.moveBoardLeft();
                            break;

                        case KeyEvent.VK_RIGHT:
                            board.moveBoardRight();
                            break;

                    }
                    if (board.getGameEnded()) {
                        enlightenColors();
                        save();
                    }
                }

                //in every case
                if (key == KeyEvent.VK_ESCAPE) {
                    exitGame();
                }
                else if(key == KeyEvent.VK_N){
                    newGame();
                }
                else if(key == KeyEvent.VK_S){
                    save();
                }


                keyPressed = true;

                repaint();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyPressed = false;
        }
    }

    /**
     * A listener for the menu bar
     */
    private class MenuBarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //if exit selected on the menu bar, exit the game
            if(e.getActionCommand().equals("menuExit pressed")){
                if(!freeze)
                    exitGame();
            }

            //if new selected on the menu bar, start a new game
            else if(e.getActionCommand().equals("menuNew pressed")){
                if(!freeze)
                    newGame();
            }

            //if save selected on the menu bar, pop up the name input window to
            //be able to type in player name
            else if(e.getActionCommand().equals("menuSave pressed")){
                if(!freeze)
                    save();
            }
        }
    }

    /**
     * A listener for the name input window that gets if the dialog window is closed
     */
    private class NameInputWindowListener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {

        }

        @Override
        public void windowClosed(WindowEvent e) {

            game.getLeaderBoard().deleteUnusedImages();

            newGame();
            freeze = false;
            requestFocusInWindow();
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
}
