package dungeonmodel;

/**
 * This is the Wall class.
 * This class represents a Wall.
 * A Wall object represents a location which is a dead end.
 * The player has to take another route from this a wall and cannot proceed further
 */
class Wall extends Location {

  protected Wall() {
    //
  }

  @Override
  public String toString() {
    return "This is a Wall";
  }
}
