package dungeonview;

import dungeoncontroller.DungeonGUIController;

/**
 * A view for DungeonGame: display the game and provide visual interface
 * for users.
 * This interface contains the methods that need to be implemented by the View Class.
 *
 */
public interface DungeonView {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(DungeonGUIController listener);

  /**
   * Transmit a message to the view.
   * The view displays the message that was provided
   * and returns an empty string if the message was displayed
   *
   * @param message message to be displayed
   */
  String showMessage(String message);

  /**
   * Set up the controller to handle keyboard events in this view.
   *
   * @param listener the controller
   */
  void addKeyListener(DungeonGUIController listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();



}

