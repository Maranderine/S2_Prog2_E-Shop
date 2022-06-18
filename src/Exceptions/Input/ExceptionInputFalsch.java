package Exceptions.Input;

/**
 * ExceptionInputFalsch
 */
public class ExceptionInputFalsch extends Exception {

  String definition;

  /**
   * 
   * @param message    erster teil der naachicht bei faalsch eingabe
   * @param definition ausgaben erklärung welche eingaben erlaubt sind. IST NUR
   *                   AUSGABE TEXT
   */
  public ExceptionInputFalsch(String message, String definition) {
    super(message + definition);
    this.definition = definition;
  }

  /**
   * vordefinierte message "Eingabe kann nicht angenommen werden.\n"
   * 
   * @param definition ausgaben erklärung welche eingaben erlaubt sind oder null.
   *                   IST NUR
   *                   AUSGABE TEXT
   */
  public ExceptionInputFalsch(String definition) {
    super("\n--Ungültige Eingabe." + (definition != null ? " Info:\n--" + definition : "") + "\n");
    this.definition = definition;
  }

  @Override
  public String toString() {
    return super.toString() + definition;
  }

}