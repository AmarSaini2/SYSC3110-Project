import java.util.*;

public class AiPlayer extends Player{

    int aiLevel; //determines how goated the ai is
    private int points;
    private ArrayList<Tile> hand;
    private String name;
    private int NUM_TILES = 7;
    private final int SIZE = 15;

    public AiPlayer(String name, int aiLevel){
        super(name);
        this.aiLevel = aiLevel;
    }

    public void setAiLevel(int newLvl){this.aiLevel = newLvl;}

    public int getAiLevel() {return this.aiLevel;}

    public static List<String> findValidWords(String startLetter, List<Character> letters, Trie trie) {
        Set<String> validWords = new HashSet<>();
        generateWords(startLetter,"", letters, validWords, trie);
        return new ArrayList<>(validWords);
    }

    private static void generateWords(String startLetter, String current, List<Character> remaining, Set<String> validWords, Trie trie) {
        if (!current.isEmpty() && trie.hasWord(startLetter + current)) {
            validWords.add(startLetter + current);
        }

        for (int i = 0; i < remaining.size(); i++) {
            List<Character> nextRemaining = new ArrayList<>(remaining);
            char nextChar = nextRemaining.remove(i);
            generateWords(startLetter,current + nextChar, nextRemaining, validWords, trie);
        }
    }

    public Dictionary<Coordinate, Tile> chooseWord(Board board, Trie trie){
                        //gets list of available words
        //gets combination of words that can be formed from bank of words and letters on board
        ArrayList<Character> lettersInHand = new ArrayList<Character>();
        for(Tile tile: this.hand){
            lettersInHand.add(tile.getID().charAt(0));
        }

        //getting letters on board
        Set<String> lettersOnBoard = new HashSet<String>();
        String[][] currentBoard = board.getBoard();
        if(board.isEmptyBoard()){
            lettersOnBoard.add("");
        }
        else {
            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    if(!currentBoard[i][j].equals(" ")){
                        lettersOnBoard.add(currentBoard[i][j]);
                    }
                }
            }
        }

        //getting all words that can be made from words in hand and letter on board
        List<String> validWords = new ArrayList<>();
        for(String c: lettersOnBoard){
            validWords.addAll(findValidWords(c,lettersInHand,trie));
        }

        //sort list of available words by point score
            //difficulty: difficult
        //get locations of the words we can play
        //go down the list by 9 - ai level (nine taking best word) (1 taking 8th best word)
            //difficulty: mickey mouse
        //if word isnt in index, pass turn (might tweak this later if we find the ai passes too often)
            //difficulty: mickey mouse
        //return chosen word (no check should be necessaray as the ai shouldnt be able to think of illegal words
            //difficulty: mickey mouse
        return null;
    }

    public void playWord(Dictionary<Coordinate, Tile> word){
        //plays the word onto the board
        //similar to submitting a word but slightly different as validility does not need to be checked
            //difficulty: medium
        //removes tiles from hand
            //difficulty: mickey mouse
    }

}
