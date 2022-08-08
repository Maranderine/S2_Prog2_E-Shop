package Exceptions.Benutzer;

public class ExceptionBenutzerNameUngültig extends Exception {

  public ExceptionBenutzerNameUngültig() {
    super("Benutzer name ist nicht gültig.");
  }

  public ExceptionBenutzerNameUngültig(String message) {
    super(message);
  }

}
