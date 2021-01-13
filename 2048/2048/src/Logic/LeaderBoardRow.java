package Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Stores data we want to represent from a saved game
 * implements the Serializable interface to be able to serialize
 */
public class LeaderBoardRow implements Serializable {

    /* Attributes */

    //the current time when the object was made
    private final LocalDateTime localDateTime;

    //the player name
    private final String name;

    //the score of the game
    private final int score;

    //the size of the board
    private final int boardSize;

    //the path to the screenshot of the board
    private final File boardImagePath;


    /* Methods */

    /**
     * Contructor of LeaderBoardRow class
     * @param l: the time the game was saved (LocalDateTime)
     * @param b: the board of the game which was saved (Board)
     * @param n: the name of the player (String)
     * @param f: the file path of the screenshot of the board (File)
     */
    public LeaderBoardRow(LocalDateTime l, Board b, String n, File f){
        this.localDateTime = l;
        this.name = n;
        this.score = b.getScore();
        this.boardSize = b.getBoardSize();
        this.boardImagePath = f;
    }

    /**
     * Returns the date in a printable form
     * @return the date (String)
     */
    public String getDateString(){
        return localDateTime.getYear() + "-" + String.format("%02d", localDateTime.getMonthValue())
                 + "-" + String.format("%02d", localDateTime.getDayOfMonth()) + " " + String.format("%02d", localDateTime.getHour())
                 + ":" + String.format("%02d", localDateTime.getMinute()) + ":" + String.format("%02d", localDateTime.getSecond());
    }

    /**
     * Returns the name of the player
     * @return the name of the player (String)
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the score of the game
     * @return the score (int)
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Returns the size of the board of the game
     * @return the size of the board (int)
     */
    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * Returns the path to the screenshot of the board
     * @return the path of the screenshot (File)
     */
    public File getBoardImagePath(){
        return this.boardImagePath;
    }

}
