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
        List<String> allowedCharactersList = allowedCharacters.get(coord);
        return allowedCharactersList.stream()
            .map(String::toLowerCase) // Ensure all characters are in lowercase
            .anyMatch(letter -> letter.equals(input.toLowerCase()));
    }

    public List<String> getAllowedCharacters(int row, int col){
        Coordinate coord = new Coordinate(row, col);
        return allowedCharacters.get(coord);
    }

    public void updateAllowedCharacters(Coordinate coord, String placedTile, Trie trie){
        allowedCharacters.remove(coord); // Remove allowed character list for that tile

        // Horizontal check
        StringBuilder horizontalWord = new StringBuilder();
        int row = coord.getRow();
        int col = coord.getCol();
    
        // Check left
        for (int j = col - 1; j >= 0 && !board[row][j].equals(" "); j--) {
            horizontalWord.insert(0, board[row][j]);
        }
        
        // Add the placed tile
        horizontalWord.append(placedTile);
        
        // Check right
        for (int j = col + 1; j < SIZE && !board[row][j].equals(" "); j++) {
            horizontalWord.append(board[row][j]);
        }
    
        // Update allowed characters to the right of the word
        if (col + horizontalWord.length() < SIZE) {
            Coordinate rightOfWord = new Coordinate(row, col + horizontalWord.length());
            allowedCharacters.replace(rightOfWord, trie.nextLetters(horizontalWord.toString()));
        }
    
        // Vertical check
        StringBuilder verticalWord = new StringBuilder();
    
        // Check above
        for (int i = row - 1; i >= 0 && !board[i][col].equals(" "); i--) {
            verticalWord.insert(0, board[i][col]);
        }
    
        // Add the placed tile
        verticalWord.append(placedTile);
    
        // Check below
        for (int i = row + 1; i < SIZE && !board[i][col].equals(" "); i++) {
            verticalWord.append(board[i][col]);
        }
    
        // Update allowed characters below the word
        if (row + verticalWord.length() < SIZE) {
            Coordinate belowWord = new Coordinate(row + verticalWord.length(), col);
            allowedCharacters.replace(belowWord, trie.nextLetters(verticalWord.toString()));
        }
    }
}
