package Logic;


import java.io.File;

/**
 * Contains the current board and the leaderboard
 */
public class Game {

    /* Attributes */

    //the current board (can be nullpointer)
    private Board currentBoard;

    //the leaderboard
    private final LeaderBoard leaderBoard;

    /* Methods */

    /**
     * Constructor of Game class
     * loads in the leaderboard
     */
    public Game(){
        this.leaderBoard = new LeaderBoard();
    }

    /**
     * Returns the current board
     * @return the current board (Board)
     */
    public Board getCurrentBoard(){
        return this.currentBoard;
    }

    /**
     * Makes a new board with the size of the parameter
     * @param boardSize: the size of the board (int)
     */
    public void newBoard(int boardSize){

        currentBoard = new Board(boardSize);
    }

    /**
     * Saves the board with the params we want to store
     * (player name, screenshot of the board, other params of the board)
     * @param name: The name of the player (String)
     * @param boardImagePath: the path to the screenshot of the board (File)
     */
    public void saveBoard(String name, File boardImagePath){

        if(currentBoard != null) {
            //put the current board into the leaderboard with current time
            leaderBoard.addBoard(currentBoard, name, boardImagePath);
        }
        this.saveLeaderBoard();
    }

    /**
     * Returns the leaderboard
     * @return the leaderboard (LeaderBoard)
     */
    public LeaderBoard getLeaderBoard(){
        return this.leaderBoard;
    }

    /**
     * Saves the leaderboard
     */
    public void saveLeaderBoard(){
        try{
            leaderBoard.save();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Resets the leaderboard
     */
    public void resetLeaderBoard(){
        leaderBoard.reset();
    }

}
