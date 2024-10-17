import java.nio.charset.CharacterCodingException;
import java.util.*;
import java.util.logging.Handler;
public class Player{
    private int points;
    private ArrayList<Tile> hand;
    private String name;

    public Player(String name){
        points = 0;
        this.name = name;
        hand = new ArrayList<Tile>();
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void removePoints(int points) {this.points -= points;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHand(Wordbag bag){
        for(int i = 0; i < 7; i++){
            hand.add(bag.get());
        }
    }

    public void addTile(Wordbag bag){
        hand.add(bag.get());
    }

    public void useTile(String letter){
        Tile tempTile = new Tile(letter);
        if(hand.contains(tempTile)){
            hand.remove(tempTile);
        }
    }

    public void printHand(){
        String returnString = new String();
        for(Tile tile: hand){
            returnString += (tile.getID() + " ");
        }
        returnString += "\n";
        for(Tile tile: hand){
            returnString += (tile.getPoints() + " ");
        }
        System.out.println(returnString);
    }

    public boolean hasTile(String tileID){
        for(Tile tile: hand){
            if(tile.getID().equalsIgnoreCase(tileID)){
                return true;
            }
        }
        return false;
    }
}