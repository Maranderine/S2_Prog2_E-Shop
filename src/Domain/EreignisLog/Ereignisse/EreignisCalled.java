package Domain.EreignisLog.Ereignisse;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;
import Domain.EreignisLog.Interfaces.EreignisInterface_CallingBenutzer;

/** abstrakte grund Ereignis Klasse für Benutzer erstellte ereignisse */
public abstract class EreignisCalled extends Ereignis implements EreignisInterface_CallingBenutzer {

  /** calling user object for specific inspection */
  private Benutzer callingBenutzer;
  /*
   * user number and type to find the user.
   * // if the data is loaded from disk the user object might not be identifiable
   * // thoug the user reference
   */
  /** user identification numeber */
  private final int callingBenutzerNummer;
  /** user type */
  private final Benutzerverwaltung.BeutzerType callingBenutzerType;
  /** calling user object for specific inspection */
  private String callingBenutzerName;

  /**
   * abstrakte grund Ereignis Klasse für Benutzer erstellte ereignisse
   * 
   * @param ereignisNummer event identifikator
   * @param ereignisDesc   event erklärungs text
   * @param CUser          calling user
   * @param CUserNumber    calling user nummer
   * @param CUserType      calling user typ
   * @param CUserName
   */
  public EreignisCalled(final int ereignisNummer, String ereignisDesc, Benutzer CUser, int CUserNumber,
      Benutzerverwaltung.BeutzerType CUserType, String CUserName) {
    super(ereignisNummer, ereignisDesc);

    // user
    this.callingBenutzer = CUser;
    this.callingBenutzerNummer = CUserNumber;
    this.callingBenutzerType = CUserType;
    this.callingBenutzerName = CUserName;

    // search terms
    String[] searchTerms = { "Called", Integer.toString(this.callingBenutzerNummer),
        this.callingBenutzerType.toString(),
        this.callingBenutzerName };
    SearchTermAdd(searchTerms);
  }

  // #region implementierungen
  @Override
  public Benutzer getCallingBenutzer() {
    return this.callingBenutzer;
  }

  @Override
  public int getCallingBenutzerNummer() {
    return this.callingBenutzerNummer;
  }

  @Override
  public BeutzerType getCallingBenutzerType() {
    return this.callingBenutzerType;
  }

  @Override
  public String getCallingBenutzerName() {
    return this.callingBenutzerName;
  }

  // #endregion

  @Override
  public String toString() {
    // return this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "\t##\t" +
    // this.ereignisDatum;
    String str = this.callingBenutzerName + "\t##";
    return super.toString().replace("##", str);
  }

  @Override
  public String toStringDetailled() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t##\t" +
    // this.ereignisDatum;
    String str = this.callingBenutzerName + "\t" + this.callingBenutzerNummer + "\t" + this.callingBenutzerType + "\t"
        + "\t##";
    return super.toStringDetailled().replace("##", str);
  }

}