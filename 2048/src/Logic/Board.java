package Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The logic of the game board, handles movements
 */
public class Board {

    /* Attributes */

    /*
     * stores the tiles as a 2D array
     * the list implementation I choose is ArrayList because a LinkedList wouldn't
     * be optimal for just storing, LinkedLists are made for adding and
     * removing elements
     */
    private final List<List<Tile>> board;

    //the size of the board
    private final int board_size;

    //the actual score of the game
    private int score;

    //true if ended, false if not
    private boolean gameEnded;

    //true if in initialization state, false if not
    private boolean init;


    /* Methods */

    /**
     * Constructor of Board class
     * initializes the board as a 2D array with 0 values except for two tiles, which have 2 value
     * @param size: the board will be size x size (int)
     */
    public Board(int size) {
        this.board_size = size;
        this.score = 0;
        this.gameEnded = false;
        this.init = true;

        //initialize 2d board of tiles
        this.board = new ArrayList<>(board_size);

        for (int i = 0; i < board_size; ++i) {
            board.add(new ArrayList<>(board_size));

            for (int j = 0; j < board_size; ++j) {
                board.get(i).add(new Tile());

            }
        }

        //spawn 2 tiles at the beginning
        this.spawnTile();
        this.spawnTile();

        this.init = false;

    }

    /**
     * Returns the size of the board
     * @return size of the board (int)
     */
    public int getBoardSize(){
        return this.board_size;
    }

    /**
     * Returns the score of the game at the moment
     * @return the score (int)
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Sets the value of a randomly choosen tile to 2
     */
    public void spawnTile(){
        List<Tile> emptyTiles = new ArrayList<>();

        for(int row = 0; row < board_size; ++row){
            for(int col = 0; col < board_size; ++col){
                Tile e = this.getTileAt(new Position(row, col));
                if(e.getValue() == 0)
                    emptyTiles.add(e);
            }
        }

        if(emptyTiles.size() != 0){
            Random rand = new Random();
            int random = rand.nextInt(emptyTiles.size());

            if(random%10 == 0 && !init){
                emptyTiles.get(random).setValue(4);
            }
            else
                emptyTiles.get(random).setValue(2);
        }
    }

    /**
     * Returns the Tile at the position given in the parameterlist
     * @param pos: the position of the tile (Position)
     * @return the tile at Position pos (Tile)
     */
    public Tile getTileAt(Position pos) {
        return this.board.get(pos.getRow()).get(pos.getColumn());
    }

    /**
     * Checks if the board is full of tiles
     * @return true if the board is full, false if not (boolean)
     */
    private boolean getBoardFull() {

        for (List<Tile> row : board) {
            for (Tile tile : row) {
                if (tile.getValue() == 0)
                    return false;
            }
        }
        return true;
    }


    /**
     * Makes a deep copy of the actual state of the board
     * @return another board with the same values
     */
    private List<List<Tile>> initTestBoard(){
        //initializing the test board

        List<List<Tile>> tiles_test = new ArrayList<>(board_size);

        for(int row = 0; row < board.size(); ++row){
            tiles_test.add(new ArrayList<>(board_size));

            for(int col = 0; col < board.size(); ++col){
                tiles_test.get(row).add(new Tile(getTileAt(new Position(row, col))));
            }
        }

        return tiles_test;
    }

    /**
     * Tests if the param and the current board have the same values on every tile
     * @param tiles_test: the 2D array which we want to compare the current board to
     * @return true if they are not equal, false if they are equal (boolean)
     */
    private boolean testNotSame(List<List<Tile>> tiles_test){
        //at least one tile changed
        for(int row = 0; row < board_size; ++row){
            for(int col = 0; col < board_size; ++col)
                if(tiles_test.get(row).get(col).getValue() != getTileAt(new Position(row, col)).getValue())
                    return true;
        }

        return false;
    }


    /**
     * Checks if the board is in the game ended state
     * @return true if the game has ended, false if not (boolean)
     */
    public boolean getGameEnded(){
        return this.gameEnded;
    }

    /**
     * Checks if the player can make any moves that changes the board through
     * testing every move the player can make
     * @return true if there weren't any changes in the test board after all the moves,
     * fasle if there were (boolean)
     */
    public boolean getCannotMove(){

        //initializing the test board

        List<List<Tile>> tiles_test = initTestBoard();

        //test up
        moveUp(tiles_test, true);

        if(testNotSame(tiles_test))
            return false;


        //test down
        moveDown(tiles_test, true);

        if(testNotSame(tiles_test))
            return false;


        //test right
        moveRight(tiles_test, true);

        if(testNotSame(tiles_test))
            return false;


        //test left
        moveLeft(tiles_test, true);

        if(testNotSame(tiles_test))
            return false;


        return true;
    }

    /**
     * Executes the operation of moving up
     * @param board: the board we want to do the operation with
     * @param test: true if called from a test, false if not
     */
    private void moveUp(List<List<Tile>> board, boolean test){
        for(int column = 0; column < board_size; ++column){

            //put tile values into array
            List<Tile> columnTiles = new ArrayList<>();
            for(int row = 0; row < board_size; ++row){
                columnTiles.add(board.get(row).get(column));
            }

            doMagic(columnTiles, test);
        }
    }

    /**
     * Executes the operation of moving down
     * @param board: the board we want to do the operation with
     * @param test: true if called from a test, false if not
     */
    private void moveDown(List<List<Tile>> board, boolean test){
        for(int column = 0; column < board_size; ++column){

            //put tile values into array
            List<Tile> columnTiles = new ArrayList<>();
            for(int row = board_size - 1; row >= 0; --row){
                columnTiles.add(board.get(row).get(column));
            }

            doMagic(columnTiles, test);
        }
    }

    /**
     * Executes the operation of moving right
     * @param board: the board we want to do the operation with
     * @param test: true if called from a test, false if not
     */
    private void moveRight(List<List<Tile>> board, boolean test){
        for(int row = 0; row < board_size; ++row){

            //put tile values into array
            List<Tile> rowTiles = new ArrayList<>();
            for(int column = board_size - 1; column >= 0; --column){
                rowTiles.add(board.get(row).get(column));
            }

            doMagic(rowTiles, test);
        }
    }

    /**
     * Executes the operation of moving left
     * @param board: the board we want to do the operation with
     * @param test: true if called from a test, false if not
     */
    private void moveLeft(List<List<Tile>> board, boolean test){
        for(int row = 0; row < board_size; ++row){

            //put tile values into array
            List<Tile> rowTiles = new ArrayList<>();
            for(int column = 0; column < board_size; ++column){
                rowTiles.add(board.get(row).get(column));
            }

            doMagic(rowTiles, test);
        }
    }

    /**
     * Moves the board up, checks if it can spawn a new tile, and if the move
     * ended the game
     */
    public void moveBoardUp(){

        List<List<Tile>> spawn_test = initTestBoard();

        moveUp(board, false);

        if(!getBoardFull() && testNotSame(spawn_test))
            spawnTile();


        if(getCannotMove())
            this.gameEnded = true;

    }

    /**
     * Moves the board down, checks if it can spawn a new tile, and if the move
     * ended the game
     */
    public void moveBoardDown(){

        List<List<Tile>> spawn_test = initTestBoard();

        moveDown(board, false);

        if(!getBoardFull() && testNotSame(spawn_test))
            spawnTile();


        if(getCannotMove())
            this.gameEnded = true;
    }

    /**
     * Moves the board right, checks if it can spawn a new tile, and if the move
     * ended the game
     */
    public void moveBoardRight(){

        List<List<Tile>> spawn_test = initTestBoard();

        moveRight(board, false);

        if(!getBoardFull() && testNotSame(spawn_test))
            spawnTile();


        if(getCannotMove())
            this.gameEnded = true;
    }

    /**
     * Moves the board left, checks if it can spawn a new tile, and if the move
     * ended the game
     */
    public void moveBoardLeft(){

        List<List<Tile>> spawn_test = initTestBoard();

        moveLeft(board, false);

        if(!getBoardFull() && testNotSame(spawn_test))
            spawnTile();


        if(getCannotMove())
            this.gameEnded = true;
    }

    /**
     * Calculates the values of the given tile list according to the 2048 game rules,
     * moves and merges the tiles where it's possible
     * @param list: the row/column of tiles we want to do the operation with
     * @param test: if the method is called in a test function, the scoreboard shouldn't change (boolean)
     */
    public void doMagic(List<Tile> list, boolean test){
        int index = 0;
        boolean merged = false;

        //if index == 0 or cannot merge with its neighbour
        while(index != board_size) {

            //if index == 0 or the neighbour is not 0 and not the same number, move index by 1
            if (index == 0 || (list.get(index - 1).getValue() != list.get(index).getValue() && list.get(index - 1).getValue() != 0)) {

                ++index;
            }

            //if the neighbour is 0, than move every tile to the neighbouring place
            else if (list.get(index - 1).getValue() == 0 && list.get(index).getValue() != 0) {

                for (int i = index - 1; i < board_size - 1; ++i) {
                    list.get(i).setValue(list.get(i + 1).getValue());
                }
                list.get(board_size - 1).setValue(0);

                --index;
            }

            //if neighbour is the same number, and not 0, merge
            else if (list.get(index - 1).getValue() == list.get(index).getValue() && list.get(index).getValue() != 0 && list.get(index - 1).getValue() != 0) {
                //only merge once in a row / column
                if(!merged) {
                    list.get(index - 1).merge();
                    list.get(index).setValue(0);

                    merged = true;

                    if(!test)
                        this.score += list.get(index - 1).getValue();

                    --index;
                }
                else
                    ++index;
            }

            //if the current value at index is 0, step to the next tile
            else if(list.get(index).getValue() == 0){

                ++index;
            }
        }

    }

}

