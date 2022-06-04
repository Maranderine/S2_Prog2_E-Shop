package Domain.EreignisLog.Ereignisse.Benutzer;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;
import Domain.EreignisLog.EreignisCalled;
import Domain.EreignisLog.Interfaces.EreignisInterface_ZielBenutzer;

/**
 * EreignisUser
 */
public abstract class EreignisBenutzer extends EreignisCalled implements EreignisInterface_ZielBenutzer {

  /** Ziel Benutzer Objekt */
  private Benutzer zielBenutzer;
  /** Ziel Benutzer Identifikations Nummer */
  private int zielBenutzerNummer;
  /** Ziel Benutzer typ als enum eintrag */
  private Benutzerverwaltung.BeutzerType zielBenutzerType;
  /** Ziel Benutzer name als string */
  private String zielBenutzerName;

  /**
   * Basis ereignis für benutzer ereignisse
   * 
   * @param ereignisNummer     event identifikator
   * @param ereignisDesc       event erklärungs text
   * @param CUser              calling user
   * @param CUserNumber        calling user nummer
   * @param CUserType          calling user typ
   * @param CUserName          calling user Name
   * @param zielBenutzer       Benutzer Objekt des ziel Benutzers welcher
   *                           bearbeitet wurde
   * @param zielBenutzerNummer Ziel benutzer identifikator
   * @param zielBenutzerType   ziel benutzer typ
   * @param zielBenutzerName   ziel benutzer name
   */
  public EreignisBenutzer(int ereignisNummer, String ereignisDesc, Benutzer CUser, int CUserNumber,
      BeutzerType CUserType,
      String CUserName, Benutzer zielBenutzer, int zielBenutzerNummer, BeutzerType zielBenutzerType,
      String zielBenutzerName) {
    super(ereignisNummer, ereignisDesc, CUser, CUserNumber, CUserType, CUserName);
    this.zielBenutzer = zielBenutzer;
    this.zielBenutzerNummer = zielBenutzerNummer;
    this.zielBenutzerType = zielBenutzerType;
    this.zielBenutzerName = zielBenutzerName;

    // search terms
    String[] searchTerms = { "nutzer", "user", Integer.toString(this.zielBenutzerNummer),
        this.zielBenutzerType.toString(),
        this.zielBenutzerName };
    SearchTermAdd(searchTerms);
  }

  // #region implementierungen
  @Override
  public Benutzer getZielBenutzer() {
    return this.zielBenutzer;
  }

  @Override
  public int getZielBenutzerNummer() {
    return this.zielBenutzerNummer;
  }

  @Override
  public BeutzerType getZielBenutzerType() {
    return this.zielBenutzerType;
  }

  @Override
  public String getZielBenutzerName() {
    return this.zielBenutzerName;
  }
  // #endregion

  @Override
  public String toString() {
    // return this.ereignisNummer + "\t" + this.ereignisDesc + "\t" +
    // this.callingBenutzerName
    // + "\t" + "##" + "\t" + this.ereignisDatum;
    String str = this.zielBenutzerName + "\t##";
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

    String str = this.zielBenutzerName + "\t" + this.zielBenutzerNummer + "\t" + this.zielBenutzerType + "\t##";
    return super.toStringDetailed().replace("##", str);
  }
}