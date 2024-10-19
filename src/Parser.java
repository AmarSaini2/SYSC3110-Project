import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Parser {
    private ValidWords words;
    private Scanner reader;
    public Parser(){
        try {
            words = new ValidWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reader = new Scanner(System.in);
    }
    public String getWord(){


        System.out.print("> ");
        String inputLine = reader.nextLine().trim();
        Scanner tokenizer = new Scanner(inputLine);
        if(!tokenizer.hasNext()){
            return null;
        }

        return tokenizer.next().trim().toLowerCase();
    }
}
