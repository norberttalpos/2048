package Logic;

/**
 * The class that represents a position in the row-column coordinate system
 */
public class Position {

    /* Attributes */

    //the row value of the position
    private final int row;

    //the column value of the position
    private final int column;

    /* Methods */

    /**
     * Constructor of Position class
     * Gives the class the values we give in the parameterlist
     * @param r: the row (int)
     * @param c: the column (int)
     */
    public Position(int r, int c){
        this.row = r;
        this.column = c;
    }

    /**
     * Return the row attribute of the class
     * @return the row attribute of the class (int)
     */
    public int getRow() {
        return row;
    }

    /**
     * Return the column attribute of the class
     * @return the column attribute of the class (int)
     */
    public int getColumn() {
        return column;
    }
}