package Exceptions;

public class nichtGenugBestandException extends Exception{
    public nichtGenugBestandException() {

        super("Die von Ihnen gewählte Menge ist nicht auf Lager");
    }
}
