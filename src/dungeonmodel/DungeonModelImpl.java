package dungeonmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * DungeonModelImpl class is the class which will be called by the Driver class.
 * This class contains all the methods and attributes required for the successful run of the game.
 *
 */
public class DungeonModelImpl implements DungeonModel {
  private Location loc;
  private Pair2 startState;
  private Pair2 goalState;
  private Pair2 curState;
  private int rows;
  private int cols;
  private int interConnectivity;
  private boolean isWrapping;
  private int percentOfTreasures;
  private final boolean isRandom;
  private int numMonsters;

  private List<Pair2> exploredLocations;

  /**
   * Constructor for the DungeonModelImpl class.
   * Number of Rows in the DungeonModel cannot be lesser than 0
   * Number of Caves in the DungeonModel cannot be lesser than 0
   * InterConnectivity should be a positive integer
   * isWrapping denotes if the DungeonModel is wrapping or not and should be a boolean
   * percentOfTreasures should not be lesser than 0 and not greater than 100
   * This constructor takes in the below values
   *
   * @param rows               number of rows in the DungeonModel
   * @param cols               number of columns in the DungeonModel
   * @param interConnectivity  interconnectivity of the dungeon
   * @param isWrapping         is the DungeonModel wrapping
   * @param percentOfTreasures the percentage of Caves that have treasure in them
   * @param isRandom           if True the DungeonModel which gets generated is random,
   *                           else same DungeonModel is created
   * @param numMonsters        number of Otyughs in the cave, the min number of monsters is 1
   */
  public DungeonModelImpl(int rows, int cols, int interConnectivity, boolean isWrapping,
                          int percentOfTreasures, boolean isRandom, int numMonsters)
      throws IllegalArgumentException {
    if (rows <= 0 || cols <= 0 || interConnectivity < 0 || interConnectivity > (cols * rows / 2)
        || percentOfTreasures < 0 || percentOfTreasures > 100 || numMonsters <= 0) {
      throw new IllegalArgumentException("Invalid input provided");
    }
    if (interConnectivity > 0 && (rows * cols < 15)) {
      throw new IllegalArgumentException("Illegal combination of the given given rows and columns");
    }
    this.rows = rows;
    this.cols = cols;
    this.interConnectivity = interConnectivity;
    this.isWrapping = isWrapping;
    this.percentOfTreasures = percentOfTreasures;
    this.isRandom = isRandom;
    this.numMonsters = numMonsters;
    this.loc = new Location(rows, cols, interConnectivity,
        isWrapping, percentOfTreasures, isRandom, numMonsters);
    this.startState = this.getStartState();
    this.goalState = this.getGoalState(this.startState);
    this.curState = startState;
    this.exploredLocations = new ArrayList<>();
    this.exploredLocations.add(startState);
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
  public String getDungeonRepresentation() {
    return this.loc.getMazeRepresentation();
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
  public String getDungeonRepresentationWithCavesAndTunnel() {
    return this.loc.getMazeRepresentationWithCavesAndTunnels();
  }

  /**
   * Returns the String representation of the DungeonModel with Cave and Tunnel
   * along with Treasures.
   * The terms used in the representation are defined below
   * 2(C)[D, R , S]  - 2 - Vertex of the Location in DungeonModel
   * - C - Denotes that the Location is a Cave
   * - D - Denotes that Diamonds are present in the Cave
   * - R - Denotes that Rubies are present in the Cave
   * - S - Denotes that Sapphires are present in the Cave
   *
   * @return String representation of the DungeonModel with Cave and Tunnel along with Treasures
   */
  public String getDungeonRepresentationWithTreasures() {
    return this.loc.getMazeRepresentationWithTreasures();
  }

  /**
   * Returns the Start state of the DungeonModel in the form of Pair2 object.
   *
   * @return start state of the DungeonModel
   */
  public Pair2 getStartState() {
    return this.loc.getStartState();
  }

  /**
   * Returns the Goal state of the DungeonModel in the form of Pair2 object.
   *
   * @param startState Start state of the DungeonModel in the form of Pair2 object.
   * @return Goal state of the DungeonModel in the form of Pair2 object.
   */
  public Pair2 getGoalState(Pair2 startState) {
    return this.loc.getGoalState(startState);
  }

  /**
   * Method to update the player location.
   * The current location of the Player needs to passed to this function
   *
   * @param curState the Current Location of the Player in the form of Pair2 object
   */
  public void updatePlayerLocation(Pair2 curState) {
    this.loc.updatePlayerLocation(curState);
  }

  /**
   * Returns the String Representation of the Current Location of Player.
   *
   * @return String Representation of the Current Location of Player.
   */
  public String getPlayerCurrentLocation() {
    return this.loc.player.getPlayerLocation();
  }

  /**
   * Returns the String Representation of the moves a player can take from the given location.
   *
   * @param curState the Current Location of the Player in the form of Pair2 object
   * @return String Representation of the moves a player can take from the given location.
   */
  public String getPlayerMovesDesc(Pair2 curState) {
    return this.loc.getPlayerMoves(curState);
  }

  /**
   * Returns the String Representation of the Treasures Player looted from the Cave.
   * If Treasures are not present in the Cave then appropriate message is returned
   *
   * @param curState the Current Location of the Player in the form of Pair2 object
   * @return String Representation of the Treasures Player looted from the Cave
   */
  protected String checkAndLootCave(Pair2 curState) {
    return this.loc.checkAndLootCaves(curState);
  }

  /**
   * Returns the List of possible locations a Player can move from the given Location.
   * The Locations are in the form of Pair2 objects
   *
   * @param curState the Current Location of the Player in the form of Pair2 object
   * @return the List of possible locations a Player can move from the given Location.
   */
  protected List<Pair2> getPossibleLocationsToMove(Pair2 curState) {
    return this.loc.getPossibleLocationsToMove(curState);
  }

  /**
   * Returns the String Representation of the Direction the Player moved.
   *
   * @param prevState   the Previous Location of the Player in the form of Pair2 object
   * @param reachedSate the New Location of the Player in the form of Pair2 object
   * @return the String Representation of the Direction the Player moved.
   */
  protected String getPlayerMovedDir(Pair2 prevState, Pair2 reachedSate) {
    return this.loc.getPlayerMovedDir(prevState, reachedSate);
  }

  /**
   * Returns the String Representation of the Player Description.
   * The Player Description contains the Present Location of the Player and
   * the Treasures' player has collected till now
   *
   * @return the String Representation of the Player Description.
   */
  protected String getPlayerDesc() {
    return this.loc.player.getPlayerDesc();
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
    return !(loc.player.isPlayerAlive() && !loc.isGoalStateReached());
  }

  /**
   * Returns the String representation of the DungeonModel with Cave, Tunnel along with Otyugh.
   * The terms used in the representation are defined below
   * 2(C)O            - 2 - Vertex of the Location in DungeonModel
   *                 - C - Denotes that the Location is a Cave
   *                 - O - Denotes that Otyugh is present in the Cave
   *                 - NO - Denotes that No Otyugh is present in the Cave
   *
   * @return String representation of the DungeonModel with Cave, Tunnel along with Otyugh
   */
  public String getDungeonRepresentationWithOtyugh() {
    return this.loc.getMazeRepresentationWithOtyugh();
  }

  /**
   * Returns the String representation of the DungeonModel with Caves and Tunnel included.
   * The terms used in the representation are defined below
   * 6(T)[A, A]      - 6 - Vertex of the Location in DungeonModel
   *                 - T - Denotes that the Location is a Tunnel
   *                 - C - Denotes that the Location is a Cave
   *                 - A - Denotes that Arrow is present
   *
   * @return String representation of the DungeonModel with Caves and Tunnel included.
   */
  public String getDungeonRepresentationWithArrows() {
    return this.loc.getMazeRepresentationWithArrows();
  }

  private Pair2 moveByDirection(Pair2 curState, String userInpDirToMove) {
    //System.out.println("Model - move by direction " + userInpDirToMove);
    return this.loc.moveByDirection(curState, userInpDirToMove);
  }

  //protected Pair2 moveByAction(Pair2 curState, String userInpDirToMove) {
  //  //return this.loc.moveByDirection(curState, userInpDirToMove, out);
  //}


  protected void checkForTreasureInCaves(Pair2 curState, Scanner in, Appendable out) {
    this.loc.checkForTreasureInCaves(curState, in, out);
  }

  private boolean checkForOtyughInCave(Pair2 curLocation, Appendable out) {
    return this.loc.checkForOtyughInCave(curLocation, out);
  }

  private boolean checkForMonsterInCave(Pair2 curLocation) {
    return this.loc.checkForMonsterInCave(curLocation);
  }

  protected void checkForOtyughSmell(Pair2 curLoc, Appendable out) {
    this.loc.checkForOtyughSmell(curLoc, out);
  }

  protected void shootArrow(Pair2 curLoc, Scanner in, Appendable out) {
    this.loc.shootArrow(curLoc, in, out);
  }

  protected boolean isStateEqualToGoalState(Pair2 curState, Pair2 goalState, Appendable out) {
    return this.loc.isStateEqualToGoalState(curState, goalState, out);
  }

  protected boolean isCurrStateEqualToGoalState(Pair2 curState, Pair2 goalState) {
    return this.loc.isCurrStateEqualToGoalState(curState, goalState);
  }

  /**
   * Returns true if the Player is alive, else returns false.
   *
   * @return True if the player is alive else returns false
   */
  public boolean isPlayerAlive() {
    return this.loc.player.isPlayerAlive();
  }

  /**
   * Method to start the game.
   * This method starts the game and this method should be called before the move method.
   *
   * @param scan Desired Input type
   * @param out Desired Output type
   */
  public void startGame(Scanner scan, Appendable out) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Invalid Arguments provided");
    }
    try {
      this.updatePlayerLocation(startState);
      out.append("\nYou are in a ");
      //This checks if you are in a cave or tunnel
      out.append(this.getPlayerCurrentLocation()).append("\n");
      this.checkForOtyughSmell(startState, out);
      this.checkForTreasureInCaves(startState, scan, out);
      // Treasure and Arrows
      //The possible directions the player can currently move are :
      out.append(this.getPlayerMovesDesc(startState));
      this.shootArrow(startState, scan, out);

      out.append("\n\nEnter direction to move : ");
    } catch (IOException ioe) {
      throw new IllegalStateException("Invalid append");
    }
  }

  /**
   * This method initializes the game making it fit to run in GUI.
   *
   */
  public void initializeGame() {
    this.updatePlayerLocation(startState);
  }

  @Override
  public void pickUpTreasureInCurLoc() {
    if (!this.loc.pickUpTreasureInCurLoc(this.curState)) {
      throw new IllegalArgumentException("No treasure present in this location");
    }
  }

  @Override
  public void pickUpArrowsInCurLoc() {
    if (!this.loc.pickUpArrowsInCurLoc(this.curState)) {
      throw new IllegalArgumentException("No treasure present in this location");
    }
  }

  @Override
  public String shootArrowFromCurLoc(String input) {
    if (input == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    String mess = this.loc.shootArrowFromCurLoc(this.curState, input);
    //System.out.println("From model: " + mess);
    return mess;
    //return this.loc.shootArrowFromCurLoc(this.curState, input);
  }

  /**
   * Method to move the player.
   * We pass in the direction to move the player,
   * and if the direction is valid the player is moved.
   *
   * @param userInpDirToMove Direction to move the Player
   * @param scan Desired Input type
   * @param out Desired Output type
   */
  public void move(String userInpDirToMove, Scanner scan, Appendable out) {
    if (userInpDirToMove.equals("") || scan == null || out == null) {
      throw new IllegalArgumentException("Invalid Arguments provided");
    }
    try {
      if (this.loc.getPossibleDirectionsToMoves(this.curState).contains(userInpDirToMove)) {
        if (this.loc.isPlayerAlive()) {
          curState = this.moveByDirection(curState, userInpDirToMove);
          out.append("\nReached State is: ").append(curState.toString());
          this.updatePlayerLocation(curState);
          if (this.isStateEqualToGoalState(curState, goalState, out)) {
            return;
          }
          out.append("\nYou are in a ");
          out.append(this.getPlayerCurrentLocation()).append("\n"); //Cave/Tunnel
          if (this.checkForOtyughInCave(curState, out)) {
            return;
          }
          this.checkForOtyughSmell(curState, out);
          this.checkForTreasureInCaves(curState, scan, out);
          // Treasure and Arrows
          //The possible directions the player can currently move are :
          out.append(this.getPlayerMovesDesc(curState));
          this.shootArrow(curState, scan, out);

          out.append("\n\nEnter direction to move : ");
        }
      } else {
        out.append("\nInvalid Input Direction");
        out.append(this.getPlayerMovesDesc(startState));
        out.append("\nEnter direction to move : ");
        return;
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Invalid append");
    }
  }

  //For View
  @Override
  public void moveByDir(String userInpDirToMove) {
    if (userInpDirToMove == null) {
      throw new IllegalArgumentException("Invalid Arguments provided");
    }
    if (this.loc.getPossibleDirectionsToMoves(this.curState).contains(userInpDirToMove)) {
      if (this.loc.isPlayerAlive()) {
        curState = this.moveByDirection(curState, userInpDirToMove);
        if (this.checkForMonsterInCave(curState)) {
          exploredLocations.add(curState);
          return;
        }
        exploredLocations.add(curState);
        this.updatePlayerLocation(curState);
        if (this.isCurrStateEqualToGoalState(curState, goalState)) {
          return;
        }
      }
    } else {
      return;
    }
  }


  @Override
  public int getDungeonRowSize() {
    return this.rows;
  }

  @Override
  public int getDungeonColSize() {
    return this.cols;
  }

  @Override
  public List<Pair2> getDungeonExploredLocations() {
    return this.exploredLocations;
  }

  @Override
  public List<String> getLocationAdjacentOpeningsDirections(Pair2 curLoc) {
    if (curLoc == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    List<String> locationAdjacentOpeningsDirections = new ArrayList<>();
    locationAdjacentOpeningsDirections.addAll(this.loc.getPossibleDirectionsPlayerCanMove(curLoc));
    return locationAdjacentOpeningsDirections;
  }

  @Override
  public boolean getArrowsPresentInLoc(Pair2 curLoc) {
    if (curLoc == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    return this.loc.getArrowsPresentInLoc(curLoc);
  }

  @Override
  public boolean getTreasurePresentInLoc(Pair2 curLoc) {
    if (curLoc == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    return this.loc.getTreasurePresentInLoc(curLoc);
  }

  @Override
  public int getStenchLevelInLoc(Pair2 curLoc) {
    if (curLoc == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    return this.loc.getStenchLevelInLoc(curLoc);
  }

  @Override
  public boolean getOtyughPresentInLoc(Pair2 curLoc) {
    if (curLoc == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    return this.loc.getOtyughPresentInLoc(curLoc);
  }

  @Override
  public Pair2 getPlayerPresentPosition() {
    return this.curState;
  }

  @Override
  public String[] getDungeonParameters() {
    String[] dungeonParameters = new String[6];
    dungeonParameters[0] = String.valueOf(this.rows);
    dungeonParameters[1] = String.valueOf(this.cols);
    dungeonParameters[2] = String.valueOf(this.interConnectivity);
    dungeonParameters[3] = String.valueOf(this.isWrapping);
    dungeonParameters[4] = String.valueOf(this.percentOfTreasures);
    dungeonParameters[5] = String.valueOf(this.numMonsters);
    return dungeonParameters;
  }

  @Override
  public String getDungeonPlayerCurrLocationDetails() {
    return this.loc.getDungeonPlayerLocationDetails(this.curState);
  }

  @Override
  public String getPlayerFullDesc() {
    return this.loc.getPlayerFullDesc();
  }

  @Override
  public int getPlayerArrowLeft() {
    return this.loc.player.getNumOfArrowsRemaining();
  }


  /**
   * Restarts the Dungeon game.
   * This makes use of the parameters which were used the game was created and restarts the game.\
   * The Player will be placed in the Start Location
   */
  @Override
  public void restartGame() {
    this.loc = new Location(this.rows, this.cols, this.interConnectivity,
        this.isWrapping, this.percentOfTreasures, this.isRandom, this.numMonsters);
    this.startState = this.getStartState();
    this.goalState = this.getGoalState(this.startState);
    this.curState = startState;
    this.exploredLocations = new ArrayList<>();
    this.exploredLocations.add(startState);
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
    if (strRows == null || strCols == null || strInterConnectivity == null
        || strIsWrapping == null || strPercentOfTreasures == null || strNumMonsters == null) {
      throw new IllegalArgumentException("Invalid input provided");
    }
    int rows;
    int cols;
    int interConnectivity;
    int percentOfTreasures;
    int numMonsters;
    boolean isWrapping;
    try {
      rows = Integer.parseInt(strRows);
      cols = Integer.parseInt(strCols);
      interConnectivity = Integer.parseInt(strInterConnectivity);
      isWrapping = Boolean.parseBoolean(strIsWrapping);
      percentOfTreasures = Integer.parseInt(strPercentOfTreasures);
      numMonsters = Integer.parseInt(strNumMonsters);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid Input");
    }
    if (rows <= 0 || cols <= 0 || interConnectivity < 0 || interConnectivity > (cols * rows / 2)
        || percentOfTreasures < 0 || percentOfTreasures > 100 || numMonsters <= 0) {
      throw new IllegalArgumentException("Invalid input provided");
    }
    if (interConnectivity > 0 && (rows * cols < 15)) {
      throw new IllegalArgumentException("Illegal combination of the given rows and columns");
    }
    this.rows = rows;
    this.cols = cols;
    this.interConnectivity = interConnectivity;
    this.isWrapping = isWrapping;
    this.percentOfTreasures = percentOfTreasures;
    this.numMonsters = numMonsters;
    this.loc = new Location(rows, cols, interConnectivity,
        isWrapping, percentOfTreasures, isRandom, numMonsters);
    this.startState = this.getStartState();
    this.goalState = this.getGoalState(this.startState);
    this.curState = startState;
    this.exploredLocations = new ArrayList<>();
    this.exploredLocations.add(startState);
  }

  /**
   * This method reuses the same DungeonMap(Maze), used in the initial setting to start a new game.
   * Player moves to the starting location, only the map remains the same, rest of the features of
   * the game depend on whether the dungeon was selected to be random or non-random during creation
   */
  @Override
  public void reUseMap() {
    this.curState = startState;
    this.goalState = this.getGoalState(this.startState);
    this.exploredLocations = new ArrayList<>();
    this.exploredLocations.add(startState);
    this.loc.reUse();
  }
}
