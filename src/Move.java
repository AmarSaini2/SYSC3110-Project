import java.io.Serializable;
import java.util.ArrayList;
import java.io.*;

public class Move implements Serializable {
    public Board board;
    public Player player;
    public Wordbag bag;
    public ArrayList<String> usedWords;
    public ArrayList<Tile> hand;
    public int points;



    public Move(Board board, Player player, Wordbag bag, ArrayList<String> usedWords, ArrayList<Tile> hand, int points) {
        this.board = board;
        this.player = player;
        this.bag = bag;
        this.usedWords = usedWords;
        this.hand = hand;
        this.points = points;
    }
}
