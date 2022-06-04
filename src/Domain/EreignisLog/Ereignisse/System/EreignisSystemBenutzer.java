package Domain.EreignisLog.Ereignisse.System;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;
import Domain.EreignisLog.Interfaces.EreignisInterface_ZielBenutzer;

/** Ereignis Klasse für System erstellte Ereignisse in bezug auf Benutzer */
public class EreignisSystemBenutzer extends EreignisSystem implements EreignisInterface_ZielBenutzer {

  /** Ziel Benutzer Objekt */
  private Benutzer zielBenutzer;
  /** Ziel Benutzer Identifikations Nummer */
  private int zielBenutzerNummer;
  /** Ziel Benutzer typ als enum eintrag */
  private Benutzerverwaltung.BeutzerType zielBenutzerType;
  /** Ziel Benutzer name als string */
  private String zielBenutzerName;

  /**
   * Ereignis Klasse für System erstellte Ereignisse in bezug auf Benutzer
   * 
   * @param ereignisNummer     event identifikator
   * @param ereignisDesc       event erklärungs text
   * @param zielBenutzer       Benutzer Objekt des ziel Benutzers welcher
   *                           bearbeitet wurde
   * @param zielBenutzerNummer Ziel benutzer identifikator
   * @param zielBenutzerType   ziel benutzer typ
   * @param zielBenutzerName   ziel benutzer name
   */
  public EreignisSystemBenutzer(int ereignisNummer, String ereignisDesc, Benutzer zielBenutzer, int zielBenutzerNummer,
      BeutzerType zielBenutzerType, String zielBenutzerName) {
    super(ereignisNummer, ereignisDesc);
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
    // TODO Auto-generated method stub
    return this.zielBenutzer;
  }

  @Override
  public int getZielBenutzerNummer() {
    // TODO Auto-generated method stub
    return this.zielBenutzerNummer;
  }

  @Override
  public BeutzerType getZielBenutzerType() {
    // TODO Auto-generated method stub
    return this.zielBenutzerType;
  }

  @Override
  public String getZielBenutzerName() {
    // TODO Auto-generated method stub
    return this.zielBenutzerName;
  }
  // #endregion

  @Override
  public String toString() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    String str = this.zielBenutzerName + "\t##";
    return super.toString().replace("##", str);
  }

  @Override
  protected String toStringDetailed() {
    
// this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    String str = this.zielBenutzerName + "\t" + this.zielBenutzerNummer + "\t" + this.zielBenutzerType + "\t##";
    return super.toStringDetailed().replace("##", str);
  }

}