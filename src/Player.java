import java.util.*;
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

    public void setHand(wordbag bag){
        Random random = new Random();
        for(int i = 0; i < 7; i++){
            hand.add(bag.get());
        }
    }

    public void addTile(wordbag bag){
        hand.add(bag.get());
    }

    public void useTile(String letter){
        Tile tempTile = new Tile(letter);
        if(hand.contains(tempTile)){
            hand.remove(tempTile);
        }else{
            System.out.println("Tile " + letter + "not in hand");
        }
    }
}