package Domain.EreignisLog.Benutzer;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;
import Domain.EreignisLog.Ereignis;

/**
 * EreignisUser
 */
public abstract class EreignisBenutzer extends Ereignis {

  private Benutzer targetUser;

  private int targetUserNumber;

  private Benutzerverwaltung.BeutzerType targetUserType;

  private String targetUserName;

  /**
   * Basis ereignis für benutzer ereignisse
   * 
   * @param eventNumber      event identifikator
   * @param eventDesc        event erklärungs text
   * @param CUser            calling user
   * @param CUserNumber      calling user nummer
   * @param CUserType        calling user typ
   * @param CUserName        calling user Name
   * @param targetUser       Benutzer Objekt des ziel Benutzers welcher bearbeitet
   *                         wurde
   * @param targetUserNumber Ziel benutzer identifikator
   * @param targetUserType   ziel benutzer typ
   */
  public EreignisBenutzer(int eventNumber, String eventDesc, Benutzer CUser, int CUserNumber, BeutzerType CUserType,
      String CUserName, Benutzer targetUser, int targetUserNumber, BeutzerType targetUserType, String targetUserName) {
    super(eventNumber, eventDesc, CUser, CUserNumber, CUserType, CUserName);
    this.targetUser = targetUser;
    this.targetUserNumber = targetUserNumber;
    this.targetUserType = targetUserType;
    this.targetUserName = targetUserName;

    // search terms
    String[] searchTerms = { "Benutzer", Integer.toString(this.targetUserNumber), this.targetUserType.toString(),
        this.targetUserName };
    SearchTermAdd(searchTerms);
  }

  // #region getter
  public Benutzer getTargetUser() {
    return targetUser;
  }

  public int getTargetUserNumber() {
    return targetUserNumber;
  }

  public Benutzerverwaltung.BeutzerType getTargetUserType() {
    return targetUserType;
  }

  public String getTargetUserName() {
    return targetUserName;
  }
  // #endregion

  /*
   * 
   * this.targetUserNumber
   * this.targetUserType
   * this.targetUserName
   */
  @Override
  public String toString() {
    // return this.eventNumber + "\t" + this.eventDesc + "\t" + this.callingUserName
    // + "\t" + "##" + "\t" + this.date;
    String str = this.targetUserName + "\t##";
    return toString().replace("##", str);
  }

  @Override
  protected String toStringDetailed() {
    // this.eventNumber + "\t" + this.eventDesc + "\t" + this.callingUserName + "\t"
    // + this.callingUserNumber + "\t" + this.callingUserType + "\t" + "##" + "\t" +
    // this.date;

    String str = this.targetUserName + "\t" + this.targetUserNumber + "\t" + this.targetUserType + "\t##";
    return toString().replace("##", str);
  }
}