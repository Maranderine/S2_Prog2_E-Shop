package Exceptions.Artikel;

import Domain.Artikel.Artikel;

public class ExceptionArtikelUngültigerBestand extends ExceptionArtikel {

  public ExceptionArtikelUngültigerBestand(Artikel artikel) {
    super(artikel, "Bestand des Artikels ist ungültig");
  }

}
