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
                if (!trie.hasWord(s.trim())) {
                    return false;
                }
            }
        }
        return true;
    }

    public int calculatePoints(int row, int col, String direction) {
        int verticalStart = row, verticalEnd = row;
        int horizontalStart = col, horizontalEnd = col;
        int points = 0;
        ArrayList<String> stringArray = new ArrayList<>();
        StringBuilder vWord = new StringBuilder();
        StringBuilder hWord = new StringBuilder();

        if(direction.equals("h")){//iterate through horizontal string and calculate all vertical auxillary strings made

            int countLeft = col;
            while (countLeft >= 0 && !board[row][countLeft].equals(" ")) {//find start of horizontal word by checking left from last placed tile
                horizontalStart = countLeft;
                countLeft--;
            }

            int countRight = col;
            while (countRight < SIZE && !board[row][countRight].equals(" ")) {//find end of horizontal word by checking right from last placed tile
                horizontalEnd = countRight;
                countRight++;
            }
            for (int i = horizontalStart; i <= horizontalEnd; i++) {//Iterate across the horizontal word. For each letter, calculate its vertical word
                hWord.append(board[row][i]);//add current letter to the horizontal word builder

                int countUp = row;
                while (countUp >= 0 && !board[countUp][i].equals(" ")) {//find start of vertical word by checking upwards from current tile
                    verticalStart = countUp;
                    countUp--;
                }

                int countDown = row;
                while (countDown < SIZE && !board[countDown][i].equals(" ")) {//find end of vertical word by checking downwards from current tile
                    verticalEnd = countDown;
                    countDown++;
                }

                for (int j = verticalStart; j <= verticalEnd; j++) {//iterate down vertical word and add to string array
                    vWord.append(board[j][i]);
                }
                if(vWord.length() > 1){
                    stringArray.add(vWord.toString());//only add if there is a word
                }
                vWord.setLength(0);//reset for next loop
            }
            stringArray.add(hWord.toString());
            hWord.setLength(0);//reset for next loop

        }else{//iterate through vertical string and calculate all auxillary horizontal strings made

            int countUp = row;
            while (countUp >= 0 && !board[countUp][col].equals(" ")) {//find start of vertical word by checking upwards from last placed tile
                verticalStart = countUp;
                countUp--;
            }

            int countDown = row;
            while (countDown < SIZE && !board[countDown][col].equals(" ")) {//find end of vertical word by checking downwards from last placed tile
                verticalEnd = countDown;
                countDown++;
            }

            for (int i = verticalStart; i <= verticalEnd; i++) {//Iterate down the vertical word. For each letter, calculate its horizontal word
                vWord.append(board[i][col]);//add current letter to the vertical word builder

                int countLeft = col;
                while (countLeft >= 0 && !board[i][countLeft].equals(" ")) {//find start of horizontal word by checking left from current tile
                    horizontalStart = countLeft;
                    countLeft--;
                }

                int countRight = col;
                while (countRight < SIZE && !board[i][countRight].equals(" ")) {//find end of horizontal word by checking right current tile
                    horizontalEnd = countRight;
                    countRight++;
                }

                for (int j = horizontalStart; j <= horizontalEnd; j++) {//iterate across horizontal word and add to string array
                    hWord.append(board[i][j]);
                }
                if(hWord.length() > 1){
                    stringArray.add(hWord.toString());//only add if there is a word
                }
                hWord.setLength(0);//reset for next loop
            }
            stringArray.add(vWord.toString());
            vWord.setLength(0);//reset for next loop
        }

        for (String word : stringArray) {//for each string in stringArray, make tiles out of the characters and sum up their points
            char[] charArray = word.toCharArray();
            for (char c : charArray) {
                Tile tile = new Tile(String.valueOf(c));
                points += tile.getPoints();
            }
        }
        return points;
    }
}
