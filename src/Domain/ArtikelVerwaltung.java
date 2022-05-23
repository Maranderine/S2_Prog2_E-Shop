package Domain;

// import Exceptions.ArtikelExistiertBereitsException;
import DatenObjekte.Artikel;

import java.util.Vector;

public class ArtikelVerwaltung {

  private Vector<Artikel> artikelListe;

  public ArtikelVerwaltung() {
    artikelListe = new Vector<Artikel>();
  }

  public Vector<Artikel> alleArtikel(){
    return artikelListe;
  } 
  
  public Artikel searchArtikel(String titel){
    Vextor<artikel> ergebnis = new Vector<>();
    for (Artikel artikel : artikelListe) {
      if (artikel.getName().equals(titel)) {
        ergebnis.add(artikel);
        return ergebnis;
      }
    }
    return null;
  }

  public void addArtikel(int artikelNr, String name, int bestand, double einzelpreis) /*throws ArtikelExistiertBereitsException*/ {
    if(searchArtikel(name) == null){
      Artikel artikel = new Artikel(int artikelNr, String name, int bestand, double einzelpreis);
      artikelListe.add(artikel);
    }
  }

  public void setBestand(String titel, Int aufLager){
    Artikel artikel = searchArtikel(titel);
    artikelListe.set(artikelListe.IndexOf(artikel), artikel.setBestand(aufLager));
  }

  public void removeArtikel(String titel){
    artikelListe.remove(this.searchArtikel(titel));
  }
  // persistenz ///////////////////////////////
  /**
   * Läd eine artikel liste aus dem speicher
   */
  public void loadListe() {
    // TEMP kreirt nur eine liste
    artikelListe = new Vector<Artikel>();
  }

}
