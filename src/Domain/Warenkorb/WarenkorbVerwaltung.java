package Domain.Warenkorb;

import java.util.HashMap;

import Domain.Artikel.Artikel;

/**
 * verwaltet den warenkorb
 */
public class WarenkorbVerwaltung{

  Warenkorb warenkorb;

  public WarenkorbVerwaltung() {
    warenkorb = new Warenkorb();
  }

  /*
   * Gibt Warenkorb Inhalt zurück
   */
  public HashMap<Artikel, Integer> getInhalt() {
    return this.warenkorb.inhalt;
  }

  /*
   * Gibt Warenkorb
   */
  public Warenkorb getWarenkorb() {
    return this.warenkorb;
  }

  /**
   * Setzt den gesamten inhalt des Warenkorbes
   * 
   * @param inhalt
   */
  public void setInhalt(HashMap<Artikel, Integer> inhalt) {
    this.warenkorb.inhalt = inhalt;
  }

  /**
   * erstellt einen neuen eintrag oder ändert einen vorhandenen
   */
  public void setArtikel(Artikel artikel, Integer integer) {
    this.warenkorb.inhalt.put(artikel, integer);
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

  /**
   * @author Maranderine
   */
  public HashMap<Artikel, Integer> ArtikelKaufen() {
    return  warenkorb.inhalt;
  }

  @Override
  public String toString() {
    return warenkorb.toString();

  }
}
