package UserInterface;

import Domain.Eshop;

/**
 * Parent for local usability classes like CUI or GUI
 */
public abstract class UserInterface {
  // a unique hash value to identify the user when logged in
  public static byte[] userHash = {};
  private Eshop eshop;

  public UserInterface(Eshop eshop) {
    this.eshop = eshop;
  }

  /**
   * abstract standard run method.
   * is the main loop of the User Interface.
   * Must be inherited.
   */
  public abstract boolean run();
}
