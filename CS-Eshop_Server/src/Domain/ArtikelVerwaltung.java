package Domain.Artikel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Search.SuchOrdnung;
import Domain.Search.SuchOrdnung.OrdnungIndex;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtErstelltWerden;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtGelöschtWerden;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameUngültig;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import Exceptions.Lager.ExceptionLagerNichtGefunden;
import persistence.FilePersistenceManager;
import persistence.PersistenceManager;

public class ArtikelVerwaltung extends Verwaltung {

  private ArrayList<Lager> lagerList = new ArrayList<Lager>();
  private final Eshop eshop;
  private PersistenceManager persistenceManager = new FilePersistenceManager();
  private final String artikelDox = "Artikel_#.txt";

  public ArtikelVerwaltung(Eshop eshop) {
    this.eshop = eshop;

    try {
      autoLoad(artikelDox);
    } catch (IOException e) {
      newLager();
      e.printStackTrace();
      System.out.println("Could not load BenutzerVerwaltung");
    }

  }

  // #region artikel management
  /**
   * erstellt nur den Artikel
   * 
   * @param artikelNummer
   * @param name
   * @param bestand
   * @param einzelpreis
   * @param packungsInhalt
   * @return
   */
  private Artikel createArtikel(int artikelNummer, String name, int bestand, double einzelpreis, int packungsInhalt) {
    Artikel artikel;
    if (packungsInhalt > 1)
      artikel = new Massengutartikel(artikelNummer, name, bestand, einzelpreis, packungsInhalt);
    else
      artikel = new Artikel(artikelNummer, name, bestand, einzelpreis);
    return artikel;
  }

  /**
   * Erstelle artikel mit nummer und füge es dem lager hinzu
   * 
   * @param lager
   * @param artikelNummer
   * @param name
   * @param bestand
   * @param einzelpreis
   * @param packungsInhalt
   * @return
   * @throws ExceptionArtikelExistiertBereits
   */
  private Artikel addArtikel(Lager lager, int artikelNummer, String name, int bestand, double einzelpreis,
      int packungsInhalt) throws ExceptionArtikelExistiertBereits {

    Artikel artikel;

    try {
      // guck ob artikel existiert | wenn nciht kommt iene exception
      artikel = findArtikelByName(name);
      // Artikel existiert und wir erstellen eine neue exception
      throw new ExceptionArtikelExistiertBereits(artikel);
    } catch (ExceptionArtikelNichtGefunden e) {
      // Artikel existiert nicht also machen wir einene neuen
      artikel = createArtikel(artikelNummer, name, bestand, einzelpreis, packungsInhalt);
      addArtikelToLager(artikel, lager);
      return artikel;
    }
  }

  /**
   * Add Artikel to system
   * 
   * @param name
   * @param bestand
   * @param einzelpreis
   * @param packungsInhalt stück zahl per packung, wenn grösser 1 wird der Artikel
   *                       ein massengut
   * @return
   * @throws ExceptionArtikelExistiertBereits
   * @throws ExceptionArtikelKonnteNichtErstelltWerden
   */
  public Artikel addArtikel(String name, int bestand, double einzelpreis, int packungsInhalt)
      throws ExceptionArtikelExistiertBereits, ExceptionArtikelKonnteNichtErstelltWerden {
    // get lager was artikel aufnehmen soll
    Lager lager;
    try {
      lager = getLagerToAdd();
      // add artikel
      return addArtikel(lager, lager.useZaehler(), name, bestand, einzelpreis, packungsInhalt);
    } catch (ExceptionLagerNichtGefunden e) {
      e.printStackTrace();
      throw new ExceptionArtikelKonnteNichtErstelltWerden(e);
    }

  }

  /**
   * Deletes a artikel from the artikelListe
   * 
   * @param artikel obj
   * @return boolean, true if something was deleted, false if not
   * @throws ExceptionArtikelKonnteNichtGelöschtWerden
   */
  public void deleteArtikel(Lager lager, Artikel artikel) throws ExceptionArtikelKonnteNichtGelöschtWerden {
    if (!lager.removeItem(artikel))
      throw new ExceptionArtikelKonnteNichtGelöschtWerden();
  }

  /**
   * Deletes a artikel from the artikelListe
   * 
   * @param artikel obj
   * @return boolean, true if something was deleted, false if not
   * @throws ExceptionArtikelKonnteNichtGelöschtWerden
   * @throws ExceptionArtikelNichtGefunden
   */
  public void deleteArtikel(Artikel artikel) throws ExceptionArtikelKonnteNichtGelöschtWerden {

    try {
      deleteArtikel(searchLager(artikel), artikel);
    } catch (ExceptionLagerNichtGefunden e) {

      e.printStackTrace();
      throw new ExceptionArtikelKonnteNichtGelöschtWerden(e);
    }
  }

  /**
   * Deletes a artikel from the artikelListe by name
   * 
   * @param name of artikel
   * @return boolean, true if something was deleted, false if not
   */
  public boolean deleteArtikel(String name) throws ExceptionArtikelKonnteNichtGelöschtWerden {

    try {
      Artikel artikel = findArtikelByName(name);
      deleteArtikel(artikel);
    } catch (ExceptionArtikelNichtGefunden e) {
      throw new ExceptionArtikelKonnteNichtGelöschtWerden(e);
    }

    return true;// if nothing could be deleted
  }

  // #endregion new artikel
  // #region check artikel
  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object
   * @throws ExceptionArtikelNichtGefunden
   */
  public Artikel findArtikelByName(String name) throws ExceptionArtikelNichtGefunden {
    // go through all Lager
    // iterates through artikelListe

    for (Lager lager : getLagerList())
      for (Artikel artikel : lager.getList()) {
        if (artikel.getName().equals(name))
          return artikel;
      }
    throw new ExceptionArtikelNichtGefunden();
  }

  /**
   * checkt ob der artikel existiert
   * 
   * @param name of artikel
   * @return bool true wenn er existiert
   */
  public boolean artikelExists(String name) {
    try {
      findArtikelByName(name);
      return true;
    } catch (ExceptionArtikelNichtGefunden e) {
      return false;
    }
  }

  /**
   * checkt ob der artikel existiert
   * 
   * @param artikel objekt
   * @return bool true wenn er existiert
   */
  public boolean artikelExists(Artikel artikel) {
    try {
      searchLager(artikel);
      return true;
    } catch (ExceptionLagerNichtGefunden e) {
      return false;
    }
  }

  /**
   * chgeckt ob name gültig ist
   * 
   * @return true wenn alles okay
   */
  public boolean checkName(String string) throws ExceptionArtikelNameUngültig{
    // TODO do regex pattern checking
    // pattern.matcher(input).matches()
    if (string.equals(null))
      throw new ExceptionArtikelNameUngültig();

    /*try {
      Artikel art = findArtikelByName(string);
      throw new ExceptionArtikelNameExistiertBereits(art);
    } catch (ExceptionArtikelNichtGefunden e) {
      return true;
    }*/
    return true;

  }

  // #endregion check artikel
  // #region Lager

  /**
   * erstellt neues Laager OBj und gibt es zurück
   * mit gegebener Artikel Liste
   * 
   * @param artikelList
   * @return Lager
   */
  private Lager createLager(Vector<Artikel> artikelList) {
    return new Lager(artikelList);
  }

  /**
   * erstellt neues Lager OBj und gibt es zurück
   * 
   * @param artikelList
   * @return Lager
   */
  private Lager createLager() {
    return new Lager();
  }

  /**
   * fügt Lager der Lager handlung hinzu
   * 
   * @param lager
   */
  private void addLagerToSystem(Lager lager) {

    this.lagerList.add(lager);
  }

  /**
   * erstellt neues lager und fügt es dem system hinzu
   * mit gegebener Artikel Liste
   */
  private void newLager(Vector<Artikel> artikelList) {
    Lager lager = createLager(artikelList);
    addLagerToSystem(lager);
  }

  /**
   * erstellt neues lager und fügt es dem system hinzu
   * 
   */
  private void newLager() {
    Lager lager = createLager();
    addLagerToSystem(lager);
  }

  /**
   * gibt lager welches den artikel gegeben bekommen soll
   * 
   * @return Lager
   * @throws ExceptionLagerNichtGefunden
   */
  private Lager getLagerToAdd() throws ExceptionLagerNichtGefunden {
    for (Lager lager : getLagerList()) {
      // if (something)
      return lager;
    }
    throw new ExceptionLagerNichtGefunden();
  }

  /**
   * add artikel to lager
   * 
   * @param artikel
   * @param lager
   */
  private void addArtikelToLager(Artikel artikel, Lager lager) {
    lager.addItem(artikel);
  }

  /**
   * 
   * @param name artikel name
   * @return
   * @throws ExceptionLagerNichtGefunden
   */
  private Lager searchLager(String name) throws ExceptionLagerNichtGefunden {
    for (Lager lager : getLagerList())
      for (Artikel artikel : lager.getList()) {
        if (artikel.getName().equals(name))
          return lager;
      }

    throw new ExceptionLagerNichtGefunden();
  }

  /**
   * 
   * @param name artikel name
   * @return
   * @throws ExceptionLagerNichtGefunden
   */
  private Lager searchLager(Artikel artikel) throws ExceptionLagerNichtGefunden {
    for (Lager lager : getLagerList())
      if (lager.getList().contains(artikel))
        return lager;

    throw new ExceptionLagerNichtGefunden();
  }

  /**
   * compiles artikel list aus allen Lagern
   * 
   * @return Vector<Artikel> liste an allen aartikeln
   */
  private Vector<Artikel> compileLagerInhalt() {
    Vector<Artikel> complist = new Vector<Artikel>();

    for (Lager lager : getLagerList()) {
      complist.addAll(lager.getList());
    }

    return complist;
  }

  // #endregion
  // #region getter

  /**
   * gibt Lager
   * 
   * @return Lager
   */
  public ArrayList<Lager> getLagerList() {
    return this.lagerList;
  }

  /**
   * get lager by index
   * 
   * @param index
   * @return
   */
  public Lager getLager(int index) {
    return this.lagerList.get(index);
  }

  /**
   * gets Artikel Object from artikelListe by index
   * 
   * @param index of the Artikel to return
   * @return Artikel type Object
   * @throws ExceptionArtikelNichtGefunden
   */
  public Artikel getArtikel(Lager lager, int index) throws ExceptionArtikelNichtGefunden {
    Vector<Artikel> list = lager.getList();
    if (index < list.size())
      return list.get(index);

    throw new ExceptionArtikelNichtGefunden();
  }

  protected Vector<Artikel> getArtikelList(Lager lager) {
    return lager.getList();
  }

  public int getArtikelNr(Artikel artikel) {
    return artikel.getArtikelNr();
  }

  public String getArtikelName(Artikel artikel) {
    return artikel.getName();
  }

  public int getArtikelBestand(Artikel artikel) {
    return artikel.getBestand();
  }

  public double getArtikelPreis(Artikel artikel) {
    return artikel.getPreis();
  }

  // #endregion
  // #region setter ///////////////////////////////
  /**
   * sets name of Artikel Object
   * 
   * @param artikel object
   * @param name    of article
   * @throws ExceptionArtikelNameExistiertBereits
   * @throws ExceptionArtikelNameUngültig
   */
  public void setArtikelName(Artikel artikel, String newName)
      throws ExceptionArtikelNameUngültig {

    if (checkName(newName))
      // wenn kein artikel gefunden wird
      artikel.setName(newName);

  }

  /**
   * sets name of Artikel Object by name
   * 
   * @param name    name
   * @param newName neuer name
   * @throws ExceptionArtikelNameUngültig
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelNameExistiertBereits
   */
  public void setArtikelName(String name, String newName)
      throws ExceptionArtikelNameExistiertBereits, ExceptionArtikelNameUngültig, ExceptionArtikelNichtGefunden {
    setArtikelName(findArtikelByName(name), newName);// checks fo null
  }

  /**
   * sets bestand of Artikel Object
   * 
   * @param artikel object
   * @param bestand of article
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void setArtikelBestand(Artikel artikel, int bestand) throws ExceptionArtikelUngültigerBestand {
    eventCheckBestand(artikel);
    artikel.setBestand(bestand);
  }

  /**
   * sets bestand of Artikel Object by name
   * 
   * @param name    String
   * @param bestand
   * @return
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void setArtikelBestand(String name, int bestand)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    setArtikelBestand(findArtikelByName(name), bestand);// checks fo null
  }

  /**
   * sets preis of Artikel Object
   * 
   * @param artikel object
   * @param preis   of article
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void setArtikelPreis(Artikel artikel, double preis) {
    artikel.setPreis(preis);
  }

  /**
   * sets preis of Artikel Object
   * 
   * @param name  name
   * @param preis of article
   * @throws ExceptionArtikelUngültigerBestand
   * @throws ExceptionArtikelNichtGefunden
   */
  public void setArtikelPreis(String name, double preis)
      throws ExceptionArtikelUngültigerBestand, ExceptionArtikelNichtGefunden {

    setArtikelPreis(findArtikelByName(name), preis);
  }

  // #endregion
  // #region ordnen

  private void sortList(Vector<Artikel> artikelList, Comparator<Artikel> comparator) {
    artikelList.sort(comparator);
  }

  private void sortList(SuchOrdnung ordnung, Comparator<HashMap<OrdnungIndex, ? extends Object>> comparator) {
    sortOrdnung(ordnung, comparator);
  }

  /**
   * 
   * @param artikelList
   * @param reverse
   */
  public void sortListName(Vector<Artikel> artikelList, boolean reverse) {

    Comparator<Artikel> comp = (o1, o2) -> {
      return compareName(o1, o2);
    };
    if (reverse)
      comp.reversed();

    sortList(artikelList, comp);
  }

  /**
   * 
   * @param artikelList
   * @param reverse
   */
  public void sortListPreis(Vector<Artikel> artikelList, boolean reverse) {

    Comparator<Artikel> comp = (o1, o2) -> {
      return comparePreis(o1, o2);
    };
    if(reverse) {comp = comp.reversed();}

    sortList(artikelList, comp);
  }

  /**
   * 
   * @param ordnung
   * @param reverse
   */
  public void sortListName(SuchOrdnung ordnung, boolean reverse) {

    Comparator<HashMap<SuchOrdnung.OrdnungIndex, ? extends Object>> comp = (o1, o2) -> {
      return compareName((Artikel) ordnung.getObjekt(o1), (Artikel) ordnung.getObjekt(o2));
    };

    if (reverse)
      comp.reversed();

    sortList(ordnung, comp);

  }

  /**
   * 
   * @param ordnung
   * @param reverse
   */
  public void sortListPreis(SuchOrdnung ordnung, boolean reverse) {
    Comparator<HashMap<SuchOrdnung.OrdnungIndex, ? extends Object>> comp = (o1, o2) -> {
      return comparePreis((Artikel) ordnung.getObjekt(o1), (Artikel) ordnung.getObjekt(o2));
    };

    if (reverse)
    if(reverse) {comp = comp.reversed();}

    sortList(ordnung, comp);
  }

  /**
   * 
   * @param ordnung
   * @param reverse
   */
  public void sortListRelevanz(SuchOrdnung ordnung) {
    sortOrdnung(ordnung);
  }

  // comparing
  /**
   * compare names lexicographically
   * 
   * @see java.lang.String.compareTo(String anotherString)
   * 
   * @param artikel1
   * @param artikel2
   * @return the value 0 if the argument string is equal to this string; a value
   *         less than 0 if this string is lexicographically less than the string
   *         argument; and a value greater than 0 if this string is
   *         lexicographically greater than the string argument.
   */
  private int compareName(Artikel artikel1, Artikel artikel2) {
    return artikel1.getName().compareTo(artikel2.getName());
  }

  /**
   * compare names lexicographically
   * 
   * @param artikel1
   * @param artikel2
   * @return int, -n to +n
   */
  private int comparePreis(Artikel artikel1, Artikel artikel2) {
    return Double.compare(artikel1.getPreis(), artikel2.getPreis());
  }

  // #endregion
  // #region user interface suchen

  /**
   * suce meherere Artikel in der Artikel liste
   * 
   * @param suchBegriffe
   * @return SuchOrdnung
   */
  public SuchOrdnung suchArtikel(String suchBegriffe) {

    return SearchCompileOrdnungSorted(compileLagerInhalt(), suchBegriffe);
  }

  // #endregion
  // #region persistenz

  /**
   * 
   * @param artikelDox
   * @throws IOException
   */
  private void autoLoad(String dataNamingConvention) throws IOException {
    String str;
    int i = 0;
    try {
      do {
        str = dataNamingConvention.replace("#", Integer.toString(i));
        newLager(load(str));
        i++;
      } while (true);
    } catch (IOException e) {
      if (i == 0)// didnt load anything
        throw e;
    }
  }

  /**
   * 
   * @param datei
   * @return Vector<Artikel>
   * @throws IOException
   */
  private Vector<Artikel> load(String datei) throws IOException {
    // @SuppressWarnings("unchecked")
    Vector<String[]> vec = persistenceManager.loadData(datei);

    Vector<Artikel> artikelListe = new Vector<Artikel>();

    // erstelle für jeden eintrag in der liste einen Artikel
    // und füge ihn einem Vector hinzu
    for (String[] strings : vec) {
      // artikelnr;name;bestand;preis;stückzahl
      artikelListe.add(
          createArtikel(
              Integer.parseInt(strings[0]), // artikelnr
              strings[1], // name
              Integer.parseInt(strings[2]), // bestand
              Double.parseDouble(strings[3]), // preis
              (strings.length == 5 ? Integer.parseInt(strings[4]) : 1))// stückzahl
      );
    }

    return artikelListe;
  }

  /**
   * Speichert daaten
   *
   * @param datei Datei
   * @throws IOException
   */
  private boolean save(String datei, Lager lager) throws IOException {

    return persistenceManager.saveData(datei, getArtikelList(lager));
  }

  /**
   * Speichert alles daten
   * 
   * @throws IOException
   */
  public boolean save(String dataNamingConvention) throws IOException {

    boolean ergebnis = true;
    String str;
    int i = 0;
    for (int index = 0; index < lagerList.size(); index++) {
      str = dataNamingConvention.replace("#", Integer.toString(i));

      if (!save(str, getLager(index)))
        ergebnis = false;
    }

    return ergebnis;
  }

  /**
   * Speichert alles daten
   * 
   * @throws IOException
   */
  public boolean save() throws IOException {

    return save(artikelDox);
  }

  // #endregion
  // #region ereignisse
  /**
   * internaql check für bestands probleme
   * 
   * @param artikel
   * @throws ExceptionArtikelUngültigerBestand
   */
  private void eventCheckBestand(Artikel artikel) throws ExceptionArtikelUngültigerBestand {
    if (artikel.getBestand() <= 0) {
      EreignisSystemArtikel(this, "Artikel '" + artikel + "' Bestand ist niedrig!", artikel);
      if (artikel.getBestand() < 0) {
        throw new ExceptionArtikelUngültigerBestand(artikel);
      }
    }
  }

  /**
   * 
   * @param artikel
   * @param num
   * @return true wenn bestand da ist, egal ob die exception trowd oder nicht
   * @throws ExceptionArtikelUngültigerBestand
   */
  public boolean eventCheckBestand(Artikel artikel, int num) {
    try {
      eventCheckBestand(artikel);
      return (artikel.getBestand() >= num);
    } catch (ExceptionArtikelUngültigerBestand e) {
      return false;
    }
  }

  // #endregion
  // #region displaying
  /**
   * display list
   * 
   * @param list
   * @param detailed
   * @return
   */
  public String displayArtikel(Vector<Artikel> list, boolean detailed, String leereNachicht) {
    String sepStr = "///////////////////////////////////////////////\n\n";
    String str = "";
    if (list.isEmpty()) {
      str += "\t" + leereNachicht + "\n";
    } else {
      if (detailed) {
        for (Artikel artikel : list) {
          str += sepStr;
          str += artikel.toStringDetailed() + "\n";
        }
        str += sepStr;

      } else {
        str += "Name | Preis\n";
        for (Artikel artikel : list) {
          str += artikel.toString() + "\n";
        }
      }
    }

    return str;
  }

  /**
   * displays ordnung
   * 
   * @param ordnung
   * @param detailed
   * @param header
   * @param leereNachicht
   * @return
   */
  public String displayArtikel(SuchOrdnung ordnung, boolean detailed, String leereNachicht) {
    String str = "";

    if (!detailed)
      str += "Name | Preis\n";
    str += ordnung.display(detailed, leereNachicht);

    return str;
  }

  /**
   * displayed alle lager nacheinander
   * 
   * @param detailed
   * @param leereNachicht
   * @return
   */
  public String displayArtikelAll(boolean detailed, String leereNachicht) {
    String str = "";

    for (Lager lager : getLagerList()) {

      str += displayArtikel(getArtikelList(lager), detailed, leereNachicht);
    }

    return str;
  }

  /**
   * erstellt eine liste aus allen lagern undstellt sie dar
   * 
   * @param detailed
   * @param leereNachicht
   * @return
   */
  public String displayArtikelAllCompiled(boolean detailed, String leereNachicht) {

    return displayArtikel(compileLagerInhalt(), detailed, leereNachicht);
  }

  /**
   * compiles artikel list aus allen Lagern
   * public usage
   * 
   * @return Vector<Artikel> liste an allen aartikeln
   */
  public Vector<Artikel> getAlleArtikelList() {
    return compileLagerInhalt();
  }

  @Override
  public String toString() {
    return displayArtikelAll(false, "Keine Artikel Vorhanden");
  }

  public String toString(boolean detailed, String leereNachicht) {
    return displayArtikelAll(detailed, leereNachicht);
  }
  // #endregion
}