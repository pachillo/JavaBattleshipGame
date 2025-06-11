# Battleship Game

## Introduction
This was my first *big* project into my programming journey. I learned a lot about game cases, logic and had a lot of fun (mostly when it actually complies and runs)!

## Overview
This is a Java implementation of the classic Battleship game, supporting both single-player (against a computer) and two-player modes. Players place ships on a 10x10 grid and take turns attacking each other's grids to sink all opponent ships.

## Features
- **Game Modes**: Single-player (vs. computer) or two-player.
- **Ship Types**: Includes Carrier (5), Battleship (4), Destroyer (3), Submarine (3), and Patrol Boat (2).
- **Grid System**: 10x10 grid with coordinates (e.g., A1 to J10).
- **Input Validation**: Handles invalid positions, placements, and ship types with custom exceptions.
- **Display**: Shows the game board with ship placements and attack results.

## Requirements
- Java Development Kit (JDK) 8 or higher.
- A Java IDE or compiler to run the game.

## Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd battleship-game
   ```
3. Compile the Java files:
   ```bash
   javac *.java
   ```
4. Run the game:
   ```bash
   java Runner
   ```

## How to Play
1. **Start the Game**: Run `Runner.java` to start the game.
2. **Choose Game Mode**: Enter `1` for single-player (vs. computer) or `2` for two-player.
3. **Place Ships**:
   - For each ship, enter a starting position (e.g., `A1`) and direction (`Across` or `Down`).
   - The game validates the placement to ensure it fits on the board and doesn't overlap.
   - In single-player mode, the computer automatically places its ships.
4. **Take Turns**:
   - Enter a position (e.g., `B3`) to attack the opponent's board.
   - The game indicates if the attack is a hit, miss, or sinks a ship.
   - In single-player mode, the computer randomly attacks your board.
5. **Win Condition**: The game ends when all ships on one player's board are sunk.

## File Structure
- `Runner.java`: Main game loop, handles user input and game flow.
- `Board.java`: Manages the game board, ship placement, and attacks.
- `Ship.java`: Defines ship types, lengths, and status (sunk or not).
- `Segment.java`: Represents individual ship segments.
- `Cell.java`: Represents a single grid cell, tracking occupancy and hits.
- `InvalidPlacementException.java`, `InvalidPositionException.java`, `InvalidShipTypeException.java`: Custom exceptions for input validation.

## Example Gameplay
```
Welcome to Battleship!
Enter number of players (1 or 2): 1

Player 1, place your ships:
Place your Battleship (length: 4)
  1 2 3 4 5 6 7 8 9 10
A . . . . . . . . . .
B . . . . . . . . . .
...
Enter starting position (e.g., A1): A1
Enter direction (Across/Down): Across
```

## Notes
- The game board is displayed with `.` for unhit cells, `O` for misses, `X` for hits on unsunk ships, and ship initials (e.g., `B` for Battleship) for sunk ships.
- The computer in single-player mode uses random placement and attacks, which may be improved in future versions.
- Error handling ensures robust input validation, preventing crashes from invalid inputs.

## Contributing
Feel free to fork the repository, make improvements, and submit pull requests. Suggestions for enhancements include:
- Improved computer AI for smarter attacks.
- Enhanced graphics or UI (e.g., using JavaFX).
- Saving and loading game states.
