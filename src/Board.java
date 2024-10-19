import java.util.*;
public class Board {
    private String[][] board;
    private final int SIZE = 15;

    public Board(){
        this.board = new String[SIZE][SIZE];
        for(String[] row: board){
            Arrays.fill(row, " ");
        }
    }

    public Board(Board inputBoard){
        this.board = new String[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                this.board[i][j] = inputBoard.board[i][j];
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

    public void swapWithTemp(Board temp){
        board = temp.board;
    }

    public boolean checkValidity(Trie trie) {
        for (int i = 0; i < SIZE; i++) {//for every row, check that the words are valid
            StringBuilder rowString = new StringBuilder();
            for (int j = 0; j < SIZE; j++) {
                rowString.append(board[i][j]);
            }
            String[] rowStringArray = rowString.toString().split("blank|\\s+");
            for (String s : rowStringArray) {
                System.out.println(s);
                if (!trie.hasWord(s.trim())) {
                    return false;
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {//for every column, check that the words are valid
            StringBuilder columnString = new StringBuilder();
            for (int j = 0; j < SIZE; j++) {
                columnString.append(board[i][j]);
            }
            String[] rowStringArray = columnString.toString().split("blank|\\s+");
            for (String s : rowStringArray) {
                System.out.println(s);
                if (!trie.hasWord(s.trim())) {
                    return false;
                }
            }
        }
        return true;
    }
}
