import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 * The ScrabbleView class represents the View component of the MVC pattern for the Scrabble game.
 * It is responsible for displaying the game board, player racks, option menu, and other game elements
 * in a graphical user interface (GUI).
 *
 * The ScrabbleView class does not handle game logic directly; instead, it updates the UI in response to changes in the
 * game model and communicates user actions to the controller.
 *
 */
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
    JTable scorePanel;
    DefaultTableModel tableModel;
    private int CENTER = 7;
    private static final int SIZE = 15;

    //private Board playerTempBoard;
    JPanel rack;
    ArrayList<Tile> playerHand;
    /**
     * Constructs a new ScrabbleView and initializes the layout, including the game board,
     * tile rack, score display, and other interactive components.
     */
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
        String[] columnNames = {"Player","Score"};
        tableModel = new DefaultTableModel(columnNames,0);
        scorePanel = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(scorePanel);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(400, 150));
        infoPanel.add(scrollPane, BorderLayout.WEST);
        infoPanel.add(tableScrollPane, BorderLayout.EAST);
        gameStartFrame();

        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(infoPanel, BorderLayout.NORTH);
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
    /**
     * Create menu bar, menu and menu items for game options.
     * Two options: Create new game and Exit game.
     *
     * @param frame
     *
     */
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
    /**
     * Update the message area with current game status information.
     *
     * @param message the updated message
     */
    public  void updateMessageArea(String message){
        messageArea.append(message + "\n");
    }
    /**
     * Clear the message area.
     */
    public void clearMessageArea(){
        messageArea.setText("");
    }
    /**
     * Start the game through the model, and update the visibility of the buttons and message area.
     *
     * @param section The section to be updated for the buttons.
     */
    private void startGame(String section){
        updateButtonVisibility(section);
        String startGame = model.startGame();
        updateMessageArea(startGame);


    }
    /**
     * Update the visibility of buttons depending on the state of the game.
     *
     * @param section The section of the game to be updated to.
     */
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
    /**
     * Starting game frame to initialize amount of players.
     */
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
    /**
     * Obtain the player names for the game.
     *
     * @param numPlayers the number of players wanting to play.
     */
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
    /**
     * Handles user interface for a player's turn and interacts with the model.
     *
     * @param section the section to update button status
     */
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
    /**
     * Set up the player rack of buttons for the user to interact with.
     */
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
    /**
     * Clear the rack after a player's turn and reset it.
     */
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
    /**
     * Updates the view to reflect the beginning of a player's turn status.
     */
    public void playTurn(){


        //playerTempBoard = new Board();

        updateMessageArea("\n" + model.getPlayer().getName() + "'s turn:\n");
        updateMessageArea("Choose a letter to place:");
        updateButtonVisibility("PLAYTURN");
    }
    /**
     * Enable all tiles on the Scrabble board.
     */
    private void enableAllTiles(){
        for(int row = 0; row<SIZE;row++){
            for(int col =0; col<SIZE;col++){
                board[row][col].setEnabled(true);
            }
        }
    }
    /**
     * Disable tiles that cannot be clicked during a player's turn when placing word on the board.
     */
    public void disableAppropriateTile(){
        //disabling all tile
        for(int row = 0; row<SIZE;row++){
            for(int col =0; col<SIZE;col++){
                board[row][col].setEnabled(false);
                board[row][col].setBackground(Color.pink);
            }
        }
        //enabling tiles that are adjacent to word tiles if they are within the index
        for(int row = 1; row<SIZE-1;row++){
            for(int col =1; col<SIZE-1;col++) {
                if (!board[row][col].getText().equals("")) {
                    board[row - 1][col].setEnabled(true);
                    board[row - 1][col].setBackground(Color.orange);
                    board[row + 1][col].setEnabled(true);
                    board[row + 1][col].setBackground(Color.orange);
                    board[row][col - 1].setEnabled(true);
                    board[row][col - 1].setBackground(Color.orange);
                    board[row][col + 1].setEnabled(true);
                    board[row][col + 1].setBackground(Color.orange);
                }
            }
            }
        //disabling all tiles with words in them in case of overlapping tile
        for(int row = 0; row<SIZE;row++){
            for(int col =0; col<SIZE;col++){
                if(!board[row][col].getText().equals("")) {
                    board[row][col].setEnabled(false);
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
    }
    /**
     * Place starting letter on the board in the middle.
     *
     * @param letter the first letter to be placed
     */
    public void placeStartLetter(String letter){
        board[CENTER][CENTER].setEnabled(false);
        board[CENTER][CENTER].setText(letter);
        board[CENTER][CENTER].setBackground(Color.pink);
        //model.setRowCol(CENTER,CENTER);
        model.updateTempBoard(CENTER,CENTER,letter);
        //playerTempBoard.placeLetter(CENTER,CENTER,letter);
        updateMessageArea("Pick next letter to play!");
        firstTile = false;
    }
    /**
     * Place the next tile(s) after the first one is placed.
     *
     * @param row the row for the tile to be placed in
     * @param col the column for the tile to be placed in
     * @param letter the letter to be placed
     */
    private void placeSubsequentTiles(int row, int col, String letter){
        model.setRowCol(-1,-1);

        board[row][col].setEnabled(false);
        board[row][col].setText(letter);
        board[row][col].setBackground(Color.pink);
        model.updateTempBoard(row,col,letter);
        //playerTempBoard.placeLetter(row,col,letter);
    }
    /**
     * Update the board according to the letter and place on the board the player wants it.
     *
     * @param row the row for the tile to be placed in
     * @param col the column for the tile to be placed in
     * @param letter the letter to be placed
     */
    public void updateBoard(int row, int col,String letter){



        if(firstTile){
            enableAllTiles();
            placeStartLetter(letter);


        }else{
            placeSubsequentTiles(row, col, letter);
        }
    }
    /**
     * Reset the board panel.
     *
     * @param hand player tiles to not be counted
     * @param numberofplays the number of plays on the board
     */
    public void resetBoard(ArrayList<Tile> hand, int numberofplays){
        for(Tile tile: hand){
            removeTileFromBoard(tile);
        }
        if(!board[CENTER][CENTER].getText().equals("") && numberofplays ==0){
            board[CENTER][CENTER].setText("");
            firstTile = true;
        }

    }
    /**
     * Remove a specific tile from the board.
     *
     * @param tile the tile to be removed
     */
    private void removeTileFromBoard(Tile tile){
        for(int row = 0; row<SIZE;row++){
            for(int col =0; col<SIZE;col++){
                String boardTileId = board[row][col].getText();
                if(boardTileId.equals(tile.getID())){
                    String removeTileId = model.getTempBoard().removeLetter(row,col);
                    if(removeTileId.equals(tile.getID())){
                        board[row][col].setText("");
                        board[row][col].setEnabled(true);
                        board[row][col].setBackground(Color.ORANGE);
                    }
                }


            }
        }
    }
    /**
     * The end of game frame to be displayed.
     *
     * @param section the section to update button visibility
     */
    public void endofGameFrame(String section){
        updateButtonVisibility(section);
        updateMessageArea(model.handleEndOfGame());


    }
     /**
     * Change the rack button visibility depending on if a player has clicked a tile.
     *
     * @param buttons the buttons to be updated
     * @param enable true to enable the buttons, false to disable
     */
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
    /**
     * Update the player score table.
     */
    public void updatePlayerChart(){
        ArrayList<Player> players = model.getPlayers();
        tableModel.setRowCount(0);
        for(Player player: players){
            tableModel.addRow(new Object[]{player.getName(),player.getPoints()});
        }
    }

    public static void main(String[] args) {


        new ScrabbleView();



    }

}
