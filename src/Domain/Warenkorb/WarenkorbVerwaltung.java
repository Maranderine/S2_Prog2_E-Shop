package Domain.Warenkorb;

import java.util.HashMap;

import DatenObjekte.Artikel;

/**
 * Verwaltet den warenkorb
 */
public class WarenkorbVerwaltung {

  Warenkorb warenkorb;

  public WarenkorbVerwaltung() {
    warenkorb = new Warenkorb();
  }

  /*
   * Gibt Wahrenkorb Inhalt zurück
   */
  public HashMap<Artikel, Integer> getInhalt() {
    return this.warenkorb.inhalt;
  }

  /**
   * Setzt den gesamten inhalt des warenkorbs
   * 
   * @param inhalt
   */
  public void setInhalt(HashMap<Artikel, Integer> inhalt) {
    this.warenkorb.inhalt = inhalt;
  }

  /**
   * erstellt einen neuen eintrag oder ändert einen vorhandenen
   */
  public void setArtikel(Artikel artikel, Integer integar) {
    this.warenkorb.inhalt.put(artikel, integar);
  }

  /**
   * entfernt einen artikel aus der map
   */
  public void removeArtikel(Artikel artikel) {
    this.warenkorb.inhalt.remove(artikel);
  }

  /**
   * cleared den Warenkorb Inhalt
   */
  public void clearAll() {
    this.warenkorb.inhalt.clear();
  }
}
