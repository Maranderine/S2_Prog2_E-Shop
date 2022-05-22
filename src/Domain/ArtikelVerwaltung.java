package Domain;

// import Exceptions.ArtikelExistiertBereitsException;
import DatenObjekte.Artikel;

import java.util.Vector;

public class ArtikelVerwaltung {

  private Vector<Artikel> artikelListe;

  public ArtikelVerwaltung() {
    artikelListe = new Vector<Artikel>();
  }

  public void addArtikel() /*throws ArtikelExistiertBereitsException*/ {

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
