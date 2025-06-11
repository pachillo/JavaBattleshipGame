import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Runner {
  private static Scanner scanner = new Scanner(System.in);
  private static Random random = new Random();
  private static Board player1Board;
  private static Board player2Board;
  private static boolean isSinglePlayer;
  private static List < String > availableShips = List.of("Battleship", "Carrier", "Destroyer", "Submarine", "Patrol Boat");

  public static void main(String[] args) {
    System.out.println("Welcome to Battleship!");

    // Choose game mode
    System.out.print("Enter number of players (1 or 2): ");
    int numPlayers = getIntInput(1, 2);
    isSinglePlayer = (numPlayers == 1);

    // Setup boards
    player1Board = new Board();
    player2Board = new Board();

    // Player 1 ship placement
    System.out.println("\nPlayer 1, place your ships:");
    placeShips(player1Board);

    // Player 2 (or computer) ship placement
    if (isSinglePlayer) {
      placeComputerShips(player2Board);
      System.out.println("\nComputer has placed its ships.");
    } else {
      System.out.println("\nPlayer 2, place your ships:");
      placeShips(player2Board);
    }

    // Game loop
    boolean player1Turn = true;
    while (true) {
      if (player1Turn) {
        System.out.println("\nPlayer 1's turn to attack:");
        if (takeTurn(player1Board, player2Board)) {
          System.out.println("Player 1 wins!");
          break;
        }
      } else {
        if (isSinglePlayer) {
          System.out.println("\nComputer's turn:");
          computerTurn(player2Board, player1Board);
        } else {
          System.out.println("\nPlayer 2's turn to attack:");
          if (takeTurn(player2Board, player1Board)) {
            System.out.println("Player 2 wins!");
            break;
          }
        }
      }
      player1Turn = !player1Turn;
    }
  }

  private static void placeShips(Board board) {
    for (String shipType: availableShips) {
      while (true) {
        try {
          System.out.println("\nPlace your " + shipType + " (length: " + Ship.createShip(shipType).length() + ")");
          System.out.println(board.displaySetup());

          System.out.print("Enter starting position (e.g., A1): ");
          String position = scanner.nextLine().toUpperCase();

          System.out.print("Enter direction (Across/Down): ");
          String direction = scanner.nextLine();

          Ship ship = Ship.createShip(shipType);
          board.placeShip(ship, position, direction);
          break;
        } catch (InvalidPositionException | InvalidPlacementException | InvalidShipTypeException e) {
          System.out.println("Invalid placement. Try again.");
        }
      }
    }
    System.out.println("\nFinal ship placement:");
    System.out.println(board.displaySetup());
  }

  private static void placeComputerShips(Board board) {
    for (String shipType: availableShips) {
      while (true) {
        try {
          // Generate random position and direction
          char row = (char)('A' + random.nextInt(10));
          int col = 1 + random.nextInt(10);
          String position = row + Integer.toString(col);
          String direction = random.nextBoolean() ? "Across" : "Down";

          Ship ship = Ship.createShip(shipType);
          board.placeShip(ship, position, direction);
          break;
        } catch (Exception e) {
          // Try again if placement fails
        }
      }
    }
  }

  private static boolean takeTurn(Board attackerBoard, Board defenderBoard) {
    while (true) {
      try {
        System.out.println("\nEnemy board:");
        System.out.println(attackerBoard.toString());

        System.out.print("Enter attack position (e.g., A1): ");
        String position = scanner.nextLine().toUpperCase();

        if (attackerBoard.hasBeenHit(position)) {
          System.out.println("You already attacked this position. Try again.");
          continue;
        }

        attackerBoard.attack(position);

        // Check if attack hit
        int row = position.charAt(0) - 'A';
        int col = Integer.parseInt(position.substring(1)) - 1;
        Cell cell = ((Board) defenderBoard).getPlace()[row][col];

        if (cell.isOccupied()) {
          System.out.println("HIT!");
          if (cell.getSegment().getShip().sunk()) {
            System.out.println("You sunk the " + cell.getSegment().getShip().name() + "!");
          }
        } else {
          System.out.println("MISS!");
        }

        // Check if all ships are sunk
        if (allShipsSunk(defenderBoard)) {
          return true;
        }

        return false;
      } catch (InvalidPositionException e) {
        System.out.println("Invalid position. Try again.");
      } catch (NumberFormatException e) {
        System.out.println("Invalid format. Use format like A1.");
      }
    }
  }

  private static void computerTurn(Board computerBoard, Board playerBoard) {
    while (true) {
      try {
        // Generate random attack position
        char row = (char)('A' + random.nextInt(10));
        int col = 1 + random.nextInt(10);
        String position = row + Integer.toString(col);

        if (computerBoard.hasBeenHit(position)) {
          continue; // Try again if already attacked
        }

        System.out.println("Computer attacks: " + position);
        computerBoard.attack(position);

        // Check if attack hit
        int rowIdx = position.charAt(0) - 'A';
        int colIdx = Integer.parseInt(position.substring(1)) - 1;
        Cell cell = playerBoard.getPlace()[rowIdx][colIdx];

        if (cell.isOccupied()) {
          System.out.println("HIT!");
          if (cell.getSegment().getShip().sunk()) {
            System.out.println("Computer sunk your " + cell.getSegment().getShip().name() + "!");
          }
        } else {
          System.out.println("MISS!");
        }

        // Check if all ships are sunk
        if (allShipsSunk(playerBoard)) {
          System.out.println("Computer wins!");
          System.exit(0);
        }

        break;
      } catch (Exception e) {
        // Try again if attack fails
      }
    }
  }

  private static boolean allShipsSunk(Board board) {
    // This method would need to be implemented in Board class
    // For now, we'll use a simple check (in a real game, we'd need to track all ships)
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        Cell cell = board.getPlace()[row][col];
        if (cell.isOccupied() && !cell.getSegment().getShip().sunk()) {
          return false;
        }
      }
    }
    return true;
  }

  private static int getIntInput(int min, int max) {
    while (true) {
      try {
        int input = Integer.parseInt(scanner.nextLine());
        if (input >= min && input <= max) {
          return input;
        }
        System.out.print("Please enter a number between " + min + " and " + max + ": ");
      } catch (NumberFormatException e) {
        System.out.print("Invalid input. Please enter a number: ");
      }
    }
  }
}