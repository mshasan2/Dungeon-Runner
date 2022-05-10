package dungeoncontroller;

import dungeonmodel.DungeonModel;

/**
 * Represents a Controller for the Dungeon Game.
 * Handles user moves by executing them in the model.
 * Conveys user move outcomes to the user.
 */
public interface DungeonController {

  /**
   * Execute a single game of Dungeon game given a Dungeon game model. When the game is over,
   * the playGame method ends.
   *
   * @param dungeonModel a non-null Dungeon game model
   */
  void playGame(DungeonModel dungeonModel);
}
