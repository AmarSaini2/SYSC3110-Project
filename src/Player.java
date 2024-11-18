import java.nio.charset.CharacterCodingException;
import java.util.*;
import java.util.logging.Handler;
import javax.swing.plaf.basic.BasicGraphicsUtils;
/**
 * The Player class represents a single player in a Scrabble game.
 * Each player has a unique name, a rack of tiles they can use to form words, and a score to keep track of points.
 *
 * The Player class provides methods to manage a player's tiles, update their score, and retrieve player information,
 * such as their name and current score.
 *
 */
public class Player{
    private int points;
    private ArrayList<Tile> hand;
    private String name;
    private int NUM_TILES = 7;

    /**
     * Constructs a new Player  without a specified name.
     * Initializes an empty list of tiles for the player's rack and sets initial score to zero
     */
    public Player(){
        points = 0;
        this.name = name;
        hand = new ArrayList<Tile>();
    }
    /**
     * Constructs a new Player  with the specified name.
     * Initializes an empty list of tiles for the player's rack and sets initial score to zero.
     *
     * @param name the name of the player
     */
    public Player(String name){
        points = 0;
        this.name = name;
        hand = new ArrayList<Tile>();
    }
    /**
     * Return the name of the player.
     *
     * @return name of player
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the player's hand with the specific tile bag.
     *
     * @param bag the current game tile bag
     */
    public void setHand(Wordbag bag){
        for(int i = 0; i < 7; i++){
            hand.add(bag.get());
        }
    }

    public void setHand(ArrayList<Tile> hand){
        this.hand = hand;
    }

    public void addTile(Wordbag bag){
        hand.add(bag.get());
    }
    /**
     * Swaps the temporary hand of tiles not played with hand.
     * @param tempHand tiles not played
     * @param bag tile bag to draw from
     */
    public void printHand(){
        for(Tile tile : hand){
            System.out.printf("%6s", tile.getID());
        }
        System.out.print("\n");

        for(Tile tile: hand){
            System.out.printf("%6d", tile.getPoints());
        }
        System.out.print("\n");
    }

    
    public boolean handIsEmpty(){
        return hand.isEmpty();
    }

    public ArrayList<Tile> getHand(){
        return this.hand;
    }

    public void swapWithTemp(ArrayList<Tile> tempHand, Wordbag bag){
        this.hand = tempHand;
        while(hand.size() < 7){
            hand.add(bag.get());
        }
    }
    /**
     * Add the points of tiles played to player's score.
     *
     * @param points tile points to be added
     */
    public void addPoints(int points){
        this.points += points;
    }

    public int getPoints(){
        return this.points;
    }
    /**
     * Draw new tiles from the tile bag of the current game.
     * Handles if there isn't enough tiles to draw back upto 7 for.
     *
     * @param tilebag the tile bag to draw from
     */
    public void drawNewTiles(Wordbag tilebag){
        int replace = NUM_TILES - hand.size();
        if(tilebag.getBagSize()< replace && tilebag.getBagSize() > 0){
            //System.out.println("Only had +"+tilebag.getBagSize()+" , these are the remaining tiles.");
            for(int i =0; i< tilebag.getBagSize();i++){
                hand.add(tilebag.drawTile());
            }
            return;
        }else if(tilebag.getBagSize()==0){
            //System.out.println("All tiles have been drawn.");
            return;
        }
        for(int i =0; i<replace;i++){
            hand.add(tilebag.drawTile());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { //check if the two references are to the same spot in memory
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) { //rejects null/not Tile objects
            return false;
        }
        Player that = (Player) obj; //lets two instances of identical coordinate values to be considered equal
        return this.name.equals(that.name) && this.points == that.points;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Rack: ");
        for(Tile tile: hand){
            sb.append(" " + tile.getID());

        }
        return sb.toString();
    }
}
