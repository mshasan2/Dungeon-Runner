package dungeonmodel;

/**
 * This is the enumeration class which contains the type of treasure which can be created.
 * Treasures can be of three types - Diamonds, Rubies and Sapphires
 */
enum TreasureType {
  DIAMONDS,
  RUBIES,
  SAPPHIRES;

  @Override
  public String toString() {
    String str = name();
    String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
    return cap;
  }
}
