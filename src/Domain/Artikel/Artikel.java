package Domain.Artikel;

import java.io.Serializable;

import Domain.Search.Searchable;

public class Artikel extends Searchable implements Serializable{

  private final int artikelNr;
  private String name;
  private int bestand;
  private double preis;

  public Artikel(int artikelNr, String name, int bestand, double einzelpreis) {
    this.artikelNr = artikelNr;
    this.name = name;
    this.bestand = bestand;
    this.preis = einzelpreis;

    // adding search terms
    /*
     * artik-el/-le
     */
    String[] searchTerms = { "artik", this.name, Integer.toString(this.artikelNr) };
    SearchTermAdd(searchTerms);
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
    SearchTermReplace(this.name, name);

    this.name = name;
  }

  protected void setBestand(int bestand) {
    this.bestand = bestand;
  }

  protected void setPreis(double preis) {
    this.preis = preis;
  }

  ///////////// to String /////////////////
  /**
   * toString in einer form die nicht ausgegeben werden sollte
   * aber weiter bearbeiotet werden kann
   * 
   * @return
   */
  protected String toStringRaw() {
    return this.artikelNr + "\t" + this.name + "\t##\t" + this.preis;
  }

  /**
   * toString in einer form die nicht direkt ausgegeben werden sollte
   * aber weiter bearbeiotet werden kann
   * 
   * @return
   */
  protected String toStringDetailedRaw() {
    return this.artikelNr + "\t" + this.name + "\t" + this.bestand + "\t" + this.preis;
  }

  // tostring
  @Override
  public String toString() {
    // return this.artikelNr + " | " + this.name + " | " + this.bestand + " | " +
    // this.preis;
    return toStringRaw().replace("##", "");
  }

  public String toStringDetailed() {
    // return this.artikelNr + " | " + this.name + " | " + this.bestand + " | " +
    // this.preis;
    return toStringDetailedRaw().replace("##", "");
  }

  /**
   * gibt alle relevanten daten des objekt in einer speicherbaren form wieder
   * 
   * @return
   */
  public String toData() {
    // return this.artikelNr + " | " + this.name + " | " + this.bestand + " | " +
    // this.preis;
    return this.artikelNr + ";" + this.name + ";" + this.bestand + ";" + this.preis;
  }
}