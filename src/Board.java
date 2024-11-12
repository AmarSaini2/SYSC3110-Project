import java.util.*;
public class Board {
    private String[][] board;
    private ArrayList<Coordinate> placedTileList;
    private enum Direction{VERTICAL, HORIZONTAL, INVALID}
    public Direction placedTileDirection;
    private final int SIZE = 15;

    public Board(){
        this.board = new String[SIZE][SIZE];
        this.placedTileList = new ArrayList<>();
        placedTileDirection = Direction.INVALID;
        for(String[] row: board){
            Arrays.fill(row, " ");
        }
    }

    public Board(Board inputBoard){
        this.board = new String[SIZE][SIZE];
        placedTileDirection = inputBoard.placedTileDirection;
        this.placedTileList = new ArrayList<>();
        for(Coordinate coord : inputBoard.placedTileList){
            placedTileList.add(new Coordinate(coord.row, coord.col));
        }
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

    }
    public String removeLetter(int row,int col){
        String letter = board[row][col];
        board[row][col] = " ";
        return letter;
    }

    public boolean isEmptyLocation(int row, int col){
        return(board[row][col].equals(" ") && row>=0 && row < SIZE && col>=0 && col < SIZE);//returns true if empty, false if not
    }

    public void swapWithTemp(Board temp){
        board = temp.board;
    }

    public boolean checkValidity(Trie trie) {
        determineDirection();
        if(placedTileList.isEmpty()){
            System.out.println("No tiles placed");
            return false;
        }

        Coordinate start = new Coordinate(SIZE, SIZE);
        Coordinate end = new Coordinate (0,0);
        for(Coordinate coord: placedTileList){
            if(placedTileDirection == Direction.HORIZONTAL){
                if(coord.col < start.col){
                    start = coord;
                }
                if(coord.col > end.col){
                    end = coord;
                }
            }
            if(placedTileDirection == Direction.VERTICAL){
                if(coord.row < start.row){
                    start = coord;
                }
                if(coord.row > end.row){
                    end = coord;
                }
            }    
        }

        Coordinate delta = new Coordinate(end.row - start.row, end.col - start.col);
        
        if(delta.row == delta.col && placedTileList.size() > 1){
            System.out.println("delta error");
            System.out.println(delta.row + ", " + delta.col);
            return false;  
        }

        for (int row = 0; row < SIZE; row++) {//for every row, check that the words are valid
            StringBuilder rowString = new StringBuilder();
            for (int col = 0; col < SIZE; col++) {
                rowString.append(board[row][col]);
            }
            String[] rowStringArray = rowString.toString().trim().split(" ");
            for (String s : rowStringArray) {
                if(s.length() < 2){
                    continue;
                }
                if (!trie.hasWord(s)) {
                    System.out.println("word not found: " + s);
                    return false;
                }

            }
        }
        for (int col = 0; col < SIZE; col++) {//for every column, check that the words are valid
            StringBuilder columnString = new StringBuilder();
            for (int row = 0; row < SIZE; row++) {
                columnString.append(board[row][col]);
            }
            String[] rowStringArray = columnString.toString().trim().split(" ");
            for (String s : rowStringArray) {
                if(s.length() < 2){
                    continue;
                }
                if(!trie.hasWord(s)){
                    System.out.println("word not found: " + s);
                    return false;
                }
            }
        }
        
        

        return true;
    }





    public int calculatePoints() {
        int row = placedTileList.getLast().row;
        int col = placedTileList.getLast().col;
        int verticalStart = row, verticalEnd = row;
        int horizontalStart = col, horizontalEnd = col;
        int points = 0;
        ArrayList<String> stringArray = new ArrayList<>();
        StringBuilder vWord = new StringBuilder();
        StringBuilder hWord = new StringBuilder();

        if(placedTileDirection.equals(Direction.HORIZONTAL)){//iterate through horizontal string and calculate all vertical auxillary strings made

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
            System.out.println(word);
            char[] charArray = word.toCharArray();
            for (char c : charArray) {
                Tile tile = new Tile(String.valueOf(c));
                points += tile.getPoints();
            }
        }
        System.out.println("points:" + points);
        return points;
    }

    public void determineDirection(){
        if(this.placedTileList.isEmpty()){
            return;
        }
        Boolean sameRow = true;
        Boolean sameCol = true;
        int row = placedTileList.getFirst().row;
        int col = placedTileList.getFirst().col;

        for(Coordinate coord: placedTileList){
            if(coord.row != row){
                sameRow = false;
            }
            if(coord.col != col){
                sameCol = false;
            }
        }

        if(sameRow == true && sameCol == false){
            placedTileDirection = Direction.HORIZONTAL;
        }else if(sameCol == true && sameRow == false){
            placedTileDirection = Direction.VERTICAL;
        }else{
            placedTileDirection = Direction.INVALID;
        }
    }

    public void addCoordinate(Coordinate c){
        placedTileList.add(c);
    }

    public void clearPlacedTileList(){
        for(Coordinate coord: placedTileList){
                placedTileList.remove(coord);
        }
    }
}
