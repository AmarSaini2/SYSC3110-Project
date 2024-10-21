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

    public String getName() {
        return name;
    }

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

    public void addPoints(int points){
        this.points += points;
    }

    public int getPoints(){
        return this.points;
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