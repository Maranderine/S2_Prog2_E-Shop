
import Domain.Eshop;
import UserInterface.UserInterface;

/**
 * Main class was quasi das programm repräsentiert
 */
public abstract class MAIN {

  // create eshop

  private final static Eshop eshop = new Eshop("Nutzer.txt", "Ereignisse.txt");

  // get used user interface from Eshop
  private final static UserInterface ui = eshop.createUserInterface();

  /**
   * main loop method des programmes
   * 
   * @param args()
   */
  public static void main(String args[]) {
    // main loop des programmes
    while (ui.run()) {
    }
  }
}
