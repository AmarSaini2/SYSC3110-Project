import java.io.IOException;
import java.util.*;

/**
 * The Game class represents a Scrabble game.
 * It starts the game, prints the board and carries out the game.
 */
public class Game {
    private ValidWords wordList;

    private Parser parser;
    boolean finished;
    private ArrayList<Player> players;
    private Tilebag tileBag;
    int currentPlayerIndex;
    private HashSet<String> playedWords;

    private boolean start;
    private Board gameBoard;
    private int CENTRE = 7;
    private int EDGE1 = 0;
    private int EDGE2 = 14;
    private int consecutivePasses;


    public Game() {
        try {
            wordList = new ValidWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parser = new Parser();
        finished = false;
        players = new ArrayList<>();
        tileBag = new Tilebag();
        currentPlayerIndex = 0;
        playedWords = new HashSet<>();
        start = true;
        gameBoard = new Board();

    }
    /**
     * Runs the player's turn and if the player wants to pass their turn or to play a word.
     *
     */
    public void playerTurn(){
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.toString());
        System.out.println("1. Play a word");
        System.out.println("2. Pass your turn");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        if(choice == 1){
            playWord();
            consecutivePasses = 0;
        }else if(choice == 2){
            currentPlayerIndex =(currentPlayerIndex+1)%players.size();
            consecutivePasses++;
            return;
        }else{
            System.out.println("Invalid choice! Please select either 1 or 2.");
            playerTurn();
        }
    }
    public void playWord(){
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.toString());
        String word = parser.getWord();

        int wordScore = 0;
        List<Tile> placedTiles = new ArrayList<>();
        if(word == null){
            System.out.println("You didn't input a word!");
            return;
        }else if(!wordList.isWord(word)){
            System.out.println("'"+word+"' is not valid per the dictionary");
            currentPlayerIndex =(currentPlayerIndex+1)%players.size();
            return;
        }else if (playedWords.contains(word)) {
            System.out.println("That word '"+ word+ "' has already been played!");
            currentPlayerIndex =(currentPlayerIndex+1)%players.size();
            return;
        }
            if(canPlayWord(word, currentPlayer,placedTiles)){
                if(placeWord(placedTiles,word)){
                    for(Tile tile: placedTiles){
                        wordScore += tile.getPointValue();
                        currentPlayer.removeTile(tile);
                    }
                    playedWords.add(word);
                    System.out.println(word + " has been played.");
                    currentPlayer.updatePlayerScore(wordScore);
                    currentPlayer.drawNewTiles(tileBag);
                    currentPlayerIndex =(currentPlayerIndex+1)%players.size();
                } else{
                    System.out.println("Word placement failed");
                    return;
                }

            }else{
                System.out.println("You don't have the necessary tiles. Turn over!");
                return;

        }}

    public boolean placeWord(List<Tile> placedTiles, String word) {
        int startRow, startCol;
        Scanner scan = new Scanner(System.in);
        String wordDirection = getWordDirection(scan);



        if(start){
                startRow = CENTRE;
                startCol = CENTRE;
                start = false;
        }else {


                startRow = getValidInput(scan, "Enter row: (0-14)" );
                //NEED ERROR CHECKING

                startCol = getValidInput(scan, "Enter column: (0-14)");
                scan.nextLine();
                System.out.println("What letter would you like to play off? ");
                String letterStart = scan.nextLine();

                if(!gameBoard.isValidPosition(startRow,startCol,letterStart)){
                      System.out.println("Invalid starting letter.");
                      return false;
                    }

            }
        if(!wordList.isWord(word)){
            System.out.println("Invalid word");
            return false;
        }

        return validatePlace(placedTiles,word, startRow,startCol,wordDirection.equals("h"));

    }
    public void startGame(){
        Scanner scan = new Scanner(System.in);
        printStart();

        int numPlayers = scan.nextInt();
        scan.nextLine();

        while(!playerCreate(numPlayers)){
            printStart();
            numPlayers = scan.nextInt();
            scan.nextLine();
            playerCreate(numPlayers);
        }
        gameBoard.printBoard();
        while(!finished){

            System.out.println(players.get(currentPlayerIndex).getName()+"'s turn: ");
            playerTurn();
            gameBoard.printBoard();
            if(currentPlayerIndex >= numPlayers){
                currentPlayerIndex =0;
            }
            if(isGameOver()){
                finished = true;
                System.out.println("The game is over.");
                break;
            }

        }
        endGameSummary();
    }
    private boolean isGameOver(){
        if(tileBag.isEmpty()){
            for(Player player: players){
                if(player.getTiles().isEmpty()){
                    return true;
                }
            }
        }
        return consecutivePasses>= players.size();
    }
    private void endGameSummary(){
        System.out.println("Final Scores: ");
        for(Player player: players){
            System.out.println(player.toString() + " - Final Score: "+ player.getPlayerScore());
        }
        Player winner = Collections.max(players, Comparator.comparing(Player::getPlayerScore));
        System.out.println(new StringBuilder().append("The winner is: ").append(winner.getName()).append(" with a score of ").append(winner.getPlayerScore()).toString());
    }
    public void printStart(){
        System.out.println();
        System.out.println("Welcome to the game of scrabble!");
        System.out.println("To start, select a number of players: (2-4)");
        System.out.println();
    }
    public boolean playerCreate(int players){
        Scanner scan = new Scanner(System.in);
        if(players <2 || players>4){
            System.out.println("Invalid number of players! You either need more friends or you need to lose a few!");
            return false;
        }
        for(int i =0; i<players; i++){
            System.out.println("Enter Player "+ (i+1)+"'s name: ");
            String name = scan.nextLine();
            this.players.add(new Player(tileBag,name));
        }
        return true;


    }
    private Tile findTile(List<Tile> placedTiles, char letter){
        for(Tile tile:placedTiles){
            if(Character.toLowerCase(tile.getLetter()) == letter){
                //placedTiles.remove(tile);
                return tile;
            }
        }
        return null;
    }
    private boolean canPlayWord(String word, Player currentPlayer, List<Tile> placedTiles){
        boolean hasTiles = true;
        boolean isUsingTile = false;

        for(char a: word.toLowerCase().toCharArray()){
            boolean hasTile = false;

            for(Tile tile: currentPlayer.getTiles()){
                //Also check the played words for the tile as well, so we can play off it
                if((Character.toLowerCase(tile.getLetter()) == a)){
                    hasTile=true;
                    placedTiles.add(tile);

                    break;
                }
            }
            if(!hasTile){
                if(!gameBoard.hasTileOnBoard(a)){
                    hasTiles=false;
                    break;
                }else{
                    isUsingTile = true;
                }
            }
        }
        return hasTiles||isUsingTile;
    }
    private String getWordDirection(Scanner scan){
        String wordDirection;
        do{
            System.out.println("What direction would like to place your tiles: (V or H)");
            wordDirection = scan.nextLine().toLowerCase();
        } while(!wordDirection.equals("v")&& !wordDirection.equals("h"));
        return wordDirection;
    }
    private boolean validatePlace(List<Tile> placedTiles, String word, int startRow, int startCol, boolean isHorizontal){
        boolean hasAdjacentTiles = gameBoard.hasAdjacentTile(startRow,startCol);
        boolean valid= true;
        for(int i = 0;i<word.length();i++){
            int row = startRow + (isHorizontal ? 0:i);
            int col = startCol + (isHorizontal? i:0);
            if(!isHorizontal){
                if(col> EDGE1 && gameBoard.getTile(row, col-1) != null){
                    String formedWord = getFormedWordHorizontal(row,col-1);
                    if(!wordList.isWord(formedWord)){
                        System.out.println("Invalid word formed vertically.");
                        valid = false;
                    }
                }
                if(col < EDGE2 && gameBoard.getTile(row,col +1) != null){
                    String formedWord = getFormedWordHorizontal(row, col+1);
                    if(!wordList.isWord(formedWord)){
                        System.out.println("Invalid word formed vertically.");
                        valid= false;
                    }
                }
            }else{
                if(row > EDGE1 && gameBoard.getTile(row-1,col) !=null){
                    String formedWord = getFormedWordVertical(row-1,col);
                    if(!wordList.isWord(formedWord)){
                        System.out.println("Invalid word formed horizontally.");
                        valid = false;
                    }
                }
                if(row < EDGE2 && gameBoard.getTile(row + 1,col) != null){
                    String formedWord = getFormedWordVertical(row + 1, col);
                    if(!wordList.isWord(formedWord)){
                        System.out.println("Invalid word formed horizontally.");
                        valid = false;
                            }
                        }
                    }
            }
        if(!valid){
            return false;
        }
        for(int i =0; i< word.length();i++){
            int row = startRow + (isHorizontal ? 0:i);
            int col = startCol + (isHorizontal? i:0);
            Tile existingTile = gameBoard.getTile(row,col);
            char currentChar = word.charAt(i);
            if(existingTile != null){
                if(Character.toLowerCase(existingTile.getLetter()) != currentChar){
                    System.out.println("Mismatch with existing tile!");
                    return false;
                }

            }else{
                Tile tileToPlace = findTile(placedTiles, currentChar);
                if(tileToPlace== null){
                    System.out.println("You don't have the tile for the letter"+ currentChar);
                    return false;
                }

            }
        }
        return gameBoard.placeWord(word,startRow,startCol,isHorizontal);
    }
    private int getValidInput(Scanner scan, String position){
        int coord = -1;
        boolean valid = false;
        while(!valid){
            System.out.println(position);
            try{
                coord = scan.nextInt();
                if(coord > EDGE1 && coord < EDGE2){
                    valid = true;
                }else{
                    System.out.println("Please enter a valid position: (0-14)");

                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid position: (0-14)");
                scan.nextLine();
            }
        }

        return coord;
    }
    private String getFormedWordVertical(int row,int col){
        StringBuilder sb = new StringBuilder();
        int currentRow = row;
        while(currentRow >= EDGE1){
            Tile tile = gameBoard.getTile(currentRow,col);
            if(tile != null){
                sb.insert(0,tile.getLetter());
            }else{
                break;
            }
            currentRow--;
        }
        while(currentRow <= EDGE2){
            Tile tile = gameBoard.getTile(currentRow,col);
            if(tile!=null){
                sb.insert(0,tile.getLetter());
            }else{
                break;
            }
            currentRow++;
        }
        return sb.toString();
    }
    private String getFormedWordHorizontal(int row, int col){
        StringBuilder sb = new StringBuilder();
        int currentCol = col;
        while(currentCol >= EDGE1){
            Tile tile = gameBoard.getTile(row,currentCol);
            if(tile != null){
                sb.insert(0,tile.getLetter());
            }else{
                break;
            }
            currentCol--;
        }
        while(currentCol <=14){
            Tile tile = gameBoard.getTile(row,currentCol);
            if(tile !=null){
                sb.insert(0,tile.getLetter());
            }else{
                break;
            }
            currentCol++;
        }

        return sb.toString();
        }

    }



