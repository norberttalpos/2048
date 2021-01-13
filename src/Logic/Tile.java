package Logic;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The class that represents a Tile
 */
public class Tile {

    /* Attributes */

    //the value of the tile
    private int value;

    //the color of the tile
    private Color color;

    /*
     * stores the pairs of values - colors with the value as key
     * the map implementation is HashMap because of fast searching
     */
    private static Map<Integer, Color> colorMap;

    /* Methods */

    /**
     * Default constructor of Tile class
     * initializes the colors of the values we want to give to a certain value
     * initializes the value of the tile to 0
     * assigns a color to its value
     */
    public Tile(){
        this.initColorMap();

        this.value = 0;

        this.color = colorMap.get(value);
    }

    /**
     * Copy contructor of Tile class
     * the values of the new tile will be the parameter tile's
     * @param ref: the Tile we want to copy (Tile)
     */
    public Tile(Tile ref){
        this.initColorMap();

        this.value = ref.getValue();
        this.color = colorMap.get(value);
    }

    /**
     * initializes the Map of values-colors
     */
    private void initColorMap(){

        colorMap = new HashMap<>();

        colorMap.put(0, new Color(204, 192, 179));
        colorMap.put(2, new Color(238, 228, 217));
        colorMap.put(4, new Color(237, 224, 199));
        colorMap.put(8, new Color(243, 178, 116));
        colorMap.put(16, new Color(243, 178, 116));
        colorMap.put(32, new Color(246, 124, 95));
        colorMap.put(64, new Color(246, 94, 59));
        colorMap.put(128, new Color(237, 207, 114));
        colorMap.put(1024, new Color(170, 96, 166, 255));
    }

    /**
     * Returns the value of the tile
     * @return the value of the tile (int)
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Sets the tiles value
     * @param val: the value we want to give to the tile's value attribute (int)
     */
    public void setValue(int val){
        this.value = val;

        if(val <= 128){
            this.color = colorMap.get(val);
        }
        else if(val <= 512){
            this.color = colorMap.get(128);
        }
        else
            this.color = colorMap.get(1024);
    }

    /**
     * Return the color of the tile
     * @return the color of the tile (Color)
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Does the merge operation on the tile
     * Doubles the current value of the tile
     */
    public void merge(){
        this.setValue(this.value*2);
    }
}
