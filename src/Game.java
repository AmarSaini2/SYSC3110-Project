import java.lang.reflect.Array;
import java.util.*;

public class Game {
    static Scanner in = new Scanner(System.in);

    Player p1,p2;
    Wordbag bag;
    Board board;
    Trie trie;

    Game(String playerName1, String playerName2){
        this.p1 = new Player(playerName1);
        this.p2 = new Player(playerName2);
        this.bag = new Wordbag();
        this.board = new Board();
        this.p1.setHand(this.bag);
        this.p2.setHand(this.bag);
        trie = new Trie();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Scrabble!\nPlayer 1, please enter your name:");
        String p1Name = getPlayerName();
        System.out.println("Player 2, please enter your name:");
        String p2Name = getPlayerName();
        Game game = new Game(p1Name, p2Name);
        System.out.println("Welcome " + game.p1.getName() + " and " + game.p2.getName());
        while(!game.p1.handIsEmpty() && !game.p2.handIsEmpty()){//if either player still has tiles, keep playing
            while(!game.playerTurn(game.p1)){// until player plays a valid turn, loop back to player
                game.playerTurn(game.p1);
            }
            while(!game.playerTurn(game.p2)){
                game.playerTurn(game.p2);
            }
        }
        System.out.println("Game over!");
        if(game.p1.getPoints() > game.p2.getPoints()){
            System.out.println("Player 1 wins!");
        }else if(game.p2.getPoints() > game.p1.getPoints()){
            System.out.println("Player 2 Wins!");
        }else{
            System.out.println("Tie!");
        }
        System.out.println("Points:\n" + game.p1.getName() + ": " + game.p1.getPoints() + "points\n" + game.p2.getName() + ": " + game.p2.getPoints() + " points");
    }

    public static String getPlayerName(){
        while (true) { 
            try {
                return in.next();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a name");
                in.next();
            }   
        }
    }

    public boolean playerTurn(Player currentPlayer){
        //player turn order: pick horizontal or vertical, place tiles until either submit is entered or hand is empty, submit turn for review -> GOTO submit();
        //A temp copy of the board is made to display as the player places tiles before submitting. A temp copy of the hand will be used for the same purpose
        ArrayList<Tile> tempHand = new ArrayList<>();
        for(Tile tile : currentPlayer.getHand()){
            tempHand.add(new Tile(tile.getID()));//have to loop this way to make a deep copy of the current hand
        }
        Board tempBoard = new Board(board);
        int firstRowOrCol;//tracker to lock player to row/column
        tempBoard.display();
        System.out.println("\n" + currentPlayer.getName() + "'s turn:");

        currentPlayer.printHand();

        System.out.println("Choose a direction to place your word in (h/v):");
        String direction;
        while(true){//get placement direction
            try{
                direction = in.next();
                if(!(direction.equalsIgnoreCase("h") || direction.equalsIgnoreCase("v"))){
                    System.out.println("Invalid input");
                    in.next();
                }else{
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input");
                in.next();
            }
        }

        if(direction.equals("h")){
            System.out.println("Enter the row you would like to place letters across");
        }else{
            System.out.println("Enter the column you would like to place letters down");
        }

        while(true){//get first row or column (depending on direction selection)
            try{
                firstRowOrCol = in.nextInt();
                if(firstRowOrCol < 0 || firstRowOrCol > 15){
                    System.out.println("Invalid input");
                    in.next();
                }else{
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input");
                in.next();
            }
        }

        while (true) {
            tempBoard.display();
            System.out.println("Choose a letter to place:");

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
                    tileName = in.next();
                    pickedTile = new Tile(tileName);
                    if (tempHand.contains(pickedTile)) {
                        break;
                    } else {
                        System.out.println("Tile not in hand");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    in.next();
                }
            }

            if (direction.equals("h")) {
                System.out.println("Please input a column to place " + tileName + " in");
            } else {
                System.out.println("Please input a row to place " + tileName + " in");
            }

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
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    in.next();
                }
            }

            String userInput;
            System.out.println("Would you like to submit your word or keep placing tiles? (y/n)");
            while(true){
                try{
                    userInput = in.next();
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
                        in.next();
                    }
                }catch(InputMismatchException e){
                    System.out.println("Invalid input.");
                    in.next();
                }
            }
        }
    }

    public boolean submitWord(Player currentPlayer, Board tempBoard, ArrayList<Tile> tempHand, int row, int col, String direction){
        //check whether tempBoard is valid, update board, update hand, update points, goto next player's turn
        if(tempBoard.checkValidity(trie)){
            board.swapWithTemp(tempBoard);//swap temp board in for main board
            currentPlayer.swapWithTemp(tempHand,bag);//swap temp hand for main hand, refresh hand to 7 tiles
            while(currentPlayer.getHand().size() < 7){
                currentPlayer.addTile(bag);
            }
            currentPlayer.addPoints(board.calculatePoints(row, col, direction));//calculate and update points
            return true;
        }else{
            return false;
        }
    }
}