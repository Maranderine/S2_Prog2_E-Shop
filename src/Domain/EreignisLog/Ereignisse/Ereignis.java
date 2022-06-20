package Domain.EreignisLog.Ereignisse;

import java.io.Serializable;
import java.util.Date;

import Domain.EreignisLog.Interfaces.EreignisInterface_Ereignis;
import Domain.Search.Searchable;

/** abstrakte grund Ereignis Klasse */
public abstract class Ereignis extends Searchable implements EreignisInterface_Ereignis, Serializable {
  // TODO gespeicherte Artikel sinf vielleicht ghost clones?????????

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
    String[] searchTerms = { "event", Integer.toString(this.ereignisNummer), this.ereignisDesc,
        this.ereignisDatum.toString() };
    SearchTermAdd(searchTerms);
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
    return this.ereignisDatum + "|" + this.ereignisNummer + "| '" + this.ereignisDesc + "'##";
  }

  @Override
  public String toStringDetailed() {
    return "Event--" +
        "\nNr: " + this.ereignisNummer +
        "\nDate: " + this.ereignisDatum +
        "\nDesc: '" + this.ereignisDesc + "'##";
  }
}