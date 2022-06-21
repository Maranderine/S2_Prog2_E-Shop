package Domain.Artikel;

import java.util.Vector;

public class Lager {

  private Vector<Artikel> artikelListe;
  private static int artikelNrCount = 0;

  protected Lager() {
    this.artikelListe = new Vector<Artikel>();
  }

  protected Lager(Vector<Artikel> vec) {
    this.artikelListe = vec;
  }

  public Vector<Artikel> getList() {
    return this.artikelListe;
  }

  public boolean addItem(Artikel obj) {
    return this.artikelListe.add(obj);
  }

  public boolean removeItem(Artikel obj) {
    return this.artikelListe.remove(obj);
  }

  protected int useZaehler() {
    return artikelNrCount++;
  }

  protected void setZaehler(int num) {
    artikelNrCount = num;
  }

  public String toString() {
    return artikelListe.toString();
  }
}
