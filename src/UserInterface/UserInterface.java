package UserInterface;

/**
 * Parent for local usability classes like CUI or GUI
 */
public abstract class UserInterface{
  // a unique hash value to identify the user when logged in
  public String userHash;

  public UserInterface() {

  }

  /**
   * abstract standard run method.
   * is the main loop of the User Interface.
   * Must be inherited.
   */
  public abstract boolean run();
}
