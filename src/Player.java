import java.nio.charset.CharacterCodingException;
import java.util.*;
import java.util.logging.Handler;
import javax.swing.plaf.basic.BasicGraphicsUtils;
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

    public boolean handIsEmpty(Wordbag bag){
        return bag.isEmpty();
    }

    public void useTile(String letter){
        for(Tile tile: hand){
            if(tile.getID().equals(letter)){
                hand.remove(tile);
                break; // remove only the first instance of that tile from hand
            }
        }
    }

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

    public boolean hasTile(String tileID){
        for(Tile tile: hand){
            if(tile.getID().equalsIgnoreCase(tileID)){
                return true;
            }
        }
        return false;
    }

    public boolean handIsEmpty(){
        return hand.isEmpty();
    }

    public ArrayList<Tile> getHand(){
        return hand;
    }

    public void swapWithTemp(ArrayList<Tile> tempHand, Wordbag bag){
        this.hand = tempHand;
        while(hand.size() < 7){
            hand.add(bag.get());
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
}