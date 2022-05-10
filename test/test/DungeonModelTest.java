package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.StringReader;
import java.util.Scanner;

import dungeoncontroller.DungeonController;
import dungeoncontroller.DungeonControllerImpl;
import dungeonmodel.DungeonModel;
import dungeonmodel.DungeonModelImpl;

/**
 * Test class for the DungeonModel Application.
 * This class contains the tests required for the testing of the application
 */
public class DungeonModelTest {

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidRows1() {
    DungeonModel game = new DungeonModelImpl(0, 10, 0, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidRows2() {
    DungeonModel game = new DungeonModelImpl(-10, 10, 0, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidColumns1() {
    DungeonModel game = new DungeonModelImpl(10, 0, 0, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidColumns2() {
    DungeonModel game = new DungeonModelImpl(10, -10, 0, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidRowsColumns1() {
    DungeonModel game = new DungeonModelImpl(-10, -10, 0, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidRowsColumns2() {
    DungeonModel game = new DungeonModelImpl(0, 0, 0, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidInterconnectivity1() {
    DungeonModel game = new DungeonModelImpl(10, 10, 100, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidInterconnectivity2() {
    DungeonModel game = new DungeonModelImpl(10, 10, -10, false, 0, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidPercentOfTreasures1() {
    DungeonModel game = new DungeonModelImpl(10, 10, -10, false, 101, true, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameInvalidPercentOfTreasures2() {
    DungeonModel game = new DungeonModelImpl(10, 10, -10, false, -1, true, 1);
  }

  @Test
  public void testDungeonCreated1() {
    DungeonModel game = new DungeonModelImpl(4, 5, 0, false, 100, false, 1);
    assertEquals(" 1[D, R] 2[D, L, R] 3[D, L, R] 4[D, L, R] 5[D, L]\n" +
        " 6[D, U] 7[D, U] 8[D, U] 9[D, U] 10[D, U]\n" +
        " 11[D, U] 12[D, U] 13[D, U] 14[D, U] 15[D, U]\n" +
        " 16[U] 17[U] 18[U] 19[U] 20[U]\n", game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonCreated2() {
    DungeonModel game = new DungeonModelImpl(5, 6, 0, false, 50, false, 1);
    assertEquals(" 1[D, R] 2[D, L, R] 3[D, L, R] 4[D, L, R] 5[D, L, R] 6[D, L]\n" +
        " 7[D, U] 8[D, U] 9[D, U] 10[D, U] 11[D, U] 12[D, U]\n" +
        " 13[D, U] 14[D, U] 15[D, U] 16[D, U] 17[D, U] 18[D, U]\n" +
        " 19[D, U] 20[D, U] 21[D, U] 22[D, U] 23[D, U] 24[D, U]\n" +
        " 25[U] 26[U] 27[U] 28[U] 29[U] 30[U]\n", game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonCreated3() {
    DungeonModel game = new DungeonModelImpl(7, 7, 0, false, 50, false, 1);
    assertEquals(" 1[D, R] 2[D, L, R] 3[D, L, R] 4[D, L, R] 5[D, L, R] 6[D, L, R] 7[D, L]\n" +
        " 8[D, U] 9[D, U] 10[D, U] 11[D, U] 12[D, U] 13[D, U] 14[D, U]\n" +
        " 15[D, U] 16[D, U] 17[D, U] 18[D, U] 19[D, U] 20[D, U] 21[D, U]\n" +
        " 22[D, U] 23[D, U] 24[D, U] 25[D, U] 26[D, U] 27[D, U] 28[D, U]\n" +
        " 29[D, U] 30[D, U] 31[D, U] 32[D, U] 33[D, U] 34[D, U] 35[D, U]\n" +
        " 36[D, U] 37[D, U] 38[D, U] 39[D, U] 40[D, U] 41[D, U] 42[D, U]\n" +
        " 43[U] 44[U] 45[U] 46[U] 47[U] 48[U] 49[U]\n", game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonCreated4() {
    DungeonModel game = new DungeonModelImpl(4, 7, 1, false, 50, false, 1);
    assertEquals(" 1[D, R] 2[D, L, R] 3[D, L, R] 4[D, L, R] 5[D, L, R] 6[D, L, R] 7[D, L]\n" +
        " 8[D, R, U] 9[D, L, U] 10[D, U] 11[D, U] 12[D, U] 13[D, U] 14[D, U]\n" +
        " 15[D, U] 16[D, U] 17[D, U] 18[D, U] 19[D, U] 20[D, U] 21[D, U]\n" +
        " 22[U] 23[U] 24[U] 25[U] 26[U] 27[U] 28[U]\n", game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonCreated5() {
    DungeonModel game = new DungeonModelImpl(5, 3, 3, false, 50, false, 1);
    assertEquals(" 1[D, R] 2[D, L, R] 3[D, L]\n" +
        " 4[D, R, U] 5[D, L, R, U] 6[D, L, U]\n" +
        " 7[D, R, U] 8[D, L, U] 9[D, U]\n" +
        " 10[D, U] 11[D, U] 12[D, U]\n" +
        " 13[U] 14[U] 15[U]\n", game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonCreated6() {
    DungeonModel game = new DungeonModelImpl(5, 3, 0, true, 50, false, 1);
    assertEquals(" 1[D, L, R, U] 2[D, L, R, U] 3[D, L, R, U]\n" +
        " 4[D, L, U] 5[D, U] 6[D, R, U]\n" +
        " 7[D, L, U] 8[D, U] 9[D, R, U]\n" +
        " 10[D, L, U] 11[D, U] 12[D, R, U]\n" +
        " 13[D, L, U] 14[D, U] 15[D, R, U]\n", game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonCreated7() {
    DungeonModel game = new DungeonModelImpl(7, 7, 3, true, 50, false, 1);
    assertEquals(" 1[D, L, R, U] 2[D, L, R, U] 3[D, L, R, U]" +
        " 4[D, L, R, U] 5[D, L, R, U] 6[D, L, R, U] 7[D, L, R, U]\n" +
        " 8[D, L, R, U] 9[D, L, R, U] 10[D, L, R, U] 11[D, L, U] 12[D, U] 13[D, U] 14[D, R, U]\n" +
        " 15[D, L, U] 16[D, U] 17[D, U] 18[D, U] 19[D, U] 20[D, U] 21[D, R, U]\n" +
        " 22[D, L, U] 23[D, U] 24[D, U] 25[D, U] 26[D, U] 27[D, U] 28[D, R, U]\n" +
        " 29[D, L, U] 30[D, U] 31[D, U] 32[D, U] 33[D, U] 34[D, U] 35[D, R, U]\n" +
        " 36[D, L, U] 37[D, U] 38[D, U] 39[D, U] 40[D, U] 41[D, U] 42[D, R, U]\n" +
        " 43[D, L, U] 44[D, U] 45[D, U] 46[D, U] 47[D, U] 48[D, U] 49[D, R, U]\n",
        game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonCreated8() {
    DungeonModel game = new DungeonModelImpl(5, 3, 2, true, 50, false, 1);
    assertEquals(" 1[D, L, R, U] 2[D, L, R, U] 3[D, L, R, U]\n" +
        " 4[D, L, R, U] 5[D, L, R, U] 6[D, L, R, U]\n" +
        " 7[D, L, U] 8[D, U] 9[D, R, U]\n" +
        " 10[D, L, U] 11[D, U] 12[D, R, U]\n" +
        " 13[D, L, U] 14[D, U] 15[D, R, U]\n", game.getDungeonRepresentation());
  }

  @Test
  public void testDungeonTreasure1() {
    DungeonModel game = new DungeonModelImpl(5, 3, 3, false, 0, false, 1);
    assertEquals(" 1(T) 2(C)[ ] 3(T)\n" +
        " 4(C)[ ] 5(C)[ ] 6(C)[ ]\n" +
        " 7(C)[ ] 8(C)[ ] 9(T)\n" +
        " 10(T) 11(T) 12(T)\n" +
        " 13(C)[ ] 14(C)[ ] 15(C)[ ]\n", game.getDungeonRepresentationWithTreasures());
  }

  @Test
  public void testDungeonTreasure2() {
    DungeonModel game = new DungeonModelImpl(5, 3, 3, false, 10, false, 1);
    assertEquals(" 1(T) 2(C)[ ] 3(T)\n" +
        " 4(C)[D, R] 5(C)[ ] 6(C)[ ]\n" +
        " 7(C)[ ] 8(C)[ ] 9(T)\n" +
        " 10(T) 11(T) 12(T)\n" +
        " 13(C)[ ] 14(C)[ ] 15(C)[ ]\n", game.getDungeonRepresentationWithTreasures());
  }

  @Test
  public void testDungeonTreasure3() {
    DungeonModel game = new DungeonModelImpl(5, 3, 3, false, 100, false, 1);
    assertEquals(" 1(T) 2(C)[S, S] 3(T)\n" +
        " 4(C)[D, R] 5(C)[D, R] 6(C)[D, R]\n" +
        " 7(C)[S, S] 8(C)[S, S] 9(T)\n" +
        " 10(T) 11(T) 12(T)\n" +
        " 13(C)[S, S] 14(C)[D, R] 15(C)[D, R]\n", game.getDungeonRepresentationWithTreasures());
  }

  @Test
  public void testDungeonTreasure4() {
    DungeonModel game = new DungeonModelImpl(4, 6, 3, true, 50, false, 1);
    assertEquals(" 1(C)[S, S] 2(C)[S, S] 3(C)[S, S] 4(C)[ ] 5(C)[ ] 6(C)[ ]\n" +
            " 7(C)[D, R] 8(C)[D, R] 9(C)[D, R] 10(C)[D, R] 11(T) 12(C)[ ]\n" +
            " 13(C)[ ] 14(T) 15(T) 16(T) 17(T) 18(C)[ ]\n" +
            " 19(C)[ ] 20(T) 21(T) 22(T) 23(T) 24(C)[S, S]\n",
        game.getDungeonRepresentationWithTreasures());
  }

  @Test
  public void testDungeonTreasure5() {
    DungeonModel game = new DungeonModelImpl(5, 3, 2, true, 100, false, 1);
    assertEquals(" 1(C)[D, R] 2(C)[S, S] 3(C)[S, S]\n" +
        " 4(C)[D, R] 5(C)[S, S] 6(C)[D, R]\n" +
        " 7(C)[D, R] 8(T) 9(C)[D, R]\n" +
        " 10(C)[S, S] 11(T) 12(C)[S, S]\n" +
        " 13(C)[D, R] 14(T) 15(C)[S, S]\n", game.getDungeonRepresentationWithTreasures());
  }

  @Test
  public void testDungeonTreasure6() {
    DungeonModel game = new DungeonModelImpl(10, 12, 3, true, 73, false, 1);
    assertEquals(" 1(C)[D, R] 2(C)[D, R] 3(C)[D, R] 4(C)[D, R] 5(C)[D, R] 6(C)[S, S]" +
            " 7(C)[D, R] 8(C)[D, R] 9(C)[S, S] 10(C)[D, R] 11(C)[S, S] 12(C)[D, R]\n" +
            " 13(C)[ ] 14(C)[ ] 15(C)[ ] 16(C)[ ] 17(T) 18(T) 19(T) 20(T) 21(T)" +
            " 22(T) 23(T) 24(C)[ ]\n" +
            " 25(C)[ ] 26(T) 27(T) 28(T) 29(T) 30(T) 31(T) 32(T) 33(T) 34(T) 35(T) 36(C)[S, S]\n" +
            " 37(C)[S, S] 38(T) 39(T) 40(T) 41(T) 42(T) 43(T) 44(T) 45(T) 46(T)" +
            " 47(T) 48(C)[S, S]\n" +
            " 49(C)[S, S] 50(T) 51(T) 52(T) 53(T) 54(T) 55(T) 56(T) 57(T) 58(T)" +
            " 59(T) 60(C)[S, S]\n" +
            " 61(C)[D, R] 62(T) 63(T) 64(T) 65(T) 66(T) 67(T) 68(T) 69(T) 70(T) 71(T) 72(C)[ ]\n" +
            " 73(C)[ ] 74(T) 75(T) 76(T) 77(T) 78(T) 79(T) 80(T) 81(T) 82(T) 83(T) 84(C)[D, R]\n" +
            " 85(C)[D, R] 86(T) 87(T) 88(T) 89(C)[D, R] 90(C)[S, S] 91(C)[D, R]" +
            " 92(C)[S, S] 93(C)[D, R] 94(C)[S, S] 95(C)[D, R] 96(T)\n" +
            " 97(T) 98(C)[D, R] 99(C)[S, S] 100(C)[D, R] 101(C)[D, R] 102(C)[S, S]" +
            " 103(C)[D, R] 104(C)[S, S] 105(C)[S, S] 106(C)[S, S] 107(C)[S, S] 108(C)[S, S]\n" +
            " 109(T) 110(C)[S, S] 111(C)[D, R] 112(C)[S, S] 113(C)[ ] 114(C)[ ] 115(C)[ ]" +
            " 116(C)[ ] 117(C)[ ] 118(C)[ ] 119(C)[ ] 120(T)\n",
        game.getDungeonRepresentationWithTreasures());
  }

  @Test
  public void testDungeonCaveAndTunnel1() {
    DungeonModel game = new DungeonModelImpl(6, 7, 3, false, 50, false, 1);
    assertEquals(" 1(T)[D, R] 2(C)[D, L, R] 3(C)[D, L, R]" +
        " 4(C)[D, L, R] 5(C)[D, L, R] 6(C)[D, L, R] 7(T)[D, L]\n" +
        " 8(C)[D, R, U] 9(C)[D, L, R, U] 10(C)[D, L, R, U] 11(C)[D, L, U] 12(T)[D, U]" +
        " 13(T)[D, U] 14(T)[D, U]\n" +
        " 15(T)[D, U] 16(T)[D, U] 17(T)[D, U] 18(T)[D, U] 19(T)[D, U] 20(T)[D, U] 21(T)[D, U]\n" +
        " 22(T)[D, U] 23(T)[D, U] 24(T)[D, U] 25(T)[D, U] 26(T)[D, U] 27(T)[D, U] 28(T)[D, U]\n" +
        " 29(T)[D, U] 30(T)[D, U] 31(T)[D, U] 32(T)[D, U] 33(T)[D, U] 34(T)[D, U] 35(T)[D, U]\n" +
        " 36(C)[U] 37(C)[U] 38(C)[U] 39(C)[U] 40(C)[U] 41(C)[U] 42(C)[U]\n",
        game.getDungeonRepresentationWithCavesAndTunnel());
  }

  @Test
  public void testDungeonCaveAndTunnel2() {
    DungeonModel game = new DungeonModelImpl(5, 3, 3, true, 70, false, 1);
    assertEquals(" 1(C)[D, L, R, U] 2(C)[D, L, R, U] 3(C)[D, L, R, U]\n" +
        " 4(C)[D, L, R, U] 5(C)[D, L, R, U] 6(C)[D, L, R, U]\n" +
        " 7(C)[D, L, R, U] 8(C)[D, L, U] 9(C)[D, R, U]\n" +
        " 10(C)[D, L, U] 11(T)[D, U] 12(C)[D, R, U]\n" +
        " 13(C)[D, L, U] 14(T)[D, U] 15(C)[D, R, U]\n",
        game.getDungeonRepresentationWithCavesAndTunnel());
  }

  @Test
  public void testDungeonCaveAndTunnel3() {
    DungeonModel game = new DungeonModelImpl(4, 4, 0, true, 30, false, 1);
    assertEquals(" 1(C)[D, L, R, U] 2(C)[D, L, R, U] 3(C)[D, L, R, U] 4(C)[D, L, R, U]\n" +
        " 5(C)[D, L, U] 6(T)[D, U] 7(T)[D, U] 8(C)[D, R, U]\n" +
        " 9(C)[D, L, U] 10(T)[D, U] 11(T)[D, U] 12(C)[D, R, U]\n" +
        " 13(C)[D, L, U] 14(T)[D, U] 15(T)[D, U] 16(C)[D, R, U]\n",
        game.getDungeonRepresentationWithCavesAndTunnel());
  }

  @Test
  public void testDungeonCaveAndTunnel4() {
    DungeonModel game = new DungeonModelImpl(4, 4, 1, true, 0, false, 1);
    assertEquals(" 1(C)[D, L, R, U] 2(C)[D, L, R, U] 3(C)[D, L, R, U] 4(C)[D, L, R, U]\n" +
        " 5(C)[D, L, R, U] 6(C)[D, L, U] 7(T)[D, U] 8(C)[D, R, U]\n" +
        " 9(C)[D, L, U] 10(T)[D, U] 11(T)[D, U] 12(C)[D, R, U]\n" +
        " 13(C)[D, L, U] 14(T)[D, U] 15(T)[D, U] 16(C)[D, R, U]\n",
        game.getDungeonRepresentationWithCavesAndTunnel());
  }

  @Test
  public void testDungeonCaveAndTunnel5() {
    DungeonModel game = new DungeonModelImpl(6, 3, 1, false, 0, false, 1);
    assertEquals(" 1(T)[D, R] 2(C)[D, L, R] 3(T)[D, L]\n" +
        " 4(C)[D, R, U] 5(C)[D, L, U] 6(T)[D, U]\n" +
        " 7(T)[D, U] 8(T)[D, U] 9(T)[D, U]\n" +
        " 10(T)[D, U] 11(T)[D, U] 12(T)[D, U]\n" +
        " 13(T)[D, U] 14(T)[D, U] 15(T)[D, U]\n" +
        " 16(C)[U] 17(C)[U] 18(C)[U]\n", game.getDungeonRepresentationWithCavesAndTunnel());
  }


  @Test
  public void testArrow1() {
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, true, 100, false, 100);
    String expected = " 1(C)[A, A] 2(C)[A, A] 3(C)[A, A] 4(C)[A, A]\n" +
        " 5(C)[A, A] 6(T)[A, A] 7(T)[A, A] 8(C)[A, A]\n" +
        " 9(C)[A, A] 10(T)[A, A] 11(T)[A, A] 12(C)[A, A]\n" +
        " 13(C)[A, A] 14(T)[A, A] 15(T)[A, A] 16(C)[A, A]\n";
    assertEquals(expected, game.getDungeonRepresentationWithArrows());
  }

  @Test
  public void testArrow2() {
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, true, 0, false, 100);
    String expected = " 1(C)[ ] 2(C)[ ] 3(C)[ ] 4(C)[ ]\n" +
        " 5(C)[ ] 6(T)[ ] 7(T)[ ] 8(C)[ ]\n" +
        " 9(C)[ ] 10(T)[ ] 11(T)[ ] 12(C)[ ]\n" +
        " 13(C)[ ] 14(T)[ ] 15(T)[ ] 16(C)[ ]\n";
    assertEquals(expected, game.getDungeonRepresentationWithArrows());
  }

  @Test
  public void testArrow3() {
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, true, 50, false, 100);
    String expected = " 1(C)[A, A] 2(C)[A, A] 3(C)[ ] 4(C)[ ]\n" +
        " 5(C)[A, A] 6(T)[A, A] 7(T)[ ] 8(C)[ ]\n" +
        " 9(C)[ ] 10(T)[A, A] 11(T)[A, A] 12(C)[ ]\n" +
        " 13(C)[ ] 14(T)[ ] 15(T)[A, A] 16(C)[A, A]\n";
    assertEquals(expected, game.getDungeonRepresentationWithArrows());
  }


  @Test
  public void testOtyugh1() {
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    String expected = " 1(T)NO 2(C)NO 3(C)O 4(T)NO\n" +
        " 5(T)NO 6(T)NO 7(T)NO 8(T)NO\n" +
        " 9(T)NO 10(T)NO 11(T)NO 12(T)NO\n" +
        " 13(C)O 14(C)O 15(C)O 16(C)O\n";

    assertEquals(expected, game.getDungeonRepresentationWithOtyugh());
  }

  @Test
  public void testOtyugh2() {
    DungeonModelImpl game = new DungeonModelImpl(5, 6, 0, false, 0, false, 2);
    String expected = " 1(T)NO 2(C)NO 3(C)NO 4(C)NO 5(C)NO 6(T)NO\n" +
        " 7(T)NO 8(T)NO 9(T)NO 10(T)NO 11(T)NO 12(T)NO\n" +
        " 13(T)NO 14(T)NO 15(T)NO 16(T)NO 17(T)NO 18(T)NO\n" +
        " 19(T)NO 20(T)NO 21(T)NO 22(T)NO 23(T)NO 24(T)NO\n" +
        " 25(C)NO 26(C)NO 27(C)NO 28(C)O 29(C)NO 30(C)O\n";

    assertEquals(expected, game.getDungeonRepresentationWithOtyugh());
  }

  @Test
  public void testOtyugh3() {
    DungeonModelImpl game = new DungeonModelImpl(7, 4, 4, false, 0, false, 100);
    String expected = " 1(T)NO 2(C)NO 3(C)O 4(T)NO\n" +
        " 5(C)O 6(C)O 7(C)O 8(C)O\n" +
        " 9(C)O 10(C)O 11(T)NO 12(T)NO\n" +
        " 13(T)NO 14(T)NO 15(T)NO 16(T)NO\n" +
        " 17(T)NO 18(T)NO 19(T)NO 20(T)NO\n" +
        " 21(T)NO 22(T)NO 23(T)NO 24(T)NO\n" +
        " 25(C)O 26(C)O 27(C)O 28(C)O\n";

    assertEquals(expected, game.getDungeonRepresentationWithOtyugh());
  }

  @Test
  public void testOtyugh4() {
    DungeonModelImpl game = new DungeonModelImpl(7, 4, 4, true, 0, false, 100);
    String expected = " 1(C)NO 2(C)O 3(C)O 4(C)O\n" +
        " 5(C)O 6(C)O 7(C)O 8(C)O\n" +
        " 9(C)O 10(C)O 11(T)NO 12(C)O\n" +
        " 13(C)O 14(T)NO 15(T)NO 16(C)O\n" +
        " 17(C)O 18(T)NO 19(T)NO 20(C)O\n" +
        " 21(C)O 22(T)NO 23(T)NO 24(C)O\n" +
        " 25(C)O 26(T)NO 27(T)NO 28(C)O\n";

    assertEquals(expected, game.getDungeonRepresentationWithOtyugh());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOtyugh5() {
    DungeonModelImpl game = new DungeonModelImpl(7, 4, 4, true, 0, false, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOtyugh6() {
    DungeonModelImpl game = new DungeonModelImpl(7, 4, 4, true, 0, false, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGame1() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    Scanner scan = null;
    model.startGame(scan, out);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGame2() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No Right");
    Appendable out = new FailingAppendable();
    Scanner scan = new Scanner(input);
    model.startGame(scan, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMove1() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No Right");
    Appendable out = new StringBuilder();
    String inpDir = "";
    Scanner scan = new Scanner(input);
    model.move(inpDir, scan, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMove2() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No Right");
    Appendable out = new StringBuilder();
    String inpDir = "North";
    model.move(inpDir, null, out);
  }

  @Test(expected = IllegalStateException.class)
  public void testMove3() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No Right");
    Appendable out = new FailingAppendable();
    String inpDir = "North";
    Scanner scan = new Scanner(input);
    model.move(inpDir, scan, out);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testShootArrowFromCurLoc() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.shootArrowFromCurLoc(str);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveByDir() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.moveByDir(str);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNewGame1() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.startNewGame(str, "Apple", "Ball", "Cat", "Dog",
        "Egg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNewGame2() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.startNewGame( "Apple", str, "Ball", "Cat", "Dog",
        "Egg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNewGame3() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.startNewGame( "Apple", "Ball", str, "Cat", "Dog",
        "Egg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNewGame4() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.startNewGame( "Apple", "Ball", "Cat", str, "Dog",
        "Egg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNewGame5() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.startNewGame( "Apple", "Ball", "Cat", "Dog",  str,
        "Egg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNewGame6() {
    DungeonModel game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    String str = null;
    game.startNewGame("Apple", "Ball", "Cat", "Dog",
        "Egg", str);
  }


}
