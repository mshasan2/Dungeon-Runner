package dungeonmodel;

import java.util.Objects;

/**
 * This is the Pair2 class.
 * A pair2 class is used when we have to represent a point in the maze.
 * Pair2 class has 2 attributes, pointA and pointB. pointA stores the x coordinate of the location,
 * and pointB stores the y coordinate of the location.
 */
public class Pair2 {
  private final int pointA;
  private final int pointB;

  public Pair2(int a, int b) {
    this.pointA = a;
    this.pointB = b;
  }

  protected int getA() {
    return this.pointA;
  }

  protected int getB() {
    return this.pointB;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pair2 other = (Pair2) o;
    return other.pointA == this.pointA && other.pointB == this.pointB;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pointA,
        this.pointB);
  }

  @Override
  public String toString() {
    return "(" + this.getA() + " , " + this.getB() + ")";
  }


}
