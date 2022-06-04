package Domain.EreignisLog.Ereignisse.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.EreignisCalled;
import Domain.EreignisLog.Interfaces.EreignisInterface_ZielArtikel;

/** Basis ereignis für artikel ereignisse */
abstract class EreignisArtikel extends EreignisCalled implements EreignisInterface_ZielArtikel {
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
   * Basis ereignis für artikel ereignisse
   * 
   * @param ereignisNummer event identifikator
   * @param ereignisDesc   event erklärungs text
   * @param CUser          calling user object
   * @param CUserNumber    calling user nummer
   * @param CUserType      calling user typ
   * @param CUserName      calling user Name
   * @param artikel        Artikel objekt
   * @param artikelNummer  Artikel identifikator
   * @param artikelName    Artikel name
   * @param artikelBestand Artikel aktueller bestand
   * @param artikelPreis   Artikel preis
   */
  protected EreignisArtikel(int ereignisNummer, String ereignisDesc, Benutzer CUser, int CUserNumber,
      Benutzerverwaltung.BeutzerType CUserType, String CUserName, Artikel artikel, int artikelNummer,
      String artikelName, int artikelBestand, double artikelPreis) {

    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName);
    this.artikel = artikel;
    this.artikelNummer = artikelNummer;
    this.artikelName = artikelName;
    this.artikelBestand = artikelBestand;
    this.artikelPreis = artikelPreis;

    // search terms
    String[] searchTerms = { "artikel", "ware", "preis", this.artikelName };
    SearchTermAdd(searchTerms);
  }

  // #region implementierungen
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
    // return this.ereignisNummer + "\t" + this.ereignisDesc + "\t" +
    // this.callingBenutzerName
    // + "\t" + "##" + "\t" + this.ereignisDatum;
    String str = this.artikelName + "\t##\t" + this.artikelBestand;
    return super.toString().replace("##", str);
  }

  @Override
  protected String toStringDetailed() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" +
    // this.callingBenutzerName
    // +
    // "\t"
    // + this.callingBenutzerNummer + "\t" + this.callingBenutzerType + "\t" + "##"
    // +
    // "\t" +
    // this.ereignisDatum;

    String str = this.artikelName + "\t" + this.artikelNummer + this.artikelPreis + "\t##\t" + this.artikelBestand;
    return super.toStringDetailed().replace("##", str);
  }
}