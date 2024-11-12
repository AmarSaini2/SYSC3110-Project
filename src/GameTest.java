import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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

    @org.junit.Test
    @Test
    public void testStartGame() {
        String expectedOutput = "Welcome to the game of scrabble!\nTo start, select a number of players: (2-4)";
        assertEquals(expectedOutput, game.startGame());
    }

    @Test
    public void testPlayerInitialization() {
        game.intializePlayer("Alice");
        game.intializePlayer("Bob");

        assertEquals(2, game.getPlayers().size(), "The game should have 2 players.");
        assertEquals("Alice", game.getPlayers().get(0).getName());
        assertEquals("Bob", game.getPlayers().get(1).getName());
    }

    @Test
    public void testPlayersTurn() {
        game.intializePlayer("Alice");
        game.intializePlayer("Bob");

        assertEquals("Player: Alice \n1. Play a word\n2. Pass your turn\n", game.playersTurn());
        game.handlePlayerChoice(2); // Pass Alice's turn
        assertEquals("Player: Bob \n1. Play a word\n2. Pass your turn\n", game.playersTurn());
    }

    @Test
    public void testHandlePlayerChoice_PlayWord() {
        game.intializePlayer("Alice");
        game.intializePlayer("Bob");

        ScrabbleView mockView = new ScrabbleView(); // Assuming ScrabbleView class
        game.addView(mockView);

        game.handlePlayerChoice(1); // Simulate "Play a word"
        assertEquals(0, game.getConsecutivePasses(), "Consecutive passes should reset after a word is played.");
    }

    @Test
    public void testHandlePlayerChoice_PassTurn() {
        game.intializePlayer("Alice");
        game.intializePlayer("Bob");

        game.handlePlayerChoice(2); // Simulate "Pass turn"
        assertEquals(1, game.getConsecutivePasses(), "Consecutive passes should increment when turn is passed.");
        assertEquals("Player: Bob \n1. Play a word\n2. Pass your turn\n", game.playersTurn());
    }

    @Test
    public void testTilePlacement() {
        game.intializePlayer("Alice");
        Tile tile = new Tile("A"); // Assuming Tile has a constructor that takes a letter and points

        game.tilePlaced(tile); // Place tile on the board
        assertTrue(game.getTempBoard().getBoard()[7][7] == "A");
    }

    @Test
    public void testSubmitWord() {
        game.intializePlayer("Alice");
        Player currentPlayer = game.currentPlayerTurn();

        // Simulating tile placement and valid word submission
        game.updateTempBoard(7, 7, "A");
        game.updateTempBoard(7, 8, "T");

        assertTrue(game.submitWord(currentPlayer), "The submitted word should be valid.");
    }

    @Test
    public void testIsGameOverByPass() {
        game.intializePlayer("Alice");
        game.intializePlayer("Bob");

        // Simulating consecutive passes
        game.handlePlayerChoice(2); // Alice passes
        game.handlePlayerChoice(2); // Bob passes

        assertTrue(game.isGameOver(), "Game should end after each player consecutively passes.");
    }

    @Test
    public void testEndGameSummary() {
        game.intializePlayer("Alice");
        game.intializePlayer("Bob");

        // Assigning points for testing
        game.getPlayers().get(0).addPoints(100);
        game.getPlayers().get(1).addPoints(120);

        String expectedSummary = "Final Scores: \nAlice - Final Score: 100\nBob - Final Score: 120\nThe winner is: Bob with a score of 120\n";
        assertEquals(expectedSummary, game.handleEndOfGame());
    }
}
