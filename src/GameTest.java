import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;


public class GameTest {
    private Game game;
    private ScrabbleView view;
    private ScrabbleController controller;

    @BeforeEach
    public void setUp() {
        game = new Game();
        view = new ScrabbleView();
        controller = new ScrabbleController(game, view);
    }

    @Test
    public void testStartGame() {
        String expectedOutput = "Welcome to the game of scrabble!\nTo start, select a number of players: (2-4)";
        assertEquals(expectedOutput, game.startGame());
    }

    @Test
    public void testPlayerInitialization() {
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);

        assertEquals(2, game.getPlayers().size(), "The game should have 2 players.");
        assertEquals("Alice", game.getPlayers().get(0).getName());
        assertEquals("Bob", game.getPlayers().get(1).getName());
    }

    @Test
    public void testPlayersTurn() {
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);

        assertEquals("Player: Alice \n1. Play a word\n2. Pass your turn\n", game.playersTurn());
        game.handlePlayerChoice(2); // Pass Alice's turn
        assertEquals("Player: Bob \n1. Play a word\n2. Pass your turn\n", game.playersTurn());
    }


    @Test
    public void testHandlePlayerChoice_PassTurn() {
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);

        game.handlePlayerChoice(2); // Simulate "Pass turn"
        assertEquals(1, game.getConsecutivePasses(), "Consecutive passes should increment when turn is passed.");
        assertEquals("Player: Bob \n1. Play a word\n2. Pass your turn\n", game.playersTurn());
    }


    @Test
    public void testSubmitWordAndScore() {
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);
        String s = game.playersTurn();
        Player currentPlayer = game.currentPlayerTurn();

        s = game.playersTurn();
        currentPlayer = game.currentPlayerTurn();
        //checking correct condition with longer word
        game.updateTempBoard(7, 7, "R");
        game.updateTempBoard(7, 8, "O");
        game.updateTempBoard(7, 9, "B");
        game.updateTempBoard(7, 10, "O");
        game.updateTempBoard(7, 11, "T");

        assertTrue(game.submitWord(currentPlayer), "The submitted word should be valid.");
        assertEquals(game.currentPlayerTurn().getPoints(), 7);

        //adding letter to previous word
        s = game.playersTurn();
        currentPlayer = game.currentPlayerTurn();
        game.updateTempBoard(7, 12, "S");
        assertTrue(game.submitWord(currentPlayer), "The submitted word should be valid.");
        assertEquals(game.currentPlayerTurn().getPoints(), 16); //points should re add for entire word since player added on to it

    }

    @Test
    public void testValidHorizontalPlacement() {
        game.intializePlayer("Alice", true);
        String s = game.playersTurn();
        Player currentPlayer = game.currentPlayerTurn();

        game.updateTempBoard(7, 7, "C");
        game.updateTempBoard(7, 8, "A");
        game.updateTempBoard(7, 9, "T");
        assertTrue(game.submitWord(currentPlayer), "The horizontal word should be valid.");
        assertEquals(game.currentPlayerTurn().getPoints(), 5);
    }

    @Test
    public void testValidVerticalPlacement() {
        game.intializePlayer("Bob", true);
        String s = game.playersTurn();
        Player currentPlayer = game.currentPlayerTurn();

        game.updateTempBoard(7, 7, "D");
        game.updateTempBoard(8, 7, "O");
        game.updateTempBoard(9, 7, "G");
        assertTrue(game.submitWord(game.currentPlayerTurn()), "The vertical word should be valid.");
        assertEquals( game.currentPlayerTurn().getPoints(), 5);
    }


    @Test
    public void testWordIntersection() {
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);
        String s = game.playersTurn();
        Player currentPlayer = game.currentPlayerTurn();

        // Initialize the game state by invoking currentPlayerTurn
        currentPlayer = game.currentPlayerTurn();

        // Player 1 places "CAT"
        game.updateTempBoard(7, 7, "C");
        game.updateTempBoard(7, 8, "O");
        game.updateTempBoard(7, 9, "T");
        assertTrue(game.submitWord(currentPlayer), "The word 'COT' should be valid.");
        assertEquals( game.currentPlayerTurn().getPoints(),5);

        // Player 2 intersects with "DOG"
        s = game.playersTurn();
        currentPlayer = game.currentPlayerTurn();
        game.updateTempBoard(6, 8, "D"); // Intersects with 'A' in "CAT"
        game.updateTempBoard(7, 8, "O");
        game.updateTempBoard(8, 8, "G");
        assertTrue(game.submitWord(currentPlayer), "The intersecting word 'DOG' should be valid.");
        assertEquals(game.currentPlayerTurn().getPoints(), 15);
    }

    @Test
    public void testParallelWordPlacements() {
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);
        String s = game.playersTurn();
        Player currentPlayer = game.currentPlayerTurn();


        // Place the horizontal word "CAT" starting at (7,7)
        game.updateTempBoard(7, 7, "C");
        game.updateTempBoard(7, 8, "A");
        game.updateTempBoard(7, 9, "T");

        // Ensure the horizontal word "CAT" is valid
        assertTrue(game.submitWord(currentPlayer), "The horizontal word 'CAT' should be valid.");
        assertEquals(currentPlayer.getPoints(),5);


        s = game.playersTurn();
        currentPlayer = game.currentPlayerTurn(); // Switch to the next player
        game.updateTempBoard(6, 8, "D");
        game.updateTempBoard(7, 8, "O");
        game.updateTempBoard(8, 8, "G");

        // Check if the vertical word "DOG" is valid
        assertTrue(game.submitWord(currentPlayer), "The vertical word 'DOG' should be valid.");

        assertEquals(currentPlayer.getPoints(), 15);
    }

    @Test
    public void testSaveGame() {
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);
        String s = game.playersTurn();
        Player currentPlayer = game.currentPlayerTurn();

        // Simulate some gameplay
        game.updateTempBoard(7, 7, "C");
        game.updateTempBoard(7, 8, "A");
        game.updateTempBoard(7, 9, "T");
        assertTrue(game.submitWord(currentPlayer), "The word 'CAT' should be valid.");

        String saveFileName = "test_save_game.ser";
        // Mock a dialog input to provide the save file name
        JOptionPane.showInputDialog(null, saveFileName);

        // Save the game state
        assertTrue(game.saveGame(), "The game state should be saved successfully.");
    }
    @Test
    public void testUndoRedo() {
        // Initialize the game and players
        game.intializePlayer("Alice", true);
        game.intializePlayer("Bob", true);

        // Get the current player
        String s = game.playersTurn();
        Player currentPlayer = game.currentPlayerTurn();

        // Simulate a move: Player places "CAT" on the board
        game.updateTempBoard(7, 7, "C");
        game.updateTempBoard(7, 8, "A");
        game.updateTempBoard(7, 9, "T");
        assertTrue(game.submitWord(currentPlayer), "The word 'CAT' should be valid.");
        int aliceScore = currentPlayer.getPoints();

        // Verify that the move stack is not empty
        assertFalse(game.moves.isEmpty(), "The moves stack should not be empty after a valid move.");

        // Perform an undo
        assertTrue(game.undo(), "Undo should succeed.");
        assertEquals(0, currentPlayer.getPoints(), "Alice's score should revert to 0 after undo.");

        // Verify the undo stack
        assertEquals(1, game.undos.size(), "Undo stack should contain one move after undo.");

        // Verify the move stack is now empty after the undo
        assertTrue(game.moves.isEmpty(), "Moves stack should be empty after undo.");

        // Perform a redo
        assertTrue(game.redo(), "Redo should succeed.");
        assertEquals(aliceScore, currentPlayer.getPoints(), "Alice's score should return after redo.");

        // Verify the undo stack is empty after redo
        assertTrue(game.undos.isEmpty(), "Undo stack should be empty after redo.");

        // Verify the move stack is back to containing the redone move
        assertEquals(1, game.moves.size(), "Moves stack should contain one move after redo.");
    }

    @Test
    public void testAiInitialization() {
        game.intializePlayer("AI Player", false);
        Player aiPlayer = game.getPlayers().getFirst();

        assertTrue(aiPlayer.isAI(), "The player should be recognized as an AI.");
        assertEquals(7, aiPlayer.getHand().size(), "The AI player should start with 7 tiles.");
    }

    @Test
    public void testAiDrawNewTiles() {
        game.intializePlayer("AI Player", false);
        AiPlayer aiPlayer = (AiPlayer) game.getPlayers().getFirst();

        // Simulate the AI having fewer tiles in hand
        aiPlayer.getHand().removeFirst();
        aiPlayer.drawNewTiles(game.bag);

        assertEquals(7, aiPlayer.getHand().size(), "The AI player's hand should be refilled to 7 tiles.");
    }



}
