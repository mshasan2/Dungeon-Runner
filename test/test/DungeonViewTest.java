package test;

import org.junit.Test;

import dungeoncontroller.DungeonGUIController;
import dungeonmodel.DungeonModel;
import dungeonmodel.DungeonModelImpl;
import dungeonview.DungeonView;
import dungeonview.DungeonViewImpl;

/**
 * Test class for DungeonViewImpl class.
 * This class contains the unit tests required for the DungeonView Class.
 */
public class DungeonViewTest {

  @Test(expected = IllegalArgumentException.class)
  public void testViewConstructor() {
    DungeonModel nullModel = null;
    DungeonView view = new DungeonViewImpl(nullModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewAddClickListener() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = new DungeonViewImpl(model);
    DungeonGUIController nullController = null;
    view.addClickListener(nullController);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewAddKeyListener() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = new DungeonViewImpl(model);
    DungeonGUIController nullController = null;
    view.addKeyListener(nullController);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testView4() {
    DungeonModel model = new DungeonModelImpl(8, 8, 0, true, 100, true, 1);
    DungeonView view = new DungeonViewImpl(model);
    String nullString = null;
    view.showMessage(nullString);
  }



}
