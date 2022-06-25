package Exceptions.Artikel;

import Domain.Artikel.Artikel;

public class ExceptionArtikelNichtGenugBestand extends ExceptionArtikel {
  public ExceptionArtikelNichtGenugBestand(Artikel artikel) {

    super(artikel, artikel.getName() + " ist nicht in der von ihnen gewählten Menge auf Lager.");
  }
}
