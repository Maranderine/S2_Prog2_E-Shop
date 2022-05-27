package Domain.Artikel;

public class Artikel {

  private int artikelNr;
  private String name;
  private int bestand;
  private double preis;

  protected Artikel(int artikelNr, String name, int bestand, double einzelpreis) {
    this.artikelNr = artikelNr;
    this.name = name;
    this.bestand = bestand;
    this.preis = einzelpreis;

  }

  // getter
  public int getArtikelNr() {
    return this.artikelNr;
  }

  public String getName() {
    return this.name;
  }

  public int getBestand() {
    return this.bestand;
  }

  public double getPreis() {
    return this.preis;
  }

  // setter
  protected void setName(String name) {
    this.name = name;
  }

  protected void setBestand(int bestand) {
    this.bestand = bestand;
  }

  protected void setPreis(double preis) {
    this.preis = preis;
  }

  // tostring
  public String toString() {
    // return this.artikelNr + " | " + this.name + " | " + this.bestand + " | " +
    // this.preis;
    return this.artikelNr + "\t" + this.name + "\t" + this.bestand + "\t" + this.preis;
  }
}