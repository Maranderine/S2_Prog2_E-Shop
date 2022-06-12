package Domain.EreignisLog.Ereignisse.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;
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
      BeutzerType CUserType, String CUserName, Artikel artikel, int artikelNummer, String artikelName,
      int artikelBestand, double artikelPreis,
      String AaltName, int AaltBestand, double AaltPreis) {
    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName, artikel, artikelNummer, artikelName,
        artikelBestand, artikelPreis);
    this.artikelAltName = AaltName;
    this.artikelAltBestand = AaltBestand;
    this.artikelAltPreis = AaltPreis;

    // new search terms
    /*
     * editier-t
     * editier-en
     * edit
     * 
     * change
     * chang-ing
     * chang-ed
     * 
     * ver-änder-t
     * änder-n
     */
    String[] searchTerms = { "edit", "chang", "änder" };
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
  public String toStringDetailled() {
    String str = "";
    return super.toStringDetailled().replace("##", str);
  }
}
