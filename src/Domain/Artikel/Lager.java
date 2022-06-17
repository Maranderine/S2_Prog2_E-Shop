package Domain.Artikel;

import java.util.Vector;

public class Lager {

  protected Vector<Artikel> artikelListe;
  protected static int artikelNrCount = 0;

  public Lager() {
    this.artikelListe = new Vector<Artikel>();
  }

  public Lager(Vector<Artikel> vec) {
    this.artikelListe = vec;
  }

  public String toString() {
    String str = "Artikelnr | Name | Bestand | Preis";
    if (!artikelListe.isEmpty())
      for (Artikel artikel : artikelListe) {
        str += artikel.toStringDetailled() + "\n";
      }
    else
      str += "Keine Artikel";

    return str;
  }
}
