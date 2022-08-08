package Exceptions.Benutzer;

public class ExceptionKundeNichtGefunden extends Exception{
    public ExceptionKundeNichtGefunden() {

        super("Der von ihnen genannte Kunde konnte nicht gefunden werden");
    }
}
