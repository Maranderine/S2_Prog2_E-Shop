package Domain.Artikel;
import java.util.Vector;

public class ArtikelVerwaltung {

  private Lager lager;

  public ArtikelVerwaltung() {
    lager = new Lager();
  }

  /**
   * @author Maranderine
   */
  public Lager alleArtikel() {
    return lager;
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
      return this.lager.artikelListe.remove(artikel);// delete from list

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
      if (artikel.getName().equals(name)) return artikel;
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

  // #region set ///////////////////////////////
  /**
   * sets name of Artikel Object
   * 
   * @param artikel object
   * @param name    of article
   */
  public boolean setArtikelName(String name, String newName) {
    findArtikelByName(name).setName(newName); 
    return true;
  }

  /**
   * sets bestand of Artikel Object
   * 
   * @param artikel object
   * @param bestand of article
   */
  public boolean setArtikelBestand(String name, int bestand) {
    findArtikelByName(name).setBestand(bestand); 
    return true;
  }

  /**
   * sets preis of Artikel Object
   * 
   * @param artikel object
   * @param preis   of article
   */
  public boolean setArtikelPreis(String name, double preis) {
    findArtikelByName(name).setPreis(preis);
    return true;
  }

  // #endregion
  // #region persistenz ///////////////////////////////
  /**
   * Läd eine artikel liste aus dem speicher
   */
  public void loadListe() {
    // TEMP kreirt nur eine liste
    lager.artikelListe = new Vector<Artikel>();
  }
  // #endregion
}
