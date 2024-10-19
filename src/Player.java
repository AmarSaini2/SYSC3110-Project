import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Tile> tiles;
    private int playerScore;
    private final int NUM_TILES = 7;
    private String name;

    public Player(Tilebag tileBag,String name){
        tiles = new ArrayList<>();
        for(int i =0;i <NUM_TILES; i++){
            tiles.add(tileBag.drawTile());
        }
        playerScore = 0;
        this.name = name;

    }
    public int getPlayerScore(){
        return playerScore;
    }
    public void updatePlayerScore(int score){
        playerScore += score;
    }
    public List<Tile> getTiles(){
        return tiles;
    }
    public void removeTile(Tile tile){
        tiles.remove(tile);
    }
    public void drawNewTiles(Tilebag tilebag){
        int replace = NUM_TILES - tiles.size();
        if(tilebag.getBagSize()< replace && tilebag.getBagSize() > 0){
            System.out.println("Only had +"+tilebag.getBagSize()+" , these are the remaining tiles.");
            for(int i =0; i< tilebag.getBagSize();i++){
                tiles.add(tilebag.drawTile());
            }
            return;
        }else if(tilebag.getBagSize()==0){
            System.out.println("All tiles have been drawn.");
            return;
        }
        for(int i =0; i<replace;i++){
            tiles.add(tilebag.drawTile());
        }
    }
    public String getName(){
        return name;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Rack: ");
        for(Tile tile: tiles){
            sb.append(tile.getLetter());

        }
        return sb.toString();
    }
}
