package Exceptions.Benutzer;

public class ExceptionBenutzerNameBereitsVergeben extends Exception{
    public ExceptionBenutzerNameBereitsVergeben() {

        super("Dieser Benutzername ist bereits vergeben");
    }
}
