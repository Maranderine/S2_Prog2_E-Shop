package Domain.EreignisLog.Ereignisse.System;

import Domain.Verwaltung;
import Domain.Artikel.Artikel;
import Domain.EreignisLog.Interfaces.EreignisInterface_ZielArtikel;

/** Ereignis Klasse für System erstellte Ereignisse in bezug auf Artikel */
public class EreignisSystemArtikel extends EreignisSystem implements EreignisInterface_ZielArtikel {

  /** artikel objekt */
  private Artikel artikel;
  /** artikel idenfikator */
  private int artikelNummer;
  /** artikel name */
  private String artikelName;
  /** artikel aktueller bestand */
  private int artikelBestand;
  /** artikel aktueller preis */
  private double artikelPreis;

  /**
   * Ereignis Klasse für System erstellte Ereignisse in bezug auf Artikel
   * 
   * @param verwaltung     Verwaltung die das event erstellt
   * @param ereignisNummer event identifikator
   * @param ereignisDesc   event erklärungs text
   * @param artikel        Artikel Object reference
   * @param artikelNummer  Artikel Identifikations Nummer
   * @param artikelName    Artikel Name als string
   * @param artikelBestand Artikel Bestabnd nummer
   * @param artikelPreis   Artikel Preis nummer
   */
  public EreignisSystemArtikel(Verwaltung verwaltung, int ereignisNummer, String ereignisDesc, Artikel artikel,
      int artikelNummer, String artikelName, int artikelBestand, double artikelPreis) {
    super(verwaltung, ereignisNummer, ereignisDesc);
    this.artikel = artikel;
    this.artikelNummer = artikelNummer;
    this.artikelName = artikelName;
    this.artikelBestand = artikelBestand;
    this.artikelPreis = artikelPreis;

    // search terms
    String[] searchTerms = { "artikel", "ware", "preis", Integer.toString(this.artikelNummer) };
    SearchTermAdd(searchTerms);
    // add artikel name split
    SearchTermAdd(this.artikelName.split(" "));
  }

  // #region implementations
  @Override
  public Artikel getZielArtikel() {
    return this.artikel;
  }

  @Override
  public int getZielArtikelNummer() {
    return this.artikelNummer;
  }

  @Override
  public String getZielArtikelName() {
    return this.artikelName;
  }

  @Override
  public int getZielArtikelBestand() {
    return this.artikelBestand;
  }

  @Override
  public double getZielArtikelPreis() {
    return this.artikelPreis;
  }
  // #endregion

  @Override
  public String toString() {
    // this.ereignisNummer + " " + this.ereignisDesc + "## " + this.ereignisDatum;

    String str = " " + this.artikelName + this.artikelBestand;
    return super.toString().replace("##", str);
  }

  @Override
  public String toStringDetailed() {
    // "Nr: " + this.ereignisNummer +
    // "\nDate: " + this.ereignisDatum +
    // "\nDesc: '" + this.ereignisDesc +
    // "\nSystem: " + simpleName + "##";

    String str = "\n\nArtikel: '" +
        "\nName: '" + this.artikelName + "'" +
        "\nNummer: " + this.artikelNummer +
        "\nPreis: " + this.artikelPreis +
        "\nBestand: " + this.artikelBestand;
    return super.toStringDetailed().replace("##", str);
  }
}