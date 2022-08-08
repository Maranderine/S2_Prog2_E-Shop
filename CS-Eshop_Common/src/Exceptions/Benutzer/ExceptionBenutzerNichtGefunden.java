package Exceptions.Benutzer;

public class ExceptionBenutzerNichtGefunden extends Exception {
  public ExceptionBenutzerNichtGefunden() {

    super("Benutzer Konnte nicht gefunden werden");
  }
}