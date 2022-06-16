package Domain.Artikel;

import java.io.IOException;
import java.util.Vector;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Search.SuchOrdnung;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
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
   * Add Artikel to artikelListe
   * 
   * @param name
   * @param bestand
   * @param einzelpreis
   * @return
   * @throws ExceptionArtikelExistiertBereits
   */
  public Artikel addArtikel(String name, int bestand, double einzelpreis) throws ExceptionArtikelExistiertBereits {

    Artikel artikel;

    try {
      // guck ob artikel existiert | wenn nciht kommt iene exception
      artikel = findArtikelByName(name);
      // Artikel existiert und wir erstellen eine neue exception
      throw new ExceptionArtikelExistiertBereits(artikel);
    } catch (ExceptionArtikelNichtGefunden e) {
      // Artikel existiert nicht also machen wir einene neuen
      artikel = new Artikel(Lager.artikelNrCount, name, bestand, einzelpreis);
      lager.artikelListe.add(artikel);
      Lager.artikelNrCount++;
      return artikel;
    }
  }

  public void addArtikel(Artikel artikel) {
    lager.artikelListe.add(artikel);
    Lager.artikelNrCount++;
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
   * @return bool ob er existiert
   * @throws ExceptionArtikelNichtGefunden
   */
  public boolean ArtikelExists(String name) {
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
   */
  public void setArtikelName(Artikel artikel, String newName) throws ExceptionArtikelNameExistiertBereits {
    try {
      checkForName(newName);
      artikel.setName(newName);
    } catch (ExceptionArtikelNameExistiertBereits e) {
      throw e;
    }
  }

  /**
   * sets name of Artikel Object by name
   * 
   * @param name    name
   * @param newName neuer name
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelNameExistiertBereits
   */
  public void setArtikelName(String name, String newName)
      throws ExceptionArtikelNameExistiertBereits, ExceptionArtikelNichtGefunden {
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

  public SuchOrdnung suchArtikel(String suchBegriffe) {
    return SearchCompileOrdnung(this.lager.artikelListe, suchBegriffe);
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
    Vector<Artikel> vec = (Vector<Artikel>) persistenceManager.loadArticle(datei);

    return vec;
  }

  /**
   * Speichert daaten
   *
   * @param datei Datei
   * @throws IOException
   */
  private boolean save(String datei) throws IOException {
    return persistenceManager.saveArticle(datei, lager.artikelListe);
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

  private void checkForName(String name) throws ExceptionArtikelNameExistiertBereits {
    Artikel artikel;
    try {
      artikel = findArtikelByName(name);
    } catch (ExceptionArtikelNichtGefunden e) {
      // dont print stack
      return;
    }
    throw new ExceptionArtikelNameExistiertBereits(artikel);
  }

  // #endregion
}