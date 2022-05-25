package Exceptions;

public class artikelNichtGefundenException extends Exception{
    public artikelNichtGefundenException() {

        super("Ihr genannter Artikel wurde nicht gefunden");
    }
}
