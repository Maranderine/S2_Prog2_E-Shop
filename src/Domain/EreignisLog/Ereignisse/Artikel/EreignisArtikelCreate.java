package Domain.EreignisLog.Ereignisse.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;

/** Ereignis Klasse für Artikel wurde erstellt event */
public class EreignisArtikelCreate extends EreignisArtikel {
  /**
   * Ereignis Klasse für Artikel wurde erstellt event
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
  public EreignisArtikelCreate(int ereignisNummer, String ereignisDesc, Benutzer CUser, int CUserNumber,
      BeutzerType CUserType, String CUserName, Artikel artikel, int artikelNummer, String artikelName,
      int artikelBestand, double artikelPreis) {
    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName, artikel, artikelNummer, artikelName,
        artikelBestand, artikelPreis);

    // new search terms
    /*
     * new
     * add-ed
     * erstell-t
     * erstell-en
     */
    String[] searchTerms = { "new", "add", "erstell", "neu" };
    SearchTermAdd(searchTerms);
  }

  // #region getters

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