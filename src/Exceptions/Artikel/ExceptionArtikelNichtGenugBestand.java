package Exceptions.Artikel;

import Domain.Artikel.Artikel;

public class ExceptionArtikelNichtGenugBestand extends ExceptionArtikel {
  public ExceptionArtikelNichtGenugBestand(Artikel artikel) {

    super(artikel, "Die von Ihnen gewählte Menge ist nicht auf Lager");
  }
}
