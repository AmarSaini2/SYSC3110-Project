import java.util.*;
/**
 * The Wordbag class represents the tile bag in a Scrabble game.
 * It contains a set of letter tiles that players can draw from throughout the game.
 *
 * The Wordbag class is responsible for initializing the bag with a predefined distribuition of letter tiles,
 * allowing players to draw random tiles, keeping track of the remaining tiles in the bag.
 *
 * At the beginning of the game, the bag is filled with a standard set of tiles according to Scrabble rules,
 * with each letter having a specific count and point value.
 */
public class Wordbag {
    private List<Tile> tiles;
    /**
     * Constructs a new Wordbag and fills it with the standard set of Scrabble tiles.
     */
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
        addTiles(" ", 2);


    }
    /**
     * Helper method to fill the tile bag with the tiles and their frequencies.
     *
     * @param letter the letter to be put in bag
     * @param frequency the frequency of the letter in the bag
     */
    public void addTiles(String letter,int frequency){
        for(int i=0; i< frequency;i++){
            tiles.add(new Tile(letter));
        }
    }
    /**
     * Return a random tile from the wordbag.
     *
     * @return the random tile from the bag
     */
    public Tile get(){
        Random random = new Random();
        Tile randomTile = tiles.get(random.nextInt(tiles.size()));
        tiles.remove(randomTile);
        return randomTile;
    }
    /**
     * Return the size of bag.
     *
     * @return amount of tiles in the bag
     */
    public int getBagSize(){
        return tiles.size();
    }
    /**
     * Checks if the bag is empty.
     *
     * @return true, if there is no more tiles. false, otherwise.
     */
    public boolean isEmpty(){
        return tiles.isEmpty();
    }
    /**
     * Draws a random tile from the bag and returns the tile.
     * It will remove the tile randomly picked to prevent it being picked again.
     *
     * @return random tiled drawn
     */
    public Tile drawTile(){
        if(tiles.isEmpty()){
            return null;
        }

        int randomInt = (int)(Math.random() * tiles.size());
        return tiles.remove(randomInt);
    }
    public Tile drawTileAI(){
        if(tiles.isEmpty()){
            return null;
        }

        int randomInt = (int)(Math.random() * tiles.size());
        Tile tile = tiles.get(randomInt);
        while(tile.getID().equals(" ")){
            randomInt = (int)(Math.random() * tiles.size());
        }
        return tiles.remove(randomInt);


    }
}
