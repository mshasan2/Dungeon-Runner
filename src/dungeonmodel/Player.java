package dungeonmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class contains all the methods and attributes required for a Player.
 * This contains an attribute which stores the location of the player, and also
 * a list that contains all the treasures a player has collected
 */
class Player {
  private String playerLocation;
  protected boolean isPlayerAlive;
  private List<Treasure> treasureList;
  private List<Arrow> arrowList;

  protected Player() {
    //default constructor
    treasureList = new ArrayList<>();
    arrowList = new ArrayList<>();
    this.playerLocation = "At Start";
    this.isPlayerAlive = true;
    this.equipPlayerWithStartingArrows();
  }

  protected void updatePlayerLocation(String playerLocation) {
    this.playerLocation = playerLocation;
  }

  protected String getPlayerLocation() {
    return this.playerLocation;
  }

  protected void addNewTreasure(List<Treasure> lootedTreasureList) {
    treasureList.addAll(lootedTreasureList);
  }

  protected String getPlayerTreasuresObtained() {
    StringBuilder str = new StringBuilder();
    if (this.treasureList.size() != 0) {
      str.append("\nThe treasures collected by the Player till now are: ");
      int index = 1;
      for (Treasure t : this.treasureList) {
        str.append("\n Stored In Treasure Slot No." + index + " are : " + t.toString());
        index++;
      }
      return str.toString();
    }
    str.append("\nPlayer has collected no treasures till now");
    return str.toString();
  }

  protected String getPlayerArrowsObtained() {
    StringBuilder str = new StringBuilder();
    if (this.arrowList.size() != 0) {
      str.append("\nNumber of Arrows with Player are ").append(this.arrowList.size());
      return str.toString();
    }
    str.append("\n Player has collected no Arrows till now");
    return str.toString();
  }

  protected String getPlayerDesc() {
    StringBuilder str = new StringBuilder();
    str.append("Player is in a ");
    str.append(this.getPlayerLocation());
    str.append(this.getPlayerTreasuresObtained());
    return str.toString();
  }

  protected String getPlayerCompleteDesc() {
    StringBuilder str = new StringBuilder();
    str.append("Player is in a ");
    str.append(this.getPlayerLocation());
    str.append(this.getPlayerTreasuresObtained());
    str.append(this.getPlayerArrowsObtained());
    return str.toString();
  }

  protected void setPlayerToDead() {
    this.isPlayerAlive = false;
  }

  protected boolean isPlayerAlive() {
    return this.isPlayerAlive;
  }

  private void equipPlayerWithStartingArrows() {
    this.arrowList.add(new Arrow());
    this.arrowList.add(new Arrow());
    this.arrowList.add(new Arrow());
  }

  protected int getNumOfArrowsRemaining() {
    return this.arrowList.size();
  }

  protected void useOneArrow() {
    if (this.arrowList.size() > 0) {
      this.arrowList.remove(0);
    }
  }

  protected void equipPlayerWithArrow(List<Arrow> arrowList) {
    this.arrowList.addAll(arrowList);
  }

}
