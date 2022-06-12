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
    simpleName = verwaltung.getClass().getSimpleName();
    // search terms
    /*
     * verwalt-ung/-en
     */
    String[] searchTerms = { "system", "sys", "verwalt", simpleName.toLowerCase() };
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
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    String str = "";
    return super.toString().replace("##", str);
  }

  @Override
  public String toStringDetailled() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    String str = simpleName;
    return super.toStringDetailled().replace("##", str);
  }
}