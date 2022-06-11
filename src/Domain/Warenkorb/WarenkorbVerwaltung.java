package Domain.Warenkorb;

import java.util.HashMap;
import java.util.Map.Entry;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Artikel.Artikel;

/**
 * verwaltet den warenkorb
 */
public class WarenkorbVerwaltung extends Verwaltung {

  Warenkorb warenkorb;
  private int RechnungZaehler;
  private final Eshop eshop;

  /**
   * WarenkorbVerwaltung
   */
  public WarenkorbVerwaltung(Eshop eshop) {
    warenkorb = new Warenkorb();
    this.eshop = eshop;
    RechnungZaehler = 1;
  }

  /**
   * WarenkorbVerwaltung und setzt den rechnungZaehler
   * 
   * @param rechnungZaehler
   */
  public WarenkorbVerwaltung(Eshop eshop, int rechnungZaehler) {
    warenkorb = new Warenkorb();
    this.eshop = eshop;
    RechnungZaehler = rechnungZaehler;
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
   */
  public Rechnung ArtikelKaufen() {
    // hard set im moment, ändern in kunden bezogenes parameter
    Warenkorb warenkorbZuKaufen = warenkorb;

    HashMap<Artikel, ARTIKELFEHLER> fehlerArtikel = checkWarenkorb(warenkorbZuKaufen.inhalt);

    // Ereignis_EreignisSystemArtikel

    // keine fehler
    if (fehlerArtikel.isEmpty()) {
      Rechnung rechnung = new Rechnung(warenkorbZuKaufen.inhalt, useRechnungZaehler());

      // warenkorb löschen
      clearAll();
      return rechnung;
    } else {
      // gib fehler aus
      System.out.println(fehlerArtikel);
      return null;
    }

  }

  // #region Warenkorb/Artikel check
  /**
   * enum mit allen möglichen artikel fahlern
   */
  protected enum ARTIKELFEHLER {
    /** keine artikel fehler */
    NONE(0, "Keine Fehler"),
    /** Fehler in der Artikel anzahl */
    STÜCKZAHL(1, "Gefragte anzahl ist größer als Anzahl im Lager");

    // index des fehlers
    private int index;
    /** beschreibung des fehlers */
    private String beschreibung;

    /**
     * basis contructor
     * 
     * @param num
     * @param desc
     */
    ARTIKELFEHLER(int index, String desc) {
      this.index = index;
      beschreibung = desc;
    }

    // #region generell
    /** returns assigned index */
    protected int index() {
      return index;
    }

    /** returns assigned index */
    protected int i() {
      return index();
    }

    @Override
    public String toString() {
      return beschreibung;
    }

    // #endregion
  }

  /**
   * checkt ob alle artikel im warenkorb kaufbar sind
   * 
   * @param artikelListe liste an artikeln und deren anzahl
   * @return HashMap die problem Artikel und deren problem als enum ARTIKELFEHLER
   *         eintrag enthält, oder null
   */
  public HashMap<Artikel, ARTIKELFEHLER> checkWarenkorb(HashMap<Artikel, Integer> artikelListe) {

    HashMap<Artikel, ARTIKELFEHLER> problemArtikel = new HashMap<Artikel, ARTIKELFEHLER>();
    ARTIKELFEHLER artikelFehler;
    Artikel artikel;
    for (Entry<Artikel, Integer> entry : artikelListe.entrySet()) {
      // checkt artikel nach fehler
      artikel = entry.getKey();
      // suche artikel fehler
      artikelFehler = checkArtikel(artikel, entry.getValue());

      // artikel hat fehler
      if (artikelFehler != null) {
        problemArtikel.put(artikel, artikelFehler);
      }
    }

    if (!problemArtikel.isEmpty())
      return problemArtikel;

    return null;
  }

  /**
   * überprüft gegebenen Artikel auf kauf fehler
   * 
   * @param artikel    Artikel zu checken
   * @param kaufanzahl
   * @return int fehler enum 0 wenn alles okay ist, false wenn ein fehler
   *         aufgetreten
   *         ist
   */
  public ARTIKELFEHLER checkArtikel(Artikel artikel, int kaufanzahl) {
    // checkt alle fehler des artikels
    // check ob stückzahl genug ist
    if (artikel.getBestand() < kaufanzahl) {
      return ARTIKELFEHLER.STÜCKZAHL;// gelagerte anzahl weniger als zu kaufende anzahl
    }

    return ARTIKELFEHLER.NONE;
  }

  // #endregion

  // #region Rechnung

  private int useRechnungZaehler() {
    return RechnungZaehler++;
  }

  // #endregion

  // #region ereignisse

  // #endregion

  @Override
  public String toString() {
    return warenkorb.toString();

  }
}
