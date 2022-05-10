package dungeonmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Maze class contains the methods required to create a maze.
 * This class also contains the attributes required to create a maze.
 * A Wrapping maze is a kind of Maze in which the vertices in a corner,
 * wrap to the vertices in the other corner
 */
class Maze {
  private final int cols; // no of columns
  private final int rows; // no of rows
  private final int interConnectivity;
  private final boolean isWrapping;
  private final boolean isRandom;
  private HashMap<Integer, Pair2> vertexMap = new HashMap<Integer, Pair2>();
  private HashMap<Pair2, Integer> vertexIntMap = new HashMap<Pair2, Integer>();
  private HashMap<Pair2, Integer> vertexConnectionsMap = new HashMap<Pair2, Integer>();
  private List<PairOfPair> mstEdges = new ArrayList<>();
  private List<PairOfPair> wrappingEdges = new ArrayList<>();
  private HashMap<Integer, Integer> edgeMap = new HashMap<Integer, Integer>();

  protected Maze(int row, int col, int interconnectivity, boolean isWrapping, boolean isRandom) {
    this.cols = col;
    this.rows = row;
    this.isWrapping = isWrapping;
    this.interConnectivity =  interconnectivity;
    this.isRandom = isRandom;
  }

  private List<PairOfPair> getPotentialPaths() {
    List<PairOfPair> allEdges = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Pair2 p1 = new Pair2(i, j);
        if (i + 1 < rows) {
          Pair2 p2 = new Pair2(i + 1, j);
          PairOfPair newPair = new PairOfPair(p1, p2);
          allEdges.add(newPair);
        }
        if (j + 1 < cols) {
          Pair2 p2 = new Pair2(i, j + 1);
          PairOfPair newPair = new PairOfPair(p1, p2);
          allEdges.add(newPair);
        }
      }
    }
    return  allEdges;
  }

  protected List<Pair2> getVertices() {
    int p = 1;
    List<Pair2> allVertices = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Pair2 p1 = new Pair2(i, j);
        allVertices.add(p1);
        this.vertexIntMap.put(p1, p);
        this.vertexMap.put(p, p1);
        this.vertexConnectionsMap.put(p1, 0);
        p += 1;
      }
    }
    return allVertices;
  }

  protected String printEdges() {
    StringBuilder str = new StringBuilder();
    List<PairOfPair> allEdges = this.mstEdges;
    for (PairOfPair i : allEdges) {
      str.append("(" + i.getA().getA() + "," + i.getA().getB() + " )" + " -> "
          + "(" + i.getB().getA() + "," + i.getB().getB() + ")");
      str.append("\n");
    }
    return str.toString();
  }

  protected HashMap<Pair2, Integer> getVerticesConnections() {
    return this.vertexConnectionsMap;
  }

  protected List<PairOfPair> getMstEdges() {
    return this.mstEdges;
  }

  protected HashMap<Pair2, Integer> getVertexIntMap() {
    return this.vertexIntMap;
  }


  int[] parents = new int[1000000];

  protected int find(int x) {
    if (parents[x] == x) {
      return x;
    }
    return find(parents[x]);
  }

  protected void merge(int x, int y) {
    int fx = find(x);
    int fy = find(y);
    parents[fx] = fy;
  }



  protected void createMst() {
    List<PairOfPair> edges = this.getPotentialPaths();
    List<Pair2> allVertices = this.getVertices();
    List<PairOfPair> leftOverEdges = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      this.parents[i] = i;
    }
    int m = this.rows;
    int n = this.cols;
    int mstEdges = 0;
    int mstNi = 0;

    if (isRandom) {
      Random rn = new Random();
      while ((edges.size() != 0)) {
        int diff = edges.size() - 1 - 0 + 1;
        int i = rn.nextInt(diff) + 0;
        Pair2 pairA = edges.get(i).getA();
        Pair2 pairB = edges.get(i).getB();
        int a = this.vertexIntMap.get(pairA);
        int b = this.vertexIntMap.get(pairB);
        if (this.find(a) != this.find(b)) {
          this.merge(a, b);
          this.mstEdges.add(new PairOfPair(pairA, pairB));
          this.vertexConnectionsMap.put(pairA, (this.vertexConnectionsMap.get(pairA) + 1));
          this.vertexConnectionsMap.put(pairB, (this.vertexConnectionsMap.get(pairB) + 1));

          edges.remove(edges.get(i));
          mstEdges++;
        } else {
          leftOverEdges.add(edges.get(i));
          edges.remove(edges.get(i));
        }
        mstNi++;
      }
      int interCon = this.interConnectivity;
      while (interCon != 0 && leftOverEdges.size() > 0) {
        int diff = leftOverEdges.size() - 1 - 0 + 1;
        int i = rn.nextInt(diff) + 0;
        PairOfPair pair = leftOverEdges.get(i);
        this.mstEdges.add(pair);
        leftOverEdges.remove(i);
        interCon--;
      }
    } else {
      int i = 0;
      while ((edges.size() != 0)) {
        Pair2 pairA = edges.get(i).getA();
        Pair2 pairB = edges.get(i).getB();
        int a = this.vertexIntMap.get(pairA);
        int b = this.vertexIntMap.get(pairB);
        if (this.find(a) != this.find(b)) {
          this.merge(a, b);
          this.mstEdges.add(new PairOfPair(pairA, pairB));
          this.vertexConnectionsMap.put(pairA, (this.vertexConnectionsMap.get(pairA) + 1));
          this.vertexConnectionsMap.put(pairB, (this.vertexConnectionsMap.get(pairB) + 1));
          edges.remove(edges.get(i));
          mstEdges++;
        } else {
          leftOverEdges.add(edges.get(i));
          edges.remove(edges.get(i));
        }
        mstNi++;
      }
      int interCon = this.interConnectivity;
      while (interCon != 0 && leftOverEdges.size() > 0) {
        PairOfPair pair = leftOverEdges.get(i);
        this.mstEdges.add(pair);
        this.vertexConnectionsMap.put(pair.getA(),
            (this.vertexConnectionsMap.get(pair.getA()) + 1));
        this.vertexConnectionsMap.put(pair.getB(),
            (this.vertexConnectionsMap.get(pair.getB()) + 1));
        leftOverEdges.remove(i);
        interCon--;
      }
    }
    if (this.isWrapping) {
      this.getWrappingEdges();
    }
  }

  protected void getWrappingEdges() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (i == 0) {
          Pair2 p1 = new Pair2(i, j);
          Pair2 p2 = new Pair2(i + rows - 1, j);
          PairOfPair newPair = new PairOfPair(p1, p2);
          this.mstEdges.add(newPair);
          this.vertexConnectionsMap.put(p1, (this.vertexConnectionsMap.get(p1) + 1));
          this.vertexConnectionsMap.put(p2, (this.vertexConnectionsMap.get(p2) + 1));
          this.wrappingEdges.add(newPair);
        } else if (j == 0) {
          Pair2 p1 = new Pair2(i, j);
          Pair2 p2 = new Pair2(i, j + cols - 1);
          PairOfPair newPair = new PairOfPair(p1, p2);
          this.mstEdges.add(newPair);
          this.vertexConnectionsMap.put(p1, (this.vertexConnectionsMap.get(p1) + 1));
          this.vertexConnectionsMap.put(p2, (this.vertexConnectionsMap.get(p2) + 1));
          this.wrappingEdges.add(newPair);
        }
        if (i == 0 && j == 0) {
          Pair2 p1 = new Pair2(i, j);
          Pair2 p2 = new Pair2(i, j + cols - 1);
          PairOfPair newPair = new PairOfPair(p1, p2);
          this.mstEdges.add(newPair);
          this.vertexConnectionsMap.put(p1, (this.vertexConnectionsMap.get(p1) + 1));
          this.vertexConnectionsMap.put(p2, (this.vertexConnectionsMap.get(p2) + 1));
          this.wrappingEdges.add(newPair);
        }
      }
    }
  }

  protected HashMap<Integer, Pair2> getVertexMap() {
    HashMap<Integer, Pair2> vertexMapCopy = new HashMap<>();
    for (Integer key : this.vertexMap.keySet()) {
      vertexMapCopy.put(key, this.vertexMap.get(key));
    }
    return vertexMapCopy;
  }


}






































