package Exceptions.Benutzer;

public class ExceptionMitarbeiterNichtGefunden extends Exception{
    public ExceptionMitarbeiterNichtGefunden() {

        super("Der von Ihnen genannte Mitarbeiter konnte nicht gefunden werden");
    }
}
