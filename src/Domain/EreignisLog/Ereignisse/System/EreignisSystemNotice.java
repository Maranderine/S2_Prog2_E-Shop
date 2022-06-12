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
    String[] searchTerms = { "notice" };
    SearchTermAdd(searchTerms);
  }

  @Override
  public String toString() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    return super.toString().replace("##", "");
  }

  @Override
  public String toStringDetailed() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    return super.toStringDetailed().replace("##", "");
  }
}
