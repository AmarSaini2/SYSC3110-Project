import java.util.*;
public class Player{
    private int points;
    private ArrayList<tile> hand;
    private String name;

    public Player(String name){
        points = 0;
        this.name = name;
        hand = new ArrayList<tile>();
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

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

    public tile useTile(String letter){
        tile tempTile = new tile(letter);
        if(hand.contains(tempTile){
            temptile = hand.remove(tempTile);
        }

    }
}