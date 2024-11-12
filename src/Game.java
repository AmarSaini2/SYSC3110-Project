import java.util.*;
/**
 * The Game class represents the main logic for a Scrabble game.
 * It handles the initialization of the game board, player management, tile bag, word validation, scoring, and game flow.
 *
 * Players take turn placing words on the board, drawing tiles from the tile bag, and interacting with the game through a
 * graphical user interface (GUI). The game ensures that all word placements are valid and that words intersect with existing words
 * according to Scrabble rules.
 *
 *
 */
public class Game {
    static Scanner in = new Scanner(System.in);


    private ArrayList<Player> players;
    Wordbag bag;
    Board board;
    Trie trie;
    private int consecutivePasses;
    private int currentPlayerIndex;
    private boolean gameOver;
    private ScrabbleView view;
    private int numPlayers;
    private Tile tile;
    int row;
    int col;
    private Board tempBoard;
    private ArrayList<String> usedWords;
    private boolean startofTurn;
    ArrayList<Tile> tempHand;
     /**
     * Constructs a new game.
     * It initializes a tile bag, a board, trie structure, players list and gameOver status.
     * 
     */
    public Game(){

        this.bag = new Wordbag();
        this.board = new Board();
        trie = new Trie();
        players= new ArrayList<>();
        gameOver = false;
        usedWords = new ArrayList<>();

    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    public void addView(ScrabbleView view){
        this.view = view;
    }
    /**
     * Print the start of game message to players.
     *
     * @return starting game message
     */
    public String startGame(){
        StringBuilder sb = new StringBuilder();

        sb.append("Welcome to the game of scrabble!\n");
        sb.append("To start, select a number of players: (2-4)");
        return sb.toString();
    }
    /**
     * Create a player based on the name given. Player's hand will be given seven tiles and
     * added to the list of players.
     * @param name the name of the player to be added
     */
    public void intializePlayer(String name){
        Player player = new Player(name);
        player.setHand(bag);
        players.add(player);

    }
    /**
     * Create a string for the end of game status.
     * @return end of game summary
     */

    public String handleEndOfGame(){
        StringBuilder sb = new StringBuilder();
        if(isGameOver()){
            gameOver = true;
            sb.append("The game is over.\n");
            sb.append(endGameSummary());
            return sb.toString();
        }

        return "";
    }


    public static String getPlayerName(){
        while (true) {
            try {
                return in.next();
            } catch (InputMismatchException e) {
                //System.out.println("Invalid input. Please enter a name");
                in.next();
            }
        }
    }
    /**
     * Create a string for the current players game options.
     * @return current player turn options
     */
    public String playersTurn(){
        StringBuilder sb = new StringBuilder();
        if(currentPlayerIndex >= players.size()){
            currentPlayerIndex = 0;
        }
        Player currentPlayer = players.get(currentPlayerIndex);
        startofTurn = true;
        sb.append("Player: "+ currentPlayer.getName()+" \n");
        sb.append("1. Play a word\n");
        sb.append("2. Pass your turn\n");
        return sb.toString();
    }
    /**
     * Returns the current players' rack of tiles.
     * @return list of tiles
     */
    public ArrayList<Tile> playerRack(){
        Player currentPlayer = players.get(currentPlayerIndex);
        //System.out.println(currentPlayer.getName());
        return currentPlayer.getHand();
    }
    /**
     * Executes the current player's choice for their turn. If choice 1, the player will begin playing.
     * If choice 2, the player's turn will be passed.
     *
     * @param choice The player's choice of passing or playing
     */
    public void handlePlayerChoice(int choice){
        Player currentPlayer = players.get(currentPlayerIndex);
        if(choice == 1){
            view.playTurn();
            consecutivePasses = 0;
        }else if(choice == 2){
            currentPlayerIndex =(currentPlayerIndex+1)%players.size();
            consecutivePasses++;

        }}
    /**
     * Returns the current player whose turn it is. Initializes a player with a new hand and tempboard
     * for their turn.
     * @return current player
     */
    public Player currentPlayerTurn(){
        Player currentPlayer = players.get(currentPlayerIndex);
        if(startofTurn){
            tempHand = new ArrayList<Tile>(currentPlayer.getHand());
            tempBoard = new Board(board);
            tempBoard.clearPlacedTileList();
            startofTurn = false;
        }

        for(Tile tile:tempHand){
            //System.out.println(tile.getID());
        }
        return currentPlayer;
    }
    public boolean playerTurn(Player currentPlayer){
        //player turn order: pick horizontal or vertical, place tiles until either submit is entered or hand is empty, submit turn for review -> GOTO submit();
        //A temp copy of the board is made to display as the player places tiles before submitting. A temp copy of the hand will be used for the same purpose



        //int firstRowOrCol;//tracker to lock player to row/column
        //tempBoard.display();
        //currentPlayer.printHand();

        /*String direction;
        while(true){//get placement direction
            System.out.println("Choose a direction to place your word in (h/v):");
            try{
                direction = in.next();
                if(!(direction.equalsIgnoreCase("h") || direction.equalsIgnoreCase("v"))){
                    System.out.println("Invalid input");
                    continue;
                }else{
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input");
                continue;
            }
        }*/

        /*while(true){//get first row or column (depending on direction selection)
            if(direction.equals("h")){
                System.out.println("Enter the row you would like to place letters across");
            }else if (direction.equals("v")){
                System.out.println("Enter the column you would like to place letters down");
            }

            try{
                firstRowOrCol = in.nextInt();
                if(firstRowOrCol < 0 || firstRowOrCol > 15){
                    System.out.println("Invalid input: must be between 0 and 15");
                    continue;
                }else{
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input: turn reset");
                return false;
            }
        }*/


        Tile pickedTile = tile;
        if(row == 7 && col == 7 ){
            tempBoard.placeLetter(row, col, tile.getID());
        }
        //System.out.println(tile.getID());
        tempHand.remove(pickedTile);
        /*while (true) {
            //tempBoard.display();

            for (Tile tile : tempHand) {
                System.out.printf("%6s", tile.getID());
            }
            System.out.print("\n");

            for (Tile tile : tempHand) {
                System.out.printf("%6d", tile.getPoints());
            }
            System.out.print("\n");

            String tileName;
            Tile pickedTile;
            while (true) {//get picked tile + check if tile is in hand
                try {

                    pickedTile = tile;
                    if (tempHand.contains(pickedTile)) {
                        break;
                    } else {
                        System.out.println("Tile not in hand");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    continue;
                }
            }




            //program loop for entering a letter
            int row, col;
            while (true) {
                try {
                    if (direction.equals("h")) {
                        col = in.nextInt();
                        row = firstRowOrCol;
                    } else {
                        row = in.nextInt();
                        col = firstRowOrCol;
                    }
                    if (tempBoard.isEmptyLocation(row, col)) {
                        tempBoard.placeLetter(row, col, tileName);
                        tempHand.remove(pickedTile);
                        if (tempHand.isEmpty()) {
                            if (submitWord(currentPlayer, tempBoard, tempHand, row, col, direction)) {
                                currentPlayer.addPoints(50); // 50 bonus points for clearing whole hand
                                System.out.println("Bonus 50 points for playing whole hand!");
                                System.out.println(currentPlayer.getName() + " has " + currentPlayer.getPoints() + " points");
                                return true; //return true for successful playerTurn
                            } else {
                                System.out.println("Invalid word placed");
                                return false; //return false for failed playerTurn
                            }
                        }
                        break;
                    } else {
                        System.out.println("Cannot place on an occupied spot");
                        tempBoard.display();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    return false;
                }
            }


            while(true){
                System.out.println("Would you like to submit your word (y) or keep placing tiles?(n)");
                try{
                    String userInput = in.next();
                    if(userInput.equalsIgnoreCase("y")) {
                        if (submitWord(currentPlayer, tempBoard, tempHand, row, col, direction)) {
                            System.out.println(currentPlayer.getName() + " has " + currentPlayer.getPoints() + " points");
                            return true;
                        } else {
                            System.out.println("Invalid word placed");
                            return false;
                        }
                    }else if(userInput.equalsIgnoreCase("n")){
                        break;//break loop and keep placing tiles
                    }else{
                        System.out.println("Invalid input");//user entered something other than y or n
                        continue;
                    }
                }catch(InputMismatchException e){
                    System.out.println("Invalid input.");
                    continue;
                }
            }
        }*/
        return true;
    }
    /**
     * Tile placed on the board is set and beginning of player turn conditions are set.
     *
     * @param tile tile to be placed onto the temp board.
     */
    public void tilePlaced(Tile tile){
        this.tile =  tile;
        Player currentPlayer = null;
        if(startofTurn){
            currentPlayer = currentPlayerTurn();
        }
        playerTurn(currentPlayer);
    }

    public void setRowCol(int row, int col){
        this.row = row;
        this.col = col;
    }
    /**
     * Updates the temporary board that documents the current players moves.
     *
     * @param row row of tile to be placed
     * @param col column of tile to be placed
     * @param letter letter for the tile to be placed
     */
    public void updateTempBoard(int row, int col, String letter){
        tempBoard.placeLetter(row,col,letter);
        tempBoard.addCoordinate(new Coordinate(row, col));
    }
    /**
     * When a player is done placing their word, it will handle if the word is valid
     * or not and return either true or false. It also handles drawing new tiles if the word
     * is valid.
     * @param currentPlayer current player that submitted the word.
     * @return true if word is valid, false if word is invalid
     */
    public boolean submitWord(Player currentPlayer){
        //check whether tempBoard is valid, update board, update hand, update points, goto next player's turn
        if(tempBoard.checkValidity(trie)){
            currentPlayer.addPoints(tempBoard.calculatePoints());
            board.swapWithTemp(tempBoard);//swap temp board in for main board
            currentPlayer.swapWithTemp(tempHand,bag);//swap temp hand for main hand, refresh hand to 7 tiles

            currentPlayer.drawNewTiles(bag);
            return true;
        }

        return false;
    }
    public boolean isGameOver(){
        if(bag.isEmpty()){
            for(Player player:players){
                if(player.getHand().isEmpty()){
                    return true;
                }
            }
        }
        return consecutivePasses >= players.size();
    }
    public Board getTempBoard(){
        return tempBoard;
    }
    /**
     * Create a string of the end of game status, specifically the player scores and the winner.
     * @return the string of end of game summary
     */
    private String endGameSummary(){
        StringBuilder sb = new StringBuilder();
        sb.append("Final Scores: \n");
        for(Player player: players){
            sb.append(player.getName() + " - Final Score: "+ player.getPoints()+"\n");
        }
        Player winner = Collections.max(players, Comparator.comparing(Player::getPoints));
        sb.append(new StringBuilder().append("The winner is: ").append(winner.getName()).append(" with a score of ").append(winner.getPoints()).toString()+"\n");
        return sb.toString();
    }
    /**
     * Return the current player.
     * @return current player
     */
    public Player getPlayer(){
        return players.get(currentPlayerIndex);
    }
    /**
     * Update the current player index.
     */
    public void updatePlayerIndex(){
        if(currentPlayerIndex >= players.size()){
            currentPlayerIndex = 0;
            return;
        }
        currentPlayerIndex++;

    }

}
