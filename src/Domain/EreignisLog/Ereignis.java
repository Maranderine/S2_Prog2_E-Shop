package Domain.EreignisLog;

import java.util.Date;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.Search.Searchable;

public abstract class Ereignis extends Searchable {
  /** Event identifikator */
  private int eventNumber;
  /** Event erklärungs text */
  private String eventDesc;
  /** Event identifikator */
  private Date date;
  /** calling user object for specific inspection */
  private Benutzer callingUser;
  /*
   * user number and type to find the user.
   * // if the data is loaded from disk the user object might not be identifiable
   * // thoug the user reference
   */
  /** user identification numeber */
  private int callingUserNumber;
  /** user type */
  private Benutzerverwaltung.BeutzerType callingUserType;
  /** calling user object for specific inspection */
  private String callingUserName;

  /**
   * basis ereignis
   * 
   * @param eventNumber event identifikator
   * @param eventDesc   event erklärungs text
   * @param CUser       calling user
   * @param CUserNumber calling user nummer
   * @param CUserType   calling user typ
   * @param CUserName
   */
  public Ereignis(int eventNumber, String eventDesc, Benutzer CUser, int CUserNumber,
      Benutzerverwaltung.BeutzerType CUserType, String CUserName) {
    super();
    this.eventNumber = eventNumber;
    this.eventDesc = eventDesc;
    this.date = new Date();

    // user
    this.callingUser = CUser;
    this.callingUserNumber = CUserNumber;
    this.callingUserType = CUserType;
    this.callingUserName = CUserName;

    // search terms
    String[] searchTerms = { "Event", Integer.toString(this.eventNumber), this.eventDesc,
        Integer.toString(this.callingUserNumber), this.callingUserType.toString(), this.callingUserName,
        this.date.toString() };
    SearchTermAdd(searchTerms);
  }

  // #region getter
  public int getEventNumber() {
    return eventNumber;
  }

  public String getEventDesc() {
    return eventDesc;
  }

  public Date getDate() {
    return date;
  }

  public Benutzer getCallingUser() {
    return callingUser;
  }

  public int getCallingUserNumber() {
    return callingUserNumber;
  }

  public Benutzerverwaltung.BeutzerType getCallingUserType() {
    return callingUserType;
  }

  public String getCallingUserName() {
    return callingUserName;
  }

  // #endregion

  @Override
  public String toString() {
    return this.eventNumber + "\t" + this.eventDesc + "\t" + this.callingUserName + "\t##\t" + this.date;
  }
  /**
   * Detailed toString
   * @return
   */
  protected String toStringDetailed() {
    String str = this.callingUserNumber + "\t" + this.callingUserType + "\t##";
    return toString().replace("##", str);
  }
}