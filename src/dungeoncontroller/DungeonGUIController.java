package dungeoncontroller;

import dungeonmodel.DungeonModel;

/**
 * Represents a Controller for Dungeon Game: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface DungeonGUIController {
  /**
   * Execute a single game of Dungeon game given a Dungeon game model. When the game is over,
   * the playGame method ends.
   *
   * @param dungeonModel a non-null TicTacToe Model
   */
  void playGame(DungeonModel dungeonModel);

  /**
   * Handle an action in a single cell of the board, such as to make a move.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  void handleCellClick(int row, int col);

  /**
   * Handle a key pressed and converts that key to make a move.
   *
   * @param keyPressed String representation of the Key Pressed
   */
  void handleKeyPressed(String keyPressed);

  /**
   * This method restarts the Game with the Parameters given at the start of the Game.
   * Thereby moving the player to the start location and resetting the Treasures,
   * Arrows and Monsters.
   * If non-random was selected same maze is returned and if random is selected
   * then new maze is selected
   */
  void restartGame();

  /**
   * This method starts a new Game with the parameters specified in the parameters.
   * New Maze is generated with the given parameters.
   * This method passes all the parameters to the model and does not do any input validation
   *
   * @param rows               number of rows in the DungeonModel
   * @param cols               number of columns in the DungeonModel
   * @param interConnectivity  interconnectivity of the dungeon
   * @param isWrapping         is the DungeonModel wrapping
   * @param percentOfTreasures the percentage of Caves that have treasure in them
   *                           else same DungeonModel is created
   * @param numMonsters        number of Otyughs in the cave, the min number of monsters is 1
   */
  void startNewGame(String rows, String cols, String interConnectivity, String isWrapping,
                             String percentOfTreasures, String numMonsters);

  /**
   * This method calls the model to reuses the same DungeonMap(Maze) used in the initial
   * setting to start a new game.
   * Player moves to the starting location, only the map remains the same, rest of the features of
   * the game depend on whether the dungeon was selected to be random or non-random during creation
   */
  void reUseMap();
}
