import java.util.*;

public class Wordbag {
    private List<Tile> tiles;

    public Wordbag(){
        this.tiles = new ArrayList<>();
        addTiles("A",9);
        addTiles("B",2);
        addTiles("C",2);
        addTiles("D",4);
        addTiles("E",12);
        addTiles("F",2);
        addTiles("G",3);
        addTiles("H",2);
        addTiles("I",9);
        addTiles("J",1);
        addTiles("K",1);
        addTiles("L",4);
        addTiles("M",2);
        addTiles("N",6);
        addTiles("O",8);
        addTiles("P",2);
        addTiles("Q",1);
        addTiles("R",6);
        addTiles("S",4);
        addTiles("T",6);
        addTiles("U",4);
        addTiles("V",2);
        addTiles("W",2);
        addTiles("X",1);
        addTiles("Y",2);
        addTiles("Z",1);


    }
    public void addTiles(String letter,int frequency){
        for(int i=0; i< frequency;i++){
            tiles.add(new Tile(letter));
        }
    }

    public Tile get(){
        Random random = new Random();
        Tile randomTile = tiles.get(random.nextInt(tiles.size()));
        tiles.remove(randomTile);
        return randomTile;
    }
    public int getBagSize(){
        return tiles.size();
    }
    public boolean isEmpty(){
        return tiles.isEmpty();
    }

    public Tile drawTile(){
        if(tiles.isEmpty()){
            return null;
        }

        int randomInt = (int)(Math.random() * tiles.size());
        return tiles.remove(randomInt);
    }
}
