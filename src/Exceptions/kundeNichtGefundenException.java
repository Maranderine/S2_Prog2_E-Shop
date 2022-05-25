package Exceptions;

public class kundeNichtGefundenException extends Exception{
    public kundeNichtGefundenException() {

        super("Der von ihnen genannte Kunde konnte nicht gefunden werden");
    }
}
