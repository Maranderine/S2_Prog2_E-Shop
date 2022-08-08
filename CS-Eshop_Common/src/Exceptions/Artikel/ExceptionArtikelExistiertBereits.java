package Exceptions.Artikel;

import Domain.Artikel.Artikel;

public class ExceptionArtikelExistiertBereits extends ExceptionArtikel {

  public ExceptionArtikelExistiertBereits(Artikel artikel) {
    super(artikel, "Der von Ihnen angegebene Artikel existiert schon");

  }

}
