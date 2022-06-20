package Domain.Artikel;

import java.util.Vector;

public class Lager {

  protected Vector<Artikel> artikelListe;
  private static int artikelNrCount = 0;

  public Lager() {
    this.artikelListe = new Vector<Artikel>();
  }

  public Lager(Vector<Artikel> vec) {
    this.artikelListe = vec;
  }

  protected int useZaehler() {
    return artikelNrCount++;
  }

  protected void setZaehler(int num) {
    artikelNrCount = num;
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public String toString(boolean detailed) {
    String str = "";
    if (!artikelListe.isEmpty()) {
      if (detailed) {
        str += "Artikelnr | Name | Bestand | Preis\n";
        for (Artikel artikel : artikelListe) {
          str += artikel.toStringDetailed() + "\n";
        }
      } else {
        str += "Artikelnr | Name | Preis\n";
        for (Artikel artikel : artikelListe) {
          str += artikel.toString() + "\n";
        }
      }
    } else
      str += "Keine Artikel";

    return str;
  }
}
