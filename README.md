# Scrabble Game

A first iteration of a simple Scrabble game in Java, allowing players to select how many players and place words on a 2D board.
The game ensures all words placed are valid, that new words placed intersect with existing words and validates the formed words.

## Features 
- **Board:** A 2D grid where players place their tiles to form words.
- **Tile Bag:** A bag of tiles that players draw from automatically after playing word during the game.
- **Word Validation:** Checks whether the placed word intersects with existing words and if all formed words are valid.
- **Player Rack:** Each player has a set of tiles to play from.
- **Error Handling:** Various error handling mechanisms for invalid words, invalid inputs, insufficient tiles, etc.

### Installing
1. **Clone the repository:**
     '''bash
     git clone urlll
     '''
2. **Open the project in your preferred IDE**
3. **Build/Run the game:**
   - Run the main class 'Game.java'.
## Usage 
1. **Start a new game:**
   Players input the amound of players and take turns placing words on the board. A player can pass their turn or choose to play a word. Each word must be placed adajacent to or intersecting with an existing word.
2. **Drawing tiles:**
   New tiles are automatically drawn for players after a player plays a valid word.
3. **Validating words:**
   The game will automatically check if your move forms valid words and intersects correctly.
4. **Winning the game:**
   The game ends when there are no more valid words to play, and the player with the highest score wins.


   
   
     
