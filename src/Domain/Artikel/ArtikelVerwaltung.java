package Domain.Artikel;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Pattern;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Search.SuchOrdnung;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameUngültig;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import persistence.FilePersistenceManager;
import persistence.PersistenceManager;

public class ArtikelVerwaltung extends Verwaltung {

  private Lager lager;
  private final Eshop eshop;
  private PersistenceManager persistenceManager = new FilePersistenceManager();
  private String artikelDox;

  public ArtikelVerwaltung(Eshop eshop, String artikelDox) {
    this.eshop = eshop;
    this.artikelDox = artikelDox;

    try {
      lager = new Lager(load(artikelDox));
    } catch (IOException e) {
      lager = new Lager();
      e.printStackTrace();
      System.out.println("Could not load BenutzerVerwaltung");
    }

  }

  public Lager alleArtikel() {
    return this.lager;
  }

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
      int packungsInhalt)
      throws ExceptionArtikelExistiertBereits {
    Lager lagerToUse = this.lager;
    Artikel artikel;

    try {
      // guck ob artikel existiert | wenn nciht kommt iene exception
      artikel = findArtikelByName(name);
      // Artikel existiert und wir erstellen eine neue exception
      throw new ExceptionArtikelExistiertBereits(artikel);
    } catch (ExceptionArtikelNichtGefunden e) {
      // Artikel existiert nicht also machen wir einene neuen
      artikel = createArtikel(artikelNummer, name, bestand, einzelpreis, packungsInhalt);
      addArtikelToLager(artikel, lagerToUse);
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
   */
  public Artikel addArtikel(String name, int bestand, double einzelpreis, int packungsInhalt)
      throws ExceptionArtikelExistiertBereits {
    // get lager was artikel aufnehmen soll
    Lager lager = getLagerToAdd();
    // add artikel
    return addArtikel(lager, lager.useZaehler(), name, bestand, einzelpreis, packungsInhalt);
  }

  /**
   * Deletes a artikel from the artikelListe
   * 
   * @param artikel obj
   * @return boolean, true if something was deleted, false if not
   * @throws ExceptionArtikelNichtGefunden
   */
  public boolean deleteArtikel(Artikel artikel) {
    return this.lager.artikelListe.remove(artikel);
  }

  /**
   * Deletes a artikel from the artikelListe by name
   * 
   * @param name of artikel
   * @return boolean, true if something was deleted, false if not
   */
  public boolean deleteArtikel(String name) throws ExceptionArtikelNichtGefunden {

    Artikel artikel = findArtikelByName(name);
    deleteArtikel(artikel);

    return true;// if nothing could be deleted
  }

  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object
   * @throws ExceptionArtikelNichtGefunden
   */
  public Artikel findArtikelByName(String name) throws ExceptionArtikelNichtGefunden {
    // iterates through artikelListe
    for (Artikel artikel : this.lager.artikelListe) {
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
    // iterates through artikelListe
    for (Artikel artikel : this.lager.artikelListe) {
      if (artikel.getName().equals(name))
        return true;
    }
    return false;
  }

  /**
   * gets Artikel Object from artikelListe by index
   * 
   * @param index of the Artikel to return
   * @return Artikel type Object
   * @throws ExceptionArtikelNichtGefunden
   */
  public Artikel getArtikel(int index) throws ExceptionArtikelNichtGefunden {
    if (index < lager.artikelListe.size())
      return lager.artikelListe.get(index);

    throw new ExceptionArtikelNichtGefunden();
  }

  /**
   * chgeckt ob name gültig ist
   * 
   * @return true wenn alles okay
   */
  public boolean checkName(String string) throws ExceptionArtikelNameUngültig, ExceptionArtikelNameExistiertBereits {
    // TODO do regex pattern checking
    // pattern.matcher(input).matches()
    if (string.equals(null))
      throw new ExceptionArtikelNameUngültig();

    try {
      Artikel art = findArtikelByName(string);
      throw new ExceptionArtikelNameExistiertBereits(art);
    } catch (ExceptionArtikelNichtGefunden e) {
      return true;
    }

  }

  // #region Lager
  /**
   * gibt lager welches den artikel gegeben bekommen soll
   * 
   * @return Lager
   */
  private Lager getLagerToAdd() {
    // temp auswahl verfahren bei mehereren lagern nach kriterien die sinn machen
    return this.lager;
  }

  /**
   * add artikel to lager
   * 
   * @param artikel
   * @param lager
   */
  private void addArtikelToLager(Artikel artikel, Lager lager) {
    lager.artikelListe.add(artikel);
  }

  // #endregion
  // #region getter

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
      throws ExceptionArtikelNameExistiertBereits, ExceptionArtikelNameUngültig {

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
    artikel.setBestand(bestand);
    eventCheckBestand(artikel);
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
  // #region suchen

  /**
   * suce meherere Artikel in der Artikel liste
   * 
   * @param suchBegriffe
   * @return SuchOrdnung
   */
  public SuchOrdnung suchArtikel(String suchBegriffe) {
    return SearchCompileOrdnungSorted(this.lager.artikelListe, suchBegriffe);
  }

  // #endregion
  // #region persistenz
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
  private boolean save(String datei) throws IOException {
    return persistenceManager.saveData(datei, lager.artikelListe);
  }

  /**
   * Speichert daaten
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

  @Override
  public String toString() {

    return toString(false);
  }

  public String toString(boolean detailed) {

    return this.lager.toString(detailed);
  }

}