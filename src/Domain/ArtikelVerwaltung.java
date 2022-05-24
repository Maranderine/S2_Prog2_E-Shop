package Domain;

// import Exceptions.ArtikelExistiertBereitsException;
import DatenObjekte.Artikel;

import java.util.Vector;

public class ArtikelVerwaltung {

  private Vector<Artikel> artikelListe;
  static int artikelNrCount = 0;

  public ArtikelVerwaltung() {
    artikelListe = new Vector<Artikel>();
  }

  /**
  *@author Maranderine
  */
  public Vector<Artikel> alleArtikel(){
    return artikelListe;
  } 
  /**
  *@author Maranderine
  */
  public Vector<Artikel> searchArtikel(String titel){
    Vector<Artikel> ergebnis = new Vector<Artikel>();
    for (Artikel artikel : artikelListe) {
      if (artikel.getName().equals(titel)) {
        ergebnis.add(artikel);
        return ergebnis;
      }
          }
    return null;
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
     if(artikel == null){
      artikel = new Artikel(artikelNrCount, name, bestand, einzelpreis);
      artikelListe.add(artikel);
      artikelNrCount++;
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
      return artikelListe.remove(artikel);// delete from list

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
    for (Artikel artikel : artikelListe) {
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
    if (index < artikelListe.size())
      return artikelListe.get(index);

    return null;
  }

  // #region set ///////////////////////////////
  /**
   * sets name of Artikel Object
   * 
   * @param artikel object
   * @param name    of article
   */
  public void setArtikelName(Artikel artikel, String name) {
    artikel.setName(name);
  }

  /**
   * sets bestand of Artikel Object
   * 
   * @param artikel object
   * @param bestand of article
   */
  public void setArtikelBestand(Artikel artikel, int bestand) {
    artikel.setBestand(bestand);
  }

  /**
   * sets preis of Artikel Object
   * 
   * @param artikel object
   * @param preis   of article
   */
  public void setArtikelPreis(Artikel artikel, double preis) {
    artikel.setPreis(preis);
  }

  // #endregion
  // #region persistenz ///////////////////////////////
  /**
   * Läd eine artikel liste aus dem speicher
   */
  public void loadListe() {
    // TEMP kreirt nur eine liste
    artikelListe = new Vector<Artikel>();
  }
  // #endregion

  @Override
  public String toString() {
    String str = "Artikelnr\tName\tBestand\tPreis\n";
    if (!artikelListe.isEmpty())
      for (Artikel artikel : artikelListe) {
        str += "\t" + artikel.toString() + "\n";
      }
    else
      str += "Keine Artikel";

    return str;
  }
}
