package Domain.EreignisLog.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.Ereignis;

/**
 * EreignisArtikel
 */
abstract class EreignisArtikel extends Ereignis {

  /** artikel object */
  private Artikel artikel;
  /** artikel idenfikator */
  private int artikelNumber;
  /** artikel name */
  private String artikelName;
  /** artikel aktueller bestand */
  private int artikelBestand;
  /** artikel aktueller preis */
  private double artikelPreis;

  /**
   * Basis ereignis für artikel ereignisse
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
  protected EreignisArtikel(int eventNumber, String eventDesc, Benutzer CUser, int CUserNumber,
      Benutzerverwaltung.BeutzerType CUserType, String CUserName, Artikel artikel, int artikelNumber,
      String artikelName,
      int artikelBestand,
      double artikelPreis) {

    super(eventNumber, eventDesc, CUser, CUserNumber, CUserType, CUserName);
    this.artikel = artikel;
    this.artikelNumber = artikelNumber;
    this.artikelName = artikelName;
    this.artikelBestand = artikelBestand;
    this.artikelPreis = artikelPreis;

    // search terms
    String[] searchTerms = { "artikel", "ware", "preis", this.artikelName };
    SearchTermAdd(searchTerms);
  }

  // #region getter
  public Artikel getArtikel() {
    return artikel;
  }

  public int getArtikelNumber() {
    return artikelNumber;
  }

  public String getArtikelName() {
    return artikelName;
  }

  public int getArtikelBestand() {
    return artikelBestand;
  }

  public double getArtikelPreis() {
    return artikelPreis;
  }

  // #endregion

  @Override
  public String toString() {
    // return this.eventNumber + "\t" + this.eventDesc + "\t" + this.callingUserName
    // + "\t" + "##" + "\t" + this.date;
    String str = this.artikelName + "\t##\t" + this.artikelBestand;
    return toString().replace("##", str);
  }

  @Override
  protected String toStringDetailed() {
    //this.eventNumber + "\t" + this.eventDesc + "\t" + this.callingUserName + "\t" + this.callingUserNumber + "\t" + this.callingUserType + "\t" + "##" + "\t" + this.date;

    String str = this.artikelName + "\t" + this.artikelNumber + "\t##\t" + this.artikelBestand;
    return super.toStringDetailed().replace("##", str);
  }
}