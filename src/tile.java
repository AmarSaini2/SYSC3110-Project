import java.util.Formatter;

public class Tile {
    private String letter;
    private int points;

    Public Tile(String letter){
        this.letter = letter;
        switch(letter){
            case "A", "E", "I", "O", "N", "R", "T", "L", "S", "U":
                this.points = 1;
                break;
            case "B", "C", "M", "P":
                this.points = 3;
                break;
            case "D", "G":
                this.points = 2;
                break;
            case "F", "H","V", "W", "Y":
                this.points = 4;
                break;
            case "J", "X":
                this.points = 8;
                break;
            case "K":
                this.points = 5;
                break;
            case "Q", "Z":
                this.points = 10;
                break;
            case "Blank:
                this.points = 0;
                break;
        }
    }

    public String getLetter(){
        return this.letter;
    }

    public int getPointValue(){
        return this.points;
    }
}
