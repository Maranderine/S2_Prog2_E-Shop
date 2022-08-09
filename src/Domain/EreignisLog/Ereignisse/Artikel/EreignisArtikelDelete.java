package Domain.EreignisLog.Ereignisse.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import common.EshopInterface.BenutzerType;

/** Ereignis Klasse für Artikel wurde entfernt */
public class EreignisArtikelDelete extends EreignisArtikel {

  /** lösch-en/ge-lösch-t, entfern-t/entfern-en, */

  /**
   * Ereignis Klasse für Artikel wurde entfernt
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
  public EreignisArtikelDelete(int ereignisNummer, String ereignisDesc, Benutzer CUser, int CUserNumber,
      BenutzerType CUserType, String CUserName, Artikel artikel, int artikelNummer, String artikelName,
      int artikelBestand, double artikelPreis) {
    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName, artikel, artikelNummer, artikelName,
        artikelBestand, artikelPreis);

    // search terms
    String[] searchTerms = { "deleted", "gelöscht", "entfernt" };
    SearchTermAdd(searchTerms);
  }

  @Override
  public String toString() {
    String str = "";
    return super.toString().replace("##", str);
  }

  @Override
  public String toStringDetailed() {
    // "Nr: " + this.ereignisNummer +
    // "\nDesc: '" + this.ereignisDesc + "'" +
    // "\nUser: " + this.callingBenutzerName +
    // "\nUserNr: " + this.callingBenutzerNummer +
    // "\nUserType: " + this.callingBenutzerType
    // "\nArtikel: '" + this.artikelName + "'" +
    // "\nNummer: " + this.artikelNummer +
    // "\nPreis: " + this.artikelPreis +
    // "\nBestand: " + this.artikelBestand + "##" +
    // "\nDate: " + this.ereignisDatum
    String str = "";
    return super.toStringDetailed().replace("##", str);
  }
}
