package Domain.EreignisLog.Ereignisse;

import java.util.Date;

import Domain.EreignisLog.Interfaces.EreignisInterface_Ereignis;
import Domain.Search.Searchable;

/** abstrakte grund Ereignis Klasse */
public abstract class Ereignis extends Searchable implements EreignisInterface_Ereignis {
  /** Event identifikator */
  private final int ereignisNummer;
  /** Event erklärungs text */
  private String ereignisDesc;
  /** Event identifikator */
  private final Date ereignisDatum;

  /**
   * abstrakte grund Ereignis Klasse
   * 
   * @param ereignisNummer event identifikator
   * @param ereignisDesc   event erklärungs text
   */
  public Ereignis(final int ereignisNummer, String ereignisDesc) {
    super();
    this.ereignisNummer = ereignisNummer;
    this.ereignisDesc = ereignisDesc;
    this.ereignisDatum = new Date();

    // search terms
    String[] searchTerms = { "Event", Integer.toString(this.ereignisNummer), this.ereignisDesc,
        this.ereignisDatum.toString() };
    SearchTermAdd(searchTerms);

    System.out.println("EVENT CREATED");
  }

  // #region implementierung

  @Override
  public int getEreignisNummer() {
    return this.ereignisNummer;
  }

  @Override
  public String getEreignisDesc() {
    return this.ereignisDesc;
  }

  @Override
  public Date getEreignisDatum() {
    return this.ereignisDatum;
  }
  // #endregion

  @Override
  public String toString() {
    return this.ereignisNummer + "\t" + this.ereignisDesc + "\t##\t" + this.ereignisDatum;
  }

  /**
   * Detailed toString, giving all data, except objects
   * 
   * @return
   */
  protected String toStringDetailed() {
    return toString();
  }
}