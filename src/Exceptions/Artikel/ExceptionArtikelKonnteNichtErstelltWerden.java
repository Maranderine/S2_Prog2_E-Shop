package Exceptions.Artikel;

public class ExceptionArtikelKonnteNichtErstelltWerden extends ExceptionArtikel {

  public ExceptionArtikelKonnteNichtErstelltWerden() {
    super("Artikel konnte nicht erstellt werden");
  }

  public ExceptionArtikelKonnteNichtErstelltWerden(Throwable cause) {
    super(cause);
  }

}
