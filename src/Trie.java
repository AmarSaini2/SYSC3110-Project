import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Trie {

    private Node root;
    private Node current;

    Trie(){
        root = new Node('0', null);
        current = root;
        try {
            File input = new File("src/wordDict.txt");
            Scanner reader = new Scanner(input);
            while(reader.hasNextLine()){
                String data = reader.nextLine().toLowerCase();
                this.addString(data);
            }
        }catch(FileNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void addString(String input) {
        input = input.toLowerCase();
        char[] charArray = input.toCharArray();
        current = root;
        for (char c : charArray){
            //if current letter is a child of current node, move current node pointer to current letter and move on
            if(!current.getChildrenLetters().contains(c)){
                current.addChild(new Node(c, current));
            }
            current = current.returnChild(c);//move pointer forward if the letter has already been added/just got added above
        }
        current.setTerminal();//set last letter to terminal to signify end of string
    }

    public boolean hasWord(String s){
        current = root;
        s = s.toLowerCase();
        char[] charArray = s.toCharArray();
        for(char c: charArray){
            if(current.getChildrenLetters().contains(c)){
                current = current.returnChild(c);
            }else{
                return false;
            }
        }
        if(current.isTerminal()){
            return true;//returns if the word is found and is a full word, not just a substring of a word
        }
        return false;
    }

    public Set nextLetters(String input){//find the next possible letters given a prefix string
        input = input.toLowerCase();
        char[] charArray = input.toCharArray();
        current = root;
        for(char c: charArray){
            if(current.getChildrenLetters().contains(c)){//iterate letter by letter to the end of the string
                current = current.returnChild(c);
            }else{
                return null;//word is not in the dictionary
            }
        }
        return current.getChildrenLetters();
    }

    public Set prevLetters(String input){//find the previous possible letters given a suffix string
        input = input.toLowerCase();
        char[] charArray = input.toCharArray();
        current = root;
        Set<Character> result = new HashSet<>();
        for(Node child: current.getChildrenNodes()){
            depthFirstSearch(charArray, 0, child, result, null);
        }
        return result;
    }

    private void depthFirstSearch(char[] charArray, int index, Node node, Set result, Node beginningOfWord){
        //base case -> all characters matched
        if(index == (charArray.length - 1) && node.getChar() == charArray[index]){
            if(node.isTerminal()){//complete word found
                if(beginningOfWord.parent != root && beginningOfWord != null){
                    result.add(beginningOfWord.parent.getChar());//add parent char to list
                }
            }
            return;
        }

        if(node.getChar() == charArray[index]){//node matches current placement in chain
            if(index == 0){
                beginningOfWord = node;//set beginningofWord node
            }
            for(Node child : node.getChildrenNodes()){
                depthFirstSearch(charArray, index + 1, child, result, beginningOfWord);
            }
        }else{
            if(!node.getChildrenNodes().isEmpty()){//node doesnt match chain but has children
                for(Node child: node.getChildrenNodes()){
                    depthFirstSearch(charArray, 0, child, result, beginningOfWord);
                }
            }
        }



    }


        private static class Node {
            private boolean isTerminal;
            private char c;
            private HashMap<Character, Node> children = new HashMap<>();

            public Node parent;

            Node(char c, Node parent) {
                this.c = c;
                this.parent = parent;
            }

            public char getChar(){
                return this.c;
            }

            public void setTerminal() {
                this.isTerminal = true;
            }

            public boolean isTerminal(){
                return this.isTerminal;
            }

            public Node returnChild(char c) {
                return children.get(c);
            }

            public Set<Character> getChildrenLetters(){
                return this.children.keySet();
            }

            public Collection<Node> getChildrenNodes(){
                return this.children.values();
            }

            public void addChild(Node child) {
                this.children.put(child.c, child);
            }

            public Node getParent(){
                return this.parent;
            }

        }
    }