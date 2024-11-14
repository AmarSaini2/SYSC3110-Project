import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
/**
 * The ScrabbleController class represents the Controller component of the MVC pattern for the Scrabble game.
 * It is responsible for handling the user interactions with ScrabbleView, such as managing player interactions, and
 * forwarding these interactions to the game model.
 *
 * The ScrabbleController acts as an intermediary between the view and the model. It listens to user actions from the
 * view, processes these actions, and updates the model and view accordingly.
 *
 */
public class ScrabbleController implements ActionListener {
    Game model;
    private ScrabbleView currentView;
    private Tile tilePlaced;
    private ArrayList<Tile> hand;
    private boolean firstTurn = true;
    private JButton lastClicked;
    private int playerPoints;
    private int play;
    private ArrayList<JButton> selectedButtons;
    /**
     * Constructs a new ScrabbleController with the specified model and view.
     *
     * @param model the game model that stores the game state and logic
     * @param view the game view that displays the game to the players
     */
    public ScrabbleController(Game model, ScrabbleView view){
        this.model = model;
        currentView = view;
        play = 0;

    }
    /**
     * Helper method to determine the button pressed on the board.
     *
     * @param str the string action command
     * @return true, if action command has numbers. false, if not
     */
    private boolean isNumeric(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * ActionListener for buttons pressed on the board.
     * It checks if it is a button on the board, else it handles it based
     * on the action command set to it.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        String[] parts = command.split(" ");

        if(parts.length == 2 && isNumeric(parts[0])){
            handleLetterPlace(e);
            return;
        }else{


            switch(command){

                case "NEW":
                    startNewGame(e);
                    break;
                case "EXIT":
                    exitGame(e);
                    break;
                case "Players2":
                    handleNumPlayers(e,2);
                    break;
                case "Players3":
                    handleNumPlayers(e,3);
                    break;
                case "Players4":
                    handleNumPlayers(e,4);
                    break;
                case "PLAY":
                    playTurn(e);
                    break;
                case "PASS":
                    passTurn(e);
                    break;
                case "PASSTURN":
                    passTurnIn(e);
                    break;
                case "LETTER":
                    playingTiles(e);
                    break;
                case "SUBMIT":
                    submitWord(e);
                    break;
                case "RESET":
                    resetRack(e);
                    break;
            }
        }
    }
    /**
     * Handles placing a letter on the ScrabbleView board.
     *
     * @param e the event to be processed
     */
    public void handleLetterPlace(ActionEvent e){
        JButton clicked = (JButton) e.getSource();
        String[] parts = e.getActionCommand().split(" ");

        if(tilePlaced == null){
            currentView.updateMessageArea("You need to pick a letter before placing!");
            return;
        }
        try {

            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);


            currentView.updateBoard(row, col, tilePlaced.getID());
            clicked.setText(tilePlaced.getID());
            clicked.setEnabled(false);


            tilePlaced = null;
            currentView.updateRackPlay(selectedButtons,true);

        } catch (NumberFormatException ex) {
            currentView.updateMessageArea("Invalid row or column format!");
        }

    }
    /**
     * When a user presses the menu option to start a new game, a new view is made.
     *
     * @param e the event to be processed
     */
    public void startNewGame(ActionEvent e){
        currentView.dispose();
        currentView = new ScrabbleView();

    }
    /**
     * When a user presses the menu option to exit the game, it will close the current game.
     *
     * @param e the event to be processed
     */
    public void exitGame(ActionEvent e){
        System.exit(0);
    }
    /**
     * Handles the number of players inputted and updates the view to prompt for names.
     *
     * @param e the action to be processed
     * @param numPlayers the number of players
     */
    public void handleNumPlayers(ActionEvent e, int numPlayers){
        currentView.updateButtonVisibility("NAMEINPUT");
        currentView.playerNameInput(numPlayers);
    }
    /**
     * Handles when a player chooses to play and communicates with the model to
     * begin the players turn.
     *
     * @param e the action to be processed
     */
    public void playTurn(ActionEvent e){
        model.handlePlayerChoice(1);
        hand = new ArrayList<>();
        selectedButtons = new ArrayList<>();
        
    }
    /**
     * Handles when a player chooses to pass their turn and communicates with the model
     * and view to update the board accordingly
     *
     * @param e the action to be processed
     */
    public void passTurn(ActionEvent e){
        model.handlePlayerChoice(2);
        if (model.isGameOver()){
            currentView.endofGameFrame("ENDGAME");
            return;
        }



        currentView.updateMessageArea("Turn Passed!");
        currentView.updateMessageArea("Next Player's Turn");

        currentView.playerTurn("PLAYERTURN");

    }
    /**
     * Handles when a player chooses to pass their turn after they have hit play.
     * It communicates with the view to reset the conditions to before the current player started
     * playing.
     * @param e the action to be processed
     */
    public void passTurnIn(ActionEvent e){
        model.handlePlayerChoice(2);
        if (model.isGameOver()){
            currentView.endofGameFrame("ENDGAME");
            return;
        }
        currentView.resetBoard(hand, play);
        hand = new ArrayList<>();
        tilePlaced = null;
        currentView.updateMessageArea("Turn Passed!");
        currentView.updateMessageArea("Next Player's Turn");

        currentView.playerTurn("PLAYERTURN");
    }
    /**
     * Handles when a player clicks on a tile on their rack.
     *
     * @param e the action to be processed
     */
    public void playingTiles(ActionEvent e){
        JButton tile= (JButton) e.getSource();
        lastClicked = tile;
        tile.setEnabled(false);
        String letter;
        if(tile.getText().equals(" ")){//blank tile clicked
            while(true){
                letter = JOptionPane.showInputDialog("What letter would you like to place as your blank tile?");
                if(letter.length() == 1){
                    tilePlaced = new Tile(letter.toUpperCase(), 0);
                    break;
                }
            }
        }else{
            letter = tile.getText();
            tilePlaced = new Tile(letter);
        }

        model.tilePlaced(tilePlaced);
        hand.add(tilePlaced);
        //playerPoints += tilePlaced.getPoints();
        if(firstTurn){

            tilePlaced = null;
            currentView.updateBoard(0,0,lastClicked.getText());
            selectedButtons.add(tile);
            firstTurn = false;

        }else{
            selectedButtons.add(tile);
            currentView.updateRackPlay(selectedButtons,false);
            currentView.disableAppropriateTile();
        }




    }
    /**
     * Handles when a player clicks on "Submit Word" and will communicate with the model
     * to check if word formed is valid and will handle the conditions if not.
     *
     * @param e the action to be processed
     */
    public void submitWord(ActionEvent e){
        JButton submit = (JButton) e.getSource();
        submit.setEnabled(false);
        submit.setBackground(Color.pink);
        play++;

        if((hand.size() > 1)&& firstTurn){
            if(model.submitWord(model.getPlayer())){
                //model.getPlayer().addPoints(playerPoints);
                model.getPlayer().addPoints(model.board.calculatePoints());
                currentView.updateMessageArea("Turn Over, Word Successfully Placed");
                currentView.updateMessageArea("Player "+model.getPlayer().getName()+" has "+ model.getPlayer().getPoints()+ " points!");
                currentView.updateMessageArea("Next Player's turn");
                playerPoints= 0;
            }else{
                restartTurn();
                return;
            }
        }else if (!firstTurn){
            if(model.submitWord(model.getPlayer())){
                //model.getPlayer().addPoints(playerPoints);
                currentView.updateMessageArea("Turn Over, Word Successfully Placed");
                currentView.updateMessageArea("Player "+model.getPlayer().getName()+" has "+ model.getPlayer().getPoints()+ " points!");
                currentView.updateMessageArea("Next Player's turn");
                playerPoints= 0;
            }else{
                restartTurn();
                return;
            }
        }else{
            restartTurn();
            return;
            /*
            currentView.resetBoard(hand, play);
            currentView.updateMessageArea("Turn over! Invalid word!");
            currentView.updateMessageArea("Player " + model.getPlayer().getName() + " has "+ model.getPlayer().getPoints()+ " points!");
            currentView.updateMessageArea("Next Player's turn!");
            playerPoints = 0;
            */
        }

        model.updatePlayerIndex();

        if(model.isGameOver()){
            currentView.endofGameFrame("ENDGAME");
            return;
        }
        currentView.updatePlayerChart();
        currentView.updateButtonVisibility("TURNOVER");
        currentView.playerTurn("PLAYERTURN");


    }
    /**
     * Helper method for submitWord, handles resetting the board after an invalid word
     * is played.
     */
    public void restartTurn(){
        currentView.resetBoard(hand, play);
        model.updatePlayerIndex();
        currentView.updateButtonVisibility("TURNOVER");
        currentView.playerTurn("PLAYERTURN");
        model.board.clearPlacedTileList();
        tilePlaced = null;
        hand = new ArrayList<>();
        selectedButtons = new ArrayList<>();
    }
    /**
     * Handles when a player clicks on resetting their rack after starting to play.
     * It will communicate with the model and view to adjust to before the current player start placing tiles.
     *
     * @param e the action to be processed
     */
    public void resetRack(ActionEvent e){
        JButton reset = (JButton) e.getSource();
        reset.setEnabled(false);
        if(!firstTurn && play == 0){
            firstTurn = true;
        }
        for(JButton button: selectedButtons){
            Tile tile = new Tile(button.getText());
            button.setEnabled(true);
            playerPoints -= tile.getPoints();
        }
        currentView.updateButtonVisibility("RESTART");
        currentView.resetBoard(hand,play);
        hand=new ArrayList<>();
        tilePlaced = null;
        selectedButtons = new ArrayList<>();
        model.board.clearPlacedTileList();
        currentView.updateButtonVisibility("TURNOVER");

    }


}
