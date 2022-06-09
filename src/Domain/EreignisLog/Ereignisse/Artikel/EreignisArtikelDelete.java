package Domain.EreignisLog.Ereignisse.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;

/** Ereignis Klasse für Artikel wurde entfernt */
public class EreignisArtikelDelete extends EreignisArtikel {

  /** lösch-en/ge-lösch-t, entfern-t/entfern-en, */

  /**
   * Ereignis Klasse für Artikel wurde entfernt
   * 
   * @param ereignisNummer    event identifikator
   * @param ereignisDesc      event erklärungs text
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
      BeutzerType CUserType, String CUserName, Artikel artikel, int artikelNummer, String artikelName,
      int artikelBestand, double artikelPreis) {
    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName, artikel, artikelNummer, artikelName,
        artikelBestand, artikelPreis);

    // search terms
    String[] searchTerms = { "delete", "lösch", "entfern" };
    SearchTermAdd(searchTerms);
  }

  @Override
  public String toString() {
    String str = "";
    return super.toString().replace("##", str);
  }
  
  @Override
  public String toStringDetailed() {
    /*
     * "\t" + this.ereignisDesc + "\t" + this.callingBenutzerName + "\t" +
     * this.callingBenutzerNummer + "\t" + this.callingBenutzerType + "\t" +
     * this.artikelName + "\t" + this.artikelNummer + "\t##\t" + this.artikelBestand
     * + "\t" + this.ereignisDatum;
     */
    String str = "";
    return super.toStringDetailed().replace("##", str);
  }
}
