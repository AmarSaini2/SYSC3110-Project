import java.util.*;
public class Coordinate{
    int row;
    int col;

    Coordinate(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj){ //check if the two references are to the same spot in memory
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){ //rejects null/not coordinate objects
            return false;
        }
        Coordinate that = (Coordinate) obj; //lets two instances of identical coordinate values to be considered equal
        return row == that.row && col == that.col;

    }


    @Override
    public int hashCode(){
        return Objects.hash(row,col);
    }
}
