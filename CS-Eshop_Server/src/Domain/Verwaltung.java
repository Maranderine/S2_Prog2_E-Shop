package Domain;

import Domain.Artikel.Artikel;
import Domain.EreignisLog.EreignisLogVerwaltung;
import Domain.Search.Searcher;

/**
 * Verwaltungs oberclasse welche generelle sachen für Verwaltungen implementiert
 */
public abstract class Verwaltung extends Searcher {

  public EreignisLogVerwaltung ereignisLogVerwaltung;

  public Verwaltung() {

    // ereignis log muss nicht sich selbst gezeigt bekommen
    if (this.getClass() == EreignisLogVerwaltung.class) {
      ereignisLogVerwaltung = (EreignisLogVerwaltung) this;
    }
  }

  /**
   * loggt neues System Ereignis welches welches keinen bezug auf andere daten
   * hat, returnt true wenn ereignis erstellt wurde
   *
   * @param verwaltung   calling verwaltung
   * @param ereignisDesc ereignis beschreibungs
   */
  public void EreignisSystemArtikel(Verwaltung verwaltung, String ereignisDesc) {
    ereignisLogVerwaltung.Ereignis_EreignisSystemNotice(verwaltung, ereignisDesc);
  }

  /**
   * loggt neues System Ereignis welches einen Artikel betrifft, returnt true wenn
   * ereignis erstellt wurde
   *
   * @param verwaltung   calling verwaltung
   * @param ereignisDesc ereignis beschreibungs
   * @param artikel      artikel zu loggen
   */
  public void EreignisSystemArtikel(Verwaltung verwaltung, String ereignisDesc, Artikel artikel) {
    ereignisLogVerwaltung.Ereignis_EreignisSystemArtikel(verwaltung, ereignisDesc, artikel);
  }

}
