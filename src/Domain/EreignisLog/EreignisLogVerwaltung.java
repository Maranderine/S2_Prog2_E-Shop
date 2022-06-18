
package Domain.EreignisLog;

import java.io.IOException;
import java.util.Vector;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelCreate;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelData;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelDelete;
import Domain.EreignisLog.Ereignisse.System.EreignisSystemArtikel;
import Domain.EreignisLog.Ereignisse.System.EreignisSystemNotice;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import persistence.FilePersistenceManager;
import persistence.PersistenceManager;

public class EreignisLogVerwaltung extends Verwaltung {

  // TODO: EVENTS - suche nach artikel objekt

  private int EreignisZaehler;
  private final Eshop meinShop;
  private final Benutzerverwaltung benutzerVW;
  private final ArtikelVerwaltung artikelVW;
  private PersistenceManager persistenceManager = new FilePersistenceManager();
  private Vector<Ereignis> log;
  private String ereignisDox;

  /**
   * EreignisLogVerwaltung
   * 
   * @param eshop Eshop
   */
  public EreignisLogVerwaltung(Eshop eshop, String ereignisDox, Benutzerverwaltung benutzerVW,
      ArtikelVerwaltung artikelVW) {
    this.ereignisDox = ereignisDox;
    // get verwaltungen von eshop
    this.meinShop = eshop;
    this.benutzerVW = benutzerVW;
    this.artikelVW = artikelVW;

    // loading events
    try {
      log = load(this.ereignisDox);
    } catch (IOException e) {
      e.printStackTrace();
      log = new Vector<Ereignis>();
      setZaehler(1);
      System.out.println("Could not load BenutzerVerwaltung");
    }

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

      addToLog(ereignis);
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

      addToLog(ereignis);
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
   * @param AaltName
   * @param AaltBestand
   * @param AaltPreis
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

      addToLog(ereignis);
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
   * @param verwaltung   calling vrwaltung
   * @param ereignisDesc ereignis beschreibung
   * @param artikel
   * @return
   */
  public Ereignis Ereignis_EreignisSystemArtikel(Verwaltung verwaltung, String ereignisDesc, Artikel artikel) {

    // neues ereignis
    Ereignis ereignis = new EreignisSystemArtikel(
        // calling vrwaltung
        verwaltung,
        // ereignis
        useZaehler(),
        ereignisDesc,
        // artikel
        artikel,
        this.artikelVW.getArtikelNr(artikel),
        this.artikelVW.getArtikelName(artikel),
        this.artikelVW.getArtikelBestand(artikel),
        this.artikelVW.getArtikelPreis(artikel));

    addToLog(ereignis);
    return ereignis;
  }

  /**
   * Erstellt neues EreignisSystemNotice Ereignis, fügt es allen listen hinzu und
   * gibt es zurück.
   * 
   * @param verwaltung   calling vrwaltung
   * @param ereignisDesc ereignis beschreibung
   * @return
   */
  public Ereignis Ereignis_EreignisSystemNotice(Verwaltung verwaltung, String ereignisDesc) {

    // neues ereignis
    Ereignis ereignis = new EreignisSystemNotice(
        // calling vrwaltung
        verwaltung,
        // ereignis
        useZaehler(),
        ereignisDesc);

    addToLog(ereignis);
    return ereignis;
  }

  // #endregion///////////////////////////////////////////////////////////

  private void addToLog(Ereignis ereignis) {
    log.add(ereignis);
  }

  public Ereignis findEreignis(int ereignisNummer) throws ExceptionEreignisNichtGefunden {
    for (Ereignis ereignis : this.log)
      if (ereignis.getEreignisNummer() == ereignisNummer)
        return ereignis;
    throw new ExceptionEreignisNichtGefunden();
  }

  // #region display
  /**
   * displayd den gesamten Ereignis Log
   * 
   * @param detailed booleaan ob die daten in detaillierter form dargestellt
   *                 werden sollen
   * @return ereignis log string
   */
  public String displayLog(boolean detailed) {
    String str = "EREIGNIS LOG:\n";
    if (this.log.isEmpty()) {
      // log empty
      str += "\tKeine Ereignisse\n";
    } else {
      // log not empty
      if (detailed) {
        for (Ereignis ereignis : this.log) {
          str += ereignis.toStringDetailed() + "\n";
        }
      } else {
        for (Ereignis ereignis : this.log) {
          str += ereignis.toString() + "\n";
        }
      }
    }
    return str;
  }

  /**
   * display ein ereignis
   * 
   * @param detailed       booleaan ob die daten in detaillierter form dargestellt
   *                       werden sollen
   * @param ereignisNummer numer des ereignissses
   * @return
   * @throws ExceptionEreignisNichtGefunden
   */
  public String displayEreignis(boolean detailed, int ereignisNummer) throws ExceptionEreignisNichtGefunden {
    Ereignis ereignis = findEreignis(ereignisNummer);
    if (detailed)
      return ereignis.toStringDetailed();
    else
      return ereignis.toString();
  }

  // #endregion

  /**
   * return and increment Ereigniszaehler
   * 
   * @return
   */
  protected int useZaehler() {
    // returns original value and increases by one
    return this.EreignisZaehler++;
  }

  /**
   * set zähler
   * 
   * @param num
   * @return
   */
  private void setZaehler(int num) {
    // returns original value and increases by one
    this.EreignisZaehler = num;
  }

  // #region persistenz

  /**
   * läd
   * 
   * @param datei
   * @return Vector<Ereignis>
   * @throws IOException
   */
  private Vector<Ereignis> load(String datei) throws IOException {
    @SuppressWarnings("unchecked")
    Vector<Ereignis> vec = (Vector<Ereignis>) persistenceManager.loadObjekt(datei);

    // get largest event number and set to current number
    int largestNum = 1;
    int num;
    for (Ereignis ereignis : vec) {
      num = ereignis.getEreignisNummer();
      if (num > largestNum)
        largestNum = num;
    }

    setZaehler(largestNum + 1);
    return vec;
  }

  /**
   * Speichert daten
   *
   * @param datei Datei
   * @throws IOException
   */
  private boolean save(String datei) throws IOException {
    return persistenceManager.saveObjekt(datei, log);
  }

  /**
   * saves
   * 
   * @return
   * @throws IOException
   */
  public boolean save() throws IOException {
    return save(ereignisDox);
  }

  // #endregion

  @Override
  public String toString() {
    return displayLog(false);
  }
}
