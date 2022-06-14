package Exceptions.Benutzer;

public class ExceptionKundeExistiertBereits extends Exception{
    public ExceptionKundeExistiertBereits() {

        super("Dieser Kunde existiert bereits");
    }
}
