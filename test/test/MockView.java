package test;

import java.io.IOException;

import dungeoncontroller.DungeonGUIController;
import dungeonview.DungeonView;

/**
 * This is the class for Mock View.
 * This class implements the DungeonView and is used to test the DungeonGUIController
 */
public class MockView implements DungeonView {
  private final Appendable log;
  private final int viewUniqueCode;

  public MockView(Appendable log, int viewUniqueCode) {
    this.log = log;
    this.viewUniqueCode = viewUniqueCode;
  }

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addClickListener(DungeonGUIController listener) {
    try {
      log.append("addClickListener - controller added"
          + " viewUniqueCode: " + this.viewUniqueCode + "\n");
    } catch (IOException e) {
      // do nothing as this is mock view
    }
  }

  /**
   * Transmit a message to the view.
   * The view displays the message that was provided
   * and returns an empty string if the message was displayed
   *
   * @param message message to be displayed
   */
  @Override
  public String showMessage(String message) {
    try {
      log.append("showMessage - " + message
          + " viewUniqueCode: " + this.viewUniqueCode);
    } catch (IOException e) {
      // do nothing as this is mock view
    }
    return null;
  }

  /**
   * Set up the controller to handle keyboard events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addKeyListener(DungeonGUIController listener) {
    try {
      log.append("addKeyListener - controller added"
          + " viewUniqueCode: " + this.viewUniqueCode + "\n");
    } catch (IOException e) {
      // do nothing as this is mock view
    }
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    try {
      log.append("\nrefresh called"
          + " viewUniqueCode: " + this.viewUniqueCode + "\n");
    } catch (IOException e) {
      // do nothing as this is mock view
    }
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    try {
      log.append("make visible called"
          + " viewUniqueCode: " + this.viewUniqueCode + "\n");
    } catch (IOException e) {
      // do nothing as this is mock view
    }
  }
}
