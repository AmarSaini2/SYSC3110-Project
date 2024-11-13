/**
 * The Tile class represents a letter tile in a Scrabble game.
 * Each tile has an associated letter and a point value, which contributes to the player's score
 * when the tile is used in forming words on the board.
 *
 * The Tile class provides methods to access the tile's letter and point value.
 * It is a fundamental element in the Scrabble game as it is used to build words, score points
 * and interact with other components like the board and player rack.
 */
public class Tile {
    private String letter;
    private int points;
    /**
     * Constructs a new Tile with the specified letter and assigns a point value.
     *
     * @param letter the letter represented by this tile
     */
    Tile(String letter){
        this.letter = letter;
        switch(letter){
            case("A"), ("E"), ("I"), ("L"), ("N"), ("O"), ("R"), ("S"), ("T"), ("U"):
                this.points = 1;
                break;
            case("B"), ("C"), ("M"), ("P"):
                this.points = 3;
                break;
            case("D"), ("G"):
                this.points = 2;
                break;
            case("F"), ("H"), ("V"), ("W"), ("Y"):
                this.points = 4;
                break;
            case("J"), ("X"):
                this.points = 8;
                break;
            case("K"):
                this.points = 5;
                break;
            case("Q"), ("Z"):
                this.points = 10;
                break;
            case("blank"):
                this.points = 0;
                break;
        }
    }
    /**
     * Returns the letter represented by this tile.
     *
     * @return the letter of this tile
     */
    public String getID(){
        return this.letter;
    }
    /**
     * Return the point value of this tile.
     *
     * @return the point of this tile
     */
    public int getPoints(){
        return this.points;
    }
    /**
     * Returns a boolean for two tiles being compared.
     *
     * @param obj object being compared
     * @return true, if equal objects. false, otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){ //check if the two references are to the same spot in memory
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){ //rejects null/not Tile objects
            return false;
        }
        Tile that = (Tile) obj; //lets two instances of identical coordinate values to be considered equal
        return letter.equals(that.letter) && points == that.points;

    }
}
