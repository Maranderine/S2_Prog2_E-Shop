package Domain.EreignisLog.Ereignisse.System;

import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.EreignisLog.Interfaces.EreignisInterface_System;

/** abstrakte grund Ereignis Klasse für System erstellte ereignisse */
public abstract class EreignisSystem extends Ereignis implements EreignisInterface_System {
  /**
   * abstrakte grund Ereignis Klasse für System erstellte ereignisse
   * 
   * @param ereignisNummer event identifikator
   * @param ereignisDesc   event erklärungs text
   */
  public EreignisSystem(int ereignisNummer, String ereignisDesc) {
    super(ereignisNummer, ereignisDesc);

    // search terms
    String[] searchTerms = { "system", "sys" };
    SearchTermAdd(searchTerms);
  }

  @Override
  public String toString() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    String str = "";
    return super.toString().replace("##", str);
  }

  @Override
  public String toStringDetailed() {
    // this.ereignisNummer + "\t" + this.ereignisDesc + "\t" + "##" + "\t" +
    // this.ereignisDatum;
    String str = "";
    return super.toStringDetailed().replace("##", str);
  }

}