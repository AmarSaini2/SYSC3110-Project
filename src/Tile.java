public class Tile {
    char letter;
    int value;

    public Tile(char letter){
        this.letter = letter;
        switch (letter) {
            case 'A', 'E', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U':
                this.value = 1;
                break;
            case 'D', 'G':
                this.value = 2;
                break;
            case 'B', 'C', 'M', 'P':
                this.value = 3;
                break;
            case 'F', 'H', 'V', 'W', 'Y':
                this.value = 4;
                break;
            case 'K':
                this.value = 5;
                break;
            case 'J', 'X':
                this.value = 8;
                break;
            case 'Q', 'Z':
                this.value = 10;
                break;


        }
    }
    public char getLetter(){
        return this.letter;
    }
    public int getPointValue(){
        return this.value;
    }
    public String toString(){
        return getLetter() + "";
    }
}
