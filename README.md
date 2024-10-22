# Scrabble Game

A first iteration of a simple Scrabble game in Java, allowing players to select how many players and place words on a 2D board.
The game ensures all words placed are valid, that new words placed intersect with existing words and validates the formed words.

## Features 
- **Board:** A 2D grid where players place their tiles to form words.
- **Tile Bag:** A bag of tiles that players draw from automatically after playing word during the game.
- **Word Validation:** Checks whether the placed word intersects with existing words and if all formed words are valid.
- **Player Rack:** Each player has a set of tiles to play from.
- **Error Handling:** Various error handling mechanisms for invalid words, invalid inputs, insufficient tiles, etc.

## Prequisites 
- **Java 8+**
-  **IntelliJ IDEA** or another Java IDE.
  
### Installing
1. **Clone the repository:**
     ```bash
     git clone <https://github.com/AmarSaini2/SYSC3110-Project>
     ```
2. **Open the project in your preferred IDE**
3. **Build/Run the game:**
   - Run the main class 'Game.java'.

## Gameplay Instructions
- Each player takes turns placing words on the boards.
- Words must be placed horizontally or vertically and must intersect with existing words.
- Scoring is based on the letters used in the words formed.

## Example Game Flow
1. Player 1 places the word "FIRE" on the board.
2. Player 1 auto-draws new tiles from the tile bag.
3. Player 2 places the word "PICK" intersecting at the 'I'.
4. The game continues until no more valid words can be played.

## Configuration Options
- Tile Distribution: Adjust tile counts in 'Player.java'
- Tile Values: Adjust point value of tiles in 'Tile.java'
- Valid Words: Adjust the valid words list in 'wordDict.txt'

## Validating Words
- The game will automatically check if your move forms valid words and intersects correctly.

## Winning the game:
- The game ends when there are no more valid words to play, and the player with the highest score wins.

## Known Issues
- Certain edge cases in word validation might not be handled correctly.
- Game does not support more than 2 players currently.
- End of game logic is still being worked on and only currently checks for if players have remaining valid words to play for the game to end.

## Roadmap
- Next Iteration: Enhance error handling and implement a GUI interface for the gameplay.
- Additional Features: Supporting more than 2 players and expanding the end of game logic.

## Authors 
- **Amar Saini:** Responsible for Game Logic Implementation
- **Kaiya Sparks:** Responsible for README file and Game Logic Implementation
- **Riya Rawat:** Responsible for UML class diagram
- **Tyler Doherty:** Responsible for Testing Gameplay Mechanics and Identify Bugs and Inconsistencies
## License
This project is licensed under the [MIT License]

