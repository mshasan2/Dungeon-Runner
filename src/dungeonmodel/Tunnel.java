package dungeonmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the Tunnel Class.
 * Tunnel class contains all the methods and variables required for a tunnel type variable
 * A tunnel has two entrances and is a type of location
 * Tunnel class extends the Location class.
 */
class Tunnel extends Location {
  private Location entrance1;
  private Location entrance2;
  private List<Arrow> arrowList;

  protected Tunnel() {
    this.entrance1 = new Wall();
    this.entrance2 = new Wall();
    this.arrowList = new ArrayList<>();
  }

  protected void addEntrance1(Location entrance1) {
    this.entrance1 = entrance1;
  }

  protected void addEntrance2(Location entrance2) {
    this.entrance2 = entrance2;
  }

  protected int getNumArrows() {
    return this.arrowList.size();
  }

  protected void addArrowToTunnel(Arrow arrow) {
    this.arrowList.add(arrow);
  }

  protected boolean areArrowsPresentInTunnel() {
    return this.getNumArrows() > 0;
  }

  protected List<Arrow> lootArrowsInTunnel() {
    List<Arrow> arrowsInTunnelCopy = new ArrayList<>();
    arrowsInTunnelCopy.addAll(this.arrowList);
    this.arrowList.clear();
    return arrowsInTunnelCopy;
  }

  protected String getArrowsInTunnelShortRep() {
    List<String> arrowRep = new ArrayList<>();
    if (this.arrowList.size() != 0) {
      for (Arrow key : this.arrowList) {
        arrowRep.add("A");
      }
      Collections.sort(arrowRep);
    } else {
      arrowRep.add(" ");
    }
    return arrowRep.toString();
  }



  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("Tunnel");
    return str.toString();
  }




}
