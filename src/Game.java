import java.util.*;

public class Game {
    static Scanner in = new Scanner(System.in);

    Player p1,p2;
    Wordbag bag;
    Board board;

    Game(String playerName1, String playerName2){
        this.p1 = new Player(playerName1);
        this.p2 = new Player(playerName2);
        this.bag = new Wordbag();
        this.board = new Board();
        this.p1.setHand(this.bag);
        this.p2.setHand(this.bag);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Scrabble!\nPlayer 1, please enter your name:");
        String p1Name = getPlayerName();
        System.out.println("Player 2, please enter your name:");
        String p2Name = getPlayerName();
        Game game = new Game(p1Name, p2Name);
        System.out.println("Welcome " + game.p1.getName() + " and " + game.p2.getName());
        game.playerTurn(game.p1);
    }

    public static String getPlayerName(){
        while (true) { 
            try {
                String playerName = in.next();
                return playerName;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a name");
                in.next();
            }   
        }
    }

    public void playerTurn(Player currentPlayer){
        board.display();
        System.out.println("\n" + currentPlayer.getName() + "'s turn:\nHand:");
        currentPlayer.printHand();
        System.out.println("Choose a letter to place:");

        String pickedTile;
        while (true) { 
            try {
                pickedTile = in.next();
                if(!currentPlayer.hasTile(pickedTile)){
                    System.out.println("That tile is not in your hand.");
                }else{
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                in.next();
            }   
        }

        System.out.println("Please input a coordinate \"row,col\" to place " + pickedTile + " in");

        int row, col;
        while (true) { 
            try {
                String coordString = in.next();
                String[] splitcoordString = coordString.split(",");
                if(splitcoordString.length != 2){
                    System.out.println("Invalid input");
                }else{
                    row = Integer.parseInt(splitcoordString[0]);
                    col = Integer.parseInt(splitcoordString[1]);
                    Coordinate coord = new Coordinate(row,col);
                    if(board.isAllowedCharacter(coord, pickedTile)){
                        board.placeLetter(row, col, pickedTile);//place tile on board
                        currentPlayer.useTile(pickedTile);//remove tile from hand
                    }else{
                        System.out.println("Cannot place that letter there");
                    }
                }


            } catch(InputMismatchException e) {
                System.out.println("Invalid input.");
                in.next();
            }   
        }

    }
}