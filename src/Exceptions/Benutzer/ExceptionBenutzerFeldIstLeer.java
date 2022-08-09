package Exceptions.Benutzer;

import java.io.Serializable;

public class ExceptionBenutzerFeldIstLeer extends Exception {
    public ExceptionBenutzerFeldIstLeer() {

        super("Bitte geben Sie ein Benutzernamen ein");
    }
}
