package Exceptions.Artikel;

import Domain.Artikel.Artikel;

/**
 * ExceptionArtikelNameExistiertBereits
 */
public class ExceptionArtikelNameExistiertBereits extends ExceptionArtikel {

  /**
   * 
   * @param artikel bereist existierender artikel
   */
  public ExceptionArtikelNameExistiertBereits(Artikel artikel) {
    super(artikel, "Ein weiterer Artikel mit diesem Namen existiert bereits");

  }
}