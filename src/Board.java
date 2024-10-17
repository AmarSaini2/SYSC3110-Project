import java.util.*;
public class Board {
    private String[][] board;
    private final int SIZE = 15;
    private Map<Coordinate, List<String>> allowedCharacters;

    public Board(){
        this.board = new String[SIZE][SIZE];
        for(String[] row: board){
            Arrays.fill(row, " ");
        }
        this.allowedCharacters = new HashMap<>();
        initializeAllowedCharacters();
    }

    private void initializeAllowedCharacters(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                allowedCharacters.put(new Coordinate(i, j), new ArrayList<>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z", "blank")));
            }
        }
    }

    public void display(){
        System.out.print("    ");
        for (int i = 0; i < SIZE; i++){
            System.out.printf("%2d |", i);//print column numbers
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2d |", i); //row number
            for (int j = 0; j < SIZE; j++){
                System.out.print("  " + board[i][j] + "|");
            }
            System.out.println();
        }
    }

    public void placeLetter(int row, int col, String input) {
        board[row][col] = input;
        display();
    }

    public boolean isEmptyLocation(int row, int col){
        return(board[row][col].equals(" ") && row>=0 && row < SIZE && col>=0 && col < SIZE);//returns true if empty, false if not
    }

    public boolean isAllowedCharacter(Coordinate coord, String input){
        return(allowedCharacters.get(coord).contains(input));
    }

    public List<String> getAllowedCharacters(int row, int col){
        Coordinate coord = new Coordinate(row, col);
        return allowedCharacters.get(coord);
    }
}
