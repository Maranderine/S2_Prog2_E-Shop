package Exceptions;

public class mitarbeiterExistiertBereitsException extends Exception{
    public mitarbeiterExistiertBereitsException() {

        super("Dieser Mitarbeiter existiert bereits");
    }
}
