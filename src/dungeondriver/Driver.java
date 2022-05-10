package dungeondriver;

import static java.lang.System.exit;

import dungeoncontroller.DungeonController;
import dungeoncontroller.DungeonControllerImpl;
import dungeoncontroller.DungeonGUIController;
import dungeoncontroller.DungeonGUIControllerImpl;
import dungeonmodel.DungeonModel;
import dungeonmodel.DungeonModelImpl;
import dungeonview.DungeonView;
import dungeonview.DungeonViewImpl;
import java.io.InputStreamReader;


/**
 * This is the Driver Class.
 * Driver class contains the code for the user to understand the working of the application.
 */
public class Driver {
  /**
   * This is the main method for the Driver class.
   * This method contains the code for the user to understand the working of the DungeonGame
   *
   * @param args the arguments supplied to the main method
   */
  public static void main(String[] args) {
    int rows = 0;
    int cols = 0;
    int interConnectivity = 0;
    boolean isWrapping = false;
    int percentOfTreasures = 0;
    int numMonsters = 1;
    if (args.length == 6) {
      try {
        rows = Integer.parseInt(args[0]);
        cols = Integer.parseInt(args[1]);
        interConnectivity = Integer.parseInt(args[2]);
        String isWrappingString = args[3];
        percentOfTreasures = Integer.parseInt(args[4]);
        numMonsters = Integer.parseInt(args[5]);
        String check = isWrappingString.toLowerCase();
        if (check.equals("true")) {
          isWrapping = true;
        } else if (check.equals("false")) {
          isWrapping = false;
        }
        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;
        DungeonModel model = new DungeonModelImpl(rows, cols, interConnectivity, isWrapping,
            percentOfTreasures, true, numMonsters);
        DungeonController controller = new DungeonControllerImpl(input, output);
        controller.playGame(model);
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid Input\n" +
            "Correct Input format is NumberOfRows, NumberOfCols, interConnectivity," +
            " isDungeonWrapping, PercentOfTreasures, NumberOfMonsters");
        exit(0);
      }
    } else if (args.length == 0) {
      DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 70, true, 2);
      DungeonView view = new DungeonViewImpl(model);
      DungeonGUIController controller = new DungeonGUIControllerImpl(view, model);
      controller.playGame(model);
    } else {
      System.out.println("Invalid Input provided" +
          " Correct Input format is NumberOfRows, NumberOfCols, interConnectivity," +
          " isDungeonWrapping, PercentOfTreasures, NumberOfMonsters");
      exit(0);
    }

  }


}
