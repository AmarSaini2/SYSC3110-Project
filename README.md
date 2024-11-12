# Scrabble Game

A second iteration of a simple Scrabble game in Java with graphical user interface (GUI), allowing players to select the number of players and pick and place letters from a rack onto a 2D board.
The GUI enables players to interact with the board through clickable tiles. 
The game ensures all words placed are valid, that new words placed intersect with existing words and validates the formed words.
The interface also provides feedback on the player's actions, such as whether a word is valid or not, and displays the current game status, including the player's score.

## Features 
- **Board:** A visually interactive 2D grid where players can click to place tiles and form words.
- **Tile Bag:** A bag of tiles that players draw from automatically after playing word during the game. It is not visually represented in the GUI.
- **Word Validation:** Checks whether the placed word intersects with existing words and if all formed words are valid.
- **Player Rack:** Each player has a personal rack of tiles, displayed visually, from which they can choose tiles to play. It also provides a "Submit Word", "Reset Rack" and "Pass" option during a player's turn.
- **Error Handling:** Provides error messages and feedback like invalid words, and other game-related issues, which are shown on the GUI.
- **Game Menu:** Gives user menu options on creating a new game, and exiting the current game. 

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
- After clicking "Reset Rack", the game does not correctly handle a player trying to play a new word.
- Clicking on a board tile outside the horizontal or vertical alignment of the word being placed still allows placement, which should be restricted.
- The end-of-game logic is still being worked on and only currently checks for if players have remaining valid words  to play or if all players have consecutively passed their turns for the game to end.
- The game does not utilize blank tiles or premium squares yet.

## Roadmap
- Next Iteration: Further improve error handling, ensuring that invalid word placements, incorrect tile selections and other errors can no longer occur without proper handling.  
- Additional Features: Implementing the use of blank tiles, premium squares onto the game board which will affect player scoring and implementing AI players with basic logic for a user to play against.

## Authors 
- **Amar Saini:** Responsible for Game Logic integration into GUI
- **Kaiya Sparks:** Responsible for README file and GUI Implementation
- **Riya Rawat:** Responsible for GUI Implementation and UML diagram
- **Tyler Doherty:** Responsible for Testing Gameplay Mechanics and Identify Bugs and Inconsistencies
## License
This project is licensed under the [MIT License]

