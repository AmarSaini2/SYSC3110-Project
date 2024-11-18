import java.util.*;

public class AiPlayer extends Player{

    int aiLevel; //determines how goated the ai is

    public AiPlayer(String name, int aiLevel){
        super(name);
        this.aiLevel = aiLevel;
    }

    public void setAiLevel(int newLvl){this.aiLevel = newLvl;}

    public int getAiLevel() {return this.aiLevel;}

    public Dictionary<Coordinate, Tile> chooseWord(Board board){
        //gets list of available words
        //sort list of available words by point score
        //go down the list by 9 - ai level (nine taking best word) (1 taking 8th best word)
        //if word isnt in index, pass turn (might tweak this later if we find the ai passes too often)
        //return chosen word (no check should be necessaray as the ai shouldnt be able to think of illegal words
        return null;
    }

    public void playWord(Dictionary<Coordinate, Tile> word){
        //plays the word onto the board
        //similar to submitting a word but slightly different as validility does not need to be checked
        //removes tiles from hand
    }

}
