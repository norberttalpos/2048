package Logic;

import javax.imageio.ImageIO;
import javax.swing.table.AbstractTableModel;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The class that represents the leaderboard which can be saved, loaded, visualized
 * extends AbstractTableModel to be able to pass as a model for a JTable
 */
public class LeaderBoard extends AbstractTableModel {

    /* Attributes */

    /*
     * stores the leaderboard's rows as a list
     * the list implementation is LinkedList because we often add
     * and remove elements
     */
    private List<LeaderBoardRow> leaderBoard;

    //the file path where the leaderboard was loaded from and will be saved into
    private final File file;


    /* Methods */

    /**
     * Contructor of the LeaderBoard class
     * loads in the leaderboard
     */
    public LeaderBoard(){

        this.leaderBoard = new LinkedList<>();

        this.file = new File(System.getProperty("user.dir"), "leaderboard.txt");

        try {
            this.load();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new row of data to the leaderboard
     * @param board: board of the game we want to save (Board)
     * @param name: the name of the player (String)
     * @param boardImagePath: the path of the screenshot of the board (File)
     */
    public void addBoard(Board board, String name, File boardImagePath){
        leaderBoard.add(new LeaderBoardRow(LocalDateTime.now(), board, name, boardImagePath));
    }

    /**
     * Loads the leaderboard from .txt file
     * @throws IOException -- throws IOException
     * @throws ClassNotFoundException -- throws ClassNotFoundException
     */
    private void load() throws IOException, ClassNotFoundException {

        if(!file.exists())
            throw new FileNotFoundException();

        FileInputStream f = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(f);

        leaderBoard = (List<LeaderBoardRow>) in.readObject();

        in.close();

    }

    /**
     * Saves the current state of the leaderboard to the file given
     * @throws IOException -- throws IOException
     */
    public void save() throws IOException{

        if(!file.exists())
            throw new FileNotFoundException();

        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(f);

        out.writeObject(leaderBoard);

        out.close();
    }

    /**
     * Resets the leaderboard through making it an empty list
     */
    public void reset(){
        leaderBoard = new ArrayList<>();
    }

    /**
     * Deletes a row from the leaderboard (also deletes the screenshot image of the board)
     * @param index: the row we want to delete (int)
     */
    public void deleteRow(int index){
        File file = leaderBoard.get(index).getBoardImagePath();
        file.delete();
        leaderBoard.remove(index);
    }

    /**
     * Deletes the images if the image was saved but the game was not
     */
    public void deleteUnusedImages() {
        File images_folder = new File(System.getProperty("user.dir") + File.separator + "screenshots");
        List<File> images = Arrays.asList(images_folder.listFiles());

        for (File file : images) {
            boolean found = false;

            for (LeaderBoardRow row : leaderBoard) {
                if (row.getBoardImagePath().equals(file)) {
                    found = true;
                }
            }
            if (!found)
                file.delete();
        }
    }

    /**
     * Returns the next valid board image name to be used
     * @return the next board image name
     */
    public String getNextImageName(){
        File images_folder = new File(System.getProperty("user.dir") + File.separator + "screenshots");
        File[] images = images_folder.listFiles();

        List<Integer> indexes = new ArrayList<>();

        for(File file: images){
            //get file name and remove 'board' and '.jpeg' from it, so only a number remains
            String s = file.getName();
            s = s.substring(5, s.indexOf("."));
            Integer i = Integer.parseInt(s);

            indexes.add(i);
        }

        if(indexes.size() > 0) {
            Collections.sort(indexes);
            int biggest = indexes.get(indexes.size() - 1);

            return "board" + (biggest + 1);
        }
        else
            return "board" + 0;
    }

    /**
     * Returns the screenshot of the board
     * @param row: the row of the leaderboard we want to view
     * @return the screenshot of the board (BufferedImage)
     */
    public BufferedImage getBoardImage(int row){
        File boardImagePath = leaderBoard.get(row).getBoardImagePath();

        try {
            return ImageIO.read(boardImagePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the number of rows in the leaderboard
     * @return the number of rows in the leaderboard (int)
     */
    @Override
    public int getRowCount() {
        return leaderBoard.size();
    }

    /**
     * Returns the number of columns in the leaderboard
     * @return the number of columns in the leaderboard (int)
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Returns the value from the row 'rowIndex', column 'columnIndex'
     * @param rowIndex: the row we want to load the value from (int)
     * @param columnIndex: the column we want to load the value from (int)
     * @return the value from the row 'rowIndex', column 'columnIndex' (Object)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LeaderBoardRow row = leaderBoard.get(rowIndex);
        switch(columnIndex) {
            case 0: return row.getDateString();
            case 1: return row.getName();
            case 2: return row.getScore();
            default: return row.getBoardSize();
        }
    }

    /**
     * Returns the name of column 'columnIndex'
     * @param columnIndex: the column, which name we want to get (int)
     * @return the name of column 'columnIndex' (String)
     */
    @Override
    public String getColumnName(int columnIndex){
        switch(columnIndex) {
            case 0:
                return "date";
            case 1:
                return "name";
            case 2:
                return "score";
            default:
                return "size";
        }
    }

    /**
     * Returns the class of column 'columnIndex'
     * @param columnIndex: the column, which class we want to get (int)
     * @return the class of column 'columnIndex' (Class)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return String.class;
            case 1: return String.class;
            case 2: return Integer.class;
            default: return Integer.class;
        }
    }
}
