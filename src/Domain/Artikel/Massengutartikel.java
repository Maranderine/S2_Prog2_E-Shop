package Domain.Artikel;

/**
 * massengut
 */
public class Massengutartikel extends Artikel {

  /** gebündelte anzahl des artikels */
  private int stückZahl;

  /**
   * massengut
   * 
   * @param artikelNr
   * @param name
   * @param bestand
   * @param einzelpreis
   * @param stückZahl
   */
  public Massengutartikel(int artikelNr, String name, int bestand, double einzelpreis, int stückZahl) {
    super(artikelNr, name, bestand, einzelpreis);
    this.stückZahl = stückZahl;

    // adding search terms
    String[] searchTermsToAdd = { "massengut" };
    SearchTermAdd(searchTermsToAdd);
  }

  // Gibt die stückZahl aus
  public int getstückZahl() {
    return stückZahl;
  }

  // Setzt die stückZahl als Integer-Wert fest
  public void setstückZahl(int stückZahl) {
    this.stückZahl = stückZahl;
  }

  ///////////// to String /////////////////
  @Override
  public String toString() {
    // return this.name + "##\t" + this.preis;
    return super.toStringRaw().replace("##", " (" + this.stückZahl + ")");
  }

  @Override
  public String toStringDetailed() {
    // "Artikel--" +
    // "\nName: '" + this.name + "'" +
    // "\nNummer: " + this.artikelNr +
    // "\nBestand: " + this.bestand + "##" +
    // "\nPreis: " + this.preis;
    return super.toStringDetailedRaw().replace("##", "\nInhalt: " + this.stückZahl);
  }

  @Override
  public String toData() {
    return super.toData() + ";" + this.stückZahl;
  }
}