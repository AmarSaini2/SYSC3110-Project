public class Tile {
    private String letter;
    private int points;

    Tile(String letter){
        this.letter = letter;
        switch(letter){
            case("a"), ("e"), ("i"), ("l"), ("n"), ("o"), ("r"), ("s"), ("t"), ("u"):
                this.points = 1;
                break;
            case("b"), ("c"), ("m"), ("p"):
                this.points = 3;
                break;
            case("d"), ("g"):
                this.points = 2;
                break;
            case("f"), ("h"), ("v"), ("w"), ("y"):
                this.points = 4;
                break;
            case("j"), ("x"):
                this.points = 8;
                break;
            case("k"):
                this.points = 5;
                break;
            case("q"), ("z"):
                this.points = 10;
                break;
            case("blank"):
                this.points = 0;
                break;
        }
    }

    public String getID(){
        return this.letter;
    }

    public int getPoints(){
        return this.points;
    }

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
