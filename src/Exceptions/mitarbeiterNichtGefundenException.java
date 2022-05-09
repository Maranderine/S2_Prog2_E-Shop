package Exceptions;

public class mitarbeiterNichtGefundenException extends Exception{
    public mitarbeiterNichtGefundenException() {

        super("Der von Ihnen genannte Mitarbeiter konnte nicht gefunden werden");
    }
}
