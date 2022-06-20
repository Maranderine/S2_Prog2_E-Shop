package Domain.EreignisLog.Ereignisse.System;

import Domain.Verwaltung;

/*
 * simple Ereignis benaachichtigung oihne besondere referenzen
 */
public class EreignisSystemNotice extends EreignisSystem {

  /**
   * erstellt eine simple Ereignis benachichtigung ohne besondere referenzen
   * 
   * @param verwaltung
   * @param ereignisNummer
   * @param ereignisDesc
   */
  public EreignisSystemNotice(Verwaltung verwaltung, int ereignisNummer, String ereignisDesc) {
    super(verwaltung, ereignisNummer, ereignisDesc);

    // search terms
    /*
     * verwalt-ung/-en
     */
    String[] searchTerms = { "notice", "meldung" };
    SearchTermAdd(searchTerms);
  }

  @Override
  public String toString() {
    // this.ereignisNummer + " " + this.ereignisDesc + "## " + this.ereignisDatum;
    return super.toString().replace("##", "");
  }

  @Override
  public String toStringDetailed() {
    // "Nr: " + this.ereignisNummer +
    // "\nDate: " + this.ereignisDatum +
    // "\nDesc: '" + this.ereignisDesc +
    // "\nSystem: " + simpleName + "##";
    return super.toStringDetailed().replace("##", "");
  }
}
