import Local.*;

/**
 * Main loop des programms
 */
abstract class MAIN {

  // loop main bool
  public static Boolean LOOP = true;

  /**
   * main methode
   * 
   * @param args()
   */
  public static void main(String args[]) {

    CUI cui = new CUI();

    while (cui.CUImenu()) {
    }
  }
}
