import javax.swing.*;
import java.io.Serializable;
import java.util.*;

public class AiPlayer extends Player {

    private int points;
    private ArrayList<Tile> tempHand;
    private String name;
    private int NUM_TILES = 7;
    private final int SIZE = 15;
    private Board board;

    public AiPlayer(String name){
        super(name);
    }
    @Override
    public boolean isAI(){
        return true;
    }
    @Override
    public void drawNewTiles(Wordbag tilebag){
        int replace = NUM_TILES - hand.size();
        if(tilebag.getBagSize()< replace && tilebag.getBagSize() > 0){
            //System.out.println("Only had +"+tilebag.getBagSize()+" , these are the remaining tiles.");
            for(int i =0; i< tilebag.getBagSize();i++){
                hand.add(tilebag.drawTileAI());
            }
            return;
        }else if(tilebag.getBagSize()==0){
            //System.out.println("All tiles have been drawn.");
            return;
        }
        for(int i =0; i<replace;i++){
            hand.add(tilebag.drawTileAI());
        }
    }
    @Override
    public void setHand(Wordbag bag){
        for(int i = 0; i < NUM_TILES; i++){
            hand.add(bag.drawTileAI());
        }
    }

    public void swapWithTemp(ArrayList<Tile> tempHand){
        this.hand = tempHand;
    }

    //function to get all valid words given a hand and the letter it must start with
    public static ArrayList<String> findValidWords(String startLetter, List<Character> letters, Trie trie, int direction) {
        //create a set of valid words for this spot so that there are no duplicates
        Set<String> validWords = new HashSet<>();
        int limit =20;
        //generating the set of words
        generateWords(startLetter,"", letters, validWords, trie, direction, limit);
        //returning set as arrayList to index
        return new ArrayList<>(validWords);
    }

    //recursive method to generate words
    private static void generateWords(String startLetter, String current, List<Character> remaining, Set<String> validWords, Trie trie, int direction, int limit) {
        if (validWords.size() >= limit) {
            return;
        }
        //checking if direction is going up or left and having startLetter be end of word
        if(direction == 1 || direction == 3){
            //check if starting letter plus current combination of letters is a valid word and adding it to valid words set
            if (!current.isEmpty() && trie.hasWord(current + startLetter)) {
                validWords.add(current + startLetter);
            }
        }
        else{
            //check if starting letter plus current combination of letters is a valid word and adding it to valid words set
            if (!current.isEmpty() && trie.hasWord(startLetter + current)) {
                validWords.add(startLetter + current);
            }
        }


        //loops for all letters left in hand
        for (int i = 0; i < remaining.size(); i++) {
            //gets the next remaining characters
            List<Character> nextRemaining = new ArrayList<>(remaining);
            //removing the selected character from remaining characters
            char nextChar = nextRemaining.remove(i);
            //add removed character to current string of characters and recursively calling the function
            generateWords(startLetter,current + nextChar, nextRemaining, validWords, trie, direction, limit);
        }
    }

    //for choosing word
    public Map.Entry<Coordinate, String> chooseWord(Board board, Trie trie, int direction){

        //creating new arrayList for characters in hand from tiles in hand
        ArrayList<Character> lettersInHand = new ArrayList<Character>();
        for(Tile tile: this.hand){
            lettersInHand.add(tile.getID().charAt(0));
        }
        Hashtable<Coordinate, String> lettersWCords = new Hashtable<>();

        //checking if board is empty (i.e, first turn)
        String[][] currentBoard = board.getBoard();
        if(board.isEmptyBoard()){
            lettersWCords.put(new Coordinate(8, 8), "");
        }
        //if board isn't empty (not first turn)
        else {
            //getting all tiles that are on the board
            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    if(!currentBoard[i][j].equals(" ")){
                        //creating a new dictionary entry for this coordinate and creating empty arraylist to
                        //put valid words at that coordinate later
                        lettersWCords.put(new Coordinate(i + 1, j + 1), currentBoard[i][j]);
                    }
                }
            }
        }
        //creating list for coordinates
        List<Coordinate> coordinates = new ArrayList<>(lettersWCords.keySet());


        //creating loop to see all valid coordinates and their equivalent letters
        System.out.println("all valid coordinates and their corresponding letters");
        for(Coordinate c: coordinates){
            System.out.println(c.row + " " + c.col + ": " + lettersWCords.get(c));
        }

        //we have a random coordinate to look at
        Random rand = new Random();
        Coordinate randCord = coordinates.get(rand.nextInt(coordinates.size()));

        //map entry containing the random coordinate and its associated word
        Map.Entry<Coordinate, String> entry = Map.entry(randCord, lettersWCords.get(randCord));
        //printing result

        System.out.println("Randomly selected coordinate and letter: " + randCord.row + " " + randCord.col + ": " + entry.getValue());

        //getting possible words to play on that tile
        ArrayList<String> validWords = findValidWords(entry.getValue(), lettersInHand, trie, direction);

        //checking if any valid words were found
        if(validWords.isEmpty()){
            return Map.entry(entry.getKey()," ");
        }

        System.out.println("valid words:");
        for(String v: validWords){
            System.out.println(v);
        }
        //getting selected word
        String randomWord = validWords.get(rand.nextInt(0, validWords.size()));

        //printing selected word
        System.out.println("selected word: " + randomWord);


        //returning map entry with coordinate and random word
        return Map.entry(entry.getKey(),randomWord);
    }

    public boolean playWord(Board board, Trie trie) {
        // Getting random number to decide the direction to play
        Random rand = new Random();

        int direction = rand.nextBoolean() ? 2:4;
        direction = 2;

        tempHand = hand;

        // Calling chooseWord to get the word and coordinate to play
        Map.Entry<Coordinate, String> wordToPlay = chooseWord(board, trie, direction);
        if(wordToPlay.getValue().equals(" ")){
            JOptionPane.showMessageDialog(null, "Ai player decided to skip");
            return false;
        }
        Coordinate wordCord = wordToPlay.getKey();
        String word = wordToPlay.getValue();

        System.out.println(word);
        System.out.println(wordCord.row + " " + wordCord.col);

        // Getting the board representation
        String[][] tempBoard = board.getBoard();

        // Direction 2: Place word downwards
         if (direction == 2) {
            for (int i = 1; i < word.length(); i++) {
                int row = wordCord.row + i;
                if (row >= tempBoard.length || !tempBoard[row][wordCord.col].equals(" ")) {
                    // Out of bounds or occupied tile
                    JOptionPane.showMessageDialog(null, "Ai player decided to skip");
                    return false;
                }
                tempBoard[row - 1][wordCord.col - 1] = String.valueOf(word.charAt(i));
                this.addPoints(new Tile(String.valueOf(word.charAt(i))).getPoints());
                tempHand.remove(new Tile(String.valueOf(word.charAt(i))));
            }
            board.swapWithTemp(tempBoard);
            this.swapWithTemp(tempHand);
            return true;
        }

        // Direction 4: Place word to the right
        else if (direction == 4) {
            for (int i = 1; i < word.length(); i++) {
                int col = wordCord.col + i;
                if (col >= tempBoard[wordCord.row].length || !tempBoard[wordCord.row][col].equals(" ")) {
                    // Out of bounds or occupied tile
                    JOptionPane.showMessageDialog(null, "Ai player decided to skip");
                    return false;
                }
                tempBoard[wordCord.row - 1][col - 1] = String.valueOf(word.charAt(i));
                this.addPoints(new Tile(String.valueOf(word.charAt(i))).getPoints());
                tempHand.remove(new Tile(String.valueOf(word.charAt(i))));
            }
            board.swapWithTemp(tempBoard);
            this.swapWithTemp(tempHand);
            return true;
        }

        // If no valid direction, return false
        return false;
    }

}
