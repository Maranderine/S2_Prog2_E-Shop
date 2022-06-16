package Domain.Warenkorb;

import java.util.HashMap;
import java.util.Map.Entry;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelNichtGenugBestand;

/**
 * verwaltet den warenkorb
 */
public class WarenkorbVerwaltung extends Verwaltung {

  Warenkorb warenkorb;
  private int RechnungZaehler;
  private final Eshop eshop;
  private ArtikelVerwaltung artikelVW;

  /**
   * WarenkorbVerwaltung
   */
  public WarenkorbVerwaltung(Eshop eshop, ArtikelVerwaltung ArtikelVW) {
    artikelVW = ArtikelVW;
    warenkorb = new Warenkorb();
    this.eshop = eshop;
    RechnungZaehler = 1;
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
  public void setArtikel(Artikel artikel, Integer integer)  {
    this.warenkorb.inhalt.put(artikel, integer);
  }

  /**
   * entfernt einen artikel aus der map
   */
  public void removeArtikel(Artikel artikel) {
    this.warenkorb.inhalt.remove(artikel);
  }

  /**
   * löscht den Warenkorb Inhalt
   */
  public void clearAll() {
    Warenkorb warenkorbToDelete = this.warenkorb;
    warenkorbToDelete.clear();
  }

  /**
   * Kauft artikel im Warenkorb: checkt bestand, erstellt Rechnung, leert
   * Warenkrob
   * 
   * @return Rechnung generierte Rechnung
   * @throws ExceptionCollection
   */
  public Rechnung ArtikelKaufen() throws ExceptionArtikelCollection {
    // hard set im moment, ändern in kunden bezogenes parameter
    Warenkorb warenkorbZuKaufen = warenkorb;

    checkWarenkorb(warenkorbZuKaufen.inhalt);

    // keine fehler
    Rechnung rechnung = new Rechnung(warenkorbZuKaufen.inhalt, useRechnungZaehler());
    // warenkorb löschen
    clearAll();

    return rechnung;
  }

  /**
   * checkt ob alle artikel im warenkorb kaufbar sind
   * 
   * @param artikelListe liste an artikeln und deren anzahl
   * @throws ExceptionArtikelCollection
   */
  public void checkWarenkorb(HashMap<Artikel, Integer> artikelListe) throws ExceptionArtikelCollection {
    ExceptionArtikelCollection collection = new ExceptionArtikelCollection("Probleme im Warenkorb");

    for (Entry<Artikel, Integer> entry : artikelListe.entrySet()) {
      // suche artikel fehler
      Artikel artikel = entry.getKey();
      if (!artikelVW.eventCheckBestand(artikel, entry.getValue()))
        collection.add(new ExceptionArtikelNichtGenugBestand(artikel));
    }
    if (!collection.isEmpty())
      throw collection;
  }

  // #endregion

  // #region Rechnung

  private int useRechnungZaehler() {
    return RechnungZaehler++;
  }

  // #endregion

  @Override
  public String toString() {
    return warenkorb.toString();

  }
}
