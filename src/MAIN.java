
// import Domain.Eshop;
import Client.Eshop;
import UserInterface.UserInterface;
import UserInterface.CUI;
import UserInterface.GUI.GUI;

/**
 * Main class was quasi das programm repräsentiert
 */
public abstract class MAIN {

  // create eshop
  private static Eshop eshop;

  // get used user interface from Eshop
  // private final static UserInterface ui = eshop.createUserInterface();
  private static UserInterface ui;

  /**
   * main loop method des programmes
   * 
   * @param args
   */
  public static void main(String args[]) {
    String host = "localhost";
    int port = 6789;
    if (args.length == 2) {
      host = args[0];
      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        port = 0;
      }
    }

    eshop = new Eshop(host, port);
    ui = CreateUI();

    // main loop des programmes
    ui.run();

    eshop.quit();
  }

  static private UserInterface CreateUI() {

    switch (eshop.createUserInterface()) {
      case "CUI":
        return new CUI(eshop);
      case "GUI":
        return new GUI(eshop);
    }
    return null;
  }
}
