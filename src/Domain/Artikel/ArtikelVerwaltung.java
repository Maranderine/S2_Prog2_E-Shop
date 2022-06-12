package Domain.Artikel;

import java.io.IOException;
import java.util.Vector;

import Domain.Eshop;
import Domain.Verwaltung;
import Domain.Search.SuchOrdnung;
import Exceptions.artikelNichtGefundenException;
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
   */
  public Artikel addArtikel(String name, int bestand, double einzelpreis) {
    Artikel artikel = findArtikelByName(name);
    if (artikel == null) {
      artikel = new Artikel(Lager.artikelNrCount, name, bestand, einzelpreis);
      lager.artikelListe.add(artikel);
      Lager.artikelNrCount++;
    }
    return artikel;
  }

  public void addArtikel(Artikel artikel) {
    lager.artikelListe.add(artikel);
    Lager.artikelNrCount++;
  }

  /**
   * Deletes a artikel from the artikelListe
   * 
   * @param name of artikel
   * @return boolean, true if something was deleted, false if not
   */
  public boolean deleteArtikel(Artikel artikel) {
    return this.lager.artikelListe.remove(artikel);// delete from list
  }

  /**
   * Deletes a artikel from the artikelListe by name
   * 
   * @param name of artikel
   * @return boolean, true if something was deleted, false if not
   */
  public boolean deleteArtikel(String name) {
    // search for Artikel
    Artikel artikel;
    try {
      artikel = findArtikelByName(name);
    } catch (artikelNichtGefundenException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (artikel != null)// if Artikel is found
      return deleteArtikel(artikel);

    return false;// if nothing could be deleted
  }

  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object or null
   * @throws artikelNichtGefundenException
   */
  public Artikel findArtikelByName(String name) throws artikelNichtGefundenException {
    // iterates through artikelListe
    for (Artikel artikel : this.lager.artikelListe) {
      if (artikel.getName().equals(name))
        return artikel;
    }
    throw new artikelNichtGefundenException();
  }

  /**
   * gets Artikel Object from artikelListe by index
   * 
   * @param index of the Artikel to return
   * @return Artikel type Object
   * @throws artikelNichtGefundenException
   */
  public Artikel getArtikel(int index) throws artikelNichtGefundenException {
    if (index < lager.artikelListe.size())
      return lager.artikelListe.get(index);

    throw new artikelNichtGefundenException();
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
   */
  public boolean setArtikelName(Artikel artikel, String newName) {
    if (artikel != null) {
      artikel.setName(newName);
      return true;
    }
    return false;
  }

  /**
   * sets name of Artikel Object by name
   * 
   * @param name    name
   * @param newName neuer name
   */
  public boolean setArtikelName(String name, String newName) {
    return setArtikelName(findArtikelByName(name), newName);// checks fo null
  }

  /**
   * sets bestand of Artikel Object
   * 
   * @param artikel object
   * @param bestand of article
   */
  public boolean setArtikelBestand(Artikel artikel, int bestand) {
    if (artikel != null) {
      artikel.setBestand(bestand);
      eventCheckBestand(artikel);
      return true;
    }
    return false;
  }

  /**
   * sets bestand of Artikel Object by name
   * 
   * @param name    String
   * @param bestand
   * @return
   */
  public boolean setArtikelBestand(String name, int bestand) {
    return setArtikelBestand(findArtikelByName(name), bestand);// checks fo null
  }

  /**
   * sets preis of Artikel Object
   * 
   * @param artikel object
   * @param preis   of article
   * @return
   */
  public boolean setArtikelPreis(Artikel artikel, double preis) {
    if (artikel != null) {
      artikel.setPreis(preis);
      return true;
    }
    return false;
  }

  /**
   * sets preis of Artikel Object
   * 
   * @param name  name
   * @param preis of article
   */
  public boolean setArtikelPreis(String name, double preis) {
    return setArtikelPreis(findArtikelByName(name), preis);
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

  private void eventCheckBestand(Artikel artikel) {
    if (artikel.getBestand() <= 0) {
      EreignisSystemArtikel(this, "Artikel '" + artikel + "' Bestand ist niedrig!", artikel);
    }
  }

  // #endregion
}