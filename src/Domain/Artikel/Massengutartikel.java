package Domain.Artikel;

public class Massengutartikel extends Artikel {

  /** gebündelte anzahl des artikels */
  private int minAnzahl;

  public Massengutartikel(int artikelNr, String bezeichnung, int bestand, double einzelpreis, int minAnzahl) {
    super(artikelNr, bezeichnung, bestand, einzelpreis);
    this.minAnzahl = minAnzahl;

    // adding search terms
    String[] searchTerms = { "mass", "massen" };
    SearchTermAdd(searchTerms);
  }

  // Gibt die MinAnzahl aus
  public int getMinAnzahl() {
    return minAnzahl;
  }

  // Setzt die MinAnzahl als Integer-Wert fest
  public void setMinAnzahl(int minAnzahl) {
    this.minAnzahl = minAnzahl;
  }

  ///////////// to String /////////////////
  @Override
  public String toString() {
    return super.toStringRaw().replace("##", Integer.toString(this.minAnzahl));
  }

  @Override
  public String toStringDetailed() {
    return super.toStringDetailedRaw().replace("##", Integer.toString(this.minAnzahl));
  }

  @Override
  public String toData() {
    return super.toData() + ";" + this.minAnzahl;
  }
}