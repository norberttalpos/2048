import Logic.Board;
import Logic.Position;
import Logic.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that tests several methods of the program
 */
public class Tests {

    /* Attributes */

    //a tile
    Tile tile;

    //a board
    Board board;

    /* Tests */

    /**
     * Sets up the attributes that are often used
     */
    @Before
    public void setUp(){
        tile = new Tile();
        board = new Board(7);
    }

    /**
     * Tests the getColor() method of the Tile class
     */
    @Test
    public void test_Tile_getColor(){
        Color test_color = new Color(204, 192, 179);

        Assert.assertEquals(tile.getColor(), test_color);
    }

    /**
     * Tests the getValue() method of the Tile class
     */
    @Test
    public void test_Tile_getValue(){
        int value = 0;

        Assert.assertEquals(tile.getValue(), value);
    }

    /**
     * Tests the copy constructor of the Tile class
     */
    @Test
    public void test_Tile_Copy_Constructor(){
        Tile original = new Tile();
        Tile copy = new Tile(original);

        Assert.assertEquals(original.getColor(), copy.getColor());
        Assert.assertEquals(original.getValue(), copy.getValue());
    }

    /**
     * Tests the doMagic() methods of the Board class
     */
    @Test
    public void test_Board_doMagic(){
        List<Tile> testList = new ArrayList<>();

        Tile t1 = new Tile();
        Tile t2 = new Tile();
        Tile t3 = new Tile();
        Tile t4 = new Tile();
        Tile t5 = new Tile();
        Tile t6 = new Tile();
        Tile t7 = new Tile();

        List<Tile> originalList = new ArrayList<>();

        t1.setValue(4);
        t2.setValue(0);
        t3.setValue(8);
        t4.setValue(0);
        t5.setValue(8);
        t6.setValue(4);
        t7.setValue(4);

        Board board = new Board(7);

        testList.add(t1);
        testList.add(t2);
        testList.add(t3);
        testList.add(t4);
        testList.add(t5);
        testList.add(t6);
        testList.add(t7);

        Tile o1 = new Tile();
        Tile o2 = new Tile();
        Tile o3 = new Tile();
        Tile o4 = new Tile();
        Tile o5 = new Tile();
        Tile o6 = new Tile();
        Tile o7 = new Tile();

        o1.setValue(4);
        o2.setValue(16);
        o3.setValue(4);
        o4.setValue(4);
        o5.setValue(0);
        o6.setValue(0);
        o7.setValue(0);

        originalList.add(o1);
        originalList.add(o2);
        originalList.add(o3);
        originalList.add(o4);
        originalList.add(o5);
        originalList.add(o6);
        originalList.add(o7);

        board.doMagic(testList, true);

        for(int i = 0; i < board.getBoardSize(); ++i){
            Assert.assertEquals(testList.get(i).getValue(), originalList.get(i).getValue());
        }

    }

    /**
     * Tests the getCannotMove() method of the Board class
     */
    @Test
    public void test_Board_getCannotMove(){

        Assert.assertFalse(board.getCannotMove());
    }

    /**
     * Tests the getScore() method of the Board class
     */
    @Test
    public void test_Board_getScore(){

        Assert.assertEquals(board.getScore(), 0);
    }

    /**
     * Tests the getColumn() method of the Position class
     */
    @Test
    public void test_Position_getColumn(){
        Position pos = new Position(5, 30);

        Assert.assertEquals(pos.getColumn(), 30);
    }

    /**
     * Tests the getGameEnded() method of the Board class
     */
    @Test
    public void test_Board_getGameEnded(){
        Assert.assertFalse(board.getGameEnded());
    }

    /**
     * Tests the getBoardSize() method of the Board class
     */
    @Test
    public void test_Board_getBoardSize(){
        Assert.assertEquals(board.getBoardSize(), 7);
    }

    /**
     * Tests the merge() method of the Tile class
     */
    @Test
    public void test_Tile_merge(){
        Tile t1 = new Tile();
        Tile t2 = new Tile();

        t1.setValue(2);
        t2.setValue(2);

        t1.merge();

        Assert.assertEquals(t1.getValue(), 4);
        Assert.assertEquals(t2.getValue(), 2);
    }

    @Test
    public void test_Board_getCannotMove_changes_score(){
        int score_before = board.getScore();

        board.getCannotMove();

        Assert.assertEquals(score_before, board.getScore());
    }
}
