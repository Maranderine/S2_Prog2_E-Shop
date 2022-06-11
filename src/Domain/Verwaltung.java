package Domain;

import Domain.Artikel.Artikel;
import Domain.EreignisLog.EreignisLogVerwaltung;

/** Verwaltungs oberclasse */
public abstract class Verwaltung {

  public EreignisLogVerwaltung ereignisLogVerwaltung;

  public Verwaltung() {

  }

  /**
   * loggt neues System Ereignis welches einen Artikel betrifft, returnt true
   * wenn
   * ereignis erstellt wurde
   *
   * @param ereignisDesc
   * @param artikel
   */
  public void EreignisSystemArtikel(String ereignisDesc, Artikel artikel) {
    ereignisLogVerwaltung.Ereignis_EreignisSystemArtikel(ereignisDesc, artikel);
  }

}
