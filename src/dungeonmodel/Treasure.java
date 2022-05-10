package dungeonmodel;

/**
 * This is the Treasure class.
 * Treasure class contains all the required methods and attributes for a Treasure object.
 * The type of treasure is stored in the treasureType object
 */
class Treasure {
  private TreasureType treasureType;

  protected Treasure(TreasureType type) {
    this.treasureType = type;
  }

  public String toString() {
    return this.treasureType.toString();
  }

}
