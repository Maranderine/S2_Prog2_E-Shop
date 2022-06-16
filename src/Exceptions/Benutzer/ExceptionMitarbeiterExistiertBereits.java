package Exceptions.Benutzer;

public class ExceptionMitarbeiterExistiertBereits extends Exception{
    public ExceptionMitarbeiterExistiertBereits() {

        super("Dieser Mitarbeiter existiert bereits");
    }
}
