package dungeonview;

import dungeoncontroller.DungeonGUIController;
import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;



/**
 * Class which is used to display the DungeonPanel.
 * This class extends JPanel class and uses GridLayout
 */
class DungeonPanel extends JPanel {
  DungeonSidePanel dungeonSidePanel;

  protected DungeonPanel(ReadOnlyDungeonModel model) {
    this.setLayout(new GridLayout(1, 2));
    this.setBackground(Color.MAGENTA); //Setting the background color of the Frame to Black
    DungeonMap dungeonMap = new DungeonMap(model);
    JScrollPane scrollPane = new JScrollPane(dungeonMap);
    Dimension minimumSize1 = new Dimension(100, 100);
    scrollPane.setMinimumSize(minimumSize1);
    scrollPane.setViewportView(dungeonMap);
    dungeonSidePanel = new DungeonSidePanel(model);
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        scrollPane, dungeonSidePanel);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(550);
    Dimension minimumSize = new Dimension(1000, 500);
    scrollPane.setMinimumSize(minimumSize);
    dungeonSidePanel.setMinimumSize(minimumSize);
    this.add(splitPane);
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
  }

  protected void addClickListener(DungeonGUIController listener) {
    dungeonSidePanel.addClickListener(listener);
  }





}
