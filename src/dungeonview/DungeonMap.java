package dungeonview;

import dungeonmodel.Pair2;
import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


class DungeonMap extends JPanel {
  private ReadOnlyDungeonModel model;

  protected DungeonMap(ReadOnlyDungeonModel model) {
    this.model = model;
    //this.setSize(model.getDungeonRowSize(), model.getDungeonColSize(), 0, 0);
    int width = 64 * model.getDungeonColSize();
    int height = 70 * model.getDungeonRowSize(); // * 2;
    //setSize(256, 512); Size for 4 * 4
    this.setPreferredSize(new Dimension(width, height));
    this.setLayout(new GridLayout(model.getDungeonRowSize(), model.getDungeonColSize(), 0, 0));
    this.setBackground(Color.BLACK);

  }


  private String checkForImageName(Pair2 curLoc) {
    List<String> dirList = this.model.getLocationAdjacentOpeningsDirections(curLoc);
    StringBuilder fileName = new StringBuilder();
    //System.out.println("DungeonMap ImageName: " + dirList);
    for (String dir : dirList) {
      fileName.append(dir.charAt(0));
    }
    return fileName.toString();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    List<Pair2> exploredLocations = this.model.getDungeonExploredLocations();
    boolean flag;
    for (int i = 0; i < this.model.getDungeonRowSize(); i++) {
      for (int j = 0; j < this.model.getDungeonColSize(); j++) {
        flag = false;
        int x = 64 * j;
        int y = 64 * i;
        for (Pair2 curLoc : exploredLocations) {
          if (curLoc.equals(new Pair2(i, j))) {
            String imgName = this.checkForImageName(curLoc);
            BufferedImage img3 = null;
            try {
              Image img2 = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/img/" + imgName + ".png")));
              if (model.getPlayerPresentPosition() == curLoc) {
                if (imgName.length() == 2) {
                  img3 = overlay((BufferedImage) img2, "/img/stickman.png", 15);
                } else {
                  img3 = overlay((BufferedImage) img2, "/img/stickman.png", 10);
                }
              }
              if (model.getArrowsPresentInLoc(curLoc)) {
                int offset = 10;
                String arrowImageName;
                if (imgName.length() == 2) {
                  arrowImageName = "arrow-white";
                  if (imgName.equals("ES") || imgName.equals("SW")) {
                    offset = 15;
                  }
                } else {
                  arrowImageName = "arrow-black";
                }
                if (img3 == null) {
                  img3 = overlay((BufferedImage) img2, "/img/" + arrowImageName + ".png", offset);
                } else {
                  img3 = overlay(img3, "/img/" + arrowImageName + ".png", offset);

                }
              }
              if (model.getTreasurePresentInLoc(curLoc)) {
                if (img3 == null) {
                  img3 = overlay((BufferedImage) img2, "/img/ruby.png", 30);
                } else {
                  img3 = overlay(img3, "/img/ruby.png", 30);
                }
              }
              if (model.getStenchLevelInLoc(curLoc) > 0) {
                String stenchImageName = "stench0" + model.getStenchLevelInLoc(curLoc);
                if (img3 == null) {
                  img3 = overlay((BufferedImage) img2, "/img/" + stenchImageName + ".png", 0);
                } else {
                  img3 = overlay(img3, "/img/" + stenchImageName + ".png", 0);
                }
              }

              if (model.getOtyughPresentInLoc(curLoc)) {
                if (img3 == null) {
                  img3 = overlay((BufferedImage) img2, "/img/otyugh.png", 5);
                } else {
                  img3 = overlay(img3, "/img/otyugh.png", 5);
                }
              }

              if (img3 == null) {
                g2d.drawImage(img2, x, y, null);
              } else {
                g2d.drawImage(img3, x, y, null);
              }


            } catch (IOException e) {
              e.printStackTrace();
            }
            flag = true;
          }
        }
      }
    }
  }

  private BufferedImage overlay(BufferedImage starting, String fpath, int offset)
      throws IOException {
    BufferedImage overlay = ImageIO.read(Objects.requireNonNull(getClass().getResource(fpath)));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }

}
