package dungeonmodel;

import java.util.Scanner;

/**
 * DungeonModel Interface represents the methods that can be performed from the Model.
 * The methods can be used to move the player, get DungeonModel representation and other methods
 * which will be useful when implementing the DungeonModel Model
 */
public interface DungeonModel extends ReadOnlyDungeonModel {

  /**
   * Returns the String representation of the DungeonModel.
   * The terms used in the representation are defined below
   * 1[D, R] - 1 - Vertex of the Location in DungeonModel
   *         - D - Denotes that we can move down from this Location
   *         - R - Denotes that we can move Right from this Location.
   *
   * @return String representation of the dungeon
   */
  String getDungeonRepresentation();

  /**
   * Returns the String representation of the DungeonModel with Caves and Tunnel included.
   * The terms used in the representation are defined below
   * 1(T) 2(C)[D, R] - 1 - Vertex of the Location in DungeonModel
   *                 - T - Denotes that the Location is a Tunnel
   *                 - C - Denotes that the Location is a Cave
   *                 - D - Denotes that we can move down from this Location
   *    *            - R - Denotes that we can move Right from this Location.
   *
   * @return String representation of the dungeon with Caves and Tunnel included
   */
  String getDungeonRepresentationWithCavesAndTunnel();

  /**
   * Returns the String representation of the DungeonModel with Cave, Tunnel along with Treasures.
   * The terms used in the representation are defined below
   * 2(C)[D, R , S]  - 2 - Vertex of the Location in DungeonModel
   *                 - C - Denotes that the Location is a Cave
   *                 - D - Denotes that Diamonds are present in the Cave
   *                 - R - Denotes that Rubies are present in the Cave
   *                 - S - Denotes that Sapphires are present in the Cave
   *
   * @return String representation of the DungeonModel with Cave and Tunnel along with Treasures
   */
  String getDungeonRepresentationWithTreasures();

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
  String getDungeonRepresentationWithOtyugh();

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
  String getDungeonRepresentationWithArrows();

  /**
   * Returns true if the Game is over.
   * Game can end when player reaches the end cave or
   * if the player dies in the game
   *
   * @return True if game is over, false if not over
   */
  boolean isGameOver();

  /**
   * Returns true if the Player is alive, else returns false.
   *
   * @return True if the player is alive else returns false
   */
  boolean isPlayerAlive();

  /**
   * Method to move the player.
   * We pass in the Direction the Player should move next.
   *
   * @param userInpDirToMove Direction to move the Player
   * @param scan Desired Input type
   * @param out Desired Output type
   */
  void move(String userInpDirToMove, Scanner scan, Appendable out);

  /**
   * Method to start the game.
   * We pass in the desired input and output type to this method.
   * This method starts the game and gives out the player's starting location
   *
   * @param scan Desired Input type
   * @param out Desired Output type
   */
  void startGame(Scanner scan, Appendable out);

  /**
   * This method initializes the game making it fit to run in GUI.
   */
  void initializeGame();

  /**
   * When this method is called, if there is treasure in the Player's current location.
   * Treasure will be picked up, else no action will be performed.
   */
  void pickUpTreasureInCurLoc();

  /**
   * When this method is called, if there are Arrows in the Player's current location.
   * Arrows will be picked up, else no action will be performed.
   */
  void pickUpArrowsInCurLoc();

  /**
   * This make is used to shoot an arrow.
   * The String input to this method contains the Direction and Distance,
   * the arrow will have to travel.
   *
   * @param input The String input to this method contains the Direction and Distance
   * @return String Representation of the Result of Shooting the arrow
   */
  String shootArrowFromCurLoc(String input);

  /**
   * This method will move the Player from their current location to the Specified direction,
   * if the given direction is a valid direction.
   *
   * @param userInpDirToMove the direction to move the Player in
   */
  void moveByDir(String userInpDirToMove);

  /**
   * Restarts the Dungeon game.
   * This makes use of the parameters which were used the game was created and restarts the game.
   * Thereby moving the player to the start location and resetting the Treasures,
   * Arrows and Monsters.
   * In restart method a whole new dungeon with the previous parameters is created
   */
  void restartGame();

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
  void startNewGame(String strRows, String strCols, String strInterConnectivity,
                    String strIsWrapping, String strPercentOfTreasures, String strNumMonsters);

  /**
   * This method reuses the same DungeonMap(Maze), used in the initial setting to start a new game.
   * Player moves to the starting location, only the map remains the same, rest of the features of
   * the game depend on whether the dungeon was selected to be random or non-random during creation
   * Player start location remains the same, but goal location will change if player is using
   * random version of the game.
   */
  void reUseMap();


}
