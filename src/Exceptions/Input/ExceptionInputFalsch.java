package Exceptions.Input;

/**
 * ExceptionInputFalsch
 */
public class ExceptionInputFalsch extends Exception {

  String definition;

  /**
   * 
   * @param message
   * @param definition definition welche eingaben erlaubt sind
   */
  public ExceptionInputFalsch(String message, String definition) {
    super(message);
    this.definition = definition;
  }

  @Override
  public String toString() {
    return super.toString() + definition;
  }

}