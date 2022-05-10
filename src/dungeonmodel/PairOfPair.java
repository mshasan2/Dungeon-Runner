package dungeonmodel;

/**
 * This is the PairOfPair class.
 * PairOfPair class contains the pair of a Pair.
 * This class contains an edge of the maze.
 * The two members in the class are the two points which are connected
 */
class PairOfPair {
  private Pair2 pairA;
  private Pair2 pairB;

  protected PairOfPair(Pair2 pairA, Pair2 pairB) {
    this.pairA = pairA;
    this.pairB = pairB;
  }

  protected Pair2 getA() {
    return this.pairA;
  }

  protected Pair2 getB() {
    return this.pairB;
  }


}
