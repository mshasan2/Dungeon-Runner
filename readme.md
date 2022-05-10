# Dungeons

## Overview

Dungeons is a Java based game with a Graphical User Interface. the world of Dungeon Game consists of a network of tunnels and caves that are interconnected so that the player can explore the entire world by travelling from cave through the tunnels that connect them. The game also contains Monsters called Otyughs, these Otyughs are ferocious creatures which will attack the player and kill the player, the player can slay the Otyugh before entering a cave inhabited by them by shooting Arrows. The player can collect treasures and arrows while exploring and having fun inside the dungeon.
Dungeon Game also supports a text-based interactive version, which has all the features explained above but in a text based format.

## List of Features

1. Dungeon is a Game which consists of a network of tunnels and caves that are interconnected, this game allows the Player to explore the dungeon and collect treasures.
2. The Dungeon game, allows the user to specify whether Dungeon is wrapping or non wrapping at the time of start of the game.
3. The Dungeon game, allows the user to specify whether Dungeon has treasures or not, at the time of start of the game. It also allows the user to specify the percentage of treasures in the game.
4. In the Dungeon game, the user gets to decide the size of the dungeon.
5. In the Graphical User Interface version of the Dungeon game, when the game begins, player starts in a cave and as the player explores the Dungeon, the visted locations are added to the Dungeon Map.
6. When the user is in a location, all the treasures and arrows present are displayed in the Dungeon Map.
7. Player can collect the Treasures in a Cave by pressing the T key on the keyboard.
8. Player can collect the Arrows in a Location by pressing the A key on the keyboard.
9. Player can shoot arrows by pressing the S key on the keyboard followed by arrow key to indicate the direction.
10. Player can navigate through the either using the arrow key on their keyboard or by clicking on the arrow buttons on their screen.
11. Player Description and Location description are displayed on the screen and these are updated whenever there is any change in information.
12. When player is near a cave with an Otyugh the smell is displayed in the Dungeon Map.
13. User can start a new Game by entering the required parameters in the given text fields by Navigating to the Menu and clicking the New Game option.
14. User can restart a new game with the previously defined settings by clicking on the Restart button in the Menu.
15. User can Quit the game either by clicking on the close button or by cling on Menu and then selecting Quit option
16. In the Text based interactive version of the Dungeon game, the user is given the choice to collect treasures/arrows or not collect treasures/arrows, the choice is decided based on the input provided by the user
17. The User can explore the dungeon by specifying the direction to move. The possible directions, which one can move from a given location are displayed, the player can select one of the directions and can move.
18. When a Otyugh is in a nearby cave, the scent of the Otyugh is detected and displayed to the Player, so that the player is notified and can take the necessary.
19. A player that has arrows, can attempt to slay an Otyugh by specifying a direction and distance in which to shoot their crooked arrow. Distance is defined as the number of caves (but not tunnels) that an arrow travels. Arrows travel freely down tunnels (even crooked ones) but only travel in a straight line through a cave.
20. Player starts with 3 crooked arrows but can find additional arrows in the dungeon with the same frequency as treasure. Arrows and treasure can be, but are not always, found together. Furthermore, arrows can be found in both caves and tunnels.
21. Distances must be exact. For example, if you shoot an arrow a distance of 3 to the east and the Otyugh is at a distance of 2, you miss the Otyugh. 
22. For a direction to move, both North, East, West, South and Up, Left, Right and Down are the valid directions. This was done in-order to accomodate users with both the sense of directions. 
23. To help new players to understand the game, Help menu exists, this menu contains an option called getting started, this contians the information of how to play the game and the keys to navifate through the dungeon.
24. When player selects the Reuse option in the Menu, then the previously used dungeon map is used.
25. When player selects the Restart option in the Menu, then a new dungeon map is created with the previously defined paramters.

## How To Run

### To Run Graphical User Interface version of the Game
1. Open terminal/command prompt
2. Navigate into the res/ directory
3. Type the below command and press enter
```bash 
 java -jar Dungeon.jar 
```

### To Run Text based version of the Game

1. Open terminal/command prompt
2. Navigate into the res/ directory
3. Type the below command and press enter
```bash 
 java -jar Dungeon.jar 4 4 1 true 10 10
```
Note: The arguments after Dungeon.jar are as follows: Dungeon Row Size (Integer Value), Dungeon Column Size (Integer Value), Interconnectivity of Dungeon (Integer Value), Is Dungeon Wrapping (Boolean Value), Percent Of Treasures in Caves (Integer Value), Number Of Monsters in the Dungeon (Integer Value).

## How to Use the Program

### How to use the Graphical User Interface version of the Dungeon Game

1. As soon as you type in the command to run the GUI version of the Game, a new Frame will open, this frame contains a Dungeon Map on the left side and the details screen on the right side.

2. To navigate the player through the Dungeon, the user can either make use of the arrow keys in the KeyBoard or can click on the arrow button on the Right Side Panel.

3. When the Player lands in a Cave with Treasure, the treasure can be picked up by pressing the T key on the KeyBoard.

4. When the player lands in a Location with arrows, the arrows can be picked up by pressing the A key on the KeyBoard.

5. Player can shoot an Otyuh by pressing on the S key on the keyBoard.
6. When a cave contains Treasure, the treasure icon is displayed in the Dungeon Map.

7. When a Location contains Arrows, the arrow icon is displayed in the Dungeon Map.

8. When the user is in the vincinity of a Otyugh, the smell eminating from the Otyugh is displayed as an icon in the Dungeon Map.

9. If the user wins the Game after killing the Otyugh in the Goal cave and navigates to the Goal Cave, then Player won message is displayed.
10. When the user enters into a cave inhabited by the Otyugh and the player dies, Game over message is displayed.

11. After Player picks up treasure, the player description is updated and this can be seen in the side panel of the game under the heading Player Description.

12. The details of the location in which the player is currently present are displayed in the right hand screen.

13. The the methods to display the view are present in the DungeonViewImpl class.

14. The methods for the GUI controller are present in the DungeonGUIController class.

15. To add a click listener to the view we can make use of the addClickListener method of the DungeonViewImpl class.

16. To add a keyBoard listener to the view we can make use of the addKeyListener method of the DungeonViewImpl class.

17. To handle a cell click we can make use of the handleCellClick method of the Controller

18. To handle a keyboard button press we can make use of the handleKeyPressed method of the Controller.

### How to use the Text Based Version of the Dungeon Game

1. As soon as you type the above command to run the java application with valid command line arguments, the java application will run and will print out the details of the Dungeon game.

2. If Invalid input values are passed to the program, an appropriate error message will be displayed.

3. A user can select whether the Dungeon is generated randomly or if the same dungeon is returned by passing the appropriate parameter in the Constructor of the DungeonImpl class.

4. Create Model of the Dungeon game, the type of the model should be DungeonModel, we pass the parameters of the game like Dungeon Row Size (Integer Value), Dungeon Column Size (Integer Value), Interconnectivity of Dungeon (Integer Value), Is Dungeon Wrapping (Boolean Value), Percent Of Treasures in Caves (Integer Value), Number Of Monsters in the Dungeon (Integer Value).

5. Create a Controller of the Dungeon game, the controller takes in the desired input type and the desired output type as the parameters.

6. We begin the game by passing in the model created in step 4 as a parameter to the playGame() method of Controller.

7. The user inputs are requested which are used to progress the game.

8. If the string representation of the dungeon is desired we can make use of the below methods of the DungeonModel interface getDungeonRepresentation(), getDungeonRepresentationWithCavesAndTunnel(), getDungeonRepresentationWithTreasures(), getDungeonRepresentationWithOtyugh() and etDungeonRepresentationWithArrows() methods return the String representation of the Dungeon. 

## Description of Examples

### For Graphical User Interface version of the Dungeon Game

Run 1 - GameImg1.png
1. Player has visited most of the locations and the unvisited locations are not being displayed.
2. The treasures collected by the player are being displayed in the Player Description
3. The details about the location are being displayed in the Location Description
4. Otyuh smell is being displayed in the locations in which Otyugh was present.
5. Menu can be seen in the preferred location

Run2 - GameImg2.png
1. As player has not yet explored the Dungeon, only the Player's current location is being displayed and no other locations are being displayed.

Run3 - GameImg3.png
1. Player has navigated through the Dungeon but encountered an Otyugh was alive.
2. The treasures collected by the player are being displayed in the Player Description
3. Game Ends. Player is dead.

Run4 - GameImg4.png
1. User has clicked on the Help option in the menu.
2. A pop up window is displayed which contains the information about how to get started with the game.

Run5 - GameImg4.png
1. Player pressed S key on keyboard followed by an arrow key to indicate the direction, then the prompt to enter the distance is displayed.

### For Text Based version of the Dungeon Game

Run 1 - Run1.txt
 1. Run1 is for the Dungeon with the following specifications: Size of the Dungeon is 4 x 4, Interconnectivity is 0, Non-Wrapping Dungeon with Treasures in 50 percent of the Dungeon and 100 percent of monsters.
 2. Player is displayed a message to pick up treasure or not
 3. Player picks up the treasure
 4. Player treasure description is displayed
 5. The directions Player can move are displayed.
 6. Player picks up arrows.
 7. A message is displayed which asks player to shoot an arrow or not.
 8. Player shoots the arrow and does not hit anything.
 9. Player moves through the dungeon and is eaten by an Otyugh.
 
Run 2 - Run2.txt
 1. Player Navigates through the dungeon 
 2. Picks up treasures and arrows
 3. Navigates through the dungeon
 4. Shoots and kills Otyugh.
 5. Reaches Goal State and wins the game.

Run3 - Run3.txt
 1. There is only one Otyugh in the entire Dungeon
 2. User Enters Invalid Direction
 3. Navigates through the whole dungeon
 4. Shoots arrow in the correct direction but wrong distance and dies in the end after getting eaten by Otyugh.

Run4 - Run4.txt
 1. 10% of location have Caves and Tunnels
 2. Player shoots an arrow and enters a cave with an injured Otyugh and survives.
 3. Player Runs out of arrows
 4. Dies after getting killed by Otyugh.
 
## Design/Model Changes

 1. I have retained most of my code from Project 3 and Project 4 and have added new functionality for the view in Project 5.
 2. In Project 5 initial design, I added classes and interfaces to represent the view and the controller for the GUI, in the revised version of my UML design I have additional package private classes(DungeonMap and DungeonSidePanel classes) to the View, this helped me break down the code and makes sure that one class is responsible for one kind of task.
 3. I have added additional methods for the controller and the model and have made some public classes to private classes.

## Assumptions

1. The first assumption I have made is that Percentage of Treasure in a cave cannot be greater than 100 percent.
2. Player can collect all the treasures/arrows he/she wants, Player has unlimited treasure/arrow carrying capacity.
3. As the player cannot know the actual number of caves at the start of the game, so when the player enters a large number of Otyughs, and if the number is greater than the actual number of caves, we add the maximum possible number of Otyughs in the game. We don’t display an Error because as the dungeon is generated randomly, the actual number of caves cannot be guessed, hence there is a huge that the player will not be able to guess the maximum number of Otyughs permissible.
4. However long a game may go, Otyugh don’t starve and die, they have a lot of patience and hunger capacity.
5. If a user decides to pick treasures/arrows, then all the treasures/arrows present in a Location will be picked.
6. Player Description is displayed always as I have assumed that Player always requires the Player Description
7. User collects all the treasures  present in a Cave when they press the T button
8. User collects all the Arrows present in a Location when they press the A button.
9. As user is always asked to play in a Random Dungeon, we do not ask the user in the New Game option in the Menu, if they want to play a non random game as we are assuming that Player always wants to play random game.


## Limitations

1. User is not given an option to enter the maximum number of moves he can make before the game terminates.
2. The Dungeon hasn't been tested for very large dungeons like the dungeons of size 100 * 100.
3. In my current implementation the Player collects all the treasures present in the cave, regardless of the number of treasures he has collected before.
4. When user is asked whether to pickup treasure or arrow, and user enters an invalid input, the invalid input is not considered and no prompt is shown that the input is invalid.


## Citations
1. Project Description of Project 3 and Project 4 of PDP course
2. Online notes of PDP Module 8.3 - Testing Controller
3. Made use of Image Overlay method which was provided by professor.
4. Some methods in the View Interface are based on Lab 11 of CS5010 course.


