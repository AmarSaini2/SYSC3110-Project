import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ValidWords {
    private Set<String> validWords;

   public ValidWords() throws IOException {
       validWords=new HashSet<>();
       File file = new File("words.txt");
       loadWordsFromFile(file.getAbsolutePath());
   }
    private void loadWordsFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                validWords.add(word.trim().toLowerCase());
            }
        }
    }
    public boolean isWord(String aString){

        return validWords.contains(aString);
    }

    public boolean removeWord(String word){
       return validWords.remove(word);
    }
    public boolean isEmpty(){
       if(validWords.isEmpty()){
           return true;
       }
       return false;
    }
}
