public class Square {
    private Tile tile;
    private boolean filled;
    public Square(){
        this.tile = null;
        this.filled = false;
    }
    public void fillSquare(Tile tile){
        if(filled){
            return;
        }
        this.tile = tile;
        this.filled = true;

    }
    public Tile getTile(){
        return tile;
    }
    public boolean isFilled(){
        return filled;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(!filled){
            sb.append("-");
            return sb.toString();
        }
        sb.append(tile.getLetter());
        return sb.toString();
    }
}
