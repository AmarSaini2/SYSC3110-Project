public class Tile {
    private String letter;
    private int points;

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
