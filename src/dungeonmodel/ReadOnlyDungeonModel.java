package dungeonmodel;

import java.util.List;

/**
 * Interface for the ReadOnlyDungeon model.
 * This interface will be used by the View.
 * This interface contains all the methods that will be needed by the view
 */
public interface ReadOnlyDungeonModel {

  int getDungeonRowSize();

  int getDungeonColSize();

  List<Pair2> getDungeonExploredLocations();

  List<String> getLocationAdjacentOpeningsDirections(Pair2 curLoc);

  boolean getArrowsPresentInLoc(Pair2 curLoc);

  boolean getTreasurePresentInLoc(Pair2 curLoc);

  int getStenchLevelInLoc(Pair2 curLoc);

  boolean getOtyughPresentInLoc(Pair2 curLoc);

  Pair2 getPlayerPresentPosition();

  String[] getDungeonParameters();

  String getDungeonPlayerCurrLocationDetails();

  String getPlayerFullDesc();

  int getPlayerArrowLeft();

}
