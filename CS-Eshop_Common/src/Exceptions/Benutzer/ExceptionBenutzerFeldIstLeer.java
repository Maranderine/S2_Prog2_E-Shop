package Exceptions.Benutzer;


public class ExceptionBenutzerFeldIstLeer extends Exception {
    public ExceptionBenutzerFeldIstLeer() {

        super("Bitte geben Sie ein Benutzernamen ein");
    }
}
