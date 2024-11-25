# Scrabble Game

A third iteration of a simple Scrabble game in Java with a graphical user interface (GUI). Players can select the number of players, place letters from their racks onto a 2D board, and interact with the game through an intuitive GUI. This version adds advanced features such as blank tiles, premium squares, and AI players, while also ensuring that the game mechanics are functioning as expected.

## Features 
- **Board:** A visually interactive 2D grid where players can click to place tiles and form words.
- **Tile Bag:** A bag of tiles that players draw from automatically after playing word during the game. It is not visually represented in the GUI.
- **Word Validation:** Checks whether the placed word intersects with existing words and if all formed words are valid.
- **Player Rack:** Each player has a personal rack of tiles, displayed visually, from which they can choose tiles to play. It also provides a "Submit Word", "Reset Rack" and "Pass" option during a player's turn.
- **Error Handling:** Provides error messages and feedback like invalid words, and other game-related issues, which are shown on the GUI.
- **Game Menu:** Gives user menu options on creating a new game, and exiting the current game.
- **Blank Tiles:** Players can use blank tiles to represent any letter. These tiles are placed on the board and are counted as zero points but allow players to form valid words.
- **Premium Squares:** Premium squares (Double Letter, Triple Letter, Double Word, Triple Word) are now part of the game. When a tile is placed on these squares, the score is modified accordingly (e.g., double the letter score or word score).
- **AI Player:** AI Player can be added to the game to make it more interesting.The AI evaluates all possible valid moves and selects the highest scoring word from those options. The AI follows basic logic and does not require user interaction.
- **Scoring Fixes:** Scoring correctly accounts for the use of premium squares and blank tiles. Words are scored based on tile values, and premium squares (when utilized) affect both letter and word scores.

## Prequisites 
- **Java 8+**
-  **IntelliJ IDEA** or another Java IDE.
  
## Gameplay Instructions
- The game will prompt for number of players and ask for the names of the players.
- Each player will be prompted to either play their turn or pass their turn using on-screen buttons.
- When a player chooses to play, they can select tiles from their personal rack and place them on the interactive board by clicking on the desired tile positions.
- If a player chooses to pass, their turn is forfeited, and the next player is prompted to take their turn.
- Words must be placed horizontally or vertically and must intersect with at least one existing word on the board.
- Scoring is based on the letter values of the tiles used in the words formed, and scores are displayed on the GUI in real-time.
- If AI Players are initialized, they will automatically select the best possible word to play and complete their turn.
- Words must be placed either horizontally or vertically and should intersect with existing words on the board.
- Blank tiles are available for use but do not contribute to the score.
- When tiles re placed on premium squares (Double Letter, Triple Letter, Double Word, Triple Word), the corresponding score modifiers are applied.

## Example Game Flow
1. Player 1 selects tiles from their rack and places the word "FIRE" on the board.
2. The game automatically updates the tile rack, and Player 1 auto-draws new tiles from the tile bag.
3. Player 2 selects from their rack and places the word "PICK", ensuring it intersects with the 'I' in "FIRE".
6. The game continues with players taking turns, placing words, drawing tiles, and recieving feedback on their actions, until no more valid words can be played.

## Configuration Options
- Tile Distribution: Adjust tile counts in 'Player.java'
- Tile Values: Adjust point value of tiles in 'Tile.java'
- Valid Words: Adjust the valid words list in 'wordDict.txt'
- Board Colour: Adjust colouring of tiles in 'ScrabbleView.java'

## Validating Words
- The game will automatically check if your move forms valid words and intersects correctly.

## Winning the game:
- The game ends when there are no more valid words to play, or if all players pass their turn in a row, and the player with the highest score wins.

## Known Issues
- The AI player works sometimes and the game has to be run twice for it to start.
- Player points are not being calculated correctly based on based on the premium squares and blank tiles. This will be fixed in the next iteration.
- Clicking on a board tile outside the horizontal or vertical alignment of the word being placed still allows placement, which should be restricted.
- The AI player may not play even if possible due to placement and coordinate problem.
- Human Player turn might return invalid when playing against AI.
- The end-of-game logic is still being worked on and only currently checks for if players have remaining valid words  to play or if all players have consecutively passed their turns for the game to end.
- The AI player plays based on a basic strategy of selecting the highest scoring move. More detailed AI logic will be explored and possibly implemented in the next iteration.

## Roadmap
- Next Iteration: Correct the scoring algorithm, especially for premium squares and blank tiles. Enhance the AI logic to consider more complex strategies. Implement an undo/redo system that supports multipple levels. Adding features like Save/Load to the game using Java Serialisation. 
- Additional Features: Allow customization of the Scrabble board, including alternate placements of premium squares (e.g., Double Word, Triple Word) on a user-defined layout.

## Authors 
- **Amar Saini:** Responsible for Blank tiles and Premium tile implementation
- **Kaiya Sparks:** Responsible for README file, Ai GUI integration and Bug Testing
- **Riya Rawat:** Responsible for Updating Test class, UML and README
- **Tyler Doherty:** Responsible for AI player implementation
## License
This project is licensed under the [MIT License]

