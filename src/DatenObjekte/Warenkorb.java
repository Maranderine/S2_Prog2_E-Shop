package DatenObjekte;

import java.util.HashMap;

/**
 * Die Warenkorb Klasse.
 * HashMap<Artikel, Integer> inhalt
 */
public class Warenkorb {
  private HashMap<Artikel, Integer> inhalt;
  /**
   * Warenkorb Constructor
   */
  public Warenkorb() {
    this.inhalt = new HashMap<Artikel, Integer>();
  }

  /**
   * Gibt Wahrenkorb Inhalt zurück
   */
  public HashMap<Artikel, Integer> getInhalt() {
    return this.inhalt;
  }

  public void setInhalt(HashMap<Artikel, Integer> inhalt) {
    this.inhalt = inhalt;
  }

  /**
   * erstellt einen neuen eintrag oder ändert einen vorhandenen
   */
  public void setArtikel(Artikel artikel, Integer integar) {
    this.inhalt.put(artikel, integar);
  }

  /**
   * entfernt einen artikel aus der map
   */
  public void removeArtikel(Artikel artikel) {
    this.inhalt.remove(artikel);
  }

  /**
   * cleared den Warenkorb Inhalt
   */
  public void clearAll() {
    this.inhalt.clear();
  }
}