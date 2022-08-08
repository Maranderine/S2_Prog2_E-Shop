package Domain.EreignisLog.Ereignisse.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.Ereignisse.EreignisCalled;
import Domain.EreignisLog.Interfaces.EreignisInterface_ZielArtikel;
import common.EshopInterface.BenutzerType;

/** Basis ereignis für artikel ereignisse */
public abstract class EreignisArtikel extends EreignisCalled implements EreignisInterface_ZielArtikel {
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
  public EreignisArtikel(int ereignisNummer, String ereignisDesc, Benutzer CUser, int CUserNumber,
      BenutzerType CUserType, String CUserName, Artikel artikel, int artikelNummer,
      String artikelName, int artikelBestand, double artikelPreis) {

    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName);
    this.artikel = artikel;
    this.artikelNummer = artikelNummer;
    this.artikelName = artikelName;
    this.artikelBestand = artikelBestand;
    this.artikelPreis = artikelPreis;

    // search terms
    String[] searchTerms = { "artikel", "ware", "preis" };
    SearchTermAdd(searchTerms);
    //add artikel name split
    SearchTermAdd(this.artikelName.split(" "));
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
    // return this.ereignisNummer + " " + this.ereignisDesc + " " +
    // this.callingBenutzerName + "## " + this.ereignisDatum;
    String str = " " + this.artikelName + "## " + this.artikelBestand;
    return super.toString().replace("##", str);
  }

  @Override
  public String toStringDetailed() {
    // // "Event--" +
    // // "\nNr: " + this.ereignisNummer +
    // // "\nDate: " + this.ereignisDatum +
    // // "\nDesc: '" + this.ereignisDesc +
    // "\n\nCalling User--" +
    // "\nUser: " + this.callingBenutzerName +
    // "\nNummer: " + this.callingBenutzerNummer +
    // "\nType: " + this.callingBenutzerType + "##";

    String str = "\n\nArtikel: '" +
        "\nName: '" + this.artikelName + "'" +
        "\nNummer: " + this.artikelNummer +
        "\nPreis: " + this.artikelPreis +
        "\nBestand: " + this.artikelBestand + "##";
    return super.toStringDetailed().replace("##", str);
  }
}