
package Domain.EreignisLog;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelCreate;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikel;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelData;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikelDelete;
import Domain.EreignisLog.Ereignisse.System.EreignisSystemArtikel;
import Domain.EreignisLog.Ereignisse.System.EreignisSystemNotice;
import Domain.Search.SuchOrdnung;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import persistence.FilePersistenceManager;
import persistence.PersistenceManager;

public class EreignisLogVerwaltung extends Verwaltung {

  private int EreignisZaehler;
  private final Eshop meinShop;
  private final Benutzerverwaltung benutzerVW;
  private final ArtikelVerwaltung artikelVW;
  private PersistenceManager persistenceManager = new FilePersistenceManager();
  /** ereignis log liste */
  private Vector<Ereignis> log;
  private String ereignisDox;

  /**
   * EreignisLogVerwaltung
   * 
   * @param eshop Eshop
   */
  public EreignisLogVerwaltung(Eshop eshop, Benutzerverwaltung benutzerVW,
      ArtikelVerwaltung artikelVW) {
    this.ereignisDox = "Ereignisse.txt";
    // get verwaltungen von eshop
    this.meinShop = eshop;
    this.benutzerVW = benutzerVW;
    this.artikelVW = artikelVW;

    // loading events
    try {
      log = load(this.ereignisDox);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      log = new Vector<Ereignis>();
      setZaehler(1);
      System.out.println("Could not load BenutzerVerwaltung");
    }

    wartung();
  }

  /**
   * füge ereignis zum log hinzu
   * 
   * @param ereignis
   */
  private void addToLog(Ereignis ereignis) {
    log.add(ereignis);
  }

  /**
   * lösche ereignis vom log
   */
  private void delFromLog(Ereignis ereignis) {
    log.remove(ereignis);
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
  // #region suchen und finden
  /**
   * findet ein ereignis mit der ereignis nummer
   * 
   * @param ereignisNummer
   * @return
   * @throws ExceptionEreignisNichtGefunden
   */
  public Ereignis findeEreignis(int ereignisNummer) throws ExceptionEreignisNichtGefunden {
    for (Ereignis ereignis : this.log)
      if (ereignis.getEreignisNummer() == ereignisNummer)
        return ereignis;
    throw new ExceptionEreignisNichtGefunden();
  }

  public Vector<Ereignis> getLog() {
    return log;
  }

  /**
   * sucht im Ereignislog ereignisse zu bestimmtem Artikel
   * 
   * @param artikel
   * @return
   */
  public Vector<EreignisArtikel> getArtikelEreignis(Artikel artikel) {
    Vector<EreignisArtikel> vec = new Vector<>();
    for (Ereignis ereignis : this.log) {
      if (ereignis instanceof EreignisArtikel) {
        EreignisArtikel ea = (EreignisArtikel) ereignis;
        if (ea.getZielArtikel().getName().equals(artikel.getName())) {
          vec.add(ea);
        }
      }
    }
    return vec;
  }

  /**
   * iteriert über den Vektor mit Ereignissen und prüft ob ereignisse am selben
   * Datum vorkommen.
   * falls ja wird nur das letzte Ereignis dieses Datums übernommen
   * 
   * @param vec
   * @return
   */
  public Integer[] getBestandHistory(Artikel artikel) {
    Integer[] data = new Integer[30];
    // alle Ereignisse zu jeweiligem Artikel
    Vector<EreignisArtikel> vec = getArtikelEreignis(artikel);
    for (int i = 0; i < 30; i++) {
      if (vec.size() < 1) {
        data[i] = artikel.getBestand();
      } else if (vec.get(0) instanceof EreignisArtikelCreate) {
        data[i] = 0;
      } else {
        EreignisArtikelData erstesEreignis = (EreignisArtikelData) vec.get(0);
        data[i] = erstesEreignis.getArtikelAltBestand();
      }

    }
    Date heute = new Date();
    for (EreignisArtikel ea : vec) {
      // distanz zwischen heute und dem datum berechnen
      Date aDate = ea.getEreignisDatum();
      Calendar cal1 = Calendar.getInstance();
      Calendar cal2 = Calendar.getInstance();
      cal1.setTime(aDate);
      cal2.setTime(heute);
      int diff = cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
      // Integer stelle 30 minus distanz = jeweiliger Bestand
      // für alle Stellen des Array an und nach dem Datum Bestand zu diesem Datum
      // eintragen
      for (int i = 29 - diff; i < 30; i++) {
        data[i] = ea.getZielArtikelBestand();
      }
    }
    return data;
  }

  /**
   * suche mehrere Events in dem Event Log
   * 
   * @param suchBegriffe
   * @return SuchOrdnung
   */
  public SuchOrdnung suchEvent(String suchBegriffe) {
    return SearchCompileOrdnungSorted(log, suchBegriffe);
  }

  // #endregion
  // #region display
  /**
   * displayd den gesamten Ereignis Log
   * 
   * @param detailed booleaan ob die daten in detaillierter form dargestellt
   *                 werden sollen
   * @return ereignis log string
   */
  public String displayLog(boolean detailed) {
    String str = "";
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
    Ereignis ereignis = findeEreignis(ereignisNummer);
    if (detailed)
      return ereignis.toStringDetailed();
    else
      return ereignis.toString();
  }

  // #endregion
  // #region persistenz

  /**
   * läd
   * 
   * @param datei
   * @return Vector<Ereignis>
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private Vector<Ereignis> load(String datei) throws IOException, ClassNotFoundException {
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
  // #region wartung

  private void wartung() {
    System.out.println("////////WARTUNG WIRDD DURCHGEFÜHRT////////");

    // löscht evens vor 30 tagen
    eventsVerwerfenVor(30);
    // checkMissionRef();
  }

  /**
   * löscht alle events die älter als gegebenes datum sind
   * 
   * @param tage x tage ältere events
   */
  private void eventsVerwerfenVor(int tage) {
    eventsVerwerfenVor(Date.from(Instant.now().minus(Duration.ofDays(tage))));
  }

  /**
   * löscht alle events die älter als gegebenes datum sind
   * 
   * @param date
   */

  private void eventsVerwerfenVor(Date date) {

    Ereignis ereignis;

    @SuppressWarnings("unchecked")
    Iterator<Ereignis> iterator = ((Vector<Ereignis>) log.clone()).iterator();

    while (iterator.hasNext()) {
      ereignis = iterator.next();
      if (ereignis.getEreignisDatum().before(date)) {
        delFromLog(ereignis);
        System.out.println(
            "wartung: ereignis ist gelöscht worden" +
                "\nälter als datum: " + date +
                "\nereignis" + ereignis);
      }
    }
  }

  // private void checkRefrences() {

  // for (Ereignis ereignis : log) {
  // if (ereignis instanceof EreignisInterface_CallingBenutzer) {
  // checkRefrences(((EreignisInterface_CallingBenutzer)
  // ereignis).getCallingBenutzer());
  // }
  // if (ereignis instanceof EreignisInterface_ZielArtikel) {
  // checkRefrences(((EreignisInterface_ZielArtikel) ereignis).getZielArtikel());
  // }
  // }
  // }

  // private Benutzer checkRefrences(Benutzer benutzer){
  // benutzerVW. (lager, index)
  // }

  // private Artikel checkRefrences(Artikel artikel){

  // if (!artikelVW.artikelExists(artikel))
  // artikelVW.getArtikel(lager, index)
  // }

  // #endregion
  // #region zähler
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

  // #endregion

  @Override
  public String toString() {
    return displayLog(false);
  }
}
