package dungeonmodel;

/**
 * Class to represent an Otyugh.
 * Otyughs are extremely smelly creatures that lead solitary lives in the deep,
 * dark places of the world like our dungeon.
 * They have a health of 2 and can only be attacked by a Player.
 */
class Otyugh {
  private int otyughHealth;

  /**
   * Cosntructor for the Otyugh Class Object.
   */
  protected Otyugh() {
    this.otyughHealth = 2;
  }

  protected boolean isOtyughAlive() {
    return this.getOtyughHealth() > 0;
  }

  protected int getOtyughHealth() {
    return this.otyughHealth;
  }

  protected void injureOtyugh() {
    if (isOtyughAlive()) {
      otyughHealth -= 1;
    }
  }


}
