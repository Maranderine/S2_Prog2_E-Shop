package Exceptions;

public class benutzerFeldIstLeerException extends Exception {
    public benutzerFeldIstLeerException() {

        super("Bitte geben Sie ein Benutzernamen ein");
    }
}
