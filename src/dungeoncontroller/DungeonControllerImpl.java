package dungeoncontroller;

import dungeonmodel.DungeonModel;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the Controller class for the Dungeon Game.
 * To play a game, the model of the game must be passed to the playGame method,
 * the controller class takes in the user input in the specified format and gives the outcome
 * of each move in the specified format.
 */
public class DungeonControllerImpl implements DungeonController {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   * This creates a controller which is used to pass,
   * the required type of user input and output.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonControllerImpl(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = new Scanner(in);
  }

  /**
   * Execute a single game of Dungeon game given a Dungeon game model. When the game is over,
   * the playGame method ends.
   *
   * @param dungeonModel a non-null Dungeon game model
   */
  @Override
  public void playGame(DungeonModel dungeonModel) {
    if (dungeonModel == null) {
      throw new IllegalArgumentException("Invalid model\n");
    }
    try {
      this.out.append("\nWelcome to the Dungeon\n");
      //this.out.append(dungeonModel.getDungeonRepresentationWithOtyugh());
      dungeonModel.startGame(scan, out);
      while (!dungeonModel.isGameOver()) {
        String userInpDirToMove = scan.next();
        dungeonModel.move(userInpDirToMove, scan, out);
      }
      if (!dungeonModel.isPlayerAlive()) {
        out.append("Player has been killed\n End of Game Reached");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
