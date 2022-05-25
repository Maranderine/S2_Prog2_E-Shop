package Exceptions;

public class benutzernameBereitsVergebenException extends Exception{
    public benutzernameBereitsVergebenException() {

        super("Dieser Benutzername ist bereits vergeben");
    }
}
