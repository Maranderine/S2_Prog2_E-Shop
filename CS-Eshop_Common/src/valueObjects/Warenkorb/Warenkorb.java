package Domain.Warenkorb;

import java.util.HashMap;
import java.util.Map.Entry;

import Domain.Artikel.Artikel;

/**
 * Der direkte Warenkorb.
 * HashMap<Artikel, Integer> inhalt
 */
class Warenkorb {/* "protected" */
  protected HashMap<Artikel, Integer> inhalt;

  /**
   * neuer Warenkorb
   */
  protected Warenkorb() {
    this.inhalt = new HashMap<Artikel, Integer>();
  }

  /**
   * exitierender Warenkorb
   */
  protected Warenkorb(HashMap<Artikel, Integer> exitierenderWarenkorb) {
    this.inhalt = exitierenderWarenkorb;
  }

  protected void clear() {
    inhalt.clear();
  }

  @Override
  public String toString() {

    String str = "\tName\tBestand\tPreis\n";

    if (!inhalt.isEmpty())
      for (Entry<Artikel, Integer> entry : inhalt.entrySet()) {
        str += "\t" + entry.getKey() + "\t" + entry.getValue() + "\n";
      }
    else
      str += "Keine Artikel";

    return str;
  }
}