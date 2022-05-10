package dungeonview;

import dungeoncontroller.DungeonGUIController;
import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;



/**
 * DungeonSidePanel class extends JPanel.
 * This class contains all the components displayed in the side panel
 */
class DungeonSidePanel extends JPanel {
  private ReadOnlyDungeonModel model;

  protected DungeonSidePanel(ReadOnlyDungeonModel model) {
    this.setSize(300, 300);
    this.setLayout(new BorderLayout());
    this.model = model;
    this.setBackground(Color.BLUE);

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    Toolkit t = Toolkit.getDefaultToolkit();
    Image img1 = t.getImage(getClass().getResource("/img/aa.png"));
    g2d.drawImage(img1, 20, 20, this);
    String str = "Location Description: ";
    int y = 200;
    FontMetrics fm = g.getFontMetrics();
    Rectangle2D rect = fm.getStringBounds(str, g);
    g.setColor(Color.BLACK);
    g.fillRect(40,
        y - fm.getAscent(),
        (int) rect.getWidth(),
        (int) rect.getHeight());
    g2d.setColor(Color.YELLOW);
    g2d.drawString(str, 40, y);
    String strLoc = model.getDungeonPlayerCurrLocationDetails();
    for (String line : strLoc.split("\n")) {
      g.drawString(line, 40, y += g.getFontMetrics().getHeight());
    }

    g.drawString("", 40, y += g.getFontMetrics().getHeight());
    String str2 = "Player Description:";
    FontMetrics fm2 = g.getFontMetrics();
    Rectangle2D rect2 = fm2.getStringBounds(str2, g);
    g.setColor(Color.BLACK);
    g.fillRect(40,
        y + g.getFontMetrics().getHeight() - fm.getAscent(),
        (int) rect2.getWidth(),
        (int) rect2.getHeight());
    g2d.setColor(Color.YELLOW);
    String strDesc = model.getPlayerFullDesc();
    g.drawString(str2, 40, y += g.getFontMetrics().getHeight());
    for (String line : strDesc.split("\n")) {
      g.drawString(line, 40, y += g.getFontMetrics().getHeight());
    }
    //g2d.drawString(sb.toString(), 40, 50);
  }



  protected void addClickListener(DungeonGUIController listener) {
    //this.addMenuBar(listener);
    MouseAdapter clickHandler = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int y = e.getY();
        int x = e.getX();
        if ((x > 80 && x < 126) && (y > 31 && y < 77)) {
          listener.handleCellClick(0, 1);
        } else if ((x > 26 && x < 79) && (y > 79 && y < 127)) {
          listener.handleCellClick(0, 2);
        } else if ((x > 129 && x < 181) && (y > 79 && y < 127)) {
          listener.handleCellClick(0, 3);
        } else if ((x > 80 && x < 126) && (y > 129 && y < 177)) {
          listener.handleCellClick(0, 4);
        } else {
          listener.handleCellClick(0, 0);
        }
      }
    };
    this.addMouseListener(clickHandler);
  }




}
