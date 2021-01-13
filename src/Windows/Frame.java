package Windows;

import Logic.Game;

import javax.swing.*;
import java.awt.*;

/**
 * The class that makes the window of the game
 * extends JFrame to work as a JFrame (window)
 */
public class Frame extends JFrame {

    /* Attributes */

    /*
     * the layout of the cardPanel
     * cardlayout so we can easily switch between the panels to show
     */
    CardLayout cardLayout;

    //the panel directly attached to the frame
    JPanel cardPanel;

    //represents the menu panel
    MenuWindow menuWindow;

    //represents a game panel
    BoardWindow boardWindow;

    //represents the leaderboard panel
    LeaderBoardWindow leaderBoardWindow;

    //the game model (data)
    Game game;

    /* Methods */

    /**
     * Contructor or Frame class
     * initializes the JFrame
     */
    public Frame(){

        //JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("2048");
        this.setSize(450, 650);
        this.setResizable(false);
        this.setLocation(600, 100);

        //Panels and Layout
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);

        this.add(cardPanel);

        this.menuWindow = new MenuWindow(this);

        cardPanel.add(menuWindow, "menuWindow");
        cardLayout.show(cardPanel, "menuWindow");
    }

    /**
     * Sets the game created in the Main class to be able to pass
     * to other classes and do operations on it
     * @param g: the game we want to set (Game)
     */
    public void setGame(Game g){
        this.game = g;
    }

    /**
     * Sets the frame visible
     */
    public void showWindow(){
        this.setVisible(true);
    }

    /**
     * Starts a new game
     * The frame will show the BoardWindow panel instead of the MenuWindow
     * The frame will change size and location according to the boardSize
     * @param boardSize: the size of the board we want to start a game with (int)
     */
    public void startGame(int boardSize){

        game.newBoard(boardSize);

        boardWindow = new BoardWindow(game, this);
        cardPanel.add(boardWindow, "boardWindow");

        switch (boardSize){
            case 4:
                this.setSize(390, 515);
                this.setLocation(610, 150);
                break;

            case 5:
                this.setSize(472, 597);
                this.setLocation(580, 120);
                break;

            case 6:
                this.setSize(551, 676);
                this.setLocation(530, 80);
                break;

            case 7:
                this.setSize(630, 755);
                this.setLocation(480, 60);
                break;

        }
        cardLayout.show(cardPanel, "boardWindow");
        boardWindow.setFocusable(true);
        boardWindow.requestFocusInWindow();
    }

    /**
     * Shows the leaderboard
     * The frame will show the LeaderBoardWindow panel instead of the MenuWindow
     */
    public void showLeaderBoard(){
        this.leaderBoardWindow = new LeaderBoardWindow(game.getLeaderBoard(), this);

        cardPanel.add(leaderBoardWindow, "leaderBoardWindow");
        cardLayout.show(cardPanel, "leaderBoardWindow");

        leaderBoardWindow.setFocusable(true);
        leaderBoardWindow.requestFocusInWindow();

        this.setResizable(true);
        this.setSize(700, 600);
        this.setLocation(450, 100);
    }

    /**
     * Shows the menu again after returning from game or leaderboard
     * @param currentPanel: the Panel we call this method from (JPanel)
     */
    public void backToMenu(JPanel currentPanel){
        this.setSize(450, 650);
        this.setLocation(600, 100);

        cardPanel.remove(currentPanel);

        cardLayout.show(cardPanel, "menuWindow");
        menuWindow.setFocusable(true);
        menuWindow.requestFocusInWindow();
    }

    /**
     * Closes the window
     */
    public void closeWindow(){
        this.setVisible(false);
        this.dispose();
    }

}
