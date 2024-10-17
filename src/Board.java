import java.util.Arrays;
public class Board {
    private char[][] board;
    private final int SIZE = 15;

    public Board(){
        this.board = new char[SIZE][SIZE];
        for(char[] row: board){
            Arrays.fill(row, ' ');
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

    public boolean placeLetter(int row, int col, char letter) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == ' ') {
            board[row][col] = letter; // Place the letter if the position is empty
            display();
            return true;
        }
        return false; // Return false if the position is invalid or occupied
    }
}
