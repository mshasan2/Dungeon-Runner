package dungeonmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains the required code for Cave objects.
 * Cave class extends the Location class.
 * A Cave has 1, 3 or 4 entrances, and it may or may not have treasure inside it
 */
class Cave extends Location {
  private List<Treasure> treasuresInCave;
  private List<Otyugh> monstersInCave;
  private List<Arrow> arrowList;


  /**
   * Constructor for the cave class.
   * A Cave object will be returned when this constructor is called
   */
  protected Cave() {
    treasuresInCave = new ArrayList<>();
    monstersInCave = new ArrayList<>();
    arrowList = new ArrayList<>();
  }

  protected void addTreasure(Treasure treasure) {
    this.treasuresInCave.add(treasure);
  }

  protected List<Treasure> lootTreasuresInCave() {
    List<Treasure> treasuresInCaveCopy = new ArrayList<>();
    treasuresInCaveCopy.addAll(this.treasuresInCave);
    this.treasuresInCave.clear();
    return treasuresInCaveCopy;
  }

  protected List<Arrow> lootArrowsInCave() {
    List<Arrow> arrowsInCaveCopy = new ArrayList<>();
    arrowsInCaveCopy.addAll(this.arrowList);
    this.arrowList.clear();
    return arrowsInCaveCopy;
  }

  protected String getTreasuresInCaveDesc() {
    StringBuilder str = new StringBuilder();
    if (this.treasuresInCave.size() != 0) {
      str.append("\nTreasures present in the current cave are : ");
      for (Treasure key : this.treasuresInCave) {
        str.append(key.toString() + " ");
      }
    } else {
      str.append("\nNo treasures are present in this cave");
    }
    return str.toString();
  }

  protected String getTreasureInCaveShortRep() {
    List<String> treasureRep = new ArrayList<>();
    if (this.treasuresInCave.size() != 0) {
      for (Treasure key : this.treasuresInCave) {
        //DIAMONDS,
        //  RUBIES,
        //  SAPPHIRES
        if (key.toString().equals("DIAMONDS")) {
          treasureRep.add("D");
        } else if (key.toString().equals("RUBIES")) {
          treasureRep.add("R");
        } else {
          treasureRep.add("S");
        }
      }
      Collections.sort(treasureRep);
    } else {
      treasureRep.add(" ");
    }
    return treasureRep.toString();
  }

  protected String getArrowsInCaveShortRep() {
    List<String> arrowRep = new ArrayList<>();
    if (this.arrowList.size() != 0) {
      for (Arrow key : this.arrowList) {
        arrowRep.add("A");
      }
      Collections.sort(arrowRep);
    } else {
      arrowRep.add(" ");
    }
    return arrowRep.toString();
  }

  protected String getArrowsInCaveDesc() {
    StringBuilder str = new StringBuilder();
    str.append("\nNumber of Arrows Present: ");
    str.append(arrowList.size());
    return str.toString();
  }

  protected String getMonsterInCaveShortRep() {
    if (this.isMonsterInCave()) {
      return "O";
    } else {
      return "NO";
    }
  }

  protected int getNumArrows() {
    return this.arrowList.size();
  }

  protected void addArrowToCave(Arrow arrow) {
    this.arrowList.add(arrow);
  }

  protected boolean areArrowsPresentInCave() {
    return this.getNumArrows() > 0;
  }


  protected int getNumberOfTreasuresInCave() {
    return this.treasuresInCave.size();
  }

  @Override
  public String toString() {
    return "Cave";
  }


  protected void addMonsterToCave(Otyugh otyugh) {
    if (this.monstersInCave.size() == 0) {
      this.monstersInCave.add(otyugh);
    }
  }

  protected boolean isMonsterInCave() {
    return this.monstersInCave.size() > 0;
  }

  protected void attackMonsterInCave() {
    if (monstersInCave.size() > 0) {
      Otyugh otyugh = this.monstersInCave.get(0);
      otyugh.injureOtyugh();
      if (!otyugh.isOtyughAlive()) {
        this.removeMonsterFromCave();
      }
    }
  }

  protected boolean isMonsterInjured() {
    if (monstersInCave.size() > 0) {
      Otyugh otyugh = this.monstersInCave.get(0);
      if (otyugh.getOtyughHealth() == 1) {
        return true;
      }
    }
    return false;
  }

  protected void removeMonsterFromCave() {
    if (monstersInCave.size() > 0) {
      this.monstersInCave.remove(0);
    }
  }








}
