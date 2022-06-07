
package Domain.EreignisLog;

import java.util.Vector;

import Domain.Eshop;
import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelCreate;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelData;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelDelete;
import Domain.EreignisLog.Ereignisse.System.EreignisSystemArtikel;
import Domain.Search.Searcher;

public class EreignisLogVerwaltung extends Searcher {

  private int EreignisZaehler;
  private final Eshop meinShop;
  private final Benutzerverwaltung benutzerVW;
  private final ArtikelVerwaltung artikelVW;

  private Vector<Ereignis> log = new Vector<Ereignis>();

  /**
   * EreignisLogVerwaltung
   * 
   * @param eshop Eshop
   */
  public EreignisLogVerwaltung(Eshop eshop, Benutzerverwaltung benutzerVW, ArtikelVerwaltung artikelVW) {
    // get verwaltungen von eshop
    this.meinShop = eshop;
    this.benutzerVW = benutzerVW;
    this.artikelVW = artikelVW;

    // TODO get ereignis Zähler: load oder 1
    this.EreignisZaehler = 1;
  }

  // #region neues ereignis//////////////////////////////////////////////////////

  // Artikel
  /**
   * Erstellt neues EreignisArtikelCreate Ereignis, fügt es allen listen hinzu und
   * gibt es zurück.
   * 
   * @param userHash
   * @param ereignisDesc
   * @param artikel
   * @return
   */
  public Ereignis Ereignis_EreignisArtikelCreate(byte[] userHash, String ereignisDesc, Artikel artikel) {
    Benutzer user = this.benutzerVW.getAktiverBenutzer(userHash);
    if (user != null) {

      // neues ereignis
      Ereignis ereignis = new EreignisArtikelCreate(
          // ereignis
          useZaehler(),
          ereignisDesc,
          // user
          user,
          this.benutzerVW.getDataNummer(user),
          this.benutzerVW.getDataTyp(user),
          this.benutzerVW.getDataName(user),
          // artikel
          artikel,
          this.artikelVW.getArtikelNr(artikel),
          this.artikelVW.getArtikelName(artikel),
          this.artikelVW.getArtikelBestand(artikel),
          this.artikelVW.getArtikelPreis(artikel));

      return ereignis;
    }
    // return true;
    return null;
  }

  /**
   * Erstellt neues EreignisArtikelDelete Ereignis, fügt es allen listen hinzu und
   * gibt es zurück.
   * 
   * @param userHash
   * @param ereignisDesc
   * @param artikel
   * @return
   */
  public Ereignis Ereignis_EreignisArtikelDelete(byte[] userHash, String ereignisDesc, Artikel artikel) {
    Benutzer user = this.benutzerVW.getAktiverBenutzer(userHash);
    if (user != null) {

      // neues ereignis
      Ereignis ereignis = new EreignisArtikelDelete(
          // ereignis
          useZaehler(),
          ereignisDesc,
          // user
          user,
          this.benutzerVW.getDataNummer(user),
          this.benutzerVW.getDataTyp(user),
          this.benutzerVW.getDataName(user),
          // artikel
          artikel,
          this.artikelVW.getArtikelNr(artikel),
          this.artikelVW.getArtikelName(artikel),
          this.artikelVW.getArtikelBestand(artikel),
          this.artikelVW.getArtikelPreis(artikel));

      return ereignis;
    }
    // return true;
    return null;
  }

  /**
   * Erstellt neues EreignisArtikelData Ereignis, fügt es allen listen hinzu und
   * gibt es zurück.
   * 
   * @param userHash
   * @param ereignisDesc
   * @param artikel
   * @return
   */
  public Ereignis Ereignis_EreignisArtikelData(byte[] userHash, String ereignisDesc, Artikel artikel, String AaltName,
      int AaltBestand, double AaltPreis) {
    Benutzer user = this.benutzerVW.getAktiverBenutzer(userHash);
    if (user != null) {

      // neues ereignis
      Ereignis ereignis = new EreignisArtikelData(
          // ereignis
          useZaehler(),
          ereignisDesc,
          // user
          user,
          this.benutzerVW.getDataNummer(user),
          this.benutzerVW.getDataTyp(user),
          this.benutzerVW.getDataName(user),
          // artikel
          artikel,
          this.artikelVW.getArtikelNr(artikel),
          this.artikelVW.getArtikelName(artikel),
          this.artikelVW.getArtikelBestand(artikel),
          this.artikelVW.getArtikelPreis(artikel),
          // alt
          AaltName,
          AaltBestand,
          AaltPreis);

      return ereignis;
    }
    // return true;
    return null;
  }

  // system

  /**
   * Erstellt neues EreignisSystemArtikel Ereignis, fügt es allen listen hinzu und
   * gibt es zurück.
   * 
   * @param userHash
   * @param ereignisDesc
   * @param artikel
   * @return
   */
  public Ereignis Ereignis_EreignisSystemArtikel(String ereignisDesc, Artikel artikel) {

    // neues ereignis
    Ereignis ereignis = new EreignisSystemArtikel(
        // ereignis
        useZaehler(),
        ereignisDesc,
        // artikel
        artikel,
        this.artikelVW.getArtikelNr(artikel),
        this.artikelVW.getArtikelName(artikel),
        this.artikelVW.getArtikelBestand(artikel),
        this.artikelVW.getArtikelPreis(artikel));

    return ereignis;
  }

  // #endregion///////////////////////////////////////////////////////////

  /**
   * displayd den gesamten Ereignis Log
   * 
   * @return ereignis log string
   */
  public String displayLog() {
    return toString();
  }

  /**
   * return and increment Ereigniszaehler
   * 
   * @return
   */
  protected int useZaehler() {
    // returns original value and increases by one
    return this.EreignisZaehler++;
  }

  @Override
  public String toString() {
    String str = "EREIGNIS LOG:\n";
    if (this.log.isEmpty()) {
      // log empty
      str += "\tKeine Ereignisse\n";
    } else {
      // log not empty
      for (Ereignis ereignis : this.log) {
        str += ereignis.toString() + "\n";
      }
    }

    return str;
  }
}
