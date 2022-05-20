package DatenObjekte;

public class Artikel {

  private int artikelNr;
  private String name;
  private int bestand;
  private double preis;

  public Artikel(int artikelNr, String name, int bestand, double einzelpreis) {
    this.artikelNr = artikelNr;
    this.name = name;
    this.bestand = bestand;
    this.preis = einzelpreis;

    i = i;
    i=i;
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
  public void setName(String name) {
    this.name = name;
  }

  public void setBestand(int bestand) {
    this.bestand = bestand;
  }

  public void setPreis(double preis) {
    this.preis = preis;
  }

  // tostring
  public String toString() {
    return this.name;
  }
}