package Domain.Warenkorb;

import java.util.Date;
import java.util.HashMap;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;

public class Rechnung {

  // kunde
  private Benutzer käuferObjekt;
  private int käuferNummer;
  private String käuferName;

  /** gesamt betrag der Rechnung */
  private double betrag = 0;
  /** string mit allen artikeln, anzahlen und dem betrag */
  private String ausgabeString = "";
  private int rechnungsId;
  private Date kaufDatum;

  /** copie des gekaufeten warenkorbs !Objekte sind nicht eingefrohren! */
  private HashMap<Artikel, Integer> inhalt;

  // type safety ist gegeben... *seuftz*
  protected Rechnung(Benutzer käufer, int käuferNummer, String käufereName, HashMap<Artikel, Integer> ArtikelListe,
      int id) {
    @SuppressWarnings("unchecked")
    HashMap<Artikel, Integer> inhalt = (HashMap<Artikel, Integer>) ArtikelListe.clone();
    this.inhalt = inhalt;

    this.käuferObjekt = käufer;
    this.käuferNummer = käuferNummer;
    this.käuferName = käufereName;
    // geht durch die map, addiert betrag, erstellt ausgebe string
    ArtikelListe.forEach((artikel, bestand) -> {
      betrag += artikel.getPreis() * bestand;
      ausgabeString += artikel.toString() + "\t" + bestand + "\n";
    });
    kaufDatum = new Date();
    rechnungsId = id;
  }

  // #region getter

  /**
   * getkäuferObjekt
   * 
   * @return
   */
  public Benutzer getkäuferObjekt() {
    return käuferObjekt;
  }

  /**
   * getkäuferNummer
   * 
   * @return
   */
  public int getkäuferNummer() {
    return käuferNummer;
  }

  /**
   * getkäuferName
   * 
   * @return
   */
  public String getkäuferName() {
    return käuferName;
  }

  //
  /**
   * Getter für das Kaufdatum, welcher das Kaufdatum übergibt.
   * 
   * @return
   */
  protected String getKaufDatum() {
    return kaufDatum.toString();
  }

  /**
   * Getter für die Rechnungs ID der Rechnung, der die Rechnungs ID übergibt.
   * 
   * @return
   */
  //
  protected String getRechnungsId() {
    return Integer.toString(rechnungsId);
  }

  /**
   * Wenn abgefragt wird der Wert auf 0 und addiert den Preis des Artikels
   * dazu(z.B. DataObjects.Artikel & Anzahl)
   * 
   * @return
   */
  protected double getGesamtPreis() {
    return this.betrag;
  }

  /**
   * Gibt den Inhalt aus der HashMap-Array zurück
   * 
   * @return
   */
  public HashMap<Artikel, Integer> getInhalt() {
    return this.inhalt;
  }

  // #endregion
  public String toString() {
    // return this.artikelNr + " | " + this.name + " | " + this.bestand + " | " +
    // this.preis;
    return "Rechnungs Id: " + this.rechnungsId + "\n" +
        "Datum: " + this.getKaufDatum() + "\n" +
        "________________________" + "\n" +
        ausgabeString +
        "________________________" + "\n" +
        "Gesamtbetrag: " + this.betrag + "\n";
  }
}