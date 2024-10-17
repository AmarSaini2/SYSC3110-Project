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
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Scrabble!\nPlayer 1, please enter your name:");
        String p1Name = getPlayerName();
        System.out.println("Player 2, please enter your name:");
        String p2Name = getPlayerName();
        Game game = new Game(p1Name, p2Name);
        System.out.println("Welcome " + game.p1.getName() + " and " + game.p2.getName());
    }

    public static String getPlayerName(){
        while (true) { 
            try {
                String playerName = in.next();
                return playerName;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter either a name");
                in.next();
            }   
        }
    }
}