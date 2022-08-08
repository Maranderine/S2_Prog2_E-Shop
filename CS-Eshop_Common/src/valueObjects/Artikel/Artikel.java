package Domain.Artikel;

import java.io.Serializable;

import Domain.Search.Searchable;
import persistence.PersistentDataObjekt;

public class Artikel extends Searchable implements Serializable, PersistentDataObjekt {

  private final int artikelNr;
  private String name;
  private int bestand;
  private double preis;

  protected Artikel(int artikelNr, String name, int bestand, double einzelpreis) {
    super();
    this.artikelNr = artikelNr;
    this.name = name;
    this.bestand = bestand;
    this.preis = einzelpreis;

    // adding search terms
    String[] searchTermsToAdd = { "artikel", Integer.toString(this.artikelNr) };
    SearchTermAdd(searchTermsToAdd);
    // add name in split parts
    SearchTermAdd(this.name.split(" "));
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
    return this.name + "##\t" + this.preis;
  }

  /**
   * toString in einer form die nicht direkt ausgegeben werden sollte
   * aber weiter bearbeiotet werden kann
   * 
   * @return
   */
  protected String toStringDetailedRaw() {
    return "Artikel--" +
        "\nName: '" + this.name + "'" +
        "\nNummer: " + this.artikelNr +
        "\nBestand: " + this.bestand + "##" +
        "\nPreis: " + this.preis +
        "\n\nSearch Terms:\n" +
        SearchDisplayTerms();
  }

  // tostring
  @Override
  public String toString() {
    // return this.name + "##\t" + this.preis;
    return toStringRaw().replace("##", "");
  }

  @Override
  public String toStringDetailed() {
    // "Artikel--" +
    // "\nName: '" + this.name + "'" +
    // "\nNummer: " + this.artikelNr +
    // "\nBestand: " + this.bestand + "##" +
    // "\nPreis: " + this.preis;
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