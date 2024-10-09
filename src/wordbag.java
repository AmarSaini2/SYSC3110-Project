import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class wordbag {
    private ArrayList<tile> tiles;

    wordbag(){
        List<String> tilenames = List.of("blank","blank","a","a","a","a","a","a","a","a","a","b","b","c","c","d","d","d","d","e","e","e","e","e","e","e","e","e","e","e","e","f","f","g","g","g","h","h","i","i","i","i","i","i","i","i","i","j","k","l","l","l","l","m","m","n","n","n","n","n","n","o","o","o","o","o","o","o","o","p","p","q","r","r","r","r","r","r","s","s","s","s","t","t","t","t","t","t","u","u","u","u","v","v","w","w","x","y","y","z");
        tiles = new ArrayList<tile>();
        for(String name: tilenames){
            tiles.add(new tile(name));
        }
    }

    public String listTiles(){//group tiles together and print x number of each tile
        String returnString = "Available Letters: \n";
        Map<String, Integer> tileCounts = new HashMap<>();

        for(tile tile: tiles){
            String name = tile.getID();
            tileCounts.put(name, tileCounts.getOrDefault(name, 0) + 1);
        }

        for(Map.Entry<String, Integer> entry: tileCounts.entrySet()){
            returnString += entry.getValue() + "->" + entry.getKey() + "\t";
        }

        return returnString;
    }

    public tile getTile(){
        Random random = new Random();
        tile randomTile = tiles.get(random.nextInt(tiles.size()));
        tiles.remove(randomTile);
        return randomTile;
    }

}
