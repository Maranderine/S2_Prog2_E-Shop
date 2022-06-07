package Domain.Artikel;

import java.io.IOException;

import persistence.FilePersistenceManager;
import persistence.PersistenceManager;

public class ArtikelVerwaltung {

  private Lager lager;
  private PersistenceManager pm = new FilePersistenceManager();

  public ArtikelVerwaltung() {
    lager = new Lager();
  }

  // #region persistenz
  /**
   * @author Maranderine
   */
  public void liesDaten(String datei) throws IOException {
    // PersistenzManager für Lesevorgänge öffnen
    pm.openForReading(datei);

    Artikel artikel;
    while ((artikel = pm.ladeArtikel()) != null) {
      addArtikel(artikel);
    }
    // Persistenz-Schnittstelle wieder schließen
    pm.close();
  }

  /**
   * Methode zum Schreiben der Buchdaten in eine Datei.
   *
   * @param datei Datei, in die der Bücherbestand geschrieben werden soll
   * @throws IOException
   */
  public void schreibeDaten(String datei) throws IOException {
    // PersistenzManager für Schreibvorgänge öffnen
    pm.openForWriting(datei);

    for (Artikel artikel : this.lager.artikelListe) {
      pm.speichereArtikel(artikel);
    }
    pm.close();
  }

  // #endregion

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
    Artikel artikel = findArtikelByName(name);
    if (artikel != null)// if Artikel is found
      return deleteArtikel(artikel);

    return false;// if nothing could be deleted
  }

  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object or null
   */
  public Artikel findArtikelByName(String name) {
    // iterates through artikelListe
    for (Artikel artikel : this.lager.artikelListe) {
      if (artikel.getName().equals(name))
        return artikel;
    }
    return null;
  }

  /**
   * gets Artikel Object from artikelListe by index
   * 
   * @param index of the Artikel to return
   * @return Artikel type Object or null
   */
  public Artikel getArtikel(int index) {
    if (index < lager.artikelListe.size())
      return lager.artikelListe.get(index);

    return null;
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
}