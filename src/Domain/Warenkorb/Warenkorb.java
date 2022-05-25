package Domain.Warenkorb;

import java.util.HashMap;
import java.util.Map.Entry;

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

  @Override
  public String toString() {

    String str = "Artikelnr\tName\tBestand\tPreis\n";

    if (!inhalt.isEmpty())
      for (Entry<Artikel, Integer> entry : inhalt.entrySet()) {
        str += "\t" + entry.getKey() + "\t" + entry.getValue() + "\n";
      }
    else
      str += "Keine Artikel";

    return str;
  }
}