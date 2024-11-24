import java.util.*;

public class AiPlayer extends Player{

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

        //initializing lists
        ArrayList<String> lettersOnBoard = new ArrayList<String>();
        Hashtable<Coordinate, ArrayList<String>> WordWSpots = new Hashtable<>();

        //checking if board is empty (i.e, first turn)
        String[][] currentBoard = board.getBoard();
        if(board.isEmptyBoard()){
            //setting letters on board to a blank character (starting character wont affect words you can make)
            lettersOnBoard.add("");
            //setting only coordinate you can play off of is the middle tile
            WordWSpots.put(new Coordinate(8, 8), new ArrayList<>());
        }
        //if board isn't empty (not first turn)
        else {
            //getting all tiles that are on the board
            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    if(!currentBoard[i][j].equals(" ")){
                        //add letter to lettersOnBoard
                        lettersOnBoard.add(currentBoard[i][j]);
                        //creating a new dictionary entry for this coordinate and creating empty arraylist to
                        //put valid words at that coordinate later
                        WordWSpots.put(new Coordinate(i, j), new ArrayList<>());
                    }
                }
            }
        }

        //parsing through keys so that we can iterate the dictionary and give each coordinate its list of valid words
        Enumeration<Coordinate> keys = WordWSpots.keys();
        //setting an index so that we can keep track of what letter is at each coordinate
        int i = 0;
        while(keys.hasMoreElements()){
            //get the coordinate for the current key
            Coordinate key = keys.nextElement();
            //get the letter that would be at the same index of the coordinate and pass it to the find valid words
            //then put that list of valid words into the dictionary at the specific coordinate that letter is associated to
            WordWSpots.put(key, findValidWords(lettersOnBoard.get(i),lettersInHand,trie, direction));
            //increment i to get next letterOnBoard
            i++;
        }

        //now we have a dictionary whose keys are coordinates with lists of valid words at those coordinates

        //creating arrayList of map entries so that we cna get a random coordinate/arrayList pair
        ArrayList<Map.Entry<Coordinate, ArrayList<String>>> entryList = new ArrayList<>(WordWSpots.entrySet());

        // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(entryList.size());

        //assign randomly indexed map entry to variable
        Map.Entry<Coordinate, ArrayList<String>> randomEntry = entryList.get(randomIndex);

        //get new random index for ArrayList of words
        randomIndex = random.nextInt(randomEntry.getValue().size());

        //getting a random Word
        String randomWord = randomEntry.getValue().get(randomIndex);

        //returning map entry with coording and random word
        return new AbstractMap.SimpleEntry<>(randomEntry.getKey(), randomWord);
    }

    public boolean playWord(Board board, Trie trie) {
        // Getting random number to decide the direction to play
        Random rand = new Random();

        int direction = rand.nextBoolean() ? 2:4;
        //int direction = 4;
        tempHand = hand;

        // Calling chooseWord to get the word and coordinate to play
        Map.Entry<Coordinate, String> wordToPlay = chooseWord(board, trie, direction);
        Coordinate wordCord = wordToPlay.getKey();
        String word = wordToPlay.getValue();

        System.out.println(word);
        System.out.println(wordCord.row + " " + wordCord.col);
        board.display();
        this.board = board;
        // Getting the board representation
        String[][] tempBoard = board.getBoard();

        // Direction 1: Place word upwards
        if (direction == 1) {
            for (int i = 0; i < word.length(); i++) {
                int row = wordCord.row - i;
                if (row < 0 || !tempBoard[row][wordCord.col].equals(" ")) {
                    // Out of bounds or occupied tile
                    return false;
                }
                tempBoard[row][wordCord.col] = String.valueOf(word.charAt(i));
                this.addPoints(new Tile(String.valueOf(word.charAt(i))).getPoints());
            }
            board.swapWithTemp(tempBoard);
            return true;
        }

        // Direction 2: Place word downwards
         if (direction == 2) {
            for (int i = 0; i < word.length(); i++) {
                int row = wordCord.row + i;
                if (row >= tempBoard.length || !tempBoard[row][wordCord.col].equals(" ")) {
                    // Out of bounds or occupied tile

                    for (int k = 1; k< word.length(); k++) {
                        int col = wordCord.col + k;
                        if (col >= tempBoard[wordCord.row].length || !tempBoard[wordCord.row][col].equals(" ")) {
                            // Out of bounds or occupied tile

                            return false;
                        }
                        tempBoard[wordCord.row][col] = String.valueOf(word.charAt(k));
                        //System.out.println(tempBoard[wordCord.row][col]);
                        this.addPoints(new Tile(String.valueOf(word.charAt(k))).getPoints());
                        tempHand.remove(new Tile(String.valueOf(word.charAt(k))));
                    }
                    board.swapWithTemp(tempBoard);
                    //board.display();
                    this.swapWithTemp(tempHand);
                    return true;
                }
                tempBoard[row][wordCord.col] = String.valueOf(word.charAt(i));
                this.addPoints(new Tile(String.valueOf(word.charAt(i))).getPoints());
                tempHand.remove(new Tile(String.valueOf(word.charAt(i))));
            }
            board.swapWithTemp(tempBoard);
            this.swapWithTemp(tempHand);
            return true;
        }
         else if (direction == 3) {
             for (int i = 0; i < word.length(); i++) {
                 int col = wordCord.col - i;
                 if (col < 0 || !tempBoard[wordCord.row][col].equals(" ")) {
                     // Out of bounds or occupied tile
                     return false;
                 }
                 tempBoard[wordCord.row][col] = String.valueOf(word.charAt(i));
                 this.addPoints(new Tile(String.valueOf(word.charAt(i))).getPoints());
             }
             board.swapWithTemp(tempBoard);
             return true;
         }


        // Direction 4: Place word to the right
        else if (direction == 4) {
            for (int i = 0; i < word.length(); i++) {
                int col = wordCord.col + i;
                if (col >= tempBoard[wordCord.row].length || !tempBoard[wordCord.row][col].equals(" ")) {
                    // Out of bounds or occupied tile
                    for (int j = 1; j < word.length(); j++) {
                        int row = wordCord.row + j;
                        if (row >= tempBoard.length || !tempBoard[row][wordCord.col].equals(" ")) {
                            System.out.println("Fail check "+tempBoard.length+" "+col);
                            System.out.println(tempBoard[wordCord.row][col]);
                            // Out of bounds or occupied tile
                            return false;
                        }
                        System.out.println(tempBoard[row][wordCord.col]);
                        tempBoard[row][wordCord.col] = String.valueOf(word.charAt(j));
                        this.addPoints(new Tile(String.valueOf(word.charAt(j))).getPoints());
                        tempHand.remove(new Tile(String.valueOf(word.charAt(j))));
                    }
                    board.swapWithTemp(tempBoard);
                    this.swapWithTemp(tempHand);
                    return true;
                }
                tempBoard[wordCord.row][col] = String.valueOf(word.charAt(i));
                //System.out.println(tempBoard[wordCord.row][col]);
                this.addPoints(new Tile(String.valueOf(word.charAt(i))).getPoints());
                tempHand.remove(new Tile(String.valueOf(word.charAt(i))));
            }
            board.swapWithTemp(tempBoard);
            //board.display();
            this.swapWithTemp(tempHand);
            return true;
        }

        // If no valid direction, return false
        return false;
    }


}
