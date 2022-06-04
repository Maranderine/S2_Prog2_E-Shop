package Domain.EreignisLog.Ereignisse.System;

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
   * @param ereignisNummer event identifikator
   * @param ereignisDesc   event erklärungs text
   * @param artikel        Artikel Object reference
   * @param artikelNummer  Artikel Identifikations Nummer
   * @param artikelName    Artikel Name als string
   * @param artikelBestand Artikel Bestabnd nummer
   * @param artikelPreis   Artikel Preis nummer
   */
  public EreignisSystemArtikel(int ereignisNummer, String ereignisDesc, Artikel artikel, int artikelNummer,
      String artikelName, int artikelBestand, double artikelPreis) {
    super(ereignisNummer, ereignisDesc);
    this.artikel = artikel;
    this.artikelNummer = artikelNummer;
    this.artikelName = artikelName;
    this.artikelBestand = artikelBestand;
    this.artikelPreis = artikelPreis;

    // search terms
    String[] searchTerms = { "artikel", "ware", "preis", this.artikelName };
    SearchTermAdd(searchTerms);
  }

  // #region implementations
  @Override
  public Artikel getZielArtikel() {
    // TODO Auto-generated method stub
    return this.artikel;
  }

  @Override
  public int getZielArtikelNummer() {
    // TODO Auto-generated method stub
    return this.artikelNummer;
  }

  @Override
  public String getZielArtikelName() {
    // TODO Auto-generated method stub
    return this.artikelName;
  }

  @Override
  public int getZielArtikelBestand() {
    // TODO Auto-generated method stub
    return this.artikelBestand;
  }

  @Override
  public double getZielArtikelPreis() {
    // TODO Auto-generated method stub
    return this.artikelPreis;
  }
  // #endregion

  @Override
  public String toString() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;

    String str = this.artikelName + "\t##\t" + this.artikelBestand;
    return super.toString().replace("##", str);
  }

  @Override
  protected String toStringDetailed() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;

    String str = this.artikelName + "\t" + this.artikelNummer + this.artikelPreis + this.artikelBestand;
    return super.toStringDetailed().replace("##", str);
  }
}