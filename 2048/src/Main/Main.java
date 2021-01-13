package Main;

import Logic.Game;
import Windows.Frame;

/**
 * The Main class of the program
 * Starts the program
 */
public class Main {

    /**
     * Starts the game, shows the game window
     * When the game ended, saves the leaderboard into file
     * @param args: command line args
     */
    public static void main(String[] args){
        Frame window = new Frame();

        Game game = new Game();

        window.setGame(game);

        window.showWindow();

        game.saveLeaderBoard();

    }
}
