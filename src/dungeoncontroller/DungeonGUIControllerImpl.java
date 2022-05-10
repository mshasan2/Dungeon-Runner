package dungeoncontroller;

import dungeonmodel.DungeonModel;
import dungeonview.DungeonView;

/**
 * This is the DungeonGUIControllerImpl class.
 * This class implements the DungeonGUIController
 * This class contains all the methods required for the GUI controller
 * Constructor of this class expects a view and a model
 */
public class DungeonGUIControllerImpl implements DungeonGUIController {
  private final DungeonView view;
  private final DungeonModel model;
  private boolean shootFlag;

  /**
   * This is the DungeonGUIControllerImpl class constructor.
   * This takes in two parameters as input.
   * A view of type DungeonView and a model of type DungeonModel
   * It expects both of these parameters to be non-null
   *
   * @param view view for the controller of type DungeonView
   * @param model model for the controller of type DungeonModel
   */
  public DungeonGUIControllerImpl(DungeonView view, DungeonModel model) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    this.view = view;
    this.model = model;
    this.shootFlag = false;
  }

  /**
   * Execute a single game of Dungeon game given a Dungeon game model. When the game is over,
   * the playGame method ends.
   *
   * @param dungeonModel a non-null TicTacToe Model
   */
  @Override
  public void playGame(DungeonModel dungeonModel) {
    if (dungeonModel == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    this.model.initializeGame();
    this.view.addClickListener(this);
    this.view.addKeyListener(this);
    this.view.makeVisible();
    this.view.refresh();
  }

  /**
   * Handle an action in a single cell of the board, such as to make a move.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  @Override
  public void handleCellClick(int row, int col) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Invalid Input");
    }
    shootFlag = false;
    if (this.checkForGameOver()) {
      return;
    }
    if (row == 0 && col == 1) {
      this.model.moveByDir("North");
      this.view.refresh();
      if (this.checkForGameOver()) {
        return;
      }
    } else if (row == 0 && col == 2) {
      this.model.moveByDir("West");
      this.view.refresh();
      if (this.checkForGameOver()) {
        return;
      }
    } else if (row == 0 && col == 3) {
      this.model.moveByDir("East");
      this.view.refresh();
      if (this.checkForGameOver()) {
        return;
      }
    } else if (row == 0 && col == 4) {
      this.model.moveByDir("South");
      this.view.refresh();
      if (this.checkForGameOver()) {
        return;
      }
    }
    this.view.refresh();
  }


  private boolean checkForGameOver() {
    if (this.model.isGameOver()) {
      if (this.model.isPlayerAlive()) {
        this.view.showMessage("Congratulations!\n You have won the game");
        return true;
      } else {
        this.view.showMessage("Game Over! \nYou have been killed!\nBetter Luck Next Time :)");
        return true;
      }
    }
    return false;
  }

  /**
   * Handle a key pressed and converts that key to make a move.
   *
   * @param keyPressed String representation of the Key Pressed
   */
  public void handleKeyPressed(String keyPressed) {
    if (keyPressed == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    if (this.checkForGameOver()) {
      return;
    }
    switch (keyPressed) {
      case "UP":
        if (shootFlag) {
          this.performShootOp("North");
          shootFlag = false;
        } else {
          shootFlag = false;
          this.model.moveByDir("North");
          this.view.refresh();
          this.view.refresh();
          if (this.checkForGameOver()) {
            return;
          }
        }
        break;
      case "DOWN":
        if (shootFlag) {
          this.performShootOp("South");
          shootFlag = false;
        } else {
          shootFlag = false;
          this.model.moveByDir("South");
          this.view.refresh();
          this.view.refresh();
          if (this.checkForGameOver()) {
            return;
          }
        }
        break;
      case "LEFT":
        if (shootFlag) {
          this.performShootOp("West");
          shootFlag = false;
        } else {
          shootFlag = false;
          this.model.moveByDir("West");
          this.view.refresh();
          this.view.refresh();
          if (this.checkForGameOver()) {
            return;
          }
        }
        break;
      case "RIGHT":
        if (shootFlag) {
          this.performShootOp("East");
          shootFlag = false;
        } else {
          shootFlag = false;
          this.model.moveByDir("East");
          this.view.refresh();
          this.view.refresh();
          if (this.checkForGameOver()) {
            return;
          }
        }
        break;
      case "T":
        shootFlag = false;
        try {
          this.model.pickUpTreasureInCurLoc();
          this.view.refresh();
          if (this.checkForGameOver()) {
            return;
          }
        } catch (IllegalArgumentException iae) {
          //display a message that there was no treasure in the LOCATION
        }
        break;
      case "A":
        shootFlag = false;
        try {
          this.model.pickUpArrowsInCurLoc();
          this.view.refresh();
          if (this.checkForGameOver()) {
            return;
          }
        } catch (IllegalArgumentException iae) {
          //Do nothing as we have to make this user-friendly
          //Player has picked up treasure
          //display a message that there were no arrows in location
        }
        break;
      case "S":
        if (model.getPlayerArrowLeft() > 0) {
          shootFlag = true;
        }
        break;
      default:
        break;
    }
  }

  private void performShootOp(String dir) {
    String input = this.view.showMessage("JInput");
    String dirDist = dir + " " + input;
    try {
      String message = this.model.shootArrowFromCurLoc(dirDist);
      this.view.showMessage(message);
      this.view.refresh();
      if (this.checkForGameOver()) {
        return;
      }
    } catch (IllegalStateException ise) {
      //this.view.showMessage("");
      //Do nothing as we have to make this user-friendly
    }
  }

  /**
   * This method restarts the Game with the Parameters given at the start of the Game.
   * Thereby moving the player to the start location and resetting the Treasures,
   * Arrows and Monsters.
   */
  @Override
  public void restartGame() {
    this.model.restartGame();
    this.view.refresh();
  }

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
  @Override
  public void startNewGame(String rows, String cols, String interConnectivity, String isWrapping,
                    String percentOfTreasures, String numMonsters) {
    //Here model checks for invalid inputs, there need not be any check as model will do validation
    //And null validation by controller will be redundant
    try {
      this.model.startNewGame(rows, cols, interConnectivity, isWrapping, percentOfTreasures,
          numMonsters);
      this.view.refresh();
    } catch (IllegalArgumentException iae) {
      this.view.showMessage("Invalid Input Value");
    }
  }

  /**
   * This method calls the model to reuses the same DungeonMap(Maze) used in the initial
   * setting to start a new game.
   * Player moves to the starting location, only the map remains the same, rest of the features of
   * the game depend on whether the dungeon was selected to be random or non-random during creation
   */
  @Override
  public void reUseMap() {
    this.model.reUseMap();
    this.view.refresh();
  }

}
