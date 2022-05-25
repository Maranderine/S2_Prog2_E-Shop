package Exceptions;

public class artikelExistiertBereitsException extends Exception{
    public artikelExistiertBereitsException() {
        super("Der von Ihnen angegebene Artikel existiert schon");
    }
}
