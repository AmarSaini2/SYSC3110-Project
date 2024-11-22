import java.util.*;

public class AiPlayer extends Player{

    private int points;
    private ArrayList<Tile> hand;
    private String name;
    private int NUM_TILES = 7;
    private final int SIZE = 15;

    public AiPlayer(String name){
        super(name);
    }


    //function to get all valid words given a hand and the letter it must start with
    public static ArrayList<String> findValidWords(String startLetter, List<Character> letters, Trie trie, int direction) {
        //create a set of valid words for this spot so that there are no duplicates
        Set<String> validWords = new HashSet<>();
        //generating the set of words
        generateWords(startLetter,"", letters, validWords, trie, direction);
        //returning set as arrayList to index
        return new ArrayList<>(validWords);
    }

    //recursive method to generate words
    private static void generateWords(String startLetter, String current, List<Character> remaining, Set<String> validWords, Trie trie, int direction) {
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
            generateWords(startLetter,current + nextChar, nextRemaining, validWords, trie, direction);
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
            WordWSpots.put(new Coordinate(7, 7), new ArrayList<>());
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

    public boolean playWord(Board board, Trie trie){
        //getting random number to decide what direction to play
        Random rand = new Random();
        int direction = rand.nextInt(1,5);
        //calling chooseWord to get word and cord to play
        Map.Entry<Coordinate, String> wordToPlay = chooseWord(board, trie, direction);
        Coordinate wordCord = wordToPlay.getKey();
        String word = wordToPlay.getValue();

        //checking what spaces around the letter are valid
        String[][] tempBoard = board.getBoard();

        //checking if space above letter is valid
        if(direction == 1){
            //looping through spaces to see if there is enough spots to put the word
            for(int i = 1; i < word.length(); i++){
                //if tile is empty
                if(tempBoard[wordCord.row - i][wordCord.col].equals(" ")){
                    //setting letter to proper space (we go in reverse order because word goes up)
                    tempBoard[wordCord.row - i][wordCord.col] = String.valueOf(word.charAt(word.length() - i - 1));
                }
                else{
                    //if tile is already occupied, pass turn
                    return false;
                }
            }
            //if entire word gets placed, we swap with temp (overloaded)
            board.swapWithTemp(tempBoard);
            return true;
        }
        //checking if space below letter is valid
        else if(direction == 2){
            //looping through spaces to see if there is enough spots to put the word
            for(int i = 1; i < word.length(); i++){
                //if tile is empty
                if(tempBoard[wordCord.row + i][wordCord.col].equals(" ")){
                    //setting letter to proper space
                    tempBoard[wordCord.row + i][wordCord.col] = String.valueOf(word.charAt(word.length() + i));
                }
                else{
                    //if tile is already occupied, pass turn
                    return false;
                }
            }
            //if entire word gets placed, we swap with temp (overloaded)
            board.swapWithTemp(tempBoard);
            return true;
        }
        //checking if space left of letter is valid
        else if(direction == 3){
            //looping through spaces to see if there is enough spots to put the word
            for(int i = 1; i < word.length(); i++){
                //if tile is empty
                if(tempBoard[wordCord.row][wordCord.col - i].equals(" ")){
                    //setting letter to proper space (we go in reverse order because word goes left)
                    tempBoard[wordCord.row][wordCord.col - i] = String.valueOf(word.charAt(word.length() - i - 1));
                }
                else{
                    //if tile is already occupied, pass turn
                    return false;
                }
            }
            //if entire word gets placed, we swap with temp (overloaded)
            board.swapWithTemp(tempBoard);
            return true;
        }
        //checking if space right of letter is valid
        else if(direction == 4){
            //looping through spaces to see if there is enough spots to put the word
            for(int i = 1; i < word.length(); i++){
                //if tile is empty
                if(tempBoard[wordCord.row][wordCord.col + i].equals(" ")){
                    //setting letter to proper space
                    tempBoard[wordCord.row][wordCord.col + i] = String.valueOf(word.charAt(word.length() + i));
                }
                else{
                    //if tile is already occupied, pass turn
                    return false;
                }
            }
            //if entire word gets placed, we swap with temp (overloaded)
            board.swapWithTemp(tempBoard);
            return true;
        }
        //never to be reached
        return true;
        //adding comment so i can commit my code again
    }

}
