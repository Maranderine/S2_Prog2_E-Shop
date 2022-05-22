package Domain.Warenkorb;

import java.util.HashMap;

import DatenObjekte.Artikel;

/**
 * Der direkte Warenkorb.
 * HashMap<Artikel, Integer> inhalt
 */
class Warenkorb {/* "protected" */
  protected HashMap<Artikel, Integer> inhalt;
  /**
   * Warenkorb Constructor
   */
  protected Warenkorb() {
    this.inhalt = new HashMap<Artikel, Integer>();
  }
}