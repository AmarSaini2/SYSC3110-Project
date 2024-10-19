import java.util.ArrayList;

public class Board {
    private Square[][] squares;
    private int BOARD_SIZE = 15;
    private ArrayList<Tile> tilesPlayed;
    public Board(){
        squares = new Square[BOARD_SIZE][BOARD_SIZE];
        for(int i =0; i<BOARD_SIZE;i++){
            for(int j =0;j<BOARD_SIZE;j++){
                squares[i][j] = new Square();
            }
        }
        tilesPlayed = new ArrayList<>();
    }
    public void printBoard(){
        System.out.print("  ");
        for(int j =0; j<BOARD_SIZE; j++){
            System.out.print(j +" ");
        }
        System.out.println();
        for(int i =0;i<BOARD_SIZE;i++){
            System.out.print(i + "  ");
            for(int j = 0; j<BOARD_SIZE;j++){
                System.out.print(squares[i][j] + " ");
            }
            System.out.println();
        }
    }
    public int getNumRows(){
        return BOARD_SIZE;
    }
    public int getNumCols(){
        return BOARD_SIZE;
    }
    public boolean isValidPosition(int row, int col, String letter){
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            System.out.println("Invalid position. Row and column must be between 0 and " + (BOARD_SIZE - 1));
            return false;
        }
        Square currentSquare = squares[row][col];
        if (currentSquare.isFilled()) {
            if (!letter.isEmpty() && letter.charAt(0)!= Character.toLowerCase(currentSquare.getTile().getLetter())) {
                System.out.println("Tile mismatch at row "+row+", col "+ col);
                return false;
            }


        }
        return true;
    }
    public boolean placeWord(String word, int startRow, int startCol, boolean isHorizontal){
        int wordLength = word.length();
        boolean intersects = false;
        if((isHorizontal && startCol + wordLength > BOARD_SIZE)||(!isHorizontal && startRow + wordLength>BOARD_SIZE)){
            System.out.println("Word goes out of bounds!");
            return false;
        }

        for(int i = 0; i<wordLength;i++){
            int row = isHorizontal ? startRow:startRow+i;
            int col = isHorizontal ? startCol+i:startCol;
            if(row< 0 || row>= BOARD_SIZE || col < 0 || col >= BOARD_SIZE){
                System.out.println("Row or Column out of bounds");
                return false;
            }

            Square currentSquare = squares[row][col];
            char currentLetter = word.charAt(i);


            if(currentSquare.isFilled()){
                    if(currentSquare.getTile().getLetter() != currentLetter){
                        System.out.println("Tile mismatch at row: " +row + ", col "+col);
                        return false;
                    }else{
                        intersects = true;
                    }
            } else{
            if(hasAdjacentTile(row,col)){
                intersects = true;
            }
            }
        }
        if(!intersects && !tilesPlayed.isEmpty()){
            System.out.println("Word must intersect with an existing word");
            return false;
        }
        for(int i = 0; i < wordLength; i++){
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;

            Square currentSquare= squares[row][col];
            if(!currentSquare.isFilled()){
                Tile newTile = new Tile(word.charAt(i));
                currentSquare.fillSquare(newTile);
                tilesPlayed.add(newTile);
            }
        }
        return true;
    }
    public boolean hasTileOnBoard(char a){
        for(int i =0;i<BOARD_SIZE;i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (squares[i][j].isFilled() && Character.toLowerCase(squares[i][j].getTile().getLetter()) == a) {
                    return true;
                }
            }
        }
        return false;
    }
    public Tile getTile(int row, int col){
        if(row >= 0 && row < BOARD_SIZE && col >= 0 && col<BOARD_SIZE){
            return squares[row][col].getTile();
        }
        return null;
    }
    public boolean hasAdjacentTile(int row, int col){
        int [] rowOffsets={-1,1,0,0};
        int [] colOffsets = {0,0, -1, 1};
        for(int i =0; i< 4;i++ ){
            int adjRow = row + rowOffsets[i];
            int adjCol = col + colOffsets[i];
            if(adjRow >= 0 && adjRow < BOARD_SIZE && adjCol >= 0 && adjCol < BOARD_SIZE){
                if(squares[adjRow][adjCol].isFilled()){
                    return true;
                }
            }
        }
        return false;
    }

}
