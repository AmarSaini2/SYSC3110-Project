import java.io.Serializable;
public class Coordinate implements Serializable {
    public int row;
    public int col;

    Coordinate(int x, int y){
        this.row = x;
        this.col = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate other = (Coordinate) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode(){
        return 31 * this.row * this.col;
    }
}
