package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.StringReader;

import dungeoncontroller.DungeonController;
import dungeoncontroller.DungeonControllerImpl;
import dungeoncontroller.DungeonGUIController;
import dungeoncontroller.DungeonGUIControllerImpl;
import dungeonmodel.DungeonModel;
import dungeonmodel.DungeonModelImpl;
import dungeonview.DungeonView;
import dungeonview.DungeonViewImpl;

/**
 * Test class for the Dungeon Controller class.
 * This class contains all the unit test cases required for the Dungeon game.
 */
public class DungeonControllerTest {

  @Test
  public void testControllerWithMockModel() {
    Appendable out = new StringBuffer();
    StringReader in = new StringReader("North East West South");
    StringBuilder log = new StringBuilder();
    DungeonModel model = new MockModel(log, 1234321);
    DungeonController controller = new DungeonControllerImpl(in, out);
    controller.playGame(model);
    assertEquals("Input: North East West South ", log.toString()); //input reaches model correctly
    assertEquals("\nWelcome to the Dungeon\n" +
        "\n" +
        "UniqueCode: 1234321", out.toString()); // output from model received correctly
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    DungeonModel model = new DungeonModelImpl(4, 4, 0, true, 50, false, 100);
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable out = new FailingAppendable();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModelInController() {
    DungeonModel model = null;
    StringReader input = new StringReader("1 1 q");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
  }

  /**
   * Player Navigates through the dungeon.
   * Doesn't pick up treasure and arrows
   * Both type of Otyugh smells are detected.
   * Player is killed by the Otyugh.
   * Game Ends.
   */
  @Test
  public void testControllerPlayGame1() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 1);
    StringReader input = new StringReader("No No No Right No Right No Down No Down No Down");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "No treasures are present in this cave\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (1 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "A Weak Pungent Smell Detected from this location\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (2 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (3 , 3)\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Player Navigates through the dungeon.
   * Picks Up Treasure and Arrows
   * Navigates through the dungeon
   * Shoots Otyugh and the screams of Otyugh can be heard
   * Player wins the Game
   * Game Ends
   */
  @Test
  public void testControllerPlayGame2() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 1);
    StringReader input = new StringReader("Yes Yes No Down Yes No Up No Right No"
        + " Right No Down No Down Yes Down 1 Yes Down 1 No Down");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Player has collected all the treasures present in the current Cave\n"
        + "\n"
        + "The updated Player treasure description is \n"
        + "The treasures collected by the Player till now are: \n"
        + " Stored In Treasure Slot No.1 are : DIAMONDS\n"
        + " Stored In Treasure Slot No.2 are : RUBIES\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "Player has collected all the Arrows present in the current Cave\n"
        + "The updated Player Arrow description is \n"
        + "Number of Arrows with Player are 5\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (1 , 1)\n"
        + "You are in a Tunnel\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "Player has collected all the Arrows present in the current Tunnel\n"
        + "The updated Player Arrow description is \n"
        + "\n"
        + "Number of Arrows with Player are 7\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 1)\n"
        + "You are in a Cave\n"
        + "\n"
        + "No treasures are present in this cave\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "No treasures are present in this cave\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (1 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "A Weak Pungent Smell Detected from this location\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (2 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No Enter the direction to"
        + " shoot the arrow Enter the distance to shoot the arrow \n"
        + "We hear a continuous roar from a Cave\n"
        + "Do you want to shoot an Arrow? Yes or No Enter the direction to"
        + " shoot the arrow Enter the distance to shoot the arrow \n"
        + "We hear a loud roar from a Cave which stops after a minute\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (3 , 3)\n"
        + "Congratulations!\n"
        + "You have reached Goal Location\n";
    assertEquals(expected, out.toString());
  }

  /**
   * Player shoots curved arrows - Through tunnel and
   * reaches the next cave and kills the Otyugh present there.
   * Player runs out of arrows, so prompt to shoot arrow is not displayed
   * Reaches the Gaol
   */
  @Test
  public void testControllerPlayGame3() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 1);
    StringReader input = new StringReader("No No No Right Yes Right 1 Yes Right 1" +
        " No Right No Down No Down No Down");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "No treasures are present in this cave\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No Enter the direction to"
        + " shoot the arrow Enter the distance to shoot the arrow \n"
        + "We hear a continuous roar from a Cave\n"
        + "Do you want to shoot an Arrow? Yes or No Enter the direction to"
        + " shoot the arrow Enter the distance to shoot the arrow \n"
        + "We hear a loud roar from a Cave which stops after a minute\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (1 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (2 , 3)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (3 , 3)\n"
        + "Congratulations!\n"
        + "You have reached Goal Location\n";
    assertEquals(expected, out.toString());
  }

  /**
   * Strong Pungent smell was detected.
   * Player didn't shoot the Otyugh
   * Monster ate the player.
   * Player Died and Game Ends.
   */
  @Test
  public void testControllerPlayGame4() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\nWelcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Player navigates through the dungeon.
   * Picks up treasure and arrows
   * Injures an Otyugh and walks into the cave and survives.
   * Navigates the dungeon further
   * Dies after entering a cave inhabited by Otyugh
   */
  @Test
  public void testControllerPlayGame5() {
    DungeonModel model = new DungeonModelImpl(4, 4, 1, false, 50, false, 100);
    StringReader input = new StringReader("Yes Yes Down 1 No Down No No No Down Yes No Down");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    assertEquals("\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "No treasures are present in this cave\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "Player has collected all the Arrows present in the current Cave\n"
        + "The updated Player Arrow description is \n"
        + "Number of Arrows with Player are 5\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No"
        + " Enter the direction to shoot the arrow Enter the distance to shoot the arrow \n"
        + "We hear a continuous roar from a Cave\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (1 , 1)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are lucky, as the Otyugh was injured it didn't notice you\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "West/Left\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (2 , 1)\n"
        + "You are in a Tunnel\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "Player has collected all the Arrows present in the current Tunnel\n"
        + "The updated Player Arrow description is \n"
        + "\n"
        + "Number of Arrows with Player are 6\n"
        + "The possible directions the player can currently move are :\n"
        + "North/Up\n"
        + "South/Down\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (3 , 1)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached", out.toString());

  }

  /**
   * Invalid Input when asked whether to pickup treasure.
   * User enters an invalid string as input.
   */
  @Test
  public void testInvalidInput1() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("Nah No No No Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Invalid Input when asked whether to pickup treasure.
   * User enters an Integer instead of String.
   */
  @Test
  public void testInvalidInput2() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("1 No No No Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Invalid Input when asked whether to pickup arrows.
   * User enters an invalid string as input.
   */
  @Test
  public void testInvalidInput3() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("Yes Nope No No Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Player has collected all the treasures present in the current Cave\n"
        + "\n"
        + "The updated Player treasure description is \n"
        + "The treasures collected by the Player till now are: \n"
        + " Stored In Treasure Slot No.1 are : DIAMONDS\n"
        + " Stored In Treasure Slot No.2 are : RUBIES\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Invalid Input when asked whether to pickup arrows.
   * User enters an Integer as input instead of String
   */
  @Test
  public void testInvalidInput4() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("Yes 100 No Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Player has collected all the treasures present in the current Cave\n"
        + "\n"
        + "The updated Player treasure description is \n"
        + "The treasures collected by the Player till now are: \n"
        + " Stored In Treasure Slot No.1 are : DIAMONDS\n"
        + " Stored In Treasure Slot No.2 are : RUBIES\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Invalid Input when asked for direction to move.
   * User enters an invalid direction(String) to move.
   * All the valid directions to move are displayed
   */
  @Test
  public void testInvalidInput5() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No Zebra Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Invalid Input when asked for direction to move.
   * User enters an invalid direction(Integer) to move.
   * All the valid directions to move are displayed
   */
  @Test
  public void testInvalidInput6() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 50, false, 100);
    StringReader input = new StringReader("No No No 1 Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "Treasures present in the current cave are : DIAMONDS RUBIES \n"
        + "Pick up all the Treasure? Yes or No :\n"
        + "Pick up all the Arrows? Yes or No :\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  /**
   * Invalid Input when asked for direction to move.
   * User enters an valid direction(Integer) to move but not the one it can move.
   * All the valid directions to move are displayed
   */
  @Test
  public void testInvalidInput7() {
    DungeonModel model = new DungeonModelImpl(4, 4, 0, false, 0, false, 100);
    StringReader input = new StringReader("No No No 1 North Right");
    Appendable out = new StringBuilder();
    DungeonController controller = new DungeonControllerImpl(input, out);
    controller.playGame(model);
    String expected = "\n"
        + "Welcome to the Dungeon\n"
        + "\n"
        + "You are in a Cave\n"
        + "\n"
        + "A Strong Pungent Smell Detected from this location\n"
        + "\n"
        + "No treasures are present in this cave\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Do you want to shoot an Arrow? Yes or No \n"
        + "\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Invalid Input Direction\n"
        + "The possible directions the player can currently move are :\n"
        + "West/Left\n"
        + "South/Down\n"
        + "East/Right\n"
        + "Enter direction to move : \n"
        + "Reached State is: (0 , 2)\n"
        + "You are in a Cave\n"
        + "\n"
        + "You are Dead. The Monster ate you\n"
        + "Player has been killed\n"
        + " End of Game Reached";
    assertEquals(expected, out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGuiController1() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = null;
    DungeonGUIController controller = new DungeonGUIControllerImpl(view, model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGuiController2() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonModel nullModel = null;
    DungeonView view = new DungeonViewImpl(model);
    DungeonGUIController controller = new DungeonGUIControllerImpl(view, nullModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGuiController3() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = new DungeonViewImpl(model);
    DungeonGUIController controller = new DungeonGUIControllerImpl(view, model);
    DungeonModel nullModel = null;
    controller.playGame(nullModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGuiController4() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = new DungeonViewImpl(model);
    DungeonGUIController controller = new DungeonGUIControllerImpl(view, model);
    String str = null;
    controller.handleKeyPressed(str);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGuiController5() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = new DungeonViewImpl(model);
    DungeonGUIController controller = new DungeonGUIControllerImpl(view, model);
    controller.handleCellClick(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGuiController6() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = new DungeonViewImpl(model);
    DungeonGUIController controller = new DungeonGUIControllerImpl(view, model);
    controller.handleCellClick(0, -1);
  }

  @Test
  public void testGUIControllerWithMockView1() {
    Appendable out = new StringBuffer();
    StringReader in = new StringReader("North East West South");
    StringBuilder log = new StringBuilder();
    DungeonModel mockModel = new MockModel(log, 1234321);
    DungeonView view = new MockView(log, 987651);
    DungeonGUIController guiController = new DungeonGUIControllerImpl(view, mockModel);
    guiController.playGame(mockModel);
    assertEquals("Initialize Game called Model Unique Code: 1234321\n" +
        "addClickListener - controller added viewUniqueCode: 987651\n" +
        "addKeyListener - controller added viewUniqueCode: 987651\n" +
        "make visible called viewUniqueCode: 987651\n" +
        "\nrefresh called viewUniqueCode: 987651\n", log.toString());
  }

  @Test
  public void testGUIControllerWithMockView2() {
    Appendable out = new StringBuffer();
    StringReader in = new StringReader("North East West South");
    StringBuilder log = new StringBuilder();
    DungeonModel mockModel = new MockModel(log, 1234321);
    DungeonView view = new MockView(log, 987651);
    DungeonGUIController guiController = new DungeonGUIControllerImpl(view, mockModel);
    guiController.restartGame();
    assertEquals("Unique Code: 1234321\n" +
        "refresh called viewUniqueCode: 987651\n", log.toString());
  }

  @Test
  public void testGUIControllerWithMockView3() {
    Appendable out = new StringBuffer();
    StringReader in = new StringReader("North East West South");
    StringBuilder log = new StringBuilder();
    DungeonModel mockModel = new MockModel(log, 1234321);
    DungeonView view = new MockView(log, 987651);
    DungeonGUIController guiController = new DungeonGUIControllerImpl(view, mockModel);
    guiController.handleKeyPressed("Apple");
    assertEquals("showMessage - Congratulations!\n" +
        " You have won the game viewUniqueCode: 987651", log.toString());
  }

  @Test
  public void testGUIControllerWithMockView4() {
    Appendable out = new StringBuffer();
    StringReader in = new StringReader("North East West South");
    StringBuilder log = new StringBuilder();
    DungeonModel mockModel = new MockModel(log, 1234321);
    DungeonView view = new MockView(log, 987651);
    DungeonGUIController guiController = new DungeonGUIControllerImpl(view, mockModel);
    guiController.handleCellClick(1, 2);
    assertEquals("showMessage - Congratulations!\n" +
        " You have won the game viewUniqueCode: 987651", log.toString());
  }

  @Test
  public void testGUIControllerWithMockView5() {
    Appendable out = new StringBuffer();
    StringReader in = new StringReader("North East West South");
    StringBuilder log = new StringBuilder();
    DungeonModel mockModel = new MockModel(log, 1234321);
    DungeonView view = new MockView(log, 987651);
    DungeonGUIController guiController = new DungeonGUIControllerImpl(view, mockModel);
    guiController.startNewGame("a", "b", "c", "d", "e", "f");
    assertEquals("a , b , c , d , e , f ModelUniqueCode: 1234321\n" +
        "\n" +
        "refresh called viewUniqueCode: 987651\n", log.toString());
  }


}
