package Domain.EreignisLog.Ereignisse.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import common.EshopInterface.BenutzerType;
import Domain.EreignisLog.Interfaces.EreignisInterface_ZielArtikelData;

/** Ereignis Klasse für Artikel daten wurden geändert */
public class EreignisArtikelData extends EreignisArtikel implements EreignisInterface_ZielArtikelData {

  private String artikelAltName;

  private int artikelAltBestand;

  private double artikelAltPreis;

  /**
   * Ereignis Klasse für Artikel daten wurden geändert
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
   * @param AaltName       Alter Artikel name
   * @param AaltBestand    Alter Artikel aktueller bestand
   * @param AaltPreis      Alter Artikel preis
   * 
   */
  public EreignisArtikelData(int ereignisNummer, String ereignisDesc, Benutzer CUser, int CUserNumber,
      BenutzerType CUserType, String CUserName, Artikel artikel, int artikelNummer, String artikelName,
      int artikelBestand, double artikelPreis,
      String AaltName, int AaltBestand, double AaltPreis) {
    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName, artikel, artikelNummer, artikelName,
        artikelBestand, artikelPreis);
    this.artikelAltName = AaltName;
    this.artikelAltBestand = AaltBestand;
    this.artikelAltPreis = AaltPreis;

    // new search terms
    String[] searchTerms = { "edit", "changed", "geändert", this.artikelAltName,
        Integer.toString(this.artikelAltBestand),
        Double.toString(this.artikelAltPreis) };
    SearchTermAdd(searchTerms);
  }

  // #region getters
  @Override
  public String getArtikelAltName() {
    return this.artikelAltName;
  }

  @Override
  public int getArtikelAltBestand() {
    return this.artikelAltBestand;
  }

  @Override
  public double getArtikelAltPreis() {

    return this.artikelAltPreis;
  }
  // #endregion

  
  @Override
  public String toString() {
    String str = "";
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
    // "\nType: " + this.callingBenutzerType +
    // "\n\nArtikel: '" +
    // "\nName: '" + this.artikelName + "'" +
    // "\nNummer: " + this.artikelNummer +
    // "\nPreis: " + this.artikelPreis +
    // "\nBestand: " + this.artikelBestand + "##";
    String str = "\n\nOld Data--" +
        "\nName: " + this.artikelAltName +
        "\nPreis: " + this.artikelAltPreis +
        "\nBestand: " + this.artikelAltBestand;
    return super.toStringDetailed().replace("##", str);
  }
}
