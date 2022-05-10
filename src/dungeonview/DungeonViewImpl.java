package dungeonview;

import dungeoncontroller.DungeonGUIController;
import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;


/**
 * This is the Dungeon View Class.
 * This class contains the methods for the GUI of the Dungeon Method.
 * This class takes in ReadOnlyDungeonModel as the parameter in the Constructor and
 * expects it to not null
 */
public class DungeonViewImpl extends JFrame implements DungeonView {
  private DungeonPanel dungeonPanel;
  private ReadOnlyDungeonModel model;

  /**
   * Constructor for the DungeonViewImpl class.
   * This constructor takes in ReadOnlyDungeonModel object as the Parameter
   * and expects it to be non-null
   *
   * @param model non-null ReadOnlyDungeonModel object
   */
  public DungeonViewImpl(ReadOnlyDungeonModel model) {
    super("Dungeon"); //Constructor of super class checks for its non-null value
    if (model == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    this.model = model;
    int width = 64 * model.getDungeonColSize();
    int height = 70 * model.getDungeonRowSize();
    setSize(width * 2, height * 2);
    this.setMinimumSize(new Dimension((int) (width * 2.2), height));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); //Centers the frame to the screen
    dungeonPanel = new DungeonPanel(model);
    add(dungeonPanel);
    //this.setResizable(false);
  }

  private void addHelpMenuBar(JMenuItem gettingStarted) {

    class HelpAction implements ActionListener {

      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        String infoHelp = "Goal: Reach the End Goal Cave by navigating through the dungeon,\n"
            + "while collecting treasures and arrows and reach your goal location by killing"
            + " the Otyughs blocking your path.\n"
            + "\n"
            + "Instruction to move the Player:\n"
            + "\n"
            + "Press Up ArrowKey to move the player in the North Direction\n"
            + "Press Down ArrowKey to move the player in the South Direction\n"
            + "Press Left ArrowKey to move the player in West Direction\n"
            + "Press Right ArrowKey to move the player in East Direction\n"
            + "\n"
            + "Click on the Up Arrow to move the player in the North Direction\n"
            + "Click on the Down ArrowKey to move the player in the South Direction\n"
            + "Click on the Left ArrowKey to move the player in West Direction\n"
            + "Click on the Right ArrowKey to move the player in East Direction\n"
            + "\n"
            + "\n"
            + "Instruction to Collect Arrow:\n"
            + "Press A Key to collect Arrows if present in the location Player is currently in.\n"
            + "\n"
            + "Instruction to Collect Treasure:\n"
            + "Press T Key to collect Treasures if present in the location Player is currently in."
            + "\n"
            + "\n"
            + "Instruction to Shoot an Arrow:\n"
            + "Press S Key followed by an arrow key to indicate the direction and "
            + "then enter the distance the arrow should travel.\n"
            + "\n"
            + "About Location Description:\n"
            + "Location Description - This contains the description about the  location the"
            + " Player is currently in.\n"
            + "The description contains the information about the type of location the Player"
            + " is currently in and the information about whether there are caves or tunnels"
            + " present in the location.\n"
            + "\n"
            + "About Player Description:\n"
            + "Player Description - This contains the Description of the Player, the"
            + " description of the treasures and arrows"
            + " the Player has collected till now.";
        JTextArea desc = new JTextArea();
        desc.append(infoHelp);
        desc.setEditable(false);
        Font boldFont = new Font(desc.getFont().getName(), Font.BOLD, desc.getFont().getSize());
        desc.setFont(boldFont);
        JPanel panel = new JPanel();
        panel.add(desc);
        int result = JOptionPane.showConfirmDialog(null, panel, "Help",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
          //Do Nothing
        } else {
          //System.out.println("Cancelled Button Clicked");
        }


      }
    }

    gettingStarted.addActionListener(new HelpAction());
  }

  private void addMenuBar(DungeonGUIController listener) {

    JMenuBar menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);
    JMenu menu = new JMenu("Menu");
    menuBar.add(menu);
    JMenu help = new JMenu("Help");
    menuBar.add(help);
    JMenuItem gettingStarted = new JMenuItem("Getting Started");
    help.add(gettingStarted);
    this.addHelpMenuBar(gettingStarted);
    JMenuItem settings = new JMenuItem("New Game");
    menu.add(settings);
    JMenuItem restart = new JMenuItem("Restart");
    menu.add(restart);
    JMenuItem reuse = new JMenuItem("Reuse");
    menu.add(reuse);
    JMenuItem quit = new JMenuItem("Quit");
    menu.add(quit);

    class QuitAction implements ActionListener {

      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    }

    quit.addActionListener(new QuitAction());

    class RestartAction implements ActionListener {

      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.restartGame();
      }
    }

    restart.addActionListener(new RestartAction());

    class SettingsAction implements ActionListener {

      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        //int rows, int cols, int interConnectivity, boolean isWrapping,
        //                           int percentOfTreasures, boolean isRandom, int numMonsters
        String[] dungeonParameters = model.getDungeonParameters();
        String[] items = {"true", "false"};
        JComboBox<String> isWrapping = new JComboBox<>(items);
        JTextField rows = new JTextField(dungeonParameters[0]);
        JTextField columns = new JTextField(dungeonParameters[1]);
        JTextField interConnectivity = new JTextField(dungeonParameters[2]);
        //JTextField field4 = new JTextField("76.54");
        JTextField percentOfTreasures = new JTextField(dungeonParameters[4]);
        JTextField numMonsters = new JTextField(dungeonParameters[5]);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Rows:"));
        panel.add(rows);
        panel.add(new JLabel("Columns:"));
        panel.add(columns);
        panel.add(new JLabel("InterConnectivity:"));
        panel.add(interConnectivity);
        panel.add(new JLabel("IsWrapping:"));
        panel.add(isWrapping);
        //panel.add(field4);
        panel.add(new JLabel("Percentage of Treasures:"));
        panel.add(percentOfTreasures);
        panel.add(new JLabel("Number Of Monsters:"));
        panel.add(numMonsters);
        //basePanel.add(panel);
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
          listener.startNewGame(rows.getText(), columns.getText(), interConnectivity.getText(),
              (String) isWrapping.getSelectedItem(), percentOfTreasures.getText(),
              numMonsters.getText());
        } else {
          //System.out.println("Cancelled Button Clicked");
        }
      }
    }

    settings.addActionListener(new SettingsAction());

    class ReUseAction implements ActionListener {

      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.reUseMap();
      }
    }

    reuse.addActionListener(new ReUseAction());
  }

  /**
   * Set up the controller to handle keyboard events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addKeyListener(DungeonGUIController listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    JRootPane rootPane = this.getRootPane();
    Action upAction = new UpAction(listener);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("UP"), "upAction");
    rootPane.getActionMap().put("upAction", upAction);

    Action downAction = new DownAction(listener);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("DOWN"), "downAction");
    rootPane.getActionMap().put("downAction", downAction);

    Action leftAction = new LeftAction(listener);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
    rootPane.getActionMap().put("leftAction", leftAction);

    Action rightAction = new RightAction(listener);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
    rootPane.getActionMap().put("rightAction", rightAction);

    Action tkeyAction = new TkeyAction(listener);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("T"), "tkeyAction");
    rootPane.getActionMap().put("tkeyAction", tkeyAction);

    Action akeyAction = new AkeyAction(listener);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("A"), "akeyAction");
    rootPane.getActionMap().put("akeyAction", akeyAction);

    Action skeyAction = new SkeyAction(listener);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("S"), "skeyAction");
    rootPane.getActionMap().put("skeyAction", skeyAction);

  }

  private class UpAction extends AbstractAction {
    private DungeonGUIController listener;

    private UpAction(DungeonGUIController listener) {
      this.listener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      //System.out.println("UP ACTION");
      this.listener.handleKeyPressed("UP");

    }
  }

  private class DownAction extends AbstractAction {
    private DungeonGUIController listener;

    private DownAction(DungeonGUIController listener) {
      this.listener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      //System.out.println("DOWN ACTION");
      this.listener.handleKeyPressed("DOWN");
    }
  }

  private class LeftAction extends AbstractAction {
    private DungeonGUIController listener;

    private LeftAction(DungeonGUIController listener) {
      this.listener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      //System.out.println("LEFT ACTION");
      this.listener.handleKeyPressed("LEFT");
    }
  }

  private class RightAction extends AbstractAction {
    private DungeonGUIController listener;

    private RightAction(DungeonGUIController listener) {
      this.listener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      //System.out.println("RIGHT ACTION");
      this.listener.handleKeyPressed("RIGHT");
    }
  }

  private class TkeyAction extends AbstractAction {
    private DungeonGUIController listener;

    private TkeyAction(DungeonGUIController listener) {
      this.listener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      this.listener.handleKeyPressed("T");

    }
  }

  private class AkeyAction extends AbstractAction {
    private DungeonGUIController listener;

    private AkeyAction(DungeonGUIController listener) {
      this.listener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      //System.out.println("A KEY ACTION");
      this.listener.handleKeyPressed("A");

    }
  }

  private class SkeyAction extends AbstractAction {
    private DungeonGUIController listener;

    private SkeyAction(DungeonGUIController listener) {
      this.listener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      //System.out.println("S KEY ACTION");
      this.listener.handleKeyPressed("S");

    }
  }

  /**
   * Transmit a message to the view.
   * The view displays the message that was provided
   * and Returns an Empty String after displaying the message.
   *
   * @param message message to be displayed
   */
  @Override
  public String showMessage(String message) {
    if (message == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    String str = "";
    if (message.equals("JInput")) {
      str = JOptionPane.showInputDialog("Enter Distance In No. of Caves"
          + " \n(Eg: 1)");
    } else {
      JOptionPane.showMessageDialog(this, message);
    }
    return str;
  }


  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addClickListener(DungeonGUIController listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Invalid Input");
    }
    this.addMenuBar(listener);
    dungeonPanel.addClickListener(listener);
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    repaint();
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }





}
