package Domain.EreignisLog.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;

public class EreignisArtikelDelete extends EreignisArtikel {

  /** lösch-en/ge-lösch-t, entfern-t/entfern-en, */

  /**
   * Ereignis Artikel wurde aud dem lager entfernt
   * 
   * @param eventNumber    event identifikator
   * @param eventDesc      event erklärungs text
   * @param CUser          calling user object
   * @param CUserNumber    calling user nummer
   * @param CUserType      calling user typ
   * @param CUserName      calling user Name
   * @param artikel        Artikel objekt
   * @param artikelNumber  Artikel identifikator
   * @param artikelName    Artikel name
   * @param artikelBestand Artikel aktueller bestand
   * @param artikelPreis   Artikel preis
   */
  public EreignisArtikelDelete(int eventNumber, String eventDesc, Benutzer CUser, int CUserNumber,
      BeutzerType CUserType, String CUserName, Artikel artikel, int artikelNumber, String artikelName,
      int artikelBestand, double artikelPreis) {
    super(eventNumber, eventDesc, CUser, CUserNumber, CUserType, CUserName, artikel, artikelNumber, artikelName,
        artikelBestand, artikelPreis);

    // search terms
    String[] searchTerms = { "delete", "lösch", "entfern" };
    SearchTermAdd(searchTerms);
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return super.toString();
  }

  @Override
  protected String toStringDetailed() {
    /*
      "\t" + this.eventDesc + "\t" + this.callingUserName + "\t" + this.callingUserNumber + "\t" + this.callingUserType + "\t" + 
      this.artikelName + "\t" + this.artikelNumber + "\t##\t" + this.artikelBestand + "\t" + this.date;
     */
    return super.toStringDetailed();
  }
}
