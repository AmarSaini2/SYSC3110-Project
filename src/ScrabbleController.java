import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public ScrabbleController(Game model, ScrabbleView view){
        this.model = model;
        currentView = view;
        play = 0;

    }
    private boolean isNumeric(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
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

    public void startNewGame(ActionEvent e){
        currentView.dispose();
        currentView = new ScrabbleView();

    }
    public void exitGame(ActionEvent e){
        System.exit(0);
    }
    public void handleNumPlayers(ActionEvent e, int numPlayers){
        currentView.updateButtonVisibility("NAMEINPUT");
        currentView.playerNameInput(numPlayers);
    }
    public void playTurn(ActionEvent e){
        model.handlePlayerChoice(1);
        hand = new ArrayList<>();
        selectedButtons = new ArrayList<>();
    }
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
    public void passTurnIn(ActionEvent e){
        model.handlePlayerChoice(2);
        if (model.isGameOver()){
            currentView.endofGameFrame("ENDGAME");
            return;
        }
        currentView.resetBoard(hand, play);
        hand = new ArrayList<>();
        currentView.updateMessageArea("Turn Passed!");
        currentView.updateMessageArea("Next Player's Turn");

        currentView.playerTurn("PLAYERTURN");
    }
    public void playingTiles(ActionEvent e){
        JButton tile= (JButton) e.getSource();
        lastClicked = tile;
        tile.setEnabled(false);
        String letter = tile.getText();
        tilePlaced = new Tile(letter);
        model.tilePlaced(tilePlaced);
        hand.add(tilePlaced);
        playerPoints += tilePlaced.getPoints();
        if(firstTurn){
            firstTurn = false;
            tilePlaced = null;
            currentView.updateBoard(0,0,lastClicked.getText());
            selectedButtons.add(tile);

        }else{
            selectedButtons.add(tile);
            currentView.updateRackPlay(selectedButtons,false);
        }



    }
    public void submitWord(ActionEvent e){
        JButton submit = (JButton) e.getSource();
        submit.setEnabled(false);
        submit.setBackground(Color.pink);
        play++;
        if(model.submitWord(model.getPlayer())){
            model.getPlayer().addPoints(playerPoints);
            currentView.updateMessageArea("Turn Over, Word Successfully Placed");
            currentView.updateMessageArea("Player "+model.getPlayer().getName()+" has "+ model.getPlayer().getPoints()+ " points!");
            currentView.updateMessageArea("Next Player's turn");
            playerPoints= 0;
        }
        model.updatePlayerIndex();
        if(model.isGameOver()){
            currentView.endofGameFrame("ENDGAME");
            return;
        }
        currentView.playerTurn("PLAYERTURN");


    }
    public void resetRack(ActionEvent e){
        JButton reset = (JButton) e.getSource();
        reset.setEnabled(false);
        Tile tile = new Tile(lastClicked.getText());
        if(lastClicked != null){
            lastClicked.setEnabled(true);
            playerPoints -= tile.getPoints();
            currentView.resetBoard(hand,play);
            hand= new ArrayList<>();
        }

    }


}
