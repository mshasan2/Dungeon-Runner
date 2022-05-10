package dungeonmodel;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Test class for Maze class.
 * This class test some protected methods of the maze class
 */
public class MazeTest {

  @Test
  public void testMst1() {
    Maze krus = new Maze(3, 3, 0, false, false);
    krus.createMst();
    String str = "(0,0 ) -> (1,0)\n"
        + "(0,0 ) -> (0,1)\n"
        + "(0,1 ) -> (1,1)\n"
        + "(0,1 ) -> (0,2)\n"
        + "(0,2 ) -> (1,2)\n"
        + "(1,0 ) -> (2,0)\n"
        + "(1,1 ) -> (2,1)\n"
        + "(1,2 ) -> (2,2)\n";
    assertEquals(str, krus.printEdges());
  }

  @Test
  public void testMst2() {
    Maze krus = new Maze(3, 3, 0, true, false);
    krus.createMst();
    String str = "(0,0 ) -> (1,0)\n"
        + "(0,0 ) -> (0,1)\n"
        + "(0,1 ) -> (1,1)\n"
        + "(0,1 ) -> (0,2)\n"
        + "(0,2 ) -> (1,2)\n"
        + "(1,0 ) -> (2,0)\n"
        + "(1,1 ) -> (2,1)\n"
        + "(1,2 ) -> (2,2)\n"
        + "(0,0 ) -> (2,0)\n"
        + "(0,0 ) -> (0,2)\n"
        + "(0,1 ) -> (2,1)\n"
        + "(0,2 ) -> (2,2)\n"
        + "(1,0 ) -> (1,2)\n"
        + "(2,0 ) -> (2,2)\n";
    assertEquals(str, krus.printEdges());
  }

  @Test
  public void testMst3() {
    Maze krus = new Maze(3, 3, 1, true, false);
    krus.createMst();
    String str = "(0,0 ) -> (1,0)\n"
        + "(0,0 ) -> (0,1)\n"
        + "(0,1 ) -> (1,1)\n"
        + "(0,1 ) -> (0,2)\n"
        + "(0,2 ) -> (1,2)\n"
        + "(1,0 ) -> (2,0)\n"
        + "(1,1 ) -> (2,1)\n"
        + "(1,2 ) -> (2,2)\n"
        + "(1,0 ) -> (1,1)\n"
        + "(0,0 ) -> (2,0)\n"
        + "(0,0 ) -> (0,2)\n"
        + "(0,1 ) -> (2,1)\n"
        + "(0,2 ) -> (2,2)\n"
        + "(1,0 ) -> (1,2)\n"
        + "(2,0 ) -> (2,2)\n";
    //(1,0 ) -> (1,1) is the new Edge which is added
    assertEquals(str, krus.printEdges());
  }

  @Test
  public void testMst4() {
    Maze krus = new Maze(3, 3, 4, true, false);
    krus.createMst();
    String str1 = "(0,0 ) -> (1,0)\n"
         + "(0,0 ) -> (0,1)\n"
         + "(0,1 ) -> (1,1)\n"
         + "(0,1 ) -> (0,2)\n"
         + "(0,2 ) -> (1,2)\n"
         + "(1,0 ) -> (2,0)\n"
         + "(1,1 ) -> (2,1)\n"
         + "(1,2 ) -> (2,2)\n"
         + "(1,0 ) -> (1,1)\n"
         + "(1,1 ) -> (1,2)\n"
         + "(2,0 ) -> (2,1)\n"
         + "(2,1 ) -> (2,2)\n"
         + "(0,0 ) -> (2,0)\n"
         + "(0,0 ) -> (0,2)\n"
         + "(0,1 ) -> (2,1)\n"
         + "(0,2 ) -> (2,2)\n"
         + "(1,0 ) -> (1,2)\n"
         + "(2,0 ) -> (2,2)\n";
    //(1,0 ) -> (1,1) is the new Edge which is added
    //(1,1 ) -> (1,2)
    //(2,0 ) -> (2,1)
    //(2,1 ) -> (2,2) are the edges added for interconnectivity
    assertEquals(str1, krus.printEdges());
  }

  @Test
  public void testMst5() {
    Maze krus = new Maze(3, 3, 2, true, false);
    krus.createMst();
    String str = "(0,0 ) -> (1,0)\n"
        + "(0,0 ) -> (0,1)\n"
        + "(0,1 ) -> (1,1)\n"
        + "(0,1 ) -> (0,2)\n"
        + "(0,2 ) -> (1,2)\n"
        + "(1,0 ) -> (2,0)\n"
        + "(1,1 ) -> (2,1)\n"
        + "(1,2 ) -> (2,2)\n"
        + "(1,0 ) -> (1,1)\n"
        + "(1,1 ) -> (1,2)\n"
        + "(0,0 ) -> (2,0)\n"
        + "(0,0 ) -> (0,2)\n"
        + "(0,1 ) -> (2,1)\n"
        + "(0,2 ) -> (2,2)\n"
        + "(1,0 ) -> (1,2)\n"
        + "(2,0 ) -> (2,2)\n";
    //(1,0 ) -> (1,1)
    //(1,1 ) -> (1,2) are the eges added for interconnectivity
    assertEquals(str, krus.printEdges());
  }


  @Test
  public void testMst6() {
    Maze krus = new Maze(4,3, 0, false, false);
    krus.createMst();
    String str = "(0,0 ) -> (1,0)\n"
        + "(0,0 ) -> (0,1)\n"
        + "(0,1 ) -> (1,1)\n"
        + "(0,1 ) -> (0,2)\n"
        + "(0,2 ) -> (1,2)\n"
        + "(1,0 ) -> (2,0)\n"
        + "(1,1 ) -> (2,1)\n"
        + "(1,2 ) -> (2,2)\n"
        + "(2,0 ) -> (3,0)\n"
        + "(2,1 ) -> (3,1)\n"
        + "(2,2 ) -> (3,2)\n";
    assertEquals(str, krus.printEdges());
  }

}
