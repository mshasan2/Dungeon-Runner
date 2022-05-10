package dungeonmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * This is the Location Class.
 * Location class contains all the methods required to create a maze.
 * This class is inherited by Cave and Tunnel classes.
 * Location refers to a place which can either be a Tunnel or Cave.
 * If a location has 2 entrances then that location is defined as a Cave,
 * else that location is defined as a Tunnel
 */
class Location {
  Player player;
  private final int rows;
  private final int cols;
  private final int interConnectivity;
  private final boolean isWrapping;
  private final int percentOfTreasures;
  private HashMap<Pair2, Integer> vertexConnectionsMap;
  private HashMap<Pair2, Location> vertexLocationMap;
  private HashMap<Pair2, Location> vertexTunnelMap;
  private HashMap<Pair2, Location> vertexCaveMap;
  private List<PairOfPair> mstEdges = new ArrayList<>();
  private Maze maze;
  private final boolean isRandom;
  private boolean isGoalReached;
  private final int numMonsters;

  /**
   * Default constructor for the location class.
   */
  protected Location() {
    this.rows = 0;
    this.cols = 0;
    this.interConnectivity = 0;
    this.isWrapping = false;
    this.player = new Player();
    this.percentOfTreasures = 0;
    this.isRandom = false;
    this.isGoalReached = false;
    this.numMonsters = 1;
    this.initializeMaps();
    this.mstEdges = new ArrayList<>();
  }

  protected Location(int rows, int cols, int interConnectivity,
                     boolean isWrapping, int percentOfTreasures,
                     boolean isRandom, int numMonsters) {
    this.rows = rows;
    this.cols = cols;
    this.interConnectivity = interConnectivity;
    this.isWrapping = isWrapping;
    this.isRandom = isRandom;
    this.numMonsters = numMonsters;
    this.mstEdges = new ArrayList<>();
    this.maze = new Maze(this.rows, this.cols, this.interConnectivity,
        this.isWrapping, this.isRandom);
    this.initializeMaps();
    maze.createMst();
    this.vertexConnectionsMap = maze.getVerticesConnections();
    this.mstEdges = maze.getMstEdges();
    this.player = new Player();
    this.percentOfTreasures = percentOfTreasures;
    this.createCaveTunnel();
    if (this.percentOfTreasures > 0) {
      this.addTreasuresToCaves();
      this.addArrowsToLocations();
    }
    this.addMonstersToCaves(numMonsters - 1);
    this.isGoalReached = false;
  }

  protected void reUse() {
    this.initializeMaps();
    this.vertexConnectionsMap = maze.getVerticesConnections();
    this.mstEdges = maze.getMstEdges();
    this.player = new Player();
    this.createCaveTunnel();
    if (this.percentOfTreasures > 0) {
      this.addTreasuresToCaves();
      this.addArrowsToLocations();
    }
    this.addMonstersToCaves(numMonsters - 1);
    this.isGoalReached = false;
  }

  private void initializeMaps() {
    this.vertexConnectionsMap = new HashMap<>();
    this.vertexLocationMap = new HashMap<>();
    this.vertexTunnelMap = new HashMap<>();
    this.vertexCaveMap = new HashMap<>();
  }


  private void createCaveTunnel() {
    for (Pair2 key : this.vertexConnectionsMap.keySet()) {
      int value = this.vertexConnectionsMap.get(key);
      if (value == 2) {
        Tunnel tunnel = new Tunnel();
        this.vertexLocationMap.put(key, tunnel);
        this.vertexTunnelMap.put(key, tunnel);
      } else {
        Cave cave = new Cave();
        this.vertexLocationMap.put(key, cave);
        this.vertexCaveMap.put(key, cave);
      }
    }
    this.createEntrancesToTunnels();
  }

  private void addTreasuresToCaves() {
    HashMap<Pair2, Location> vertexCaveMapCopy = new HashMap<>();
    for (Pair2 key : this.vertexCaveMap.keySet()) {
      vertexCaveMapCopy.put(key, this.vertexCaveMap.get(key));
    }
    int numCaves = vertexCaveMapCopy.size();
    double numCavesWithT = (numCaves * (this.percentOfTreasures * 0.01));
    int numCavesWithTreasure = (int) Math.ceil(numCavesWithT);

    if (this.isRandom) {
      Random rn = new Random();
      for (int i = 0; i < numCavesWithTreasure; i++) {
        int diff = numCaves - 1 - 0 + 1;
        int k = rn.nextInt(diff) + 0;
        Pair2 cavePair1 = this.getCaveByIndex(vertexCaveMapCopy, k);
        try {
          Cave toAddCave = (Cave) vertexCaveMapCopy.get(cavePair1);
          Random tr = new Random();
          int diffTr = 3 - 1 + 1;
          int numTreasures = tr.nextInt(diffTr) + 1;
          for (int t = 0; t < numTreasures; t++) {
            toAddCave.addTreasure(this.getRandomTreasureType());
          }
          vertexCaveMapCopy.remove(cavePair1);
        } catch (java.lang.NullPointerException e) {
          i--;
        }
      }
    } else {
      int z = 0;
      for (int i = 0; i < numCavesWithTreasure; i++) {
        int k = 0;
        Pair2 cavePair1 = this.getCaveByIndex(vertexCaveMapCopy, k);
        try {
          Cave toAddCave = (Cave) vertexCaveMapCopy.get(cavePair1);
          int numTreasures = 2;
          for (int t = 0; t < numTreasures; t++) {
            toAddCave.addTreasure(this.getNonRandomTreasureType(z % 4 + 1));
            z++;
          }
          vertexCaveMapCopy.remove(cavePair1);
        } catch (java.lang.NullPointerException e) {
          i--;
        }
      }
    }
  }


  private void addArrowsToLocations() {
    HashMap<Pair2, Location> vertexLocationMapCopy = new HashMap<>();
    for (Pair2 key : this.vertexLocationMap.keySet()) {
      vertexLocationMapCopy.put(key, this.vertexLocationMap.get(key));
    }
    int numLocations = vertexLocationMapCopy.size();
    double numLocWithA = (numLocations * (this.percentOfTreasures * 0.01));
    int numCavesWithArrows = (int) Math.ceil(numLocWithA);
    if (this.isRandom) {
      Random rn = new Random();
      for (int i = 0; i < numCavesWithArrows; i++) {
        numLocations = vertexLocationMapCopy.size();
        int diff = numLocations - 1 - 0 + 1;
        int k = rn.nextInt(diff) + 0;
        Pair2 cavePair1 = this.getLocationByIndex(vertexLocationMapCopy, k);
        try {
          Location toAddLoc = vertexLocationMapCopy.get(cavePair1);
          if (isLocCave(toAddLoc)) {
            Cave toAddCave = (Cave) toAddLoc;
            Random tr = new Random();
            int diffTr = 3 - 1 + 1;
            int numArrows = tr.nextInt(diffTr) + 1;
            for (int t = 0; t < numArrows; t++) {
              toAddCave.addArrowToCave(new Arrow());
            }
            vertexLocationMapCopy.remove(cavePair1);
          } else {
            Tunnel toAddTunnel = (Tunnel) toAddLoc;
            Random tr = new Random();
            int diffTr = 3 - 1 + 1;
            int numArrows = tr.nextInt(diffTr) + 1;
            for (int t = 0; t < numArrows; t++) {
              toAddTunnel.addArrowToTunnel(new Arrow());
            }
            vertexLocationMapCopy.remove(cavePair1);
          }
        } catch (java.lang.NullPointerException e) {
          //DONothing
        }
      }
    } else {
      for (int i = 0; i < numCavesWithArrows; i++) {
        int k = 0;
        Pair2 cavePair1 = this.getCaveByIndex(vertexLocationMapCopy, k);
        try {
          Location toAddLoc = vertexLocationMapCopy.get(cavePair1);
          if (isLocCave(toAddLoc)) {
            Cave toAddCave = (Cave) toAddLoc;
            int numTreasures = 2;
            for (int t = 0; t < numTreasures; t++) {
              toAddCave.addArrowToCave(new Arrow());
            }
            vertexLocationMapCopy.remove(cavePair1);
          } else {
            Tunnel toAddTunnel = (Tunnel) toAddLoc;
            int numTreasures = 2;
            for (int t = 0; t < numTreasures; t++) {
              toAddTunnel.addArrowToTunnel(new Arrow());
            }
            vertexLocationMapCopy.remove(cavePair1);
          }
        } catch (java.lang.NullPointerException e) {
          i--;
        }
      }
    }
  }

  private void addMonstersToCaves(int numMonsterToAdd) {
    HashMap<Pair2, Location> vertexCaveMapCopy = new HashMap<>();
    for (Pair2 key : this.vertexCaveMap.keySet()) {
      vertexCaveMapCopy.put(key, this.vertexCaveMap.get(key));
    }
    int numCaves = vertexCaveMapCopy.size();
    int numCavesWithMonster = numMonsterToAdd;
    if (numCavesWithMonster > vertexCaveMap.size() - 1) {
      numCavesWithMonster = vertexCaveMap.size();
    }
    int z = 0;
    for (int i = 0; i < numCavesWithMonster; i++) {
      int k = 0;
      Pair2 cavePair1 = this.getCaveByIndex(vertexCaveMapCopy, k);
      try {
        Cave toAddCave = (Cave) vertexCaveMapCopy.get(cavePair1);
        if (!toAddCave.isMonsterInCave()) {
          toAddCave.addMonsterToCave(new Otyugh());
        }
        vertexCaveMapCopy.remove(cavePair1);
      } catch (java.lang.NullPointerException e) {
        i--;
      }
    }
  }

  private Treasure getRandomTreasureType() {
    Random tr = new Random();
    int diffTr = 3 - 1 + 1;
    int treasureType = tr.nextInt(diffTr) + 1;
    if (treasureType == 1) {
      return new Treasure(TreasureType.DIAMONDS);
    } else if (treasureType == 2) {
      return new Treasure(TreasureType.RUBIES);
    } else {
      return new Treasure(TreasureType.SAPPHIRES);
    }
  }

  private Treasure getNonRandomTreasureType(int i) {
    int treasureType = i;
    if (treasureType == 1) {
      return new Treasure(TreasureType.DIAMONDS);
    } else if (treasureType == 2) {
      return new Treasure(TreasureType.RUBIES);
    } else {
      return new Treasure(TreasureType.SAPPHIRES);
    }
  }

  private Pair2 getCaveByIndex(HashMap<Pair2, Location> vertexCaveMapCopy, int index) {
    int checkIndex = 0;
    for (Pair2 key : vertexCaveMapCopy.keySet()) {
      if (checkIndex == index) {
        return key;
      }
    }
    return new Pair2(1, 1);
  }

  private Pair2 getLocationByIndex(HashMap<Pair2, Location> vertexLocationMapCopy, int index) {
    int checkIndex = 0;
    for (Pair2 key : vertexLocationMapCopy.keySet()) {
      if (checkIndex == index) {
        return key;
      }
      checkIndex++;
    }
    return new Pair2(1, 1);
  }

  private void createEntrancesToTunnels() {
    for (Pair2 key : this.vertexTunnelMap.keySet()) {
      Tunnel tunnel = (Tunnel) this.vertexTunnelMap.get(key);
      List<Location> entracesForTunnel = new ArrayList<>();
      for (PairOfPair pairOfPair : this.mstEdges) {
        if (key.equals(pairOfPair.getA())) {
          Location entrance1 = this.vertexLocationMap.get(pairOfPair.getB());
          entracesForTunnel.add(entrance1);
        } else if (key.equals(pairOfPair.getB())) {
          Location entrance2 = this.vertexLocationMap.get(pairOfPair.getA());
          entracesForTunnel.add(entrance2);
        }
      }
      tunnel.addEntrance1(entracesForTunnel.get(0));
      tunnel.addEntrance2(entracesForTunnel.get(1));
    }
  }

  protected String getPlayerMoves(Pair2 currentLoc) {
    StringBuilder str = new StringBuilder();
    str.append("\nThe possible directions the player can currently move are :");
    List<Pair2> possibleLocationsToMove = this.getPosToMove(currentLoc);
    for (Pair2 loc : possibleLocationsToMove) {
      str.append(this.getPlayerMovedDir(currentLoc, loc));
    }
    return str.toString();
  }

  protected List<String> getPossibleDirectionsToMoves(Pair2 currentLoc) {
    List<String> possDirToMove = new ArrayList<>();
    //str.append("\nThe possible directions the player can currently move are :");
    List<Pair2> possibleLocationsToMove = this.getPosToMove(currentLoc);
    for (Pair2 loc : possibleLocationsToMove) {
      possDirToMove.addAll(this.getMovedDirections(currentLoc, loc));
    }
    return possDirToMove;
  }

  private List<String> getMovedDirections(Pair2 currentLoc, Pair2 nextLoc) {
    List<String> str = new ArrayList<>();
    if (nextLoc.getA() == currentLoc.getA()) {
      if (nextLoc.getB() - 1 == currentLoc.getB()
          || nextLoc.getB() == (currentLoc.getB() - this.cols + 1)) {
        str.add("East");
        str.add("Right");
      } else if (nextLoc.getB() + 1 == currentLoc.getB()
          || nextLoc.getB() == (currentLoc.getB() + this.cols - 1)) {
        str.add("West");
        str.add("Left");
      }
    } else if (nextLoc.getB() == currentLoc.getB()) {
      if (nextLoc.getA() - 1 == currentLoc.getA()
          || nextLoc.getA() == (currentLoc.getA() - this.rows + 1)) {
        str.add("South");
        str.add("Down");
      } else if (nextLoc.getA() + 1 == currentLoc.getA()
          || nextLoc.getA() == (currentLoc.getA() - this.rows - 1)
          || nextLoc.getA() - this.rows + 1 == currentLoc.getA()) {
        str.add("North");
        str.add("Up");
      }
    }
    return str;
  }

  protected List<String> getPossibleDirectionsPlayerCanMove(Pair2 currentLoc) {
    //NSEW - Naming Convention they have followed
    List<String> possibleDirectionsToMove = new ArrayList<>();
    List<Pair2> possibleLocationsToMove = this.getPosToMove(currentLoc);
    for (Pair2 loc : possibleLocationsToMove) {
      possibleDirectionsToMove.add(this.getMovedDir(currentLoc, loc));
    }
    Collections.sort(possibleDirectionsToMove);
    return possibleDirectionsToMove;
  }

  private String getMovedDir(Pair2 currentLoc, Pair2 nextLoc) {
    StringBuilder str = new StringBuilder();
    if (nextLoc.getA() == currentLoc.getA()) {
      if (nextLoc.getB() - 1 == currentLoc.getB()
          || nextLoc.getB() == (currentLoc.getB() - this.cols + 1)) {
        str.append("East");
      } else if (nextLoc.getB() == currentLoc.getB() - 1
          || nextLoc.getB() == (currentLoc.getB() + this.cols - 1)) {
        str.append("West");
      }
    } else if (nextLoc.getB() == currentLoc.getB()) {
      if (nextLoc.getA() - 1 == currentLoc.getA()
          || nextLoc.getA() == (currentLoc.getA() - this.rows + 1)) {
        str.append("South");
      } else if (nextLoc.getA() + 1 == currentLoc.getA()
          || nextLoc.getA() == (currentLoc.getA() - this.rows - 1)
          || nextLoc.getA() - this.rows + 1 == currentLoc.getA()) {
        str.append("North");
      }
    }
    return str.toString();
  }

  protected String getPlayerMovedDir(Pair2 currentLoc, Pair2 nextLoc) {
    StringBuilder str = new StringBuilder();
    if (nextLoc.getA() == currentLoc.getA()) {
      if (nextLoc.getB() - 1 == currentLoc.getB()
          || nextLoc.getB() == (currentLoc.getB() + this.cols - 1)) {
        str.append("\nEast/Right");
      } else if (nextLoc.getB() + 1 == currentLoc.getB()
          || nextLoc.getB() == (currentLoc.getB() - this.cols - 1)) {
        str.append("\nWest/Left");
      }
    } else if (nextLoc.getB() == currentLoc.getB()) {
      if (nextLoc.getA() - 1 == currentLoc.getA()
          || nextLoc.getA() == (currentLoc.getA() - this.rows + 1)) {
        str.append("\nSouth/Down");
      } else if (nextLoc.getA() + 1 == currentLoc.getA()
          || nextLoc.getA() == (currentLoc.getA() - this.rows - 1)
          || nextLoc.getA() - this.rows + 1 == currentLoc.getA()) {
        str.append("\nNorth/Up");
      }
    }
    return str.toString();
  }

  protected String getMazeRepresentation() {
    StringBuilder str1 = new StringBuilder();
    HashMap<Integer, List<Integer>> vertexConnections = this.getVertexConnectionsHashMap();
    int colTrack = 0;
    for (int from : vertexConnections.keySet()) {
      colTrack++;
      str1.append(" " + from);
      List<Integer> connectedIntList  = vertexConnections.get(from);
      str1.append(this.getDirectionsForMazeRep(connectedIntList, from));
      if (colTrack == this.cols) {
        colTrack = 0;
        str1.append("\n");
      }
    }
    return str1.toString();
  }

  private String checkAndLootCave(Location curLocation) {
    StringBuilder str = new StringBuilder();
    if (isLocCave(curLocation)) {
      str.append(((Cave) curLocation).getTreasuresInCaveDesc());
      if (((Cave) curLocation).getNumberOfTreasuresInCave() > 0) {
        List<Treasure> lootedTreasure = ((Cave) curLocation).lootTreasuresInCave();
        this.player.addNewTreasure(lootedTreasure);
        str.append("\nPlayer has collected all the treasures present in the current Cave\n"
            + "\nThe updated Player treasure description is ");
        str.append(this.player.getPlayerTreasuresObtained());
      }
    }
    return str.toString();
  }

  private void checkForTreasureArrowInCave(Location curLocation, Scanner in, Appendable out) {
    if (isLocCave(curLocation)) {
      try {
        out.append(((Cave) curLocation).getTreasuresInCaveDesc());
        if (((Cave) curLocation).getNumberOfTreasuresInCave() > 0) {
          out.append("\nPick up all the Treasure? Yes or No :");
          String choiceTreasure = in.next();
          if (choiceTreasure.equals("Yes") || choiceTreasure.equals("yes")) {
            List<Treasure> lootedTreasure = ((Cave) curLocation).lootTreasuresInCave();
            this.player.addNewTreasure(lootedTreasure);
            out.append("\nPlayer has collected all the treasures present in the current Cave\n"
                + "\nThe updated Player treasure description is ");
            out.append(this.player.getPlayerTreasuresObtained());
          }
        }
        if (((Cave) curLocation).getNumArrows() > 0) {
          out.append("\nPick up all the Arrows? Yes or No :");
          String choiceArrows = in.next();
          if (choiceArrows.equals("Yes") || choiceArrows.equals("yes")) {
            List<Arrow> lootedArrows = ((Cave) curLocation).lootArrowsInCave();
            this.player.equipPlayerWithArrow(lootedArrows);
            out.append("\nPlayer has collected all the Arrows present in the current Cave"
                + "\nThe updated Player Arrow description is ");
            out.append(this.player.getPlayerArrowsObtained());
          }
        }
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    } else {
      try {
        if (((Tunnel) curLocation).getNumArrows() > 0) {
          out.append("Pick up all the Arrows? Yes or No :");
          String choiceArrows = in.next();
          if (choiceArrows.equals("Yes") || choiceArrows.equals("yes")) {
            List<Arrow> lootedArrows = ((Tunnel) curLocation).lootArrowsInTunnel();
            this.player.equipPlayerWithArrow(lootedArrows);
            out.append("\nPlayer has collected all the Arrows present in the current Tunnel"
                + "\nThe updated Player Arrow description is \n");
            out.append(this.player.getPlayerArrowsObtained());
          }
        }
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    }
  }


  protected void checkForOtyughSmell(Pair2 curLoc, Appendable out) {
    List<Pair2> possibleLocationsToMoveOneStep = this.getPossibleLocationsToMove(curLoc);
    int count1 = 0;
    int count2 = 0;
    for (Pair2 reachedSateOneStep : possibleLocationsToMoveOneStep) {
      Location futureLocationOneStep = vertexLocationMap.get(reachedSateOneStep);
      if (isLocCave(futureLocationOneStep)) {
        if (((Cave) futureLocationOneStep).isMonsterInCave()) {
          count1 += 1;
        }
      }
      List<Pair2> possibleLocationsToMoveTwoStep =
          this.getPossibleLocationsToMove(reachedSateOneStep);
      for (Pair2 reachedSateTwoStep : possibleLocationsToMoveTwoStep) {
        Location futureLocationTwoStep = vertexLocationMap.get(reachedSateTwoStep);
        if (isLocCave(futureLocationTwoStep)) {
          if (((Cave) futureLocationTwoStep).isMonsterInCave()) {
            count2 += 1;
          }
        }
      }
    }
    try {
      if (count1 >= 1 || count2 >= 2) {
        out.append("\nA Strong Pungent Smell Detected from this location\n");
      } else if (count2 == 1) {
        out.append("\nA Weak Pungent Smell Detected from this location\n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  protected boolean checkForOtyughInCave(Pair2 curLoc, Appendable out) {
    Location curLocation = vertexLocationMap.get(curLoc);
    if (isLocCave(curLocation)) {
      try {
        if (((Cave) curLocation).isMonsterInCave()) {
          if (((Cave) curLocation).isMonsterInjured()) {
            if (this.isRandom) {
              Random rn = new Random();
              int diff = 2;
              int i = rn.nextInt(diff); //i is either 0 or 1
              if (i == 0) {
                out.append("\nYou are lucky, as the Otyugh was injured it didn't notice you");
                return false;
              }
            } else {
              out.append("\nYou are lucky, as the Otyugh was injured it didn't notice you");
              return false;
            }
          }
          out.append("\nYou are Dead. The Monster ate you\n");
          this.player.setPlayerToDead();
          return true;
        }
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    }
    return false;
  }

  protected boolean checkForMonsterInCave(Pair2 curLoc) {
    Location curLocation = vertexLocationMap.get(curLoc);
    if (isLocCave(curLocation)) {
      if (((Cave) curLocation).isMonsterInCave()) {
        if (((Cave) curLocation).isMonsterInjured()) {
          if (this.isRandom) {
            Random rn = new Random();
            int diff = 2;
            int i = rn.nextInt(diff); //i is either 0 or 1
            if (i == 0) {
              //out.append("\nYou are lucky, as the Otyugh was injured it didn't notice you");
              return false;
            }
          } else {
            //out.append("\nYou are lucky, as the Otyugh was injured it didn't notice you");
            return false;
          }
        }
        //out.append("\nYou are Dead. The Monster ate you\n");
        this.player.setPlayerToDead();
        return true;
      }
    }
    return false;
  }


  protected void shootArrow(Pair2 curLoc, Scanner in, Appendable out) {
    if (this.player.getNumOfArrowsRemaining() > 0) {
      try {
        while (this.player.getNumOfArrowsRemaining() > 0) {
          out.append("\nDo you want to shoot an Arrow? Yes or No ");
          String choice = in.next();
          if (choice.equalsIgnoreCase("Yes")) {
            out.append("Enter the direction to shoot the arrow ");
            String direction = in.next();
            //Check for direction here
            if (this.getPossibleDirectionsToMoves(curLoc).contains(direction)) {
              out.append("Enter the distance to shoot the arrow ");
              try {
                int distance = in.nextInt();

                this.releaseArrow(curLoc, direction, distance, out);
                this.player.useOneArrow();
              } catch (InputMismatchException ime) {
                out.append("Distance must be an Integer");
              }
            } else {
              out.append("\nInvalid Direction");
            }
          } else if (choice.equalsIgnoreCase("No")) {
            break;
          } else {
            out.append("\nInvalid Input");
          }
        }
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    }
  }

  private boolean isLocCave(Location curLoc) {
    return (curLoc.getClass() == Cave.class);
  }

  private void releaseArrow(Pair2 playerLoc, String userInputDirection,
                             int distance, Appendable out) {
    String direction = userInputDirection;
    Pair2 curLoc = playerLoc;
    Pair2 prevLoc = playerLoc;
    boolean isCurvePossible = true;
    int hops = 0;
    try {
      for (int i = 0; i < (distance * 4); i++) {
        if (hops < distance) {
          List<Pair2> possibleLocationsToMove = this.getPossibleLocationsToMove(curLoc);
          if (this.getPossibleDirectionsToMoves(curLoc).contains(direction)) {
            for (Pair2 reachedState : possibleLocationsToMove) {
              Location reachedLocation = vertexLocationMap.get(reachedState);
              if (isLocCave(reachedLocation)) {
                if (direction.equalsIgnoreCase("Right") || direction.equalsIgnoreCase("East")) {
                  if (reachedState.getA() == curLoc.getA()) {
                    if (reachedState.getB() - 1 == curLoc.getB()
                        || reachedState.getB() == (curLoc.getB() + this.cols - 1)) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      hops += 1;
                    }
                  }
                } else if (direction.equalsIgnoreCase("Left")
                    || direction.equalsIgnoreCase("West")) {
                  if (reachedState.getA() == curLoc.getA()) {
                    if (reachedState.getB() + 1 == curLoc.getB()
                        || reachedState.getB() == (curLoc.getB() - this.cols - 1)) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      hops += 1;
                      continue;
                    }
                  }
                } else if (direction.equalsIgnoreCase("Down")
                    || direction.equalsIgnoreCase("South")) {
                  if (reachedState.getB() == curLoc.getB()) {
                    if (reachedState.getA() - 1 == curLoc.getA()
                        || reachedState.getA() == (curLoc.getA() - this.rows + 1)) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      hops += 1;
                      continue;
                    }
                  }
                } else if (direction.equalsIgnoreCase("Up")
                    || direction.equalsIgnoreCase("North")) {
                  if (reachedState.getB() == curLoc.getB()) {
                    if (reachedState.getA() + 1 == curLoc.getA()
                        || reachedState.getA() == (curLoc.getA() - this.rows - 1)
                        || reachedState.getA() - this.rows + 1 == curLoc.getA()) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      hops += 1;
                      continue;
                    }
                  }
                } //End North
              } else { //Direction is present in a tunnel
                if (direction.equalsIgnoreCase("Right")
                    || direction.equalsIgnoreCase("East")) {
                  //System.out.println("Tunnel From: " + curLoc.toString()
                  // + " To: " + reachedState.toString());
                  if (reachedState.getA() == curLoc.getA()) {
                    if (reachedState.getB() - 1 == curLoc.getB()
                        || reachedState.getB() == (curLoc.getB() + this.cols - 1)) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      continue;
                    }
                  }
                } else if (direction.equalsIgnoreCase("Left")
                    || direction.equalsIgnoreCase("West")) {
                  if (reachedState.getA() == curLoc.getA()) {
                    if (reachedState.getB() + 1 == curLoc.getB()
                        || reachedState.getB() == (curLoc.getB() - this.cols - 1)) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      continue;
                    }
                  }
                } else if (direction.equalsIgnoreCase("Down")
                    || direction.equalsIgnoreCase("South")) {
                  if (reachedState.getB() == curLoc.getB()) {
                    if (reachedState.getA() - 1 == curLoc.getA()
                        || reachedState.getA() == (curLoc.getA() - this.rows + 1)) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      continue;
                    }
                  }
                } else if (direction.equalsIgnoreCase("Up")
                    || direction.equalsIgnoreCase("North")) {
                  if (reachedState.getB() == curLoc.getB()) {
                    if (reachedState.getA() + 1 == curLoc.getA()
                        || reachedState.getA() == (curLoc.getA() - this.rows - 1)
                        || reachedState.getA() - this.rows + 1 == curLoc.getA()) {
                      prevLoc = curLoc;
                      curLoc = reachedState;
                      continue;
                    }
                  }
                }
              }
            }
          } else { //If it contains the direction to move,
            // now we have it if doesn't contain the direction to move
            Location curLocation = vertexLocationMap.get(curLoc);
            if (!prevLoc.equals(curLoc) && !isLocCave(curLocation) && isCurvePossible) {
              for (Pair2 reachedState : possibleLocationsToMove) {
                if (!reachedState.equals(prevLoc)) {
                  isCurvePossible = false;
                  direction = this.getMovedDir(curLoc, reachedState);
                  prevLoc = curLoc;
                  curLoc = reachedState;
                }
              }
            }
          }
        } else {
          break;
        }
      } //End for
      Location curLocation = vertexLocationMap.get(curLoc);
      if (this.isLocCave(curLocation) && hops == distance) {
        Cave thisCave = (Cave) curLocation;
        if (thisCave.isMonsterInCave()) {
          thisCave.attackMonsterInCave();
          try {
            if (thisCave.isMonsterInCave()) {
              out.append("\nWe hear a continuous roar from a Cave");
            } else {
              out.append("\nWe hear a loud roar from a Cave which stops after a minute");
            }
          } catch (IOException ioe1) {
            throw new IllegalStateException("Append failed", ioe1);
          }
        }
      } else {
        out.append("\nArrow did not hit anything");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }




  private String releaseArrowFromCurLoc(Pair2 playerLoc, String userInputDirection,
                            int distance) {
    StringBuilder str = new StringBuilder();
    String direction = userInputDirection;
    Pair2 curLoc = playerLoc;
    Pair2 prevLoc = playerLoc;
    boolean isCurvePossible = true;
    int hops = 0;

    for (int i = 0; i < (distance * 4); i++) {
      if (hops < distance) {
        List<Pair2> possibleLocationsToMove = this.getPossibleLocationsToMove(curLoc);
        if (this.getPossibleDirectionsToMoves(curLoc).contains(direction)) {
          for (Pair2 reachedState : possibleLocationsToMove) {
            Location reachedLocation = vertexLocationMap.get(reachedState);
            if (isLocCave(reachedLocation)) {
              if (direction.equalsIgnoreCase("Right") || direction.equalsIgnoreCase("East")) {
                if (reachedState.getA() == curLoc.getA()) {
                  if (reachedState.getB() - 1 == curLoc.getB()
                      || reachedState.getB() == (curLoc.getB() + this.cols - 1)) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    hops += 1;
                  }
                }
              } else if (direction.equalsIgnoreCase("Left")
                  || direction.equalsIgnoreCase("West")) {
                if (reachedState.getA() == curLoc.getA()) {
                  if (reachedState.getB() + 1 == curLoc.getB()
                      || reachedState.getB() == (curLoc.getB() - this.cols - 1)) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    hops += 1;
                    continue;
                  }
                }
              } else if (direction.equalsIgnoreCase("Down")
                  || direction.equalsIgnoreCase("South")) {
                if (reachedState.getB() == curLoc.getB()) {
                  if (reachedState.getA() - 1 == curLoc.getA()
                      || reachedState.getA() == (curLoc.getA() - this.rows + 1)) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    hops += 1;
                    continue;
                  }
                }
              } else if (direction.equalsIgnoreCase("Up")
                  || direction.equalsIgnoreCase("North")) {
                if (reachedState.getB() == curLoc.getB()) {
                  if (reachedState.getA() + 1 == curLoc.getA()
                      || reachedState.getA() == (curLoc.getA() - this.rows - 1)
                      || reachedState.getA() - this.rows + 1 == curLoc.getA()) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    hops += 1;
                    continue;
                  }
                }
              } //End North
            } else { //Direction is present in a tunnel
              if (direction.equalsIgnoreCase("Right")
                  || direction.equalsIgnoreCase("East")) {
                //System.out.println("Tunnel From: " + curLoc.toString()
                // + " To: " + reachedState.toString());
                if (reachedState.getA() == curLoc.getA()) {
                  if (reachedState.getB() - 1 == curLoc.getB()
                      || reachedState.getB() == (curLoc.getB() + this.cols - 1)) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    continue;
                  }
                }
              } else if (direction.equalsIgnoreCase("Left")
                  || direction.equalsIgnoreCase("West")) {
                if (reachedState.getA() == curLoc.getA()) {
                  if (reachedState.getB() + 1 == curLoc.getB()
                      || reachedState.getB() == (curLoc.getB() - this.cols - 1)) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    continue;
                  }
                }
              } else if (direction.equalsIgnoreCase("Down")
                  || direction.equalsIgnoreCase("South")) {
                if (reachedState.getB() == curLoc.getB()) {
                  if (reachedState.getA() - 1 == curLoc.getA()
                      || reachedState.getA() == (curLoc.getA() - this.rows + 1)) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    continue;
                  }
                }
              } else if (direction.equalsIgnoreCase("Up")
                  || direction.equalsIgnoreCase("North")) {
                if (reachedState.getB() == curLoc.getB()) {
                  if (reachedState.getA() + 1 == curLoc.getA()
                      || reachedState.getA() == (curLoc.getA() - this.rows - 1)
                      || reachedState.getA() - this.rows + 1 == curLoc.getA()) {
                    prevLoc = curLoc;
                    curLoc = reachedState;
                    continue;
                  }
                }
              }
            }
          }
        } else { //If it contains the direction to move,
          // now we have it if doesn't contain the direction to move
          Location curLocation = vertexLocationMap.get(curLoc);
          if (!prevLoc.equals(curLoc) && !isLocCave(curLocation) && isCurvePossible) {
            for (Pair2 reachedState : possibleLocationsToMove) {
              if (!reachedState.equals(prevLoc)) {
                isCurvePossible = false;
                direction = this.getMovedDir(curLoc, reachedState);
                prevLoc = curLoc;
                curLoc = reachedState;
              }
            }
          }
        }
      } else {
        //str.append("Arrow did not hit anything");
        //System.out.println("Arrrrooooww");
        break;
      }
    } //End for
    Location curLocation = vertexLocationMap.get(curLoc);
    if (this.isLocCave(curLocation) && hops == distance) {
      Cave thisCave = (Cave) curLocation;
      if (thisCave.isMonsterInCave()) {
        thisCave.attackMonsterInCave();
        if (thisCave.isMonsterInCave()) {
          //out.append("\nWe hear a continuous roar from a Cave");
          str.append("We hear a continuous roar from a Cave");
        } else {
          str.append("We hear a loud roar from a Cave which stops after a minute");
          //out.append("\nWe hear a loud roar from a Cave which stops after a minute");
        }
      }
    } else {
      //out.append("\nArrow did not hit anything");
      str.append("Arrow did not hit anything");
    }
    if (str.length() == 0) {
      str.append("Arrow did not hit anything");
    }
    return str.toString();
  }




  private int getNumRuns(Pair2 curState, Pair2 goalState) {
    Stack<Pair2> queue = new Stack<>();
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 0;
    while (!curState.equals(goalState) && run < 1000) {
      curState = queue.pop();
      explored.add(curState);
      List<Pair2> possibleLocationsToMove = this.getPosToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        if (reachedSate.equals(goalState)) {
          return run;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
          }
        }
      }
      run++;
    }
    return 9999999;
  }


  protected List<Pair2> getPosToMove(Pair2 curPos) {
    List<Pair2> possibleLocationsToMove = new ArrayList<>();
    for (PairOfPair curPair : mstEdges) {
      if (curPos.equals(curPair.getA())) {
        Pair2 possibleLoc = curPair.getB();
        possibleLocationsToMove.add(possibleLoc);
      } else if (curPos.equals(curPair.getB())) {
        Pair2 possibleLoc = curPair.getA();
        possibleLocationsToMove.add(possibleLoc);
      }
    }
    return possibleLocationsToMove;
  }

  protected void updatePlayerLocation(Pair2 startState) {
    Location curLoc = this.vertexLocationMap.get(startState);
    this.player.updatePlayerLocation(curLoc.toString());
  }

  protected Pair2 getStartState() {
    int p = 0;
    Pair2 startState = mstEdges.get(p).getA();
    Location curLoc = this.vertexLocationMap.get(startState);
    while (!isLocCave(curLoc)) {
      p++;
      startState = mstEdges.get(p).getA();
      curLoc = this.vertexLocationMap.get(startState);
    }
    //Check this for Random Cave
    ((Cave) curLoc).removeMonsterFromCave();
    return startState;
  }

  protected Pair2 getGoalState(Pair2 startState) {
    Pair2 goalState;
    Location goalLoc;
    //Start of Random
    if (this.isRandom) {
      Random rn = new Random();
      int diff = mstEdges.size() - 1 - 0 + 1;
      int i = rn.nextInt(diff) + 0;
      goalState = mstEdges.get(i).getB();
      goalLoc = this.vertexLocationMap.get(goalState);
      if (goalState.equals(startState)) {
        i = rn.nextInt(diff) + 0;
        goalState = mstEdges.get(i).getB();
        goalLoc = this.vertexLocationMap.get(goalState);
      }
      int runValue = this.getNumRuns(startState, goalState);
      while (runValue <= 5 || runValue == 9999999 || !isLocCave(goalLoc)) {
        i = rn.nextInt(diff) + 0;
        goalState = mstEdges.get(i).getB();
        runValue = this.getNumRuns(startState, goalState);
        goalLoc = this.vertexLocationMap.get(goalState);
      }
    } else {
      int i = mstEdges.size() - 1;
      goalState = mstEdges.get(i).getB();
      goalLoc = this.vertexLocationMap.get(goalState);
      if (goalState.equals(startState)) {
        i += 1;
        goalState = mstEdges.get(i).getB();
        goalLoc = this.vertexLocationMap.get(goalState);
      }
      int runValue = this.getNumRuns(startState, goalState);
      int k = 0;
      while ((runValue < 4 || runValue == 9999999
          || !isLocCave(goalLoc)) && k < mstEdges.size() - 1) {
        k++;
        goalState = mstEdges.get(k).getB();
        runValue = this.getNumRuns(startState, goalState);
        goalLoc = this.vertexLocationMap.get(goalState);
      }
    }
    Cave goalCave = (Cave) goalLoc;
    if (!goalCave.isMonsterInCave()) {
      goalCave.addMonsterToCave(new Otyugh());
    } else {
      this.addMonstersToCaves(1);
    }
    return goalState;
  }

  protected void checkForTreasureInCaves(Pair2 curState, Scanner in, Appendable out) {
    Location curLocation = vertexLocationMap.get(curState);
    this.checkForTreasureArrowInCave(curLocation, in, out);
  }

  protected String checkAndLootCaves(Pair2 curState) {
    Location curLocation = vertexLocationMap.get(curState);
    return this.checkAndLootCave(curLocation);
  }

  protected List<Pair2> getPossibleLocationsToMove(Pair2 curState) {
    return this.getPosToMove(curState);
  }

  protected String getMazeRepresentationWithCavesAndTunnels() {
    StringBuilder str1 = new StringBuilder();
    HashMap<Integer, List<Integer>> vertexConnections = this.getVertexConnectionsHashMap();
    int colTrack = 0;
    for (int from : vertexConnections.keySet()) {
      colTrack++;
      str1.append(" " + from);
      List<Integer> connectedIntList  = vertexConnections.get(from);
      if (connectedIntList.size() - 1 == 2) {
        str1.append("(T)");
      } else {
        str1.append("(C)");
      }
      str1.append(this.getDirectionsForMazeRep(connectedIntList, from));
      if (colTrack == this.cols) {
        colTrack = 0;
        str1.append("\n");
      }
    }
    return str1.toString();
  }

  protected String getMazeRepresentationWithTreasures() {
    StringBuilder str1 = new StringBuilder();
    HashMap<Integer, List<Integer>> vertexConnections = this.getVertexConnectionsHashMap();
    int colTrack = 0;
    for (int from : vertexConnections.keySet()) {
      colTrack++;
      str1.append(" " + from);
      List<Integer> connectedIntList  = vertexConnections.get(from);
      if (connectedIntList.size() - 1 == 2) {
        str1.append("(T)");
      } else {
        str1.append("(C)");
        try {
          HashMap<Integer, Pair2> vertexMap = this.maze.getVertexMap();
          Pair2 thisCaveIntRep = vertexMap.get(from);
          Cave thisCave = (Cave) this.vertexCaveMap.get(thisCaveIntRep);
          str1.append(thisCave.getTreasureInCaveShortRep());
        } catch (NullPointerException e) {
          str1.append("[ ]");
        }
      }
      if (colTrack == this.cols) {
        colTrack = 0;
        str1.append("\n");
      }
    }
    return str1.toString();
  }

  protected String getMazeRepresentationWithArrows() {
    StringBuilder str1 = new StringBuilder();
    HashMap<Integer, List<Integer>> vertexConnections = this.getVertexConnectionsHashMap();
    int colTrack = 0;
    for (int from : vertexConnections.keySet()) {
      colTrack++;
      str1.append(" " + from);
      List<Integer> connectedIntList  = vertexConnections.get(from);
      if (connectedIntList.size() - 1 == 2) {
        str1.append("(T)");
        try {
          HashMap<Integer, Pair2> vertexMap = this.maze.getVertexMap();
          Pair2 thisTunnelIntRep = vertexMap.get(from);
          Tunnel thisTunnel = (Tunnel) this.vertexTunnelMap.get(thisTunnelIntRep);
          str1.append(thisTunnel.getArrowsInTunnelShortRep());
        } catch (NullPointerException e) {
          HashMap<Integer, Pair2> vertexMap = this.maze.getVertexMap();
          Pair2 thisTunnelIntRep = vertexMap.get(from);
          Cave thisCave = (Cave) this.vertexCaveMap.get(thisTunnelIntRep);
          str1.append(thisCave.getArrowsInCaveShortRep());
          //str1.append("[ ]");
        }
      } else {
        str1.append("(C)");
        try {
          HashMap<Integer, Pair2> vertexMap = this.maze.getVertexMap();
          Pair2 thisCaveIntRep = vertexMap.get(from);
          Cave thisCave = (Cave) this.vertexCaveMap.get(thisCaveIntRep);
          str1.append(thisCave.getArrowsInCaveShortRep());
        } catch (NullPointerException e) {
          HashMap<Integer, Pair2> vertexMap = this.maze.getVertexMap();
          Pair2 thisCaveIntRep = vertexMap.get(from);
          Tunnel thisTunnel = (Tunnel) this.vertexTunnelMap.get(thisCaveIntRep);
          str1.append(thisTunnel.getArrowsInTunnelShortRep());
          //str1.append("[ ]");
        }
      }
      if (colTrack == this.cols) {
        colTrack = 0;
        str1.append("\n");
      }
    }
    return str1.toString();
  }

  protected String getMazeRepresentationWithOtyugh() {
    StringBuilder str1 = new StringBuilder();
    HashMap<Integer, List<Integer>> vertexConnections = this.getVertexConnectionsHashMap();
    int colTrack = 0;
    for (int from : vertexConnections.keySet()) {
      colTrack++;
      str1.append(" " + from);
      List<Integer> connectedIntList  = vertexConnections.get(from);

      if (connectedIntList.size() - 1 == 2) {
        str1.append("(T)");
        str1.append("NO");
      } else {
        str1.append("(C)");
        try {
          HashMap<Integer, Pair2> vertexMap = this.maze.getVertexMap();
          Pair2 thisCaveIntRep = vertexMap.get(from);
          Cave thisCave = (Cave) this.vertexCaveMap.get(thisCaveIntRep);
          str1.append(thisCave.getMonsterInCaveShortRep());
        } catch (NullPointerException e) {
          str1.append("[ ]");
        }
      }
      if (colTrack == this.cols) {
        colTrack = 0;
        str1.append("\n");
      }
    }
    return str1.toString();
  }

  private HashMap<Integer, List<Integer>> getVertexConnectionsHashMap() {
    HashMap<Pair2, Integer> vertexInt = maze.getVertexIntMap();
    HashMap<Integer, List<Integer>> vertexConnections = new HashMap<>();
    for (int i = 1; i <= (this.rows * this.cols); i++) {
      List<Integer> intList = new ArrayList<Integer>();
      intList.add(0);
      vertexConnections.put(i, intList);
    }
    for (PairOfPair pairOfPair : mstEdges) {
      Pair2 p1 = pairOfPair.getA();
      Pair2 p2 = pairOfPair.getB();
      int vertex1 = vertexInt.get(p1);
      int vertex2 = vertexInt.get(p2);
      List<Integer> intList1 = vertexConnections.get(vertex1);
      intList1.add(vertex2);
      vertexConnections.put(vertex1, intList1);
      List<Integer> intList2 = vertexConnections.get(vertex2);
      intList2.add(vertex1);
      vertexConnections.put(vertex2, intList2);
    }
    return vertexConnections;
  }

  private String getDirectionsForMazeRep(List<Integer> connectedIntList, int from) {
    List<String> directions = new ArrayList<>();
    for (int to : connectedIntList) {
      if (to != 0) {
        if (to - this.cols + 1 == from || to + 1 == from) {
          directions.add("L");
        } else if (to == from - this.cols + 1 || to - 1 == from) {
          directions.add("R");
        } else if (to == from + this.cols || to == from - (this.rows * this.cols) + this.cols) {
          directions.add("D");
        } else if (to == from + (this.rows * this.cols) - this.cols || from == to + this.cols) {
          directions.add("U");
        }
      }
    }
    Collections.sort(directions);
    return directions.toString();
  }

  protected boolean isPlayerAlive() {
    return this.player.isPlayerAlive;
  }

  protected Pair2 moveByDirection(Pair2 curState, String userInpDirToMove) {
    //Take Curstate and move in the given direction and return the resultant state
    List<Pair2> possibleLocationsToMove = this.getPossibleLocationsToMove(curState);
    for (Pair2 reachedSate : possibleLocationsToMove) {
      if (userInpDirToMove.equals("Right") || userInpDirToMove.equals("East")) {
        if (reachedSate.getA() == curState.getA()) {
          if (reachedSate.getB() - 1 == curState.getB()
              || reachedSate.getB() == (curState.getB() - this.cols + 1)) {
            return reachedSate;
          }
        }
      } else if (userInpDirToMove.equals("Left") || userInpDirToMove.equals("West")) {
        if (reachedSate.getA() == curState.getA()) {
          if (reachedSate.getB() + 1 == curState.getB()
              || reachedSate.getB() == (curState.getB() + this.cols - 1)) {
            return reachedSate;
          }
        }
      } else if (userInpDirToMove.equals("Down") || userInpDirToMove.equals("South")) {
        if (reachedSate.getB() == curState.getB()) {
          if (reachedSate.getA() - 1 == curState.getA()
              || reachedSate.getA() == (curState.getA() - this.rows + 1)) {
            return reachedSate;
          }
        }
      } else if (userInpDirToMove.equals("Up") || userInpDirToMove.equals("North")) {
        if (reachedSate.getB() == curState.getB()) {
          if (reachedSate.getA() + 1 == curState.getA()
              || reachedSate.getA() == (curState.getA() - this.rows - 1)
              || reachedSate.getA() - this.rows + 1 == curState.getA()) {
            return reachedSate;
          }
        }
      }
    }
    return curState;
  }

  protected boolean isStateEqualToGoalState(Pair2 currState, Pair2 goalState, Appendable out) {
    if (currState.equals(goalState)) {
      if (this.checkForOtyughInCave(currState, out)) {
        return true;
      }
      this.setGoalStateReached();
      try {
        out.append("\nCongratulations!\nYou have reached Goal Location\n");
        return true;
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    }
    return false;
  }

  protected boolean isCurrStateEqualToGoalState(Pair2 currState, Pair2 goalState) {
    if (currState.equals(goalState)) {
      if (this.checkForMonsterInCave(currState)) {
        return true;
      }
      this.setGoalStateReached();
      //out.append("\nCongratulations!\nYou have reached Goal Location\n");
      return true;
    }
    return false;
  }

  protected void setGoalStateReached() {
    this.isGoalReached = true;
  }

  protected boolean isGoalStateReached() {
    return this.isGoalReached;
  }

  protected boolean getArrowsPresentInLoc(Pair2 curLoc) {
    List<Arrow> arrowList = new ArrayList<>();
    Location currentLocation = vertexLocationMap.get(curLoc);
    if (isLocCave(currentLocation)) {
      if (((Cave) currentLocation).getNumArrows() > 0) {
        return true;
      }
    } else {
      if (((Tunnel) currentLocation).getNumArrows() > 0) {
        return true;
      }
    }
    return false;
  }

  protected boolean getTreasurePresentInLoc(Pair2 curLoc) {
    List<Treasure> treasureList = new ArrayList<>();
    Location currentLocation = vertexLocationMap.get(curLoc);
    if (isLocCave(currentLocation)) {
      if (((Cave) currentLocation).getNumberOfTreasuresInCave() > 0) {
        return true;
      }
    }
    return false;
  }

  protected int getStenchLevelInLoc(Pair2 curLoc) {
    List<Pair2> possibleLocationsToMoveOneStep = this.getPossibleLocationsToMove(curLoc);
    int count1 = 0;
    int count2 = 0;
    int stenchLevel = 0;
    for (Pair2 reachedSateOneStep : possibleLocationsToMoveOneStep) {
      Location futureLocationOneStep = vertexLocationMap.get(reachedSateOneStep);
      if (isLocCave(futureLocationOneStep)) {
        if (((Cave) futureLocationOneStep).isMonsterInCave()) {
          count1 += 1;
        }
      }
      List<Pair2> possibleLocationsToMoveTwoStep =
          this.getPossibleLocationsToMove(reachedSateOneStep);
      for (Pair2 reachedSateTwoStep : possibleLocationsToMoveTwoStep) {
        Location futureLocationTwoStep = vertexLocationMap.get(reachedSateTwoStep);
        if (isLocCave(futureLocationTwoStep)) {
          if (((Cave) futureLocationTwoStep).isMonsterInCave()) {
            count2 += 1;
          }
        }
      }
    }
    if (count1 >= 1 || count2 >= 2) {
      //System.out.println("count1 " + count1 + "count2 " + count2);
      //out.append("\nA Strong Pungent Smell Detected from this location\n");
      stenchLevel = 2;
    } else if (count2 == 1) {
      //System.out.println("count2 " + count2);
      //out.append("\nA Weak Pungent Smell Detected from this location\n");
      stenchLevel = 1;
    }
    return stenchLevel;
  }

  protected boolean pickUpTreasureInCurLoc(Pair2 curLoc) {
    Location currentLocation = vertexLocationMap.get(curLoc);
    if (isLocCave(currentLocation)) {
      if (((Cave) currentLocation).getNumberOfTreasuresInCave() > 0) {
        List<Treasure> lootedTreasure = ((Cave) currentLocation).lootTreasuresInCave();
        this.player.addNewTreasure(lootedTreasure);
        return true;
      }
    }
    return false;
  }

  protected boolean pickUpArrowsInCurLoc(Pair2 curLoc) {
    Location currentLocation = vertexLocationMap.get(curLoc);
    if (isLocCave(currentLocation)) {
      if (((Cave) currentLocation).getNumArrows() > 0) {

        //out.append("\nPick up all the Arrows? Yes or No :");
        //String choiceArrows = in.next();
        //if (choiceArrows.equals("Yes") || choiceArrows.equals("yes")) {
        List<Arrow> lootedArrows = ((Cave) currentLocation).lootArrowsInCave();
        this.player.equipPlayerWithArrow(lootedArrows);
        return true;
      }
    } else {
      if (((Tunnel) currentLocation).getNumArrows() > 0) {
        List<Arrow> lootedArrows = ((Tunnel) currentLocation).lootArrowsInTunnel();
        this.player.equipPlayerWithArrow(lootedArrows);
        return true;
      }
    }
    return false;
  }

  protected String shootArrowFromCurLoc(Pair2 curLoc, String input) {
    StringBuilder str = new StringBuilder();
    if (this.player.getNumOfArrowsRemaining() > 0) {
      //System.out.println("Player has more than 1 arrows! YaaY");
      String[] inpSplit = input.split(" ");
      if (inpSplit.length != 2) {
        str.append("Invalid Input\nCorrect Input Format is \n(Direction, Distance)");
        return str.toString();
      }
      String direction = inpSplit[0];
      //Check for direction here
      if (this.getPossibleDirectionsToMoves(curLoc).contains(direction)) {
        //out.append("Enter the distance to shoot the arrow ");
        try {
          int distance = Integer.parseInt(inpSplit[1]);
          if (distance <= 0) {
            throw new InputMismatchException("Invalid Input");
          }
          str.append(this.releaseArrowFromCurLoc(curLoc, direction, distance));
          this.player.useOneArrow();
        } catch (InputMismatchException | NumberFormatException ie) {
          str.append("Distance must be a Positive Integer");
        }
      } else {

        if (direction.equals("East") || direction.equals("West")
            || direction.equals("North") || direction.equals("South")) {
          str.append("Arrow did not hit anything");
        } else {
          str.append("Invalid Direction");
        }
      }
    }
    return str.toString();
  }

  protected boolean getOtyughPresentInLoc(Pair2 curLoc) {
    Location curLocation = vertexLocationMap.get(curLoc);
    if (isLocCave(curLocation)) {
      if (((Cave) curLocation).isMonsterInCave()) {
        return true;
      }
    }
    return false;
  }

  protected String getDungeonPlayerLocationDetails(Pair2 curLoc) {
    Location curLocation = vertexLocationMap.get(curLoc);
    StringBuilder str = new StringBuilder();
    if (isLocCave(curLocation)) {
      str.append("You are in a Cave");
      str.append(((Cave) curLocation).getTreasuresInCaveDesc());
      str.append(((Cave) curLocation).getArrowsInCaveDesc());
      if (((Cave) curLocation).isMonsterInCave()) {
        str.append("\nOtyugh is Present in this Cave");
      } else {
        str.append("\nNo Otyugh Present in this Cave");
      }
    } else {
      str.append("You are in a Tunnel");
      if (((Tunnel) curLocation).getNumArrows() > 0) {
        str.append("\nNumber of Arrows Present: ");
        str.append(((Tunnel) curLocation).getNumArrows());
      } else {
        str.append("\nNumber of Arrows Present: 0");
      }
    }
    int level = this.getStenchLevelInLoc(curLoc);
    if (level == 0) {
      str.append("\nNo Smell Detected");
    } else if (level == 1) {
      str.append("\nWeak Pungent Smell Detected");
    } else if (level == 2) {
      str.append("\nStrong Pungent Smell Detected");
    }
    return str.toString();
  }

  protected String getPlayerFullDesc() {
    StringBuilder str = new StringBuilder();
    str.append(this.player.getPlayerCompleteDesc());
    return str.toString();
  }





} //End of Class



