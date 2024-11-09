import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ScrabbleView extends JFrame {
    Game model;
    ScrabbleController SC;
    private boolean firstTile = true;
    private Player currentPLayer;
    JButton[] playerRack;
    JButton[][] board;
    JTextArea messageArea;
    JButton passTurn;
    JButton playTurn;
    JButton[] numPlayers;
    JButton submit;
    JButton resetRack;
    JButton passWord;
    JPanel mainPanel;
    JScrollPane buttonPanel;
    JPanel inputPanel;
    private int CENTER = 7;
    private static final int SIZE = 15;

    private Board playerTempBoard;
    JPanel rack;
    ArrayList<Tile> playerHand;

    public ScrabbleView(){
        super("Scrabble");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE,SIZE));
        board = new JButton[SIZE][SIZE];

        JPanel rowLabel = new JPanel();
        rowLabel.setLayout(new GridLayout(SIZE,1));
        for(int row = 1; row <= SIZE; row++){
            rowLabel.add(new JLabel(String.valueOf(row)));
        }
        JPanel colLabel = new JPanel();
        colLabel.setLayout(new GridLayout(1,SIZE+1));
        colLabel.add(new JLabel(""));
        for(int col = 1; col <= SIZE; col++){
            colLabel.add(new JLabel(String.valueOf(col)));
        }


        model = new Game();
        model.addView(this);
        SC = new ScrabbleController(model, this);
        setMenu(this,SC);
        JButton button;
        for(int row = 0; row<SIZE;row++){
            for(int col = 0;col < SIZE; col++){
                /*if(row == CENTER && col == CENTER){
                    ImageIcon icon = new ImageIcon("star.png");
                    Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(img);
                    button = new JButton(icon);

                }else{
                    button = new JButton("");
                }*/
                button = new JButton("");
                button.setEnabled(false);
                button.setBackground(Color.ORANGE);
                button.setForeground(Color.white);
                button.setActionCommand(row+" "+col);
                button.addActionListener(SC);
                board[row][col]= button;
                board[row][col].setPreferredSize(new Dimension(40, 40));
                boardPanel.add(button);
            }
        }

        messageArea = new JTextArea(5,30);
        messageArea.setFont(new Font("Arial",Font.PLAIN,12));


        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        gameStartFrame();

        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(scrollPane, BorderLayout.NORTH);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);


        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(colLabel, BorderLayout.NORTH);
        mainPanel.add(boardPanel,BorderLayout.CENTER);
        mainPanel.add(rowLabel,BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        startGame("GAMESTART");
        setVisible(true);

    }
    private void setMenu(JFrame frame, ScrabbleController control){
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Game Options");
        menuBar.add(fileMenu);
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setActionCommand("NEW");
        newGame.addActionListener(control);
        fileMenu.add(newGame);
        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.setActionCommand("EXIT");
        exitGame.addActionListener(control);
        fileMenu.add(exitGame);


    }
    public  void updateMessageArea(String message){
        messageArea.append(message + "\n");
    }
    public void clearMessageArea(){
        messageArea.setText("");
    }
    private void startGame(String section){
        updateButtonVisibility(section);
        String startGame = model.startGame();
        updateMessageArea(startGame);


    }

    public void updateButtonVisibility(String section){
        switch (section){
            case "GAMESTART":
                for(JButton button: numPlayers){
                    button.setVisible(true);
                }
                break;
            case "NAMEINPUT":
                for(JButton button:numPlayers){
                    button.setVisible(false);
                }
                break;
            case "PLAYERTURN":
                playTurn.setVisible(true);
                passTurn.setVisible(true);
                for(JButton button: playerRack){
                    button.setEnabled(false);
                    button.setVisible(true);
                }
                submit.setVisible(false);
                resetRack.setVisible(false);
                passWord.setVisible(false);
                break;
            case "PLAYTURN":
                playTurn.setVisible(false);
                passTurn.setVisible(false);
                for(JButton button: playerRack){
                    button.setEnabled(true);
                }
                submit.setVisible(true);
                submit.setEnabled(true);
                resetRack.setVisible(true);
                resetRack.setEnabled(true);
                passWord.setVisible(true);
                passWord.setEnabled(true);
                break;
            case "ENDGAME":
                playTurn.setVisible(false);
                passTurn.setVisible(false);
                for(JButton button: playerRack){
                    button.setEnabled(false);
                    button.setVisible(false);
                }
                submit.setVisible(false);
                resetRack.setVisible(false);
                passWord.setVisible(false);
                for(int i =0;i<SIZE;i++){
                    for(int j = 0; j<SIZE;j++){
                        board[i][j].setEnabled(false);
                    }

                }
                break;


        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void gameStartFrame(){
        numPlayers = new JButton[3];
        for(int i = 0; i<3; i++){
            JButton button = new JButton(""+(i+2));
            button.setActionCommand("Players"+(i+2));
            button.addActionListener(SC);
            numPlayers[i] = button;
        }
        JPanel numPlayerPanel = new JPanel();
        for(JButton button: numPlayers){
            numPlayerPanel.add(button);
        }
        buttonPanel = new JScrollPane(numPlayerPanel);
    }
    public void playerNameInput(int numPlayers){
        for(int i = 0; i < numPlayers; i++){
            String name = JOptionPane.showInputDialog(null,"Enter name for Player "+ (i +1)+ ":", "Player Name", JOptionPane.PLAIN_MESSAGE);
            if(name.isEmpty()){
                name = "Player " + (i+1);
            }
            model.intializePlayer(name);
        }

        clearMessageArea();
        playerTurn("PLAYERTURN");
    }
    public void playerTurn(String section){
        String play = model.playersTurn();
        playTurn = new JButton("Play a word");
        playTurn.setActionCommand("PLAY");
        playTurn.addActionListener(SC);
        passTurn = new JButton("Pass Turn");
        passTurn.setActionCommand("PASS");
        passTurn.addActionListener(SC);
        JPanel playPass = new JPanel();
        playPass.add(playTurn);
        playPass.add(passTurn);
        inputPanel.remove(buttonPanel);
        buttonPanel = new JScrollPane(playPass);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        setPlayerRack();
        updateMessageArea(play);
        updateButtonVisibility(section);
    }
    public void setPlayerRack(){
        clearRack();

        playerHand = model.playerRack();
        playerRack = new JButton[playerHand.size()];
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        for(int i = 0; i<playerHand.size();i++){
            JButton tile = new JButton(""+playerHand.get(i).getID());
            tile.setActionCommand("LETTER");
            tile.addActionListener(SC);

            tile.setEnabled(false);
            playerRack[i] = tile;
            buttons.add(tile);
        }
        rack = new JPanel();
        rack.setLayout(new BorderLayout());

        resetRack = new JButton("Reset Rack");
        resetRack.setActionCommand("RESET");
        resetRack.addActionListener(SC);
        buttons.add(resetRack);
        submit = new JButton("Submit Word");
        submit.setActionCommand("SUBMIT");
        submit.addActionListener(SC);
        buttons.add(submit);
        passWord = new JButton("Pass");
        passWord.setActionCommand("PASSTURN");
        passWord.addActionListener(SC);
        buttons.add(passWord);


        rack.add(buttons, BorderLayout.CENTER);
        JLabel rackName = new JLabel("Rack");
        rack.add(rackName,BorderLayout.NORTH);

        inputPanel.add(rack, BorderLayout.CENTER);
        inputPanel.revalidate();
        inputPanel.repaint();


    }
    public void clearRack(){
        if (playerRack != null) {
            for (JButton button : playerRack) {
                //System.out.println(button.getText());
                inputPanel.remove(button); // Remove the old buttons from the input panel
            }
            if(submit != null){
                inputPanel.remove(submit);
            }
            if(resetRack!= null){
                inputPanel.remove(resetRack);
            }
            if(passWord !=null){
                inputPanel.remove(passWord);
            }
            inputPanel.remove(rack);

        }
    }
    public void playTurn(){


        playerTempBoard = new Board();

        updateMessageArea("\n" + model.getPlayer().getName() + "'s turn:\n");
        updateMessageArea("Choose a letter to place:");
        updateButtonVisibility("PLAYTURN");
    }
    private void enableAllTiles(){
        for(int row = 0; row<SIZE;row++){
            for(int col =0; col<SIZE;col++){
                board[row][col].setEnabled(true);
            }
        }
    }
    public void placeStartLetter(String letter){
        board[CENTER][CENTER].setEnabled(false);
        board[CENTER][CENTER].setText(letter);
        board[CENTER][CENTER].setBackground(Color.pink);
        //model.setRowCol(CENTER,CENTER);
        model.updateTempBoard(CENTER,CENTER,letter);
        playerTempBoard.placeLetter(CENTER,CENTER,letter);
        updateMessageArea("Pick next letter to play!");
        firstTile = false;
    }
    private void placeSubsequentTiles(int row, int col, String letter){
        model.setRowCol(-1,-1);

        board[row][col].setEnabled(false);
        board[row][col].setText(letter);
        board[row][col].setBackground(Color.pink);
        model.updateTempBoard(row,col,letter);
        playerTempBoard.placeLetter(row,col,letter);
    }
    public void updateBoard(int row, int col,String letter){



        if(firstTile){
            enableAllTiles();
            placeStartLetter(letter);


        }else{
            placeSubsequentTiles(row, col, letter);
        }
    }
    public void resetBoard(ArrayList<Tile> hand, int numberofplays){
        for(Tile tile: hand){
            removeTileFromBoard(tile);
        }
        if(!board[CENTER][CENTER].getText().equals("") && numberofplays ==0){
            board[CENTER][CENTER].setText("");
            firstTile = true;
        }

    }
    private void removeTileFromBoard(Tile tile){
        for(int row = 0; row<SIZE;row++){
            for(int col =0; col<SIZE;col++){
                String boardTileId = board[row][col].getText();
                if(boardTileId.equals(tile.getID())){
                    String removeTileId = playerTempBoard.removeLetter(row,col);
                    if(removeTileId.equals(tile.getID())){
                        board[row][col].setText(" ");
                        board[row][col].setEnabled(true);
                        board[row][col].setBackground(Color.ORANGE);
                    }
                }


            }
        }
    }
    public void endofGameFrame(String section){
        updateButtonVisibility(section);
        updateMessageArea(model.handleEndOfGame());


    }
    public void updateRackPlay(ArrayList<JButton> buttons, boolean enable){
        if(!enable){
            for(JButton button: playerRack){
                button.setEnabled(false);
            }
        }else{
            for(JButton button:playerRack){
                if(!buttons.contains(button)){
                    button.setEnabled(true);
                }
            }
        }
    }

    public static void main(String[] args) {


        new ScrabbleView();



    }

}
