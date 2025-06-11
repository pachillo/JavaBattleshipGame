import java.util.ArrayList;
import java.util.List;

public class Board {
  private Ship ship;
  private Cell cell;
  private String[][] board;
  private Cell[][] place;
  private boolean isHit;
  private List < String > here;
  private List < Segment > sm;
  private boolean isHIt = false;

  public Board() {
    place = new Cell[11][11];
    for (int i = 0; i < 11; i++) {
      for (int j = 0; j < 11; j++) {
        place[i][j] = new Cell();
      }
    }
    this.here = new ArrayList < > ();
    here.add("hi");
    this.sm = new ArrayList < Segment > ();
  }

  private static List < String > checkOccupied(String input, int length, String direction) {
    List < String > sequence = new ArrayList < > ();
    int baseValue = Integer.parseInt(input.substring(1));

    if (direction.equalsIgnoreCase("across")) {
      for (int i = 0; i < length; i++) {
        sequence.add(input.charAt(0) + Integer.toString(baseValue + i));
      }
    }
    if (direction.equalsIgnoreCase("down")) {
      for (int i = 0; i < length; i++) {
        sequence.add((char)(input.charAt(0) + i) + Integer.toString(baseValue));
      }
    }

    return sequence;
  }

  private boolean positionCheck(String position) {
    return position.length() == 2 || position.length() == 3 && Character.isLetter(position.charAt(0)) && Character.isDigit(position.charAt(1));
  }

  private boolean positionCheck(int row, int col) {
    return row >= 1 && row <= 10 && col >= 1 && col <= 10;
  }

  private boolean positionCheckattack(int row, int col) {
    return row >= 0 && row <= 9 && col >= 0 && col <= 9;
  }

  private char convertToRowChar(int row) {
    return (char)('A' + row - 1);
  }

  public void placeShip(Ship ship, String position, String direction)
  throws InvalidPositionException, InvalidPlacementException, InvalidShipTypeException {
    if (ship == null) throw new InvalidShipTypeException();
    if (!positionCheck(position)) throw new InvalidPositionException();

    // Convert position to coordinates
    int col = Integer.parseInt(position.substring(1));
    int row = position.charAt(0) - 'A' + 1; // Changed from 'a' to 'A'

    if (!positionCheck(row, col)) throw new InvalidPositionException();

    int shipLength = ship.length();
    List < String > occupiedCells = new ArrayList < > ();

    // Check if ship fits in the chosen direction
    if (direction.equalsIgnoreCase("across")) {
      if (col + shipLength - 1 > 10) throw new InvalidPlacementException();

      // Check all cells the ship would occupy
      for (int i = 0; i < shipLength; i++) {
        String cellPos = position.charAt(0) + Integer.toString(col + i);
        if (here.contains(cellPos)) {
          throw new InvalidPlacementException();
        }
        occupiedCells.add(cellPos);
      }
    } else if (direction.equalsIgnoreCase("down")) {
      if (row + shipLength - 1 > 10) throw new InvalidPlacementException();

      // Check all cells the ship would occupy
      for (int i = 0; i < shipLength; i++) {
        String cellPos = (char)(position.charAt(0) + i) + position.substring(1);
        if (here.contains(cellPos)) {
          throw new InvalidPlacementException();
        }
        occupiedCells.add(cellPos);
      }
    } else {
      throw new InvalidPlacementException();
    }

    // Place the ship segments
    for (String cellPos: occupiedCells) {
      int cellRow = cellPos.charAt(0) - 'A';
      int cellCol = Integer.parseInt(cellPos.substring(1)) - 1;
      place[cellRow][cellCol].placeSegment(new Segment(ship));
    }

    // Mark these cells as occupied
    here.addAll(occupiedCells);
  }

  public void attack(String position) throws InvalidPositionException {
    int row;
    int col;
    try {
      col = Integer.parseInt(position.substring(1)) - 1;
      row = Character.toUpperCase(position.charAt(0)) - 'A';
    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
      throw new InvalidPositionException();
    }

    if (!positionCheck(position)) {
      throw new InvalidPositionException();
    }
    if (!positionCheckattack(row, col)) {
      throw new InvalidPositionException();
    }

    place[row][col].attack();
  }
  private void Update() {
    for (Segment s: ship.segments) {
      if (isHit == false) {
        if (!s.hit()) {
          this.isHit = true;
          s.attack();
        } else {
          this.isHit = false;
        }
      } else {
        if (s.hit()) this.isHit = false;
      }

    }
  }

  public boolean hasBeenHit(String position) throws InvalidPositionException {

    int row;
    int col;
    try {
      col = Integer.parseInt(position.substring(1)) - 1;
      row = Character.toUpperCase(position.charAt(0)) - 'A' + 1 - 1;
    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
      throw new InvalidPositionException();
    }
    if (!positionCheck(position)) {
      throw new InvalidPositionException();
    }
    if (!positionCheckattack(row, col)) {
      throw new InvalidPositionException();
    }
    return place[row][col].hasBeenHit();

  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
    for (int row = 0; row < 10; row++) {
      sb.append((char)('A' + row)).append(" ");
      for (int col = 0; col < 10; col++) {
        if (place[row][col].hasBeenHit()) {
          sb.append(place[row][col]).append(" ");
        } else {
          sb.append(". ");
        }
      }
      sb.setLength(sb.length() - 1);
      sb.append("\n");
    }
    return sb.toString();
  }

  public String displaySetup() {
    StringBuilder sb = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
    for (int row = 0; row < 10; row++) {
      sb.append((char)('A' + row)).append(" ");
      for (int col = 0; col < 10; col++) {

        sb.append(place[row][col].displaySetup()).append(" ");
      }
      sb.setLength(sb.length() - 1);
      sb.append("\n");
    }
    return sb.toString();
  }

  public Cell[][] getPlace() {
    return place;
  }
}