import java.util.*;

public class Wordbag {
    private ArrayList<Tile> tiles;

    Wordbag(){
        List<String> tilenames = List.of("blank","blank","a","a","a","a","a","a","a","a","a","b","b","c","c","d","d","d","d","e","e","e","e","e","e","e","e","e","e","e","e","f","f","g","g","g","h","h","i","i","i","i","i","i","i","i","i","j","k","l","l","l","l","m","m","n","n","n","n","n","n","o","o","o","o","o","o","o","o","p","p","q","r","r","r","r","r","r","s","s","s","s","t","t","t","t","t","t","u","u","u","u","v","v","w","w","x","y","y","z");
        tiles = new ArrayList<Tile>();
        for(String name: tilenames){
            tiles.add(new Tile(name));
        }
    }

    public String listTiles(){//group tiles together and print x number of each tile
        String returnString = "Available Letters: \n";
        Map<String, Integer> tileCounts = new HashMap<>();

        for(Tile tile: tiles){
            String name = tile.getID();
            tileCounts.put(name, tileCounts.getOrDefault(name, 0) + 1);
        }

        for(Map.Entry<String, Integer> entry: tileCounts.entrySet()){
            returnString += entry.getValue() + "->" + entry.getKey() + "\t";
        }

        return returnString;
    }

    public Tile get(){
        Random random = new Random();
        Tile randomTile = tiles.get(random.nextInt(tiles.size()));
        tiles.remove(randomTile);
        return randomTile;
    }

    public boolean isEmpty(){
        return tiles.isEmpty();
    }

}
