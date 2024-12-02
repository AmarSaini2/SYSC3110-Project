import java.io.Serializable;
import java.util.*;
/**
 * The Board class represents the Scrabble game board where players place tiles to form words.
 * The board is a 2D grid structure, typically 15x15, which holds the tiles played by each player.
 *
 * The Board class provides methods to place tiles on the board, check word validity and placement.
 *
 * This class is responsible for validating that placed words intersect with existing words and adhere to Scrabble rules.
 *
 */
public class Board implements Serializable {
    private String[][] board;
    private ArrayList<Coordinate> placedTileList;
    private HashMap<Coordinate, String> premiumTileList;
    private enum Direction{VERTICAL, HORIZONTAL, INVALID}
    public Direction placedTileDirection;
    private final int SIZE = 15;
    /**
     * Constructs a new Board with a default size of 15x15 and initializes an empty list of placed tiles.
     *
     */
    public Board(){
        this.board = new String[SIZE][SIZE];
        this.placedTileList = new ArrayList<>();
        this.premiumTileList = new HashMap<>();
        initializeBoard();
        placedTileDirection = Direction.INVALID;
        for(String[] row: board){
            Arrays.fill(row, " ");
        }
    }
    /**
     * Constructs a new Board from an inputBoard and initializes the grid with the input information
     * found.
     *
     * @param inputBoard the board to be inputted
     */
    public Board(Board inputBoard){
        this.board = new String[SIZE][SIZE];
        placedTileDirection = inputBoard.placedTileDirection;
        this.placedTileList = new ArrayList<>();
        this.premiumTileList = inputBoard.getPremiumTileList();
        for(Coordinate coord : inputBoard.placedTileList){
            placedTileList.add(new Coordinate(coord.row, coord.col));
        }
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                this.board[i][j] = inputBoard.board[i][j];
            }
        }
    }

    public void initializeBoard(){
        // Triple Word Score (3WS)
        premiumTileList.put(new Coordinate(0, 0), "3WS");
        premiumTileList.put(new Coordinate(0, 7), "3WS");
        premiumTileList.put(new Coordinate(0, 14), "3WS");
        premiumTileList.put(new Coordinate(7, 0), "3WS");
        premiumTileList.put(new Coordinate(7, 14), "3WS");
        premiumTileList.put(new Coordinate(14, 0), "3WS");
        premiumTileList.put(new Coordinate(14, 7), "3WS");
        premiumTileList.put(new Coordinate(14, 14), "3WS");

        // Double Word Score (2WS)
        premiumTileList.put(new Coordinate(1, 1), "2WS");
        premiumTileList.put(new Coordinate(1, 13), "2WS");
        premiumTileList.put(new Coordinate(13, 1), "2WS");
        premiumTileList.put(new Coordinate(13, 13), "2WS");
        premiumTileList.put(new Coordinate(3, 0), "2WS");
        premiumTileList.put(new Coordinate(3, 14), "2WS");
        premiumTileList.put(new Coordinate(11, 0), "2WS");
        premiumTileList.put(new Coordinate(11, 14), "2WS");
        premiumTileList.put(new Coordinate(5, 5), "2WS");
        premiumTileList.put(new Coordinate(5, 9), "2WS");
        premiumTileList.put(new Coordinate(9, 5), "2WS");
        premiumTileList.put(new Coordinate(9, 9), "2WS");

        // Triple Letter Score (3LS)
        premiumTileList.put(new Coordinate(1, 5), "3LS");
        premiumTileList.put(new Coordinate(1, 9), "3LS");
        premiumTileList.put(new Coordinate(5, 1), "3LS");
        premiumTileList.put(new Coordinate(5, 13), "3LS");
        premiumTileList.put(new Coordinate(9, 1), "3LS");
        premiumTileList.put(new Coordinate(9, 13), "3LS");
        premiumTileList.put(new Coordinate(13, 5), "3LS");
        premiumTileList.put(new Coordinate(13, 9), "3LS");

        // Double Letter Score (2LS)
        premiumTileList.put(new Coordinate(0, 3), "2LS");
        premiumTileList.put(new Coordinate(0, 11), "2LS");
        premiumTileList.put(new Coordinate(3, 0), "2LS");
        premiumTileList.put(new Coordinate(3, 14), "2LS");
        premiumTileList.put(new Coordinate(11, 0), "2LS");
        premiumTileList.put(new Coordinate(11, 14), "2LS");
        premiumTileList.put(new Coordinate(14, 3), "2LS");
        premiumTileList.put(new Coordinate(14, 11), "2LS");
        premiumTileList.put(new Coordinate(2, 6), "2LS");
        premiumTileList.put(new Coordinate(2, 8), "2LS");
        premiumTileList.put(new Coordinate(6, 2), "2LS");
        premiumTileList.put(new Coordinate(6, 12), "2LS");
        premiumTileList.put(new Coordinate(8, 2), "2LS");
        premiumTileList.put(new Coordinate(8, 12), "2LS");
        premiumTileList.put(new Coordinate(12, 6), "2LS");
        premiumTileList.put(new Coordinate(12, 8), "2LS");

    }
    public String[][] getBoard(){return this.board;}

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
    /**
     * Place the letter of a tile on the board at the specified row and column.
     *
     * @param row the row of the letter to be placed
     * @param col the column of the letter to be placed
     * @param input the letter to be placed
     */
    public void placeLetter(int row, int col, String input) {
        board[row][col] = input;
    }
    /**
     * Remove a letter from a specified row and column.
     *
     * @param row the row of letter to be removed
     * @param col the column of letter to be removed
     * @return the letter removed
     */
    public String removeLetter(int row,int col){
        String letter = board[row][col];
        board[row][col] = " ";
        return letter;
    }

    public boolean isEmptyBoard(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(!this.board[i][j].equals(" ")){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isEmptyLocation(int row, int col){
        return(board[row][col].equals(" ") && row>=0 && row < SIZE && col>=0 && col < SIZE);//returns true if empty, false if not
    }

    public void swapWithTemp(Board temp){
        board = temp.board;
    }

    public void swapWithTemp(String[][] temp){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                this.board[i][j] = temp[i][j];
            }
        }
    }
    /**
     * Check the validity of a word.
     *
     * @param trie word check tree structure
     * @return true, if valid word. false, otherwise
     */
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
        Coordinate coord = null;

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

                coord = new Coordinate(row, i);
                //Coordinate coord = new Coordinate(row, i);
                if(!placedTileList.contains(coord)){//if the tile was not newly placed this turn, don't check if it makes an additional word
                    continue;
                }
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

                coord = new Coordinate(i, col);
                //Coordinate coord = new Coordinate(i, col);
                if(!placedTileList.contains(coord)){//if the tile was not newly placed this turn, don't check if it makes an additional word
                    continue;
                }
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

        int wordPoints = 0;
        Boolean tripleWord = false;
        Boolean doubleWord = false;
        for (String word : stringArray) {//for each string in stringArray, make tiles out of the characters and sum up their points
            tripleWord = false;
            doubleWord = false;
            wordPoints = 0;
            System.out.println(word);
            char[] charArray = word.toCharArray();
            for (char c : charArray) {
                Tile tile = new Tile(String.valueOf(c));
                for(Coordinate coordinate: placedTileList){
                    if(board[coordinate.row][coordinate.col].equals(String.valueOf(c)) && premiumTileList.containsKey(coordinate)){
                        switch(premiumTileList.get(coord)){
                            case("3WS"):
                                tripleWord = true;
                                System.out.println("Triple Word!");
                                break;
                            case("2WS"):
                                doubleWord = true;
                                System.out.println("Double Word!");
                                break;
                            case("3LS"):
                                wordPoints += tile.getPoints() * 3;
                                System.out.println("Triple Letter!");
                                break;
                            case("2LS"):
                                wordPoints += tile.getPoints() * 2;
                                System.out.println("Double Letter!");
                                break;
                        }
                    }
                }
                wordPoints += tile.getPoints();
            }
            if(tripleWord){
                points += wordPoints*3;
            }
            if(doubleWord){
                points += wordPoints*2;
            }

            if(!doubleWord && !tripleWord){
                points += wordPoints;
                //points += tile.getPoints();
            }
        }
        //System.out.println("points:" + points);
        System.out.println("Total points: " + points);
        return points;
    }
    /**
     * Helper method to determine the direction of word being played.
     */
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
    /**
     * Add a coordinate to the placedTileList.
     * @param c coordinate
     */
    public void addCoordinate(Coordinate c){
        placedTileList.add(c);
    }
    /**
     * Clear the placed tile list.
     */
    public void clearPlacedTileList(){
        for(Coordinate coord: placedTileList){
            placedTileList.remove(coord);
        }
    }

    public void clearPremiumTileList(){
        premiumTileList.clear();
    }

    public void updatePremiumTileList(int row, int col, String tileType){
        switch(tileType){
            case("Triple Tile"):
                premiumTileList.put(new Coordinate(row, col), "3LS");
                break;
            case("Double Tile"):
                premiumTileList.put(new Coordinate(row, col), "2LS");
                break;
            case("Triple Word"):
                premiumTileList.put(new Coordinate(row, col), "3WS");
                break;
            case("Double Word"):
                premiumTileList.put(new Coordinate(row, col), "2WS");
                break;
        }
    }

    public HashMap<Coordinate, String> getPremiumTileList(){
        return premiumTileList;
    }
}