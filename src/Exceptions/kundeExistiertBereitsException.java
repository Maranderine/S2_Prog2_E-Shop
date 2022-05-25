package Exceptions;

public class kundeExistiertBereitsException extends Exception{
    public kundeExistiertBereitsException() {

        super("Dieser Kunde existiert bereits");
    }
}
