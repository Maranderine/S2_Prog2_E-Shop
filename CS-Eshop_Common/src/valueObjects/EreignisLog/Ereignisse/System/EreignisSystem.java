package Domain.EreignisLog.Ereignisse.System;

import Domain.Verwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.EreignisLog.Interfaces.EreignisInterface_System;

/** abstrakte grund Ereignis Klasse für System erstellte ereignisse */
public abstract class EreignisSystem extends Ereignis implements EreignisInterface_System {

  // calling verwaltung
  private Verwaltung verwaltung;
  // name der objekt classe
  private String simpleName;

  /**
   * abstrakte grund Ereignis Klasse für System erstellte ereignisse
   * 
   * @param verwaltung     Verwaltung die das event erstellt
   * @param ereignisNummer event identifikator
   * @param ereignisDesc   event erklärungs text
   */
  public EreignisSystem(Verwaltung verwaltung, int ereignisNummer, String ereignisDesc) {
    super(ereignisNummer, ereignisDesc);
    this.verwaltung = verwaltung;
    this.simpleName = verwaltung.getClass().getSimpleName();
    // search terms
    /*
     * verwalt-ung/-en
     */
    String[] searchTerms = { "system", "verwaltung", this.simpleName };
    SearchTermAdd(searchTerms);
  }

  // #region implementations

  @Override
  public Verwaltung getCallingSystem() {
    return this.verwaltung;
  }

  // #endregion

  @Override
  public String toString() {
    // this.ereignisNummer + " " + this.ereignisDesc + "## " + this.ereignisDatum;
    // String str = "";
    // return super.toString().replace("##", str);
    return super.toString();
  }

  @Override
  public String toStringDetailed() {

    // "Nr: " + this.ereignisNummer +
    // "\nDate: " + this.ereignisDatum;
    // "\nDesc: '" + this.ereignisDesc + "'##";
    String str = "\n\nSystem--" +
        "\nsimplename: " + simpleName + "##";
    return super.toStringDetailed().replace("##", str);
  }
}