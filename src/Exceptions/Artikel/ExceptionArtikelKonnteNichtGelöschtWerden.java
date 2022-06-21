package Exceptions.Artikel;

public class ExceptionArtikelKonnteNichtGelöschtWerden extends ExceptionArtikel {

  public ExceptionArtikelKonnteNichtGelöschtWerden() {
    super("Artikel konnte nicht gelöscht werden");
  }

  public ExceptionArtikelKonnteNichtGelöschtWerden(Throwable cause) {
    super(cause);
  }
}