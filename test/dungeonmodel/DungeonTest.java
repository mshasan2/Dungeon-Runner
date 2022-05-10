package dungeonmodel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the DungeonModel.
 * This class contains all the tests for the protected methods is the Dungeon Model
 */
public class DungeonTest {

  @Test
  public void testDungeonPlayerDesc1() {
    StringBuilder str = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(6, 8, 0, false, 0, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    str.append(game.getPlayerDesc());
    assertEquals("\nThe Starting Location is : (0 , 1)\n" +
        "The Starting Location Type is : CaveThe Goal Location is : (5 , 7)\n" +
        "Player is in a Cave\n" +
        "Player has collected no treasures till now", str.toString());
  }

  @Test
  public void testDungeonPlayerDesc2() {
    StringBuilder str = new StringBuilder();
    StringBuilder str1 = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(6, 8, 0, false, 70, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    Stack<Pair2> queue = new Stack<>();
    Pair2 curState = startState;
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 1;
    boolean isGoalReached = false;
    while (!curState.equals(goalState) && run <= 1000 && curState != null && !isGoalReached) {
      curState = queue.pop();
      explored.add(curState);
      str.append(game.getPlayerMovesDesc(curState));
      str.append(game.checkAndLootCave(curState));
      str1.append(game.getPlayerDesc());
      str1.append("\n");
      str.append("\n");
      Pair2 prevState = curState;
      List<Pair2> possibleLocationsToMove = game.getPossibleLocationsToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        str.append("New Location Reached is : " + reachedSate.toString());
        game.updatePlayerLocation(reachedSate);
        str.append("\nThe Current Location Type is : " + game.getPlayerCurrentLocation());
        str.append("\nPlayer Reached this Location by moving : "
            + game.getPlayerMovedDir(prevState, reachedSate));
        str.append(game.checkAndLootCave(reachedSate));
        if (reachedSate.equals(goalState)) {
          str.append("\n\nGoal Location has been reached");
          str.append("\nPlayer reached the Goal Location in " + run + " moves");
          isGoalReached = true;
          break;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
            str.append("This is not the goal state.");
            str.append("\nHence, Now Moving to the Next Location\n");
          } else {
            str.append("Passing through the previously visited Location\n");
          }
        }
        prevState = reachedSate;
      }
      run++;
    }
    String expectedRes = "Player is in a Cave\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        "Player is in a Cave\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        "Player is in a Cave\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        "Player is in a Cave\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        "Player is in a Cave\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        "Player is in a Cave\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n" +
        "Player is in a Tunnel\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n" +
        "Player is in a Tunnel\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n" +
        "Player is in a Tunnel\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n" +
        "Player is in a Tunnel\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n" +
        "Player is in a Tunnel\n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.2 are : RUBIES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.6 are : RUBIES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n";
    assertEquals(expectedRes, str1.toString());
  }


  @Test
  public void testDungeonCompleteRun1() {
    StringBuilder str = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(6, 8, 0, false, 0, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    Stack<Pair2> queue = new Stack<>();
    Pair2 curState = startState;
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 1;
    boolean isGoalReached = false;
    while (!curState.equals(goalState) && run <= 1000 && curState != null && !isGoalReached) {
      curState = queue.pop();
      explored.add(curState);
      str.append(game.getPlayerMovesDesc(curState));
      str.append(game.checkAndLootCave(curState));
      Pair2 prevState = curState;
      List<Pair2> possibleLocationsToMove = game.getPossibleLocationsToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        str.append("New Location Reached is : " + reachedSate.toString());
        game.updatePlayerLocation(reachedSate);
        str.append("\nThe Current Location Type is : " + game.getPlayerCurrentLocation());
        str.append("\nPlayer Reached this Location by moving : "
            + game.getPlayerMovedDir(prevState, reachedSate));
        str.append(game.checkAndLootCave(reachedSate));
        if (reachedSate.equals(goalState)) {
          str.append("\n\nGoal Location has been reached");
          str.append("\nPlayer reached the Goal Location in " + run + " moves");
          isGoalReached = true;
          break;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
            str.append("This is not the goal state.");
            str.append("\nHence, Now Moving to the Next Location\n");
          } else {
            str.append("Passing through the previously visited Location\n");
          }
        }
        prevState = reachedSate;
      }
      run++;
    }
    if (run == 1001) {
      str.append("Player did not reach the Gaol Location in the given number of moves.\n"
          + "Hence terminating the DungeonModelImpl");
    }

    String expectedRes = "\n" +
        "The Starting Location is : (0 , 1)\n" +
        "The Starting Location Type is : CaveThe Goal Location is : (5 , 7)\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "West/LeftThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (1 , 1)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 2)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 2)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 2)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 3)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 4)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 4)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 4)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 5)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 6)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 6)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/DownNew Location Reached is : (0 , 6)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/DownNew Location Reached is : (0 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "North/UpPassing through the previously visited Location\n" +
        "New Location Reached is : (2 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/DownNew Location Reached is : (1 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "North/UpPassing through the previously visited Location\n" +
        "New Location Reached is : (3 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/DownNew Location Reached is : (2 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "North/UpPassing through the previously visited Location\n" +
        "New Location Reached is : (4 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/DownNew Location Reached is : (3 , 7)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "North/UpPassing through the previously visited Location\n" +
        "New Location Reached is : (5 , 7)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cave\n" +
        "\n" +
        "Goal Location has been reached\n" +
        "Player reached the Goal Location in 11 moves";
    assertEquals(expectedRes, str.toString());
  }

  @Test
  public void testDungeonCompleteRun2() {
    StringBuilder str = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, false, 0, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    Stack<Pair2> queue = new Stack<>();
    Pair2 curState = startState;
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 1;
    boolean isGoalReached = false;
    while (!curState.equals(goalState) && run <= 1000 && curState != null && !isGoalReached) {
      curState = queue.pop();
      explored.add(curState);
      str.append(game.getPlayerMovesDesc(curState));
      str.append(game.checkAndLootCave(curState));
      Pair2 prevState = curState;
      List<Pair2> possibleLocationsToMove = game.getPossibleLocationsToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        str.append("New Location Reached is : " + reachedSate.toString());
        game.updatePlayerLocation(reachedSate);
        str.append("\nThe Current Location Type is : " + game.getPlayerCurrentLocation());
        str.append("\nPlayer Reached this Location by moving : "
            + game.getPlayerMovedDir(prevState, reachedSate));
        str.append(game.checkAndLootCave(reachedSate));
        if (reachedSate.equals(goalState)) {
          str.append("\n\nGoal Location has been reached");
          str.append("\nPlayer reached the Goal Location in " + run + " moves");
          isGoalReached = true;
          break;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
            str.append("This is not the goal state.");
            str.append("\nHence, Now Moving to the Next Location\n");
          } else {
            str.append("Passing through the previously visited Location\n");
          }
        }
        prevState = reachedSate;
      }
      run++;
    }
    if (run == 1001) {
      str.append("Player did not reach the Gaol Location in the given number of moves.\n"
          + "Hence terminating the DungeonModelImpl");
    }

    String expectedRes = "\n" +
        "The Starting Location is : (0 , 1)\n" +
        "The Starting Location Type is : CaveThe Goal Location is : (3 , 3)\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "West/LeftThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (1 , 1)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 2)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 2)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 3)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/DownNew Location Reached is : (0 , 2)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 3)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/DownNew Location Reached is : (0 , 3)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "North/UpPassing through the previously visited Location\n" +
        "New Location Reached is : (2 , 3)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : This is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/DownNew Location Reached is : (1 , 3)\n" +
        "The Current Location Type is : Tunnel\n" +
        "Player Reached this Location by moving : \n" +
        "North/UpPassing through the previously visited Location\n" +
        "New Location Reached is : (3 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cave\n" +
        "\n" +
        "Goal Location has been reached\n" +
        "Player reached the Goal Location in 5 moves";
    assertEquals(expectedRes, str.toString());
  }

  @Test
  public void testDungeonCompleteRun3() {
    StringBuilder str = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(6, 6, 6, true, 0, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    Stack<Pair2> queue = new Stack<>();
    Pair2 curState = startState;
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 1;
    boolean isGoalReached = false;
    while (!curState.equals(goalState) && run <= 1000 && curState != null && !isGoalReached) {
      curState = queue.pop();
      explored.add(curState);
      str.append(game.getPlayerMovesDesc(curState));
      str.append(game.checkAndLootCave(curState));
      Pair2 prevState = curState;
      List<Pair2> possibleLocationsToMove = game.getPossibleLocationsToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        str.append("New Location Reached is : " + reachedSate.toString());
        game.updatePlayerLocation(reachedSate);
        str.append("\nThe Current Location Type is : " + game.getPlayerCurrentLocation());
        str.append("\nPlayer Reached this Location by moving : "
            + game.getPlayerMovedDir(prevState, reachedSate));
        str.append(game.checkAndLootCave(reachedSate));
        if (reachedSate.equals(goalState)) {
          str.append("\n\nGoal Location has been reached");
          str.append("\nPlayer reached the Goal Location in " + run + " moves");
          isGoalReached = true;
          break;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
            str.append("This is not the goal state.");
            str.append("\nHence, Now Moving to the Next Location\n");
          } else {
            str.append("Passing through the previously visited Location\n");
          }
        }
        prevState = reachedSate;
      }
      run++;
    }
    if (run == 1001) {
      str.append("Player did not reach the Gaol Location in the given number of moves.\n"
          + "Hence terminating the DungeonModelImpl");
    }

    String expectedRes = "\n" +
        "The Starting Location is : (0 , 0)\n" +
        "The Starting Location Type is : CaveThe Goal Location is : (1 , 1)\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "South/Down\n" +
        "East/Right\n" +
        "North/Up\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (1 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "South/Down\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (5 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "North/Up\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 4)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (1 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (5 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "No treasures are present in this caveNew Location Reached is : (4 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (5 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (4 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (5 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (3 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (5 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (4 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "No treasures are present in this caveNew Location Reached is : (3 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (5 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (4 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "No treasures are present in this caveNew Location Reached is : (2 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (4 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (3 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (2 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (4 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (3 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "East/Right\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (1 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (3 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (2 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (2 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "No treasures are present in this caveNew Location Reached is : (1 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (3 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (2 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "West/Left\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (2 , 5)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 4)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (1 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "North/Up\n" +
        "South/Down\n" +
        "East/Right\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "North/Up\n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (2 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (1 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cave\n" +
        "\n" +
        "Goal Location has been reached\n" +
        "Player reached the Goal Location in 12 moves";
    assertEquals(expectedRes, str.toString());
  }

  @Test
  public void testDungeonCompleteRun4() {
    StringBuilder str = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, true, 100, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    Stack<Pair2> queue = new Stack<>();
    Pair2 curState = startState;
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 1;
    boolean isGoalReached = false;
    while (!curState.equals(goalState) && run <= 1000 && curState != null && !isGoalReached) {
      curState = queue.pop();
      explored.add(curState);
      str.append(game.getPlayerMovesDesc(curState));
      str.append(game.checkAndLootCave(curState));
      Pair2 prevState = curState;
      List<Pair2> possibleLocationsToMove = game.getPossibleLocationsToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        str.append("New Location Reached is : " + reachedSate.toString());
        game.updatePlayerLocation(reachedSate);
        str.append("\nThe Current Location Type is : " + game.getPlayerCurrentLocation());
        str.append("\nPlayer Reached this Location by moving : "
            + game.getPlayerMovedDir(prevState, reachedSate));
        str.append(game.checkAndLootCave(reachedSate));
        if (reachedSate.equals(goalState)) {
          str.append("\n\nGoal Location has been reached");
          str.append("\nPlayer reached the Goal Location in " + run + " moves");
          isGoalReached = true;
          break;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
            str.append("This is not the goal state.");
            str.append("\nHence, Now Moving to the Next Location\n");
          } else {
            str.append("Passing through the previously visited Location\n");
          }
        }
        prevState = reachedSate;
      }
      run++;
    }
    if (run == 1001) {
      str.append("Player did not reach the Gaol Location in the given number of moves.\n"
          + "Hence terminating the DungeonModelImpl");
    }

    String expectedRes = "\n" +
        "The Starting Location is : (0 , 0)\n" +
        "The Starting Location Type is : CaveThe Goal Location is : (3 , 3)\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "South/Down\n" +
        "East/Right\n" +
        "North/Up\n" +
        "East/Right\n" +
        "Treasures present in the current cave are : SAPPHIRES SAPPHIRES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRESNew Location Reached is : (1 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "South/Down\n" +
        "Treasures present in the current cave are : DIAMONDS RUBIES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "Treasures present in the current cave are : SAPPHIRES SAPPHIRES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (3 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "Treasures present in the current cave are : DIAMONDS RUBIES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.7 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.8 are : RUBIESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "Treasures present in the current cave are : SAPPHIRES SAPPHIRES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.7 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.8 are : RUBIES\n" +
        " Stored In Treasure Slot No.9 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.10 are : SAPPHIRESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "North/Up\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 2)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "Treasures present in the current cave are : SAPPHIRES SAPPHIRES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.7 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.8 are : RUBIES\n" +
        " Stored In Treasure Slot No.9 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.10 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (1 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "Treasures present in the current cave are : DIAMONDS RUBIES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.7 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.8 are : RUBIES\n" +
        " Stored In Treasure Slot No.9 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.10 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.13 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.14 are : RUBIESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (3 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "Treasures present in the current cave are : DIAMONDS RUBIES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.7 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.8 are : RUBIES\n" +
        " Stored In Treasure Slot No.9 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.10 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.11 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.12 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.13 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.14 are : RUBIES\n" +
        " Stored In Treasure Slot No.15 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.16 are : RUBIES\n" +
        "\n" +
        "Goal Location has been reached\n" +
        "Player reached the Goal Location in 2 moves";
    assertEquals(expectedRes, str.toString());
  }

  @Test
  public void testDungeonCompleteRun5() {
    StringBuilder str = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, true, 0, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    Stack<Pair2> queue = new Stack<>();
    Pair2 curState = startState;
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 1;
    boolean isGoalReached = false;
    while (!curState.equals(goalState) && run <= 1000 && curState != null && !isGoalReached) {
      curState = queue.pop();
      explored.add(curState);
      str.append(game.getPlayerMovesDesc(curState));
      str.append(game.checkAndLootCave(curState));
      Pair2 prevState = curState;
      List<Pair2> possibleLocationsToMove = game.getPossibleLocationsToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        str.append("New Location Reached is : " + reachedSate.toString());
        game.updatePlayerLocation(reachedSate);
        str.append("\nThe Current Location Type is : " + game.getPlayerCurrentLocation());
        str.append("\nPlayer Reached this Location by moving : "
            + game.getPlayerMovedDir(prevState, reachedSate));
        str.append(game.checkAndLootCave(reachedSate));
        if (reachedSate.equals(goalState)) {
          str.append("\n\nGoal Location has been reached");
          str.append("\nPlayer reached the Goal Location in " + run + " moves");
          isGoalReached = true;
          break;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
            str.append("This is not the goal state.");
            str.append("\nHence, Now Moving to the Next Location\n");
          } else {
            str.append("Passing through the previously visited Location\n");
          }
        }
        prevState = reachedSate;
      }
      run++;
    }
    if (run == 1001) {
      str.append("Player did not reach the Gaol Location in the given number of moves.\n"
          + "Hence terminating the DungeonModelImpl");
    }

    String expectedRes = "\n" +
        "The Starting Location is : (0 , 0)\n" +
        "The Starting Location Type is : CaveThe Goal Location is : (3 , 3)\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "South/Down\n" +
        "East/Right\n" +
        "North/Up\n" +
        "East/Right\n" +
        "No treasures are present in this caveNew Location Reached is : (1 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "South/Down\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (3 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "North/Up\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 2)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (1 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (3 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cave\n" +
        "\n" +
        "Goal Location has been reached\n" +
        "Player reached the Goal Location in 2 moves";
    assertEquals(expectedRes, str.toString());
  }

  @Test
  public void testDungeonCompleteRun6() {
    StringBuilder str = new StringBuilder();
    DungeonModelImpl game = new DungeonModelImpl(4, 4, 0, true, 53, false, 1);
    Pair2 startState = game.getStartState();
    str.append("\nThe Starting Location is : " + startState.toString());
    game.updatePlayerLocation(startState);
    str.append("\nThe Starting Location Type is : " + game.getPlayerCurrentLocation());
    Pair2 goalState = game.getGoalState(startState);
    str.append("The Goal Location is : " + goalState.toString() + "\n");
    Stack<Pair2> queue = new Stack<>();
    Pair2 curState = startState;
    queue.add(curState);
    List<Pair2> explored = new ArrayList<>();
    int run = 1;
    boolean isGoalReached = false;
    while (!curState.equals(goalState) && run <= 1000 && curState != null && !isGoalReached) {
      curState = queue.pop();
      explored.add(curState);
      str.append(game.getPlayerMovesDesc(curState));
      str.append(game.checkAndLootCave(curState));
      Pair2 prevState = curState;
      List<Pair2> possibleLocationsToMove = game.getPossibleLocationsToMove(curState);
      for (Pair2 reachedSate : possibleLocationsToMove) {
        str.append("New Location Reached is : " + reachedSate.toString());
        game.updatePlayerLocation(reachedSate);
        str.append("\nThe Current Location Type is : " + game.getPlayerCurrentLocation());
        str.append("\nPlayer Reached this Location by moving : "
            + game.getPlayerMovedDir(prevState, reachedSate));
        str.append(game.checkAndLootCave(reachedSate));
        if (reachedSate.equals(goalState)) {
          str.append("\n\nGoal Location has been reached");
          str.append("\nPlayer reached the Goal Location in " + run + " moves");
          isGoalReached = true;
          break;
        } else {
          if (!explored.contains(reachedSate)) {
            queue.push(reachedSate);
            str.append("This is not the goal state.");
            str.append("\nHence, Now Moving to the Next Location\n");
          } else {
            str.append("Passing through the previously visited Location\n");
          }
        }
        prevState = reachedSate;
      }
      run++;
    }
    if (run == 1001) {
      str.append("Player did not reach the Gaol Location in the given number of moves.\n"
          + "Hence terminating the DungeonModelImpl");
    }

    String expectedRes = "\n" +
        "The Starting Location is : (0 , 0)\n" +
        "The Starting Location Type is : CaveThe Goal Location is : (3 , 3)\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "South/Down\n" +
        "East/Right\n" +
        "North/Up\n" +
        "East/Right\n" +
        "Treasures present in the current cave are : SAPPHIRES SAPPHIRES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRESNew Location Reached is : (1 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "South/Down\n" +
        "Treasures present in the current cave are : DIAMONDS RUBIES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 1)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "Treasures present in the current cave are : SAPPHIRES SAPPHIRES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (3 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "\n" +
        "The possible directions the player can currently move are :\n" +
        "West/Left\n" +
        "South/Down\n" +
        "North/Up\n" +
        "No treasures are present in this caveNew Location Reached is : (0 , 2)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "West/Left\n" +
        "Treasures present in the current cave are : SAPPHIRES SAPPHIRES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRESThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (1 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this caveThis is not the goal state.\n" +
        "Hence, Now Moving to the Next Location\n" +
        "New Location Reached is : (0 , 0)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "No treasures are present in this cavePassing through the previously visited Location\n" +
        "New Location Reached is : (3 , 3)\n" +
        "The Current Location Type is : Cave\n" +
        "Player Reached this Location by moving : \n" +
        "Treasures present in the current cave are : DIAMONDS RUBIES \n" +
        "Player has collected all the treasures present in the current Cave\n" +
        "\n" +
        "The updated Player treasure description is \n" +
        "The treasures collected by the Player till now are: \n" +
        " Stored In Treasure Slot No.1 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.2 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.3 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.4 are : RUBIES\n" +
        " Stored In Treasure Slot No.5 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.6 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.7 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.8 are : SAPPHIRES\n" +
        " Stored In Treasure Slot No.9 are : DIAMONDS\n" +
        " Stored In Treasure Slot No.10 are : RUBIES\n" +
        "\n" +
        "Goal Location has been reached\n" +
        "Player reached the Goal Location in 2 moves";
    assertEquals(expectedRes, str.toString());
  }

  /*
  @Test
  public void testModel() {
    DungeonModelImpl game = new DungeonModelImpl(7, 4, 4, true, 10, true, 100);
    Pair2 startState = game.getStartState();
    System.out.println(game.getLocationAdjacentOpeningsDirections(startState));
  }

   */


  /*
  startNewGame(String strRows, String strCols, String strInterConnectivity,
               String strIsWrapping, String strPercentOfTreasures, String strNumMonsters);


   */


}
