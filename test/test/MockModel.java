package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dungeonmodel.DungeonModel;
import dungeonmodel.Pair2;

/**
 * A Mock model to test whether the controller is working correctly or nor.
 */
public class MockModel implements DungeonModel {
  private StringBuilder log;
  private final int uniqueCode;

  public MockModel(StringBuilder log, int uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }


  /**
   * Returns the String representation of the DungeonModel.
   * The terms used in the representation are defined below
   * 1[D, R] - 1 - Vertex of the Location in DungeonModel
   * - D - Denotes that we can move down from this Location
   * - R - Denotes that we can move Right from this Location.
   *
   * @return String representation of the dungeon
   */
  @Override
  public String getDungeonRepresentation() {
    return null;
  }

  /**
   * Returns the String representation of the DungeonModel with Caves and Tunnel included.
   * The terms used in the representation are defined below
   * 1(T) 2(C)[D, R] - 1 - Vertex of the Location in DungeonModel
   * - T - Denotes that the Location is a Tunnel
   * - C - Denotes that the Location is a Cave
   * - D - Denotes that we can move down from this Location
   * *            - R - Denotes that we can move Right from this Location.
   *
   * @return String representation of the dungeon with Caves and Tunnel included
   */
  @Override
  public String getDungeonRepresentationWithCavesAndTunnel() {
    return null;
  }

  /**
   * Returns the String representation of the DungeonModel with Cave, Tunnel along with Treasures.
   * The terms used in the representation are defined below
   * 2(C)[D, R , S]  - 2 - Vertex of the Location in DungeonModel
   * - C - Denotes that the Location is a Cave
   * - D - Denotes that Diamonds are present in the Cave
   * - R - Denotes that Rubies are present in the Cave
   * - S - Denotes that Sapphires are present in the Cave
   *
   * @return String representation of the DungeonModel with Cave and Tunnel along with Treasures
   */
  @Override
  public String getDungeonRepresentationWithTreasures() {
    return null;
  }

  /**
   * Returns the String representation of the DungeonModel with Cave, Tunnel along with Otyugh.
   * The terms used in the representation are defined below
   * 2(C)O            - 2 - Vertex of the Location in DungeonModel
   * - C - Denotes that the Location is a Cave
   * - O - Denotes that Otyugh is present in the Cave
   * - NO - Denotes that No Otyugh is present in the Cave
   *
   * @return String representation of the DungeonModel with Cave, Tunnel along with Otyugh
   */
  @Override
  public String getDungeonRepresentationWithOtyugh() {
    return null;
  }

  /**
   * Returns the String representation of the DungeonModel with Caves and Tunnel included.
   * The terms used in the representation are defined below
   * 6(T)[A, A]      - 6 - Vertex of the Location in DungeonModel
   * - T - Denotes that the Location is a Tunnel
   * - C - Denotes that the Location is a Cave
   * - A - Denotes that Arrow is present
   *
   * @return String representation of the DungeonModel with Caves and Tunnel included.
   */
  @Override
  public String getDungeonRepresentationWithArrows() {
    return null;
  }

  /**
   * Returns true if the Game is over.
   * Game can end when player reaches the end cave or
   * if the player dies in the game
   *
   * @return True if game is over, false if not over
   */
  @Override
  public boolean isGameOver() {
    return true;
  }

  /**
   * Returns true if the Player is alive, else returns false.
   *
   * @return True if the player is alive else returns false
   */
  @Override
  public boolean isPlayerAlive() {
    return true;
  }

  /**
   * Method to move the player.
   *
   * @param userInpDirToMove Direction to move the Player
   * @param scan             Desired Input type
   * @param out              Desired Output type
   */
  @Override
  public void move(String userInpDirToMove, Scanner scan, Appendable out) {
    //Mock model method
  }

  /**
   * Method to start the game.
   *
   * @param scan Desired Input type
   * @param out  Desired Output type
   */
  @Override
  public void startGame(Scanner scan, Appendable out) {
    try {
      log.append("Input: " );
      while (scan.hasNext()) {
        String input = scan.next();
        log.append(input + " ");
      }
      out.append("\nUniqueCode: " + this.uniqueCode);
    } catch (IOException ioe) {
      throw new IllegalStateException("Invalid");
    }
  }

  /**
   * This method initializes the game.
   */
  @Override
  public void initializeGame() {
    //Mock model method
    log.append("Initialize Game called Model Unique Code: ").append(uniqueCode).append("\n");
  }

  @Override
  public void pickUpTreasureInCurLoc() {
    //Mock model method
  }

  @Override
  public void pickUpArrowsInCurLoc() {
    //Mock model method
  }

  @Override
  public String shootArrowFromCurLoc(String input) {
    return null;
  }

  /**
   * Restarts the Dungeon game.
   * This makes use of the parameters which were used the game was created and restarts the game.
   * Thereby moving the player to the start location and resetting the Treasures,
   * Arrows and Monsters.
   */
  @Override
  public void restartGame() {
    log.append("Unique Code: " );
    log.append(this.uniqueCode);
  }

  /**
   * This method starts a new Game with the given parameters.
   * Number of Rows in the DungeonModel cannot be lesser than 0
   * Number of Caves in the DungeonModel cannot be lesser than 0
   * InterConnectivity should be a positive integer
   * isWrapping denotes if the DungeonModel is wrapping or not and should be a boolean
   * percentOfTreasures should not be lesser than 0 and not greater than 100
   *
   * @param strRows               number of rows in the DungeonModel
   * @param strCols               number of columns in the DungeonModel
   * @param strInterConnectivity  interconnectivity of the dungeon
   * @param strIsWrapping         is the DungeonModel wrapping
   * @param strPercentOfTreasures the percentage of Caves that have treasure in them
   * @param strNumMonsters        number of Otyughs in the cave, the min number of monsters is 1
   */
  @Override
  public void startNewGame(String strRows, String strCols, String strInterConnectivity,
                           String strIsWrapping, String strPercentOfTreasures,
                           String strNumMonsters) {
    //Mock model method
    log.append(strRows + " , " + strCols + " , " + strInterConnectivity + " , "
        + strIsWrapping  + " , " + strPercentOfTreasures  + " , " + strNumMonsters
        + " ModelUniqueCode: " + uniqueCode + "\n");
  }

  /**
   * This method reuses the same DungeonMap(Maze), used in the initial setting to start a new game.
   * Player moves to the starting location, only the map remains the same, rest of the features of
   * the game depend on whether the dungeon was selected to be random or non-random during creation
   */
  @Override
  public void reUseMap() {
    //Mock model method
  }


  @Override
  public int getDungeonRowSize() {
    return 0;
  }

  @Override
  public int getDungeonColSize() {
    return 0;
  }

  @Override
  public List<Pair2> getDungeonExploredLocations() {
    return null;
  }

  @Override
  public List<String> getLocationAdjacentOpeningsDirections(Pair2 curLoc) {
    return new ArrayList<>();
  }

  @Override
  public boolean getArrowsPresentInLoc(Pair2 curLoc) {
    return false;
  }

  @Override
  public boolean getTreasurePresentInLoc(Pair2 curLoc) {
    return false;
  }

  @Override
  public int getStenchLevelInLoc(Pair2 curLoc) {
    return 0;
  }

  @Override
  public boolean getOtyughPresentInLoc(Pair2 curLoc) {
    return false;
  }

  @Override
  public Pair2 getPlayerPresentPosition() {
    return null;
  }

  @Override
  public String[] getDungeonParameters() {
    return new String[0];
  }

  @Override
  public String getDungeonPlayerCurrLocationDetails() {
    return null;
  }

  @Override
  public String getPlayerFullDesc() {
    return null;
  }

  @Override
  public int getPlayerArrowLeft() {
    //Mock Model Method
    return 0;
  }

  @Override
  public void moveByDir(String userInpDirToMove) {
    //DoNothing
  }
}
